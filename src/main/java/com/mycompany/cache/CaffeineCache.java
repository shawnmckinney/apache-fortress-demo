/*
 * © 2025 iamfortress.net
 */
package com.mycompany.cache;

import com.github.benmanes.caffeine.cache.Caffeine;
import org.apache.directory.fortress.core.util.cache.DsdCacheEntry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

/**
 * Caffeine-backed implementation of the fortress {@link Cache} interface.
 *
 * <h3>Searchable DSD index</h3>
 * When a {@link DsdCacheEntry} is stored the implementation automatically maintains
 * three secondary indices so that cache entries can be searched by:
 * <ul>
 *   <li>{@code member}    – the role-name of the DSD member</li>
 *   <li>{@code name}      – the DSD set name</li>
 *   <li>{@code contextId} – the tenant identifier</li>
 * </ul>
 *
 * These indices are updated synchronously on every {@link #put} / {@link #clear} / {@link #flush}.
 *
 * <h3>Querying</h3>
 * The Caffeine cache itself has no built-in search API.  Instead, {@link #search(CacheCriteria)}
 * resolves each leaf criterion against the appropriate index, intersects the result sets for AND
 * nodes, and returns the matching entries.  This mirrors the ehcache searchable-cache semantics
 * used by {@code SDUtil} in fortress-core:
 *
 * <pre>
 *   CacheAttribute&lt;String&gt; member  = cache.getSearchAttribute("member");
 *   CacheAttribute&lt;String&gt; context = cache.getSearchAttribute("contextId");
 *   CacheResults results = cache.createQuery()
 *       .includeKeys().includeValues()
 *       .addCriteria(member.in(roles).and(context.eq(contextId)))
 *       .execute();
 * </pre>
 * <p>Index cleanup on natural eviction (TTL / size) is intentionally not done via a Caffeine
 * removal listener because Caffeine dispatches removal callbacks asynchronously; this races with
 * subsequent {@link #put} calls after a {@link #flush} and can leave the index in an inconsistent
 * state.  Stale index pointers are instead filtered out at query time by checking whether the key
 * is still present in the Caffeine store.  Explicit removals ({@link #clear}/{@link #flush})
 * perform immediate, synchronous index cleanup.
 */
public class CaffeineCache implements Cache
{
    private static final Logger LOG = LoggerFactory.getLogger( CaffeineCache.class );

    static final String ATTR_MEMBER     = "member";
    static final String ATTR_NAME       = "name";
    static final String ATTR_CONTEXT_ID = "contextId";

    private final String cacheName;
    private final com.github.benmanes.caffeine.cache.Cache<String, Object> store;

    // Secondary indices: attributeValue -> set of cache keys
    private final Map<String, Set<String>> memberIndex     = new ConcurrentHashMap<>();
    private final Map<String, Set<String>> nameIndex       = new ConcurrentHashMap<>();
    private final Map<String, Set<String>> contextIdIndex  = new ConcurrentHashMap<>();

    /**
     * Create a named Caffeine cache with the supplied TTL and maximum entry count.
     *
     * @param cacheName   logical name used in log messages
     * @param maxSize     maximum number of entries before eviction
     * @param ttlSeconds  time-to-live in seconds (entries expire this long after being written)
     */
    public CaffeineCache( String cacheName, long maxSize, long ttlSeconds )
    {
        this.cacheName = cacheName;
        this.store = Caffeine.newBuilder()
            .maximumSize( maxSize )
            .expireAfterWrite( ttlSeconds, TimeUnit.SECONDS )
            .build();
    }

    // -------------------------------------------------------------------------
    // Cache interface
    // -------------------------------------------------------------------------

    @Override
    public Object get( Object key ) throws CacheException
    {
        return store.getIfPresent( key.toString() );
    }

    @Override
    public void put( Object key, Object value ) throws CacheException
    {
        String strKey = key.toString();
        // Clean up stale index entries if the key is being overwritten
        Object existing = store.getIfPresent( strKey );
        if ( existing instanceof DsdCacheEntry )
        {
            removeFromIndices( ( DsdCacheEntry ) existing, strKey );
        }
        store.put( strKey, value );
        if ( value instanceof DsdCacheEntry )
        {
            addToIndices( ( DsdCacheEntry ) value, strKey );
            if ( LOG.isDebugEnabled() )
            {
                DsdCacheEntry e = ( DsdCacheEntry ) value;
                LOG.debug( "cache [{}] put key={} member={} contextId={}", cacheName, strKey, e.getMember(), e.getContextId() );
            }
        }
    }

    @Override
    public boolean clear( Object key ) throws CacheException
    {
        String strKey = key.toString();
        Object existing = store.getIfPresent( strKey );
        if ( existing == null )
        {
            return false;
        }
        if ( existing instanceof DsdCacheEntry )
        {
            removeFromIndices( ( DsdCacheEntry ) existing, strKey );
        }
        store.invalidate( strKey );
        return true;
    }

    @Override
    public void flush() throws CacheException
    {
        store.invalidateAll();
        memberIndex.clear();
        nameIndex.clear();
        contextIdIndex.clear();
    }

    @Override
    public <T> CacheAttribute<T> getSearchAttribute( String attributeName ) throws CacheException
    {
        return new CacheAttribute<>( attributeName );
    }

    @Override
    public CacheQuery createQuery()
    {
        return new CacheQuery( this );
    }

    // -------------------------------------------------------------------------
    // Search execution
    // -------------------------------------------------------------------------

    /**
     * Execute the supplied criteria against the secondary indices and return all
     * matching cache entries.
     *
     * @param criteria criteria tree built via {@link CacheAttribute} (may be {@code null})
     * @return matching results (never {@code null})
     */
    CacheResults search( CacheCriteria criteria )
    {
        if ( criteria == null )
        {
            return new CacheResults( Collections.emptyList() );
        }
        Set<String> matchingKeys = resolveKeys( criteria );
        List<CacheResult> results = new ArrayList<>();
        for ( String key : matchingKeys )
        {
            Object value = store.getIfPresent( key );
            if ( value != null )
            {
                results.add( new CacheResult( key, value ) );
            }
        }
        return new CacheResults( results );
    }

    // -------------------------------------------------------------------------
    // Private helpers
    // -------------------------------------------------------------------------

    /**
     * Recursively resolve a criteria tree to the set of matching cache keys.
     */
    @SuppressWarnings( "unchecked" )
    private Set<String> resolveKeys( CacheCriteria criteria )
    {
        if ( criteria.isCompound() )
        {
            // AND: intersect the key sets of both sides
            Set<String> leftKeys  = resolveKeys( criteria.left );
            Set<String> rightKeys = resolveKeys( criteria.right );
            Set<String> intersection = new HashSet<>( leftKeys );
            intersection.retainAll( rightKeys );
            return intersection;
        }

        // Leaf criterion
        Map<String, Set<String>> index = indexFor( criteria.attributeName );
        if ( index == null )
        {
            LOG.warn( "cache [{}] no index for attribute '{}'", cacheName, criteria.attributeName );
            return Collections.emptySet();
        }

        Set<String> result = new HashSet<>();
        if ( criteria.op == CacheCriteria.Op.EQ )
        {
            String val = criteria.value == null ? null : criteria.value.toString();
            Set<String> keys = index.get( val );
            if ( keys != null )
            {
                result.addAll( keys );
            }
        }
        else if ( criteria.op == CacheCriteria.Op.IN )
        {
            Set<String> values = ( Set<String> ) criteria.value;
            for ( String v : values )
            {
                Set<String> keys = index.get( v );
                if ( keys != null )
                {
                    result.addAll( keys );
                }
            }
        }
        return result;
    }

    private Map<String, Set<String>> indexFor( String attributeName )
    {
        if ( ATTR_MEMBER.equals( attributeName ) )     return memberIndex;
        if ( ATTR_NAME.equals( attributeName ) )       return nameIndex;
        if ( ATTR_CONTEXT_ID.equals( attributeName ) ) return contextIdIndex;
        return null;
    }

    private void addToIndices( DsdCacheEntry entry, String key )
    {
        addToIndex( memberIndex,    entry.getMember(),    key );
        addToIndex( nameIndex,      entry.getName(),      key );
        addToIndex( contextIdIndex, entry.getContextId(), key );
    }

    private void removeFromIndices( DsdCacheEntry entry, String key )
    {
        removeFromIndex( memberIndex,    entry.getMember(),    key );
        removeFromIndex( nameIndex,      entry.getName(),      key );
        removeFromIndex( contextIdIndex, entry.getContextId(), key );
    }

    private static void addToIndex( Map<String, Set<String>> index, String attrValue, String key )
    {
        if ( attrValue == null )
        {
            return;
        }
        index.computeIfAbsent( attrValue, k -> ConcurrentHashMap.newKeySet() ).add( key );
    }

    private static void removeFromIndex( Map<String, Set<String>> index, String attrValue, String key )
    {
        if ( attrValue == null )
        {
            return;
        }
        Set<String> keys = index.get( attrValue );
        if ( keys != null )
        {
            keys.remove( key );
            if ( keys.isEmpty() )
            {
                index.remove( attrValue, keys );
            }
        }
    }
}

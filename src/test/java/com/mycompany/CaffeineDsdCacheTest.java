/*
 * © 2025 iamfortress.net
 */
package com.mycompany;

import com.mycompany.cache.Cache;
import com.mycompany.cache.CacheAttribute;
import com.mycompany.cache.CacheCriteria;
import com.mycompany.cache.CacheResult;
import com.mycompany.cache.CacheResults;
import com.mycompany.cache.CaffeineCache;
import com.mycompany.cache.CaffeineCacheMgr;

import org.apache.directory.fortress.core.model.SDSet;
import org.apache.directory.fortress.core.util.cache.DsdCacheEntry;

import org.junit.Before;
import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.*;

/**
 * Unit tests for the Caffeine-backed DSD (Dynamic Separation of Duty) cache.
 *
 * <p>The tests mirror the query pattern used by {@code SDUtil.getDsdCache} in
 * fortress-core:
 * <pre>
 *   Attribute&lt;String&gt; member  = dsdCache.getSearchAttribute("member");
 *   Attribute&lt;String&gt; context = dsdCache.getSearchAttribute("contextId");
 *   query.addCriteria(member.in(roles).and(context.eq(contextId)));
 * </pre>
 *
 * Sample cache data (from the problem statement):
 * <pre>
 *   key: oamT7Dsd5:oamT16SDR1:tenant123  member=oamT16SDR1  contextId=tenant123
 *   key: oamT7Dsd5:oamT16SDR2:tenant123  member=oamT16SDR2  contextId=tenant123
 *   ...
 * </pre>
 */
public class CaffeineDsdCacheTest
{
    private static final String DSD_NAME    = "oamT7Dsd5";
    private static final String CONTEXT_ID  = "tenant123";
    private static final String CONTEXT_ID2 = "tenant456";

    private static final String[] MEMBERS = {
        "oamT16SDR1", "oamT16SDR2", "oamT16SDR3", "oamT16SDR4", "oamT16SDR5",
        "oamT16SDR6", "oamT16SDR7", "oamT16SDR8", "oamT16SDR9", "oamT16SDR10"
    };

    private CaffeineCache dsdCache;

    @Before
    public void setUp()
    {
        dsdCache = CaffeineCacheMgr.getInstance().getCache( "fortress.dsd" );
        try { dsdCache.flush(); } catch ( Exception ignore ) { }
        populateCache();
    }

    // -------------------------------------------------------------------------
    // Tests
    // -------------------------------------------------------------------------

    @Test
    public void testGetByKey()
    {
        String key = buildKey( DSD_NAME, MEMBERS[0], CONTEXT_ID );
        DsdCacheEntry entry = ( DsdCacheEntry ) dsdCache.get( key );
        assertNotNull( "Expected entry for key " + key, entry );
        assertEquals( MEMBERS[0], entry.getMember() );
        assertEquals( CONTEXT_ID, entry.getContextId() );
    }

    @Test
    public void testSearchByMemberIn()
    {
        Set<String> searchRoles = new HashSet<>();
        searchRoles.add( MEMBERS[0] ); // oamT16SDR1
        searchRoles.add( MEMBERS[3] ); // oamT16SDR4

        CacheAttribute<String> memberAttr = dsdCache.getSearchAttribute( "member" );
        CacheResults results = dsdCache.createQuery()
            .includeKeys()
            .includeValues()
            .addCriteria( memberAttr.in( searchRoles ) )
            .execute();

        assertEquals( "Expected 2 results matching member IN {SDR1, SDR4}", 2, results.size() );
        Set<String> foundMembers = new HashSet<>();
        for ( CacheResult r : results.all() )
        {
            DsdCacheEntry entry = ( DsdCacheEntry ) r.getValue();
            foundMembers.add( entry.getMember() );
        }
        assertTrue( foundMembers.contains( MEMBERS[0] ) );
        assertTrue( foundMembers.contains( MEMBERS[3] ) );
    }

    @Test
    public void testSearchByMemberInAndContextId()
    {
        // Populate the same DSD in a second tenant
        populateCacheForTenant( CONTEXT_ID2 );

        Set<String> searchRoles = new HashSet<>();
        searchRoles.add( MEMBERS[0] );
        searchRoles.add( MEMBERS[1] );

        CacheAttribute<String> memberAttr  = dsdCache.getSearchAttribute( "member" );
        CacheAttribute<String> contextAttr = dsdCache.getSearchAttribute( "contextId" );
        CacheCriteria criteria = memberAttr.in( searchRoles ).and( contextAttr.eq( CONTEXT_ID ) );

        CacheResults results = dsdCache.createQuery()
            .includeKeys()
            .includeValues()
            .addCriteria( criteria )
            .execute();

        assertEquals( "Expected exactly 2 results for tenant123", 2, results.size() );
        for ( CacheResult r : results.all() )
        {
            DsdCacheEntry entry = ( DsdCacheEntry ) r.getValue();
            assertEquals( "All results must belong to tenant123", CONTEXT_ID, entry.getContextId() );
        }
    }

    @Test
    public void testSearchNoMatchForWrongContext()
    {
        Set<String> searchRoles = new HashSet<>();
        searchRoles.add( MEMBERS[0] );

        CacheAttribute<String> memberAttr  = dsdCache.getSearchAttribute( "member" );
        CacheAttribute<String> contextAttr = dsdCache.getSearchAttribute( "contextId" );

        CacheResults results = dsdCache.createQuery()
            .includeKeys()
            .includeValues()
            .addCriteria( memberAttr.in( searchRoles ).and( contextAttr.eq( "unknownTenant" ) ) )
            .execute();

        assertEquals( "No results expected for unknown tenant", 0, results.size() );
    }

    @Test
    public void testSearchByDsdName()
    {
        CacheAttribute<String> nameAttr = dsdCache.getSearchAttribute( "name" );
        CacheResults results = dsdCache.createQuery()
            .includeKeys()
            .includeValues()
            .addCriteria( nameAttr.eq( DSD_NAME ) )
            .execute();

        assertEquals( "Expected all 10 members for DSD " + DSD_NAME, MEMBERS.length, results.size() );
    }

    @Test
    public void testClearEntry()
    {
        String key = buildKey( DSD_NAME, MEMBERS[0], CONTEXT_ID );
        assertTrue( dsdCache.clear( key ) );
        assertNull( "Entry should be gone after clear", dsdCache.get( key ) );

        // Searching for the cleared member should now return 0 results
        Set<String> searchRoles = new HashSet<>();
        searchRoles.add( MEMBERS[0] );
        CacheAttribute<String> memberAttr = dsdCache.getSearchAttribute( "member" );
        CacheResults results = dsdCache.createQuery()
            .includeKeys()
            .includeValues()
            .addCriteria( memberAttr.in( searchRoles ) )
            .execute();
        assertEquals( "Index must be cleaned up after clear", 0, results.size() );
    }

    @Test
    public void testFlush()
    {
        dsdCache.flush();
        String key = buildKey( DSD_NAME, MEMBERS[0], CONTEXT_ID );
        assertNull( "All entries should be gone after flush", dsdCache.get( key ) );

        // Index must also be empty
        CacheAttribute<String> memberAttr = dsdCache.getSearchAttribute( "member" );
        Set<String> allMembers = new HashSet<>();
        for ( String m : MEMBERS ) allMembers.add( m );
        CacheResults results = dsdCache.createQuery()
            .includeKeys()
            .includeValues()
            .addCriteria( memberAttr.in( allMembers ) )
            .execute();
        assertEquals( "Index must be cleared after flush", 0, results.size() );
    }

    @Test
    public void testCaffeineCacheMgrReturnsSameInstance()
    {
        Cache c1 = CaffeineCacheMgr.getInstance().getCache( "fortress.dsd" );
        Cache c2 = CaffeineCacheMgr.getInstance().getCache( "fortress.dsd" );
        assertSame( "CaffeineCacheMgr must return the same cache instance", c1, c2 );
    }

    // -------------------------------------------------------------------------
    // Helpers
    // -------------------------------------------------------------------------

    /**
     * Seed the DSD cache with one entry per member for the default tenant.
     * Key format mirrors SDUtil: {@code dsdName:member:contextId}
     */
    private void populateCache()
    {
        populateCacheForTenant( CONTEXT_ID );
    }

    private void populateCacheForTenant( String contextId )
    {
        SDSet sdSet = buildSdSet( contextId );
        for ( String member : MEMBERS )
        {
            String key = buildKey( DSD_NAME, member, contextId );
            DsdCacheEntry entry = new DsdCacheEntry( member, sdSet, false );
            entry.setName( DSD_NAME );
            dsdCache.put( key, entry );
        }
    }

    private SDSet buildSdSet( String contextId )
    {
        SDSet sdSet = new SDSet();
        sdSet.setType( SDSet.SDType.DYNAMIC );
        sdSet.setName( DSD_NAME );
        sdSet.setContextId( contextId );
        sdSet.setCardinality( 2 );
        for ( String m : MEMBERS ) sdSet.addMember( m );
        return sdSet;
    }

    /** {@code dsdName:member:contextId} – same format as SDUtil.getKey(buildKey(name,member),contextId) */
    private static String buildKey( String dsdName, String member, String contextId )
    {
        return dsdName + ":" + member + ":" + contextId;
    }
}

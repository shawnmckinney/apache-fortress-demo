/*
 * © 2025 iamfortress.net
 */
package com.mycompany.cache;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * Singleton factory that creates and manages named {@link CaffeineCache} instances.
 *
 * <p>Default settings per cache:
 * <table border="1">
 *   <tr><th>Cache name</th><th>Max size</th><th>TTL (s)</th></tr>
 *   <tr><td>fortress.dsd</td><td>1000</td><td>3600</td></tr>
 *   <tr><td>fortress.ssd</td><td>1000</td><td>600</td></tr>
 *   <tr><td>fortress.policies</td><td>10</td><td>600</td></tr>
 *   <tr><td>fortress.roles</td><td>10</td><td>600</td></tr>
 *   <tr><td>fortress.admin.roles</td><td>10</td><td>600</td></tr>
 *   <tr><td>(default)</td><td>100</td><td>120</td></tr>
 * </table>
 */
public final class CaffeineCacheMgr
{
    private static final Logger LOG = LoggerFactory.getLogger( CaffeineCacheMgr.class );

    private static volatile CaffeineCacheMgr sINSTANCE = null;

    private final ConcurrentMap<String, CaffeineCache> caches = new ConcurrentHashMap<>();

    private CaffeineCacheMgr() { }

    /**
     * Return the singleton instance.
     *
     * @return the global {@link CaffeineCacheMgr}
     */
    public static CaffeineCacheMgr getInstance()
    {
        if ( sINSTANCE == null )
        {
            synchronized ( CaffeineCacheMgr.class )
            {
                if ( sINSTANCE == null )
                {
                    sINSTANCE = new CaffeineCacheMgr();
                    LOG.info( "CaffeineCacheMgr initialised" );
                }
            }
        }
        return sINSTANCE;
    }

    /**
     * Return the named cache, creating it (with default settings) if it does not yet exist.
     *
     * @param cacheName logical cache name (e.g. {@code "fortress.dsd"})
     * @return a ready-to-use {@link CaffeineCache}
     */
    public CaffeineCache getCache( String cacheName )
    {
        return caches.computeIfAbsent( cacheName, this::createCache );
    }

    /**
     * Invalidate all entries in all managed caches.
     */
    public void clearAll()
    {
        caches.values().forEach( c ->
        {
            try
            {
                c.flush();
            }
            catch ( CacheException e )
            {
                LOG.warn( "clearAll: failed to flush cache", e );
            }
        } );
    }

    // -------------------------------------------------------------------------
    // Private helpers
    // -------------------------------------------------------------------------

    private CaffeineCache createCache( String name )
    {
        long maxSize;
        long ttlSeconds;
        switch ( name )
        {
            case "fortress.dsd":
                maxSize    = 1000;
                ttlSeconds = 3600;
                break;
            case "fortress.ssd":
                maxSize    = 1000;
                ttlSeconds = 600;
                break;
            case "fortress.policies":
            case "fortress.roles":
            case "fortress.admin.roles":
            case "fortress.pso":
            case "fortress.uso":
            case "fortress.ous":
                maxSize    = 10;
                ttlSeconds = 600;
                break;
            default:
                maxSize    = 100;
                ttlSeconds = 120;
                break;
        }
        LOG.debug( "Creating Caffeine cache '{}' maxSize={} ttl={}s", name, maxSize, ttlSeconds );
        return new CaffeineCache( name, maxSize, ttlSeconds );
    }
}

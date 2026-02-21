/*
 * © 2025 iamfortress.net
 */
package com.mycompany.cache;

/**
 * Cache interface backed by Caffeine.  Mirrors the fortress-core Cache API but without
 * ehcache-specific types, enabling the DSD (Dynamic Separation of Duty) cache to be
 * implemented with Caffeine's high-performance in-memory store.
 *
 * <p>The search methods ({@link #getSearchAttribute} / {@link #createQuery}) support
 * attribute-based queries over cached {@link org.apache.directory.fortress.core.util.cache.DsdCacheEntry}
 * values using secondary indices maintained internally by the implementation.
 */
public interface Cache
{
    /**
     * Return the value stored under {@code key}, or {@code null} when absent.
     *
     * @param key cache key
     * @return cached value, or {@code null}
     * @throws CacheException on internal cache error
     */
    Object get( Object key ) throws CacheException;

    /**
     * Store {@code value} under {@code key}.  If the value is a
     * {@link org.apache.directory.fortress.core.util.cache.DsdCacheEntry} the secondary
     * indices for {@code member}, {@code name}, and {@code contextId} are also updated.
     *
     * @param key   cache key
     * @param value value to store
     * @throws CacheException on internal cache error
     */
    void put( Object key, Object value ) throws CacheException;

    /**
     * Invalidate the entry stored under {@code key}.
     *
     * @param key cache key
     * @return {@code true} if an entry existed and was removed
     * @throws CacheException on internal cache error
     */
    boolean clear( Object key ) throws CacheException;

    /**
     * Invalidate all entries in this cache.
     *
     * @throws CacheException on internal cache error
     */
    void flush() throws CacheException;

    /**
     * Return a {@link CacheAttribute} that can be used to build search criteria on the
     * named attribute.
     *
     * @param <T>           attribute value type
     * @param attributeName one of {@code "member"}, {@code "name"}, or {@code "contextId"}
     * @return search attribute bound to this cache
     * @throws CacheException on internal cache error
     */
    <T> CacheAttribute<T> getSearchAttribute( String attributeName ) throws CacheException;

    /**
     * Create a new query builder for this cache.
     *
     * @return a new {@link CacheQuery}
     */
    CacheQuery createQuery();
}

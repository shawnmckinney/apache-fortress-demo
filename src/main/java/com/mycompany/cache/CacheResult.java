/*
 * © 2025 iamfortress.net
 */
package com.mycompany.cache;

/**
 * Holds a single entry returned by a {@link CacheQuery} execution.
 */
public class CacheResult
{
    private final Object key;
    private final Object value;

    CacheResult( Object key, Object value )
    {
        this.key = key;
        this.value = value;
    }

    /** @return the cache key of this entry */
    public Object getKey()
    {
        return key;
    }

    /** @return the cached value of this entry */
    public Object getValue()
    {
        return value;
    }
}

/*
 * © 2025 iamfortress.net
 */
package com.mycompany.cache;

import java.util.Set;

/**
 * Represents a searchable attribute of a cache entry.
 * Returned by {@link Cache#getSearchAttribute(String)} and used to build
 * {@link CacheCriteria} that can be passed to a {@link CacheQuery}.
 *
 * <p>Usage example (mirrors the ehcache search API):
 * <pre>
 *   CacheAttribute&lt;String&gt; member  = cache.getSearchAttribute("member");
 *   CacheAttribute&lt;String&gt; context = cache.getSearchAttribute("contextId");
 *   cache.createQuery()
 *        .includeKeys()
 *        .includeValues()
 *        .addCriteria(member.in(roles).and(context.eq(contextId)))
 *        .execute();
 * </pre>
 *
 * @param <T> the type of the attribute value
 */
public class CacheAttribute<T>
{
    private final String attributeName;

    CacheAttribute( String attributeName )
    {
        this.attributeName = attributeName;
    }

    /**
     * Create an equality criterion: {@code attribute == value}.
     *
     * @param value the value to compare against
     * @return a {@link CacheCriteria} that matches entries where this attribute equals {@code value}
     */
    public CacheCriteria eq( T value )
    {
        return new CacheCriteria( attributeName, CacheCriteria.Op.EQ, value );
    }

    /**
     * Create a membership criterion: {@code attribute IN values}.
     *
     * @param values the set of acceptable values
     * @return a {@link CacheCriteria} that matches entries where this attribute is contained in {@code values}
     */
    public CacheCriteria in( Set<T> values )
    {
        return new CacheCriteria( attributeName, CacheCriteria.Op.IN, values );
    }
}

/*
 * © 2025 iamfortress.net
 */
package com.mycompany.cache;

/**
 * Builds and executes a search against a {@link CaffeineCache}.
 *
 * <p>Usage example:
 * <pre>
 *   CacheAttribute&lt;String&gt; member  = cache.getSearchAttribute("member");
 *   CacheAttribute&lt;String&gt; context = cache.getSearchAttribute("contextId");
 *   CacheResults results = cache.createQuery()
 *       .includeKeys()
 *       .includeValues()
 *       .addCriteria(member.in(roles).and(context.eq(contextId)))
 *       .execute();
 * </pre>
 */
public class CacheQuery
{
    private final CaffeineCache cache;
    private CacheCriteria criteria;

    CacheQuery( CaffeineCache cache )
    {
        this.cache = cache;
    }

    /** Instruct the query to include keys in results (fluent). */
    public CacheQuery includeKeys()
    {
        return this;
    }

    /** Instruct the query to include values in results (fluent). */
    public CacheQuery includeValues()
    {
        return this;
    }

    /**
     * Set the search criteria.  Multiple calls overwrite the previous criteria; use
     * {@link CacheCriteria#and(CacheCriteria)} to combine multiple constraints.
     *
     * @param criteria the criteria to apply
     * @return this query (fluent)
     */
    public CacheQuery addCriteria( CacheCriteria criteria )
    {
        this.criteria = criteria;
        return this;
    }

    /**
     * Execute the query and return all matching results.
     *
     * @return matching results
     */
    public CacheResults execute()
    {
        return cache.search( criteria );
    }
}

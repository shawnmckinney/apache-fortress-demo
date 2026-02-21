/*
 * © 2025 iamfortress.net
 */
package com.mycompany.cache;

import java.util.Collections;
import java.util.List;

/**
 * The result set returned by executing a {@link CacheQuery}.
 */
public class CacheResults
{
    private final List<CacheResult> results;

    CacheResults( List<CacheResult> results )
    {
        this.results = Collections.unmodifiableList( results );
    }

    /** @return total number of results */
    public int size()
    {
        return results.size();
    }

    /** @return all results as an immutable list */
    public List<CacheResult> all()
    {
        return results;
    }
}

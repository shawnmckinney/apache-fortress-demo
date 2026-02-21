/*
 * © 2025 iamfortress.net
 */
package com.mycompany.cache;

import java.util.Set;

/**
 * Represents a search criterion (or a conjunction of criteria) used with {@link CacheQuery}.
 *
 * <p>Simple criteria are created via {@link CacheAttribute#eq(Object)} or
 * {@link CacheAttribute#in(Set)}.  Compound criteria are built with {@link #and(CacheCriteria)}.
 */
public class CacheCriteria
{
    enum Op
    {
        EQ, IN
    }

    final String attributeName;
    final Op op;
    final Object value;          // single value for EQ; Set for IN
    final CacheCriteria left;    // non-null only for AND nodes
    final CacheCriteria right;   // non-null only for AND nodes

    /** Simple criterion */
    CacheCriteria( String attributeName, Op op, Object value )
    {
        this.attributeName = attributeName;
        this.op = op;
        this.value = value;
        this.left = null;
        this.right = null;
    }

    /** Compound AND criterion */
    private CacheCriteria( CacheCriteria left, CacheCriteria right )
    {
        this.attributeName = null;
        this.op = null;
        this.value = null;
        this.left = left;
        this.right = right;
    }

    /**
     * Combine this criterion with {@code other} using a logical AND.
     *
     * @param other additional criterion
     * @return a compound criterion that is satisfied only when both sides are satisfied
     */
    public CacheCriteria and( CacheCriteria other )
    {
        return new CacheCriteria( this, other );
    }

    boolean isCompound()
    {
        return left != null;
    }
}

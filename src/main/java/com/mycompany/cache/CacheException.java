/*
 * © 2025 iamfortress.net
 */
package com.mycompany.cache;

/**
 * Wraps an internal cache exception.
 */
public class CacheException extends RuntimeException
{
    public CacheException( String message )
    {
        super( message );
    }

    public CacheException( String message, Throwable cause )
    {
        super( message, cause );
    }
}

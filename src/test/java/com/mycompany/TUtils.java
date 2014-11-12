/*
 * Copyright (c) 2009-2013, JoshuaTree. All Rights Reserved.
 */

package com.mycompany;


import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;

import junit.framework.TestCase;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.apache.directory.fortress.core.GlobalIds;


/**
 * Description of the Class
 *
 * @author Shawn McKinney
 */
public class TUtils extends TestCase
{
    private static final String CLS_NM = TUtils.class.getName();
    private static final Logger LOG = LoggerFactory.getLogger( CLS_NM );

    /**
     * Fortress stores complex attribute types within a single attribute in ldap.  Usually a delimiter of ',' is used for string tokenization.
     * format: {@code name:value}
     */
    public static final String DELIMITER_TEST_DATA = ",";

    private static String contextId = GlobalIds.HOME;


    public static String getContext()
    {
        // This property can be overriden with system property:
        String tenant = System.getProperty( GlobalIds.TENANT );
        if ( tenant != null && tenant.length() > 0 && !tenant.equals( "${tenant}" ) )
        {
            contextId = tenant;
        }
        return contextId;
    }


    public static byte[] readJpegFile( String fileName )
    {
        URL fUrl = TUtils.class.getClassLoader().getResource( fileName );
        byte[] image = null;
        try
        {
            if ( fUrl != null )
            {
                image = FileUtils.readFileToByteArray( new File( fUrl.toURI() ) );
            }
        }
        catch ( URISyntaxException se )
        {
            String error = "readJpegFile caught URISyntaxException=" + se;
            LOG.error( error );
        }
        catch ( IOException ioe )
        {
            String error = "readJpegFile caught IOException=" + ioe;
            LOG.error( error );
        }
        return image;
    }


    /**
     *
     * @param len
     */
    public static void sleep( String len )
    {
        try
        {
            Integer iSleep = ( Integer.parseInt( len ) * 1000 );
            //LOG.debug(TUtils.class.getName() + ".sleep for len=" + iSleep);
            LOG.info( TUtils.class.getName() + ".sleep for len=" + iSleep );
            Thread.currentThread().sleep( iSleep );
        }
        catch ( InterruptedException ie )
        {
            LOG.warn( TUtils.class.getName() + ".sleep caught InterruptedException=" + ie.getMessage(), ie );
        }
    }


    /**
     *
     * @param len
     */
    public static void sleep( int len )
    {
        try
        {
            int iSleep = len * 1000;
            LOG.info( TUtils.class.getName() + ".sleep for len=" + iSleep );
            Thread.currentThread().sleep( iSleep );
        }
        catch ( InterruptedException ie )
        {
            LOG.warn( TUtils.class.getName() + ".sleep caught InterruptedException=" + ie.getMessage(), ie );
        }
    }


    /**
     *
     * @param len
     */
    public static void sleep( long len )
    {
        try
        {
            long iSleep = len * 1000;
            org.apache.directory.fortress.core.util.LogUtil.logIt( TUtils.class.getName() + ".sleep for len=" + iSleep );
            Thread.currentThread().sleep( iSleep );
        }
        catch ( InterruptedException ie )
        {
            LOG.warn( TUtils.class.getName() + ".sleep caught InterruptedException=" + ie.getMessage(), ie );
        }
    }
}

/*
 * This is free and unencumbered software released into the public domain.
 */
package com.mycompany;


import org.apache.log4j.Logger;
import org.apache.wicket.markup.html.basic.Label;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;

/**
 * @author Shawn McKinney
 * @version $Rev$
 */
public class LogoutPage extends MyBasePage
{
    private static final Logger LOG = Logger.getLogger( LogoutPage.class.getName() );
    public LogoutPage()
    {
        HttpServletRequest servletReq = (HttpServletRequest)getRequest().getContainerRequest();
        LOG.info( "logout user, route to login page" );
        // invalidate the session and force the user to log back on:
        servletReq.getSession().invalidate();
        getSession().invalidate();
        setResponsePage( LoginPage.class );
        add(new Label("label1", "Select logout"));
    }
}
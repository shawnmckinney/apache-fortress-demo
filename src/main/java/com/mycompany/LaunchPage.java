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
public class LaunchPage extends MyBasePage
{
    private static final Logger LOG = Logger.getLogger( LaunchPage.class.getName() );
    public LaunchPage()
    {
        HttpServletRequest servletReq = (HttpServletRequest)getRequest().getContainerRequest();
        Principal principal = servletReq.getUserPrincipal();
        // needed anytime container security checker allows requests in with old cookie (perhaps after server/app restart)::
        if(principal == null)
        {
            LOG.info( "user not logged in, route to login page instead" );
            // invalidate the session and force the user to log back on:
            servletReq.getSession().invalidate();
            getSession().invalidate();
            setResponsePage( LoginPage.class );
        }
        add(new Label("label1", "You have access to the link(s) above."));
    }
}
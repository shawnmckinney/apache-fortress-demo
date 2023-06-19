/*  © 2023 iamfortress.net   */package com.mycompany;


import org.apache.wicket.markup.html.basic.Label;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jakarta.servlet.http.HttpServletRequest;

/**
 * @author Shawn McKinney
 * @version $Rev$
 */
public class LogoutPage extends MyBasePage
{
    private static final org.slf4j.Logger LOG = LoggerFactory.getLogger( LogoutPage.class.getName() );
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

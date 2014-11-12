/*
 * This is free and unencumbered software released into the public domain.
 */
package com.mycompany;


/**
 * Since the Java EE container performs authentication, this wicket page simply sets the response page to wicket
 * application home page.  If Java EE security session has timed out, the container will direct request to its configured login form: /login/login.html.
 * See web.xml for container security configuration settings.
 *
 * @author Shawn McKinney
 * @version $Rev$
 */
public final class LoginPage extends MyBasePage
{
    public LoginPage()
    {
        setResponsePage( LaunchPage.class );
    }
}

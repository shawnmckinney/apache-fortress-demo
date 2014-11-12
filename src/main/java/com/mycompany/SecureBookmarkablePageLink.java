/*
 * This is free and unencumbered software released into the public domain.
 */
package com.mycompany;

import org.apache.wicket.Page;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.request.mapper.parameter.PageParameters;

import javax.servlet.http.HttpServletRequest;
import java.util.StringTokenizer;

/**
 * ...
 *
 * @author Shawn McKinney
 * @version $Rev$
 */
public class SecureBookmarkablePageLink extends BookmarkablePageLink
{
    public <C extends Page> SecureBookmarkablePageLink( String id, Class<C> pageClass, String roleName )
    {
        super( id, pageClass );
        if(!isAuthorized( roleName ))
        {
            setVisible( false );
        }
    }

    private boolean isAuthorized( String roleName )
    {
        HttpServletRequest servletReq = ( HttpServletRequest ) getRequest().getContainerRequest();
        return isAuthorized( roleName, servletReq );
    }

    private boolean isAuthorized( String roleNames, HttpServletRequest servletReq )
    {
        boolean isAuthorized = false;
        StringTokenizer tokenizer = new StringTokenizer( roleNames, "," );
        if (tokenizer.countTokens() > 0)
        {
            while (tokenizer.hasMoreTokens())
            {
                String roleName = tokenizer.nextToken();
                if ( servletReq.isUserInRole( roleName ) )
                {
                    isAuthorized = true;
                    break;
                }
            }
        }
        return isAuthorized;
    }
}

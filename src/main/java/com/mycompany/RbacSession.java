/*
 * This is free and unencumbered software released into the public domain.
 */
package com.mycompany;


import org.apache.wicket.protocol.http.WebSession;
import org.apache.wicket.request.Request;
import org.apache.directory.fortress.core.rbac.Permission;
import org.apache.directory.fortress.core.rbac.Session;

import java.util.List;


/**
 * ...
 *
 * @author Shawn McKinney
 * @version $Rev$
 */
public class RbacSession extends WebSession
{
    private Session session;
    private List<Permission> permissions;

    /**
     * Constructor. Note that {@link org.apache.wicket.request.cycle.RequestCycle} is not available until this
     * constructor returns.
     *
     * @param request The current request
     */
    public RbacSession( Request request )
    {
        super( request );
    }

    public Session getRbacSession()
    {
        return session;
    }

    public void setSession( Session session )
    {
        this.session = session;
    }

    public List<Permission> getPermissions()
    {
        return permissions;
    }

    public void setPermissions( List<Permission> permissions )
    {
        this.permissions = permissions;
    }
}

/*
 * This is free and unencumbered software released into the public domain.
 */
package com.mycompany;

import com.googlecode.wicket.jquery.ui.form.button.IndicatingAjaxButton;
import org.apache.log4j.Logger;
import org.apache.wicket.Component;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.apache.directory.fortress.core.*;
import org.apache.directory.fortress.core.rbac.Permission;

import javax.servlet.http.HttpServletRequest;

/**
 * ...
 *
 * @author Shawn McKinney
 * @version $Rev$
 */
public class SecureIndicatingAjaxButton extends IndicatingAjaxButton
{
    Permission perm;

    @SpringBean
    private AccessMgr accessMgr;

    private static final Logger LOG = Logger.getLogger( MyBasePage.class.getName() );

    public SecureIndicatingAjaxButton( Component component, String id, String objectName, String opName )
    {
        super( id );
        this.perm = new Permission(objectName, opName);
        if(GlobalUtils.IS_PERM_CACHED)
        {
            if(!GlobalUtils.isFound( perm, this ))
                setVisible( false );
        }
        else
        {
            boolean isAuthorized = false;
            try
            {
                RbacSession session = ( RbacSession )component.getSession();
                isAuthorized = accessMgr.checkAccess( session.getRbacSession(), perm );
                LOG.info( "Fortress checkAccess objectName: " + objectName + " operationName: " + opName + " userId: " + session.getRbacSession().getUserId() + " result: " + isAuthorized);
            }
            catch(org.apache.directory.fortress.core.SecurityException se)
            {
                String error = "Fortress SecurityException checkAccess objectName: " + objectName + " operationName: " + opName + " error=" + se;
                LOG.error( error );
            }
            if(!isAuthorized)
                setVisible( false );
        }
    }

    public SecureIndicatingAjaxButton( String id, String roleName )
    {
        super( id );
        HttpServletRequest servletReq = ( HttpServletRequest ) getRequest().getContainerRequest();
        if( ! GlobalUtils.isAuthorized( roleName, servletReq ) )
            setVisible( false );
     }

    protected boolean checkAccess( String objectName, String opName )
    {
        boolean isAuthorized = false;
        try
        {
            RbacSession session = ( RbacSession )getSession();
            Permission permission = new Permission( objectName, opName );
            //Permission permission = new Permission( objectName, perm.getOpName() );
            isAuthorized = accessMgr.checkAccess( session.getRbacSession(), permission );
            LOG.info( "Fortress checkAccess objectName: " + permission.getObjName() + " operationName: " + permission.getOpName() + " userId: " + session.getRbacSession().getUserId() + " result: " + isAuthorized);
        }
        catch(org.apache.directory.fortress.core.SecurityException se)
        {
            String error = "Fortress SecurityException checkAccess objectName: " + this.perm.getObjName() + " operationName: " + this.perm.getOpName() + " error=" + se;
            LOG.error( error );
        }
        return isAuthorized;
    }

    protected boolean checkAccess( )
    {
        boolean isAuthorized = false;
        try
        {
            RbacSession session = ( RbacSession )getSession();
            isAuthorized = accessMgr.checkAccess( session.getRbacSession(), perm );
            LOG.info( "Fortress checkAccess objName: " + this.perm.getObjName() + " opName: " + this.perm.getOpName() + " userId: " + session.getRbacSession().getUserId() + " result: " + isAuthorized);
        }
        catch(org.apache.directory.fortress.core.SecurityException se)
        {
            String error = "Fortress SecurityException checkAccess objName: " + this.perm.getObjName() + " opName: " + this.perm.getOpName() + " error=" + se;
            LOG.error( error );
        }
        return isAuthorized;
    }


    protected boolean checkAccess( String objectId )
    {
        boolean isAuthorized = false;
        try
        {
            RbacSession session = ( RbacSession )getSession();
            Permission finePerm = new Permission(perm.getObjName(), perm.getOpName(), objectId);
            isAuthorized = accessMgr.checkAccess( session.getRbacSession(), finePerm );
            LOG.info( "Fortress checkAccess objName: " + this.perm.getObjName() + " opName: " + this.perm.getOpName() + ", objId: " + finePerm.getObjId() + ", userId: " + session.getRbacSession().getUserId() + " result: " + isAuthorized);
        }
        catch(org.apache.directory.fortress.core.SecurityException se)
        {
            String error = "Fortress SecurityException checkAccess objectName: " + this.perm.getObjName() + " opName: " + this.perm.getOpName() + ", objId: " + objectId + ", error=" + se;
            LOG.error( error );
        }
        return isAuthorized;
    }
}
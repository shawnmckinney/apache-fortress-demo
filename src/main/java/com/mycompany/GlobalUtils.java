/*
 * This is free and unencumbered software released into the public domain.
 */
package com.mycompany;

import org.apache.log4j.Logger;
import org.apache.wicket.Component;
import org.apache.directory.fortress.core.AccessMgr;
import org.apache.directory.fortress.core.cfg.Config;
import org.apache.directory.fortress.core.rbac.Permission;
import org.apache.directory.fortress.core.rbac.Session;
import org.apache.directory.fortress.core.util.attr.VUtil;

import javax.servlet.http.HttpServletRequest;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.List;
import java.util.StringTokenizer;

/**
 * ...
 *
 * @author Shawn McKinney
 * @version $Rev$
 */
public class GlobalUtils
{
    private static final String PERMS_CACHED = "perms.cached";
    public static final boolean IS_PERM_CACHED = ( ( Config.getProperty( PERMS_CACHED ) != null ) && ( Config
        .getProperty( PERMS_CACHED ).equalsIgnoreCase( "true" ) ) );

    public static final String ADD = "add";
    public static final String UPDATE = "update";
    public static final String DELETE = "delete";
    public static final String SEARCH = "search";
    public static final String SEARCH_A = "searchA";
    public static final String READ = "read";

    public static final String SUPER_USER = "superuser";
    public static final String POWER_USER = "poweruser";

    public static final String USER_1 = "user1";
    public static final String USER_1_123 = "user1_123";
    public static final String USER_1_456 = "user1_456";
    public static final String USER_1_789 = "user1_789";

    public static final String USER_2 = "user2";
    public static final String USER_2_123 = "user2_123";
    public static final String USER_2_456 = "user2_456";
    public static final String USER_2_789 = "user2_789";

    public static final String USER_3 = "user3";
    public static final String USER_3_123 = "user3_123";
    public static final String USER_3_456 = "user3_456";
    public static final String USER_3_789 = "user3_789";

    public static final String USER_123 = "user123";
    public static final String USER_456 = "user456";
    public static final String USER_789 = "user789";

    public static final String USER_ID = "userId";
    public static final String PSWD_FIELD = "pswdField";
    public static final String LOGIN = "login";
    public static final String PAGE_1 = "PAGE1";
    public static final String BTN_PAGE_1 = "page1";

    public static final String PAGE1_OBJNAME = "com.mycompany.Page1";
    public static final String PAGE2_OBJNAME = "com.mycompany.Page2";
    public static final String PAGE3_OBJNAME = "com.mycompany.Page3";

    public static final String PAGE_2 = "PAGE2";
    public static final String BTN_PAGE_2 = "page2";

    public static final String PAGE_3 = "PAGE3";
    public static final String BTN_PAGE_3 = "page3";

    public static final String BTN_PAGE_1_ADD = "page1.add";
    public static final String BTN_PAGE_1_UPDATE = "page1.update";
    public static final String BTN_PAGE_1_DELETE = "page1.delete";
    public static final String BTN_PAGE_1_SEARCH = "page1.search";

    public static final String BTN_PAGE_2_ADD = "page2.add";
    public static final String BTN_PAGE_2_UPDATE = "page2.update";
    public static final String BTN_PAGE_2_DELETE = "page2.delete";
    public static final String BTN_PAGE_2_SEARCH = "page2.search";

    public static final String BTN_PAGE_3_ADD = "page3.add";
    public static final String BTN_PAGE_3_UPDATE = "page3.update";
    public static final String BTN_PAGE_3_DELETE = "page3.delete";
    public static final String BTN_PAGE_3_SEARCH = "page3.search";

    public static final String ROLE_PAGE1_123 = "PAGE1_123";
    public static final String ROLE_PAGE1_456 = "PAGE1_456";
    public static final String ROLE_PAGE1_789 = "PAGE1_789";
    public static final String ROLE_PAGE2_123 = "PAGE2_123";
    public static final String ROLE_PAGE2_456 = "PAGE2_456";
    public static final String ROLE_PAGE2_789 = "PAGE2_789";
    public static final String ROLE_PAGE3_123 = "PAGE3_123";
    public static final String ROLE_PAGE3_456 = "PAGE3_456";
    public static final String ROLE_PAGE3_789 = "PAGE3_789";

    public static final String CUSTOMER_EF_ID = "customer";

    public static final String ROLE_SUPER = "ROLE_DEMO2_SUPER_USER";
    public static final String ROLE_PAGE1 = "ROLE_PAGE1";
    public static final String ROLE_PAGE2 = "ROLE_PAGE2";
    public static final String ROLE_PAGE3 = "ROLE_PAGE3";
    public static final String LOGOUT = "logout";
    public static final String INACTIVE_ROLES = "inactiveRoles";
    public static final String ACTIVE_ROLES = "activeRoles";
    public static final String ROLES_DEACTIVATE = "roles.deactivate";
    public static final String ROLES_ACTIVATE = "roles.activate";
    public static final String FOOTER = "This is free and unencumbered software released into the public domain.";

    public static String getLocationReplacement(HttpServletRequest servletRequest)
    {
        return "window.location.replace(\"" + servletRequest.getContextPath() + "\");";
    }

    public static Session getRbacSession( Component component )
    {
        return ( ( RbacSession ) component.getSession() ).getRbacSession();
    }

    public static List<Permission> getRbacPermissions( Component component )
    {
        return ( ( RbacSession ) component.getSession() ).getPermissions();
    }

    public static boolean isAuthorized( String roleNames, HttpServletRequest servletReq )
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

    public static boolean isFound( Permission permission, Component component )
    {
        List<Permission> permissions = GlobalUtils.getRbacPermissions( component );
        return VUtil.isNotNullOrEmpty( permissions ) && permissions.contains( permission );
    }

    public static boolean checkAccess(Component component, AccessMgr accessMgr, String objName, String opName, String objId ) throws org.apache.directory.fortress.core.SecurityException
    {
        RbacSession session = ( RbacSession )component.getSession();
        Permission permission = new Permission( objName, opName, objId );
        return accessMgr.checkAccess( session.getRbacSession(), permission );
    }
}

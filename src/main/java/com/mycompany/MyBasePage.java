/*
 * This is free and unencumbered software released into the public domain.
 */
package com.mycompany;

import com.googlecode.wicket.kendo.ui.form.combobox.ComboBox;
import com.googlecode.wicket.kendo.ui.renderer.ChoiceRenderer;
import org.apache.directory.fortress.core.*;
import org.apache.directory.fortress.core.SecurityException;
import org.apache.directory.fortress.realm.J2eePolicyMgr;
import org.apache.directory.fortress.web.SecUtils;
import org.apache.directory.fortress.web.SecureIndicatingAjaxButton;
import org.apache.directory.fortress.web.WicketSession;
import org.apache.log4j.Logger;
import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.attributes.AjaxCallListener;
import org.apache.wicket.ajax.attributes.AjaxRequestAttributes;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextArea;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.settings.IExceptionSettings;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.apache.directory.fortress.core.rbac.Session;
import org.apache.directory.fortress.core.rbac.UserRole;
import org.apache.directory.fortress.core.rbac.Warning;
import org.apache.directory.fortress.core.util.attr.VUtil;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.List;

/**
 * Base class for apache-fortress-demo Wicket sample project.  It contains security control functions to demonstrate ANSI
 * RBAC security concepts.
 *
 * @author Shawn McKinney
 * @version $Rev$
 */
public abstract class MyBasePage extends WebPage
{
    @SpringBean
    protected AccessMgr accessMgr;
    @SpringBean
    private ReviewMgr reviewMgr;
    @SpringBean
    private J2eePolicyMgr j2eePolicyMgr;
    private static final Logger LOG = Logger.getLogger( MyBasePage.class.getName() );
    private Form myForm;
    private static final String LINKS_LABEL = "linksLabel";
    private String linksLabel = "Authorized Links";
    protected String infoField;
    protected TextArea infoTA;


    /**
     * These are the child pages of this web application.
     */
    public enum ChildPage
    {
        PAGE1,
        PAGE2,
        PAGE3,
    }

    private ChildPage childPage;

    /**
     * All pages in this Wicket application extend this page.
     *
     */
    public MyBasePage()
    {
        addSecureLinks();
        final Link actionLink = new Link( GlobalIds.LOGOUT )
        {
            @Override
            public void onClick()
            {
                HttpServletRequest servletReq = ( HttpServletRequest ) getRequest().getContainerRequest();
                servletReq.getSession().invalidate();
                getSession().invalidate();
                setResponsePage( LoginPage.class );
            }
        };
        add( actionLink );
        infoTA = new TextArea<>( "infoField", Model.of( infoField ) );
        infoTA.setOutputMarkupId( true );
        add( infoTA );
        add( new Label( "footer", GlobalIds.FOOTER ) );

        HttpServletRequest servletReq = ( HttpServletRequest ) getRequest().getContainerRequest();
        // RBAC Security Processing:
        Principal principal = servletReq.getUserPrincipal();

        // Is this a Java EE secured page && has the User successfully authenticated already?
        boolean isSecured = principal != null;
        if(isSecured)
        {
            linksLabel += " for " + principal.getName();
            if( !isLoggedIn( ) )
            {
                String szPrincipal = principal.toString();
                // Pull the RBAC session from the realm and assert into the Web app's session:
                SecUtils.initializeSession( this, j2eePolicyMgr, accessMgr, szPrincipal );
            }
        }
        myForm = new MyBasePageForm( "commonForm" );
        myForm.setOutputMarkupId( true );
        add( myForm );
    }

    /**
     *
     * @return
     */
    private boolean isLoggedIn( )
    {
        boolean isLoggedIn = false;
        if ( SecUtils.getSession( this ) != null )
        {
            isLoggedIn = true;
        }
        return isLoggedIn;
    }

    /**
     *
     */
    private void addSecureLinks()
    {
        add( new Label( LINKS_LABEL, new PropertyModel<String>( this, LINKS_LABEL ) ) );
        SecureBookmarkablePageLink page1Link = new SecureBookmarkablePageLink( GlobalIds.BTN_PAGE_1, Page1.class,
            GlobalIds.ROLE_SUPER + "," + GlobalIds.ROLE_PAGE1 );
        add( page1Link );
        SecureBookmarkablePageLink page2Link = new SecureBookmarkablePageLink( GlobalIds.BTN_PAGE_2, Page2.class,
            GlobalIds.ROLE_SUPER + "," + GlobalIds.ROLE_PAGE2 );
        add( page2Link );
        SecureBookmarkablePageLink page3Link = new SecureBookmarkablePageLink( GlobalIds.BTN_PAGE_3, Page3.class,
            GlobalIds.ROLE_SUPER + "," + GlobalIds.ROLE_PAGE3 );
        add( page3Link );
    }

    /**
     *
     */
    public class MyBasePageForm extends Form
    {
        private ComboBox<UserRole> rolesCB;
        private String roleSelection;
        private List<UserRole> inactiveRoles;
        private ComboBox<UserRole> activeRolesCB;
        private String activeRoleSelection;
        private List<UserRole> activeRoles;

        public MyBasePageForm( String id )
        {
            super( id );
            loadActivatedRoleSets();
            addRoleActivationComboBoxesAndButtons();
        }

        private void addRoleActivationComboBoxesAndButtons()
        {
            rolesCB = new ComboBox<UserRole>( GlobalIds.INACTIVE_ROLES, new PropertyModel<String>( this, "roleSelection" ), inactiveRoles, new ChoiceRenderer<UserRole>( "name" ) );
            rolesCB.setOutputMarkupId( true );
            add( rolesCB );
            add( new SecureIndicatingAjaxButton( this, GlobalIds.ROLES_ACTIVATE, "com.mycompany.MyBasePage", "addActiveRole" )
            {
                private static final long serialVersionUID = 1L;

                @Override
                protected void onSubmit( AjaxRequestTarget target, Form<?> form )
                {
                    getApplication().getExceptionSettings().setAjaxErrorHandlingStrategy(
                        IExceptionSettings.AjaxErrorStrategy.REDIRECT_TO_ERROR_PAGE);

                    if ( VUtil.isNotNullOrEmpty( roleSelection ) )
                    {
                        if ( checkAccess( roleSelection, "addActiveRole" ) )
                        {
                            if ( addActiveRole( target, roleSelection ) )
                            {
                                setMyResponsePage();
                            }
                            target.add( form );
                        }
                        else
                        {
                            String msg = "Unauthorized addActiveRole: " + roleSelection;
                            target.appendJavaScript( ";alert('" + msg + "');" );
                            roleSelection = "";
                        }
                    }
                }
                @Override
                protected void updateAjaxAttributes( AjaxRequestAttributes attributes )
                {
                    AjaxCallListener ajaxCallListener = new AjaxCallListener()
                    {
                        @Override
                        public CharSequence getFailureHandler( Component component )
                        {
                            String szRelocation = GlobalIds.getLocationReplacement( ( HttpServletRequest ) getRequest
                                ().getContainerRequest() );
                            LOG.info( "MyBasePage.addActiveRole Failure Handler, relocation string = " + szRelocation );
                            return szRelocation;
                        }
                    };
                    attributes.getAjaxCallListeners().add( ajaxCallListener );
                }
            } );

            activeRolesCB = new ComboBox<UserRole>( GlobalIds.ACTIVE_ROLES, new PropertyModel<String>( this, "activeRoleSelection" ), activeRoles, new ChoiceRenderer<UserRole>( "name" ) );
            activeRolesCB.setOutputMarkupId( true );
            add( activeRolesCB );
            add( new SecureIndicatingAjaxButton( this, GlobalIds.ROLES_DEACTIVATE, "com.mycompany.MyBasePage", "dropActiveRole" )
            {
                private static final long serialVersionUID = 1L;

                @Override
                protected void onSubmit( AjaxRequestTarget target, Form<?> form )
                {
                    if ( VUtil.isNotNullOrEmpty( activeRoleSelection ) )
                    {
                        if ( checkAccess( activeRoleSelection, "dropActiveRole" ) )
                        {
                            if ( dropActiveRole( target, activeRoleSelection ) )
                            {
                                setMyResponsePage();
                            }
                            target.add( form );
                        }
                        else
                        {
                            target.appendJavaScript( ";alert('Unauthorized');" );
                            activeRoleSelection = "";
                        }
                    }
                }

                @Override
                protected void updateAjaxAttributes( AjaxRequestAttributes attributes )
                {
                    AjaxCallListener ajaxCallListener = new AjaxCallListener()
                    {
                        @Override
                        public CharSequence getFailureHandler( Component component )
                        {
                            String szRelocation = GlobalIds.getLocationReplacement( ( HttpServletRequest ) getRequest().getContainerRequest() );
                            LOG.info( "MyBasePage.dropActiveRole Failure Handler, relocation string = " + szRelocation );
                            return szRelocation;
                        }
                    };
                    attributes.getAjaxCallListeners().add( ajaxCallListener );
                }
            } );

            Label activatedRoleString = new Label( "activatedRoleString", new PropertyModel<String>( this, "activatedRoleString" ) );
            add( activatedRoleString );
            Label inactivatedRoleString = new Label( "inactivatedRoleString", new PropertyModel<String>( this, "inactivatedRoleString" ) );
            add( inactivatedRoleString );
        }

        /**
         * Build a comma delimited String containing activated roles to be displayed in page label.
         *
         * @return String containing comma delimited activated roles
         */
        public String getActivatedRoleString()
        {
            String szRoleStr = "";
            if(VUtil.isNotNullOrEmpty( activeRoles ))
            {
                int ctr = 0;
                for(UserRole role : activeRoles )
                {
                    if(ctr++ > 0)
                    {
                        szRoleStr += ", ";
                    }
                    szRoleStr += role.getName();
                }
            }
            return szRoleStr;
        }


        /**
         * Build a comma delimited String containing inactivated roles to be displayed in page label.
         *
         * @return String containing comma delimited inactivated roles
         */
        public String getInactivatedRoleString()
        {
            String szRoleStr = "";
            if(VUtil.isNotNullOrEmpty( inactiveRoles ))
            {
                int ctr = 0;
                for(UserRole role : inactiveRoles )
                {
                    if(ctr++ > 0)
                    {
                        szRoleStr += ", ";
                    }
                    szRoleStr += role.getName();
                }
            }
            return szRoleStr;
        }

        /**
         * Called during page form submission.
         */
        private void setMyResponsePage()
        {
            if ( childPage != null )
            {
                switch ( childPage )
                {
                    case PAGE1:
                        setResponsePage( new Page1() );
                        break;
                    case PAGE2:
                        setResponsePage( new Page2() );
                        break;
                    case PAGE3:
                        setResponsePage( new Page3() );
                        break;
                }
            }
            else
            {
                setResponsePage( new LaunchPage() );
            }
        }

        /**
         * This loads the set of user's activated roles into a local page variable.  It is used for deactivate combo
         * box.
         */
        private void loadActivatedRoleSets()
        {
            Session session = SecUtils.getSession( this );
            if ( session != null )
            {
                LOG.info( "get assigned roles for user: " + session.getUserId() );
                try
                {
                    inactiveRoles = reviewMgr.assignedRoles( session.getUser() );
                    // remove inactiveRoles already activated:
                    for ( UserRole activatedRole : session.getRoles() )
                    {
                        inactiveRoles.remove( activatedRole );
                    }
                    LOG.info( "user: " + session.getUserId() + " inactiveRoles for activate list: " + inactiveRoles );
                    activeRoles = session.getRoles();
                    //activeRoles.remove( new UserRole ( "FORTRESS_DEMO2" ) );
                }
                catch ( org.apache.directory.fortress.core.SecurityException se )
                {
                    String error = "SecurityException getting assigned inactiveRoles for user: " + session.getUserId();
                    LOG.error( error );
                }
            }
        }
    }

    /**
     * Called by the child of this class and used during page's submit operations to determine which page to reload.
     *
     * @param childPage
     */
    protected void setChildPage( ChildPage childPage )
    {
        this.childPage = childPage;
    }

    /**
     * Call RBAC addActiveRole to active role into session.
     *
     * @param target
     * @param roleName
     * @return
     */
    protected boolean addActiveRole( AjaxRequestTarget target, String roleName )
    {
        boolean isSuccessful = false;
        try
        {
            WicketSession session = ( WicketSession ) getSession();
            session.getSession().setWarnings( null );
            accessMgr.addActiveRole( session.getSession(), new UserRole( roleName ) );
            List<Warning> warnings = session.getSession().getWarnings();
            if ( VUtil.isNotNullOrEmpty( warnings ) )
            {
                for ( Warning warning : warnings )
                {
                    LOG.info( "Warning: " + warning.getMsg() + " errCode: " + warning.getId() + " name: " + warning
                        .getName() + " type: " + warning.getType().toString() );
                    if ( warning.getType() == Warning.Type.ROLE && warning.getName().equalsIgnoreCase( roleName ) )
                    {
                        String error = warning.getMsg() + " code: " + warning.getId();
                        LOG.error( error );
                        target.appendJavaScript( ";alert('" + error + "');" );
                        return false;
                    }
                }
            }

            SecUtils.getPermissions( this, accessMgr );
            isSuccessful = true;
            String message = "Activate role name: " + roleName + " successful";
            LOG.info( message );
        }
        catch ( org.apache.directory.fortress.core.SecurityException se )
        {
            String msg = "Role selection " + roleName + " activation failed because of ";
            if ( se.getErrorId() == GlobalErrIds.DSD_VALIDATION_FAILED )
            {
                msg += "Dynamic SoD rule violation";
            }
            else if ( se.getErrorId() == GlobalErrIds.URLE_ALREADY_ACTIVE )
            {
                msg += "Role already active in Session";
            }
            else
            {
                msg += "System error: " + se + ", " + "errId=" + se.getErrorId();
            }
            LOG.error( msg );
            target.appendJavaScript( ";alert('" + msg + "');" );
        }
        return isSuccessful;
    }

    /**
     * Call RBAC dropActiveRole to deactivate role from session.
     *
     * @param target
     * @param roleName
     * @return
     */
    protected boolean dropActiveRole( AjaxRequestTarget target, String roleName )
    {
        boolean isSuccessful = false;
        try
        {
            WicketSession session = ( WicketSession ) getSession();
            accessMgr.dropActiveRole( session.getSession(), new UserRole( roleName ) );
            SecUtils.getPermissions( this, accessMgr );
            isSuccessful = true;
            LOG.info( "Fortress dropActiveRole roleName: " + roleName + " was successful" );
        }
        catch ( SecurityException se )
        {
            String msg = "Role selection " + roleName + " deactivation failed because of ";
            if ( se.getErrorId() == GlobalErrIds.URLE_NOT_ACTIVE )
            {
                msg += "Role not active in session";
            }
            else
            {
                msg += "System error: " + se + ", " + "errId=" + se.getErrorId();
            }
            LOG.error( msg );
            target.appendJavaScript( ";alert('" + msg + "');" );
        }
        return isSuccessful;
    }
}
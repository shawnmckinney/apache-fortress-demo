/*
 * This is free and unencumbered software released into the public domain.
 */
package com.mycompany;


import com.inmethod.grid.IGridColumn;
import com.inmethod.grid.SizeUnit;
import com.inmethod.grid.column.PropertyColumn;
import com.inmethod.grid.treegrid.TreeGrid;
import com.mycompany.dao.Page2DaoMgr;
import com.mycompany.dao.Page2EO;
import org.apache.commons.collections.CollectionUtils;
import org.apache.directory.fortress.web.control.SecureIndicatingAjaxButton;
import org.apache.log4j.Logger;
import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.attributes.AjaxCallListener;
import org.apache.wicket.ajax.attributes.AjaxRequestAttributes;
import org.apache.wicket.event.IEvent;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;

import javax.servlet.http.HttpServletRequest;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


/**
 * Example Page class for apache-fortress-demo Wicket sample project.  It contains security control functions to demonstrate ANSI RBAC security concepts.
 *
 * @author Shawn McKinney
 * @version $Rev$
 */
public class Page2 extends MyBasePage
{
    private static final Logger LOG = Logger.getLogger( Page2.class.getName() );
    private DefaultTreeModel treeModel;
    private DefaultMutableTreeNode node;
    private TreeGrid<DefaultTreeModel, DefaultMutableTreeNode, String> grid;
    private DefaultMutableTreeNode rootNode;
    private Form editForm;
    private Page2DaoMgr p2manager = new Page2DaoMgr();

    public Page2()
    {
        Page2ListModel page2ListModel = new Page2ListModel( new Page2EO( ), this );
        setDefaultModel(page2ListModel);
        this.editForm = new Page2Form( "pageForm", new CompoundPropertyModel<Page2EO>( new Page2EO() ) );
        this.editForm.setOutputMarkupId( true );
        add( this.editForm );
        setChildPage( ChildPage.PAGE2 );
    }

    public class Page2Form extends Form
    {
        private TextField customer;

        public Page2Form( String id, final IModel<Page2EO> model )
        {
            super( id, model );
            addDetailFields();
            addGrid();
            add( new Label( "label1", "If you see this page, ROLE_DEMO2_SUPER_USER or ROLE_PAGE2 is activated within your session" ) );
            addButtons();
        }

        /**
         * Add the Page Buttons for CRUD
         */
        private void addButtons()
        {
            //final String szBtn1 = GlobalUtils.BTN_PAGE_2 + "." + GlobalUtils.ADD;
            add( new SecureIndicatingAjaxButton( this, GlobalIds.BTN_PAGE_2_ADD, GlobalIds.PAGE2_OBJNAME, GlobalIds.ADD )
            {
                @Override
                protected void onSubmit( AjaxRequestTarget target, Form form )
                {
                    Page2EO page2EO = ( Page2EO ) editForm.getModel().getObject();
                    info( GlobalIds.BTN_PAGE_2_ADD );
                    if( page2EO != null && checkAccess( page2EO.getCustomer() ) )
                    {
                        p2manager.addPage2( page2EO, this );
                        SaveModelEvent.send( getPage(), this, page2EO, target, SaveModelEvent.Operations.ADD );
                    }
                    else
                    {
                        setAuthZError( "Authorization Failed", GlobalIds.PAGE2_OBJNAME, GlobalIds.ADD, page2EO.getCustomer() );
                    }
                }

                @Override
                public void onError( AjaxRequestTarget target, Form form )
                {
                    LOG.error( "submit failed: " + GlobalIds.BTN_PAGE_2_ADD );
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
                            LOG.info( "Page2.add Failure Handler, relocation string = " + szRelocation );
                            return szRelocation;
                        }
                    };
                    attributes.getAjaxCallListeners().add( ajaxCallListener );
                }
            } );
            add( new SecureIndicatingAjaxButton( this, GlobalIds.BTN_PAGE_2_UPDATE, GlobalIds.PAGE2_OBJNAME, GlobalIds.UPDATE )
            {
                @Override
                protected void onSubmit( AjaxRequestTarget target, Form form )
                {
                    Page2EO page2EO = ( Page2EO ) editForm.getModel().getObject();
                    info( GlobalIds.BTN_PAGE_2_UPDATE );
                    if( page2EO != null && checkAccess( page2EO.getCustomer() ) )
                    {
                        p2manager.updatePage2( page2EO, this );
                        SaveModelEvent.send( getPage(), this, page2EO, target, SaveModelEvent.Operations.UPDATE );
                    }
                    else
                    {
                        setAuthZError( "Authorization Failed", GlobalIds.PAGE2_OBJNAME, GlobalIds.UPDATE, page2EO.getCustomer() );
                    }
                }

                @Override
                public void onError( AjaxRequestTarget target, Form form )
                {
                    LOG.error( "submit failed: " + GlobalIds.BTN_PAGE_2_UPDATE );
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
                            LOG.info( "Page2.update Failure Handler, relocation string = " + szRelocation );
                            return szRelocation;
                        }
                    };
                    attributes.getAjaxCallListeners().add( ajaxCallListener );
                }
            } );
            add( new SecureIndicatingAjaxButton( this, GlobalIds.BTN_PAGE_2_DELETE, GlobalIds.PAGE2_OBJNAME, GlobalIds.DELETE )
            {
                @Override
                protected void onSubmit( AjaxRequestTarget target, Form form )
                {
                    Page2EO page2EO = ( Page2EO ) editForm.getModel().getObject();
                    info( GlobalIds.BTN_PAGE_2_DELETE );
                    if( page2EO != null && checkAccess( page2EO.getCustomer() ) )
                    {
                        p2manager.deletePage2ById( page2EO, this );
                        SaveModelEvent.send( getPage(), this, clearDetailFields( ), target, SaveModelEvent.Operations.DELETE );
                        target.appendJavaScript(";alert('" + GlobalIds.BTN_PAGE_2_DELETE + "');");
                    }
                    else
                    {
                        setAuthZError( "Authorization Failed", GlobalIds.PAGE2_OBJNAME, GlobalIds.DELETE, page2EO.getCustomer() );
                    }
                }

                @Override
                public void onError( AjaxRequestTarget target, Form form )
                {
                    LOG.error( "submit failed: " + GlobalIds.BTN_PAGE_2_DELETE );
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
                            LOG.info( "Page2.delete Failure Handler, relocation string = " + szRelocation );
                            return szRelocation;
                        }
                    };
                    attributes.getAjaxCallListeners().add( ajaxCallListener );
                }
            } );
            add( new SecureIndicatingAjaxButton( this, GlobalIds.BTN_PAGE_2_SEARCH, GlobalIds.PAGE2_OBJNAME, GlobalIds.SEARCH )
            {
                @Override
                protected void onSubmit( AjaxRequestTarget target, Form form )
                {
                    Page2EO page2EO = ( Page2EO ) editForm.getModel().getObject();
                    if( page2EO != null && checkAccess( page2EO.getCustomer() ) )
                    {
                        this.getPage().setDefaultModel( new Page2ListModel<Page2EO>( page2EO, this.getPage() ) );
                        treeModel.reload();
                        rootNode.removeAllChildren();
                        List<Page2EO> page2EOs = ( List<Page2EO> ) this.getPage().getDefaultModelObject();
                        if ( CollectionUtils.isNotEmpty( page2EOs ) )
                        {
                            for ( Page2EO entity : page2EOs )
                                rootNode.add( new DefaultMutableTreeNode( entity ) );
                            info( "Search returned " + page2EOs.size() + " matching objects" );
                        }
                        else
                        {
                            target.appendJavaScript( ";alert('Page2.Search Button No matching objects found');" );
                        }
                    }
                    else
                    {
                        setAuthZError( "Authorization Failed", GlobalIds.PAGE2_OBJNAME, GlobalIds.SEARCH, page2EO.getCustomer() );
                    }
                }

                @Override
                public void onError( AjaxRequestTarget target, Form form )
                {
                    LOG.error( "submit failed: " + GlobalIds.BTN_PAGE_2_SEARCH );
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
                            LOG.info( "Page2.search Failure Handler, relocation string = " + szRelocation );
                            return szRelocation;
                        }
                    };
                    attributes.getAjaxCallListeners().add( ajaxCallListener );
                }
            } );
        }

        private void addDetailFields()
        {
            customer = new TextField( "customer" );
            add( customer );
            customer.setRequired( true );

            TextField attr_d = new TextField( "attr_d" );
            add( attr_d );
            attr_d.setRequired( false );

            TextField attr_e = new TextField( "attr_e" );
            add( attr_e );
            attr_e.setRequired( false );

            TextField attr_f = new TextField( "attr_f" );
            add( attr_f );
            attr_f.setRequired( false );
        }

        private Page2EO clearDetailFields( )
        {
            Page2EO page2EO = new Page2EO();
            setModelObject( page2EO );
            modelChanged();
            return page2EO;
        }

        @Override
        public void onEvent( final IEvent<?> event )
        {
            if ( event.getPayload() instanceof SelectModelEvent )
            {
                SelectModelEvent modelEvent = ( SelectModelEvent ) event.getPayload();
                final Page2EO page2EO = ( Page2EO ) modelEvent.getEntity();
                this.setModelObject(page2EO);
                LOG.info("Received SelectModelEvent, customer: " + page2EO.getCustomer());
            }
            else if ( event.getPayload() instanceof AjaxRequestTarget )
            {
                AjaxRequestTarget target = ( ( AjaxRequestTarget ) event.getPayload() );
                LOG.info( ".onEvent AjaxRequestTarget: " + target.toString() );
                target.add( editForm );
            }
        }
    }

    /**
     *
     */
    private void addGrid()
    {
        List<IGridColumn<DefaultTreeModel, DefaultMutableTreeNode, String>> columns =
            new ArrayList<>();

        PropertyColumn id = new PropertyColumn<DefaultTreeModel, DefaultMutableTreeNode, String, String>(
            Model.of( "ID" ), "userObject.Id");
        id.setInitialSize( 200 );
        columns.add(id);

        PropertyColumn customer = new PropertyColumn<DefaultTreeModel, DefaultMutableTreeNode, String, String>(
            Model.of( "Customer" ), "userObject.Customer");
        customer.setInitialSize( 200 );
        columns.add(customer);

        PropertyColumn attrD = new PropertyColumn<DefaultTreeModel, DefaultMutableTreeNode, String, String>(
            Model.of("Attribute D"), "userObject.Attr_d");
        attrD.setInitialSize( 200 );
        columns.add(attrD);

        PropertyColumn attrE = new PropertyColumn<DefaultTreeModel, DefaultMutableTreeNode, String, String>(
            Model.of("Attribute E"), "userObject.Attr_e");
        attrE.setInitialSize( 200 );
        columns.add(attrE);

        PropertyColumn attrF = new PropertyColumn<DefaultTreeModel, DefaultMutableTreeNode, String, String>(
            Model.of("Attribute F"), "userObject.Attr_f");
        attrF.setInitialSize( 200 );
        columns.add(attrF);

        List<Page2EO> page2EOs = (List<Page2EO>) getDefaultModel().getObject();
        treeModel = createTreeModel(page2EOs);
        grid = new TreeGrid<DefaultTreeModel, DefaultMutableTreeNode, String>("page2treegrid", treeModel, columns)
        {
            @Override
            public void selectItem(IModel itemModel, boolean selected)
            {
                node = (DefaultMutableTreeNode) itemModel.getObject();
                if(!node.isRoot())
                {
                    Page2EO page2EO = (Page2EO) node.getUserObject();
                    LOG.debug( "TreeGrid.addGrid.selectItem selected customer =" + page2EO.getCustomer() );
                    if (super.isItemSelected(itemModel))
                    {
                        LOG.debug("TreeGrid.addGrid.selectItem item is selected");
                        super.selectItem(itemModel, false);
                    }
                    else
                    {
                        super.selectItem(itemModel, true);
                        LOG.info( "Page2 List Entity Customer: " + page2EO.getCustomer() );
                        SelectModelEvent.send(getPage(), this, page2EO);
                    }
                }
            }
        };
        grid.setContentHeight(10, SizeUnit.EM);
        grid.setAllowSelectMultiple(false);
        grid.setClickRowToSelect(true);
        grid.setClickRowToDeselect(false);
        grid.setSelectToEdit(false);
        // expand the root node
        grid.getTreeState().expandAll();
        this.add(grid);
        grid.setOutputMarkupId(true);
    }

    @Override
    public void onEvent(IEvent event)
    {
        // Page level SaveModelEvents triggered by button in the Page Detail Form:
        if (event.getPayload() instanceof SaveModelEvent)
        {
            SaveModelEvent modelEvent = (SaveModelEvent) event.getPayload();
            switch (modelEvent.getOperation())
            {
                case ADD:
                    add((Page2EO)modelEvent.getEntity());
                    break;
                case UPDATE:
                    modelChanged();
                    break;
                case DELETE:
                    prune();
                    break;
                default:
                    LOG.error( "onEvent caught invalid operation" );
                    break;
            }
            AjaxRequestTarget target = ((SaveModelEvent) event.getPayload()).getAjaxRequestTarget();
            LOG.debug(".onEvent AJAX - Page2 - SaveModelEvent: " + target.toString());
        }
        // Page level AJAX events - replace TreeGrid:
        else if ( event.getPayload() instanceof AjaxRequestTarget )
        {
            LOG.info("Page level AjaxRequestTarget Event Occurred");
            AjaxRequestTarget target = ( ( AjaxRequestTarget ) event.getPayload() );
            target.add( grid );
        }
    }

    /**
     *
     * @param page2EOs
     * @return
     */
    private DefaultTreeModel createTreeModel(List<Page2EO> page2EOs)
    {
        DefaultTreeModel model;
        rootNode = new DefaultMutableTreeNode(null);
        model = new DefaultTreeModel(rootNode);
        if (page2EOs == null)
            LOG.debug("no Page2 datasets found");
        else
        {
            LOG.debug( ".createTreeModel Groups found:" + page2EOs.size() );
            for (Page2EO page2EO : page2EOs)
                rootNode.add(new DefaultMutableTreeNode(page2EO));
        }
        return model;
    }

    private void prune()
    {
        removeSelectedItems(grid);
    }

    private void add(Page2EO entity)
    {
        if (getDefaultModelObject() != null)
        {
            treeModel.insertNodeInto(new DefaultMutableTreeNode(entity), rootNode, 0);
        }
    }

    private void removeSelectedItems(TreeGrid<DefaultTreeModel, DefaultMutableTreeNode, String> grid)
    {
        Collection<IModel<DefaultMutableTreeNode>> selected = grid.getSelectedItems();
        for (IModel<DefaultMutableTreeNode> model : selected)
        {
            DefaultMutableTreeNode nodeLocal = model.getObject();
            treeModel.removeNodeFromParent(nodeLocal);
            Page2EO page2EO = (Page2EO) nodeLocal.getUserObject();
            LOG.debug(".removeSelectedItems page2 node: " + page2EO.getCustomer());
        }
    }
}

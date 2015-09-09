/*
 * This is free and unencumbered software released into the public domain.
 */
package com.mycompany;


import com.inmethod.grid.IGridColumn;
import com.inmethod.grid.SizeUnit;
import com.inmethod.grid.column.PropertyColumn;
import com.inmethod.grid.treegrid.TreeGrid;
import com.mycompany.dao.Page1DaoMgr;
import com.mycompany.dao.Page1EO;
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
public class Page1 extends MyBasePage
{
    private static final Logger LOG = Logger.getLogger( Page1.class.getName() );
    private DefaultTreeModel treeModel;
    private DefaultMutableTreeNode node;
    private TreeGrid<DefaultTreeModel, DefaultMutableTreeNode, String> grid;
    private DefaultMutableTreeNode rootNode;
    private Form editForm;
    private Page1DaoMgr p1manager = new Page1DaoMgr();

    public Page1()
    {
        Page1ListModel page1ListModel = new Page1ListModel( new Page1EO( ), this );
        setDefaultModel(page1ListModel);
        this.editForm = new Page1Form( "pageForm", new CompoundPropertyModel<Page1EO>( new Page1EO() ) );
        this.editForm.setOutputMarkupId( true );
        add( this.editForm );
        setChildPage( ChildPage.PAGE1 );
    }

    public class Page1Form extends Form
    {
        private TextField customer;

        public Page1Form( String id, final IModel<Page1EO> model )
        {
            super( id, model );
            addDetailFields();
            addGrid();
            add( new Label( "label1", "If you see this page, ROLE_DEMO2_SUPER_USER or ROLE_PAGE1 is activated within your session" ) );
            addButtons();
        }

        /**
         * Add the Page Buttons for CRUD
         */
        private void addButtons()
        {
            add( new SecureIndicatingAjaxButton( this, GlobalIds.BTN_PAGE_1_ADD, GlobalIds.PAGE1_OBJNAME, GlobalIds.ADD )
            {
                @Override
                protected void onSubmit( AjaxRequestTarget target, Form form )
                {
                    Page1EO page1EO = ( Page1EO ) editForm.getModel().getObject();
                    info( GlobalIds.BTN_PAGE_1_ADD );
                    if( page1EO != null && checkAccess(page1EO.getCustomer()) )
                    {
                        p1manager.addPage1( page1EO, this );
                        SaveModelEvent.send( getPage(), this, page1EO, target, SaveModelEvent.Operations.ADD );
                    }
                    else
                    {
                        info( "Page1.Add Button Unauthorized" );
                        target.appendJavaScript( ";alert('Page1.Add Button Unauthorized');" );
                    }
                }

                @Override
                public void onError( AjaxRequestTarget target, Form form )
                {
                    LOG.error( "submit failed: " + GlobalIds.BTN_PAGE_1_ADD );
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
                            LOG.info( "Page1.add Failure Handler, relocation string = " + szRelocation );
                            return szRelocation;
                        }
                    };
                    attributes.getAjaxCallListeners().add( ajaxCallListener );
                }
            } );
            add( new SecureIndicatingAjaxButton( this, GlobalIds.BTN_PAGE_1_UPDATE, GlobalIds.PAGE1_OBJNAME, GlobalIds.UPDATE )
            {
                @Override
                protected void onSubmit( AjaxRequestTarget target, Form form )
                {
                    Page1EO page1EO = ( Page1EO ) editForm.getModel().getObject();
                    info( GlobalIds.BTN_PAGE_1_UPDATE );
                    if( page1EO != null && checkAccess(page1EO.getCustomer()) )
                    {
                        p1manager.updatePage1( page1EO, this );
                        SaveModelEvent.send( getPage(), this, page1EO, target, SaveModelEvent.Operations.UPDATE );
                    }
                    else
                    {
                        target.appendJavaScript( ";alert('Page1.Update Button Unauthorized');" );
                    }
                }

                @Override
                public void onError( AjaxRequestTarget target, Form form )
                {
                    LOG.error( "submit failed: " + GlobalIds.BTN_PAGE_1_UPDATE );
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
                            LOG.info( "Page1.update Failure Handler, relocation string = " + szRelocation );
                            return szRelocation;
                        }
                    };
                    attributes.getAjaxCallListeners().add( ajaxCallListener );
                }
            } );
            add( new SecureIndicatingAjaxButton( this, GlobalIds.BTN_PAGE_1_DELETE, GlobalIds.PAGE1_OBJNAME, GlobalIds.DELETE )
            {
                @Override
                protected void onSubmit( AjaxRequestTarget target, Form form )
                {
                    Page1EO page1EO = ( Page1EO ) editForm.getModel().getObject();
                    info( GlobalIds.BTN_PAGE_1_DELETE );
                    if( page1EO != null && checkAccess(page1EO.getCustomer()) )
                    {
                        p1manager.deletePage1ById( page1EO, this );
                        SaveModelEvent.send( getPage(), this, clearDetailFields( ), target, SaveModelEvent.Operations.DELETE );
                        target.appendJavaScript(";alert('" + GlobalIds.BTN_PAGE_1_DELETE + "');");
                    }
                    else
                    {
                        target.appendJavaScript( ";alert('Page1.Delete Button Unauthorized');" );
                    }
                }

                @Override
                public void onError( AjaxRequestTarget target, Form form )
                {
                    LOG.error( "submit failed: " + GlobalIds.BTN_PAGE_1_DELETE );
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
                            LOG.info( "Page1.delete Failure Handler, relocation string = " + szRelocation );
                            return szRelocation;
                        }
                    };
                    attributes.getAjaxCallListeners().add( ajaxCallListener );
                }
            } );
            add( new SecureIndicatingAjaxButton( this, GlobalIds.BTN_PAGE_1_SEARCH, GlobalIds.PAGE1_OBJNAME, GlobalIds.SEARCH )
            {
                @Override
                protected void onSubmit( AjaxRequestTarget target, Form form )
                {
                    Page1EO page1EO = ( Page1EO ) editForm.getModel().getObject();
                    if(page1EO != null && checkAccess(page1EO.getCustomer()))
                    {
                        this.getPage().setDefaultModel( new Page1ListModel<Page1EO>( page1EO, this.getPage() ) );
                        treeModel.reload();
                        rootNode.removeAllChildren();
                        List<Page1EO> page1EOs = ( List<Page1EO> ) this.getPage().getDefaultModelObject();
                        if ( CollectionUtils.isNotEmpty( page1EOs ) )
                        {
                            for ( Page1EO entity : page1EOs )
                                rootNode.add( new DefaultMutableTreeNode( entity ) );
                            info( "Search returned " + page1EOs.size() + " matching objects" );
                        }
                        else
                        {
                            info( "No matching objects found" );
                            target.appendJavaScript( ";alert('Page1.Search Button No matching objects found');" );
                        }
                    }
                    else
                    {
                        target.appendJavaScript( ";alert('Page1.Search Button Unauthorized');" );
                    }
                }

                @Override
                public void onError( AjaxRequestTarget target, Form form )
                {
                    LOG.error( "submit failed: " + GlobalIds.BTN_PAGE_1_SEARCH );
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
                            LOG.info( "Page1.search Failure Handler, relocation string = " + szRelocation );
                            return szRelocation;
                        }
                    };
                    attributes.getAjaxCallListeners().add( ajaxCallListener );
                }
            } );
        }

        private void addDetailFields()
        {
            Label id = new Label( "id" );
            add( id );
            customer = new TextField( "customer" );
            add( customer );
            customer.setRequired( true );

            TextField attr_a = new TextField( "attr_a" );
            add( attr_a );
            attr_a.setRequired( false );

            TextField attr_b = new TextField( "attr_b" );
            add( attr_b );
            attr_b.setRequired( false );

            TextField attr_c = new TextField( "attr_c" );
            add( attr_c );
            attr_c.setRequired( false );
        }

        private Page1EO clearDetailFields( )
        {
            Page1EO page1EO = new Page1EO();
            setModelObject( page1EO );
            modelChanged();
            return page1EO;
        }

        @Override
        public void onEvent( final IEvent<?> event )
        {
            if ( event.getPayload() instanceof SelectModelEvent )
            {
                SelectModelEvent modelEvent = ( SelectModelEvent ) event.getPayload();
                final Page1EO page1EO = ( Page1EO ) modelEvent.getEntity();
                this.setModelObject(page1EO);
                LOG.info("Received SelectModelEvent, customer: " + page1EO.getCustomer());
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
        id.setInitialSize( 100 );
        columns.add(id);

        PropertyColumn customer = new PropertyColumn<DefaultTreeModel, DefaultMutableTreeNode, String, String>(
            Model.of( "Customer" ), "userObject.Customer");
        customer.setInitialSize( 200 );
        columns.add(customer);

        PropertyColumn attrA = new PropertyColumn<DefaultTreeModel, DefaultMutableTreeNode, String, String>(
            Model.of("Attribute A"), "userObject.Attr_a");
        attrA.setInitialSize( 200 );
        columns.add(attrA);

        PropertyColumn attrB = new PropertyColumn<DefaultTreeModel, DefaultMutableTreeNode, String, String>(
            Model.of("Attribute B"), "userObject.Attr_b");
        attrB.setInitialSize( 200 );
        columns.add(attrB);

        PropertyColumn attrC = new PropertyColumn<DefaultTreeModel, DefaultMutableTreeNode, String, String>(
            Model.of("Attribute C"), "userObject.Attr_c");
        attrC.setInitialSize( 200 );
        columns.add(attrC);

        List<Page1EO> page1EOs = (List<Page1EO>) getDefaultModel().getObject();
        treeModel = createTreeModel(page1EOs);
        grid = new TreeGrid<DefaultTreeModel, DefaultMutableTreeNode, String>("page1treegrid", treeModel, columns)
        {
            @Override
            public void selectItem(IModel itemModel, boolean selected)
            {
                node = (DefaultMutableTreeNode) itemModel.getObject();
                if(!node.isRoot())
                {
                    Page1EO page1EO = (Page1EO) node.getUserObject();
                    LOG.debug( "TreeGrid.addGrid.selectItem selected customer =" + page1EO.getCustomer() );
                    if (super.isItemSelected(itemModel))
                    {
                        LOG.debug("TreeGrid.addGrid.selectItem item is selected");
                        super.selectItem(itemModel, false);
                    }
                    else
                    {
                        super.selectItem(itemModel, true);
                        LOG.info( "Page1 List Entity Customer: " + page1EO.getCustomer() );
                        SelectModelEvent.send(getPage(), this, page1EO);
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
                    add((Page1EO)modelEvent.getEntity());
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
            LOG.debug(".onEvent AJAX - Page1 - SaveModelEvent: " + target.toString());
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
     * @param page1EOs
     * @return
     */
    private DefaultTreeModel createTreeModel(List<Page1EO> page1EOs)
    {
        DefaultTreeModel model;
        //Page1EO rootObject = new Page1EO(  );
        rootNode = new DefaultMutableTreeNode(null);
        model = new DefaultTreeModel(rootNode);
        if (page1EOs == null)
            LOG.debug("no Page1 datasets found");
        else
        {
            LOG.debug( ".createTreeModel Groups found:" + page1EOs.size() );
            for (Page1EO page1EO : page1EOs)
                rootNode.add(new DefaultMutableTreeNode(page1EO));
        }
        return model;
    }

    private void prune()
    {
        removeSelectedItems(grid);
    }

    private void add(Page1EO entity)
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
            DefaultMutableTreeNode node = model.getObject();
            treeModel.removeNodeFromParent(node);
            Page1EO page1EO = (Page1EO) node.getUserObject();
            LOG.debug(".removeSelectedItems page1 node: " + page1EO.getCustomer());
        }
    }
}

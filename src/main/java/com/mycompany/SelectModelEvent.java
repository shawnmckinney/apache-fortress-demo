/*  © 2023 iamfortress.net   */
package com.mycompany;

import org.apache.wicket.Component;
import org.apache.wicket.MarkupContainer;
import org.apache.wicket.Page;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.event.Broadcast;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.request.ILogData;
import org.apache.wicket.request.IRequestCycle;
import org.apache.wicket.request.component.IRequestablePage;
import org.apache.wicket.request.mapper.parameter.PageParameters;

import java.util.Collection;

/**
 * @author Shawn McKinney
 * @version $Rev$
 */
public class SelectModelEvent extends AjaxUpdateEvent
{
    private int index = 0;
    private Object entity;

    public SelectModelEvent(AjaxRequestTarget target)
    {
        super(target);
    }

    public SelectModelEvent(AjaxRequestTarget target, int index)
    {
        super(target);
        this.index = index;
    }

    public SelectModelEvent(AjaxRequestTarget target, Object entity)
    {
        super(target);
        this.entity = entity;
    }

    public int getIndex()
    {
        return index;
    }

    public void setIndex(int index)
    {
        this.index = index;
    }

    public Object getEntity()
    {
        return entity;
    }

    public void setEntity(Object entity)
    {
        this.entity = entity;
    }

    public static void send(Page page, Component component, Object entity)
    {
        AjaxRequestTarget target = new AjaxRequestTarget()
        {
            @Override
            public void add(Component component, String markupId)
            {
                //To change body of implemented methods use File | Settings | File Templates.
                //component.
            }
            @Override
            public void add(Component... components)
            {
                //To change body of implemented methods use File | Settings | File Templates.
            }
            @Override
            public void addChildren(MarkupContainer parent, Class<?> childCriteria)
            {
                //To change body of implemented methods use File | Settings | File Templates.
            }
            @Override
            public void addListener(IListener listener)
            {
                //To change body of implemented methods use File | Settings | File Templates.
            }
            @Override
            public void appendJavaScript(CharSequence javascript)
            {
                //To change body of implemented methods use File | Settings | File Templates.
            }
            @Override
            public void prependJavaScript(CharSequence javascript)
            {
                //To change body of implemented methods use File | Settings | File Templates.
            }
            @Override
            public void registerRespondListener(ITargetRespondListener listener)
            {
                //To change body of implemented methods use File | Settings | File Templates.
            }
            @Override
            public Collection<? extends Component> getComponents()
            {
                return null;  //To change body of implemented methods use File | Settings | File Templates.
            }
            @Override
            public void focusComponent(Component component)
            {
                //To change body of implemented methods use File | Settings | File Templates.
            }
            @Override
            public IHeaderResponse getHeaderResponse()
            {
                return null;  //To change body of implemented methods use File | Settings | File Templates.
            }
            @Override
            public String getLastFocusedElementId()
            {
                return null;  //To change body of implemented methods use File | Settings | File Templates.
            }
            @Override
            public Page getPage()
            {
                return null;  //To change body of implemented methods use File | Settings | File Templates.
            }
            @Override
            public ILogData getLogData()

            {
                return null;  //To change body of implemented methods use File | Settings | File Templates.
            }
            @Override
            public Integer getPageId()
            {
                return null;  //To change body of implemented methods use File | Settings | File Templates.
            }
            @Override
            public boolean isPageInstanceCreated()
            {
                return false;  //To change body of implemented methods use File | Settings | File Templates.
            }
            @Override
            public Integer getRenderCount()
            {
                return null;  //To change body of implemented methods use File | Settings | File Templates.
            }
            @Override
            public Class<? extends IRequestablePage> getPageClass()
            {
                return null;  //To change body of implemented methods use File | Settings | File Templates.
            }
            @Override
            public PageParameters getPageParameters()
            {
                return null;  //To change body of implemented methods use File | Settings | File Templates.
            }
            @Override
            public void respond(IRequestCycle iRequestCycle)
            {
                //To change body of implemented methods use File | Settings | File Templates.
            }
            @Override
            public void detach(IRequestCycle iRequestCycle)
            {
                //To change body of implemented methods use File | Settings | File Templates.
            }
        };
        component.send(page, Broadcast.BREADTH, new SelectModelEvent(target, entity));
    }

    public static void send(Page page, Component component, Object entity, AjaxRequestTarget target)
    {
        component.send(page, Broadcast.BREADTH, new SaveModelEvent(target, entity));
    }
}

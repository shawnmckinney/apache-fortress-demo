/*
 * This is free and unencumbered software released into the public domain.
 */

package com.mycompany;

import com.mycompany.dao.Page3DaoMgr;
import com.mycompany.dao.Page3EO;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.apache.wicket.Component;
import org.apache.wicket.model.Model;

import java.io.Serializable;
import java.util.List;

/**
 * @author Shawn McKinney
 * @version $Rev$
 * @param <T>
 */
public class Page3ListModel<T extends Serializable> extends Model
{
    private static final Logger log = LoggerFactory.getLogger(Page3ListModel.class.getName());
    private transient Page3EO page3EO;
    private transient List<Page3EO> page3EOs = null;
    private Page3DaoMgr p3manager = new Page3DaoMgr();
    private Component component;

    /**
     * Page3EO contains the search argument customer number.
     *
     * @param page3EO
     */
    public Page3ListModel(Page3EO page3EO, Component component )
    {
        this.page3EO = page3EO;
        this.component = component;
    }

    /**
     * This data is bound for {@link com.mycompany.Page3}
     *
     * @return T extends List<Page3EO> data will be bound to panel data view component.
     */
    @Override
    public T getObject()
    {
        if ( page3EOs != null)
        {
            return (T) page3EOs;
        }
        else
        {
            page3EOs = getList(page3EO);
        }
        return (T) page3EOs;
    }

    @Override
    public void setObject(Object object)
    {
        //log.debug(".setObject count: " + object != null ? ((List<Page3EO>)object).size() : "null");
        this.page3EOs = (List<Page3EO>) object;
    }

    @Override
    public void detach()
    {
        this.page3EOs = null;
        this.page3EO = null;
    }

    /**
     * Return a list of Page3EO by Customer
     * @param page3EO if customer number is populated call the search method on Page3DAO
     * @return
     */
    private List<Page3EO> getList(Page3EO page3EO)
    {
        List<Page3EO> page3EOList = null;
        if( page3EO != null && StringUtils.isNotEmpty( page3EO.getCustomer() ) )
        {
            page3EOList = p3manager.getPages3ByCustomer( page3EO, this.component );
        }
        return page3EOList;
    }
}

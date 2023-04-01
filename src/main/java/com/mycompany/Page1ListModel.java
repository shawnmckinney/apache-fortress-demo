/*  © 2023 iamfortress.net   */
package com.mycompany;

import com.mycompany.dao.Page1DaoMgr;
import com.mycompany.dao.Page1EO;
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
public class Page1ListModel<T extends Serializable> extends Model
{
    private static final Logger log = LoggerFactory.getLogger(Page1ListModel.class.getName());
    private transient Page1EO page1EO;
    private transient List<Page1EO> page1EOs = null;
    private Page1DaoMgr p1manager = new Page1DaoMgr();
    private Component component;

    /**
     * Page1EO contains the search argument customer number.
     *
     * @param page1EO
     */
    public Page1ListModel(Page1EO page1EO, Component component )
    {
        this.page1EO = page1EO;
        this.component = component;
    }

    /**
     * This data is bound for {@link com.mycompany.Page1}
     *
     * @return T extends List<Page1EO> data will be bound to panel data view component.
     */
    @Override
    public T getObject()
    {
        if ( page1EOs != null)
        {
            return (T) page1EOs;
        }
        else
        {
            page1EOs = getList(page1EO);
        }
        return (T) page1EOs;
    }

    @Override
    public void setObject(Object object)
    {
        //log.debug(".setObject count: " + object != null ? ((List<Page1EO>)object).size() : "null");
        this.page1EOs = (List<Page1EO>) object;
    }

    @Override
    public void detach()
    {
        this.page1EOs = null;
        this.page1EO = null;
    }

    /**
     * Return a list of Page1EO by Customer
     * @param page1EO if customer number is populated call the search method on Page1DAO
     * @return
     */
    private List<Page1EO> getList(Page1EO page1EO)
    {
        List<Page1EO> page1EOList = null;
        if( page1EO != null && StringUtils.isNotEmpty( page1EO.getCustomer() ) )
        {
            page1EOList = p1manager.getPages1ByCustomer( page1EO, this.component );
        }
        return page1EOList;
    }
}

/*  © 2025 iamfortress.net   */
package com.mycompany;

import com.mycompany.dao.Page2DaoMgr;
import com.mycompany.dao.Page2EO;
import org.apache.commons.lang3.StringUtils;
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
public class Page2ListModel<T extends Serializable> extends Model
{
    private static final Logger log = LoggerFactory.getLogger(Page2ListModel.class.getName());

    private transient Page2EO page2EO;
    private transient List<Page2EO> page2EOs = null;
    private Page2DaoMgr p2manager = new Page2DaoMgr();
    private Component component;

    /**
     * Page2EO contains the search argument customer number.
     *
     * @param page2EO
     */
    public Page2ListModel(Page2EO page2EO, Component component )
    {
        this.page2EO = page2EO;
        this.component = component;
    }

    /**
     * This data is bound for {@link com.mycompany.Page2}
     *
     * @return T extends List<Page2EO> data will be bound to panel data view component.
     */
    @Override
    public T getObject()
    {
        if ( page2EOs != null)
        {
            return (T) page2EOs;
        }
        else
        {
            page2EOs = getList(page2EO);
        }
        return (T) page2EOs;
    }

    @Override
    public void setObject(Object object)
    {
        //log.debug(".setObject count: " + object != null ? ((List<Page2EO>)object).size() : "null");
        this.page2EOs = (List<Page2EO>) object;
    }

    @Override
    public void detach()
    {
        this.page2EOs = null;
        this.page2EO = null;
    }

    /**
     * Return a list of Page2EO by Customer
     * @param page2EO if customer number is populated call the search method on Page2DAO
     * @return
     */
    private List<Page2EO> getList(Page2EO page2EO)
    {
        List<Page2EO> page2EOList = null;
        if( page2EO != null && StringUtils.isNotEmpty( page2EO.getCustomer() ) )
        {
            page2EOList = p2manager.getPages2ByCustomer( page2EO, this.component );
        }
        return page2EOList;
    }
}

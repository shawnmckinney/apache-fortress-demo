/*
 * This is free and unencumbered software released into the public domain.
 */

package com.mycompany.dao;

import com.mycompany.GlobalUtils;
import org.apache.ibatis.session.SqlSession;
import org.apache.wicket.Component;
import org.apache.wicket.injection.Injector;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.apache.directory.fortress.core.AccessMgr;

import java.io.Serializable;
import java.util.List;

/**
 * Example Page DAO Impl for apache-fortress-demo Wicket sample project.  It is used to demonstrate data level security capabilities.
 *
 * @author Shawn McKinney
 * @version $Rev$
 */
public class Page2DaoMgr implements Serializable
{
    @SpringBean
    private AccessMgr accessMgr;
    public Page2DaoMgr()
    {
        Injector.get().inject(this);
    }

    /**
     * Add a new Page2 entity to the database.
     *
     * @param page2EO Contains Page2 data.
     * @return Page2 entity containing current data in table.
     */
    public Page2EO addPage2( Page2EO page2EO, Component component )
    {
        SqlSession sqlSession = ConnectionFactory.getSqlSessionFactory().openSession();
        try
        {
            if(GlobalUtils.checkAccess( component, accessMgr, GlobalUtils.PAGE2_OBJNAME, GlobalUtils.ADD, page2EO.getCustomer() ))
            {
                Page2Dao page2DaoMapper = sqlSession.getMapper( Page2Dao.class );
                Integer id = page2DaoMapper.getMaxId();
                id = id == null ? 1 : id + 1;
                page2EO.setId( id );
                int rc = page2DaoMapper.addPage2( page2EO );
                sqlSession.commit();
                page2EO = page2DaoMapper.getPage2ById( id );
            }
            else throw new RuntimeException( "User not Authorized: " + GlobalUtils.PAGE2_OBJNAME + "." + GlobalUtils.ADD + "." + page2EO.getCustomer() );
        }
        catch ( Exception e )
        {
            String error = "addPage2 caught Exception=" + e;
            throw new RuntimeException( error );
        }
        finally
        {
            sqlSession.close();
        }
        return page2EO;
    }

    /**
     * Update an existing Page2 entity to the database.
     *
     * @param page2EO Contains Page2 data.
     * @return Page2 entity containing current data in table.
     */
    public Page2EO updatePage2( Page2EO page2EO, Component component )
    {
        SqlSession sqlSession = ConnectionFactory.getSqlSessionFactory().openSession();
        try
        {
            if(GlobalUtils.checkAccess( component, accessMgr, GlobalUtils.PAGE2_OBJNAME, GlobalUtils.UPDATE, page2EO.getCustomer() ))
            {
                Page2Dao page2DaoMapper = sqlSession.getMapper( Page2Dao.class );
                int rc = page2DaoMapper.updatePage2( page2EO );
                sqlSession.commit();
                page2EO = page2DaoMapper.getPage2ById( page2EO.getId() );
            }
            else throw new RuntimeException( "User not Authorized:" + GlobalUtils.PAGE2_OBJNAME + "." + GlobalUtils.UPDATE + "." + page2EO.getCustomer() );
        }
        catch ( Exception e )
        {
            String error = "updatePage2 caught Exception=" + e;
            throw new RuntimeException( error );
        }
        finally
        {
            sqlSession.close();
        }
        return page2EO;
    }

    /**
     * Return all Page2 entities that match attr D parameter.
     *
     * @param component contains reference to calling wicket component.
     * @param page2EO contains reference to page entity.
     * @return List of type Page2 entity.
     */
    public List<Page2EO> getPages2ByAttrD( Page2EO page2EO, Component component )
    {
        List<Page2EO> pages = null;
        SqlSession sqlSession = ConnectionFactory.getSqlSessionFactory().openSession();
        try
        {
            if(GlobalUtils.checkAccess( component, accessMgr, GlobalUtils.PAGE2_OBJNAME, GlobalUtils.SEARCH_A, page2EO.getCustomer() ))
            {
                Page2Dao page2DaoMapper = sqlSession.getMapper( Page2Dao.class );
                pages = page2DaoMapper.getPages2ByAttrD( page2EO.getAttr_d() + "%" );
            }
            else throw new RuntimeException( "User not Authorized: " + GlobalUtils.PAGE2_OBJNAME + "." + GlobalUtils.SEARCH_A + "." + page2EO.getCustomer());
        }
        catch ( Exception e )
        {
            String error = "getPages2ByAttrD attribute value [" + page2EO.getAttr_d() + "], caught Exception=" + e;
            throw new RuntimeException( error );
        }
        finally
        {
            sqlSession.close();
        }
        return pages;
    }

    /**
     * Return all Page2 entities that match customer parameter.
     *
     * @param component contains reference to calling wicket component.
     * @param page2EO contains reference to page entity.
     * @return List of type Page2 entity.
     */
    public List<Page2EO> getPages2ByCustomer( Page2EO page2EO, Component component )
    {
        List<Page2EO> pages = null;
        SqlSession sqlSession = ConnectionFactory.getSqlSessionFactory().openSession();
        try
        {
            if(GlobalUtils.checkAccess( component, accessMgr, GlobalUtils.PAGE2_OBJNAME, GlobalUtils.SEARCH, page2EO.getCustomer() ))
            {
                Page2Dao page2DaoMapper = sqlSession.getMapper( Page2Dao.class );
                pages = page2DaoMapper.getPages2ByCustomer( page2EO.getCustomer() + "%" );
            }
            else throw new RuntimeException( "User not Authorized: "  + GlobalUtils.PAGE2_OBJNAME + "." + GlobalUtils.SEARCH + "." + page2EO.getCustomer());
        }
        catch ( Exception e )
        {
            String error = "getPages2ByCustomer customer number [" + page2EO.getCustomer() + "], caught Exception=" + e;
            throw new RuntimeException( error );
        }
        finally
        {
            sqlSession.close();
        }
        return pages;
    }

    /**
     * Return Page2 entity that matches id.
     *
     * @param component contains reference to calling wicket component.
     * @param page2EO contains reference to page entity.

     * @return Page2 entity containing current match.
     */
    public Page2EO getPage2ById( Page2EO page2EO, Component component )
    {
        Page2EO page2 = null;
        SqlSession sqlSession = ConnectionFactory.getSqlSessionFactory().openSession();
        try
        {
            if(GlobalUtils.checkAccess( component, accessMgr, GlobalUtils.PAGE2_OBJNAME, GlobalUtils.READ, page2EO.getCustomer() ))
            {
                Page2Dao page2DaoMapper = sqlSession.getMapper( Page2Dao.class );
                page2 = page2DaoMapper.getPage2ById( page2EO.getId() );
            }
            else throw new RuntimeException( "User not Authorized: " + GlobalUtils.PAGE2_OBJNAME + "." + GlobalUtils.READ + "." + page2EO.getCustomer());
        }
        catch ( Exception e )
        {
            String error = "getPage2ById id value [" + page2EO.getId() + "], caught Exception=" + e;
            throw new RuntimeException( error );
        }
        finally
        {
            sqlSession.close();
        }
        return page2;
    }

    /**
     * Remove Page2 entity that matches id parameter.
     *
     * @param page2EO must be populated with {@link Page2EO#getCustomer()} and {@link Page2EO#getId()}.
     */
    public void deletePage2ById( Page2EO page2EO, Component component )
    {
        SqlSession sqlSession = ConnectionFactory.getSqlSessionFactory().openSession();
        try
        {
            if(GlobalUtils.checkAccess( component, accessMgr, GlobalUtils.PAGE2_OBJNAME, GlobalUtils.DELETE, page2EO.getCustomer() ))
            {
                Page2Dao page2DaoMapper = sqlSession.getMapper( Page2Dao.class );
                page2DaoMapper.deletePage2ById( page2EO.getId() );
                sqlSession.commit();
            }
            else throw new RuntimeException( "User not Authorized: " + GlobalUtils.PAGE2_OBJNAME + "." + GlobalUtils.DELETE + "." + page2EO.getCustomer());
        }
        catch ( Exception e )
        {
            String error = "deletePage2ById id value [" + page2EO.getId() + "], caught Exception=" + e;
            throw new RuntimeException( error );
        }
        finally
        {
            sqlSession.close();
        }
    }
}

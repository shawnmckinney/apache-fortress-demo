/*
 * This is free and unencumbered software released into the public domain.
 */

package com.mycompany.dao;

import com.mycompany.GlobalIds;
import org.apache.directory.fortress.web.control.SecUtils;
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
public class Page1DaoMgr implements Serializable
{
    @SpringBean
    private AccessMgr accessMgr;
    public Page1DaoMgr()
    {
        Injector.get().inject(this);
    }

    /**
     * Add a new Page1 entity to the database.
     *
     * @param page1EO Contains Page1 data.
     * @return Page1 entity containing current data in table.
     */
    public Page1EO addPage1( Page1EO page1EO, Component component )
    {
        SqlSession sqlSession = ConnectionFactory.getSqlSessionFactory().openSession();
        try
        {
            if( SecUtils.checkAccess( component, accessMgr, GlobalIds.PAGE1_OBJNAME, GlobalIds.ADD, page1EO
                .getCustomer() ))
            {
                Page1Dao page1DaoMapper = sqlSession.getMapper( Page1Dao.class );
                Integer id = page1DaoMapper.getMaxId();
                id = id == null ? 1 : id + 1;
                page1EO.setId( id );
                page1DaoMapper.addPage1( page1EO );
                sqlSession.commit();
                page1EO = page1DaoMapper.getPage1ById( id );
            }
            else throw new RuntimeException( "User not Authorized: " + GlobalIds.PAGE1_OBJNAME + "." + GlobalIds.ADD + "." + page1EO.getCustomer() );
        }
        catch ( Exception e )
        {
            String error = "addPage1 caught Exception=" + e;
            throw new RuntimeException( error );
        }
        finally
        {
            sqlSession.close();
        }
        return page1EO;
    }

    /**
     * Update an existing Page1 entity to the database.
     *
     * @param page1EO Contains Page1 data.
     * @return Page1 entity containing current data in table.
     */
    public Page1EO updatePage1( Page1EO page1EO, Component component )
    {
        SqlSession sqlSession = ConnectionFactory.getSqlSessionFactory().openSession();
        try
        {
            if( SecUtils.checkAccess( component, accessMgr, GlobalIds.PAGE1_OBJNAME, GlobalIds.UPDATE, page1EO
                .getCustomer() ))
            {
                Page1Dao page1DaoMapper = sqlSession.getMapper( Page1Dao.class );
                page1DaoMapper.updatePage1( page1EO );
                sqlSession.commit();
                page1EO = page1DaoMapper.getPage1ById( page1EO.getId() );
            }
            else throw new RuntimeException( "User not Authorized:" + GlobalIds.PAGE1_OBJNAME + "." + GlobalIds.UPDATE + "." + page1EO.getCustomer() );
        }
        catch ( Exception e )
        {
            String error = "updatePage1 caught Exception=" + e;
            throw new RuntimeException( error );
        }
        finally
        {
            sqlSession.close();
        }
        return page1EO;
    }

    /**
     * Return all Page1 entities that match attr A parameter.
     *
     * @param component contains reference to calling wicket component.
     * @param page1EO contains reference to page entity.
     * @return List of type Page1 entity.
     */
    public List<Page1EO> getPages1ByAttrA( Page1EO page1EO, Component component )
    {
        List<Page1EO> pages = null;
        SqlSession sqlSession = ConnectionFactory.getSqlSessionFactory().openSession();
        try
        {
            if(SecUtils.checkAccess( component, accessMgr, GlobalIds.PAGE1_OBJNAME, GlobalIds.SEARCH_A, page1EO.getCustomer() ))
            {
                Page1Dao page1DaoMapper = sqlSession.getMapper( Page1Dao.class );
                pages = page1DaoMapper.getPages1ByAttrA( page1EO.getAttr_a() + "%" );
            }
            else throw new RuntimeException( "User not Authorized: " + GlobalIds.PAGE1_OBJNAME + "." + GlobalIds.SEARCH_A + "." + page1EO.getCustomer());
        }
        catch ( Exception e )
        {
            String error = "getPages1ByAttrA attribute value [" + page1EO.getAttr_a() + "], caught Exception=" + e;
            throw new RuntimeException( error );
        }
        finally
        {
            sqlSession.close();
        }
        return pages;
    }

    /**
     * Return all Page1 entities that match customer parameter.
     *
     * @param component contains reference to calling wicket component.
     * @param page1EO contains reference to page entity.
     * @return List of type Page1 entity.
     */
    public List<Page1EO> getPages1ByCustomer( Page1EO page1EO, Component component )
    {
        List<Page1EO> pages = null;
        SqlSession sqlSession = ConnectionFactory.getSqlSessionFactory().openSession();
        try
        {
            if(SecUtils.checkAccess( component, accessMgr, GlobalIds.PAGE1_OBJNAME, GlobalIds.SEARCH, page1EO.getCustomer() ))
            {
                Page1Dao page1DaoMapper = sqlSession.getMapper( Page1Dao.class );
                pages = page1DaoMapper.getPages1ByCustomer( page1EO.getCustomer() + "%" );
            }
            else throw new RuntimeException( "User not Authorized: "  + GlobalIds.PAGE1_OBJNAME + "." + GlobalIds.SEARCH + "." + page1EO.getCustomer());
        }
        catch ( Exception e )
        {
            String error = "getPages1ByCustomer customer number [" + page1EO.getCustomer() + "], caught Exception, errorId=" + e;
            e.printStackTrace();
            throw new RuntimeException( error );
        }
        finally
        {
            sqlSession.close();
        }
        return pages;
    }

    /**
     * Return Page1 entity that matches id.
     *
     * @param component contains reference to calling wicket component.
     * @param page1EO contains reference to page entity.

     * @return Page1 entity containing current match.
     */
    public Page1EO getPage1ById( Page1EO page1EO, Component component )
    {
        Page1EO page1 = null;
        SqlSession sqlSession = ConnectionFactory.getSqlSessionFactory().openSession();
        try
        {
            if(SecUtils.checkAccess( component, accessMgr, GlobalIds.PAGE1_OBJNAME, GlobalIds.READ, page1EO.getCustomer() ))
            {
                Page1Dao page1DaoMapper = sqlSession.getMapper( Page1Dao.class );
                page1 = page1DaoMapper.getPage1ById( page1EO.getId() );
            }
            else throw new RuntimeException( "User not Authorized: " + GlobalIds.PAGE1_OBJNAME + "." + GlobalIds.READ + "." + page1EO.getCustomer());
        }
        catch ( Exception e )
        {
            String error = "getPage1ById id value [" + page1EO.getId() + "], caught Exception=" + e;
            throw new RuntimeException( error );
        }
        finally
        {
            sqlSession.close();
        }
        return page1;
    }

    /**
     * Remove Page1 entity that matches id parameter.
     *
     * @param page1EO must be populated with {@link Page1EO#getCustomer()} and {@link Page1EO#getId()}.
     */
    public void deletePage1ById( Page1EO page1EO, Component component )
    {
        SqlSession sqlSession = ConnectionFactory.getSqlSessionFactory().openSession();
        try
        {
            if(SecUtils.checkAccess( component, accessMgr, GlobalIds.PAGE1_OBJNAME, GlobalIds.DELETE, page1EO.getCustomer() ))
            {
                Page1Dao page1DaoMapper = sqlSession.getMapper( Page1Dao.class );
                page1DaoMapper.deletePage1ById( page1EO.getId() );
                sqlSession.commit();
            }
            else throw new RuntimeException( "User not Authorized: " + GlobalIds.PAGE1_OBJNAME + "." + GlobalIds.DELETE + "." + page1EO.getCustomer());
        }
        catch ( Exception e )
        {
            String error = "deletePage1ById id value [" + page1EO.getId() + "], caught Exception=" + e;
            throw new RuntimeException( error );
        }
        finally
        {
            sqlSession.close();
        }
    }
}
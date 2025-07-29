/*  © 2025 iamfortress.net   */
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
public class Page3DaoMgr implements Serializable
{
    @SpringBean
    private AccessMgr accessMgr;
    public Page3DaoMgr()
    {
        Injector.get().inject(this);
    }

    /**
     * Add a new Page3 entity to the database.
     *
     * @param page3EO Contains Page3 data.
     * @return Page3 entity containing current data in table.
     */
    public Page3EO addPage3( Page3EO page3EO, Component component )
    {
        SqlSession sqlSession = ConnectionFactory.getSqlSessionFactory().openSession();
        try
        {
            if( SecUtils.checkAccess( component, accessMgr, GlobalIds.PAGE3_OBJNAME, GlobalIds.ADD, page3EO
                .getCustomer() ))
            {
                Page3Dao page3DaoMapper = sqlSession.getMapper( Page3Dao.class );
                Integer id = page3DaoMapper.getMaxId();
                id = id == null ? 1 : id + 1;
                page3EO.setId( id );
                int rc = page3DaoMapper.addPage3( page3EO );
                sqlSession.commit();
                page3EO = page3DaoMapper.getPage3ById( id );
            }
            else throw new RuntimeException( "User not Authorized: " + GlobalIds.PAGE3_OBJNAME + "." + GlobalIds.ADD + "." + page3EO.getCustomer() );
        }
        catch ( Exception e )
        {
            String error = "addPage3 caught Exception=" + e;
            throw new RuntimeException( error );
        }
        finally
        {
            sqlSession.close();
        }
        return page3EO;
    }

    /**
     * Update an existing Page3 entity to the database.
     *
     * @param page3EO Contains Page3 data.
     * @return Page3 entity containing current data in table.
     */
    public Page3EO updatePage3( Page3EO page3EO, Component component )
    {
        SqlSession sqlSession = ConnectionFactory.getSqlSessionFactory().openSession();
        try
        {
            if(SecUtils.checkAccess( component, accessMgr, GlobalIds.PAGE3_OBJNAME, GlobalIds.UPDATE, page3EO.getCustomer() ))
            {
                Page3Dao page3DaoMapper = sqlSession.getMapper( Page3Dao.class );
                int rc = page3DaoMapper.updatePage3( page3EO );
                sqlSession.commit();
                page3EO = page3DaoMapper.getPage3ById( page3EO.getId() );
            }
            else throw new RuntimeException( "User not Authorized:" + GlobalIds.PAGE3_OBJNAME + "." + GlobalIds.UPDATE + "." + page3EO.getCustomer() );
        }
        catch ( Exception e )
        {
            String error = "updatePage3 caught Exception=" + e;
            throw new RuntimeException( error );
        }
        finally
        {
            sqlSession.close();
        }
        return page3EO;
    }

    /**
     * Return all Page3 entities that match attr G parameter.
     *
     * @param component contains reference to calling wicket component.
     * @param page3EO contains reference to page entity.
     * @return List of type Page3 entity.
     */
    public List<Page3EO> getPages3ByAttrG( Page3EO page3EO, Component component )
    {
        List<Page3EO> pages = null;
        SqlSession sqlSession = ConnectionFactory.getSqlSessionFactory().openSession();
        try
        {
            if(SecUtils.checkAccess( component, accessMgr, GlobalIds.PAGE3_OBJNAME, GlobalIds.SEARCH_A, page3EO.getCustomer() ))
            {
                Page3Dao page3DaoMapper = sqlSession.getMapper( Page3Dao.class );
                pages = page3DaoMapper.getPages3ByAttrG( page3EO.getAttr_g() + "%" );
            }
            else throw new RuntimeException( "User not Authorized: " + GlobalIds.PAGE3_OBJNAME + "." + GlobalIds.SEARCH_A + "." + page3EO.getCustomer());
        }
        catch ( Exception e )
        {
            String error = "getPages3ByAttrG attribute value [" + page3EO.getAttr_g() + "], caught Exception=" + e;
            throw new RuntimeException( error );
        }
        finally
        {
            sqlSession.close();
        }
        return pages;
    }

    /**
     * Return all Page3 entities that match customer parameter.
     *
     * @param component contains reference to calling wicket component.
     * @param page3EO contains reference to page entity.
     * @return List of type Page3 entity.
     */
    public List<Page3EO> getPages3ByCustomer( Page3EO page3EO, Component component )
    {
        List<Page3EO> pages = null;
        SqlSession sqlSession = ConnectionFactory.getSqlSessionFactory().openSession();
        try
        {
            if(SecUtils.checkAccess( component, accessMgr, GlobalIds.PAGE3_OBJNAME, GlobalIds.SEARCH, page3EO.getCustomer() ))
            {
                Page3Dao page3DaoMapper = sqlSession.getMapper( Page3Dao.class );
                pages = page3DaoMapper.getPages3ByCustomer( page3EO.getCustomer() + "%" );
            }
            else throw new RuntimeException( "User not Authorized: "  + GlobalIds.PAGE3_OBJNAME + "." + GlobalIds.SEARCH + "." + page3EO.getCustomer());
        }
        catch ( Exception e )
        {
            String error = "getPages3ByCustomer customer number [" + page3EO.getCustomer() + "], caught Exception=" + e;
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
     * Return Page3 entity that matches id.
     *
     * @param component contains reference to calling wicket component.
     * @param page3EO contains reference to page entity.

     * @return Page3 entity containing current match.
     */
    public Page3EO getPage3ById( Page3EO page3EO, Component component )
    {
        Page3EO page3 = null;
        SqlSession sqlSession = ConnectionFactory.getSqlSessionFactory().openSession();
        try
        {
            if(SecUtils.checkAccess( component, accessMgr, GlobalIds.PAGE3_OBJNAME, GlobalIds.READ, page3EO.getCustomer() ))
            {
                Page3Dao page3DaoMapper = sqlSession.getMapper( Page3Dao.class );
                page3 = page3DaoMapper.getPage3ById( page3EO.getId() );
            }
            else throw new RuntimeException( "User not Authorized: " + GlobalIds.PAGE3_OBJNAME + "." + GlobalIds.READ + "." + page3EO.getCustomer());
        }
        catch ( Exception e )
        {
            String error = "getPage3ById id value [" + page3EO.getId() + "], caught Exception=" + e;
            throw new RuntimeException( error );
        }
        finally
        {
            sqlSession.close();
        }
        return page3;
    }

    /**
     * Remove Page3 entity that matches id parameter.
     *
     * @param page3EO must be populated with {@link Page3EO#getCustomer()} and {@link Page3EO#getId()}.
     */
    public void deletePage3ById( Page3EO page3EO, Component component )
    {
        SqlSession sqlSession = ConnectionFactory.getSqlSessionFactory().openSession();
        try
        {
            if(SecUtils.checkAccess( component, accessMgr, GlobalIds.PAGE3_OBJNAME, GlobalIds.DELETE, page3EO.getCustomer() ))
            {
                Page3Dao page3DaoMapper = sqlSession.getMapper( Page3Dao.class );
                page3DaoMapper.deletePage3ById( page3EO.getId() );
                sqlSession.commit();
            }
            else throw new RuntimeException( "User not Authorized: " + GlobalIds.PAGE3_OBJNAME + "." + GlobalIds.DELETE + "." + page3EO.getCustomer());
        }
        catch ( Exception e )
        {
            String error = "deletePage3ById id value [" + page3EO.getId() + "], caught Exception=" + e;
            throw new RuntimeException( error );
        }
        finally
        {
            sqlSession.close();
        }
    }
}

/*
 * This is free and unencumbered software released into the public domain.
 */

package com.mycompany.dao;


import java.util.List;

/**
 * Example Page1 DAO interface.
 *
 * @author Shawn McKinney
 * @version $Rev$
 */
public interface Page3Dao
{
    public int addPage3( Page3EO page3 );

    public int updatePage3( Page3EO page3 );

    public Page3EO getPage3ById( Integer id );

    public void deletePage3ById( Integer id );

    public List<Page3EO> getPages3ByAttrG( String attr_a );

    public List<Page3EO> getPages3ByCustomer( String customer );

    public Integer getMaxId( );
}

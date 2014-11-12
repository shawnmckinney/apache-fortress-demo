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
public interface Page1Dao
{
    public int addPage1( Page1EO page1 );

    public int updatePage1( Page1EO page1 );

    public Page1EO getPage1ById( Integer id );

    public void deletePage1ById( Integer id );

    public List<Page1EO> getPages1ByAttrA( String attr_a );

    public List<Page1EO> getPages1ByCustomer( String customer );

    public Integer getMaxId( );
}

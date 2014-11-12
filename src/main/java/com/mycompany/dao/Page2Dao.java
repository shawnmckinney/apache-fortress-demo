/*
 * This is free and unencumbered software released into the public domain.
 */

package com.mycompany.dao;


import java.util.List;

/**
 * Example Page2 DAO interface.
 *
 * @author Shawn McKinney
 * @version $Rev$
 */
public interface Page2Dao
{
    public int addPage2( Page2EO page2 );

    public int updatePage2( Page2EO page2 );

    public Page2EO getPage2ById( Integer id );

    public void deletePage2ById( Integer id );

    public List<Page2EO> getPages2ByAttrD( String attr_a );

    public List<Page2EO> getPages2ByCustomer( String customer );

    public Integer getMaxId( );
}

/*
 * This is free and unencumbered software released into the public domain.
 */

package com.mycompany.dao;


import java.io.Serializable;

/**
 * Example Page3 entity data.
 *
 * @author Shawn McKinney
 * @version $Rev$
 */
public class Page3EO implements Serializable
{
    private Integer id;
    private String customer;
    private String attr_g;
    private String attr_h;
    private String attr_i;

    public String getCustomer()
    {
        return customer;
    }

    public void setCustomer( String customer )
    {
        this.customer = customer;
    }

    public String getAttr_g()
    {
        return attr_g;
    }

    public void setAttr_g( String attr_g )
    {
        this.attr_g = attr_g;
    }

    public String getAttr_h()
    {
        return attr_h;
    }

    public void setAttr_h( String attr_h )
    {
        this.attr_h = attr_h;
    }

    public String getAttr_i()
    {
        return attr_i;
    }

    public void setAttr_i( String attr_i )
    {
        this.attr_i = attr_i;
    }

    public Integer getId()
    {
        return id;
    }

    public void setId( Integer id )
    {
        this.id = id;
    }
}

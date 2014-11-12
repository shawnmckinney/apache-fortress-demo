/*
 * This is free and unencumbered software released into the public domain.
 */

package com.mycompany.dao;


import java.io.Serializable;

/**
 * Example Page1 entity data.
 *
 * @author Shawn McKinney
 * @version $Rev$
 */
public class Page1EO implements Serializable
{
    private Integer id;
    private String customer;
    private String attr_a;
    private String attr_b;
    private String attr_c;

    public String getCustomer()
    {
        return customer;
    }

    public void setCustomer( String customer )
    {
        this.customer = customer;
    }

    public String getAttr_a()
    {
        return attr_a;
    }

    public void setAttr_a( String attr_a )
    {
        this.attr_a = attr_a;
    }

    public String getAttr_b()
    {
        return attr_b;
    }

    public void setAttr_b( String attr_b )
    {
        this.attr_b = attr_b;
    }

    public String getAttr_c()
    {
        return attr_c;
    }

    public void setAttr_c( String attr_c )
    {
        this.attr_c = attr_c;
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

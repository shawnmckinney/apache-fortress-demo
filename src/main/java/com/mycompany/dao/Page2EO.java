/*
 * This is free and unencumbered software released into the public domain.
 */

package com.mycompany.dao;


import java.io.Serializable;

/**
 * Example Page2 entity data.
 *
 * @author Shawn McKinney
 * @version $Rev$
 */
public class Page2EO implements Serializable
{
    private Integer id;
    private String customer;
    private String attr_d;
    private String attr_e;
    private String attr_f;

    public String getCustomer()
    {
        return customer;
    }

    public void setCustomer( String customer )
    {
        this.customer = customer;
    }

    public String getAttr_d()
    {
        return attr_d;
    }

    public void setAttr_d( String attr_d )
    {
        this.attr_d = attr_d;
    }

    public String getAttr_e()
    {
        return attr_e;
    }

    public void setAttr_e( String attr_e )
    {
        this.attr_e = attr_e;
    }

    public String getAttr_f()
    {
        return attr_f;
    }

    public void setAttr_f( String attr_f )
    {
        this.attr_f = attr_f;
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

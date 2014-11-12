/*
 * This is free and unencumbered software released into the public domain.
 */

package com.mycompany;


import org.apache.wicket.ajax.AjaxRequestTarget;

/**
 * @author Shawn McKinney
 * @version $Rev$
 */
public class AjaxUpdateEvent
{

    private final AjaxRequestTarget target;

    public AjaxUpdateEvent(AjaxRequestTarget target)
    {
        this.target = target;
    }

    public AjaxRequestTarget getAjaxRequestTarget()
    {
        return target;
    }
}

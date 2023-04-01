/*  © 2023 iamfortress.net   */
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

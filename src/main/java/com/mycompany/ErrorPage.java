/*  © 2025 iamfortress.net   */package com.mycompany;


import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.model.Model;

/**
 * @author Shawn McKinney
 * @version $Rev$
 */
public class ErrorPage extends MyBasePage
{
    public ErrorPage( )
    {
        add( new Label( "title", new Model<>( "Runtime Exception Occurred" ) ) );
        add( new Label( "message", new Model<>( "runtime exception" ) ) );
    }

    public ErrorPage( Exception e )
    {
        add( new Label( "title", new Model<>( "Runtime Exception Occurred" ) ) );
        add( new Label( "message", new Model<>( e.getLocalizedMessage() ) ) );
    }
}

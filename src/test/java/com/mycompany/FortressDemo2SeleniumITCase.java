/*
 * Copyright (c) 2013, JoshuaTree Software. All rights reserved.
 */
package com.mycompany;

import java.lang.String;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.junit.*;
import static org.junit.Assert.*;

import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;

/**
 * This class uses apache selenium firefox driver to drive commander web ui
 */
public class FortressDemo2SeleniumITCase
{
    private WebDriver driver;
    private String baseUrl;
    private boolean acceptNextAlert = true;
    private StringBuffer verificationErrors = new StringBuffer();
    private static final Logger LOG = Logger.getLogger( FortressDemo2SeleniumITCase.class.getName() );

    @Before
    public void setUp() throws Exception
    {
        FirefoxProfile ffProfile = new FirefoxProfile();
        ffProfile.setPreference( "browser.safebrowsing.malware.enabled", false );
        driver = new FirefoxDriver( ffProfile );
        driver.manage().window().maximize();

        // Use test local default:
        baseUrl = "http://localhost:8080";
        //baseUrl = "http://fortressdemo2.com:8080";
        //baseUrl = "https://fortressdemo2.com:8443";
        baseUrl += "/fortressdemo2";
        driver.manage().timeouts().implicitlyWait( 2500, TimeUnit.MILLISECONDS );
    }

    private void info(String msg)
    {
        ( ( JavascriptExecutor ) driver ).executeScript( "$(document.getElementById('infoField')).val('" + msg + "');" );
    }

    @Test
    public void testCase1() throws Exception
    {
        LOG.info( "Begin FortressDemo2SeleniumITCase" );
        driver.get( baseUrl );

        // SuperUser has access to all pages and all customer data without restriction:
        login( GlobalUtils.SUPER_USER, "password");
        TUtils.sleep( 1 );
        doPositiveButtonTests( GlobalUtils.PAGE_1, GlobalUtils.BTN_PAGE_1 );
        TUtils.sleep( 1 );
        doPositiveDataTest(GlobalUtils.BTN_PAGE_1, GlobalUtils.ROLE_SUPER, "123");
        TUtils.sleep( 1 );
        doPositiveDataTest(GlobalUtils.BTN_PAGE_1, GlobalUtils.ROLE_SUPER, "456");
        TUtils.sleep( 1 );
        doPositiveDataTest(GlobalUtils.BTN_PAGE_1, GlobalUtils.ROLE_SUPER, "789");
        TUtils.sleep( 1 );
        doPositiveButtonTests( GlobalUtils.PAGE_2, GlobalUtils.BTN_PAGE_2 );
        TUtils.sleep( 1 );
        doPositiveDataTest(GlobalUtils.BTN_PAGE_2, GlobalUtils.ROLE_SUPER, "123");
        TUtils.sleep( 1 );
        doPositiveDataTest(GlobalUtils.BTN_PAGE_2, GlobalUtils.ROLE_SUPER, "456");
        TUtils.sleep( 1 );
        doPositiveDataTest(GlobalUtils.BTN_PAGE_2, GlobalUtils.ROLE_SUPER, "789");
        TUtils.sleep( 1 );
        doPositiveButtonTests( GlobalUtils.PAGE_3, GlobalUtils.BTN_PAGE_3 );
        doPositiveDataTest(GlobalUtils.BTN_PAGE_3, GlobalUtils.ROLE_SUPER, "123");
        TUtils.sleep( 1 );
        doPositiveDataTest(GlobalUtils.BTN_PAGE_3, GlobalUtils.ROLE_SUPER, "456");
        TUtils.sleep( 1 );
        doPositiveDataTest(GlobalUtils.BTN_PAGE_3, GlobalUtils.ROLE_SUPER, "789");
        TUtils.sleep( 1 );
        logout( GlobalUtils.SUPER_USER );

        // Poweruser has access to all pages, and all customer data with DSD constraints applied:
        login( GlobalUtils.POWER_USER, "password");
        TUtils.sleep( 1 );
        driver.findElement( By.linkText( GlobalUtils.PAGE_1 ) ).click();
        activateRole( GlobalUtils.ROLE_PAGE3_789 );
        doActivateTest( GlobalUtils.POWER_USER, GlobalUtils.PAGE_1, GlobalUtils.BTN_PAGE_1, GlobalUtils.ROLE_PAGE1_123, GlobalUtils.ROLE_PAGE3_789, "123", "456", true );
        driver.findElement( By.linkText( GlobalUtils.PAGE_2 ) ).click();
        doActivateTest( GlobalUtils.POWER_USER, GlobalUtils.PAGE_2, GlobalUtils.BTN_PAGE_2, GlobalUtils.ROLE_PAGE2_123, GlobalUtils.ROLE_PAGE1_123, "123", "456", true );
        driver.findElement( By.linkText( GlobalUtils.PAGE_3 ) ).click();
        doActivateTest( GlobalUtils.POWER_USER, GlobalUtils.PAGE_3, GlobalUtils.BTN_PAGE_3, GlobalUtils.ROLE_PAGE3_123, GlobalUtils.ROLE_PAGE2_123, "123", "456", true );
        driver.findElement( By.linkText( GlobalUtils.PAGE_1 ) ).click();
        doActivateTest( GlobalUtils.POWER_USER, GlobalUtils.PAGE_1, GlobalUtils.BTN_PAGE_1, GlobalUtils.ROLE_PAGE1_456, GlobalUtils.ROLE_PAGE3_123, "456", "789", true );
        driver.findElement( By.linkText( GlobalUtils.PAGE_2 ) ).click();
        doActivateTest( GlobalUtils.POWER_USER, GlobalUtils.PAGE_2, GlobalUtils.BTN_PAGE_2, GlobalUtils.ROLE_PAGE2_456, GlobalUtils.ROLE_PAGE1_456, "456", "789", true );
        driver.findElement( By.linkText( GlobalUtils.PAGE_3 ) ).click();
        doActivateTest( GlobalUtils.POWER_USER, GlobalUtils.PAGE_3, GlobalUtils.BTN_PAGE_3, GlobalUtils.ROLE_PAGE3_456, GlobalUtils.ROLE_PAGE2_456, "456", "789", true );
        driver.findElement( By.linkText( GlobalUtils.PAGE_1 ) ).click();
        doActivateTest( GlobalUtils.POWER_USER, GlobalUtils.PAGE_1, GlobalUtils.BTN_PAGE_1, GlobalUtils.ROLE_PAGE1_789, GlobalUtils.ROLE_PAGE3_456, "789", "123", true );
        driver.findElement( By.linkText( GlobalUtils.PAGE_2 ) ).click();
        doActivateTest( GlobalUtils.POWER_USER, GlobalUtils.PAGE_2, GlobalUtils.BTN_PAGE_2, GlobalUtils.ROLE_PAGE2_789, GlobalUtils.ROLE_PAGE1_789, "789", "123", true );
        driver.findElement( By.linkText( GlobalUtils.PAGE_3 ) ).click();
        doActivateTest( GlobalUtils.POWER_USER, GlobalUtils.PAGE_3, GlobalUtils.BTN_PAGE_3, GlobalUtils.ROLE_PAGE3_789, GlobalUtils.ROLE_PAGE2_789, "789", "123", true );
        logout( GlobalUtils.POWER_USER );

        // User 123, has access to all pages, Customer 123 data only:
        login( GlobalUtils.USER_123, "password" );
        TUtils.sleep( 1 );
        doNegativeButtonTests( GlobalUtils.PAGE_1, GlobalUtils.USER_123, GlobalUtils.BTN_PAGE_1 );
        doActivateTest( GlobalUtils.USER_123, GlobalUtils.PAGE_1, GlobalUtils.BTN_PAGE_1, GlobalUtils.ROLE_PAGE1_123, null, "123", "789", false );
        TUtils.sleep( 1 );
        doNegativeDataTest(GlobalUtils.BTN_PAGE_1, GlobalUtils.ROLE_PAGE1_123, "456");
        TUtils.sleep( 1 );
        doNegativeDataTest(GlobalUtils.BTN_PAGE_1, GlobalUtils.ROLE_PAGE1_123, "789");
        driver.findElement( By.linkText( GlobalUtils.PAGE_2 ) ).click();
        doActivateTest( GlobalUtils.USER_123, GlobalUtils.PAGE_2, GlobalUtils.BTN_PAGE_2, GlobalUtils.ROLE_PAGE2_123, GlobalUtils.ROLE_PAGE1_123, "123", "789", true );
        TUtils.sleep( 1 );
        doNegativeDataTest(GlobalUtils.BTN_PAGE_2, GlobalUtils.ROLE_PAGE2_123, "456");
        TUtils.sleep( 1 );
        doNegativeDataTest(GlobalUtils.BTN_PAGE_2, GlobalUtils.ROLE_PAGE2_123, "789");
        driver.findElement( By.linkText( GlobalUtils.PAGE_3 ) ).click();
        doActivateTest( GlobalUtils.USER_123, GlobalUtils.PAGE_3, GlobalUtils.BTN_PAGE_3, GlobalUtils.ROLE_PAGE3_123, GlobalUtils.ROLE_PAGE2_123, "123", "789", true );
        TUtils.sleep( 1 );
        doNegativeDataTest(GlobalUtils.BTN_PAGE_3, GlobalUtils.ROLE_PAGE3_123, "456");
        TUtils.sleep( 1 );
        doNegativeDataTest(GlobalUtils.BTN_PAGE_3, GlobalUtils.ROLE_PAGE3_123, "789");
        logout( GlobalUtils.USER_123 );

        // User 456, has access to all pages, Customer 456 data only:
        login( GlobalUtils.USER_456, "password" );
        TUtils.sleep( 1 );
        doNegativeButtonTests( GlobalUtils.PAGE_1, GlobalUtils.USER_456, GlobalUtils.BTN_PAGE_1 );
        doActivateTest( GlobalUtils.USER_456, GlobalUtils.PAGE_1, GlobalUtils.BTN_PAGE_1, GlobalUtils.ROLE_PAGE1_456, null, "456", "123", false );
        TUtils.sleep( 1 );
        doNegativeDataTest(GlobalUtils.BTN_PAGE_1, GlobalUtils.ROLE_PAGE1_456, "123");
        TUtils.sleep( 1 );
        doNegativeDataTest(GlobalUtils.BTN_PAGE_1, GlobalUtils.ROLE_PAGE1_456, "789");
        driver.findElement( By.linkText( GlobalUtils.PAGE_2 ) ).click();
        doActivateTest( GlobalUtils.USER_456, GlobalUtils.PAGE_2, GlobalUtils.BTN_PAGE_2, GlobalUtils.ROLE_PAGE2_456, GlobalUtils.ROLE_PAGE1_456, "456", "789", true );
        TUtils.sleep( 1 );
        doNegativeDataTest(GlobalUtils.BTN_PAGE_2, GlobalUtils.ROLE_PAGE2_456, "123");
        TUtils.sleep( 1 );
        doNegativeDataTest(GlobalUtils.BTN_PAGE_2, GlobalUtils.ROLE_PAGE2_456, "789");
        driver.findElement( By.linkText( GlobalUtils.PAGE_3 ) ).click();
        doActivateTest( GlobalUtils.USER_456, GlobalUtils.PAGE_3, GlobalUtils.BTN_PAGE_3, GlobalUtils.ROLE_PAGE3_456, GlobalUtils.ROLE_PAGE2_456, "456", "789", true );
        TUtils.sleep( 1 );
        doNegativeDataTest(GlobalUtils.BTN_PAGE_3, GlobalUtils.ROLE_PAGE3_456, "123");
        TUtils.sleep( 1 );
        doNegativeDataTest(GlobalUtils.BTN_PAGE_3, GlobalUtils.ROLE_PAGE3_456, "789");
        logout( GlobalUtils.USER_456 );

        // User 789, has access to all pages, Customer 789 data only:
        login( GlobalUtils.USER_789, "password" );
        TUtils.sleep( 1 );
        doNegativeButtonTests( GlobalUtils.PAGE_1, GlobalUtils.USER_789, GlobalUtils.BTN_PAGE_1 );
        doActivateTest( GlobalUtils.USER_789, GlobalUtils.PAGE_1, GlobalUtils.BTN_PAGE_1, GlobalUtils.ROLE_PAGE1_789, null, "789", "123", false );
        TUtils.sleep( 1 );
        doNegativeDataTest(GlobalUtils.BTN_PAGE_1, GlobalUtils.ROLE_PAGE1_789, "123");
        TUtils.sleep( 1 );
        doNegativeDataTest(GlobalUtils.BTN_PAGE_1, GlobalUtils.ROLE_PAGE1_789, "456");
        driver.findElement( By.linkText( GlobalUtils.PAGE_2 ) ).click();
        doActivateTest( GlobalUtils.USER_789, GlobalUtils.PAGE_2, GlobalUtils.BTN_PAGE_2, GlobalUtils.ROLE_PAGE2_789, GlobalUtils.ROLE_PAGE1_789, "789", "456", true );
        TUtils.sleep( 1 );
        doNegativeDataTest(GlobalUtils.BTN_PAGE_2, GlobalUtils.ROLE_PAGE2_789, "123");
        TUtils.sleep( 1 );
        doNegativeDataTest(GlobalUtils.BTN_PAGE_2, GlobalUtils.ROLE_PAGE2_789, "456");
        driver.findElement( By.linkText( GlobalUtils.PAGE_3 ) ).click();
        doActivateTest( GlobalUtils.USER_789, GlobalUtils.PAGE_3, GlobalUtils.BTN_PAGE_3, GlobalUtils.ROLE_PAGE3_789, GlobalUtils.ROLE_PAGE2_789, "789", "123", true );
        TUtils.sleep( 1 );
        doNegativeDataTest(GlobalUtils.BTN_PAGE_3, GlobalUtils.ROLE_PAGE3_789, "123");
        TUtils.sleep( 1 );
        doNegativeDataTest(GlobalUtils.BTN_PAGE_3, GlobalUtils.ROLE_PAGE3_789, "456");
        logout( GlobalUtils.USER_789 );

        // User 1 has access to Page 1, all customer data with DSD constraints applied::
        login( GlobalUtils.USER_1, "password" );
        TUtils.sleep( 1 );
        doNegativeButtonTests( GlobalUtils.PAGE_1, GlobalUtils.USER_1, GlobalUtils.BTN_PAGE_1 );
        doNegativeLinkTest( GlobalUtils.PAGE_2, GlobalUtils.USER_1 );
        doNegativeLinkTest( GlobalUtils.PAGE_3, GlobalUtils.USER_1 );
        activateRole( GlobalUtils.ROLE_PAGE1_789 );
        doActivateTest( GlobalUtils.USER_1, GlobalUtils.PAGE_1, GlobalUtils.BTN_PAGE_1, GlobalUtils.ROLE_PAGE1_123, GlobalUtils.ROLE_PAGE1_789, "123", "789", true );
        doActivateTest( GlobalUtils.USER_1, GlobalUtils.PAGE_1, GlobalUtils.BTN_PAGE_1, GlobalUtils.ROLE_PAGE1_456, GlobalUtils.ROLE_PAGE1_123, "456", "123", true );
        doActivateTest( GlobalUtils.USER_1, GlobalUtils.PAGE_1, GlobalUtils.BTN_PAGE_1, GlobalUtils.ROLE_PAGE1_789, GlobalUtils.ROLE_PAGE1_456, "789", "456", true );
        logout( GlobalUtils.USER_1 );

        // User 1 123 has access to Page 1, Customer 123 only:
        login( GlobalUtils.USER_1_123, "password" );
        TUtils.sleep( 1 );
        doNegativeButtonTests( GlobalUtils.PAGE_1, GlobalUtils.USER_1_123, GlobalUtils.BTN_PAGE_1 );
        doNegativeLinkTest( GlobalUtils.PAGE_2, GlobalUtils.USER_1_123 );
        doNegativeLinkTest( GlobalUtils.PAGE_3, GlobalUtils.USER_1_123 );
        doActivateTest( GlobalUtils.USER_1_123, GlobalUtils.PAGE_1, GlobalUtils.BTN_PAGE_1, GlobalUtils.ROLE_PAGE1_123, null, "123", "789", false );
        logout( GlobalUtils.USER_1_123 );

        // User 1 456 has access to Page 1, Customer 456 only::
        login( GlobalUtils.USER_1_456, "password" );
        TUtils.sleep( 1 );
        doNegativeButtonTests( GlobalUtils.PAGE_1, GlobalUtils.USER_1_456, GlobalUtils.BTN_PAGE_1 );
        doNegativeLinkTest( GlobalUtils.PAGE_2, GlobalUtils.USER_1_456 );
        doNegativeLinkTest( GlobalUtils.PAGE_3, GlobalUtils.USER_1_456 );
        doActivateTest( GlobalUtils.USER_1_456, GlobalUtils.PAGE_1, GlobalUtils.BTN_PAGE_1, GlobalUtils.ROLE_PAGE1_456, null, "456", "123", false );
        logout( GlobalUtils.USER_1_456 );

        // User 1 789 has access to Page 1, Customer 789 only::
        login( GlobalUtils.USER_1_789, "password" );
        TUtils.sleep( 1 );
        doNegativeButtonTests( GlobalUtils.PAGE_1, GlobalUtils.USER_1_789, GlobalUtils.BTN_PAGE_1 );
        doNegativeLinkTest( GlobalUtils.PAGE_2, GlobalUtils.USER_1_789 );
        doNegativeLinkTest( GlobalUtils.PAGE_3, GlobalUtils.USER_1_789 );
        doActivateTest( GlobalUtils.USER_1_789, GlobalUtils.PAGE_1, GlobalUtils.BTN_PAGE_1, GlobalUtils.ROLE_PAGE1_789, null, "789", "123", false );
        logout( GlobalUtils.USER_1_789 );

        // User 2 has access to Page 2, all customer data with DSD constraints applied::
        login( GlobalUtils.USER_2, "password" );
        TUtils.sleep( 1 );
        doNegativeButtonTests( GlobalUtils.PAGE_2, GlobalUtils.USER_2, GlobalUtils.BTN_PAGE_2 );
        doNegativeLinkTest( GlobalUtils.PAGE_1, GlobalUtils.USER_2 );
        doNegativeLinkTest( GlobalUtils.PAGE_3, GlobalUtils.USER_2 );
        activateRole( GlobalUtils.ROLE_PAGE2_789 );
        doActivateTest( GlobalUtils.USER_2, GlobalUtils.PAGE_2, GlobalUtils.BTN_PAGE_2, GlobalUtils.ROLE_PAGE2_123, GlobalUtils.ROLE_PAGE2_789, "123", "789", true );
        doActivateTest( GlobalUtils.USER_2, GlobalUtils.PAGE_2, GlobalUtils.BTN_PAGE_2, GlobalUtils.ROLE_PAGE2_456, GlobalUtils.ROLE_PAGE2_123, "456", "123", true );
        doActivateTest( GlobalUtils.USER_2, GlobalUtils.PAGE_2, GlobalUtils.BTN_PAGE_2, GlobalUtils.ROLE_PAGE2_789, GlobalUtils.ROLE_PAGE2_456, "789", "456", true );
        logout( GlobalUtils.USER_2 );

        // User 2 123 has access to Page 2, Customer 123 only::
        login( GlobalUtils.USER_2_123, "password" );
        TUtils.sleep( 1 );
        doNegativeButtonTests( GlobalUtils.PAGE_2, GlobalUtils.USER_2_123, GlobalUtils.BTN_PAGE_2 );
        doNegativeLinkTest( GlobalUtils.PAGE_1, GlobalUtils.USER_2_123 );
        doNegativeLinkTest( GlobalUtils.PAGE_3, GlobalUtils.USER_2_123 );
        doActivateTest( GlobalUtils.USER_2_123, GlobalUtils.PAGE_2, GlobalUtils.BTN_PAGE_2, GlobalUtils.ROLE_PAGE2_123, null, "123", "789", false );
        logout( GlobalUtils.USER_2_123 );

        // User 2 456 has access to Page 2, Customer 456 only::
        login( GlobalUtils.USER_2_456, "password" );
        TUtils.sleep( 1 );
        doNegativeButtonTests( GlobalUtils.PAGE_2, GlobalUtils.USER_2_456, GlobalUtils.BTN_PAGE_2 );
        doNegativeLinkTest( GlobalUtils.PAGE_1, GlobalUtils.USER_2_456 );
        doNegativeLinkTest( GlobalUtils.PAGE_3, GlobalUtils.USER_2_456 );
        doActivateTest( GlobalUtils.USER_2_456, GlobalUtils.PAGE_2, GlobalUtils.BTN_PAGE_2, GlobalUtils.ROLE_PAGE2_456, null, "456", "123", false );
        logout( GlobalUtils.USER_2_456 );

        // User 2 789 has access to Page 2, Customer 789 only::
        login( GlobalUtils.USER_2_789, "password" );
        TUtils.sleep( 1 );
        doNegativeButtonTests( GlobalUtils.PAGE_2, GlobalUtils.USER_2_789, GlobalUtils.BTN_PAGE_2 );
        doNegativeLinkTest( GlobalUtils.PAGE_1, GlobalUtils.USER_2_789 );
        doNegativeLinkTest( GlobalUtils.PAGE_3, GlobalUtils.USER_2_789 );
        doActivateTest( GlobalUtils.USER_2_789, GlobalUtils.PAGE_2, GlobalUtils.BTN_PAGE_2, GlobalUtils.ROLE_PAGE2_789, null, "789", "123", false );
        logout( GlobalUtils.USER_1_789 );

        // User 3 has access to Page 3, all customer data with DSD constraints applied::
        login( GlobalUtils.USER_3, "password" );
        TUtils.sleep( 1 );
        doNegativeButtonTests( GlobalUtils.PAGE_3, GlobalUtils.USER_3, GlobalUtils.BTN_PAGE_3 );
        doNegativeLinkTest( GlobalUtils.PAGE_1, GlobalUtils.USER_3 );
        doNegativeLinkTest( GlobalUtils.PAGE_2, GlobalUtils.USER_3 );
        activateRole( GlobalUtils.ROLE_PAGE3_789 );
        doActivateTest( GlobalUtils.USER_3, GlobalUtils.PAGE_3, GlobalUtils.BTN_PAGE_3, GlobalUtils.ROLE_PAGE3_123, GlobalUtils.ROLE_PAGE3_789, "123", "789", true );
        doActivateTest( GlobalUtils.USER_3, GlobalUtils.PAGE_3, GlobalUtils.BTN_PAGE_3, GlobalUtils.ROLE_PAGE3_456, GlobalUtils.ROLE_PAGE3_123, "456", "123", true );
        doActivateTest( GlobalUtils.USER_3, GlobalUtils.PAGE_3, GlobalUtils.BTN_PAGE_3, GlobalUtils.ROLE_PAGE3_789, GlobalUtils.ROLE_PAGE3_456, "789", "456", true );
        logout( GlobalUtils.USER_3 );

        // User 3 123 has access to Page 3, Customer 123 only::
        login( GlobalUtils.USER_3_123, "password" );
        TUtils.sleep( 1 );
        doNegativeButtonTests( GlobalUtils.PAGE_3, GlobalUtils.USER_3_123, GlobalUtils.BTN_PAGE_3 );
        doNegativeLinkTest( GlobalUtils.PAGE_1, GlobalUtils.USER_3_123 );
        doNegativeLinkTest( GlobalUtils.PAGE_2, GlobalUtils.USER_3_123 );
        doActivateTest( GlobalUtils.USER_3_123, GlobalUtils.PAGE_3, GlobalUtils.BTN_PAGE_3, GlobalUtils.ROLE_PAGE3_123, null, "123", "789", false );
        logout( GlobalUtils.USER_3_123 );

        // User 3 456 has access to Page 3, Customer 456 only::
        login( GlobalUtils.USER_3_456, "password" );
        TUtils.sleep( 1 );
        doNegativeButtonTests( GlobalUtils.PAGE_3, GlobalUtils.USER_3_456, GlobalUtils.BTN_PAGE_3 );
        doNegativeLinkTest( GlobalUtils.PAGE_1, GlobalUtils.USER_3_456 );
        doNegativeLinkTest( GlobalUtils.PAGE_2, GlobalUtils.USER_3_456 );
        doActivateTest( GlobalUtils.USER_3_456, GlobalUtils.PAGE_3, GlobalUtils.BTN_PAGE_3, GlobalUtils.ROLE_PAGE3_456, null, "456", "123", false );
        logout( GlobalUtils.USER_3_456 );

        // User 3 789 has access to Page 3, Customer 789 only::
        login( GlobalUtils.USER_3_789, "password" );
        TUtils.sleep( 1 );
        doNegativeButtonTests( GlobalUtils.PAGE_3, GlobalUtils.USER_3_789, GlobalUtils.BTN_PAGE_3 );
        doNegativeLinkTest( GlobalUtils.PAGE_1, GlobalUtils.USER_3_789 );
        doNegativeLinkTest( GlobalUtils.PAGE_2, GlobalUtils.USER_3_789 );
        doActivateTest( GlobalUtils.USER_3_789, GlobalUtils.PAGE_3, GlobalUtils.BTN_PAGE_3, GlobalUtils.ROLE_PAGE3_789, null, "789", "123", false );
        logout( GlobalUtils.USER_1_789 );
    }

    private void doActivateTest( String userId, String page, String buttonPage, String activateRole, String deactivateRole, String authorizedData, String unauthorizedData, boolean isDeactivateTest )
    {
        info( "Do Role Activation Test role: " + activateRole );
        doNegativeButtonTest( userId, page, GlobalUtils.ADD );

        if(isDeactivateTest)
        {
            // Now activate ROLE:
            activateRole(activateRole);
            // Make sure the pop-up is correct:
            if(!processPopup("Role selection " + activateRole + " activation failed because of Dynamic SoD rule violation"))
                fail("doActivate Button Test 2 Failed: " + buttonPage);
            doNegativeButtonTest( userId, page, buttonPage );

            // Now deactivate ROLE_PAGE1:
            ( ( JavascriptExecutor ) driver ).executeScript( "$(document.getElementById('" + GlobalUtils.ACTIVE_ROLES + "')).val('" + deactivateRole + "');" );
            driver.findElement( By.name( GlobalUtils.ROLES_DEACTIVATE ) ).click();
            TUtils.sleep( 1 );
        }
        // Now active ROLE:
        activateRole(activateRole);

        //TUtils.sleep( 1 );

        // Click the buttons on page 2
        doPositiveButtonTests( null, buttonPage );
        TUtils.sleep( 1 );
        doNegativeDataTest(buttonPage, activateRole, unauthorizedData);
        TUtils.sleep( 1 );
        doPositiveDataTest(buttonPage, activateRole, authorizedData);
    }

    private void doPositiveDataTest(String buttonPage, String activateRole, String data)
    {
        info( "Postive Data test for role: " + activateRole + ", customer: " + data );
        driver.findElement( By.id( GlobalUtils.CUSTOMER_EF_ID ) ).clear();
        driver.findElement( By.id( GlobalUtils.CUSTOMER_EF_ID ) ).sendKeys( data );
        driver.findElement( By.name( buttonPage + "." + GlobalUtils.SEARCH ) ).click();
    }

    private void doNegativeDataTest(String buttonPage, String activateRole, String data)
    {
        info( "Negative Data test for role: " + activateRole + ", customer: " + data );
        driver.findElement( By.id( GlobalUtils.CUSTOMER_EF_ID ) ).clear();
        driver.findElement( By.id( GlobalUtils.CUSTOMER_EF_ID ) ).sendKeys( data );
        driver.findElement( By.name( buttonPage + "." + GlobalUtils.SEARCH ) ).click();
        if(!processPopup("Unauthorized"))
            fail("doActivateTest Unauthorized data Test Failed: " + buttonPage + "." + GlobalUtils.SEARCH);
    }

    private void doPositiveButtonTests( String linkName, String pageId )
    {
        info( "Postive Button test for " + linkName );
        if(linkName != null)
            driver.findElement( By.linkText( linkName ) ).click();
        TUtils.sleep( 1 );
        // Click the buttons on the page
        doPositiveButtonTest(pageId, GlobalUtils.ADD, pageId + "." + GlobalUtils.ADD);
        doPositiveButtonTest(pageId, GlobalUtils.UPDATE, pageId + "." + GlobalUtils.UPDATE);
        doPositiveButtonTest(pageId, GlobalUtils.DELETE, pageId + "." + GlobalUtils.DELETE);
        doPositiveButtonTest(pageId, GlobalUtils.SEARCH, pageId + "." + GlobalUtils.SEARCH);
    }

    private void doNegativeButtonTests( String linkName, String userId, String pageId )
    {
        info( "Negative Button test for user: " + userId + ", linkName: " + linkName );
        if(linkName != null)
            driver.findElement( By.linkText( linkName ) ).click();
        doNegativeButtonTest( userId, pageId, GlobalUtils.ADD );
        doNegativeButtonTest( userId, pageId, GlobalUtils.UPDATE );
        doNegativeButtonTest( userId, pageId, GlobalUtils.DELETE );
        doNegativeButtonTest( userId, pageId, GlobalUtils.SEARCH );
    }

    private boolean processPopup(String text)
    {
        boolean textFound = false;
        try
        {
            Alert alert = driver.switchTo ().alert ();
            //alert is present
            LOG.info( "Button Pressed:" + alert.getText() );
            if(alert.getText().contains( text ))
                textFound = true;

            alert.accept();
        }
        catch ( NoAlertPresentException n)
        {
            //Alert isn't present
        }
        return textFound;
    }

    private void activateRole(String roleName)
    {
        info("Activate test for " + roleName);
        ( ( JavascriptExecutor ) driver ).executeScript( "$(document.getElementById('" + GlobalUtils.INACTIVE_ROLES + "')).val('" + roleName + "');" );
        driver.findElement( By.name( GlobalUtils.ROLES_ACTIVATE ) ).click();
    }

    private void doPositiveButtonTest(String pageId, String buttonId, String alertText)
    {
        info("Positive button test for " + pageId + ", " + buttonId);
        try
        {
            driver.findElement( By.name( pageId + "." + buttonId ) ).click();
        }
        catch(Exception e)
        {
            LOG.error( "activateRole Exception: " + e);
        }

        //TUtils.sleep( 1 );
        //if(!processPopup(alertText))
        //    fail("Button Test Failed: " + pageId + "." + buttonId);
    }

    private void doNegativeButtonTest( String userId, String pageId, String buttonId )
    {
        info("Negative button test for " + buttonId + ", and " + userId);
        try
        {
            driver.findElement( By.name( pageId + "." + buttonId ) ).click();
            fail("Negative Button Test Failed: " + pageId + "." + GlobalUtils.ADD);
        }
        catch (org.openqa.selenium.NoSuchElementException e)
        {
            // pass
        }
    }

    private void doNegativeLinkTest( String linkName, String userId  )
    {
        info("Negative link:" + linkName + " test for " + userId);
        try
        {
            if(driver.findElement( By.linkText( linkName ) ).isEnabled())
            {
                fail("Negative Link Test Failed UserId: " + userId + " Link: " + linkName);
            }
            fail("Negative Button Test Failed UserId: " + userId + " Link: " + linkName);
        }
        catch (org.openqa.selenium.NoSuchElementException e)
        {
            // pass
        }
        try
        {
            if(driver.findElement( By.linkText( linkName ) ).isEnabled())
            {
                fail("Negative Link Test Failed UserId: " + userId + " Link: " + linkName);
            }
        }
        catch (org.openqa.selenium.NoSuchElementException e)
        {
            // pass
        }

        // Check that Spring security is enforcing page level security:
        String pageName = linkName;
        // convert from link name to page name for url:
        pageName = pageName.substring( 0, 1 ) + pageName.substring( 1 ).toLowerCase();
        String unauthorizedUrl = baseUrl + "/wicket/bookmarkable/com.mycompany." + pageName;
        driver.get( unauthorizedUrl );
        if(is403())
        {
            // pass
            TUtils.sleep( 1 );
            driver.navigate().back();
        }
        else
        {
            fail("Spring Security Test Failed URL: " + unauthorizedUrl + "." + GlobalUtils.ADD);
        }
    }

    public boolean is403()
    {
        try
        {
            driver.findElement(By.id("web_403"));
            return true;
        }
        catch (NoSuchElementException e)
        {
            return false;
        }
    }
    private void login(String userId, String password)
    {
        driver.findElement( By.id( GlobalUtils.USER_ID ) ).clear();
        driver.findElement( By.id( GlobalUtils.USER_ID ) ).sendKeys( userId );
        driver.findElement( By.id( GlobalUtils.PSWD_FIELD ) ).clear();
        driver.findElement( By.id( GlobalUtils.PSWD_FIELD ) ).sendKeys( password );
        driver.findElement( By.name( GlobalUtils.LOGIN ) ).click();
        LOG.info( "User: " + userId + " has logged ON" );
        info("Login User: " + userId);
    }

    private void logout(String userId)
    {
        info("Logout " + userId);
        driver.findElement( By.linkText( "LOGOUT" ) ).click();
        LOG.info( "User: " + userId + " has logged OFF" );
    }

    private void nextPage(WebElement table, String szTableName)
    {
        table = driver.findElement(By.id( szTableName));
        List<WebElement> allRows = table.findElements(By.tagName("a"));
        for (WebElement row : allRows)
        {
            String szText = row.getText();
            if(szText.equals( "Go to the next page" ))
                row.click();
            LOG.debug( "row text=" + row.getText() );
        }
    }

    @After
    public void tearDown() throws Exception
    {
        driver.quit();
        String verificationErrorString = verificationErrors.toString();
        if ( !"".equals( verificationErrorString ) )
        {
            fail( verificationErrorString );
        }
    }

    private boolean isElementPresent( By by )
    {
        try
        {
            driver.findElement( by );
            return true;
        }
        catch ( NoSuchElementException e )
        {
            return false;
        }
    }

    private boolean isAlertPresent()
    {
        try
        {
            driver.switchTo().alert();
            return true;
        }
        catch ( NoAlertPresentException e )
        {
            return false;
        }
    }

    private String closeAlertAndGetItsText()
    {
        try
        {
            Alert alert = driver.switchTo().alert();
            String alertText = alert.getText();
            if ( acceptNextAlert )
            {
                alert.accept();
            }
            else
            {
                alert.dismiss();
            }
            return alertText;
        }
        finally
        {
            acceptNextAlert = true;
        }
    }
}

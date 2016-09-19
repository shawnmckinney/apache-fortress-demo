/*
 * This is free and unencumbered software released into the public domain.
 */
package com.mycompany;

import java.lang.String;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.github.bonigarcia.wdm.ChromeDriverManager;
import io.github.bonigarcia.wdm.MarionetteDriverManager;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.junit.*;
import static org.junit.Assert.*;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

/**
 * This class uses apache selenium firefox driver to drive commander web ui
 */
public class ApacheFortressDemoSeleniumITCase
{
    public static final String C123 = "123";
    public static final String C789 = "789";
    public static final String C456 = "456";
    private WebDriver driver;
    private String baseUrl;
    private boolean acceptNextAlert = true;
    private StringBuffer verificationErrors = new StringBuffer();
    private static final Logger LOG = Logger.getLogger( ApacheFortressDemoSeleniumITCase.class.getName() );
    private static final String DRIVER_SYS_PROP = "web.driver";
    private enum DriverType
    {
        FIREFOX,
        CHROME
    }

    private static DriverType driverType = DriverType.FIREFOX;


    @Before
    public void setUp() throws Exception
    {
        // Use test local default:
        baseUrl = "http://localhost:8080";
        //baseUrl = "https://fortressdemo2.com:8443";
        baseUrl += "/apache-fortress-demo";
        driver.manage().timeouts().implicitlyWait( 2500, TimeUnit.MILLISECONDS );
    }

    private void info(String msg)
    {
        ( ( JavascriptExecutor ) driver ).executeScript( "$(document.getElementById('infoField')).val('" + msg + "');" );
    }

    @BeforeClass
    public static void setupClass()
    {
        String szDriverType = System.getProperty( DRIVER_SYS_PROP );
        if( StringUtils.isNotEmpty( szDriverType ) && szDriverType.equalsIgnoreCase( DriverType.CHROME.toString() ))
        {
            driverType = DriverType.CHROME;
            ChromeDriverManager.getInstance().setup();
        }
        else
        {
            driverType = DriverType.FIREFOX;
            MarionetteDriverManager.getInstance().setup();
        }
    }

    @Before
    public void setupTest()
    {
        if ( driverType.equals( DriverType.CHROME ) )
        {
            driver = new ChromeDriver( );
        }
        else
        {
            driver = new FirefoxDriver( );
        }
        driver.manage().window().maximize();
    }

    @After
    public void teardown()
    {
        if (driver != null)
        {
            driver.quit();
        }
    }
    @Test
    public void testCase1() throws Exception
    {
        LOG.info( "Begin FortressDemo2SeleniumITCase" );
        driver.get( baseUrl );

        // User 123, has access to all pages, Customer 123 data only:
        login( GlobalIds.USER_123, "password" );
        TUtils.sleep( 1 );
        doNegativeButtonTests( GlobalIds.PAGE_1, GlobalIds.USER_123, GlobalIds.BTN_PAGE_1 );
        doActivateTest( GlobalIds.USER_123, GlobalIds.PAGE_1, GlobalIds.BTN_PAGE_1, GlobalIds.ROLE_PAGE1_123, null, C123, C789, false );
        TUtils.sleep( 1 );
        doNegativeDataTest( GlobalIds.PAGE_1, GlobalIds.BTN_PAGE_1, GlobalIds.ROLE_PAGE1_123, C456 );
        TUtils.sleep( 1 );
        doNegativeDataTest( GlobalIds.PAGE_1, GlobalIds.BTN_PAGE_1, GlobalIds.ROLE_PAGE1_123, C789);
        driver.findElement( By.linkText( GlobalIds.PAGE_2 ) ).click();
        doActivateTest( GlobalIds.USER_123, GlobalIds.PAGE_2, GlobalIds.BTN_PAGE_2, GlobalIds.ROLE_PAGE2_123, GlobalIds.ROLE_PAGE1_123, C123, C789, true );
        TUtils.sleep( 1 );
        doNegativeDataTest( GlobalIds.PAGE_2, GlobalIds.BTN_PAGE_2, GlobalIds.ROLE_PAGE2_123, C456);
        TUtils.sleep( 1 );
        doNegativeDataTest( GlobalIds.PAGE_2, GlobalIds.BTN_PAGE_2, GlobalIds.ROLE_PAGE2_123, C789);
        driver.findElement( By.linkText( GlobalIds.PAGE_3 ) ).click();
        doActivateTest( GlobalIds.USER_123, GlobalIds.PAGE_3, GlobalIds.BTN_PAGE_3, GlobalIds.ROLE_PAGE3_123, GlobalIds.ROLE_PAGE2_123, C123, C789, true );
        TUtils.sleep( 1 );
        doNegativeDataTest( GlobalIds.PAGE_3, GlobalIds.BTN_PAGE_3, GlobalIds.ROLE_PAGE3_123, C456);
        TUtils.sleep( 1 );
        doNegativeDataTest( GlobalIds.PAGE_3, GlobalIds.BTN_PAGE_3, GlobalIds.ROLE_PAGE3_123, C789);
        logout( GlobalIds.USER_123 );

        // User 456, has access to all pages, Customer 456 data only:
        login( GlobalIds.USER_456, "password" );
        TUtils.sleep( 1 );
        doNegativeButtonTests( GlobalIds.PAGE_1, GlobalIds.USER_456, GlobalIds.BTN_PAGE_1 );
        doActivateTest( GlobalIds.USER_456, GlobalIds.PAGE_1, GlobalIds.BTN_PAGE_1, GlobalIds.ROLE_PAGE1_456, null, C456, C123, false );
        TUtils.sleep( 1 );
        doNegativeDataTest( GlobalIds.PAGE_1, GlobalIds.BTN_PAGE_1, GlobalIds.ROLE_PAGE1_456, C123);
        TUtils.sleep( 1 );
        doNegativeDataTest( GlobalIds.PAGE_1, GlobalIds.BTN_PAGE_1, GlobalIds.ROLE_PAGE1_456, C789);
        driver.findElement( By.linkText( GlobalIds.PAGE_2 ) ).click();
        doActivateTest( GlobalIds.USER_456, GlobalIds.PAGE_2, GlobalIds.BTN_PAGE_2, GlobalIds.ROLE_PAGE2_456, GlobalIds.ROLE_PAGE1_456, C456, C789, true );
        TUtils.sleep( 1 );
        doNegativeDataTest( GlobalIds.PAGE_2, GlobalIds.BTN_PAGE_2, GlobalIds.ROLE_PAGE2_456, C123);
        TUtils.sleep( 1 );
        doNegativeDataTest( GlobalIds.PAGE_2, GlobalIds.BTN_PAGE_2, GlobalIds.ROLE_PAGE2_456, C789);
        driver.findElement( By.linkText( GlobalIds.PAGE_3 ) ).click();
        doActivateTest( GlobalIds.USER_456, GlobalIds.PAGE_3, GlobalIds.BTN_PAGE_3, GlobalIds.ROLE_PAGE3_456, GlobalIds.ROLE_PAGE2_456, C456, C789, true );
        TUtils.sleep( 1 );
        doNegativeDataTest( GlobalIds.PAGE_3, GlobalIds.BTN_PAGE_3, GlobalIds.ROLE_PAGE3_456, C123);
        TUtils.sleep( 1 );
        doNegativeDataTest( GlobalIds.PAGE_3, GlobalIds.BTN_PAGE_3, GlobalIds.ROLE_PAGE3_456, C789);
        logout( GlobalIds.USER_456 );

        // User 789, has access to all pages, Customer 789 data only:
        login( GlobalIds.USER_789, "password" );
        TUtils.sleep( 1 );
        doNegativeButtonTests( GlobalIds.PAGE_1, GlobalIds.USER_789, GlobalIds.BTN_PAGE_1 );
        doActivateTest( GlobalIds.USER_789, GlobalIds.PAGE_1, GlobalIds.BTN_PAGE_1, GlobalIds.ROLE_PAGE1_789, null, C789, C123, false );
        TUtils.sleep( 1 );
        doNegativeDataTest( GlobalIds.PAGE_1, GlobalIds.BTN_PAGE_1, GlobalIds.ROLE_PAGE1_789, C123);
        TUtils.sleep( 1 );
        doNegativeDataTest( GlobalIds.PAGE_1, GlobalIds.BTN_PAGE_1, GlobalIds.ROLE_PAGE1_789, C456);
        driver.findElement( By.linkText( GlobalIds.PAGE_2 ) ).click();
        doActivateTest( GlobalIds.USER_789, GlobalIds.PAGE_2, GlobalIds.BTN_PAGE_2, GlobalIds.ROLE_PAGE2_789, GlobalIds.ROLE_PAGE1_789, C789, C456, true );
        TUtils.sleep( 1 );
        doNegativeDataTest( GlobalIds.PAGE_2, GlobalIds.BTN_PAGE_2, GlobalIds.ROLE_PAGE2_789, C123);
        TUtils.sleep( 1 );
        doNegativeDataTest( GlobalIds.PAGE_2, GlobalIds.BTN_PAGE_2, GlobalIds.ROLE_PAGE2_789, C456);
        driver.findElement( By.linkText( GlobalIds.PAGE_3 ) ).click();
        doActivateTest( GlobalIds.USER_789, GlobalIds.PAGE_3, GlobalIds.BTN_PAGE_3, GlobalIds.ROLE_PAGE3_789, GlobalIds.ROLE_PAGE2_789, C789, C123, true );
        TUtils.sleep( 1 );
        doNegativeDataTest( GlobalIds.PAGE_3, GlobalIds.BTN_PAGE_3, GlobalIds.ROLE_PAGE3_789, C123);
        TUtils.sleep( 1 );
        doNegativeDataTest( GlobalIds.PAGE_3, GlobalIds.BTN_PAGE_3, GlobalIds.ROLE_PAGE3_789, C456);
        logout( GlobalIds.USER_789 );

        // User 1 has access to Page 1, all customer data with DSD constraints applied::
        login( GlobalIds.USER_1, "password" );
        TUtils.sleep( 1 );
        doNegativeButtonTests( GlobalIds.PAGE_1, GlobalIds.USER_1, GlobalIds.BTN_PAGE_1 );
        doNegativeLinkTest( GlobalIds.PAGE_2, GlobalIds.USER_1 );
        doNegativeLinkTest( GlobalIds.PAGE_3, GlobalIds.USER_1 );
        activateRole( GlobalIds.ROLE_PAGE1_789 );
        doActivateTest( GlobalIds.USER_1, GlobalIds.PAGE_1, GlobalIds.BTN_PAGE_1, GlobalIds.ROLE_PAGE1_123, GlobalIds.ROLE_PAGE1_789, C123, C789, true );
        doActivateTest( GlobalIds.USER_1, GlobalIds.PAGE_1, GlobalIds.BTN_PAGE_1, GlobalIds.ROLE_PAGE1_456, GlobalIds.ROLE_PAGE1_123, C456, C123, true );
        doActivateTest( GlobalIds.USER_1, GlobalIds.PAGE_1, GlobalIds.BTN_PAGE_1, GlobalIds.ROLE_PAGE1_789, GlobalIds.ROLE_PAGE1_456, C789, C456, true );
        logout( GlobalIds.USER_1 );

        // User 1 123 has access to Page 1, Customer 123 only:
        login( GlobalIds.USER_1_123, "password" );
        TUtils.sleep( 1 );
        doNegativeButtonTests( GlobalIds.PAGE_1, GlobalIds.USER_1_123, GlobalIds.BTN_PAGE_1 );
        doNegativeLinkTest( GlobalIds.PAGE_2, GlobalIds.USER_1_123 );
        doNegativeLinkTest( GlobalIds.PAGE_3, GlobalIds.USER_1_123 );
        doActivateTest( GlobalIds.USER_1_123, GlobalIds.PAGE_1, GlobalIds.BTN_PAGE_1, GlobalIds.ROLE_PAGE1_123, null, C123, C789, false );
        logout( GlobalIds.USER_1_123 );

        // User 1 456 has access to Page 1, Customer 456 only::
        login( GlobalIds.USER_1_456, "password" );
        TUtils.sleep( 1 );
        doNegativeButtonTests( GlobalIds.PAGE_1, GlobalIds.USER_1_456, GlobalIds.BTN_PAGE_1 );
        doNegativeLinkTest( GlobalIds.PAGE_2, GlobalIds.USER_1_456 );
        doNegativeLinkTest( GlobalIds.PAGE_3, GlobalIds.USER_1_456 );
        doActivateTest( GlobalIds.USER_1_456, GlobalIds.PAGE_1, GlobalIds.BTN_PAGE_1, GlobalIds.ROLE_PAGE1_456, null, C456, C123, false );
        logout( GlobalIds.USER_1_456 );

        // User 1 789 has access to Page 1, Customer 789 only::
        login( GlobalIds.USER_1_789, "password" );
        TUtils.sleep( 1 );
        doNegativeButtonTests( GlobalIds.PAGE_1, GlobalIds.USER_1_789, GlobalIds.BTN_PAGE_1 );
        doNegativeLinkTest( GlobalIds.PAGE_2, GlobalIds.USER_1_789 );
        doNegativeLinkTest( GlobalIds.PAGE_3, GlobalIds.USER_1_789 );
        doActivateTest( GlobalIds.USER_1_789, GlobalIds.PAGE_1, GlobalIds.BTN_PAGE_1, GlobalIds.ROLE_PAGE1_789, null, C789, C123, false );
        logout( GlobalIds.USER_1_789 );

        // User 2 has access to Page 2, all customer data with DSD constraints applied::
        login( GlobalIds.USER_2, "password" );
        TUtils.sleep( 1 );
        doNegativeButtonTests( GlobalIds.PAGE_2, GlobalIds.USER_2, GlobalIds.BTN_PAGE_2 );
        doNegativeLinkTest( GlobalIds.PAGE_1, GlobalIds.USER_2 );
        doNegativeLinkTest( GlobalIds.PAGE_3, GlobalIds.USER_2 );
        activateRole( GlobalIds.ROLE_PAGE2_789 );
        doActivateTest( GlobalIds.USER_2, GlobalIds.PAGE_2, GlobalIds.BTN_PAGE_2, GlobalIds.ROLE_PAGE2_123, GlobalIds.ROLE_PAGE2_789, C123, C789, true );
        doActivateTest( GlobalIds.USER_2, GlobalIds.PAGE_2, GlobalIds.BTN_PAGE_2, GlobalIds.ROLE_PAGE2_456, GlobalIds.ROLE_PAGE2_123, C456, C123, true );
        doActivateTest( GlobalIds.USER_2, GlobalIds.PAGE_2, GlobalIds.BTN_PAGE_2, GlobalIds.ROLE_PAGE2_789, GlobalIds.ROLE_PAGE2_456, C789, C456, true );
        logout( GlobalIds.USER_2 );

        // User 2 123 has access to Page 2, Customer 123 only::
        login( GlobalIds.USER_2_123, "password" );
        TUtils.sleep( 1 );
        doNegativeButtonTests( GlobalIds.PAGE_2, GlobalIds.USER_2_123, GlobalIds.BTN_PAGE_2 );
        doNegativeLinkTest( GlobalIds.PAGE_1, GlobalIds.USER_2_123 );
        doNegativeLinkTest( GlobalIds.PAGE_3, GlobalIds.USER_2_123 );
        doActivateTest( GlobalIds.USER_2_123, GlobalIds.PAGE_2, GlobalIds.BTN_PAGE_2, GlobalIds.ROLE_PAGE2_123, null, C123, C789, false );
        logout( GlobalIds.USER_2_123 );

        // User 2 456 has access to Page 2, Customer 456 only::
        login( GlobalIds.USER_2_456, "password" );
        TUtils.sleep( 1 );
        doNegativeButtonTests( GlobalIds.PAGE_2, GlobalIds.USER_2_456, GlobalIds.BTN_PAGE_2 );
        doNegativeLinkTest( GlobalIds.PAGE_1, GlobalIds.USER_2_456 );
        doNegativeLinkTest( GlobalIds.PAGE_3, GlobalIds.USER_2_456 );
        doActivateTest( GlobalIds.USER_2_456, GlobalIds.PAGE_2, GlobalIds.BTN_PAGE_2, GlobalIds.ROLE_PAGE2_456, null, C456, C123, false );
        logout( GlobalIds.USER_2_456 );

        // User 2 789 has access to Page 2, Customer 789 only::
        login( GlobalIds.USER_2_789, "password" );
        TUtils.sleep( 1 );
        doNegativeButtonTests( GlobalIds.PAGE_2, GlobalIds.USER_2_789, GlobalIds.BTN_PAGE_2 );
        doNegativeLinkTest( GlobalIds.PAGE_1, GlobalIds.USER_2_789 );
        doNegativeLinkTest( GlobalIds.PAGE_3, GlobalIds.USER_2_789 );
        doActivateTest( GlobalIds.USER_2_789, GlobalIds.PAGE_2, GlobalIds.BTN_PAGE_2, GlobalIds.ROLE_PAGE2_789, null, C789, C123, false );
        logout( GlobalIds.USER_1_789 );

        // User 3 has access to Page 3, all customer data with DSD constraints applied::
        login( GlobalIds.USER_3, "password" );
        TUtils.sleep( 1 );
        doNegativeButtonTests( GlobalIds.PAGE_3, GlobalIds.USER_3, GlobalIds.BTN_PAGE_3 );
        doNegativeLinkTest( GlobalIds.PAGE_1, GlobalIds.USER_3 );
        doNegativeLinkTest( GlobalIds.PAGE_2, GlobalIds.USER_3 );
        activateRole( GlobalIds.ROLE_PAGE3_789 );
        doActivateTest( GlobalIds.USER_3, GlobalIds.PAGE_3, GlobalIds.BTN_PAGE_3, GlobalIds.ROLE_PAGE3_123, GlobalIds.ROLE_PAGE3_789, C123, C789, true );
        doActivateTest( GlobalIds.USER_3, GlobalIds.PAGE_3, GlobalIds.BTN_PAGE_3, GlobalIds.ROLE_PAGE3_456, GlobalIds.ROLE_PAGE3_123, C456, C123, true );
        doActivateTest( GlobalIds.USER_3, GlobalIds.PAGE_3, GlobalIds.BTN_PAGE_3, GlobalIds.ROLE_PAGE3_789, GlobalIds.ROLE_PAGE3_456, C789, C456, true );
        logout( GlobalIds.USER_3 );

        // User 3 123 has access to Page 3, Customer 123 only::
        login( GlobalIds.USER_3_123, "password" );
        TUtils.sleep( 1 );
        doNegativeButtonTests( GlobalIds.PAGE_3, GlobalIds.USER_3_123, GlobalIds.BTN_PAGE_3 );
        doNegativeLinkTest( GlobalIds.PAGE_1, GlobalIds.USER_3_123 );
        doNegativeLinkTest( GlobalIds.PAGE_2, GlobalIds.USER_3_123 );
        doActivateTest( GlobalIds.USER_3_123, GlobalIds.PAGE_3, GlobalIds.BTN_PAGE_3, GlobalIds.ROLE_PAGE3_123, null, C123, C789, false );
        logout( GlobalIds.USER_3_123 );

        // User 3 456 has access to Page 3, Customer 456 only::
        login( GlobalIds.USER_3_456, "password" );
        TUtils.sleep( 1 );
        doNegativeButtonTests( GlobalIds.PAGE_3, GlobalIds.USER_3_456, GlobalIds.BTN_PAGE_3 );
        doNegativeLinkTest( GlobalIds.PAGE_1, GlobalIds.USER_3_456 );
        doNegativeLinkTest( GlobalIds.PAGE_2, GlobalIds.USER_3_456 );
        doActivateTest( GlobalIds.USER_3_456, GlobalIds.PAGE_3, GlobalIds.BTN_PAGE_3, GlobalIds.ROLE_PAGE3_456, null, C456, C123, false );
        logout( GlobalIds.USER_3_456 );

        // User 3 789 has access to Page 3, Customer 789 only::
        login( GlobalIds.USER_3_789, "password" );
        TUtils.sleep( 1 );
        doNegativeButtonTests( GlobalIds.PAGE_3, GlobalIds.USER_3_789, GlobalIds.BTN_PAGE_3 );
        doNegativeLinkTest( GlobalIds.PAGE_1, GlobalIds.USER_3_789 );
        doNegativeLinkTest( GlobalIds.PAGE_2, GlobalIds.USER_3_789 );
        doActivateTest( GlobalIds.USER_3_789, GlobalIds.PAGE_3, GlobalIds.BTN_PAGE_3, GlobalIds.ROLE_PAGE3_789, null, C789, C123, false );
        logout( GlobalIds.USER_1_789 );
        
        // SuperUser has access to all pages and all customer data without restriction:
        login( GlobalIds.SUPER_USER, "password");
        TUtils.sleep( 1 );
        doPositiveButtonTests( GlobalIds.PAGE_1, GlobalIds.BTN_PAGE_1 );
        TUtils.sleep( 1 );
        doPositiveDataTest( GlobalIds.BTN_PAGE_1, GlobalIds.ROLE_SUPER, C123);
        TUtils.sleep( 1 );
        doPositiveDataTest( GlobalIds.BTN_PAGE_1, GlobalIds.ROLE_SUPER, C456);
        TUtils.sleep( 1 );
        doPositiveDataTest( GlobalIds.BTN_PAGE_1, GlobalIds.ROLE_SUPER, C789);
        TUtils.sleep( 1 );
        doPositiveButtonTests( GlobalIds.PAGE_2, GlobalIds.BTN_PAGE_2 );
        TUtils.sleep( 1 );
        doPositiveDataTest( GlobalIds.BTN_PAGE_2, GlobalIds.ROLE_SUPER, C123);
        TUtils.sleep( 1 );
        doPositiveDataTest( GlobalIds.BTN_PAGE_2, GlobalIds.ROLE_SUPER, C456);
        TUtils.sleep( 1 );
        doPositiveDataTest( GlobalIds.BTN_PAGE_2, GlobalIds.ROLE_SUPER, C789);
        TUtils.sleep( 1 );
        doPositiveButtonTests( GlobalIds.PAGE_3, GlobalIds.BTN_PAGE_3 );
        doPositiveDataTest( GlobalIds.BTN_PAGE_3, GlobalIds.ROLE_SUPER, C123);
        TUtils.sleep( 1 );
        doPositiveDataTest( GlobalIds.BTN_PAGE_3, GlobalIds.ROLE_SUPER, C456);
        TUtils.sleep( 1 );
        doPositiveDataTest( GlobalIds.BTN_PAGE_3, GlobalIds.ROLE_SUPER, C789);
        TUtils.sleep( 1 );
        logout( GlobalIds.SUPER_USER );

        // Poweruser has access to all pages, and all customer data with DSD constraints applied:
        login( GlobalIds.POWER_USER, "password");
        TUtils.sleep( 1 );
        driver.findElement( By.linkText( GlobalIds.PAGE_1 ) ).click();
        activateRole( GlobalIds.ROLE_PAGE3_789 );
        doActivateTest( GlobalIds.POWER_USER, GlobalIds.PAGE_1, GlobalIds.BTN_PAGE_1, GlobalIds.ROLE_PAGE1_123, GlobalIds.ROLE_PAGE3_789, C123, C456, true );
        driver.findElement( By.linkText( GlobalIds.PAGE_2 ) ).click();
        doActivateTest( GlobalIds.POWER_USER, GlobalIds.PAGE_2, GlobalIds.BTN_PAGE_2, GlobalIds.ROLE_PAGE2_123, GlobalIds.ROLE_PAGE1_123, C123, C456, true );
        driver.findElement( By.linkText( GlobalIds.PAGE_3 ) ).click();
        doActivateTest( GlobalIds.POWER_USER, GlobalIds.PAGE_3, GlobalIds.BTN_PAGE_3, GlobalIds.ROLE_PAGE3_123, GlobalIds.ROLE_PAGE2_123, C123, C456, true );
        driver.findElement( By.linkText( GlobalIds.PAGE_1 ) ).click();
        doActivateTest( GlobalIds.POWER_USER, GlobalIds.PAGE_1, GlobalIds.BTN_PAGE_1, GlobalIds.ROLE_PAGE1_456, GlobalIds.ROLE_PAGE3_123, C456, C789, true );
        driver.findElement( By.linkText( GlobalIds.PAGE_2 ) ).click();
        doActivateTest( GlobalIds.POWER_USER, GlobalIds.PAGE_2, GlobalIds.BTN_PAGE_2, GlobalIds.ROLE_PAGE2_456, GlobalIds.ROLE_PAGE1_456, C456, C789, true );
        driver.findElement( By.linkText( GlobalIds.PAGE_3 ) ).click();
        doActivateTest( GlobalIds.POWER_USER, GlobalIds.PAGE_3, GlobalIds.BTN_PAGE_3, GlobalIds.ROLE_PAGE3_456, GlobalIds.ROLE_PAGE2_456, C456, C789, true );
        driver.findElement( By.linkText( GlobalIds.PAGE_1 ) ).click();
        doActivateTest( GlobalIds.POWER_USER, GlobalIds.PAGE_1, GlobalIds.BTN_PAGE_1, GlobalIds.ROLE_PAGE1_789, GlobalIds.ROLE_PAGE3_456, C789, C123, true );
        driver.findElement( By.linkText( GlobalIds.PAGE_2 ) ).click();
        doActivateTest( GlobalIds.POWER_USER, GlobalIds.PAGE_2, GlobalIds.BTN_PAGE_2, GlobalIds.ROLE_PAGE2_789, GlobalIds.ROLE_PAGE1_789, C789, C123, true );
        driver.findElement( By.linkText( GlobalIds.PAGE_3 ) ).click();
        doActivateTest( GlobalIds.POWER_USER, GlobalIds.PAGE_3, GlobalIds.BTN_PAGE_3, GlobalIds.ROLE_PAGE3_789, GlobalIds.ROLE_PAGE2_789, C789, C123, true );
        logout( GlobalIds.POWER_USER );
    }

    private void doActivateTest( String userId, String page, String buttonPage, String activateRole, String deactivateRole, String authorizedData, String unauthorizedData, boolean isDeactivateTest )
    {
        info( "Do Role Activation Test role: " + activateRole );
        doNegativeButtonTest( userId, page, GlobalIds.ADD );

        if(isDeactivateTest)
        {
            // Now activate ROLE:
            activateRole(activateRole);
            // Make sure the pop-up is correct:
            if(!processError( GlobalIds.DSD_ERROR_MSG ))
                fail("doActivate Button Test 2 Failed: " + buttonPage);
            driver.findElement( By.linkText( page ) ).click();
            doNegativeButtonTest( userId, page, buttonPage );

            // Now deactivate ROLE_PAGE1:
            ( ( JavascriptExecutor ) driver ).executeScript( "$(document.getElementById('" + GlobalIds.ACTIVE_ROLES + "')).val('" + deactivateRole + "');" );
            driver.findElement( By.name( GlobalIds.ROLES_DEACTIVATE ) ).click();
            TUtils.sleep( 1 );
        }
        // Now active ROLE:
        activateRole(activateRole);

        //TUtils.sleep( 1 );

        // Click the buttons on page 2
        doPositiveButtonTests( null, buttonPage );
        TUtils.sleep( 1 );
        doNegativeDataTest(page, buttonPage, activateRole, unauthorizedData);
        driver.findElement( By.linkText( page ) ).click();
        TUtils.sleep( 1 );
        doPositiveDataTest(buttonPage, activateRole, authorizedData);
    }

    private void doPositiveDataTest(String buttonPage, String activateRole, String data)
    {
        info( "Postive Data test for role: " + activateRole + ", customer: " + data );
        driver.findElement( By.id( GlobalIds.CUSTOMER_EF_ID ) ).clear();
        driver.findElement( By.id( GlobalIds.CUSTOMER_EF_ID ) ).sendKeys( data );
        driver.findElement( By.name( buttonPage + "." + GlobalIds.SEARCH ) ).click();
    }

    private void doNegativeDataTest(String page, String buttonPage, String activateRole, String data)
    {
        info( "Negative Data test for role: " + activateRole + ", customer: " + data );
        driver.findElement( By.id( GlobalIds.CUSTOMER_EF_ID ) ).clear();
        driver.findElement( By.id( GlobalIds.CUSTOMER_EF_ID ) ).sendKeys( data );
        driver.findElement( By.name( buttonPage + "." + GlobalIds.SEARCH ) ).click();
        if(!processError( GlobalIds.AUTHZ_ERROR_MSG ))
            fail("doActivateTest Unauthorized data Test Failed: " + buttonPage + "." + GlobalIds.SEARCH);
        driver.findElement( By.linkText( page ) ).click();
    }

    private void doPositiveButtonTests( String linkName, String pageId )
    {
        info( "Postive Button test for " + linkName );
        if(linkName != null)
            driver.findElement( By.linkText( linkName ) ).click();
        TUtils.sleep( 1 );
        // Click the buttons on the page
        doPositiveButtonTest(pageId, GlobalIds.ADD, pageId + "." + GlobalIds.ADD);
        doPositiveButtonTest(pageId, GlobalIds.UPDATE, pageId + "." + GlobalIds.UPDATE);
        doPositiveButtonTest(pageId, GlobalIds.DELETE, pageId + "." + GlobalIds.DELETE);
        doPositiveButtonTest(pageId, GlobalIds.SEARCH, pageId + "." + GlobalIds.SEARCH);
    }

    private void doNegativeButtonTests( String linkName, String userId, String pageId )
    {
        info( "Negative Button test for user: " + userId + ", linkName: " + linkName );
        if(linkName != null)
            driver.findElement( By.linkText( linkName ) ).click();
        doNegativeButtonTest( userId, pageId, GlobalIds.ADD );
        doNegativeButtonTest( userId, pageId, GlobalIds.UPDATE );
        doNegativeButtonTest( userId, pageId, GlobalIds.DELETE );
        doNegativeButtonTest( userId, pageId, GlobalIds.SEARCH );
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

    private boolean processError(String text)
    {
        boolean textFound = false;
        WebElement element = driver.findElement(By.xpath("//*[@id=\"body\"]/h3/span"));
        String szValue = element.getText();
        if(szValue.contains( text ))
            textFound = true;
        return textFound;
    }

    private void activateRole(String roleName)
    {
        info("Activate test for " + roleName);
        ( ( JavascriptExecutor ) driver ).executeScript( "$(document.getElementById('" + GlobalIds.INACTIVE_ROLES + "')).val('" + roleName + "');" );
        driver.findElement( By.name( GlobalIds.ROLES_ACTIVATE ) ).click();
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
        //if(!processError(alertText))
        //    fail("Button Test Failed: " + pageId + "." + buttonId);
    }

    private void doNegativeButtonTest( String userId, String pageId, String buttonId )
    {
        info("Negative button test for " + buttonId + ", and " + userId);
        try
        {
            driver.findElement( By.name( pageId + "." + buttonId ) ).click();
            fail("Negative Button Test Failed: " + pageId + "." + GlobalIds.ADD);
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
            fail("Spring Security Test Failed URL: " + unauthorizedUrl + "." + GlobalIds.ADD);
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
        driver.findElement( By.id( GlobalIds.USER_ID ) ).clear();
        driver.findElement( By.id( GlobalIds.USER_ID ) ).sendKeys( userId );
        driver.findElement( By.id( GlobalIds.PSWD_FIELD ) ).clear();
        driver.findElement( By.id( GlobalIds.PSWD_FIELD ) ).sendKeys( password );
        driver.findElement( By.name( GlobalIds.LOGIN ) ).click();
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

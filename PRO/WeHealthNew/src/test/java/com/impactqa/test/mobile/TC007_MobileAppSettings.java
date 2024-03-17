package com.impactqa.test.mobile;

import com.impactqa.listeners.TestAllureListener;
import com.impactqa.listeners.TestNGExecutionLister;
import com.impactqa.page.mobile.*;
import com.impactqa.page.web_new.LoginPage;
import com.impactqa.page.web_new.MenuPageWeb;
import com.impactqa.page.web_new.MobileAppSettingsPage;
import com.impactqa.utilities.DriverProvider;
import com.impactqa.utilities.ExcelUtil;
import com.impactqa.utilities.FrameworkConfig;
import com.impactqa.utilities.PageObjectRepoHelper;
import io.qameta.allure.Description;
import io.qameta.allure.Story;
import io.restassured.RestAssured;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Listeners;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import java.net.MalformedURLException;
import java.util.*;

@Listeners({TestAllureListener.class, TestNGExecutionLister.class})
public class TC007_MobileAppSettings extends BaseTestMobile2{

    WebDriver webBrowser;
    MobileAppSettingsPage mobileAppSettingsPage;

    @BeforeClass
    @Parameters({"dataID"})
    @Description("Read test data with testID {0}")
    public void getTestData(String dataID)
    {
        ExcelUtil excel = new ExcelUtil();
        excel.setWorkbook(FrameworkConfig.getStringEnvProperty("TestDataFileLocation"),
                FrameworkConfig.getStringEnvProperty("TestDataSheetName"));

        testDataMap = excel.getRowDataMtahcingDataId(dataID);
        if(testDataMap.size()<1)
            Assert.fail("dataID '"+dataID+"' is valid the excel sheet. please check the test data sheet");
    }


    @Test( priority = 1, description = "Login To The Application")
    @Story("Mobile App Settings")
    @Description("Login To The Application")
    public void loginToTheApplication()
    {
        webBrowser = DriverProvider.createNewBrowserSession("chrome");
        LoginPage loginPage = new LoginPage(webBrowser);
        loginPage.doLogin(testDataMap.get("WebUserName"), testDataMap.get("WebPassword"));
    }

    @Test( priority = 2, dependsOnMethods = {"loginToTheApplication"}, description = "Set Mobile App Settings For The Region")
    @Story("Mobile App Settings")
    @Description("Set Mobile App Settings For The Region")
    public void setMobileAppSettingsForTheRegion()
    {
        MenuPageWeb menuPageWeb = new MenuPageWeb(webBrowser);
        menuPageWeb.navigateToMobileAppSettings();

        mobileAppSettingsPage = new MobileAppSettingsPage(webBrowser);
        mobileAppSettingsPage.selectRegion(testDataMap.get("Region"));
      //  mobileAppSettingsPage.setNoSignificantExposureMessages(testDataMap.get("Region"));
        mobileAppSettingsPage.saveAndSubmitChanges();
        mobileAppSettingsPage.getSeleniumUtils().attachScreenShotToTheReport("Screenshot");
        mobileAppSettingsPage.getSeleniumUtils().sleep(60000);
        if(webBrowser!=null)
            webBrowser.quit();

    }

    @Test(priority = 3, dependsOnMethods = {"setMobileAppSettingsForTheRegion"}, description = "Verify The Changes In The Json URL")
    @Story("Mobile App Settings")
    @Description("Verify The Changes In The Json URL")
    public void verifyTheChangesInTheJsonURL() throws MalformedURLException{

        String url = "";
        if(testDataMap.get("BuildType").contains("Arizona"))
            url = "https://exposure.wehealth.org/config/dev/gov.azdhs.covidwatch.json";
        else
            url = "https://exposure.wehealth.org/config/dev/org.wehealth.exposure.json";

        List<Map<String, String>> steps = RestAssured.given()
                .get(url)
                .getBody().jsonPath()
                .get("findAll { it.name=='" + testDataMap.get("Region") + "' }[0].nextStepsNoSignificantExposure");
        String actualStep1 = steps.get(0).get("description");
        String expectedStep1 = (String) mobileAppSettingsPage.changesMade.get("nextStepsSignificantExposure[0].description");
        Assert.assertTrue(expectedStep1.equals(actualStep1)
                        ,"Step 1 - NoSignificantExposureDescription changes not reflected." +
                        "\nExpected: "+expectedStep1+
                        "\nActual: "+actualStep1+"\n");
    }

    @Test(priority = 4, dependsOnMethods = {"verifyTheChangesInTheJsonURL"}, description = "Uninstall All App in Device")
    @Story("Mobile App Settings")
    @Description("Uninstall All Apps in Device")
    public void unInstallAllAppsDevice1() throws MalformedURLException {
        openMobileSessionDeviceSettings(testDataMap.get("MobileSessionID1"));
        DeviceSettingsAppScreen deviceSettingsAppScreen = new DeviceSettingsAppScreen(driver,platform);
        deviceSettingsAppScreen.turnOffExposureNotificationFromIOSDeviceSettings();
        deviceSettingsAppScreen.uninstallAllApps();
    }

    @Test(priority = 5, dependsOnMethods = {"unInstallAllAppsDevice1"}, description = "Install Main App in Device")
    @Story("Mobile App Settings")
    @Description("Install Main App in Device")
    public void installAppDevice1() throws MalformedURLException {
        teardownDriverInstance();
        openMobileSession(testDataMap.get("MobileSessionID1"));
    }

    @Test(priority = 6, dependsOnMethods = {"installAppDevice1"}, description = "Fill The Initial Screens If Required in Device")
    @Story("Mobile App Settings")
    @Description("Fill The Initial Screens If Required in Device1")
    public void fillTheInitialScreensDevice1()
    {
        GettingStarted gettingStarted = new GettingStarted(driver, platform);
        Dimension size = gettingStarted.getAppiumUtils().getScreenSize();
        System.out.println("ScreenSize: "+size);
        if(gettingStarted.isGettingStartedPageDisplayed()) {
            gettingStarted.clickGettingStartedButton();

            AppExplanationPage appExplanationPage = new AppExplanationPage(driver, platform);
//        appExplanationPage.verifyAppExplanationPageDisplayed();
            appExplanationPage.clickHowItWorksButton();

            HowItWorks howItWorks = new HowItWorks(driver, platform);
//        howItWorks.verifyHowItWorksPageDisplayed();
            howItWorks.clickNextButton();
            howItWorks.clickNextButton();
            howItWorks.clickNextButton();
            howItWorks.clickContinueSetupButton();

            EnableExposureScreen enableExposureScreen = new EnableExposureScreen(driver, platform);
            enableExposureScreen.verifyEnableExposurePageDisplayed();
            enableExposureScreen.clickEnableButton();
            enableExposureScreen.verifyEnableExposurePopupDisplayed();
            enableExposureScreen.clickTurnOnButton();

            if (platform == PageObjectRepoHelper.PLATFORM.IOS) {

                IOSPushNotifications iosPushNotifications = new IOSPushNotifications(driver, platform);
                iosPushNotifications.verifyPushNotificationPageDisplayed();
                iosPushNotifications.clickOnEnableButton();
                iosPushNotifications.verifyPushNotificationPopupDisplayed();
                iosPushNotifications.clickAllowButton();
            }

            SettingsPage settingsPage = new SettingsPage(driver, platform);
            settingsPage.verifySettingsPageDisplayed();
            settingsPage.selectRegion(testDataMap.get("BuildType"), testDataMap.get("Region"));
            settingsPage.notifyLowExposureCheckbox(testDataMap.get("NotifyLowExposures"));
            settingsPage.clickSaveButton();
        }
    }



    @Test(priority = 7, dependsOnMethods = {"fillTheInitialScreensDevice1"}, description = "Verify The Changes In The Mobile App")
    @Story("Mobile App Settings")
    @Description("Verify The Changes In The Mobile App")
    public void verifyTheChangesInTheMobileApp() throws MalformedURLException{

//        openMobileSession(testDataMap.get("MobileSessionID1"));
        HomePage homePage = new HomePage(driver, platform);
        homePage.verifyHomePageDisplayed();
        homePage.verifyRiskLevelMessage("No Exposure Detected in the past 14 days");

        homePage.VerifyNextSteps(testDataMap.get("BuildType"), testDataMap.get("Region"));


    }
}

package com.impactqa.test.mobile;

import com.impactqa.listeners.TestAllureListener;
import com.impactqa.listeners.TestNGExecutionLister;
import com.impactqa.page.mobile.*;
import com.impactqa.page.web.IssueCodePage;
import com.impactqa.page.web.LoginPage;
import com.impactqa.page.web.SelectRealmPage;
import com.impactqa.utilities.DriverProvider;
import com.impactqa.utilities.ExcelUtil;
import com.impactqa.utilities.FrameworkConfig;
import com.impactqa.utilities.PageObjectRepoHelper;
import io.qameta.allure.Description;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Listeners;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import java.net.MalformedURLException;
import java.util.Calendar;
import java.util.Date;

@Listeners({TestAllureListener.class, TestNGExecutionLister.class})
public class Archive_TC006_CheckExposureNotification_New extends BaseTestMobile2{

    Calendar submitDate = Calendar.getInstance();
    Calendar calTestDate;
    Calendar calSymptomsDate;
    String fileName= "keys_"+(new Date().getTime())+".zip";
    String localFilepath;

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


    @Test(priority = 1, description = "Uninstall All App in Device1")
    @Description("Uninstall All Apps in Device1")
    public void unInstallAllAppsDevice1() throws MalformedURLException {
        openMobileSessionDeviceSettings(testDataMap.get("MobileSessionID1"));
        DeviceSettingsAppScreen deviceSettingsAppScreen = new DeviceSettingsAppScreen(driver,platform);
        deviceSettingsAppScreen.turnOffExposureNotificationFromIOSDeviceSettings();
        deviceSettingsAppScreen.uninstallAllApps();
    }

    @Test(priority = 2, dependsOnMethods = {"unInstallAllAppsDevice1"}, description = "Uninstall All App in Device2")
    @Description("Uninstall All Apps in Device2")
    public void unInstallAllAppsDevice2() throws MalformedURLException {
        teardownDriverInstance();
        openMobileSessionDeviceSettings(testDataMap.get("MobileSessionID2"));
        DeviceSettingsAppScreen deviceSettingsAppScreen = new DeviceSettingsAppScreen(driver,platform);
        deviceSettingsAppScreen.turnOffExposureNotificationFromIOSDeviceSettings();
        deviceSettingsAppScreen.uninstallAllApps();
    }

    @Test(priority = 3, dependsOnMethods = {"unInstallAllAppsDevice2"}, description = "Install Main App in Device1")
    @Description("Install Main App in Device1")
    public void installAppDevice1() throws MalformedURLException {
        teardownDriverInstance();
        openMobileSession(testDataMap.get("MobileSessionID1"));
    }


    @Test(priority = 4, dependsOnMethods = {"installAppDevice1"}, description = "Fill The Initial Screens If Required in Device1")
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



    @Test(priority = 5, dependsOnMethods = {"fillTheInitialScreensDevice1"}, description = "Install Main App in Device2")
    @Description("Install Main App in Device2")
    public void installAppDevice2() throws MalformedURLException {
        teardownDriverInstance();
        openMobileSession(testDataMap.get("MobileSessionID2"));
    }


    @Test(priority = 6, dependsOnMethods = {"installAppDevice2"}, description = "Fill The Initial Screens If Required in Device2")
    @Description("Fill The Initial Screens If Required in Device2")
    public void fillTheInitialScreensDevice2()
    {
        GettingStarted gettingStarted = new GettingStarted(driver, platform);
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
//        settingsPage.selectRegion("");
            settingsPage.clickSaveButton();
        }
    }

    @Test(priority = 7, dependsOnMethods = {"fillTheInitialScreensDevice2"}, description = "Wait For Exposures Time")
    @Description("Wait For Exposures Time")
    public void waitForExposuresTime()
    {
        teardownDriverInstance();
        try{
            Integer exposWaitTime = Integer.valueOf(testDataMap.get("ExposuresWaitTime"));
            Thread.sleep(exposWaitTime*60*1000);
        }catch (NumberFormatException e1){

        }catch (InterruptedException e)
        {

        }
    }

    @Test(priority = 8, dependsOnMethods = {"waitForExposuresTime"}, description = "Export Keys From Device 2")
    @Description("Export Keys From Device 2")
//    @Test
    public void exportKeysFromDevice2() throws MalformedURLException {
        openMobileSession(testDataMap.get("MobileSessionID2"));


        HomePage homePage = new HomePage(driver, platform);
        homePage.clickMenuButton();
        MenuPage menuPage = new MenuPage(driver, platform);
        menuPage.clickExportExposureKeysMenuButton();

        ExportExposureKeys exportExposureKeys = new ExportExposureKeys(driver, platform);
        localFilepath = exportExposureKeys.exportKeysWithDaysSinceSymptomsTextbox(testDataMap.get("BuildType"), testDataMap.get("SymptomsDay"), fileName);

    }



    @Test(priority = 9, dependsOnMethods = {"exportKeysFromDevice2"}, description = "Import Keys From Device 1")
//    @Test
    @Description("Import Keys From Device 1")
    public void importKeysFromDevice1() throws MalformedURLException {
        openMobileSession(testDataMap.get("MobileSessionID1"));
        HomePage homePage = new HomePage(driver, platform);
        homePage.verifyHomePageDisplayed();
        homePage.clickMenuButton();

        MenuPage menuPage = new MenuPage(driver, platform);
        menuPage.verifyMenuPageDisplayed();
        menuPage.clickImportExposureKeysMenuButton();

        ImportExposureKeys importExposureKeys = new ImportExposureKeys(driver, platform);
        importExposureKeys.verifyImportExposureKeysPageDisplayed();
        importExposureKeys.importKeys(localFilepath);

        if(platform== PageObjectRepoHelper.PLATFORM.IOS)
            menuPage.clickCloseButton();

        homePage.verifyHomePageDisplayed();
        if("High".equals(testDataMap.get("ExpectedExposureLevel")))
            homePage.verifyRiskLevelMessage("High Exposure in the past 14 days");
        else if("Low".equals(testDataMap.get("ExpectedExposureLevel")))
            homePage.verifyRiskLevelMessage("Low Exposure in the past 14 days");
        else
            homePage.verifyRiskLevelMessage("No Exposure Detected in the past 14 days");

        homePage.clickMenuButton();
        menuPage.verifyMenuPageDisplayed();
        menuPage.clickMyExposureMenuButton();
    }

}

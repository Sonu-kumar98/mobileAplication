package com.impactqa.test.mobile;
import com.impactqa.listeners.TestAllureListener;
import com.impactqa.listeners.TestNGExecutionLister;
import com.impactqa.page.mobile.*;
import com.impactqa.utilities.ExcelUtil;
import com.impactqa.utilities.FrameworkConfig;
import com.impactqa.utilities.PageObjectRepoHelper;
import io.qameta.allure.Description;
import org.openqa.selenium.Dimension;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Listeners;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import java.net.MalformedURLException;
import java.util.Calendar;
import java.util.Date;
@Listeners({TestAllureListener.class, TestNGExecutionLister.class})
public class TC008_LanguageSetting_Low_Exposure extends BaseTestMobile2 {
    Calendar submitDate = Calendar.getInstance();
    Calendar calTestDate;
    Calendar calSymptomsDate;
    String fileName= "keys_"+(new Date().getTime())+".zip";
    String localFilepath;

    @BeforeClass(description = "Read test data with testID")
    @Parameters({"dataID"})
    public void getTestData(String dataID)
    {
        ExcelUtil excel = new ExcelUtil();
        excel.setWorkbook(FrameworkConfig.getStringEnvProperty("TestDataFileLocation"),
                FrameworkConfig.getStringEnvProperty("TestDataSheetName"));

        testDataMap = excel.getRowDataMtahcingDataId(dataID);
        System.out.println(""+testDataMap);
        if(testDataMap.size()<1)
            Assert.fail("dataID '"+dataID+"' is valid the excel sheet. please check the test data sheet");
    }
    ///*
    @Test(priority = 1, description = "Uninstall All App in Device1")
    @Description("Uninstall All App" +
            "s in Device1")
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

    }


    @Test(priority = 4, dependsOnMethods = {"installAppDevice1"}, description = "Fill The Initial Screens If Required in Device1")
    @Description("Fill The Initial Screens If Required in Device1")
    public void fillTheInitialScreensDevice1() throws MalformedURLException {
        openMobileSession(testDataMap.get("MobileSessionID1"));
        GettingStarted gettingStarted = new GettingStarted(driver, platform);
        Dimension size = gettingStarted.getAppiumUtils().getScreenSize();
        System.out.println("ScreenSize: "+size);
        if(gettingStarted.isGettingStartedPageDisplayed()) {
            gettingStarted.clickGettingStartedButton();

            AppExplanationPage appExplanationPage = new AppExplanationPage(driver, platform);
//          appExplanationPage.verifyAppExplanationPageDisplayed();
            appExplanationPage.clickHowItWorksButton();

            HowItWorks howItWorks = new HowItWorks(driver, platform);
//          howItWorks.verifyHowItWorksPageDisplayed();
            howItWorks.clickNextButton();
            howItWorks.clickNextButton();
            howItWorks.clickNextButton();
            howItWorks.clickContinueSetupButton();

            EnableExposureScreen enableExposureScreen = new EnableExposureScreen(driver, platform);
            //enableExposureScreen.verifyEnableExposurePageDisplayed();
            enableExposureScreen.clickEnableButton();
            // enableExposureScreen.verifyEnableExposurePopupDisplayed();
            enableExposureScreen.clickTurnOnButton();

            if (platform == PageObjectRepoHelper.PLATFORM.IOS) {

                IOSPushNotifications iosPushNotifications = new IOSPushNotifications(driver, platform);
                iosPushNotifications.verifyPushNotificationPageDisplayed();
                iosPushNotifications.clickOnEnableButton();
                iosPushNotifications.verifyPushNotificationPopupDisplayed();
                iosPushNotifications.clickAllowButton();
            }

            SettingsPage settingsPage = new SettingsPage(driver, platform);
            // settingsPage.verifySettingsPageDisplayed();
            // settingsPage.selectRegion(testDataMap.get("BuildType"), testDataMap.get("Region"));
           // settingsPage.notifyLowExposureCheckbox(testDataMap.get("NotifyLowExposures"));
            settingsPage.getAppiumUtils().sleepForMiliseconds(10000);
            settingsPage.clickSaveButton();
            SetupPage set = new SetupPage(driver,platform);
            //set.verifySetupCompletePageDisplayed();
            //  set.verifyTextOnSetupCompletePage();
            set.clickOkButton();
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
//         howItWorks.verifyHowItWorksPageDisplayed();
            howItWorks.clickNextButton();
            howItWorks.clickNextButton();
            howItWorks.clickNextButton();
            howItWorks.clickContinueSetupButton();

            EnableExposureScreen enableExposureScreen = new EnableExposureScreen(driver, platform);
            //enableExposureScreen.verifyEnableExposurePageDisplayed();
            enableExposureScreen.clickEnableButton();
            //enableExposureScreen.verifyEnableExposurePopupDisplayed();
            enableExposureScreen.clickTurnOnButton();

            if (platform == PageObjectRepoHelper.PLATFORM.IOS) {

                IOSPushNotifications iosPushNotifications = new IOSPushNotifications(driver, platform);
                iosPushNotifications.verifyPushNotificationPageDisplayed();
                iosPushNotifications.clickOnEnableButton();
                iosPushNotifications.verifyPushNotificationPopupDisplayed();
                iosPushNotifications.clickAllowButton();
            }

            SettingsPage settingsPage = new SettingsPage(driver, platform);
            //settingsPage.verifySettingsPageDisplayed();
            // settingsPage.selectRegion(testDataMap.get("BuildType"), testDataMap.get("Region"));
           // settingsPage.notifyLowExposureCheckbox(testDataMap.get("NotifyLowExposures"));
            settingsPage.clickSaveButton();
            SetupPage set = new SetupPage(driver,platform);
            //set.verifySetupCompletePageDisplayed();
            //  set.verifyTextOnSetupCompletePage();
            set.clickOkButton();
        }
    }

    @Test(priority = 7, dependsOnMethods = {"fillTheInitialScreensDevice2"}, description = "Wait For Exposures Time")
    @Description("Wait For Exposures Time")
    public void  waitForExposuresTime()
    {
        teardownDriverInstance();
        try{
            Double exposWaitTime = Double.valueOf(testDataMap.get("ExposuresWaitTime"));
            Thread.sleep((long)(exposWaitTime*60.0*1000.0));
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
        //  homePage.verifyHomePageDisplayed();
        homePage.clickMenuButton();
        MenuPage menuPage = new MenuPage(driver, platform);
        // menuPage.verifyMenuPageDisplayed();
        menuPage.clickExportExposureKeysMenuButton();
    //    menuPage.getAppiumUtils().snap("26-export_in_devices_two");

        ExportExposureKeys exportExposureKeys = new ExportExposureKeys(driver, platform);
        localFilepath = exportExposureKeys.
                exportKeysWithDaysSinceSymptomsTextbox(testDataMap.get("BuildType"), testDataMap.get("SymptomsDay"), fileName);

    }

    @Test(priority = 9, dependsOnMethods = {"exportKeysFromDevice2"}, description = "Import Keys From Device 1")
//    @Test
    @Description("Import Keys From Device 1")
    public void importKeysFromDevice1() throws MalformedURLException {
        //  teardownDriverInstance();
        openMobileSession(testDataMap.get("MobileSessionID1"));
        HomePage homePage = new HomePage(driver, platform);
        //homePage.verifyHomePageDisplayed();
        homePage.getAppiumUtils().snap("27-import_in_devices");
        homePage.pushLocalFileToDownloadsDirectory(localFilepath);
        homePage.clickMenuButton();

        MenuPage menuPage = new MenuPage(driver, platform);
        // menuPage.verifyMenuPageDisplayed();
        menuPage.clickImportExposureKeysMenuButton();


        ImportExposureKeys importExposureKeys = new ImportExposureKeys(driver, platform);
        // importExposureKeys.verifyImportExposureKeysPageDisplayed();
        importExposureKeys.importKeys(localFilepath);

        importExposureKeys.getAppiumUtils().sleepForMiliseconds(10000);
//        if(platform== PageObjectRepoHelper.PLATFORM.IOS)
        menuPage.clickCloseButton();
    }

    @Test(priority = 10, dependsOnMethods = {"importKeysFromDevice1"}, description = "Verify Exposure Level In Device1")
//    @Test
    @Description("Verify Exposure Level In Device1")
    public void verifyExposureLevelInDevice1() throws MalformedURLException{

        openMobileSession(testDataMap.get("MobileSessionID1"));
        HomePage homePage = new HomePage(driver, platform);
        homePage.getAppiumUtils().sleepForMiliseconds(5000);
        homePage.getAppiumUtils().snap("28-Low_Exposure_ScreenShort");
        //homePage.verifyHomePageDisplayed();
        if("High".equals(testDataMap.get("ExpectedExposureLevel")))
            if(platform== PageObjectRepoHelper.PLATFORM.ANDROID)
                homePage.verifyRiskLevelMessage("High Exposure in the past 14 days");
              //  homePage.verifyRiskLevelMessage("Exposición significativa detectada en los últimos 14 días");
            else
                homePage.verifyRiskLevelMessage("High Exposure");
        else if("Low".equals(testDataMap.get("ExpectedExposureLevel")))
            if(platform== PageObjectRepoHelper.PLATFORM.ANDROID)

               homePage.verifyRiskLevelMessage("Low Exposure in the past 14 days");
             // homePage.verifyRiskLevelMessage("Baja exposición en los últimos 14 días");

            else
                homePage.verifyRiskLevelMessage("Low Exposure");


        else
        if(platform== PageObjectRepoHelper.PLATFORM.ANDROID)
            homePage.verifyRiskLevelMessage("No Exposure Detected in the past 14 days");
        else
            homePage.verifyRiskLevelMessage("No Exposure Detected");

        // homePage.VerifyNextSteps(testDataMap.get("BuildType"), testDataMap.get("Region"));

        homePage.clickMenuButton();

        MenuPage menuPage = new MenuPage(driver, platform);
        // menuPage.verifyMenuPageDisplayed();
        menuPage.clickMyExposureMenuButton();

        MyExposures myExposures = new MyExposures(driver,platform);
        if("High".equals(testDataMap.get("ExpectedExposureLevel"))) {
            myExposures.verifyHighExposureMessageDisplayedInSummaryPage(submitDate);
            myExposures.getAppiumUtils().snap("29-Summary_Tab");
            myExposures.verifyHighExposureMessageDisplayedInDailyPage(submitDate);
            myExposures.getAppiumUtils().snap("30-Daily_Tab");
        }
        else if("Low".equals(testDataMap.get("ExpectedExposureLevel"))){

            myExposures.verifyLowExposureMessageDisplayedInSummaryPage(submitDate);
            myExposures.getAppiumUtils().snap("29-Summary_Tab");
            myExposures.getAppiumUtils().sleepForMiliseconds(5000);
            myExposures.verifyLowExposureMessageDisplayedInDailyPage(submitDate);
            myExposures.getAppiumUtils().snap("30-Daily_Tab");

        }
    }
}

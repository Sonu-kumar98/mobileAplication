package com.impactqa.test.mobile;

import com.impactqa.listeners.TestAllureListener;
import com.impactqa.listeners.TestNGExecutionLister;
import com.impactqa.page.mobile.*;
import com.impactqa.utilities.ExcelUtil;
import com.impactqa.utilities.FrameworkConfig;
import com.impactqa.utilities.PageObjectRepoHelper;
import io.qameta.allure.Allure;
import io.qameta.allure.Description;
import io.qameta.allure.model.Status;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Listeners;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import java.net.MalformedURLException;

@Listeners({TestAllureListener.class, TestNGExecutionLister.class})
public class TC003_FreshInstallNegativeCase extends BaseTestMobile2{

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

    @Test(priority = 1, description = "Uninstall All App")
    @Description("Uninstall All Apps")
    public void unInstallAllApp() throws MalformedURLException {
        System.out.println("Mobile session id"+testDataMap.get("MobileSessionID1"));
        openMobileSessionDeviceSettings(testDataMap.get("MobileSessionID1"));
        DeviceSettingsAppScreen deviceSettingsAppScreen = new DeviceSettingsAppScreen(driver,platform);
        deviceSettingsAppScreen.turnOffExposureNotificationFromIOSDeviceSettings();
        deviceSettingsAppScreen.uninstallAllApps();
    }

    @Test(priority = 2, dependsOnMethods = {"unInstallAllApp"}, description = "Install Main App")
    @Description("Install Main App")
    public void installApp() throws MalformedURLException {
        teardownDriverInstance();
        openMobileSession(testDataMap.get("MobileSessionID1"));
    }

    @Test(priority = 3, dependsOnMethods = {"installApp"}, description = "Verify Getting Started Page")
    @Description("Verify Getting Started Page")
    public void verifyGettingStartedPage() throws MalformedURLException {
        GettingStarted gettingStarted = new GettingStarted(driver, platform);
      //  gettingStarted.verifyGettingStartedPageDisplayed();
        gettingStarted.clickGettingStartedButton();
    }

    @Test(priority = 4, dependsOnMethods = {"verifyGettingStartedPage"}, description = "Verify App Introduction Page")
    @Description("Verify App Introduction Page")
    public void verifyIntroductionPage()
    {
        AppExplanationPage appExplanationPage = new AppExplanationPage(driver, platform);
        //appExplanationPage.verifyAppExplanationPageDisplayed();
      //  appExplanationPage.verifyTextHowToWorkPage();
        appExplanationPage.clickHowItWorksButton();


    }

    @Test(priority = 5, dependsOnMethods = {"verifyIntroductionPage"}, description = "Verify How It Works")
    @Description("Verify How It Works")
    public void verifyHowItWorksPage()
    {
        HowItWorks howItWorks = new HowItWorks(driver, platform);
//      howItWorks.verifyHowItWorksPageDisplayed();
        howItWorks.clickNextButton();
        howItWorks.clickNextButton();
        howItWorks.clickNextButton();
        howItWorks.clickContinueSetupButton();
    }

    @Test(priority = 6, dependsOnMethods = {"verifyHowItWorksPage"}, description = "Don't Allow Exposure Notification")
    @Description("Don't Allow Exposure Notification")
    public void dontAllowExposureNotification()
    {
        EnableExposureScreen enableExposureScreen = new EnableExposureScreen(driver, platform);
        //enableExposureScreen.verifyEnableExposurePageDisplayed();
        enableExposureScreen.clickNotNowButton();
      //  enableExposureScreen.clickEnableButton();
    }

    @Test(priority = 7, dependsOnMethods = {"dontAllowExposureNotification"}, description = "Don't Allow Push Notification")
    @Description("Don't Allow Push Notification")
    public void dontAllowPushNotification()
    {
        if(platform== PageObjectRepoHelper.PLATFORM.IOS) {
            IOSPushNotifications iosPushNotifications = new IOSPushNotifications(driver, platform);
            iosPushNotifications.verifyPushNotificationPageDisplayed();
            iosPushNotifications.clickOnNotNowButton();
        }
    }

//    @Test(priority = 8, dependsOnMethods = {"dontAllowPushNotification"}, description = "Verify Settings Screen")
//    @Description("Verify Settings Screen")
//    public void verifySettingsPage()
//    {
//        SettingsPage settingsPage = new SettingsPage(driver, platform);
//        //settingsPage.verifySettingsPageDisplayed();
//       // settingsPage.selectRegion(testDataMap.get("BuildType"), testDataMap.get("Region"));
//        //settingsPage.verifyExposureNotificationCheckboxStatus("unchecked");
//        settingsPage.clickSaveButton();
//    }
//
//
//    // Made by Sonu Kumar
//    // 06-03-21
//    @Test(priority = 9, dependsOnMethods = {"dontAllowPushNotification"}, description = "Verify SetupComplete page")
//    @Description("Verify Setup Complete Page")
//    public void verifySetupCompletePage()
//    {
//        SetupPage set = new SetupPage(driver,platform);
//      //  set.verifySetupCompletePageDisplayed();
//       // set.verifyTextOnSetupCompletePage();
//        set.clickOkButton();
//
//    }
//
//    @Test(priority = 10, dependsOnMethods = {"verifySettingsPage"}, description = "Verify Home Screen")
//    @Description("Verify Error message in Home Screen")
//    public void verifyHomePage()
//    {
//
//        HomePage homePage = new HomePage(driver, platform);
//       // homePage.verifyHomePageDisplayed();
//        homePage.getAppiumUtils().snap("26-Exposure-Notification-off");
//
//        if(platform== PageObjectRepoHelper.PLATFORM.ANDROID)
//            homePage.verifyRiskLevelMessage("No Exposure Detected in the past 14 days");
//        else
//            homePage.verifyRiskLevelMessage("No Exposure Detected");
//
//        homePage.verifyExposureNotificationNotEnabledErrorMessageDisplayed();
//
//        if(platform== PageObjectRepoHelper.PLATFORM.IOS)
//            homePage.verifyPushNotificationNotEnabledErrorMessageDisplayed_IOS();
//
//    }
//
//    @Test(priority = 11, dependsOnMethods = {"verifyHomePage"}, description = "Verify Enable Exposure Screen From Home Page Error Message")
//    @Description("Verify Enable Exposure Screen From Home Page Error Message")
//    public void verifyEnableExposureScreen()
//    {
//        HomePage homePage = new HomePage(driver, platform);
//        homePage.verifyHomePageDisplayed();
//
//        homePage.clickExposureNotificationNotEnabledErrorMessage();
//
//        EnableExposureScreen enableExposureScreen = new EnableExposureScreen(driver, platform);
//        enableExposureScreen.verifyEnableExposurePageDisplayed();
//        enableExposureScreen.clickEnableButton();
//        enableExposureScreen.verifyEnableExposurePopupDisplayed();
//        enableExposureScreen.getAppiumUtils().snap("27-Banner-Bluetooth-off");
//        enableExposureScreen.clickTurnOnButton();
//        homePage.verifyHomePageDisplayed();
// //        homePage.verifyExposureNotificationNotEnabledErrorMessageIsNotDisplayed();
//
//    }
//
//    @Test(priority = 12, dependsOnMethods = {"verifyEnableExposureScreen"}, description = "Verify Push Notifications Screen From Home Page Error Message")
//    @Description("Verify Push Notifications Screen From Home Page Error Message")
//    public void verifyPushNotificationsScreenFromHomePageErrorMessage()
//    {
//        HomePage homePage = new HomePage(driver, platform);
//        homePage.verifyHomePageDisplayed();
//
//        if(platform== PageObjectRepoHelper.PLATFORM.IOS) {
//            homePage.clickPushNotificationNotEnabledErrorMessage_IOS();
//            IOSPushNotifications iosPushNotifications = new IOSPushNotifications(driver, platform);
//            iosPushNotifications.verifyPushNotificationPageDisplayed();
//            iosPushNotifications.clickOnEnableButton();
//            iosPushNotifications.verifyPushNotificationPopupDisplayed();
//            iosPushNotifications.clickAllowButton();
//            homePage.verifyHomePageDisplayed();
//            homePage.verifyPushNotificationNotEnabledErrorMessageIsNotDisplayed_IOS();
//        }
//        else
//            Allure.step("Not applicable for Android", Status.PASSED);
//    }
//
//    @Test(priority = 13, dependsOnMethods = {"verifyPushNotificationsScreenFromHomePageErrorMessage"}, description = "Verify My Exposure Screen")
//    @Description("Verify My Exposure Screen")
//    public void verifyMyExposureScreenFromHomePageErrorMessage()
//    {
//        HomePage homePage = new HomePage(driver, platform);
//        homePage.clickMenuButton();
//        MenuPage menuPage = new MenuPage(driver, platform);
//        menuPage.verifyMenuPageDisplayed();
//        menuPage.clickMyExposureMenuButton();
//        MyExposures myExposures = new MyExposures(driver, platform);
//        myExposures.verifyMyExposuresDisplayed();
//        myExposures.verifyNoExposureMessageDisplayed();
//        myExposures.getAppiumUtils().attachScreenShotToTheReport("VerifyNoExposureMessageDisplayed");
//        myExposures.clickCloseButton();
//        menuPage.clickCloseButton();
//    }
//
//    @Test(priority = 14, dependsOnMethods = {"verifyMyExposureScreenFromHomePageErrorMessage"}, description = "Verify Past Diagnoses Screen")
//    @Description("Verify Past Diagnoses Screen")
//    public void verifyPastDiagnosesScreen()
//    {
//        HomePage homePage = new HomePage(driver, platform);
//        homePage.clickMenuButton();
//        MenuPage menuPage = new MenuPage(driver, platform);
//        menuPage.verifyMenuPageDisplayed();
//        menuPage.clickMyPastDiagnosesMenuButton();
//        SharedDiagnoses sharedDiagnoses = new SharedDiagnoses(driver, platform);
//        sharedDiagnoses.verifySharedDiagnosesPageDisplayed();
//        sharedDiagnoses.verifyNoPastPositiveDiagnosesMessageDisplayed();
//        sharedDiagnoses.getAppiumUtils().attachScreenShotToTheReport("VerifyNoExposureMessageDisplayed");
//        sharedDiagnoses.clickCloseButton();
//        menuPage.clickCloseButton();
//    }

}

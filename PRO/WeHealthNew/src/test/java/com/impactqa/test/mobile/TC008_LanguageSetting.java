
package com.impactqa.test.mobile;
import com.impactqa.listeners.TestAllureListener;
import com.impactqa.page.mobile.*;
import com.impactqa.utilities.AppiumUtils;
import com.impactqa.utilities.ExcelUtil;
import com.impactqa.utilities.FrameworkConfig;
import com.impactqa.utilities.PageObjectRepoHelper;
import io.qameta.allure.Allure;
import io.qameta.allure.Description;
import io.qameta.allure.model.Status;
import org.testng.Assert;
import org.testng.annotations.*;
import restAssured.PojoData;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Calendar;

@Listeners({TestAllureListener.class})
public class TC008_LanguageSetting  extends BaseTestMobile2{

    Calendar submitDate = Calendar.getInstance();
    Calendar calTestDate;
    Calendar calSymptomsDate;
    String verificationCode;

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
        openMobileSessionDeviceSettings(testDataMap.get("MobileSessionID1"));
        DeviceSettingsAppScreen deviceSettingsAppScreen = new DeviceSettingsAppScreen(driver,platform);
        deviceSettingsAppScreen.turnOffExposureNotificationFromIOSDeviceSettings();;
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
        gettingStarted.verifyGettingStartedPageDisplayed();
        gettingStarted.getAppiumUtils().snap("01-GettingStartedPage");
        gettingStarted.clickGettingStartedButton();
    }

    @Test(priority = 4, dependsOnMethods = {"verifyGettingStartedPage"}, description = "Verify App Introduction Page")
    @Description("Verify App Introduction Page")
    public void verifyIntroductionPage()
    {

          AppExplanationPage appExplanationPage = new AppExplanationPage(driver, platform);
//        appExplanationPage.verifyAppExplanationPageDisplayed();
//        appExplanationPage.verifyTextHowToWorkPage();
           appExplanationPage.getAppiumUtils().snap("02-AppExplanationPagePage");
           appExplanationPage.clickHowItWorksButton();
    }

    @Test(priority = 5, dependsOnMethods = {"verifyIntroductionPage"}, description = "Verify How It Works")
    @Description("Verify How It Works")
    public void verifyHowItWorksPage()
    {

        HowItWorks howItWorks = new HowItWorks(driver, platform);
//        howItWorks.verifyHowItWorksPageDisplayed();
        howItWorks.getAppiumUtils().snap("03-HowItWorks");
        howItWorks.clickNextButton();

        howItWorks.getAppiumUtils().snap("04-HowItWorks");
        howItWorks.clickNextButton();

        howItWorks.getAppiumUtils().snap("05-HowItWorks");
        howItWorks.clickNextButton();

        howItWorks.getAppiumUtils().snap("06-HowItWorks");
        howItWorks.clickContinueSetupButton();
    }

    @Test(priority = 6, dependsOnMethods = {"verifyHowItWorksPage"}, description = "Verify Enable Exposure Screen")
    @Description("Verify Enable Exposure Screen")
    public void verifyEnableExposureScreen()
    {

        EnableExposureScreen enableExposureScreen = new EnableExposureScreen(driver, platform);
       // enableExposureScreen.verifyEnableExposurePageDisplayed();
        enableExposureScreen.getAppiumUtils().snap("07-EnableExposureScreen");
        enableExposureScreen.clickEnableButton();
        //enableExposureScreen.verifyEnableExposurePopupDisplayed();
        enableExposureScreen.getAppiumUtils().snap("08-EnableExposureScreen");
        enableExposureScreen.clickTurnOnButton();
       
    }

    @Test(priority = 7, dependsOnMethods = {"verifyEnableExposureScreen"}, description = "Verify Push Notifications Screen")
    @Description("Verify Push Notifications Screen")
    public void verifyPushNotificationsScreen()
    {
        if(platform== PageObjectRepoHelper.PLATFORM.IOS) {

            IOSPushNotifications iosPushNotifications = new IOSPushNotifications(driver, platform);
           // iosPushNotifications.verifyPushNotificationPageDisplayed();
            iosPushNotifications.clickOnEnableButton();
            iosPushNotifications.verifyPushNotificationPopupDisplayed();
            iosPushNotifications.clickAllowButton();
        }
        else
            Allure.step("Not applicable for Android", Status.PASSED);
    }

    @Test(priority = 8, dependsOnMethods = {"verifyPushNotificationsScreen"}, description = "Verify Settings Screen")
    @Description("Verify Settings Screen")
    public void verifySettingsPage()
    {

        SettingsPage settingsPage = new SettingsPage(driver, platform);
        settingsPage.getAppiumUtils().sleepForMiliseconds(15000);
       // settingsPage.verifySettingsPageDisplayed();
//        settingsPage.selectRegion(testDataMap.get("BuildType"), testDataMap.get("Region"));
        settingsPage.getAppiumUtils().snap("09-SettingsPage");
        settingsPage.verifyExposureNotificationCheckboxStatus("checked");
        settingsPage.notifyLowExposureCheckbox(testDataMap.get("NotifyLowExposures"));
        settingsPage.getAppiumUtils().snap("10-SettingsPage");
        settingsPage.clickSaveButton();
    }

    // Made by Sonu Kumar
    // 06-03-21
    @Test(priority = 9, dependsOnMethods = {"verifyPushNotificationsScreen"}, description = "Verify SetupComplete page")
    @Description("Verify Setup Complete Page")
    public void verifySetupCompletePage()
    {
        SetupPage set = new SetupPage(driver,platform);
      //  set.verifySetupCompletePageDisplayed();
       // set.verifyTextOnSetupCompletePage();
        set.getAppiumUtils().snap("11-SetupPage");
        set.clickOkButton();

    }


    @Test(priority = 10, dependsOnMethods = {"verifySettingsPage"}, description = "Verify Home Screen")
    @Description("Verify Home Screen")
    public void verifyHomePage()
    {

        HomePage homePage = new HomePage(driver, platform);
        homePage.getAppiumUtils().snap("12-HomePage-NoExposure");

        homePage.getAppiumUtils().scrollDown();
        homePage.getAppiumUtils().snap("13-HomePage-NoExposure");
    }

    @Test(priority = 11, dependsOnMethods = {"verifyHomePage"}, description = "Verify My Exposure Screen")
    @Description("Verify My Exposure Screen")
    public void verifyMyExposureScreen()
    {
        HomePage homePage = new HomePage(driver, platform);
        homePage.clickMenuButton();

        MenuPage menuPage = new MenuPage(driver, platform);
        menuPage.getAppiumUtils().snap("14-MenuPage");
        menuPage.scrollDown();;
        menuPage.getAppiumUtils().snap("15-MenuPage");

        menuPage.clickMyExposureMenuButton();
        MyExposures myExposures = new MyExposures(driver, platform);
        myExposures.getAppiumUtils().snap("16-MyExposures");
        myExposures.ClickDailyTab();

        myExposures.getAppiumUtils().snap("17-Daily-tab");
        myExposures.clickCloseButton();
        menuPage.clickCloseButton();
    }


    @Test(priority = 12, dependsOnMethods = {"verifyMyExposureScreen"}, description = "Verify Shared Diagnoses Screen")
    @Description("Verify Shared Diagnoses Screen")
    public void verifySharedDiagnosesScreen()
    {
        HomePage homePage = new HomePage(driver, platform);

        homePage.clickMenuButton();

        MenuPage menuPage = new MenuPage(driver, platform);

     //   menuPage.verifyMenuPageDisplayed();
        menuPage.clickMyPastDiagnosesMenuButton();
        SharedDiagnoses shareDiagnoses = new SharedDiagnoses(driver, platform);
       // shareDiagnoses.verifySharedDiagnosesPageDisplayed();
       // shareDiagnoses.verifyNoPastPositiveDiagnosesMessageDisplayed();
       // shareDiagnoses.getAppiumUtils().attachScreenShotToTheReport("VerifyNoExposureMessageDisplayed");
        shareDiagnoses.getAppiumUtils().snap("18-SharedDiagnosesPage");
        shareDiagnoses.clickCloseButton();
        menuPage.clickCloseButton();
    }

    @Test(priority = 13, dependsOnMethods = {"verifySharedDiagnosesScreen"}, description = "Verify Shared Diagnoses Screen")
    @Description("Verify About Screen")
    public void verifyAboutPage()
    {
        HomePage homePage = new HomePage(driver, platform);
        homePage.clickMenuButton();

        MenuPage menuPage = new MenuPage(driver, platform);
        menuPage.clickAboutMenuButton();

        AboutPage a = new AboutPage(driver,platform);
        a.getAppiumUtils().snap("19-AboutPage");
        a.close();
        menuPage.clickCloseButton();

    }



    @Test(priority = 14, dependsOnMethods = {"verifyAboutPage"}, description = "Generate Verification Code From Web Portal")
    @Description("Generate Verification Code From Web Portal")
    public void generateVerificationCodeFromWebPortal() throws IOException {


        if(!"".equals(testDataMap.get("SymptomsDay"))) {
            try {
                int intSympDay = -(Integer.valueOf(testDataMap.get("SymptomsDay")));
                calSymptomsDate = Calendar.getInstance();
                calSymptomsDate.add(Calendar.DATE, intSympDay);
            } catch (NumberFormatException nfe) {
                Assert.fail("SymptomsDay should be number in the excel sheet.");
            }
        }

        if(!"".equals(testDataMap.get("TestedDay"))) {
            try {
                int intTestedDay = -(Integer.valueOf(testDataMap.get("TestedDay")));
                calTestDate = Calendar.getInstance();
                calTestDate.add(Calendar.DATE, intTestedDay);
            } catch (NumberFormatException nfe) {
                Assert.fail("TestedDay should be number in the excel sheet.");
            }
        }

//        WebDriver webBrowser = DriverProvider.createNewBrowserSession("chrome");
//        LoginPage loginPage = new LoginPage(webBrowser);
//        loginPage.doLogin(testDataMap.get("WebUserName"), testDataMap.get("WebPassword"));
//        SelectRealmPage selectRealmPage = new SelectRealmPage(webBrowser);
//        selectRealmPage.verifyRealmPageDisplayed();
//        selectRealmPage.selectRealm(testDataMap.get("Realm").trim());
//
        //IssueCodePage issueCodePage = new IssueCodePage(webBrowser);
//        issueCodePage.clickIssueCodeTab();
//        issueCodePage.verifyIssueCodePageDisplayed();
        PojoData data = new PojoData();

        verificationCode = data. fetchData(testDataMap.get("BuildType"),calSymptomsDate,calTestDate);

        //verificationCode = issueCodePage.generateCodeForPositiveTest(calSymptomsDate, null );
//       webBrowser.quit();
    }

    @Test(priority = 15, dependsOnMethods = {"generateVerificationCodeFromWebPortal"}, description = "Anonymously Share Positive Diagnosis")
    @Description("Anonymously Share Positive Diagnosis")
    public void anonymouslySharePositiveDiagnosis()
    {
        HomePage homePage = new HomePage(driver, platform);
        homePage.clickShareDiagnosisButton();
        HowToSharePositiveDiagnosis howToSharePositiveDiagnosis = new HowToSharePositiveDiagnosis(driver, platform);
        howToSharePositiveDiagnosis.getAppiumUtils().snap("20-HowToSharePositiveDiagnosisPage");
        //howToSharePositiveDiagnosis.verifyHowToSharePositiveDiagnosisPageDisplayed();
        howToSharePositiveDiagnosis.clickSharePositiveDiagnosisButton();
        AnonymouslySharePositiveDiagnosis anonymouslySharePositiveDiagnosis = new AnonymouslySharePositiveDiagnosis(driver,platform);
      //  anonymouslySharePositiveDiagnosis.verifyAnonymouslySharePositiveDiagnosisPageDisplayed();
        anonymouslySharePositiveDiagnosis.getAppiumUtils().snap("21-AnonymouslySharePositiveDiagnosis");
        anonymouslySharePositiveDiagnosis.enterVerificationCode(verificationCode);

        if(calTestDate!=null){
            anonymouslySharePositiveDiagnosis.selectNoSymptomsCheckbox("check");
            anonymouslySharePositiveDiagnosis.selectTestedDate(calTestDate);
        }
        else
            anonymouslySharePositiveDiagnosis.selectSymptomsDate(calSymptomsDate);
        anonymouslySharePositiveDiagnosis.getAppiumUtils().snap("22-AnonymouslySharePositiveDiagnosis");
        anonymouslySharePositiveDiagnosis.clickAnonymouslyShareDiagnosisButton();
        anonymouslySharePositiveDiagnosis.getAppiumUtils().snap("23-AnonymouslySharePositiveDiagnosis");
        anonymouslySharePositiveDiagnosis.clickShareButtonAndWaitForConfirmation();
        anonymouslySharePositiveDiagnosis.getAppiumUtils().sleepForMiliseconds(5000);
        anonymouslySharePositiveDiagnosis.getAppiumUtils().snap("24-AnonymouslySharePositiveDiagnosis");
        anonymouslySharePositiveDiagnosis.clickCloseButton();

    }

    @Test(priority = 17, dependsOnMethods = {"anonymouslySharePositiveDiagnosis"}, description = "Anonymously Share Positive Diagnosis")
    @Description("Verify Home And Past Diagnoses Screens After Sharing Positive Diagnosis")
    public void verifyHomeAndPastDiagnosesScreensAfterSharingPositiveDiagnosis()
    {
        HomePage homePage = new HomePage(driver, platform);
        homePage.getAppiumUtils().snap("25-HomePage-VerifiedPositive");
        homePage.clickMenuButton();
        MenuPage menuPage = new MenuPage(driver, platform);
       // menuPage.verifyMenuPageDisplayed();
        menuPage.clickMyPastDiagnosesMenuButton();
        SharedDiagnoses shareDiagnoses = new SharedDiagnoses(driver, platform);
        shareDiagnoses.getAppiumUtils().snap("26-SharedDiagnoses-VerifiedPostive");
        shareDiagnoses.clickDropDown();
        shareDiagnoses.getAppiumUtils().snap("27-Symptom-onset-date");


        //shareDiagnoses.verifyPastDiagnosesDataDisplayedDisplayed(submitDate, calTestDate, calSymptomsDate);

    }
}


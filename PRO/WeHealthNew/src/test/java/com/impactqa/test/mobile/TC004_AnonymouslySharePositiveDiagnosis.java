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
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Listeners;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import restAssured.PojoData;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Calendar;
@Listeners({TestAllureListener.class, TestNGExecutionLister.class})
public class TC004_AnonymouslySharePositiveDiagnosis extends BaseTestMobile2{

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
        deviceSettingsAppScreen.turnOffExposureNotificationFromIOSDeviceSettings();
        deviceSettingsAppScreen.uninstallAllApps();
    }

    @Test(priority = 2, dependsOnMethods = {"unInstallAllApp"}, description = "Install Main App")
    @Description("Install Main App")
    public void installApp() throws MalformedURLException {
        teardownDriverInstance();
        openMobileSession(testDataMap.get("MobileSessionID1"));
    }

    @Test(priority = 3, dependsOnMethods = {"installApp"}, description = "Fill The Initial Screens If Required")
    @Description("Fill The Initial Screens If Required")
    public void fillTheInitialScreens()
    {
        GettingStarted gettingStarted = new GettingStarted(driver, platform);
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
         //   settingsPage.selectRegion(testDataMap.get("BuildType"), testDataMap.get("Region"));
            settingsPage.notifyLowExposureCheckbox(testDataMap.get("NotifyLowExposures"));
            settingsPage.clickSaveButton();
        }
    }

    // Made by Sonu Kumar
    @Test(priority = 4, dependsOnMethods = {"fillTheInitialScreens"}, description = "Verify SetupComplete page")
    @Description("Verify Setup Complete Page")
    public void verifySetupCompletePage()
    {
        SetupPage set = new SetupPage(driver,platform);
        set.verifySetupCompletePageDisplayed();
        set.verifyTextOnSetupCompletePage();
        set.clickOkButton();
    }


    @Test(priority = 5, dependsOnMethods = {"verifySetupCompletePage"}, description = "Generate Verification Code From Web Portal")
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

    @Test(priority = 6, dependsOnMethods = {"generateVerificationCodeFromWebPortal"}, description = "Anonymously Share Positive Diagnosis")
    @Description("Anonymously Share Positive Diagnosis")
    public void anonymouslySharePositiveDiagnosis()
    {
        HomePage homePage = new HomePage(driver, platform);
        homePage.clickShareDiagnosisButton();
        HowToSharePositiveDiagnosis howToSharePositiveDiagnosis = new HowToSharePositiveDiagnosis(driver, platform);
        howToSharePositiveDiagnosis.verifyHowToSharePositiveDiagnosisPageDisplayed();
        howToSharePositiveDiagnosis.clickSharePositiveDiagnosisButton();
        AnonymouslySharePositiveDiagnosis anonymouslySharePositiveDiagnosis = new AnonymouslySharePositiveDiagnosis(driver,platform);
        anonymouslySharePositiveDiagnosis.verifyAnonymouslySharePositiveDiagnosisPageDisplayed();
        anonymouslySharePositiveDiagnosis.enterVerificationCode(verificationCode);

        if(calTestDate!=null){
            anonymouslySharePositiveDiagnosis.selectNoSymptomsCheckbox("check");
            anonymouslySharePositiveDiagnosis.selectTestedDate(calTestDate);
        }else
            anonymouslySharePositiveDiagnosis.selectSymptomsDate(calSymptomsDate);
        anonymouslySharePositiveDiagnosis.clickAnonymouslyShareDiagnosisButton();
        anonymouslySharePositiveDiagnosis.clickDontShareButton();
        anonymouslySharePositiveDiagnosis.getAppiumUtils().sleepForMiliseconds(2000);
        anonymouslySharePositiveDiagnosis.verifyAnonymouslySharePositiveDiagnosisPageDisplayed();
        anonymouslySharePositiveDiagnosis.clickAnonymouslyShareDiagnosisButton();
        anonymouslySharePositiveDiagnosis.clickShareButtonAndWaitForConfirmation();
        anonymouslySharePositiveDiagnosis.getAppiumUtils().sleepForMiliseconds(5000);
        anonymouslySharePositiveDiagnosis.clickCloseButton();

    }

    @Test(priority = 7, dependsOnMethods = {"anonymouslySharePositiveDiagnosis"}, description = "Anonymously Share Positive Diagnosis")
    @Description("Verify Home And Past Diagnoses Screens After Sharing Positive Diagnosis")
    public void verifyHomeAndPastDiagnosesScreensAfterSharingPositiveDiagnosis()
    {
        HomePage homePage = new HomePage(driver, platform);
        homePage.verifyHomePageDisplayed();
        homePage.verifyRiskLevelMessage("Verified Positive Diagnosis");
        homePage.clickMenuButton();
        MenuPage menuPage = new MenuPage(driver, platform);
        menuPage.verifyMenuPageDisplayed();
        menuPage.clickMyPastDiagnosesMenuButton();
        SharedDiagnoses shareDiagnoses = new SharedDiagnoses(driver, platform);
        shareDiagnoses.verifySharedDiagnosesPageDisplayed();
        shareDiagnoses.verifyNoPastPositiveDiagnosesMessageIsNotDisplayed();
        shareDiagnoses.verifyPastDiagnosesDataDisplayedDisplayed(submitDate, calTestDate, calSymptomsDate);

    }
}

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
import io.qameta.allure.Allure;
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
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.LinkedHashMap;
import java.util.Map;

@Listeners({TestAllureListener.class, TestNGExecutionLister.class})
public class TC005_SharePositiveDiagnosisWithPortalDateConflict extends BaseTestMobile2{

    String verificationCode;
    Map<String, Object> caseData = new LinkedHashMap<>();

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
        //    settingsPage.selectRegion(testDataMap.get("BuildType"), testDataMap.get("Region"));
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
    @Description("Generate Verification Codes From Web Portal")
    public void generateVerificationCodesFromWebPortal() throws IOException {

        for(int caseNo=1;caseNo<5;caseNo++){

            Calendar calSymptomsDateApp = null;
            Calendar calSymptomsDatePortal = null;

            switch (caseNo) {
                case 1:
                    calSymptomsDateApp = Calendar.getInstance();
                    calSymptomsDateApp.add(Calendar.DATE, -4);
                    calSymptomsDatePortal = Calendar.getInstance();
                    calSymptomsDatePortal.add(Calendar.DATE, -2);
                    break;
                case 2:
                    calSymptomsDateApp = Calendar.getInstance();
                    calSymptomsDateApp.add(Calendar.DATE, -2);
                    calSymptomsDatePortal = Calendar.getInstance();
                    calSymptomsDatePortal.add(Calendar.DATE, -4);
                    break;
                case 3:
                    calSymptomsDateApp = Calendar.getInstance();
                    calSymptomsDateApp.add(Calendar.DATE, -3);
                    break;
                case 4:
                    calSymptomsDatePortal = Calendar.getInstance();
                    calSymptomsDatePortal.add(Calendar.DATE, -1);
                    break;
            }

                caseData.put("calSymptomsDateAppCase"+caseNo, calSymptomsDateApp);
                caseData.put("calSymptomsDatePortalCase"+caseNo, calSymptomsDatePortal);


            PojoData data = new PojoData();
           // String verificationCode= data. fetchData("Arizona",calSymptomsDatePortal,null);
            String verificationCode= data. fetchData(testDataMap.get("BuildType"),calSymptomsDatePortal,null);
            caseData.put("verificationCodeCase"+caseNo, verificationCode);

        }

    }

    @Test(priority = 6, dependsOnMethods = {"generateVerificationCodesFromWebPortal"}, description = "Anonymously Share Positive Diagnosis - Case1: Portal date greater than App Date")
    @Description("Anonymously Share Positive Diagnosis - Case1: Portal date greater than App Date")
    public void anonymouslySharePositiveDiagnosisCase1()
    {
        Allure.step("calSymptomsDateAppCase1: "+new SimpleDateFormat("yyyy-MM-dd").format( ((Calendar)caseData.get("calSymptomsDateAppCase1")).getTime() ));
        Allure.step("calSymptomsDatePortalCase1: "+new SimpleDateFormat("yyyy-MM-dd").format( ((Calendar)caseData.get("calSymptomsDatePortalCase1")).getTime() ));
        HomePage homePage = new HomePage(driver, platform);
        homePage.clickShareDiagnosisButton();

        HowToSharePositiveDiagnosis howToSharePositiveDiagnosis = new HowToSharePositiveDiagnosis(driver, platform);
        howToSharePositiveDiagnosis.verifyHowToSharePositiveDiagnosisPageDisplayed();
        howToSharePositiveDiagnosis.clickSharePositiveDiagnosisButton();

        AnonymouslySharePositiveDiagnosis anonymouslySharePositiveDiagnosis = new AnonymouslySharePositiveDiagnosis(driver,platform);
        anonymouslySharePositiveDiagnosis.verifyAnonymouslySharePositiveDiagnosisPageDisplayed();

        anonymouslySharePositiveDiagnosis.enterVerificationCode((String) caseData.get("verificationCodeCase1"));

        anonymouslySharePositiveDiagnosis.selectSymptomsDate((Calendar) caseData.get("calSymptomsDateAppCase1"));

        anonymouslySharePositiveDiagnosis.clickAnonymouslyShareDiagnosisButton();
        anonymouslySharePositiveDiagnosis.clickShareButtonAndWaitForConfirmation();
        anonymouslySharePositiveDiagnosis.getAppiumUtils().sleepForMiliseconds(5000);
        anonymouslySharePositiveDiagnosis.clickCloseButton();

    }

    @Test(priority = 7, dependsOnMethods = {"anonymouslySharePositiveDiagnosisCase1"}, description = "Anonymously Share Positive Diagnosis - Case2: Portal date lesser than App Date")
    @Description("Anonymously Share Positive Diagnosis - Case2: Portal date lesser than App Date")
    public void anonymouslySharePositiveDiagnosisCase2()
    {

        Allure.step("calSymptomsDateAppCase2: "+new SimpleDateFormat("yyyy-MM-dd").format( ((Calendar)caseData.get("calSymptomsDateAppCase2")).getTime() ));
        Allure.step("calSymptomsDatePortalCase2: "+new SimpleDateFormat("yyyy-MM-dd").format( ((Calendar)caseData.get("calSymptomsDatePortalCase2")).getTime() ));
        AnonymouslySharePositiveDiagnosis anonymouslySharePositiveDiagnosis = new AnonymouslySharePositiveDiagnosis(driver,platform);
      //  anonymouslySharePositiveDiagnosis.clickCloseButton();
        HomePage homePage = new HomePage(driver, platform);
        homePage.clickShareDiagnosisButton();
        HowToSharePositiveDiagnosis howToSharePositiveDiagnosis = new HowToSharePositiveDiagnosis(driver, platform);
        howToSharePositiveDiagnosis.verifyHowToSharePositiveDiagnosisPageDisplayed();
        howToSharePositiveDiagnosis.clickSharePositiveDiagnosisButton();
        anonymouslySharePositiveDiagnosis.verifyAnonymouslySharePositiveDiagnosisPageDisplayed();
        anonymouslySharePositiveDiagnosis.enterVerificationCode((String) caseData.get("verificationCodeCase2"));
        anonymouslySharePositiveDiagnosis.selectSymptomsDate((Calendar) caseData.get("calSymptomsDateAppCase2"));
        anonymouslySharePositiveDiagnosis.clickAnonymouslyShareDiagnosisButton();
        anonymouslySharePositiveDiagnosis.getAppiumUtils().sleepForMiliseconds(5000);
        anonymouslySharePositiveDiagnosis.clickCloseButton();

        if(platform== PageObjectRepoHelper.PLATFORM.IOS)
            anonymouslySharePositiveDiagnosis.clickShareButtonAndWaitForConfirmation();
//        else
//            anonymouslySharePositiveDiagnosis.waitForConfirmation();


    }

    @Test(priority = 8, dependsOnMethods = {"anonymouslySharePositiveDiagnosisCase2"}, description = "Anonymously Share Positive Diagnosis - Case3: Portal date is NOT set, But but SET on app")
    @Description("Anonymously Share Positive Diagnosis - Case3: Portal date is NOT set, But but SET on app")
    public void anonymouslySharePositiveDiagnosisCase3()
    {
        Allure.step("calSymptomsDateAppCase3: "+new SimpleDateFormat("yyyy-MM-dd").format( ((Calendar)caseData.get("calSymptomsDateAppCase3")).getTime() ));
        Allure.step("calSymptomsDatePortalCase3: NOT SET");
        AnonymouslySharePositiveDiagnosis anonymouslySharePositiveDiagnosis = new AnonymouslySharePositiveDiagnosis(driver,platform);
       // anonymouslySharePositiveDiagnosis.clickCloseButton();
        HomePage homePage = new HomePage(driver, platform);
        homePage.clickShareDiagnosisButton();
        HowToSharePositiveDiagnosis howToSharePositiveDiagnosis = new HowToSharePositiveDiagnosis(driver, platform);
        howToSharePositiveDiagnosis.verifyHowToSharePositiveDiagnosisPageDisplayed();
        howToSharePositiveDiagnosis.clickSharePositiveDiagnosisButton();
        anonymouslySharePositiveDiagnosis.verifyAnonymouslySharePositiveDiagnosisPageDisplayed();
        anonymouslySharePositiveDiagnosis.enterVerificationCode((String) caseData.get("verificationCodeCase3"));
        anonymouslySharePositiveDiagnosis.selectSymptomsDate((Calendar) caseData.get("calSymptomsDateAppCase3"));
        anonymouslySharePositiveDiagnosis.clickAnonymouslyShareDiagnosisButton();
        anonymouslySharePositiveDiagnosis.getAppiumUtils().sleepForMiliseconds(5000);
        anonymouslySharePositiveDiagnosis.clickCloseButton();

        if(platform== PageObjectRepoHelper.PLATFORM.IOS)
            anonymouslySharePositiveDiagnosis.clickShareButtonAndWaitForConfirmation();
//        else
//            anonymouslySharePositiveDiagnosis.waitForConfirmation();

    }

    @Test(priority = 9, dependsOnMethods = {"anonymouslySharePositiveDiagnosisCase3"}, description = "Anonymously Share Positive Diagnosis - Case4: Portal date is set, But but NOT SET on app")
    @Description("Anonymously Share Positive Diagnosis - Case4: Portal date is set, But but NOT SET on app")
    public void anonymouslySharePositiveDiagnosisCase4()
    {

        Allure.step("calSymptomsDateAppCase4: NOT SET");
        Allure.step("calSymptomsDatePortalCase4: "+new SimpleDateFormat("yyyy-MM-dd").format( ((Calendar)caseData.get("calSymptomsDatePortalCase4")).getTime() ));
        AnonymouslySharePositiveDiagnosis anonymouslySharePositiveDiagnosis = new AnonymouslySharePositiveDiagnosis(driver,platform);
        //anonymouslySharePositiveDiagnosis.clickCloseButton();
        HomePage homePage = new HomePage(driver, platform);
        homePage.clickShareDiagnosisButton();
        HowToSharePositiveDiagnosis howToSharePositiveDiagnosis = new HowToSharePositiveDiagnosis(driver, platform);
        howToSharePositiveDiagnosis.verifyHowToSharePositiveDiagnosisPageDisplayed();
        howToSharePositiveDiagnosis.clickSharePositiveDiagnosisButton();
        anonymouslySharePositiveDiagnosis.verifyAnonymouslySharePositiveDiagnosisPageDisplayed();
        anonymouslySharePositiveDiagnosis.enterVerificationCode((String) caseData.get("verificationCodeCase3"));
        anonymouslySharePositiveDiagnosis.verifyAnonymouslyShareDiagnosisButtonIsNotDisplayed();
        anonymouslySharePositiveDiagnosis.getAppiumUtils().sleepForMiliseconds(5000);
        anonymouslySharePositiveDiagnosis.clickCloseButton();
    }
}

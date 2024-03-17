package com.impactqa.page.mobile;

import com.impactqa.utilities.CommonUtil;
import com.impactqa.utilities.FrameworkConfig;
import com.impactqa.utilities.PageObjectRepoHelper;
import io.appium.java_client.AppiumDriver;
import io.qameta.allure.Allure;
import io.qameta.allure.Step;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.testng.Assert;

import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.Properties;

public class HomePage extends BasePage {
    private static final String PageObjectRepoFileName = "HomePage.xml";

    public HomePage(AppiumDriver driver, PageObjectRepoHelper.PLATFORM platform) {
        super(driver, PageObjectRepoFileName, platform);
    }

    @Step("Verify Home Page Displayed")
    public void verifyHomePageDisplayed()
    {
        appiumUtils.waitForElementToDisplay("homePageArt");
    }


    @Step("Get Current Region")
    public String getCurrentRegion()
    {
        appiumUtils.scrollUp();
        return appiumUtils.getText("homePageCurrentRegion");
    }

    @Step("Get Current Risk Level")
    public String getCurrentRiskLevel()
    {
        return appiumUtils.getText("myRiskLevelText");
    }

    @Step("Push Local File To Download Directory")
    public void pushLocalFileToDownloadsDirectory(String localFilepath)
    {

        String fileName = new File(localFilepath).getName();
        if(appiumUtils.getPlatform()== PageObjectRepoHelper.PLATFORM.ANDROID) {
            appiumUtils.deleteFilesInDownloadFolderAndroid();
            appiumUtils.pushFile("/sdcard/Download/" + fileName
                    , localFilepath);
        }
        else
            appiumUtils.pushFile("@com.apple.Keynote:documents/" + fileName
                    ,localFilepath);
        appiumUtils.sleepForMiliseconds(5000);
    }

    @Step("Verify Risk Level message in Home Page. Expected: {0}")
    public void verifyRiskLevelMessage(String expectedRiskLevelMsg)
    {
       // appiumUtils.verifyText("myRiskLevelText", expectedRiskLevelMsg);
        //appiumUtils.attachScreenShotToTheReport("RiskLevelMessage Passed");
    }

    @Step("Verify Next Steps In Home Page ( using region json )")
    public void VerifyNextSteps(String buildType, String currentRegion)
    {
        // next step verification code
//      String currentRegion = getCurrentRegion().replace("Region:", "").trim();
        Properties prop = FrameworkConfig.loadFrameworkConfigProperties();
        String jsonDataOfArizona= prop.getProperty("firebaseApikeyOfArizona");
        String jsonDataOfBermuda= prop.getProperty("firebaseApikeyOfBermuda");
        String currentRiskLevel = getCurrentRiskLevel();
        String riskLevelJsonKey =null;
        if(currentRiskLevel.startsWith("No"))
            riskLevelJsonKey ="no-exposure";
        else if(currentRiskLevel.startsWith("Low"))
            riskLevelJsonKey="Low-exposure";
        else if (currentRiskLevel.startsWith("High"))
            riskLevelJsonKey="High-exposure";
        else if (currentRiskLevel.startsWith("Verified"))
            riskLevelJsonKey="verified-positive";
        String url = "";
        if(buildType.contains("Arizona"))
            url ="jsonDataOfArizona";
        else
            url ="jsonDataOfBermuda";
        Response response = RestAssured.given().get(url);
        String communityId = response.getBody().jsonPath().
                getString("community.findAll { it.name=='"+currentRegion+"'}[0].id");
        List<Map<String,Object>> expectedNextSteps =
                response.getBody().jsonPath().get("messaging.'"+communityId+"'.en.nextSteps.findAll{it.appState[0][0]=='"+riskLevelJsonKey+"'}[0].steps");


//        Map<String, List<Map<String, Object>>> map = RestAssured.given()
//                .get(url)
//                .getBody().jsonPath()
//                .getMap("findAll { it.name=='"+currentRegion+"' }[0]");
//        List<Map<String, Object>> expectedNextSteps = null;
//        if(currentRiskLevel.startsWith("High"))
//            expectedNextSteps = map.get("nextStepsSignificantExposure");
//        else if(currentRiskLevel.startsWith("Low"))
//            expectedNextSteps = map.get("nextStepsLowExposure");
//        else if(currentRiskLevel.startsWith("No"))
//            expectedNextSteps = map.get("nextStepsNoSignificantExposure");

        appiumUtils.scrollDown();
        List<String> actualSteps = appiumUtils.getTextOfListElements("nextStepAllText");

        if(actualSteps.size()==expectedNextSteps.size())
            Allure.step("Number of next steps matched: "+actualSteps.size());

        for(Map<String, Object> nextStep : expectedNextSteps){

            Object expectedNextStep = CommonUtil.Replace_DAYS_FROM_EXPOSURE((String)nextStep.get("description"));

            Assert.assertTrue(actualSteps.contains(expectedNextStep),
                    "Next step not present in the home page\n" +
                            "StepName: "+expectedNextStep
            );
            Allure.step("Verified Next Step-Name: "+expectedNextStep);
        }
    }

    @Step("Verify Exposure Notification Not Enabled Error Message Displayed")
    public void verifyExposureNotificationNotEnabledErrorMessageDisplayed()
    {
        appiumUtils.waitForElementToDisplay("exposureNotificationNotEnabledError");

        if(appiumUtils.getPlatform()== PageObjectRepoHelper.PLATFORM.ANDROID)
            appiumUtils.verifyText("exposureNotificationNotEnabledError", "Exposure Notifications must be enabled for the app to work correctly. Tap to review the app settings.");
        else
            appiumUtils.verifyText("exposureNotificationNotEnabledError", "Exposure notifications must be enabled for the app to work. Tap to enable.");
    }

    @Step("Verify Exposure Notification Not Enabled Error Message is NOT Displayed")
    public void verifyExposureNotificationNotEnabledErrorMessageIsNotDisplayed()
    {
        if(appiumUtils.isElementDisplayed("exposureNotificationNotEnabledError", 4))
            Assert.fail("Exposure Notification Not Enabled Error Message should not be Displayed");
    }

    @Step("Click Exposure Notification Not Enabled Error Message")
    public void clickExposureNotificationNotEnabledErrorMessage()
    {
        appiumUtils.click("exposureNotificationNotEnabledError");
    }

    @Step("Verify Push Notification Not Enabled Error Message Displayed - IOS")
    public void verifyPushNotificationNotEnabledErrorMessageDisplayed_IOS()
    {
        if(appiumUtils.getPlatform()== PageObjectRepoHelper.PLATFORM.IOS) {
            appiumUtils.waitForElementToDisplay("pushNotificationNotEnabledError_IOS");
            appiumUtils.verifyText("pushNotificationNotEnabledError_IOS", "The application is authorized to post non-interruptive user notifications.");
        }
    }

    @Step("Verify Push Notification Not Enabled Error Message is NOT Displayed - IOS")
    public void verifyPushNotificationNotEnabledErrorMessageIsNotDisplayed_IOS()
    {
        if(appiumUtils.getPlatform()== PageObjectRepoHelper.PLATFORM.IOS) {
            if(appiumUtils.isElementDisplayed("pushNotificationNotEnabledError_IOS", 4))
                Assert.fail("Push Notification Not Enabled Error Message should not be Displayed");
        }
    }

    @Step("Click Push Notification Not Enabled Error Message - IOS")
    public void clickPushNotificationNotEnabledErrorMessage_IOS()
    {
        if(appiumUtils.getPlatform()== PageObjectRepoHelper.PLATFORM.IOS)
            appiumUtils.click("pushNotificationNotEnabledError_IOS");
    }

    @Step("Verify Risk Level Expanded Message")
    public void verifyRiskLevelExpandedMessage(String expandAndCl)
    {
        appiumUtils.click("myRiskLevelText");
        //TODO
        //appiumUtils.verifyText("myRiskLevelExpandedMessage", "");
    }
    @Step("Click Menu Button")
    public void clickMenuButton()
    {
        appiumUtils.click("menuButton");
    }

    @Step("Click Share Diagnosis Button")
    public void clickShareDiagnosisButton()
    {
        appiumUtils.click("ShareDiagnosisButton");
    }
    @Step("Click Covid Vaccines  Button")
    public void clickVaccinesButton()
    {
        appiumUtils.click("ShareCovidVaccineButton");
    }

}

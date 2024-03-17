package com.impactqa.page.mobile;

import com.impactqa.utilities.FrameworkConfig;
import com.impactqa.utilities.PageObjectRepoHelper;
import io.appium.java_client.AppiumDriver;
import io.qameta.allure.Step;

import java.util.Map;
/**
 * @author  Sonu Kumar
 * @since   2021-03-05
 * @description This page conatins all functionality of add covid vaccine Introduction page.
 */
public class AppExplanationPage extends BasePage {
    private static final String PageObjectRepoFileName = "intialscreens/AppExplanationPage.xml";

    public AppExplanationPage(AppiumDriver driver, PageObjectRepoHelper.PLATFORM platform) {
        super(driver, PageObjectRepoFileName, platform);
    }
    // change by sonu kumar
    // This method worked on only arizona build.

    @Step("Verify App Explanation Page Displayed")
    public void verifyAppExplanationPageDisplayed()
    {
        appiumUtils.waitForElementToDisplay("safePrivateAnonymous");
        appiumUtils.waitForElementToDisplay("covidWatchArizona");
        appiumUtils.waitForElementToDisplay("getVaccineSupport");
        appiumUtils.waitForElementToDisplay("getExposureNotification");
        appiumUtils.waitForElementToDisplay("shareCovidTestResult");
        appiumUtils.waitForElementToDisplay("getCustomRecommendation");
        appiumUtils.waitForElementToDisplay("stopCommunitySpread");
        appiumUtils.waitForElementToDisplay("howItWorksButton");
    }


    @Step("Verify all text on How To work page")
    public void verifyTextHowToWorkPage()
    {
        appiumUtils.verifyText("safePrivateAnonymous","SAFE. PRIVATE. ANONYMOUS.");

        String verifyText = appiumUtils.getText("covidWatchArizona").trim();

        if(verifyText.equals("Covid Watch Arizona")){
            appiumUtils.verifyText("covidWatchArizona","Covid Watch Arizona");

        }else{
            appiumUtils.verifyText("covidWatchArizona","WeHealth Bermuda");

        }
        appiumUtils.verifyText("getVaccineSupport","NEW: Get vaccine support");
        appiumUtils.verifyText("getExposureNotification","Get exposure notifications");
        appiumUtils.verifyText("shareCovidTestResult","Share COVID-19 test results");
        appiumUtils.verifyText("getCustomRecommendation","Get custom recommendations");
        appiumUtils.verifyText("stopCommunitySpread","Stop community spread");

    }

    @Step("Click How It Works Button")
    public void clickHowItWorksButton()
    {
        appiumUtils.click("howItWorksButton");
    }

}

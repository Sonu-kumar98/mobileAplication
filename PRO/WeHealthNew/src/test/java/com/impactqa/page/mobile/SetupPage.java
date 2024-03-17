package com.impactqa.page.mobile;
import com.impactqa.utilities.PageObjectRepoHelper;
import io.appium.java_client.AppiumDriver;
import io.qameta.allure.Step;

// Made By Sonu Kumar
// date 06-03-21
public class SetupPage extends BasePage {

    private static final String PageObjectRepoFileName = "intialscreens/SetupPage.xml";

    public SetupPage(AppiumDriver driver, PageObjectRepoHelper.PLATFORM platform) {
        super(driver, PageObjectRepoFileName, platform);
    }

    @Step("Verify App Explanation Page Displayed")
    public void verifySetupCompletePageDisplayed()
    {
        appiumUtils.waitForElementToDisplay("SetupComplete");
        appiumUtils.waitForElementToDisplay("ThanksForHelping");
        appiumUtils.waitForElementToDisplay("getVaccineSupport");
        appiumUtils.waitForElementToDisplay("getExposureNotification");
        appiumUtils.waitForElementToDisplay("shareCovidTestResult");
        appiumUtils.waitForElementToDisplay("getCustomRecommendation");
        appiumUtils.waitForElementToDisplay("stopCommunitySpread");
        appiumUtils.waitForElementToDisplay("OKButton");
    }

    @Step("Verify all text on Setup Complete page")
    public void verifyTextOnSetupCompletePage()
    {
        appiumUtils.verifyText("SetupComplete","Setup Complete!");
        appiumUtils.verifyText("ThanksForHelping","Thanks for helping to stop the spread within your community. You can now…");
        appiumUtils.verifyText("getVaccineSupport","NEW: Get vaccine support");
        appiumUtils.verifyText("getExposureNotification","Get exposure notifications");
        appiumUtils.verifyText("shareCovidTestResult","Share COVID-19 test results");
        appiumUtils.verifyText("getCustomRecommendation","Get custom recommendations");
        appiumUtils.verifyText("stopCommunitySpread","Stop community spread");
        appiumUtils.verifyText("OKButton","ok");

    }

    @Step("Click Ok Button")
    public void clickOkButton()
    {
        appiumUtils.click("OKButton");
    }
}

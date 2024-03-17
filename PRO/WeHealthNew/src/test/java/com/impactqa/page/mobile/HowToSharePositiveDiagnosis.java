package com.impactqa.page.mobile;
import com.impactqa.utilities.PageObjectRepoHelper;
import io.appium.java_client.AppiumDriver;
import io.qameta.allure.Step;

public class HowToSharePositiveDiagnosis extends BasePage {
    private static final String PageObjectRepoFileName = "HowToSharePositiveDiagnosis.xml";

    public HowToSharePositiveDiagnosis(AppiumDriver driver, PageObjectRepoHelper.PLATFORM platform) {
        super(driver, PageObjectRepoFileName, platform);
    }

    @Step("Verify How To Share Positive Diagnosis Page Displayed")
    public void verifyHowToSharePositiveDiagnosisPageDisplayed()
    {
        appiumUtils.waitForElementToDisplay("howToSharePositiveDiagPageTitle");
    }

    // change by Sonu Kumar
    @Step("take screen sort of page")
    public void takeScreenSort()
    {
        appiumUtils.snap("how to share positive");
    }

    @Step("Click Where Is My Code Button")
    public void clickWhereIsMyCodeButton()
    {
        appiumUtils.click("whereisMyCodeButton");
    }

    @Step("Click Share Positive Diagnosis Button")
    public void clickSharePositiveDiagnosisButton()
    {
        appiumUtils.click("sharePositiveDiagnosisButton");
    }
}

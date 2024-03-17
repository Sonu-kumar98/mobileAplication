package com.impactqa.page.mobile;

import com.impactqa.utilities.FrameworkConfig;
import com.impactqa.utilities.PageObjectRepoHelper;
import io.appium.java_client.AppiumDriver;
import io.qameta.allure.Step;


public class HowItWorks extends BasePage {
    private static final String PageObjectRepoFileName = "intialscreens/HowItWorks.xml";


    public HowItWorks(AppiumDriver driver, PageObjectRepoHelper.PLATFORM platform) {
        super(driver, PageObjectRepoFileName, platform);
    }


    @Step("Verify How It Works Page Displayed")
    public void verifyHowItWorksPageDisplayed()
    {
       appiumUtils.waitForElementToDisplay("howItWorksTopTitle");
    }

    @Step("Click Next Button")
    public void clickNextButton()
    {
        appiumUtils.click("nextButton");
    }

    @Step("Click Continue Setup Button")
    public void clickContinueSetupButton()
    {
        appiumUtils.click("continueSetupButton");
    }

}

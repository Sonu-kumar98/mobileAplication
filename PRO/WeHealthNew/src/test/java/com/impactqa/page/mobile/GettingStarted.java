package com.impactqa.page.mobile;

import com.impactqa.utilities.FrameworkConfig;
import com.impactqa.utilities.PageObjectRepoHelper;
import io.appium.java_client.AppiumDriver;
import io.qameta.allure.Step;


public class GettingStarted extends BasePage {
    private static final String PageObjectRepoFileName = "intialscreens/GettingStarted.xml";

    public GettingStarted(AppiumDriver driver, PageObjectRepoHelper.PLATFORM platform) {
        super(driver, PageObjectRepoFileName, platform);
    }

    @Step("is Getting Started Page Displayed")
    public boolean isGettingStartedPageDisplayed()
    {
        return appiumUtils.isElementDisplayed("getStartedButton", 5);
    }

    @Step("Verify Getting Started Page Displayed")
    public void verifyGettingStartedPageDisplayed()
    {
        appiumUtils.waitForElementToDisplay("getStartedButton");
    }

    @Step("Click Getting Started Button")
    public void clickGettingStartedButton()
    {
        appiumUtils.click("getStartedButton");
    }




}

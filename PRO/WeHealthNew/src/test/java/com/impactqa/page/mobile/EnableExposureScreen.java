package com.impactqa.page.mobile;

import com.impactqa.utilities.FrameworkConfig;
import com.impactqa.utilities.PageObjectRepoHelper;
import io.appium.java_client.AppiumDriver;
import io.qameta.allure.Step;

/**
 * @author  Sonu Kumar
 * @since
 * @description This page conatins all functionality of EnableExposureScreen page.
 */
public class EnableExposureScreen extends BasePage {
    private static final String PageObjectRepoFileName = "intialscreens/EnableExposureScreen.xml";

    public EnableExposureScreen(AppiumDriver driver, PageObjectRepoHelper.PLATFORM platform) {
        super(driver, PageObjectRepoFileName, platform);
    }

    @Step("Verify Enable Exposure Page Displayed")
    public void verifyEnableExposurePageDisplayed()
    {
        appiumUtils.waitForElementToDisplay("enableExposureTitle");
    }



    @Step("Click Enable Button")
    public void clickEnableButton()
    {
        appiumUtils.click("enableButton");
    }

    @Step("Click Not Now Button")
    public void clickNotNowButton()
    {
        appiumUtils.click("notNowButton");
    }

    @Step("Verify Enable Exposure Popup Displayed")
    public void verifyEnableExposurePopupDisplayed()
    {
        appiumUtils.waitForElementToDisplay("alertPopupTitle");
        if(appiumUtils.getPlatform()== PageObjectRepoHelper.PLATFORM.IOS )
            appiumUtils.verifyText("alertPopupTitle", "Enable COVID-19 Exposure Logging and Notifications");
        else
            appiumUtils.verifyText("alertPopupTitle", "Turn on COVID-19 exposure notifications?");

    }

    @Step("Click Popup Turn On Button")
    public void clickTurnOnButton()
    {
        appiumUtils.click("alertPopupTurnOnButton");
    }
}

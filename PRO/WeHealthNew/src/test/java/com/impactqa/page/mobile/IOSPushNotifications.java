package com.impactqa.page.mobile;

import com.impactqa.utilities.PageObjectRepoHelper;
import io.appium.java_client.AppiumDriver;
import io.qameta.allure.Step;


public class IOSPushNotifications extends BasePage {
    private static final String PageObjectRepoFileName = "intialscreens/IOSPushNotifications.xml";

    public IOSPushNotifications(AppiumDriver driver, PageObjectRepoHelper.PLATFORM platform) {
        super(driver, PageObjectRepoFileName, platform);
    }

    @Step("Verify Push Notification Page Displayed")
    public void verifyPushNotificationPageDisplayed()
    {
        appiumUtils.waitForElementToDisplay("pushNotificationTitle");
    }


    @Step("Click On Enable Button")
    public void clickOnEnableButton()
    {
        appiumUtils.click("enableButton");
    }

    @Step("Click On Not Now Button")
    public void clickOnNotNowButton()
    {
        appiumUtils.click("notNowButton");
    }

    @Step("Verify Push Notification Popup Displayed")
    public void verifyPushNotificationPopupDisplayed()
    {
        appiumUtils.verifyText("alertPopupTitle", "\u201CCovid Watch\u201D Would Like to Send You Notifications");
    }

    @Step("Click Popup Allow Button")
    public void clickAllowButton()
    {
        appiumUtils.click("allowButton");
    }
}

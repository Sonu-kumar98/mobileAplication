package com.impactqa.page.mobile;

import com.impactqa.utilities.PageObjectRepoHelper;
import io.appium.java_client.AppiumDriver;
import io.qameta.allure.Step;

/**
 * @author  Sonu Kumar
 * @since   2021-04-05
 * @description This page conatins all functionality of add covid vaccine Introduction page.
 */
public class  AboutPage extends BasePage {
    private static final String PageObjectRepoFileName = "intialscreens/About.xml";

    public AboutPage(AppiumDriver driver, PageObjectRepoHelper.PLATFORM platform) {
        super(driver, PageObjectRepoFileName, platform);
    }


    @Step("Verify all text on About page")
    public void aboutPage()
    {
        appiumUtils.click("openAboutPage");

    }

    @Step("close  About page")
    public void close()
    {
        appiumUtils.click("close");

    }
}
package com.impactqa.page.mobile;

import com.impactqa.utilities.PageObjectRepoHelper;
import io.appium.java_client.AppiumDriver;
import io.qameta.allure.Step;

public class MenuPage extends BasePage {
    private static final String PageObjectRepoFileName = "MenuPage.xml";

    public MenuPage(AppiumDriver driver, PageObjectRepoHelper.PLATFORM platform) {
        super(driver, PageObjectRepoFileName, platform);
    }

    @Step("Verify Menu Page Displayed")
    public void verifyMenuPageDisplayed()
    {
        appiumUtils.waitForElementToDisplay("myExposuresMenu");
    }


    @Step("Click My Exposure Menu")
    public void clickMyExposureMenuButton()
    {
        appiumUtils.click("myExposuresMenu");
    }

    @Step("Click About Menu")
    public void clickAboutMenuButton()
    {
        appiumUtils.click("aboutMenu");
    }

//    // change by Sonu Kumar
//    @Step("take screen sort of page")
//    public void takeScreenSort()
//    {
//        //appiumUtils.sleepForMiliseconds(6000);
//        appiumUtils.snap("menu page");
//    }

    @Step("take screen sort of page")
    public void scrollDown()
    {
      appiumUtils.scrollDown();
    }

    @Step("Click Past Diagnoses Menu")
    public void clickMyPastDiagnosesMenuButton()
    {
        appiumUtils.click("sharedDiagnosesMenu");
    }

    @Step("Click Detect Exposures From Server Menu")
    public void clickDetectExposuresFromServerMenuButton()
    {
        appiumUtils.click("detectExposuresFromServerMenu");
    }

    @Step("Click Export Exposure Keys Menu")
    public void clickExportExposureKeysMenuButton()
    {
        appiumUtils.click("exportExposureKeysMenu");
    }

    @Step("Click Import Exposure Keys Menu")
    public void clickImportExposureKeysMenuButton()
    {
        appiumUtils.click("importExposureKeysMenu");
    }

    @Step("Click Close Button")
    public void clickCloseButton()
    {
        appiumUtils.click("closeButton");
    }
}

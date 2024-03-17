package com.impactqa.page.web_new;

import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;


public class MenuPageWeb extends BasePage {
    private static final String PageObjectRepoFileName = "MenuPage.xml";

    public MenuPageWeb(WebDriver driver) {
        super(driver, PageObjectRepoFileName);
    }


    @Step("Navigate To Mobile App Settings")
    public void navigateToMobileAppSettings()
    {
        seleniumUtils.waitForThePageLoad();
        seleniumUtils.click("menuIcon");
        seleniumUtils.click("mobileAppSettingsMenuOption");
    }



}

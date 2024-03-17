package com.impactqa.page.web;

import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;

/**
 * @author Sonu Kumar
 * @since   22020-09-12
 * @description Implemented logic to handle FindTextPage functionality and validations
 */
public class MyAccountPage extends BasePage {
    private static final String PageObjectRepoFileName = "MyAccountPage.xml";

    public MyAccountPage(WebDriver driver) {
        super(driver, PageObjectRepoFileName);
    }

    @Step("Verify My Account Page Displayed")
    public void verifyMyAccountPageDisplayed()
    {
        seleniumUtils.click("MyAccountTitle");
    }

    @Step("Click On Change Password Link")
    public void clickChangePasswordLink()
    {
        seleniumUtils.click("changePasswordLink");
    }


}

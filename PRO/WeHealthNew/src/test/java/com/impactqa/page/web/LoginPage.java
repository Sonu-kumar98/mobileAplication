package com.impactqa.page.web;

import com.impactqa.page.web.BasePage;
import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;

/**
 * @author  Sonu Kumar
 * @since   22020-09-12
 * @description Implemented logic to handle FindTextPage functionality and validations
 */
public class LoginPage extends BasePage {
    private static final String PageObjectRepoFileName = "LoginPage.xml";

    public LoginPage(WebDriver driver) {
        super(driver, PageObjectRepoFileName);
    }


    @Step("Login to the application")
    public void doLogin(String username, String password)
    {
        seleniumUtils.waitForThePageLoad();
        seleniumUtils.enterText("usernameTextbox", username);
        seleniumUtils.enterText("passwordTextbox", password);
        seleniumUtils.click("loginButton");
    }

    @Step("Verify Login Error Message Displayed")
    public void verifyLoginErrorMessageDisplayed()
    {
        seleniumUtils.waitForThePageLoad();
        seleniumUtils.waitForElementToDisplay("loginErrorMessage");
    }

}

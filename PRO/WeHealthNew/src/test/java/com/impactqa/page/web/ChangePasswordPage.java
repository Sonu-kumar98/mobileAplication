package com.impactqa.page.web;

import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;

/**
 * @author  Sonu
 * @since   22020-09-12
 * @description Implemented logic to handle FindTextPage functionality and validations
 */
public class ChangePasswordPage extends BasePage {
    private static final String PageObjectRepoFileName = "ChangePasswordPage.xml";

    public ChangePasswordPage(WebDriver driver) {
        super(driver, PageObjectRepoFileName);
    }


    @Step("Verify Change Password Page Displayed")
    public void verifyChangePasswordPageDisplayed()
    {
        seleniumUtils.click("ChangePasswordTitle");
    }

    @Step("Change The Password")
    public void changePassword(String newPassword){
        seleniumUtils.enterText("passwordTextbox", newPassword);
        seleniumUtils.enterText("retypePasswordTextbox", newPassword);
        seleniumUtils.click("setPasswordButton");
    }

    @Step("Set The Password Field")
    public void setNewPasswordField(String newPassword){
        seleniumUtils.enterText("passwordTextbox", newPassword);
    }

    @Step("Set The Re-Password Field")
    public void setRePasswordField(String newPassword){
        seleniumUtils.enterText("retypePasswordTextbox", newPassword);
    }


}

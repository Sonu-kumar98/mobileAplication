package com.impactqa.page.web;

import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;

/**
 * @author  Sonu Kumar
 * @since   22020-09-12
 * @description Implemented logic to handle FindTextPage functionality and validations
 */
public class NewEditUserPage extends BasePage {
    private static final String PageObjectRepoFileName = "NewEditUserPage.xml";

    public NewEditUserPage(WebDriver driver) {
        super(driver, PageObjectRepoFileName);
    }


    @Step("Verify New User Page Displayed")
    public void verifyNewUserPageDisplayed()
    {
        seleniumUtils.click("NewUserTitle");
    }

    @Step("Verify Edit User Page Displayed")
    public void verifyEditUserPageDisplayed()
    {
        seleniumUtils.click("EditUserTitle");
    }

    @Step("Fill Form and Create New User")
    public void fillFormAndCreateNewUser(String userName, String email, String isAdmin)
    {
        setUserName(userName);
        seleniumUtils.enterText("emailTextbox", email);
        setAdminStatus(isAdmin);
        clickSubmitButton();
    }

    @Step("Set Username")
    public void setUserName(String userName)
    {
        seleniumUtils.enterText("userNameTextbox", userName);
    }

    @Step("Set Username")
    public void setAdminStatus(String isAdmin)
    {
        if("Yes".equals(isAdmin))
            seleniumUtils.selectCheckboxJS("adminCheckbox", "check");
        else
            seleniumUtils.selectCheckboxJS("adminCheckbox", "uncheck");
    }

    @Step("Verify Email Is Non Editable")
    public void verifyEmailIsNonEditable()
    {
        String status = seleniumUtils.getAttribute("userNameTextbox", "disabled");
        if(status!=null && status.trim().equals("false"))
            Assert.fail("Email Should Not Be Editable");
    }

    @Step("Click Submit Button")
    public void clickSubmitButton()
    {
        seleniumUtils.click("createUpdateUserButton");
    }

}

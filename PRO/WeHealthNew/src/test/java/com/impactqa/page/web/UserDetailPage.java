package com.impactqa.page.web;

import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;

/**
 * @author Sonu Kumar
 * @since   22020-09-12
 * @description Implemented logic to handle FindTextPage functionality and validations
 */
public class UserDetailPage extends BasePage {
    private static final String PageObjectRepoFileName = "UserDetailPage.xml";

    public UserDetailPage(WebDriver driver) {
        super(driver, PageObjectRepoFileName);
    }

    @Step("Verify User DetailPage Displayed")
    public void verifyUserDetailPageDisplayed()
    {
        seleniumUtils.click("UserDetailTitle");
    }

    @Step("Verify User Data")
    public void verifyUserData(String username, String email, String isAdmin)
    {
        seleniumUtils.verifyText("userName", username);
        seleniumUtils.verifyText("email", email);
        if("Yes".equals(isAdmin))
            seleniumUtils.verifyText("admin", "true");
        else
            seleniumUtils.verifyText("admin", "false");
    }

    @Step("Click Edit Button")
    public void clickEditButton()
    {
        seleniumUtils.javaScriptClick("editLink");
    }


}

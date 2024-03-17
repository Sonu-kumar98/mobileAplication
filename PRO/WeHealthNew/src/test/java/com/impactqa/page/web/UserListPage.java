package com.impactqa.page.web;

import io.qameta.allure.Allure;
import io.qameta.allure.Step;
import io.qameta.allure.model.Status;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;

import java.util.Calendar;
import java.util.Map;

/**
 * @author  Sonu Kumar
 * @since   22020-09-12
 * @description Implemented logic to handle FindTextPage functionality and validations
 */
public class UserListPage extends BasePage {
    private static final String PageObjectRepoFileName = "UserListPage.xml";

    public UserListPage(WebDriver driver) {
        super(driver, PageObjectRepoFileName);
    }


    @Step("Verify User List Page")
    public void verifyUserListPage()
    {
        seleniumUtils.click("UsersTitle");
    }

    @Step("Click New User Button")
    public void clickNewUserButton()
    {
        seleniumUtils.click("NewUserButton");
    }

    @Step("Delete The User")
    public void deleteTheUser(String username)
    {
//        seleniumUtils.click("dynamicUserDeleteLink"
//                ,"Delete Icon Of User - "+username
//                , Map.of("{{useFullName}}", username));
//        seleniumUtils.acceptAlert();
//        if(seleniumUtils.isElementDisplayed("dynamicUserDeleteLink"
//                ,5
//                ,"Delete Icon Of User - "+username
//                , Map.of("{{useFullName}}", username)))
//            Assert.fail("User Should Not be Displayed in List page after delete");
    }

}

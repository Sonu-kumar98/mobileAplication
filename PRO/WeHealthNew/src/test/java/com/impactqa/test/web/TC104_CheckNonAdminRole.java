package com.impactqa.test.web;

import com.impactqa.listeners.TestAllureListener;
import com.impactqa.listeners.TestNGExecutionLister;
import com.impactqa.page.web.*;
import com.impactqa.utilities.ExcelUtil;
import com.impactqa.utilities.FrameworkConfig;
import io.qameta.allure.Description;
import io.qameta.allure.Story;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import java.util.Date;
import java.util.Map;

/**
 * @author  Maqdoom Sharief
 * @since   22020-09-12
 * @description This will validate the functionality of the Find Text Occurrence page.
 * Test data should be provided in the data sheet located src/test/resources/TestData/testdata.xlsx
 * DataID and Browser should be passed from testng.xml
 */

@Listeners({TestAllureListener.class})
public class TC104_CheckNonAdminRole extends BaseTestWeb{

    private Map<String, String> testDataMap;
    static  final String timestamp = String.valueOf(new Date().getTime());
    LoginPage loginPage;
    SelectRealmPage selectRealmPage;
    IssueCodePage issueCodePage;
    MyAccountPage myAccountPage;
    ChangePasswordPage changePasswordPage;
    String newPassword;

    @BeforeMethod
    @Parameters({"dataID"})
    @Description("Read test data with testID {0}")
    public void getTestData(String dataID)
    {

        ExcelUtil excel = new ExcelUtil();
        excel.setWorkbook(FrameworkConfig.getStringEnvProperty("TestDataFileLocation"),
                "TestDataWeb");

        testDataMap = excel.getRowDataMtahcingDataId(dataID);
        if(testDataMap.size()<1)
            Assert.fail("dataID '"+dataID+"' is valid the excel sheet. please check the test data sheet");
    }

    @Test( priority = 1, description = "Check Issue Code Page Loaded")
    @Story("Change Password")
    @Description("Check Issue Code Page Loaded")
    public void checkIssueCodePage()
    {
        loginPage = new LoginPage(driver);
        loginPage.doLogin(testDataMap.get("WebUserName"), testDataMap.get("WebPassword"));

        issueCodePage = new IssueCodePage(driver);
        issueCodePage.clickIssueCodeTab();
        issueCodePage.verifyIssueCodePageDisplayed();
    }

    @Test( priority = 2, dependsOnMethods = {"checkIssueCodePage"}, description = "Verify The Menu List")
    @Story("Change Password")
    @Description("Verify The Menu List")
    public void verifyTheMenuList()
    {
        issueCodePage.clickMenuToggleButton();
        for(String menuName : testDataMap.get("MenuList").split("~"))
        {
            if(!menuName.trim().isEmpty())
                issueCodePage.verifyMenuOption(menuName);
        }
    }

    @Test( priority = 3, dependsOnMethods = {"verifyTheMenuList"}, description = "Change password")
    @Story("Change Password")
    @Description("Change password")
    public void changePassword() {
        issueCodePage.clickMenuOption("My account");
        myAccountPage = new MyAccountPage(driver);
        myAccountPage.clickChangePasswordLink();
        changePasswordPage = new ChangePasswordPage(driver);
        newPassword = testDataMap.get("WebPassword")+"Pass$#1234";
        changePasswordPage.changePassword(newPassword);
        issueCodePage.verifyIssueCodePageDisplayed();

    }

    @Test( priority = 4, dependsOnMethods = {"changePassword"}, description = "Login With Old Password")
    @Story("Change Password")
    @Description("Login With Old Password")
    public void loginWithOldPassword() {
        driver.quit();
        setupDriverInstance(browser);
        loginPage = new LoginPage(driver);
        loginPage.doLogin(testDataMap.get("WebUserName"), testDataMap.get("WebPassword"));
        loginPage.verifyLoginErrorMessageDisplayed();
    }

    @Test( priority = 5, dependsOnMethods = {"loginWithOldPassword"}, description = "Login With New Password")
    @Story("Change Password")
    @Description("Login With New Password")
    public void loginWithNewPassword() {
        loginPage = new LoginPage(driver);
        loginPage.doLogin(testDataMap.get("WebUserName"), newPassword);

        issueCodePage = new IssueCodePage(driver);
        issueCodePage.clickIssueCodeTab();
        issueCodePage.verifyIssueCodePageDisplayed();
    }

    @Test( priority = 6, dependsOnMethods = {"loginWithNewPassword"}, description = "Revert The Password")
    @Story("Change Password")
    @Description("Revert The Password")
    public void revertThePassword() {
        issueCodePage.clickMenuToggleButton();
        issueCodePage.clickMenuOption("My account");
        myAccountPage = new MyAccountPage(driver);
        myAccountPage.clickChangePasswordLink();
        changePasswordPage = new ChangePasswordPage(driver);
        changePasswordPage.changePassword(testDataMap.get("WebPassword"));
        issueCodePage.verifyIssueCodePageDisplayed();
    }

}

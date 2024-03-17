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

import java.util.Calendar;
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
public class TC103_CheckAdminRole extends BaseTestWeb{

    private Map<String, String> testDataMap;
    static  final String timestamp = String.valueOf(new Date().getTime());
    LoginPage loginPage;
    SelectRealmPage selectRealmPage;
    IssueCodePage issueCodePage;
    UserListPage userListPage;
    NewEditUserPage newEditUserPage;
    UserDetailPage userDetailPage;


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
    @Story("Admin Role")
    @Description("Check Issue Code Page Loaded")
    public void checkIssueCodePage()
    {
        loginPage = new LoginPage(driver);
        loginPage.doLogin(testDataMap.get("WebUserName"), testDataMap.get("WebPassword"));

        selectRealmPage = new SelectRealmPage(driver);
        selectRealmPage.verifyRealmPageDisplayed();
        selectRealmPage.selectRealm(testDataMap.get("Realm").trim());
        issueCodePage = new IssueCodePage(driver);
        issueCodePage.clickIssueCodeTab();
        issueCodePage.verifyIssueCodePageDisplayed();
    }

    @Test( priority = 2, dependsOnMethods = {"checkIssueCodePage"}, description = "Verify The Menu List")
    @Story("Admin Role")
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

    @Test( priority = 3, dependsOnMethods = {"verifyTheMenuList"}, description = "Navigate To User List Page")
    @Story("Admin Role")
    @Description("Navigate To User List Page")
    public void navigateToUserListPage()
    {
        issueCodePage.clickMenuOption("Users");
        userListPage = new UserListPage(driver);
        userListPage.verifyUserListPage();
    }

    @Test( priority = 4, dependsOnMethods = {"navigateToUserListPage"}, description = "Create New Non-Admin User")
    @Story("Admin Role")
    @Description("Create New Non-Admin User")
    public void createNewNonAdminUser()
    {
        userListPage.clickNewUserButton();
        newEditUserPage = new NewEditUserPage(driver);
        newEditUserPage.verifyNewUserPageDisplayed();
        newEditUserPage.fillFormAndCreateNewUser("userName_"+timestamp,
                                                    "email_"+timestamp+"@covidwatch.org",
                                                            "No");
        userDetailPage = new UserDetailPage(driver);
        userDetailPage.verifyUserDetailPageDisplayed();
        userDetailPage.verifyUserData("userName_"+timestamp,
                "email_"+timestamp+"@covidwatch.org",
                "No");

    }
    @Test( priority = 5, dependsOnMethods = {"createNewNonAdminUser"}, description = "Edit Non-Admin User")
    @Story("Admin Role")
    @Description("Edit Non-Admin User")
    public void editNonAdminUser()
    {
        userDetailPage.clickEditButton();
        newEditUserPage.verifyEditUserPageDisplayed();
        newEditUserPage.setUserName("User_Edit_"+timestamp);
        newEditUserPage.verifyEmailIsNonEditable();
        newEditUserPage.setAdminStatus("Yes");
        newEditUserPage.clickSubmitButton();
        userListPage.verifyUserListPage();
    }

    @Test( priority = 6, dependsOnMethods = {"editNonAdminUser"}, description = "Delete The User1")
    @Story("Admin Role")
    @Description("Delete The User1")
    public void deleteTheUser1()
    {
        userListPage.deleteTheUser("User_Edit_"+timestamp);
    }

    @Test( priority = 7, dependsOnMethods = {"deleteTheUser1"}, description = "Create New Admin User")
    @Story("Admin Role")
    @Description("Create New Admin User")
    public void createNewAdminUser()
    {
        userListPage.clickNewUserButton();
        newEditUserPage = new NewEditUserPage(driver);
        newEditUserPage.verifyNewUserPageDisplayed();
        newEditUserPage.fillFormAndCreateNewUser("userNameAdmin_"+timestamp,
                "emailAdmin_"+timestamp+"@covidwatch.org",
                "Yes");
        userDetailPage = new UserDetailPage(driver);
        userDetailPage.verifyUserDetailPageDisplayed();
        userDetailPage.verifyUserData("userNameAdmin_"+timestamp,
                "emailAdmin_"+timestamp+"@covidwatch.org",
                "Yes");

    }
    @Test( priority = 8, dependsOnMethods = {"createNewAdminUser"}, description = "Edit Admin User")
    @Story("Admin Role")
    @Description("Edit Admin User")
    public void editAdminUser()
    {
        userDetailPage.clickEditButton();
        newEditUserPage.verifyEditUserPageDisplayed();
        newEditUserPage.setUserName("userNameAdmin_Edit_"+timestamp);
        newEditUserPage.verifyEmailIsNonEditable();
        newEditUserPage.setAdminStatus("No");
        newEditUserPage.clickSubmitButton();
        userListPage.verifyUserListPage();
    }

    @Test( priority = 9, dependsOnMethods = {"editAdminUser"}, description = "Delete The User2")
    @Story("Admin Role")
    @Description("Delete The User2")
    public void deleteTheUser2()
    {
        userListPage.deleteTheUser("userNameAdmin_Edit_"+timestamp);
    }

}

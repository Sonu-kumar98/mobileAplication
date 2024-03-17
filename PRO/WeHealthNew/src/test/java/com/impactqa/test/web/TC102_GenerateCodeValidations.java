package com.impactqa.test.web;

import com.impactqa.listeners.TestAllureListener;
import com.impactqa.listeners.TestNGExecutionLister;
import com.impactqa.page.web.IssueCodePage;
import com.impactqa.page.web.LoginPage;
import com.impactqa.page.web.SelectRealmPage;
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
import java.util.Map;

/**
 * @author  Maqdoom Sharief
 * @since   22020-09-12
 * @description This will validate the functionality of the Find Text Occurrence page.
 * Test data should be provided in the data sheet located src/test/resources/TestData/testdata.xlsx
 * DataID and Browser should be passed from testng.xml
 */

@Listeners({TestAllureListener.class})
public class TC102_GenerateCodeValidations extends BaseTestWeb{

    private Map<String, String> testDataMap;
    LoginPage loginPage;
    SelectRealmPage selectRealmPage;
    IssueCodePage issueCodePage;
    Calendar cal;
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

    @Test( priority = 1, description = "Check IssueCode Page")
    @Story("Issue Code")
    @Description("Check IssueCode Page")
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
        issueCodePage.verifyTheFieldsInIssueCode();
    }

    @Test( priority = 2, dependsOnMethods = {"checkIssueCodePage"}, description = "Case1: Symptom date too far in the past")
    @Story("Issue Code")
    @Description("Case1: Symptom date too far in the past")
    public void symptomDateTooFarInThePast()
    {
        cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -16);
        issueCodePage.setSymptomsDate(cal);
        issueCodePage.clickSubmitButton();
        issueCodePage.getSeleniumUtils().sleep(1000);
        issueCodePage.veryVerificationCodeNotDisplayed();
    }

    @Test( priority = 3, dependsOnMethods = {"symptomDateTooFarInThePast"}, description = "Case2: Tested date too far in the past")
    @Story("Issue Code")
    @Description("Case2: Tested date too far in the past")
    public void testedDateTooFarInThePast()
    {
        issueCodePage.clickIssueCodeTab();
        cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -16);
        issueCodePage.setTestedDate(cal);
        issueCodePage.clickSubmitButton();
        issueCodePage.getSeleniumUtils().sleep(1000);
        issueCodePage.veryVerificationCodeNotDisplayed();
    }

    @Test( priority = 4, dependsOnMethods = {"testedDateTooFarInThePast"}, description = "Case3: Symptom Date Is Set To Future Date")
    @Story("Issue Code")
    @Description("Case3: Symptom Date Is Set To Future Date")
    public void symptomDateSetToFutureDate()
    {
        issueCodePage.clickIssueCodeTab();
        cal = Calendar.getInstance();
        cal.add(Calendar.DATE, +2);
        issueCodePage.setSymptomsDate(cal);
        issueCodePage.clickSubmitButton();
        issueCodePage.getSeleniumUtils().sleep(1000);
        issueCodePage.veryVerificationCodeNotDisplayed();
    }


    @Test( priority = 5, dependsOnMethods = {"symptomDateSetToFutureDate"}, description = "Case4: Tested Date Is Set To Future Date")
    @Story("Issue Code")
    @Description("Case4: Tested Date Is Set To Future Date")
    public void testedDateSetToFutureDate()
    {
        issueCodePage.clickIssueCodeTab();
        cal = Calendar.getInstance();
        cal.add(Calendar.DATE, +2);
        issueCodePage.setTestedDate(cal);
        issueCodePage.clickSubmitButton();
        issueCodePage.getSeleniumUtils().sleep(1000);
        issueCodePage.veryVerificationCodeNotDisplayed();
    }

    @Test( priority = 6, dependsOnMethods = {"testedDateSetToFutureDate"}, description = "Case5: Both Tested And Symptoms Dates Are Set Within The Past 14 Days")
    @Story("Issue Code")
    @Description("Case5: Both Tested And Symptoms Dates Are Set Within The Past 14 Days")
    public void bothTestedAndSymptomsDatesAreSetWithinPast14Days()
    {
        issueCodePage.clickIssueCodeTab();
        cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -1);
        issueCodePage.setSymptomsDate(cal);

        cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -14);
        issueCodePage.setTestedDate(cal);

        issueCodePage.clickSubmitButton();
        issueCodePage.getSeleniumUtils().sleep(1000);
        issueCodePage.veryVerificationCodeDisplayed();

    }


    @Test( priority = 7, dependsOnMethods = {"bothTestedAndSymptomsDatesAreSetWithinPast14Days"}, description = "Check Clicking On Reset Button Shows the Issue Code Page")
    @Story("Issue Code")
    @Description("Check Clicking On Reset Button Shows the Issue Code Page")
    public void checkResetButton()
    {
        issueCodePage.clickResetButton();
        issueCodePage.verifyIssueCodePageDisplayed();
    }
}

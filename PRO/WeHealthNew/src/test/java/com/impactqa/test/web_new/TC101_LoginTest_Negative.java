package com.impactqa.test.web_new;

import com.impactqa.listeners.TestAllureListener;
import com.impactqa.page.web_new.LoginPage;
import com.impactqa.utilities.ExcelUtil;
import com.impactqa.utilities.FrameworkConfig;
import io.qameta.allure.Description;
import io.qameta.allure.Story;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import java.util.Map;

/**
 * @author  Maqdoom Sharief
 * @since   22020-09-12
 * @description This will validate the functionality of the Find Text Occurrence page.
 * Test data should be provided in the data sheet located src/test/resources/TestData/testdata.xlsx
 * DataID and Browser should be passed from testng.xml
 */

@Listeners({TestAllureListener.class})
public class TC101_LoginTest_Negative extends BaseTestWeb {

    private Map<String, String> testDataMap;

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

    @Test( description = "Generate Verification Code From Web Portal")
    @Story("Authorization")
    @Description("Check login functionality with invalid credentials")
    public void loginTestWithInvalidCredentials()
    {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.doLogin(testDataMap.get("WebUserName"), testDataMap.get("WebPassword"));
        loginPage.verifyLoginErrorMessageDisplayed();

    }
}

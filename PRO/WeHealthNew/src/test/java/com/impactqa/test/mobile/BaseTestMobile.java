package com.impactqa.test.mobile;

import com.impactqa.utilities.AppiumUtils;
import com.impactqa.utilities.DriverProvider;
import com.impactqa.utilities.ExcelUtil;
import com.impactqa.utilities.FrameworkConfig;
import com.impactqa.utilities.PageObjectRepoHelper;
import io.appium.java_client.AppiumDriver;
import io.qameta.allure.Allure;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;

import java.io.ByteArrayInputStream;
import java.net.MalformedURLException;
import java.util.Map;

/**
 * @author  Maqdoom Sharief
 * @since   22020-11-10
 * @description All Test classes should be extended by this class. It will manage browser sessions before and after each test case execution
 */
public class BaseTestMobile {

    AppiumDriver driver;
    PageObjectRepoHelper.PLATFORM platform;
    Map<String, String> testDataMap;

    @BeforeTest(description = "Open new mobile session")
    @Parameters({"dataID"})
    public void openMobileSession( String dataID) throws MalformedURLException {

        AppiumUtils appiumUtils;

        ExcelUtil excel = new ExcelUtil();
        excel.setWorkbook(FrameworkConfig.getStringEnvProperty("TestDataFileLocation"),
                FrameworkConfig.getStringEnvProperty("TestDataSheetName"));

        testDataMap = excel.getRowDataMtahcingDataId(dataID);
        if(testDataMap.size()<1)
            Assert.fail("dataID '"+dataID+"' is valid the excel sheet. please check the test data sheet");

        excel.setWorkbook(FrameworkConfig.getStringEnvProperty("TestDataFileLocation"),
                "MobileSessionDetails");

        Map<String, String> sessionDetails = excel.getRowDataMtahcingDataId(testDataMap.get("MobileSessionID1"));


        if("ios".equals(sessionDetails.get("sessionDetails").toLowerCase()))
            platform=PageObjectRepoHelper.PLATFORM.IOS;
        else
            platform=PageObjectRepoHelper.PLATFORM.ANDROID;
        driver = DriverProvider.createNewMobileSession(platform, sessionDetails);
    }

    public AppiumDriver getDriver() {
        return driver;
    }


//    @AfterMethod(description = "Get Screenshot for failed cases")
//    public void afterMethodFailed(ITestResult result) {
//        if(ITestResult.FAILURE ==result.getStatus()){
//            TakesScreenshot tk = (TakesScreenshot) driver;
//            byte[] b = tk.getScreenshotAs(OutputType.BYTES);
//            Allure.addAttachment("Screenshot", "image/png", new ByteArrayInputStream(b), "png");
//        }
//    }
//
//
//
//    //Added for reference. If you don't want screenshots for passed statements, then set enabled flag as false
//    @AfterMethod(enabled = true, description = "Get Screenshot for passed cases")
//    public void afterMethodPassed(ITestResult result) {
//        if(ITestResult.SUCCESS ==result.getStatus()){
//            TakesScreenshot tk = (TakesScreenshot) driver;
//            byte[] b = tk.getScreenshotAs(OutputType.BYTES);
//            Allure.addAttachment("Screenshot", "image/png", new ByteArrayInputStream(b), "png");
//        }
//    }

    @AfterTest(description = "close the mobile session")
    public void teardownDriverInstance()
    {
        if(driver!=null)
            driver.quit();
    }
}

package com.impactqa.test.web;

import com.impactqa.utilities.FrameworkConfig;
import io.qameta.allure.Allure;
import io.qameta.allure.Description;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.ITestResult;
import org.testng.annotations.*;

import java.io.ByteArrayInputStream;

/**
 * @author  Maqdoom Sharief
 * @since   22020-11-10
 * @description All Test classes should be extended by this class. It will manage browser sessions before and after each test case execution
 */
public class BaseTestWeb {

    protected WebDriver driver;
    protected String browser;

    @BeforeTest(description = "Open new browser session")
    @Parameters({"browser"})
    public void setupDriverInstance(String browser)
    {
        this.browser =browser;
        switch(browser.toLowerCase())
        {
            case "chrome":
                driver = new ChromeDriver();
                break;
            case "firefox":
                driver = new FirefoxDriver();
                break;
            default:
                driver = new ChromeDriver();
                break;
        }

        driver.get(FrameworkConfig.getStringEnvProperty("ApplicationURL"));
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
//    //Added for reference. If you don't want screenshots for passed statements, then set enabled flag as false
//    @AfterMethod(enabled = true, description = "Get Screenshot for passed cases")
//    public void afterMethodPassed(ITestResult result) {
//        if(ITestResult.SUCCESS ==result.getStatus()){
//            TakesScreenshot tk = (TakesScreenshot) driver;
//            byte[] b = tk.getScreenshotAs(OutputType.BYTES);
//            Allure.addAttachment("Screenshot", "image/png", new ByteArrayInputStream(b), "png");
//        }
//    }

    @AfterTest(description = "close the browser session")
    public void teardownDriverInstance()
    {
        if(driver!=null)
            driver.quit();
    }

    public WebDriver getDriver()
    {
        return driver;
    }
}

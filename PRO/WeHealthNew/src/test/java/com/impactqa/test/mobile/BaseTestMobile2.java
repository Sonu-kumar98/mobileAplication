package com.impactqa.test.mobile;

import com.impactqa.utilities.DriverProvider;
import com.impactqa.utilities.ExcelUtil;
import com.impactqa.utilities.FrameworkConfig;
import com.impactqa.utilities.PageObjectRepoHelper;
import io.appium.java_client.AppiumDriver;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;

import java.net.MalformedURLException;
import java.util.Map;

/**
 * @author Sonu Kumar
 *
 * @description All Test classes should be extended by this class. It will manage browser sessions before and after each test case execution
 */
public class BaseTestMobile2 {

    AppiumDriver driver;
    PageObjectRepoHelper.PLATFORM platform;
    Map<String, String> testDataMap;
    Map<String, String> mobileSessionDetails;


    public void openMobileSession( String mobSessionId) throws MalformedURLException {

        ExcelUtil excel = new ExcelUtil();
        excel.setWorkbook(FrameworkConfig.getStringEnvProperty("TestDataFileLocation"),
                "MobileSessionDetails");

        Map<String, String> sessionDetails = excel.getRowDataMtahcingDataId(mobSessionId);

        if("ios".equals(sessionDetails.get("platformName").toLowerCase()))
            platform=PageObjectRepoHelper.PLATFORM.IOS;
        else
            platform=PageObjectRepoHelper.PLATFORM.ANDROID;
        driver = DriverProvider.createNewMobileSession(platform, sessionDetails);
    }

    public void openMobileSessionDeviceSettings( String mobSessionId) throws MalformedURLException {
        ExcelUtil excel = new ExcelUtil();
        excel.setWorkbook(FrameworkConfig.getStringEnvProperty("TestDataFileLocation"),
                "MobileSessionDetails");

        Map<String, String> sessionDetails = excel.getRowDataMtahcingDataId(mobSessionId);


        if("ios".equals(sessionDetails.get("platformName").toLowerCase())){
            platform=PageObjectRepoHelper.PLATFORM.IOS;
            sessionDetails.put("appFileName", "settings");
        }else {
            platform = PageObjectRepoHelper.PLATFORM.ANDROID;
            if(sessionDetails.containsKey("appFileName"))
                sessionDetails.remove("appFileName");
            sessionDetails.put("appActivity", "com.android.settings.Settings");
        }

        driver = DriverProvider.createNewMobileSession(platform, sessionDetails);
    }


    public AppiumDriver getDriver() {
        return driver;
    }

    public void setCurrentDriver(AppiumDriver driver)
    {
        this.driver=driver;
    }

    @AfterTest(description = "close the mobile session")
    public void teardownDriverInstance()
    {
        if(driver!=null)
            driver.quit();
    }

}

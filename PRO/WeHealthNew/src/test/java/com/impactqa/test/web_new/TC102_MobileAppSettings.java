package com.impactqa.test.web_new;

import com.impactqa.listeners.TestAllureListener;
import com.impactqa.page.web_new.LoginPage;
import com.impactqa.page.web_new.MenuPageWeb;
import com.impactqa.page.web_new.MobileAppSettingsPage;
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


@Listeners({TestAllureListener.class})
public class TC102_MobileAppSettings extends BaseTestWeb {

    private Map<String, String> testDataMap;
    LoginPage loginPage;
    MobileAppSettingsPage mobileAppSettingsPage;
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

    @Test( priority = 1, description = "Login To The Application")
    @Story("Mobile App Settings")
    @Description("Login To The Application")
    public void loginToTheApplication()
    {
        loginPage = new LoginPage(driver);
        loginPage.doLogin(testDataMap.get("WebUserName"), testDataMap.get("WebPassword"));
    }

    @Test( priority = 2, dependsOnMethods = {"loginToTheApplication"}, description = "Set Mobile App Settings For The Region")
    @Story("Mobile App Settings")
    @Description("Set Mobile App Settings For The Region")
    public void setMobileAppSettingsForTheRegion()
    {
        MenuPageWeb menuPage = new MenuPageWeb(driver);
        menuPage.navigateToMobileAppSettings();

        mobileAppSettingsPage = new MobileAppSettingsPage(driver);
        mobileAppSettingsPage.selectRegion(testDataMap.get("Region"));
        mobileAppSettingsPage.setNoSignificantExposureMessages(testDataMap.get("Region"));
        mobileAppSettingsPage.saveAndSubmitChanges();
    }

    @Test( priority = 3, dependsOnMethods = {"setMobileAppSettingsForTheRegion"}, description = "Validate the changes in the mobile app")
    @Story("Mobile App Settings")
    @Description("Validate the changes in the mobile app")
    public void ValidateTheChangesInTheMobileApp()
    {

    }




}

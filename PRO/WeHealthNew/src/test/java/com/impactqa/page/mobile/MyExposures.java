package com.impactqa.page.mobile;

import com.impactqa.utilities.PageObjectRepoHelper;
import io.appium.java_client.AppiumDriver;
import io.qameta.allure.Allure;
import io.qameta.allure.Step;
import org.testng.Assert;

import java.text.SimpleDateFormat;
import java.util.Calendar;


public class MyExposures extends BasePage {
    private static final String PageObjectRepoFileName = "MyExposures.xml";

    public MyExposures(AppiumDriver driver, PageObjectRepoHelper.PLATFORM platform) {
        super(driver, PageObjectRepoFileName, platform);
    }

    @Step("Verify My Exposures Page Displayed")
    public void verifyMyExposuresDisplayed()
    {
        appiumUtils.waitForElementToDisplay("MyExposuresTitle");
    }



    @Step("Verify No Exposure Message Displayed")
    public void verifyNoExposureMessageDisplayed()
    {
        appiumUtils.click("summaryTab");

        appiumUtils.waitForElementToDisplay("noExposureDates");

        Assert.assertFalse(appiumUtils.isElementDisplayed("highExposureDates", 5),
                            "High Exposure Should Not Be Displayed" );

        Assert.assertFalse(appiumUtils.isElementDisplayed("lowExposureDates", 5),
                "Low Exposure Should Not Be Displayed" );



        appiumUtils.click("dailyTab");
        if(appiumUtils.getPlatform()== PageObjectRepoHelper.PLATFORM.ANDROID)
            appiumUtils.verifyText("noExposureMessage", "No Exposures Detected\nPlease check back later to see details of possible exposures.");
        else
            appiumUtils.verifyText("noExposureMessage", "No Exposures Detected");

    }


    @Step("Verify High Exposure Displayed In Summary Page")
    public void verifyHighExposureMessageDisplayedInSummaryPage(Calendar expectedDateCal)
    {
        appiumUtils.click("summaryTab");

//        appiumUtils.waitForElementToDisplay("highExposureDates");
//        String actualDate = appiumUtils.getAttribute("highExposureDates", "content-desc");
//        String expectedDate =  "High Exposure on "+new SimpleDateFormat("MMM dd, yyyy").format(expectedDateCal.getTime());
//        Assert.assertTrue(actualDate.contains(expectedDate), "High exposure date is not matched.\n ActualDate: '" + actualDate+ "'. Expected: '"+expectedDate+"'\n");
//        Allure.step("Passed: "+actualDate);
//        appiumUtils.attachScreenShotToTheReport("MyExposureSummaryPage");
    }

    @Step("Verify Low Exposure Displayed In Summary Page")
    public void verifyLowExposureMessageDisplayedInSummaryPage(Calendar expectedDateCal)
    {
        appiumUtils.click("summaryTab");
        appiumUtils.waitForElementToDisplay("lowExposureDates");
        String actualDate = appiumUtils.getAttribute("lowExposureDates", "content-desc");
        String expectedDate =  "No hubo exposición en "+new SimpleDateFormat("MMM dd, yyyy").format(expectedDateCal.getTime());
       // String expectedDate =  "Low Exposure on "+new SimpleDateFormat("MMM dd, yyyy").format(expectedDateCal.getTime());
//        Assert.assertTrue(actualDate.contains(expectedDate), "Low exposure date is not matched.\n ActualDate: '" + actualDate+ "'. Expected: '"+expectedDate+"'\n");
//        Allure.step("Passed: "+actualDate);
        appiumUtils.attachScreenShotToTheReport("MyExposureSummaryPage");
    }

    @Step("Verify High Exposure Displayed In Daily Page")
    public void verifyHighExposureMessageDisplayedInDailyPage(Calendar expectedDateCal)
    {
        appiumUtils.click("dailyTab");

        appiumUtils.click("dailyMessageTitle");
//        String actualDate = appiumUtils.getAttribute("dailyMessageTitle", "text");
//        String expectedDate =  new SimpleDateFormat("MMM dd, yyyy").format(expectedDateCal.getTime())+": High Exposure";
//        Assert.assertTrue(actualDate.contains(expectedDate), "High exposure date is not matched.\n ActualDate: '" + actualDate+ "'. Expected: '"+expectedDate+"'\n");
//        Allure.step("Passed: "+actualDate);
//        appiumUtils.attachScreenShotToTheReport("MyExposureDailyPage");
}

    @Step("Verify Low Exposure Displayed In Daily Page")
    public void verifyLowExposureMessageDisplayedInDailyPage(Calendar expectedDateCal)
    {
        appiumUtils.click("dailyTab");

        appiumUtils.click("dailyMessageTitle");
//        String actualDate = appiumUtils.getAttribute("dailyMessageTitle", "text");
//        String expectedDate =  new SimpleDateFormat("MMM dd, yyyy").format(expectedDateCal.getTime())+": Low Exposure";
//        String expectedDate =  new SimpleDateFormat("MMM. dd, yyyy").format(expectedDateCal.getTime())+": Baja exposición";
//        Assert.assertTrue(actualDate.contains(expectedDate), "Low exposure date is not matched.\n ActualDate: '" + actualDate+ "'. Expected: '"+expectedDate+"'\n");
//        Allure.step("Passed: "+actualDate);
       // appiumUtils.attachScreenShotToTheReport("MyExposureDailyPage");
    }

    @Step("Click Close Button")
    public void ClickDailyTab()  {
        appiumUtils.click("dailyTab");



    }
    @Step("Click Close Button")
    public void clickCloseButton()
    {
        appiumUtils.sleepForMiliseconds(5000);
        appiumUtils.click("closeButton");
    }

}

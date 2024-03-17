package com.impactqa.page.mobile;
import com.impactqa.utilities.PageObjectRepoHelper;
import io.appium.java_client.AppiumDriver;
import io.qameta.allure.Step;
import org.testng.Assert;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class SharedDiagnoses extends BasePage {
    private static final String PageObjectRepoFileName = "SharedDiagnoses.xml";

    public SharedDiagnoses(AppiumDriver driver, PageObjectRepoHelper.PLATFORM platform) {
        super(driver, PageObjectRepoFileName, platform);
    }

    @Step("Verify Shared Diagnoses Page Displayed")
    public void verifySharedDiagnosesPageDisplayed()
    {
        appiumUtils.waitForElementToDisplay("PastDiagnosesTitle");
    }


    @Step("Verify No Past Diagnoses Message Displayed")
    public void verifyNoPastPositiveDiagnosesMessageDisplayed()
    {

        if(appiumUtils.getPlatform()== PageObjectRepoHelper.PLATFORM.ANDROID)

            appiumUtils.verifyText("noPastPositiveDiagnosesText", "No Shared Diagnoses");
            //appiumUtils.verifyText("noPastPositiveDiagnosesText", "No Shared Diagnoses\nPlease check back later when you submit a diagnosis.");
        else
            appiumUtils.verifyText("noPastPositiveDiagnosesText", "No Shared Diagnoses");

    }

    @Step("Verify No Past Diagnoses Message Displayed")
    public void verifyNoPastPositiveDiagnosesMessageIsNotDisplayed()
    {
        if(appiumUtils.isElementDisplayed("noPastPositiveDiagnosesText", 5))
            Assert.fail("No Past Diagnoses Message Should not be Displayed");
    }

    @Step("Verify Past Diagnoses Data Displayed")
    public void verifyPastDiagnosesDataDisplayedDisplayed(Calendar submitDate, Calendar calTestDate, Calendar calSymtomsDate)
    {
        if(appiumUtils.getPlatform()== PageObjectRepoHelper.PLATFORM.IOS) {
            String submitDateStr = new SimpleDateFormat("d-MMM-yyyy").format(submitDate.getTime());
            String testDateStr = calTestDate != null ? new SimpleDateFormat("d-MMM-yyyy").format(calTestDate.getTime()) : "N/A";
            String symtomsDateStr = calSymtomsDate != null ? new SimpleDateFormat("d-MMM-yyyy").format(calSymtomsDate.getTime()) : "N/A";

            appiumUtils.click("covidPositiveDiagnosesText");
            appiumUtils.verifyPartialText("covidPositiveDiagnosesText", "Submit Date:  " + submitDateStr);

            appiumUtils.verifyPartialText("covidPositiveDiagnosesDetails", "Symptoms Start Date: , " + symtomsDateStr);
            appiumUtils.verifyPartialText("covidPositiveDiagnosesDetails", "Test Date: , " + testDateStr);
        }
        else
        {
            String submitDateStr = new SimpleDateFormat("MMM dd, yyyy").format(submitDate.getTime());
            String testDateStr = calTestDate != null ? new SimpleDateFormat("MMM dd, yyyy").format(calTestDate.getTime()) : "Unknown";
            String symtomsDateStr = calSymtomsDate != null ? new SimpleDateFormat("MMM dd, yyyy").format(calSymtomsDate.getTime()) : "Unknown";

            appiumUtils.click("covidPositiveDiagnosesText");
            appiumUtils.verifyPartialText("covidPositiveDiagnosesDetailsSubmitDate_Android", "Submitted: " + submitDateStr);

            appiumUtils.verifyPartialText("covidPositiveDiagnosesDetailsSymptomsDate_Android", "Symptom onset date: " + symtomsDateStr);
            appiumUtils.verifyPartialText("covidPositiveDiagnosesDetailsTestDate_Android", "Test date: " + "N/A");
           // appiumUtils.verifyPartialText("covidPositiveDiagnosesDetailsTestDate_Android", "Test date: " +calSymtomsDate );

        }
    }

    // only change by multilanguage support for in spanish language.

    @Step("Click Expand Arrow Button")
    public void clickDropDown()
    {
        appiumUtils.click("expandArrow");
    }
    @Step("Click Close Button")
    public void clickCloseButton()
    {
        appiumUtils.click("closeButton");
    }
}

package com.impactqa.page.mobile;

import com.impactqa.utilities.PageObjectRepoHelper;
import io.appium.java_client.AppiumDriver;
import io.qameta.allure.Step;
import org.testng.Assert;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;

/**
 * @author  Sonu Kumar
 * @since   2021-03-05
 * @description This page conatins all functionality of add covid vaccine Introduction page.
 */
public class AnonymouslySharePositiveDiagnosis extends BasePage {
    private static final String PageObjectRepoFileName = "AnonymouslySharePositiveDiagnosis.xml";

    public AnonymouslySharePositiveDiagnosis(AppiumDriver driver, PageObjectRepoHelper.PLATFORM platform) {
        super(driver, PageObjectRepoFileName, platform);
    }

    @Step("Verify Anonymously Share Positive Diagnosis Page Displayed")
    public void verifyAnonymouslySharePositiveDiagnosisPageDisplayed()
    {
        appiumUtils.waitForElementToDisplay("anonymouslySharePositiveDiagnosisPageTitle");
    }


    @Step("Enter Verification Code")
    public void enterVerificationCode(String verifCode)
    {
        appiumUtils.enterText("enterVerificationCodeTextbox", verifCode);
    }

    @Step("Select Symptoms Date")
    public void selectSymptomsDate(Calendar cal)
    {
        Date date = cal.getTime();
        String fD=  new SimpleDateFormat("d").format(date);
        String fMM=  new SimpleDateFormat("MM").format(date);
        String fMMM=  new SimpleDateFormat("MMM").format(date);
        String fMMMM=  new SimpleDateFormat("MMMM").format(date);
        String fYYYY=  new SimpleDateFormat("yyyy").format(date);

        appiumUtils.click("selectSymptomsDate");
        if(appiumUtils.getPlatform()== PageObjectRepoHelper.PLATFORM.ANDROID)
        {
//            appiumUtils.click("dynamicCalendarDay", "Click day ("+fD+") in Calender"
//                    , Map.of("{{D}}",fD));
//                            ,"{{MM}}",fMM
//                            ,"{{MMM}}",fMMM
//                            ,"{{MMMM}}",fMMMM
//                            ,"{{YYYY}}",fYYYY));

            //-"+fMMM+"

            appiumUtils.click("calendarOkButton_Android");
        }
        else
        {
//            appiumUtils.click("dynamicCalendarDay", "Click day ("+fD+"-"+fMMMM+"-"+fYYYY+") in Calender"
//                    , Map.of("{{MMMM}}",fMMMM.toUpperCase()
//                            ,"{{YYYY}}",fYYYY
//                            , "{{D}}", fD));
            System.out.println("jjf");

        }
    }

    @Step("Select No Symptoms Checkbox")
    public void selectNoSymptomsCheckbox(String status)
    {
        Boolean  currentCheckedStatus = null;
        if (appiumUtils.getPlatform() == PageObjectRepoHelper.PLATFORM.IOS)
            currentCheckedStatus = appiumUtils.isElementDisplayed("testedDate", 3);
        else
            currentCheckedStatus = appiumUtils.getAttribute("noSymptomsCheckbox", "checked").equals("true");

        if("check".equals(status) && !currentCheckedStatus)
            appiumUtils.click("noSymptomsCheckbox");

        if("uncheck".equals(status) && currentCheckedStatus)
            appiumUtils.click("noSymptomsCheckbox");

    }

    @Step("Select Tested Date")
    public void selectTestedDate(Calendar cal)
    {
        Date date = cal.getTime();
        String fD=  new SimpleDateFormat("d").format(date);
        String fMMM=  new SimpleDateFormat("MMM").format(date);
        String fMMMM=  new SimpleDateFormat("MMMM").format(date);
        String fYYYY=  new SimpleDateFormat("yyyy").format(date);

        appiumUtils.click("testedDate");
        if(appiumUtils.getPlatform()== PageObjectRepoHelper.PLATFORM.ANDROID)
        {
//            appiumUtils.click("dynamicCalendarDay", "Click day ("+fD+"-"+fMMM+") in Calender"
//                    , Map.of("{{D}}",fD
//                            ,"{MMM}",fMMM
//                            ,"{{MMMM}}",fMMMM
//                            ,"{{YYYY}}",fYYYY));
            System.out.println("jjf");

            appiumUtils.click("calendarOkButton_Android");
        }
        else
        {
//            appiumUtils.click("dynamicCalendarDay", "Click day ("+fD+"-"+fMMMM+"-"+fYYYY+") in Calender"
//                    , Map.of("{{MMMM}}",fMMMM.toUpperCase()
//                            ,"{{YYYY}}",fYYYY
//                            , "{{D}}", fD));
            System.out.println("jjf");

        }
    }

    @Step("Click Anonymously Share Diagnosis Button")
    public void clickAnonymouslyShareDiagnosisButton()
    {
        appiumUtils.click("anonymouslyShareDiagnosisButton");
    }

    @Step("Verify Anonymously Share Diagnosis Button Is Not Displayed")
    public void verifyAnonymouslyShareDiagnosisButtonIsNotDisplayed()
    {
        Assert.assertFalse(appiumUtils.isElementDisplayed("anonymouslyShareDiagnosisButton", 5),
                            "Anonymously Share Diagnosis Button Should Not Be Displayed");
    }


    @Step("Click Share button and wait for confirmation")
    public void clickShareButtonAndWaitForConfirmation()
    {
        appiumUtils.isElementDisplayed("shareButton", 20);
        appiumUtils.click("shareButton");
//        appiumUtils.isElementDisplayed("DiagnosisSharedTitle", 60);
//        appiumUtils.waitForElementToDisplay("DiagnosisSharedTitle");
//        appiumUtils.waitForElementToDisplay("DiagnosisSharedThanksMessage");
        appiumUtils.attachScreenShotToTheReport("Screenshot");

    }


    @Step("Wait for confirmation")
    public void waitForConfirmation()
    {
        appiumUtils.isElementDisplayed("DiagnosisSharedTitle", 60);
        appiumUtils.waitForElementToDisplay("DiagnosisSharedTitle");
        appiumUtils.waitForElementToDisplay("DiagnosisSharedThanksMessage");
        appiumUtils.attachScreenShotToTheReport("Screenshot");
    }

    @Step("Click Share button and wait for confirmation")
    public void clickDontShareButton()
    {
        appiumUtils.isElementDisplayed("dontShareButton", 20);
        appiumUtils.click("dontShareButton");
        appiumUtils.sleepForMiliseconds(3000);
    }

    @Step("Click close button")
    public void clickCloseButton()
    {
        appiumUtils.click("closeButton");
    }
}

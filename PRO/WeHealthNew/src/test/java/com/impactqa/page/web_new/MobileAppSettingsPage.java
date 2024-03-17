package com.impactqa.page.web_new;

import com.impactqa.utilities.FrameworkConfig;
import io.qameta.allure.Step;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import org.openqa.selenium.WebDriver;

import java.io.File;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;


public class MobileAppSettingsPage extends BasePage {
    private static final String PageObjectRepoFileName = "MobileAppSettings.xml";

    public Map<String, Object> changesMade = new LinkedHashMap<>();
    public MobileAppSettingsPage(WebDriver driver) {
        super(driver, PageObjectRepoFileName);
    }


    @Step("Select Region")
    public void selectRegion(String region)
    {
        seleniumUtils.waitForThePageLoad();
        seleniumUtils.selectDropdown("regionDropdown", region);
    }

    @Step("Set High Exposure Threshold")
    public void setHighExposureThreshold(String significantThreshold)
    {
        seleniumUtils.enterText("HighExposureThreshold", significantThreshold);
    }

    @Step("Set No Significant Exposure Messages")
    public void setNoSignificantExposureMessages(String region)
    {
        seleniumUtils.click("NoSignificantExposureMessagesExpandCollapseButton");
        JsonPath jsonpath = JsonPath.from(new File(System.getProperty("user.dir") + "/" + FrameworkConfig.getStringEnvProperty("JsonTemplatepath") + "/gov.azdhs.covidwatch.json"));
        List<Map<String, String>> steps = jsonpath.get("findAll { it.name=='" + region + "' }[0].nextStepsNoSignificantExposure");
        String step1 = steps.get(0).get("description")
                + "--TestMessage-" + region + "--" + new Date().getTime();
//        seleniumUtils.enterText("DynamicNoSignificantExposureDescription"
//                ,"Step 1 - NoSignificantExposureDescription"
//                ,Map.of("{{index}}","0")
//                , step1);
        changesMade.put("nextStepsSignificantExposure[0].description", step1);
    }

    @Step("Save And Submit Changes")
    public void saveAndSubmitChanges()
    {
        seleniumUtils.click("saveAndSubmitButton");
        seleniumUtils.click("saveAndSubmitIAcceptCheckboxSpan");
        seleniumUtils.click("submitButton");
        seleniumUtils.waitForElementToBeNotDisplayed("saveAndSubmitIAcceptCheckboxSpan");
        System.out.println();
    }

//    nextStepsNoSignificantExposure[0].description
//    nextStepsNoSignificantExposure[0].type
//    nextStepsNoSignificantExposure[0].url
//
//    nextStepsSignificantExposure[0].description
//    nextStepsSignificantExposure[0].type
//    nextStepsSignificantExposure[0].url
//
//    nextStepsVerificationCode[0].description
//    nextStepsVerificationCode[0].type
//    nextStepsVerificationCode[0].url
//
//    nextStepsVerifiedPositive[0].description
//    nextStepsVerifiedPositive[0].type
//    nextStepsVerifiedPositive[0].url
//
//    No Significant Exposure
//    Significant Exposure
//    Verification Code
//    Verified Positive Diagnosis



}

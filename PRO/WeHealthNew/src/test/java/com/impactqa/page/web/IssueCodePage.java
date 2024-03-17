package com.impactqa.page.web;

import io.qameta.allure.Allure;
import io.qameta.allure.Step;
import io.qameta.allure.model.Status;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;

import java.util.Calendar;
import java.util.List;
import java.util.Map;

/**
 * @author  Sonu Kumar
 * @since   2020-09-12
 * @description Implemented logic to handle FindTextPage functionality and validations
 */
public class IssueCodePage extends BasePage {
    private static final String PageObjectRepoFileName = "IssueCodePage.xml";

    public IssueCodePage(WebDriver driver) {
        super(driver, PageObjectRepoFileName);
    }


    @Step("Click Issue Code Tab")
    public void clickIssueCodeTab()
    {
        seleniumUtils.click("IssueCodeTab");
    }

    @Step("Verify Issue Code page is displayed")
    public void verifyIssueCodePageDisplayed()
    {
        seleniumUtils.waitForThePageLoad();
        seleniumUtils.waitForElementToDisplay("IssueCodeTitle");
    }

    @Step("Generate Code For Positive Test")
    public String generateCodeForPositiveTest(Calendar symptomsDate, Calendar testingDate)
    {
        seleniumUtils.click("PositiveTestCheckbox");
        if(testingDate!=null)
            seleniumUtils.setDateField("TestingDate", testingDate);
        if(symptomsDate!=null)
            seleniumUtils.setDateField("SymptomDate", symptomsDate);
        seleniumUtils.click("submitButton");

        seleniumUtils.waitForThePageLoad();
        seleniumUtils.sleep(2000);
        String code = seleniumUtils.getText("verificationCode").replace("\n","").trim();
        String uuid = seleniumUtils.getAttribute("verificationUUID", "value").trim();
        Allure.step("Code: "+code, Status.PASSED);
        Allure.step("UUID: "+uuid, Status.PASSED);
        return code;
    }

    @Step("Verify The Fields In Issue Code")
    public void verifyTheFieldsInIssueCode()
    {
        seleniumUtils.waitForElementToDisplay("PositiveTestCheckbox");
        seleniumUtils.waitForElementToDisplay("LikelyDiagnosisCheckbox");
        seleniumUtils.waitForElementToDisplay("NegativeTestCheckbox");
        seleniumUtils.waitForElementToDisplay("TestingDate");
        seleniumUtils.waitForElementToDisplay("SymptomDate");
        seleniumUtils.waitForElementToDisplay("submitButton");
    }

    @Step("Set Symptoms Date")
    public void setSymptomsDate(Calendar symptomsDate)
    {
        if(symptomsDate!=null)
            seleniumUtils.setDateField("SymptomDate", symptomsDate);
    }

    @Step("Set Tested Date")
    public void setTestedDate(Calendar testingDate)
    {
        if(testingDate!=null)
            seleniumUtils.setDateField("TestingDate", testingDate);
    }

    @Step("Click Submit Button")
    public void clickSubmitButton()
    {
        seleniumUtils.click("submitButton");
    }

    @Step("Very Verification Code IS Displayed")
    public void veryVerificationCodeDisplayed()
    {
        seleniumUtils.waitForElementToDisplay("verificationCode");
    }

    @Step("Very Verification Code IS NOT Displayed")
    public void veryVerificationCodeNotDisplayed()
    {
        seleniumUtils.waitForThePageLoad();
        if(seleniumUtils.isElementDisplayed("verificationCode", 5))
            Assert.fail("verificationCode should not be displayed");
    }

    @Step("Click Reset Button")
    public void clickResetButton()
    {
        seleniumUtils.click("resetButton");
    }

    @Step("Click Menu Toggle Button")
    public void clickMenuToggleButton()
    {
        seleniumUtils.click("menuToggleButton");
    }

    @Step("Verify Menu Option {0}")
    public void verifyMenuOption(String menuName)
    {
      //  seleniumUtils.waitForElementToDisplay("dynamicMenuOption", menuName+" - Menu", Map.of("{{menuName}}", menuName));
    }

    @Step("Click Menu Option {0}")
    public void clickMenuOption(String menuName)
    {
      //  seleniumUtils.click("dynamicMenuOption", menuName+" - Menu", Map.of("{{menuName}}", menuName));
        System.out.println("jjf");

    }
}

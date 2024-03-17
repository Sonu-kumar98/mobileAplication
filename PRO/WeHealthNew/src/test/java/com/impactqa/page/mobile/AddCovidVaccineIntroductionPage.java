package com.impactqa.page.mobile;
import com.impactqa.utilities.PageObjectRepoHelper;
import io.appium.java_client.AppiumDriver;
import io.qameta.allure.Step;

/**
 * @author  Sonu Kumar
 * @since   2021-04-05
 * @description This page conatins all functionality of add covid vaccine Introduction page.
 */

public class AddCovidVaccineIntroductionPage extends BasePage {

    private static final String PageObjectRepoFileName = "intialscreens/AddCovidVaccineIntroPage.xml";

    public AddCovidVaccineIntroductionPage(AppiumDriver driver, PageObjectRepoHelper.PLATFORM platform) {
        super(driver, PageObjectRepoFileName, platform);
    }

    @Step("Verify Add a Covid-19 Vaccine Introduction Page Displayed")
    public void verifyAddCovidVaccineIntroductionPageDisplay()
    {
        appiumUtils.waitForElementToDisplay("openIntroCovidVaccinePage");
        appiumUtils.waitForElementToDisplay("textFieldOne");
        appiumUtils.waitForElementToDisplay("textFieldTwo");
        appiumUtils.waitForElementToDisplay("textFieldThree");
        appiumUtils.waitForElementToDisplay("continueButton");

    }

    @Step("Verify all text Add a Covid-19 Vaccine Introduction Page")
    public void verifyTextAddCovidVaccineIntroductionPageDisplay()
    {
        appiumUtils.verifyText("openIntroCovidVaccinePage", "Add a COVID-19 Vaccine");
        appiumUtils.verifyText("textFieldOne","Easily add the type, dose, and date.");
        appiumUtils.verifyText("textFieldTwo","Save it anonymously in the app.");
        appiumUtils.verifyText("textFieldThree","Get custom recommendations with an updated risk level, so that you can continue to help keep you, your family, " +
                "and community safe.");
        appiumUtils.verifyText("continueButton","Continue");

    }

    @Step("Click Continue Button")
    public void clickContinueButton()
    {
        appiumUtils.click("continueButton");
    }

}

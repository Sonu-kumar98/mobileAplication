package com.impactqa.page.web;

import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;

/**
 * @author  Sonu Kumar
 * @since   22020-09-12
 * @description Implemented logic to handle FindTextPage functionality and validations
 */
public class SelectRealmPage extends BasePage {
    private static final String PageObjectRepoFileName = "SelectRealmPage.xml";

    public SelectRealmPage(WebDriver driver) {
        super(driver, PageObjectRepoFileName);
    }


    @Step("Verify Realm page is displayed")
    public void verifyRealmPageDisplayed()
    {
        seleniumUtils.waitForThePageLoad();
        seleniumUtils.waitForElementToDisplay("realmTitle");
    }

    @Step("Select Realm {0}")
    public void selectRealm(String realmName)
    {
        switch(realmName.toUpperCase())
        {
            case "ARIZONA":
                seleniumUtils.click("ARIZONA_Realm_link");
                break;
            case "ARIZONA-DEMO":
                seleniumUtils.click("ARIZONA_Demo_Realm_link");
                break;
            case "BERMUDA":
                seleniumUtils.click("BERMUDA_Realm_link");
                break;
            case "BERMUDA-DEMO":
                seleniumUtils.click("BERMUDA_Demo_Realm_link");
                break;
            default:
                Assert.fail("Please check whether you provided correct Realm");
                break;
        }

    }
}

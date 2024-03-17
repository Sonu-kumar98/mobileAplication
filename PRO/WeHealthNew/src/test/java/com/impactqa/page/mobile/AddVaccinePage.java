package com.impactqa.page.mobile;

/**
 * @author  Sonu Kumar
 * @since   2021-14-05
 * @description This page conatins all functionality of add covid vaccine page.
 */

import com.impactqa.utilities.PageObjectRepoHelper;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.qameta.allure.Step;
import java.util.Map;

public class AddVaccinePage extends BasePage  {
    private static final String PageObjectRepoFileName = "intialscreens/AddVaccine.xml";

    public AddVaccinePage(AppiumDriver driver, PageObjectRepoHelper.PLATFORM platform) {
        super(driver, PageObjectRepoFileName, platform);
    }

    @Step("Verify Add a Covid-19 Vaccine Page Displayed")
    public void verifyAddCovidVaccineIntroductionPageDisplay()
    {
        appiumUtils.waitForElementToDisplay("openAddVaccinePage");

    }

    @Step("Verify all text Add a Covid-19 Vaccine Page")
    public void verifyTextAddCovidVaccinePageDisplay()
    {
        appiumUtils.verifyText("openAddVaccinePage","Add a Covid-19 Vaccine");

    }
    @Step("Select Type '{0} - {1}'")
    public void selectType(String buildType, String type) {

//        if(!buildType.isEmpty())
//            return;

        // appiumUtils.click("RegionDropdownSelectedOption");
        //  appiumUtils.attachScreenShotToTheReport("Region options");
        if (appiumUtils.getPlatform() == PageObjectRepoHelper.PLATFORM.IOS) {
//            Map<String, Integer> typeMap = Map.of("Arizona - Johnson & Johnson", 0,
//                    "Arizona - Moderna", 1,
//                    "Arizona - Pfizer", 2,
//                    "Bermuda - Pfizer", 0,
//                    "Bermuda - Moderna", 1,
//                    "Bermuda-Johnson & Johnson",2);

            MobileElement optionElement = appiumUtils.getMobileElement("typeDropdownOptions_IOS");
            double h = Double.valueOf(optionElement.getSize().height);
            double option_h = h/6;
            double option_h_c = option_h/2;
            int mx = optionElement.getCenter().x;
            double y = Double.valueOf(optionElement.getLocation().y);

//            appiumUtils.clickAtCoordinates(mx, (int) (y + option_h_c + (option_h * typeMap.get(buildType+" - "+type.trim())))
//                    , type+" Type Option");


        } else {

//            Map<String, Integer> typeMap = Map.of("Arizona - Pfizer", 0,
//                    "Arizona - Moderna", 1,
//                    "Arizona - Johnson & Johnson", 2,
//                    "Bermuda - Pfizer", 0,
//                    "Bermuda - Moderna", 1,
//                    "Bermuda-Johnson & Johnson",2);

            MobileElement regionDropDown = appiumUtils.getMobileElement("typeDropdownOptions");
            int mx= regionDropDown.getCenter().x;
            int my= regionDropDown.getCenter().y;
            int h= regionDropDown.getSize().height;
            int w= regionDropDown.getSize().width;
          //  appiumUtils.clickAtCoordinates(mx, (my+h*typeMap.get(buildType+" - "+type.trim())) , type+" Type Option");

        }
    }

    @Step("Select Dose '{0} - {1}'")
    public void selectDose(String buildType, String dose) {

//        if(!buildType.isEmpty())
//            return;

        // appiumUtils.click("RegionDropdownSelectedOption");
        //  appiumUtils.attachScreenShotToTheReport("Region options");
        if (appiumUtils.getPlatform() == PageObjectRepoHelper.PLATFORM.IOS) {
//            Map<String, Integer> doseMap = Map.of("Arizona - Dose 1", 0,
//                    "Arizona - Dose 2", 1,
//                    "Bermuda - Dose 1", 0,
//                    "Bermuda - Dose 2", 1);

            MobileElement optionElement = appiumUtils.getMobileElement("doseDropdownOptions_IOS");
            double h = Double.valueOf(optionElement.getSize().height);
            double option_h = h/6;
            double option_h_c = option_h/2;
            int mx = optionElement.getCenter().x;
            double y = Double.valueOf(optionElement.getLocation().y);

//            appiumUtils.clickAtCoordinates(mx, (int) (y + option_h_c + (option_h * doseMap.get(buildType+" - "+dose.trim())))
//                    , dose+" Dose Option");


        } else {

//            Map<String, Integer> typeMap = Map.of("Arizona - Dose 2", 0,
//                    "Arizona - Dose 1", 1,
//                    "Bermuda - Dose 1", 0,
//                    "Bermuda - Dose 2", 1);

            appiumUtils.sleepForMiliseconds(5000);
            MobileElement doseDropDown = appiumUtils.getMobileElement("doseDropdownOptions");
            int mx= doseDropDown.getCenter().x;
            int my= doseDropDown.getCenter().y;
            int h= doseDropDown.getSize().height;
            int w= doseDropDown.getSize().width;
         //   appiumUtils.clickAtCoordinates(mx, (my+h*typeMap.get(buildType+" - "+dose.trim())) , dose+" Dose Option");

        }
    }

    @Step("Click on Calendar")
    public void clickOnCalender()
    {
        appiumUtils.click("calender");

    }

    @Step("Click on Date")
    public void clickOnDate()
    {
        appiumUtils.click("date");

    }
    @Step("Click on okButton")
    public void clickOnOkButton()
    {
        appiumUtils.click("okButton");

    }
    @Step("Click Add Vaccine Button")
    public void clickAddVaccineButton()
    {
        appiumUtils.click("addVaccineButton");

    }

    // Congratulation page
    @Step("Verify Congratulation Page Displayed")
    public void verifyCongratulationPageDisplay()
    {
       appiumUtils.waitForElementToDisplay("tittle");

    }

    @Step("Verify all text Add a Covid-19 Vaccine Page")
    public void verifyTextOnCongratulationPageDisplay()
    {
        appiumUtils.verifyText("tittle"," Thank you for helping to reduce the spread in your community");

    }

    @Step("Click My Anonymous Button")
    public void clickMyAnonymousButton()
    {
        appiumUtils.click("myAnonymous_Btn");

    }

    // My Anonymous Page

    @Step("Verify My Anonymous Page Displayed")
    public void verifyMyAnonymousPageDisplay()
    {
        appiumUtils.waitForElementToDisplay("tittle_MyAnonymous_Info");

    }

    @Step("Verify all text Add a Covid-19 Vaccine Page")
    public void verifyTextMyAnonymousPageDisplay()
    {
        appiumUtils.verifyText("tittle_MyAnonymous_Info","My Anonymous Info");

    }


    @Step("Close Button of My Anonymous Page")
    public void clickCloseButtonMyAnonymous()
    {
        appiumUtils.click("close_Btn");

    }

    @Step("Close Button of Menu Page")
    public void clickCloseButtonMenu()
    {
        appiumUtils.click("close_Btn");

    }

    // Home Page
    @Step("Verify Home Page Displayed")
    public void verifyHomePageDisplay()
    {
        appiumUtils.waitForElementToDisplay("vaccinated_Btn");

    }

    @Step("Verify Home Page")
    public void verifyTextOnHomePageDisplay()
    {
        appiumUtils.verifyText("vaccinated_Btn","Vaccinated");

    }

    @Step("Click on Menu Button of Home page")
    public void clickOnMenuButton()
    {
        appiumUtils.click("menu_Btn");

    }

    @Step("Click on My Anonymous Info")
    public void clickOnMyAnonymousInfo()
    {
        appiumUtils.click("my_Anonymous_Info");

    }

    @Step("Click expand Button of My Anonymous Page")
    public void clickOnExpandButton()
    {
        appiumUtils.click("expand_Arrow");

    }

    @Step("Click delete Vaccine on My Anonymous Page")
    public void clickOnDeleteButton()
    {
        appiumUtils.click("delete_Btn");

    }
    @Step("Click delete PopUp on My Anonymous Page")
    public void clickOnDeletePopUp()
    {
        appiumUtils.click("delete_PopUp");

    }

}

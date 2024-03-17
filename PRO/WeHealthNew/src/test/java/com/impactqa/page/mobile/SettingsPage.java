package com.impactqa.page.mobile;
import com.impactqa.utilities.FrameworkConfig;
import com.impactqa.utilities.PageObjectRepoHelper;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.TouchAction;
import io.appium.java_client.touch.offset.PointOption;
import io.qameta.allure.Step;
import org.testng.Assert;

import java.util.List;
import java.util.Map;


public class SettingsPage extends BasePage {
    private static final String PageObjectRepoFileName = "intialscreens/Settings.xml";

    public SettingsPage(AppiumDriver driver, PageObjectRepoHelper.PLATFORM platform) {
        super(driver, PageObjectRepoFileName, platform);
    }

    @Step("Verify Settings Page Displayed")
    public void verifySettingsPageDisplayed() {
        appiumUtils.waitForElementToDisplay("settingsTitle");
    }

    @Step("Select Region '{0} - {1}'")
     public void selectRegion(String buildType, String region) {

//        if(!buildType.isEmpty())
//            return;

       // appiumUtils.click("RegionDropdownSelectedOption");
      //  appiumUtils.attachScreenShotToTheReport("Region options");
        if (appiumUtils.getPlatform() == PageObjectRepoHelper.PLATFORM.IOS) {
//            Map<String, Integer> regionMap = Map.of("Arizona - State of Arizona", 0,
//                    "Arizona - University of Arizona", 1,
//                    "Arizona - Northern Arizona University", 2,
//                    "Bermuda - Residents", 0,
//                    "Bermuda - Visitors", 1);
            System.out.println("jjf");

            MobileElement optionElement = appiumUtils.getMobileElement("RegionDropdownOptions_IOS");
            // h=60 =10
            //5
            // 50=y
            // 100
            // 50+5+(10*2)
            double h = Double.valueOf(optionElement.getSize().height);
            double option_h = h/6;
            double option_h_c = option_h/2;
            int mx = optionElement.getCenter().x;
            double y = Double.valueOf(optionElement.getLocation().y);

//            appiumUtils.clickAtCoordinates(mx, (int) (y + option_h_c + (option_h * regionMap.get(buildType+" - "+region.trim())))
//                , region+" Region Option");

            appiumUtils.verifyText("RegionDropdownSelectedOption", region);


        } else {

//            Map<String, Integer> regionMap = Map.of("Arizona - State of Arizona", 1,
//                                                    "Arizona - University of Arizona", 2,
//                                                    "Arizona - Northern Arizona University", 3,
//                                                    "Bermuda - Residents", 1,
//                                                    "Bermuda - Visitors", 2);

            MobileElement regionDropDown = appiumUtils.getMobileElement("RegionDropdownSelectedOption");
            int mx= regionDropDown.getCenter().x;
            int my= regionDropDown.getCenter().y;

            int h= regionDropDown.getSize().height;
            int w= regionDropDown.getSize().width;

          // appiumUtils.clickAtCoordinates(mx, (my+h*regionMap.get(buildType+" - "+region.trim())) , region+" Region Option");

            appiumUtils.verifyText("RegionDropdownSelectedOption", region);

        }
    }

    @Step("NotifyLowExposureCheckbox - '{0}'")
    public void notifyLowExposureCheckbox(String status) {
        appiumUtils.scrollDown();
        if(!"".equals(status)) {
            boolean checkCheckbox = false;
            String locatorName = "notifyLowExposureCheckbox";

            if ("yes".equals(status.toLowerCase()))
                checkCheckbox = true;
            else
                checkCheckbox = false;

            Boolean  currentCheckedStatus = null;
            if (appiumUtils.getPlatform() == PageObjectRepoHelper.PLATFORM.IOS)
                currentCheckedStatus = !appiumUtils.getAttribute(locatorName, "label").endsWith("Unchecked");
            else
                currentCheckedStatus = appiumUtils.getAttribute(locatorName, "checked").equals("true");

            if(checkCheckbox & !currentCheckedStatus)
                appiumUtils.click(locatorName);
            else if(!checkCheckbox & currentCheckedStatus)
                appiumUtils.click(locatorName);

        }
    }


    @Step("Verify Exposure Notification Checkbox Status")
    public void verifyExposureNotificationCheckboxStatus(String expectedStatus) {
        appiumUtils.scrollDown();

        Boolean  currentCheckedStatus = null;
        if (appiumUtils.getPlatform() == PageObjectRepoHelper.PLATFORM.IOS)
            currentCheckedStatus = appiumUtils.getAttribute("exposureNotificationCheckbox", "value").endsWith("1");
        else
            currentCheckedStatus = appiumUtils.getAttribute("exposureNotificationCheckbox", "checked").equals("true");

        if("checked".equals(expectedStatus))
            Assert.assertTrue(currentCheckedStatus, "Exposure Notification Checkbox should be checked");
        else
            Assert.assertFalse(currentCheckedStatus, "Exposure Notification Checkbox should be unchecked");
    }



    @Step("Click Save Button")
    public void clickSaveButton()
    {
        // appiumUtils.scrollDown();
        appiumUtils.click("saveButton");
    }

}

package com.impactqa.page.mobile;

import com.impactqa.utilities.PageObjectRepoHelper;
import io.appium.java_client.AppiumDriver;
import io.qameta.allure.Step;

import java.io.File;
import java.util.Map;


public class ImportExposureKeys extends BasePage {
    private static final String PageObjectRepoFileName = "ImportExposureKeys.xml";

    public ImportExposureKeys(AppiumDriver driver, PageObjectRepoHelper.PLATFORM platform) {
        super(driver, PageObjectRepoFileName, platform);
    }

    // change by Sonu Kumar
    @Step("take screen sort of page")
    public void takeScreenSort()
    {
        appiumUtils.snap("importExposureKey");
    }

    @Step("Verify Import Exposure Keys Page Displayed")
    public void verifyImportExposureKeysPageDisplayed()
    {
        if(appiumUtils.getPlatform()== PageObjectRepoHelper.PLATFORM.ANDROID)
            appiumUtils.waitForElementToDisplay("showRoots");
        else
            appiumUtils.waitForElementToDisplay("browseButton");
    }

    @Step("Import keys ")
    public void importKeys(String localFilpath)
    {
        String fileName = new File(localFilpath).getName();
        if(appiumUtils.getPlatform()== PageObjectRepoHelper.PLATFORM.ANDROID){
        appiumUtils.click("showRoots");
        if(appiumUtils.isElementDisplayed("oldDeviceNameOption", 3))
        {
            appiumUtils.click("oldDeviceNameOption");
            appiumUtils.click("downloadOption");
        }
        else
            appiumUtils.click("downloadsRootOption");
        appiumUtils.attachScreenShotToTheReport("screenshot");
        if(appiumUtils.getDeviceModelName().equals("Moto E (4)"))
        {
//            appiumUtils.longClick("fileName"
//                    , "FileName: "+fileName+" in the download folder"
//                    , Map.of("{{fileName}}", fileName));
//            appiumUtils.click("OPEN_Option");
        }
        else
            System.out.println("jjdjdjd");
//            appiumUtils.click("fileName"
//                    , "FileName: "+fileName+" in the download folder"
//                    , Map.of("{{fileName}}", fileName));
        }
        else{
//            appiumUtils.click("browseButton");
//            appiumUtils.click("browseButton");
//            appiumUtils.click("OnMyiPhonePath");
//            appiumUtils.click("Keynotepath");
//            appiumUtils.attachScreenShotToTheReport("screenshot");
//            appiumUtils.click("fileNameIOS"
//                    , "FileName: "+fileName+" in the Keynote folder"
//                    , Map.of("{{fileName}}", fileName));
        }

    }


}

package com.impactqa.page.mobile;

import com.impactqa.utilities.PageObjectRepoHelper;
import io.appium.java_client.AppiumDriver;
import io.qameta.allure.Step;
import org.testng.Assert;
public class ExportExposureKeys extends BasePage {
    private static final String PageObjectRepoFileName = "ExportExposureKeys.xml";

    public ExportExposureKeys(AppiumDriver driver, PageObjectRepoHelper.PLATFORM platform) {
        super(driver, PageObjectRepoFileName, platform);
    }

    @Step("take screen sort of page")
    public void takeScreenSort()
    {
        appiumUtils.snap("Exportexposurekey");
    }

    @Step("Verify Export Exposure Keys Page Displayed")
    public void verifyExportExposureKeysPageDisplayed()
    {
        appiumUtils.waitForElementToDisplay("daysSinceSymptomsTextbox");
    }

    @Step("Export keys with  {0} Days Since Symptoms")
    public String exportKeysWithDaysSinceSymptomsTextbox(String buildType, String daysSinceSymptoms, String fileName)
    {
        appiumUtils.enterText("daysSinceSymptomsTextbox", daysSinceSymptoms);
        appiumUtils.click("continueButton");
        if(appiumUtils.isElementDisplayed("alertPopupTitle", 3)) {

            if(appiumUtils.getPlatform()== PageObjectRepoHelper.PLATFORM.ANDROID) {
                if ("Arizona".equals(buildType.trim()))
                   appiumUtils.verifyText("alertPopupTitle", "Share your random IDs with CW DEMO?");
          //   appiumUtils.verifyText("alertPopupTitle", "¿Compartir tus ID aleatorios con CW DEMO?");
                else if ("Bermuda".equals(buildType.trim()))
                    appiumUtils.verifyText("alertPopupTitle", "Share your random IDs with WH DEMO?");
            }
            appiumUtils.click("alertPopupShareButton");
        }
        if(appiumUtils.getPlatform()== PageObjectRepoHelper.PLATFORM.ANDROID) {
            appiumUtils.click("showRoots");
            appiumUtils.click("downloadsRootOption");
            appiumUtils.enterText("fileNameTextbox", fileName);
            appiumUtils.attachScreenShotToTheReport("screenshot");
            appiumUtils.click("saveButton");
        }
        else{

            appiumUtils.click("saveToFilesButton");
            appiumUtils.click("KeynoteLocationIcontext");
            appiumUtils.click("renameButton");
            appiumUtils.waitForElementToDisplay("renameDocumentTitle");
            appiumUtils.enterText("fileNameTextboxIOS", fileName.replace(".zip", ""));
            appiumUtils.attachScreenShotToTheReport("screenshot");
            appiumUtils.click("doneButton");
            appiumUtils.click("saveButtonIOS");

        }

        String localFilePath =null;
        appiumUtils.sleepForMiliseconds(5000);
        if(appiumUtils.getPlatform()== PageObjectRepoHelper.PLATFORM.ANDROID)
            localFilePath = appiumUtils.pullFile("/sdcard/Download/" + fileName);
        else
            localFilePath = appiumUtils.pullFile("@com.apple.Keynote:documents/" + fileName);
        return localFilePath;
    }

}

package com.impactqa.page.mobile;

import com.impactqa.utilities.FrameworkConfig;
import com.impactqa.utilities.PageObjectRepoHelper;
import io.appium.java_client.AppiumDriver;
import io.qameta.allure.Step;

import java.io.File;


public class DeviceSettingsAppScreen extends BasePage {
    private static final String PageObjectRepoFileName = "DeviceSettingsAppScreen.xml";

    public DeviceSettingsAppScreen(AppiumDriver driver, PageObjectRepoHelper.PLATFORM platform) {
        super(driver, PageObjectRepoFileName, platform);
    }

    @Step("Turn Off Exposure Notification From IOS Device Settings")
    public void turnOffExposureNotificationFromIOSDeviceSettings()
    {
        appiumUtils.dismissIfAnyAlerts();
        appiumUtils.sleepForMiliseconds(2000);
        if(appiumUtils.getPlatform()== PageObjectRepoHelper.PLATFORM.IOS)
        {
            appiumUtils.scrollAndSearchElementWithText("Exposure Notifications", true);
            appiumUtils.click("exposureNotification");
            if(appiumUtils.isElementDisplayed("TurnOffExposureNotification", 5))
            {
                appiumUtils.click("TurnOffExposureNotification");
                appiumUtils.click("confirmButton");
            }
        }
    }

    @Step("Uninstall All WeHealth Apps")
    public void uninstallAllApps()
    {
        appiumUtils.dismissIfAnyAlerts();
        appiumUtils.sleepForMiliseconds(2000);

       // appiumUtils.unInstallApp("org.wehealth.exposure");
        if(appiumUtils.getPlatform()== PageObjectRepoHelper.PLATFORM.ANDROID) {
            appiumUtils.unInstallApp("fr.antelop.whitelabel");
//            appiumUtils.unInstallApp("gov.azdhs.covidwatch.android.demo");
//            appiumUtils.unInstallApp("org.wehealth.exposure.demo");
        }
        else {
            appiumUtils.unInstallApp("gov.azdhs.covidwatch.ios");
            appiumUtils.unInstallApp("gov.azdhs.covidwatch.ios.demo");

        }
    }

//    @Step("install App")
//    public void installApp(String appFile)
//    {
//        String appPath = null;
//        if(appFile.contains("/") || appFile.contains("\\"))
//            appPath = appFile;
//        else
//            appPath = new File(FrameworkConfig.getStringEnvProperty("MobileApppath")+"/"+appFile).getAbsolutePath();
//        appiumUtils.installApp(appPath);
//        appiumUtils.launchApp();
//    }


}

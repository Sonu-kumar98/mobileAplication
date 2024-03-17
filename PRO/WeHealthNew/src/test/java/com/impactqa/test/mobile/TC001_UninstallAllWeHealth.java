package com.impactqa.test.mobile;

import com.impactqa.listeners.TestAllureListener;
import com.impactqa.page.mobile.*;
import io.qameta.allure.Description;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

@Listeners({TestAllureListener.class})
public class TC001_UninstallAllWeHealth extends BaseTestMobile{

    @Test(priority = 0)
    @Description("Uninstall Relevant Apps And Reinstall Main App")
    public void unInstallRelevantAppsAndReinstallMainApp()
    {
        DeviceSettingsAppScreen deviceSettingsAppScreen = new DeviceSettingsAppScreen(driver,platform);
        deviceSettingsAppScreen.turnOffExposureNotificationFromIOSDeviceSettings();
        deviceSettingsAppScreen.uninstallAllApps();

    }
}

package com.impactqa.utilities;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import io.qameta.allure.Allure;
import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;

public class DriverProvider {


    @Step("Mobile Session")
    public static AppiumDriver createNewMobileSession(PageObjectRepoHelper.PLATFORM platform, Map<String, String> sessionDetails) throws MalformedURLException {
        DesiredCapabilities desiredCapabilities = new DesiredCapabilities();
        for(String key: sessionDetails.keySet())
        {
            System.out.println("print key  set :    "+sessionDetails.keySet());
            System.out.println("print key value:    "+sessionDetails.get(key));
            if(sessionDetails.get(key)!=null && !sessionDetails.get(key).trim().isEmpty()) {

                if(key.equals("appFileName"))
                {
                    String appFileName = sessionDetails.get(key);
                    String appPath = null;
                    // System.out.println("Print app pathddd app path"+appFileName);
                    // if(appFileName.contains("/") || appFileName.contains("\\") || appFileName.equals("settings")  || appFileName.equals("com.android.settings"))
                    if(appFileName.contains("-") || appFileName.contains("\\") || appFileName.equals("settings")  || appFileName.equals("com.android.settings"))

                        appPath = appFileName;

                    else
                        appPath = new File(FrameworkConfig.getStringEnvProperty("MobileApppath")+"/"+appFileName).getAbsolutePath();

                    System.out.println("inside the"+appPath);
                    desiredCapabilities.setCapability("app", appPath);
                }
                else
                    //  desiredCapabilities.setCapability(key, sessionDetails.get(key).trim());
                    desiredCapabilities.setCapability("udid", "RZCTA0ZLTRK");
                //                 capabilities.setCapability("deviceName", "OnePlus");
                desiredCapabilities.setCapability("automationName", "UiAutomator2");
                desiredCapabilities.setCapability("platformName","Android" );
                desiredCapabilities.setCapability("platformVersion","13" );
                desiredCapabilities.setCapability("deviceName", "Galaxy F23 5G");

            }
        }
        URL remoteUrl = new URL("http://127.0.0.1:4723");
        /// URL remoteUrl = new URL("http://"+FrameworkConfig.getStringEnvProperty("remoteMobileDriverHubHost"));
        AppiumDriver driver = null;
        if(platform == PageObjectRepoHelper.PLATFORM.ANDROID) {
            driver = new AndroidDriver(remoteUrl, desiredCapabilities);

        }else  if(platform == PageObjectRepoHelper.PLATFORM.IOS) {
            //  remoteUrl = new URL("http://192.168.0.100:4723/wd/hub");
            remoteUrl = new URL("http://192.168.0.100:4723");
            driver = new IOSDriver(remoteUrl, desiredCapabilities);

        }
        Allure.step("Capabilities: "+driver.getCapabilities());
        return driver;
    }

    public static WebDriver createNewBrowserSession(String browserName)
    {
        WebDriver driver = null;
        switch(browserName.toLowerCase())
        {
            case "chrome":
                driver = new ChromeDriver();
                break;
            case "firefox":
                driver = new FirefoxDriver();
                break;
            default:
                driver = new ChromeDriver();
                break;
        }

        driver.get(FrameworkConfig.getStringEnvProperty("ApplicationURL"));

        return driver;
    }

}

package com.impactqa.utilities;

import com.google.common.collect.ImmutableMap;
import com.google.common.io.Files;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileBy;
import io.appium.java_client.MobileElement;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.nativekey.AndroidKey;
import io.appium.java_client.android.nativekey.KeyEvent;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.touch.LongPressOptions;
import io.appium.java_client.touch.WaitOptions;
import io.appium.java_client.touch.offset.ElementOption;
import io.appium.java_client.touch.offset.PointOption;
import io.qameta.allure.Allure;
import io.qameta.allure.Step;
import io.qameta.allure.model.Status;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import com.impactqa.utilities.PageObjectRepoHelper.PLATFORM;

import org.openqa.selenium.remote.RemoteWebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.util.*;

public class AppiumUtils {

    private AppiumDriver driver;
    private PageObjectRepoHelper pageObjectRepoHelper;
    private PLATFORM platform;
    private static final int defaltWaitTimeForPageLoad = FrameworkConfig.getNumberEnvProperty("DefaltWaitTimeForPageLoad");
    private static final int defaltWaitTimeForElement = FrameworkConfig.getNumberEnvProperty("DefaltWaitTimeForElement");

    public AppiumUtils(AppiumDriver driver, String pageObjectRepoFileName, PLATFORM platform) {
        this.driver = driver;
        this.platform = platform;
        pageObjectRepoHelper = new PageObjectRepoHelper(pageObjectRepoFileName, platform);
    }

    @Step("Get text from element '{0}'")
    public String getText(String locatorName) {

        WebElement element = waitForElementToDisplay(locatorName);
        return element.getText();
    }

    @Step("Get text from list of elements '{0}'")
    public List<String> getTextOfListElements(String locatorName) {

        List<String> textList = new LinkedList<>();
        By locBy = pageObjectRepoHelper.getObject(locatorName);
        List<WebElement> elements = new WebDriverWait(driver, defaltWaitTimeForElement).until(ExpectedConditions.presenceOfAllElementsLocatedBy(locBy));
        for(WebElement element: elements)
            textList.add(element.getText().trim());
        return textList;
    }

    @Step("Get attribute '{1}' from element '{0}'")
    public String getAttribute(String locatorName, String attributeName) {

        WebElement element = waitForElementToPresent(locatorName);
        return element.getAttribute(attributeName);
    }

    @Step("Verify text of element '{0}'")
    public void verifyText(String locatorName, String expectedText) {
        String actualText = getText(locatorName).trim();
        Assert.assertTrue(actualText.equalsIgnoreCase(expectedText.trim()), "text not matched. ActualText: " + actualText+ "'. Expected: '"+expectedText+"'\n");
    }

    @Step("Verify partial text of element '{0}'")
    public void verifyPartialText(String locatorName, String expectedText) {
        String actualText = getText(locatorName).trim();
        Assert.assertTrue(actualText.contains(expectedText.trim()), "partial text not matched. ActualText: '" + actualText+ "'. Expected(Partial): '"+expectedText+"'\n");
    }


    @Step("Click '{0}'")
    public void click(String locatorName) {
        WebElement element = waitForElementToBeClickable(locatorName);
        element.click();
    }

    @Step("Click '{1}'")
    public void click(String locatorName, String customName, Map<String, String> replaceKeyVal) {
        WebElement element = waitForElementToBeClickable(locatorName, customName, replaceKeyVal);
        element.click();
    }

    @Step("longClick '{0}'")
    public void longClick(String locatorName) {
        WebElement element = waitForElementToBeClickable(locatorName);
        TouchAction action = new TouchAction(driver);
        action.longPress(LongPressOptions.longPressOptions().withElement(ElementOption.element(element)));
        action.release().perform();
    }


    @Step("longClick '{1}'")
    public void longClick(String locatorName, String customName, Map<String, String> replaceKeyVal) {
        WebElement element = waitForElementToBeClickable(locatorName, customName, replaceKeyVal);
        TouchAction action = new TouchAction(driver);
        action.longPress(LongPressOptions.longPressOptions().withElement(ElementOption.element(element)));
        action.release().perform();
    }

    @Step("Click '{2}' using Coordinates x:'{0}' y:'{1}'")
    public void clickAtCoordinates(int x, int y, String customName) {
        TouchAction ta = new TouchAction<>(driver);
        ta.tap(new PointOption().withCoordinates(x, y));
        ta.perform();
    }

    @Step("Get MobileElement '{0}'")
    public MobileElement getMobileElement(String locatorName) {
        WebElement element = waitForElementToDisplay(locatorName);
        return ((MobileElement)element);
    }

    @Step("Activate App '{0}'")
    public void activateApp(String packageOrBundleID) {

        driver.activateApp(packageOrBundleID);
    }

    @Step("Sleep for '{0}' milliseconds")
    public void sleepForMiliseconds(long miliseconds) {
        try {
            Thread.sleep(miliseconds);
        } catch (InterruptedException e) {

        }
    }

    @Step("Is App Installed '{0}'")
    public boolean isAppInstalled(String packageOrBundleID) {
        return driver.isAppInstalled(packageOrBundleID);
    }

    @Step("Install app '{0}'")
    public void installApp(String appPath) {
        driver.installApp(appPath);
    }

    @Step("Launch Main App")
    public void launchApp() {
        driver.launchApp();
    }

    @Step("Uninstall the App '{0}'")
    public void unInstallApp(String packageOrBundleID) {
        driver.removeApp(packageOrBundleID);
    }

    @Step("pull file from path {0}")
    public String pullFile(String remoteFilepath) {

        byte[] fileContent = driver.pullFile(remoteFilepath);
        String filename = new File(remoteFilepath).getName();
        String localFilePath = "./temp/"+filename;

        try {
            FileUtils.writeByteArrayToFile(new File("./temp/"+filename), fileContent);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return localFilePath;
    }

    @Step("push file from path {0}")
    public void pushFile(String remotePath, String localFilePath) {

        try {
            if (platform == PLATFORM.IOS)
                ((IOSDriver) driver).pushFile(remotePath, new File(localFilePath));
            if (platform == PLATFORM.ANDROID)
                ((AndroidDriver) driver).pushFile(remotePath, new File(localFilePath));
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    @Step("Delete Files In Download Folder Android")
    public void deleteFilesInDownloadFolderAndroid() {

        try {
            if (platform == PLATFORM.ANDROID) {
//                Map<String, Object> args = Map.of("command", "rm",
//                                                      "args", List.of("/sdcard/Download/*")
//                                                 );
//                driver.executeScript("mobile: shell", args);
                System.out.println("jjjfj");
            }
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }


    @Step("Dismiss If Any Alerts")
    public void dismissIfAnyAlerts() {
        try {
            driver.switchTo().alert().accept();
        }catch (Exception e)
        {

        }
    }

    @Step("Get Screen Size")
    public Dimension getScreenSize() {
        Dimension windowSize = driver.manage().window().getSize();
        Allure.step("width: '"+windowSize.width+"' . height: '"+windowSize.height+"'", Status.PASSED);
        return windowSize;
    }
    @Step("scrollDown")
    public void scrollDown() {
        Dimension windowSize = driver.manage().window().getSize();
        PointOption startPoint = PointOption.point(getPortionOfTheNumber(windowSize.width, 50),
                getPortionOfTheNumber(windowSize.height, 90));
        PointOption endPoint = PointOption.point(getPortionOfTheNumber(windowSize.width, 50),
                getPortionOfTheNumber(windowSize.width, 10));

        TouchAction touchAction = new TouchAction(driver);
        touchAction.press(startPoint)
                .waitAction(WaitOptions.waitOptions(Duration.ofMillis(200)))
                .moveTo(endPoint)
                .release().perform();

    }

    @Step("scrollUp")
    public void scrollUp() {
        Dimension windowSize = driver.manage().window().getSize();

        PointOption startPoint  = PointOption.point(getPortionOfTheNumber(windowSize.width, 50),
                getPortionOfTheNumber(windowSize.width, 20));

        PointOption endPoint= PointOption.point(getPortionOfTheNumber(windowSize.width, 50),
                getPortionOfTheNumber(windowSize.height, 90));

        TouchAction touchAction = new TouchAction(driver);
        touchAction.press(startPoint)
                .waitAction(WaitOptions.waitOptions(Duration.ofMillis(200)))
                .moveTo(endPoint)
                .release().perform();

    }

    @Step("scrollAndSearchElementWithText '{0}'")
    public WebElement scrollAndSearchElementWithText(String text, boolean exactSearch) {
        if (platform == PLATFORM.ANDROID) {
            if (exactSearch)
                return ((AndroidDriver) driver).findElementByAndroidUIAutomator("new UiScrollable(new UiSelector().scrollable(true).instance(0)).scrollIntoView(new UiSelector().textMatches(\"" + text + "\").instance(0))");
            else
                return ((AndroidDriver) driver).findElementByAndroidUIAutomator("new UiScrollable(new UiSelector().scrollable(true).instance(0)).scrollIntoView(new UiSelector().textContains(\"" + text + "\").instance(0))");

        } else if (platform == PLATFORM.IOS) {
            // scroll to item
            HashMap scrollObject = new HashMap<>();
            String elementID = ((RemoteWebElement) driver.findElement(MobileBy.iOSNsPredicateString("label == '" + text + "'"))).getId();
            scrollObject.put("element", elementID);
            scrollObject.put("toVisible", "not an empty string");
//            HashMap scrollObject = new HashMap<>();
//            if (exactSearch)
//                scrollObject.put("predicateString", "label == '" + text + "'");
//            else
//                scrollObject.put("predicateString", "value CONTAINS '" + text + "'");
//
//            scrollObject.put("direction", "down");

            return (WebElement) driver.executeScript("mobile: scroll", scrollObject);

        }
        return null;

    }


    @Step("Press Physical Home Button")
    public void pressDeviceHomeButton() {
        if (platform == PLATFORM.IOS) {
            driver.executeScript("mobile: pressButton", ImmutableMap.of("name", "home"));
            //other supported button actions - volumeup, volumedown
        } else if (platform == PLATFORM.ANDROID) {
            ((AndroidDriver) driver).pressKey(new KeyEvent(AndroidKey.HOME));
        }
    }


    @Step("Wait For Element '{0}' To Be Clickable")
    public WebElement waitForElementToBeClickable(String locatorName) {
        By locBy = pageObjectRepoHelper.getObject(locatorName);
        WebElement element = waitForElementToDisplay(locatorName);
        return new WebDriverWait(driver, defaltWaitTimeForElement).until(ExpectedConditions.elementToBeClickable(element));
    }

    @Step("Wait For Element '{1}' To Be Clickable")
    public WebElement waitForElementToBeClickable(String locatorName, String customName, Map<String, String> replaceKeyVal) {
        By locBy = pageObjectRepoHelper.getObject(locatorName, replaceKeyVal);
        WebElement element = waitForElementToDisplay(locatorName, customName, replaceKeyVal);
        return new WebDriverWait(driver, defaltWaitTimeForElement).until(ExpectedConditions.elementToBeClickable(element));
    }


    @Step("Wait For Element '{0}' To Display")
    public WebElement waitForElementToDisplay(String locatorName) {
        By locBy = pageObjectRepoHelper.getObject(locatorName);
        return new WebDriverWait(driver, defaltWaitTimeForElement).until(ExpectedConditions.visibilityOfElementLocated(locBy));
    }

    @Step("Wait For Element '{1}' To Display")
    public WebElement waitForElementToDisplay(String locatorName, String customName, Map<String, String> replaceKeyVal) {
        By locBy = pageObjectRepoHelper.getObject(locatorName, replaceKeyVal);
        return new WebDriverWait(driver, defaltWaitTimeForElement).until(ExpectedConditions.visibilityOfElementLocated(locBy));
    }

    @Step("is Element '{0}' Displayed")
    public boolean isElementDisplayed(String locatorName, int waitTimeInSeconds) {
        By locBy = pageObjectRepoHelper.getObject(locatorName);
        try{
            WebElement ele = new WebDriverWait(driver, waitTimeInSeconds).until(ExpectedConditions.visibilityOfElementLocated(locBy));
            return ele.isDisplayed();
        }catch (Exception e)
        {
            return false;
        }

    }

    @Step("is Element '{2}' Displayed")
    public boolean isElementDisplayed(String locatorName, int waitTime, String customName, Map<String, String> replaceKeyVal) {
        By locBy = pageObjectRepoHelper.getObject(locatorName, replaceKeyVal);
        try{
            WebElement ele = new WebDriverWait(driver, waitTime).until(ExpectedConditions.visibilityOfElementLocated(locBy));
            return ele.isDisplayed();
        }catch (Exception e)
        {
            return false;
        }

    }

    @Step("Wait For Element '{1}' To Present")
    public WebElement waitForElementToPresent(String locatorName, String customName, Map<String, String> replaceKeyVal) {
        By locBy = pageObjectRepoHelper.getObject(locatorName, replaceKeyVal);
        return new WebDriverWait(driver, defaltWaitTimeForElement).until(ExpectedConditions.presenceOfElementLocated(locBy));
    }

    @Step("Wait For Element '{0}' To Present")
    public WebElement waitForElementToPresent(String locatorName) {
        By locBy = pageObjectRepoHelper.getObject(locatorName);
        return new WebDriverWait(driver, defaltWaitTimeForElement).until(ExpectedConditions.presenceOfElementLocated(locBy));
    }

    public String getDeviceModelName() {
        String ret ="";
        Capabilities cap = driver.getCapabilities();
        if(cap.getCapability("deviceModel")!=null)
            ret =  (String) cap.getCapability("deviceModel");
        return ret;
    }

    public void attachScreenShotToTheReport(String name) {
        TakesScreenshot tk = (TakesScreenshot) driver;
        byte[] b = tk.getScreenshotAs(OutputType.BYTES);
        Allure.addAttachment(name, "image/png", new ByteArrayInputStream(b), "png");
    }

    @Step("Enter '{0}' with the value '{1}'")
    public void enterText(String locatorName, String value) {
        if (!CommonUtil.isValidString(value))
            Assert.fail("Value to be enter should not be null or empty");
        WebElement element = waitForElementToDisplay(locatorName);
        if (!element.isEnabled())
            Assert.fail("Element " + locatorName + " is disabled");
        element.clear();
        element.sendKeys(value);
    }

    private static int getPortionOfTheNumber(int input, int percentange) {

        double inputD = Double.valueOf(input);
        double percentangeD = Double.valueOf(percentange);

        return (int) (inputD * (percentangeD / 100.0));
    }

    public PLATFORM getPlatform()
    {
        return platform;
    }

    // Change by sonu kumar
    // it is alternative option of take screen sort.
    public void snap(String filename) {

        sleepForMiliseconds(3000);
        // move to new destination
        TakesScreenshot ts1 = (TakesScreenshot) driver;

        File file1 = ts1.getScreenshotAs(OutputType.FILE);

        try {
            Files.copy(file1, new File("D:\\language\\Pima County\\Spanish_language(Pima_County)\\Pima County(Spanish_language)\\" + filename + ".jpg"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}

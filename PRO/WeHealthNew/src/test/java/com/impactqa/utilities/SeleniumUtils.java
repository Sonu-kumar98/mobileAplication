package com.impactqa.utilities;

import io.qameta.allure.Allure;
import io.qameta.allure.Step;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import com.impactqa.utilities.PageObjectRepoHelper.PLATFORM;

import java.io.ByteArrayInputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Map;

/**
 * @author  Sonu Kumar
 * @version 1.0
 * @since   22020-09-12
 * @description This class is Util class to interact with browser
 */
public class SeleniumUtils {
    private WebDriver driver;
    private PageObjectRepoHelper pageObjectRepoHelper;
    private static final int defaltWaitTimeForPageLoad = FrameworkConfig.getNumberEnvProperty("DefaltWaitTimeForPageLoad");
    private static final int defaltWaitTimeForElement = FrameworkConfig.getNumberEnvProperty("DefaltWaitTimeForElement");

    public SeleniumUtils(WebDriver driver, String pageObjectRepoFileName){
        this.driver = driver;
        pageObjectRepoHelper = new PageObjectRepoHelper(pageObjectRepoFileName, PLATFORM.WEB);
    }

    /**
     * Waits till the page loads. With the timeout   defaltWaitTimeForPageLoad
     * DefaltWaitTimeForPageLoad should be set in the config file located at src/test/resources/config.properties
     */
    public void waitForThePageLoad()
    {
        new WebDriverWait(driver, defaltWaitTimeForPageLoad).until(
                webDriver -> ((JavascriptExecutor) webDriver).executeScript("return document.readyState").equals("complete"));
    }


    public void sleep(long milliSec) {
        try {
            Thread.sleep(milliSec);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Step("Set Date Field {0}'")
    public void setDateField(String locatorName, Calendar date)
    {
        WebElement element = waitForElementToDisplay(locatorName);
        if("input".equals(element.getTagName()) && "date".equals(element.getAttribute("type"))) {

            String dateString=  new SimpleDateFormat("yyyy-MM-dd").format(date.getTime());
            ((JavascriptExecutor) driver).executeScript("arguments[0].value='"+dateString+"'", element);
        }else {
            Assert.fail("The element should be <input type=\"date\"/>");
        }
    }

    @Step("JS Click Element {0}'")
    public void javaScriptClick(String locatorName)
    {
        WebElement element = waitForElementToPresent(locatorName);

        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
    }

    /**
     * Waits till element @locatorName is displayed. With the timeout   defaltWaitTimeForElement
     * DefaltWaitTimeForElement should be set in the config file located at src/test/resources/config.properties
     * @param locatorName
     * @return WebElement object
     */
    @Step("Wait For Element '{0}' To Display")
    public WebElement waitForElementToDisplay(String locatorName)
    {
        By locBy = pageObjectRepoHelper.getObject(locatorName);
        return new WebDriverWait(driver, defaltWaitTimeForElement).until(ExpectedConditions.visibilityOfElementLocated(locBy));
    }

    @Step("Wait For Element '{0}' To Be Not Display")
    public void waitForElementToBeNotDisplayed(String locatorName)
    {
        try {
            By locBy = pageObjectRepoHelper.getObject(locatorName);
            new WebDriverWait(driver, defaltWaitTimeForElement).until(ExpectedConditions.invisibilityOfElementLocated(locBy));
        }catch (Exception e){

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

    @Step("Wait For Element '{1}' To Display")
    public WebElement waitForElementToDisplay(String locatorName, String customName, Map<String, String> replaceKeyVal) {
        By locBy = pageObjectRepoHelper.getObject(locatorName, replaceKeyVal);
        return new WebDriverWait(driver, defaltWaitTimeForElement).until(ExpectedConditions.visibilityOfElementLocated(locBy));
    }



    @Step("is Element '{0}' Displayed")
    public boolean isElementDisplayed(String locatorName, int waitTime) {
        By locBy = pageObjectRepoHelper.getObject(locatorName);
        try{
            WebElement ele = new WebDriverWait(driver, waitTime).until(ExpectedConditions.visibilityOfElementLocated(locBy));
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

    /**
     * Waits till element @locatorName is clickable. With the timeout   defaltWaitTimeForElement
     * DefaltWaitTimeForElement should be set in the config file located at src/test/resources/config.properties
     * @param locatorName
     * @return WebElement object
     */
    @Step("Wait For Element '{0}' To Be Clickable")
    public WebElement waitForElementToBeClickable(String locatorName)
    {
        By locBy = pageObjectRepoHelper.getObject(locatorName);
        WebElement element = waitForElementToDisplay(locatorName);
        return new WebDriverWait(driver, defaltWaitTimeForElement).until(ExpectedConditions.elementToBeClickable(element));
    }

    @Step("Wait For Element '{0}' To Be Clickable")
    public WebElement waitForElementToBeClickable(String locatorName, String customName, Map<String, String> replaceKeyVal) {
        By locBy = pageObjectRepoHelper.getObject(locatorName, replaceKeyVal);
        WebElement element = waitForElementToDisplay(locatorName, customName, replaceKeyVal);
        return new WebDriverWait(driver, defaltWaitTimeForElement).until(ExpectedConditions.elementToBeClickable(element));
    }

    /**
     * Will enter @value in the element @locatorName
     *
     * It will check whether element is displayed with the timeout - defaltWaitTimeForElement  before performing the action
     * DefaltWaitTimeForElement should be set in the config file located at src/test/resources/config.properties
     *
     * Additionally element should not be disabled
     *
     * @param locatorName
     * @param value
     */
    @Step("Enter '{0}' with the value '{1}'")
    public void enterText(String locatorName, String value)
    {
        if(!CommonUtil.isValidString(value))
            Assert.fail("Value to be enter should not be null or empty");
        WebElement element = waitForElementToDisplay(locatorName);
        if(!element.isEnabled())
            Assert.fail("Element "+locatorName+" is disabled");
        element.clear();
        element.sendKeys(value);
    }

    @Step("Enter '{1}' with the value '{3}'")
    public void enterText(String locatorName, String customName, Map<String, String> replaceKeyVal, String value)
    {
        if(!CommonUtil.isValidString(value))
            Assert.fail("Value to be enter should not be null or empty");
        WebElement element = waitForElementToDisplay(locatorName, customName, replaceKeyVal);
        if(!element.isEnabled())
            Assert.fail("Element "+locatorName+" is disabled");
        element.clear();
        element.sendKeys(value);
    }

    @Step("Select dropdown '{0}' with the value '{1}'")
    public void selectDropdown(String locatorName, String value)
    {
        if(!CommonUtil.isValidString(value))
            Assert.fail("Option to be selected should not be null or empty");
        WebElement element = waitForElementToDisplay(locatorName);
        if(!element.isEnabled())
            Assert.fail("Element "+locatorName+" is disabled");
        Select select = new Select(element);
        select.selectByVisibleText(value);
    }

    @Step("'{1}' the checkbox '{0}'")
    public void selectCheckbox(String locatorName, String checkUncheck)
    {
        if(!CommonUtil.isValidString(checkUncheck))
            Assert.fail("Value to be enter should not be null or empty");
        else if(!(("check".equals(checkUncheck)) || "uncheck".equals(checkUncheck)))
            Assert.fail("Value to be check or uncheck");

        WebElement element = waitForElementToDisplay(locatorName);
        if(!element.isSelected() && "check".equals(checkUncheck))
            element.click();
        else  if(element.isSelected() && "uncheck".equals(checkUncheck))
            element.click();

    }

    @Step("'{1}' the checkbox '{0}'")
    public void selectCheckboxJS(String locatorName, String checkUncheck)
    {
        if(!CommonUtil.isValidString(checkUncheck))
            Assert.fail("Value to be enter should not be null or empty");
        else if(!(("check".equals(checkUncheck)) || "uncheck".equals(checkUncheck)))
            Assert.fail("Value to be check or uncheck");

        WebElement element = waitForElementToPresent(locatorName);
        if(!element.isSelected() && "check".equals(checkUncheck))
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
        else  if(element.isSelected() && "uncheck".equals(checkUncheck))
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);

    }

    /**
     * Will enter @value (password) in the element @locatorName
     *
     * It will check whether element is displayed with the timeout - defaltWaitTimeForElement  before performing the action
     * DefaltWaitTimeForElement should be set in the config file located at src/test/resources/config.properties
     *
     * Additionally element should not be disabled
     *
     * This will not log password in the allure report
     *
     * @param locatorName
     * @param value
     */
    @Step("Enter '{0}' with the value ******")
    public void enterPassword(String locatorName, String value)
    {
        if(!CommonUtil.isValidString(value))
            Assert.fail("Value to be enter should not be null or empty");
        WebElement element = waitForElementToDisplay(locatorName);
        if(!element.isEnabled())
            Assert.fail("Element "+locatorName+" is disabled");
        element.sendKeys(value);
    }

    /**
     * Will click the element @locatorName
     *
     * It will check whether element is displayed and clickable with the timeout defaltWaitTimeForElement before performing the action
     * DefaltWaitTimeForElement should be set in the config file located at src/test/resources/config.properties
     *
     * @param locatorName
     */
    @Step("Click '{0}'")
    public void click(String locatorName)
    {
        WebElement element = waitForElementToBeClickable(locatorName);
        element.click();
    }

    @Step("Accept the Alert")
    public void acceptAlert()
    {
        new WebDriverWait(driver, 10)
                .until(ExpectedConditions.alertIsPresent())
                .accept();
    }

    @Step("Click '{1}'")
    public void click(String locatorName, String customName, Map<String, String> replaceKeyVal) {
        WebElement element = waitForElementToBeClickable(locatorName, customName, replaceKeyVal);
        element.click();
    }

    /**
     * Will get the text from element @locatorName
     *
     * It will check whether element is displayed with the timeout defaltWaitTimeForElement before performing the action
     * DefaltWaitTimeForElement should be set in the config file located at src/test/resources/config.properties
     *
     * @param locatorName
     * @return String value from element
     */
    @Step("Get text from element '{0}'")
    public String getText(String locatorName)
    {
        WebElement element = waitForElementToDisplay(locatorName);
        return element.getText();
    }

    @Step("Get attribute {1} from element '{0}'")
    public String getAttribute(String locatorName, String attributeName)
    {
        WebElement element = waitForElementToDisplay(locatorName);
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


    /**
     * Wil take the ScreenShot of the current browser session to the Allure Report
     */
    public void attachScreenShotToTheReport(String name)
    {
        TakesScreenshot tk = (TakesScreenshot) driver;
        byte[] b = tk.getScreenshotAs(OutputType.BYTES);
        Allure.addAttachment(name, "image/png", new ByteArrayInputStream(b), "png");
    }
}

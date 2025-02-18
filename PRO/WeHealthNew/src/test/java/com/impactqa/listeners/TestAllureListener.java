package com.impactqa.listeners;

import io.qameta.allure.Attachment;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.impactqa.utilities.DriverProvider;
import org.testng.annotations.Listeners;

public class TestAllureListener implements ITestListener {

	private static String getTestMethodName(ITestResult iTestResult) {
		return iTestResult.getMethod().getConstructorOrMethod().getName();
	}


	// Text attachments for Allure
	@Attachment(value = "Page screenshot", type = "image/png")
	public byte[] saveScreenshotPNG(WebDriver driver) {
		return ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
	}


	// Text attachments for Allure
	@Attachment(value = "{0}", type = "text/plain")
	public static String saveTextLog(String message) {
		return message;
	}

	// HTML attachments for Allure
	@Attachment(value = "{0}", type = "text/html")
	public static String attachHtml(String html) {
		return html;
	}

	@Override
	public void onStart(ITestContext iTestContext) {
		
	}

	@Override
	public void onFinish(ITestContext iTestContext) {
		
	}

	@Override
	public void onTestStart(ITestResult iTestResult) {
		
	}

	@Override
	public void onTestSuccess(ITestResult iTestResult) {
		System.out.println("I am in onTestSuccess method " + getTestMethodName(iTestResult) + " passed");
		Object testClass = iTestResult.getInstance();
		WebDriver driver =null;
		try {
		 driver = (WebDriver) testClass.getClass().getMethod("getDriver").invoke(testClass);
		}catch (Exception e) {
			throw new RuntimeException(e);
		}
		// Allure ScreenShotRobot and SaveTestLog
		if (driver!=null && ((RemoteWebDriver) driver).getSessionId()!=null && driver instanceof WebDriver) {
			System.out.println("Screenshot captured for test case:" + getTestMethodName(iTestResult));
			saveScreenshotPNG(driver);
			saveTextLog("passed and screenshot taken!");
		}	
	}

	@Override
	public void onTestFailure(ITestResult iTestResult) {
		System.out.println("I am in onTestFailure method " + getTestMethodName(iTestResult) + " failed");
		Object testClass = iTestResult.getInstance();
		WebDriver driver =null;
		try {
		 driver = (WebDriver) testClass.getClass().getMethod("getDriver").invoke(testClass);
		}catch (Exception e) {
			throw new RuntimeException(e);
		}

		// Allure ScreenShotRobot and SaveTestLog
		if (driver!=null && ((RemoteWebDriver) driver).getSessionId()!=null && driver instanceof WebDriver) {
			System.out.println("Screenshot captured for test case:" + getTestMethodName(iTestResult));
			saveScreenshotPNG(driver);
			saveTextLog("failed and screenshot taken!");
		}				
	}

	@Override
	public void onTestSkipped(ITestResult iTestResult) {
		System.out.println("I am in onTestSkipped method " + getTestMethodName(iTestResult) + " skipped");
	}

	@Override
	public void onTestFailedButWithinSuccessPercentage(ITestResult iTestResult) {
		System.out.println("Test failed but it is in defined success ratio " + getTestMethodName(iTestResult));
	}
}


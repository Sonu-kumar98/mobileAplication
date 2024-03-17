package com.impactqa.page.web;

import com.impactqa.utilities.SeleniumUtils;
import org.openqa.selenium.WebDriver;

/**
 * @author  Sonu Kumar
 * @since   22020-09-12
 * @description All page classes should be extended by this class. It will have handle common elements to all the pages
 */
public class BasePage {

    protected SeleniumUtils seleniumUtils;

    public BasePage(WebDriver driver, String pageObjectRepoFileName )
    {
        this.seleniumUtils = new SeleniumUtils(driver, pageObjectRepoFileName);
    }

    public SeleniumUtils getSeleniumUtils(){
        return seleniumUtils;
    }
}

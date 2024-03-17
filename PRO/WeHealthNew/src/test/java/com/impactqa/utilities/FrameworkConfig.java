package com.impactqa.utilities;

import org.testng.Assert;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * @author  Sonu Kumar
 * @version 1.0
 * @since   22020-09-12
 * @description This class is Util class to get framework config values like timeouts, URL, paths, etc
 * Framework Config values should be provided in the file src/test/resources/config.properties
 */
public class FrameworkConfig extends CommonUtil{

    private static final String envProperties = System.getProperty("user.dir") + "/src/test/resources/" + "config.properties";
    private static final Properties props = loadFrameworkConfigProperties();


    /**
     * loads config.properties
     * @return  Properties instance
     */
// change private to public
    // change by sonu kumar
    public static Properties loadFrameworkConfigProperties()
    {
        Properties props = new Properties();
        try {
            InputStream steam = new FileInputStream(envProperties);

            props.load(steam);
        } catch (IOException e) {
            Assert.fail("Error occured while reading Framework Config file: "+envProperties, e);
        }

        return props;
    }
    /**
     * return String value of the config property
     * @param key
     * @return  String value of the property
     */
    static public String getStringEnvProperty(String key)
    {
        Assert.assertTrue(isValidString(key), "Env Property should not be null or empty");
        String value=props.getProperty(key);
        Assert.assertTrue(isValidString(value),"Property with key '"+key+"' is not found or it's empty");
        return value;
    }

    /**
     * return numeric value of the config property
     * @param key
     * @return  numeric value of the property
     */
    static public int getNumberEnvProperty(String key)
    {
        String stringVal = getStringEnvProperty(key);
        if(!isValidInteger(stringVal))
            Assert.fail("Env Property is not a number");
        return getNumericValue(stringVal);
    }

}

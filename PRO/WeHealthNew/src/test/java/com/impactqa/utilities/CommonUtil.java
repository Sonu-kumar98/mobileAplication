package com.impactqa.utilities;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.testng.Assert;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Sonu Kumar
 * @version 1.0
 * @since   22020-09-12
 * @description This class is Util class which will provides common methods for validations/manipulations/conversion
 */

public class CommonUtil {

    /**
     * Return the status whether the given string is valid
     * @param inputString
     * @return true or false
     */
    public static boolean isValidString(String inputString)    {
        return (inputString != null && !inputString.trim().isEmpty());
    }

    /**
     * Return the status whether the given string is valid number
     * @param inputNumberInString
     * @return true or false
     */
    public static boolean isValidInteger(String inputNumberInString)    {
        return StringUtils.isNumeric(inputNumberInString);
    }

    /**
     * Return convert string to number
     * @param inputNumberInString
     * @return integer value
     */
    public static int getNumericValue(String inputNumberInString)    {
        return Integer.valueOf(inputNumberInString);
    }

    /**
     * Return the status whether the given @regexPatternStr matches with @textContent
     * @param regexPatternStr
     * @param textContent
     * @return true or false
     */
    public static boolean isRegexParrenMatched(String regexPatternStr, String textContent)    {
        Pattern pattern = Pattern.compile(regexPatternStr);
        Matcher matcher = pattern.matcher(textContent);
        return matcher.find();
    }

    /**
     * Return the extracted  Sub-String using 1st regex Group.
     * the regex @regexPatternStr should have grouping pattern like "'(.*?)'".
     * It will first check whether pattern is found. then it will return 1st group
     * @param regexPatternStr
     * @param textContent
     * @return true or false
     */
    public static String extractSubStringUsingRegex(String regexPatternStr, String textContent){
        String extractedValue = null;
        Pattern pattern = Pattern.compile(regexPatternStr);
        Matcher matcher = pattern.matcher(textContent);
        if (matcher.find()){
            if(matcher.groupCount()>0)
                extractedValue = matcher.group(1);
            else
                Assert.fail("No matching group found for the extraction");
        }
        else
            Assert.fail("Pattern '"+regexPatternStr+"' cannot be found in the content: "+textContent);
        return extractedValue;
    }

    //Refer: https://wehealth.atlassian.net/wiki/spaces/WH/pages/263782407/Remote+String+Placeholders
    public static String Replace_DAYS_FROM_EXPOSURE(String actualString)
    {

        if(actualString.contains("DAYS_FROM_EXPOSURE"))
        {
            String ret = "";
            String stringPlaceholder = extractSubStringUsingRegex("(DAYS_FROM_EXPOSURE.+})", actualString);
            int days = Integer.parseInt(extractSubStringUsingRegex( "(\\d+)",   stringPlaceholder));
            if(stringPlaceholder.toUpperCase().contains("FALSE"))
            {
                Calendar cal = Calendar.getInstance();
                cal.add(Calendar.DATE, days);
                return new SimpleDateFormat("MMM d, yyyy").format(cal.getTime());
            }
            else{
                Calendar cal = Calendar.getInstance();
                cal.add(Calendar.DATE, days);
                if(cal.get(Calendar.DAY_OF_WEEK)==7)
                    cal.add(Calendar.DATE, 2);
                else if(cal.get(Calendar.DAY_OF_WEEK)==1)
                    cal.add(Calendar.DATE, 1);
                return new SimpleDateFormat("MMM d, yyyy").format(cal.getTime());
            }
        }
        else
            return actualString;
    }

    public static void main(String[] args) {

        String s = "Stay at home and self-quarantine until DAYS_FROM_EXPOSURE{LATEST,8,FALSE}";
//        System.out.println(getText(s, "(\\d+)"));
//        System.out.println(getText(s, "(DAYS_FROM_EXPOSURE.+})"));
        System.out.println(Replace_DAYS_FROM_EXPOSURE(s));
    }
}

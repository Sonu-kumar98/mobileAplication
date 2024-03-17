package com.impactqa.test.mobile;

import com.impactqa.listeners.TestAllureListener;
import com.impactqa.listeners.TestNGExecutionLister;
import com.impactqa.page.mobile.*;
import com.impactqa.page.web_new.LoginPage;
import com.impactqa.page.web_new.MenuPageWeb;
import com.impactqa.page.web_new.MobileAppSettingsPage;
import com.impactqa.utilities.DriverProvider;
import com.impactqa.utilities.ExcelUtil;
import com.impactqa.utilities.FrameworkConfig;
import com.impactqa.utilities.PageObjectRepoHelper;
import io.qameta.allure.Description;
import io.qameta.allure.Story;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.response.ResponseBody;
import io.restassured.specification.RequestSpecification;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Listeners;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import java.net.MalformedURLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Listeners( {TestNGExecutionLister.class})
public class Dummy {


//    @Test( priority = 1, description = "Login To The Application")
//    @Story("Mobile App Settings")
//    @Description("Login To The Application")
//    public void loginToTheApplication()
//    {
//        Assert.fail();
//    }

    public static void main(String[] args) {
        System.out.println(generateverificationCode("Arizona"));

    }
    public static String generateverificationCode(String env){

        RequestSpecification req = RestAssured.given()
//                .log().all()
                .header("content-type", "application/json")
                .body("{\n" +
                        "  \"symptomDate\": \"2021-03-07\",\n" +
                        "  \"testDate\": \"\",\n" +
                        "  \"testType\": \"confirmed\",\n" +
                        "  \"tzOffset\": 0,\n" +
                        "  \"padding\": \"66666666666666666666\"\n" +
                        "}");
        if("Arizona".equals(env))
            req.header("x-api-key", "8BFF236A6919433DA4B312733A98BCCA");
        else
            req.header("x-api-key", "A92B726EA8244FE5A167974499B4ECF1");



        Response res =req.post("https://adminapi.verification.api.wehealth.org/api/issue");
        if(res.statusCode()!=200)
            Assert.fail("Status code shoud  be 200");
        String verificationCode = res.jsonPath()
                .getString("code");

//        System.out.println( res.asString());
        System.out.println( verificationCode );
        return verificationCode;

    }
    public static String Replace_DAYS_FROM_EXPOSURE(String actualString)
    {

        if(actualString.contains("DAYS_FROM_EXPOSURE"))
        {
            String ret = "";
            String stringPlaceholder = getText(actualString, "(DAYS_FROM_EXPOSURE.+})");
            int days = Integer.parseInt(getText(stringPlaceholder, "(\\d+)"));
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

    public static String getText(String mydata, String patternStr)
    {
        //"some string with 'the data i want' inside"
        //pattern "'(.*?)'"
        Pattern pattern = Pattern.compile(patternStr);
        Matcher matcher = pattern.matcher(mydata);
        if (matcher.find())
        {
            return matcher.group(1);
        }
        else
            return "";
    }






}

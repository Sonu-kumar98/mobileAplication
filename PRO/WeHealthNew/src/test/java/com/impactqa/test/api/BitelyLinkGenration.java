package com.impactqa.test.api;
import com.impactqa.listeners.TestNGExecutionLister;
import com.impactqa.utilities.ExcelUtil;
import com.impactqa.utilities.FrameworkConfig;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import java.util.List;
import java.util.Map;

@Listeners({TestNGExecutionLister.class})
public class BitelyLinkGenration {

    List<Map<String,String>> communityShortNameList;
    List<Map<String, String>> nextStepKeyShortNameList;
    Map<String, Map<String, Object>> bitlyLinkListMap;

    @BeforeClass
    public void getTestData()
    {
        ExcelUtil excel = new ExcelUtil();
        excel.setWorkbook(FrameworkConfig.getStringEnvProperty("TestDataFileLocation"),
                "CommunityShortName");

        communityShortNameList = excel.getAllRows();
        excel.setWorkbook(FrameworkConfig.getStringEnvProperty("TestDataFileLocation"),
                "NextStepKeyShortName");

        nextStepKeyShortNameList = excel.getAllRows();

    }

    @Test
    public void biltyTagsCheck() {
        //bitlyLinkListMap = getLinkDetails();

        String buildType = "Arizona";

        String urlJson = "";
        if (buildType.contains("Arizona"))
            urlJson = "https://org-wehealth-demo.firebaseio.com/gov-azdhs-covidwatch.json?print=pretty";
        else
            urlJson = "https://org-wehealth-demo.firebaseio.com/org-wehealth-exposure.json?print=pretty";


        Response jsonResponse = RestAssured.given().get(urlJson);
        List<Map<String, Object>> communityList = jsonResponse.jsonPath().get("community");
        System.out.println("" + communityList);

        for (Map<String, Object> communityMap : communityList) {
            String communityName = (String) communityMap.get("name");
            System.out.println(""+communityName);
            String communityID = (String) communityMap.get("id");
            System.out.println(""+communityID );


        }

    }
    }

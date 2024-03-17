package com.impactqa.test.api;

import com.impactqa.listeners.TestNGExecutionLister;
import com.impactqa.utilities.ExcelUtil;
import com.impactqa.utilities.FrameworkConfig;
import io.qameta.allure.Allure;
import io.qameta.allure.model.Status;
import io.qameta.allure.model.StepResult;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Listeners;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.util.*;

@Listeners({TestNGExecutionLister.class})
public class BitlyLinkTagTest {

    List<String> failures = new LinkedList<>();
    List<Map<String, String>> communityShortNameList;
    List<Map<String, String>> nextStepKeyShortNameList;

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
    public void biltyTagsCheck()
    {
        Map<String, Map<String, Object>> bitlyLinkListMap = getLinkDetails();
        Response communityResponse = RestAssured.given().get("https://exposure.wehealth.org/config/dev/gov.azdhs.covidwatch.json");
        List<Map<String, Object>> communityList = communityResponse.jsonPath().get();
        for(Map<String, Object> communityMap: communityList){
            String communityName = (String) communityMap.get("name");
            for(String communityMapKey : communityMap.keySet()){
                if(communityMapKey.startsWith("nextSteps")){
                    String nextStepKeyName = communityMapKey;

                    List<Map<String, Object>> nextSteps = (List<Map<String, Object>>) communityMap.get(nextStepKeyName);
                    int nextStepCount=0;
                    for(Map<String, Object> nextStepMap : nextSteps){
                        nextStepCount++;
                        String hierarchy = "Community: "+communityName+" > "+nextStepKeyName+" > Step-"+(nextStepCount)+"";
                        int type = (int) nextStepMap.get("type");
                        if(type==2)
                        {
                            List<String> stepFailures = new LinkedList<>();
                            String url = (String) nextStepMap.get("url");
                            if(url.startsWith("https://c.wehealth.org") || url.startsWith("https://bit.ly") ||
                                    url.startsWith("http://c.wehealth.org") || url.startsWith("http://bit.ly")
                              )
                            {

                                String linkId = url.replace("https://", "").replace("http://", "");

                                Map<String, Object> linkDetailByID =null;
                                if(bitlyLinkListMap.containsKey(linkId))
                                    linkDetailByID = bitlyLinkListMap.get(linkId);
                                else{
                                    linkDetailByID = getLinkDetailByID(linkId);
                                }

                                if(linkDetailByID!=null)
                                {
                                    List<String> tags = (List<String>) linkDetailByID.get("tags");
                                    System.out.println(communityName+"~"+nextStepKeyName+"~"+tags);
                                    String communityShortName = communityShortNameList.stream().filter(map -> map.get("CommunityName").equals(communityName)).findFirst().get().get("CommunityShortName");
                                    String nextStepKeyShortName = nextStepKeyShortNameList.stream().filter(map -> map.get("NextStepKeyName").equals(nextStepKeyName)).findFirst().get().get("NextStepKeyShortName");
                                    if(tags.size()==0)
                                        stepFailures.add("There are no tags assigned to the URL: "+url+" . Expected Tags: ["+communityShortName+", "+nextStepKeyShortName+"]");
                                    if(tags.size()==1)
                                        stepFailures.add("Only one tag is assigned to the URL: "+url+" . Expected Tags: ["+communityShortName+", "+nextStepKeyShortName+"]");
                                    if(tags.size()>2)
                                        stepFailures.add("Additional tags are assigned to the URL: "+url+" . Expected Tags: ["+communityShortName+", "+nextStepKeyShortName+"]. Actual Tags: "+tags.toString());
                                    for(String tag:tags){
                                        if((tag.equals(communityShortName) || tag.equals(nextStepKeyShortName)))
                                        {}
                                        else
                                            stepFailures.add("Incorrect tag: '"+tag+"' is assigned assigned to the URL: "+url+" . Expected Tags: ["+communityShortName+", "+nextStepKeyShortName+"]");
                                    }
                                }
                                else
                                    stepFailures.add("URL is invalid. URL: "+url);
                            }
                            else
                                stepFailures.add("URL should be a valid bilty link. Actual link: "+url);

                            String stepFailuresStr ="";
                            for( String stepFailure: stepFailures) {

                                stepFailuresStr = stepFailuresStr + "   - "+stepFailure+"\n";
                            }
                            if(!stepFailuresStr.isEmpty())
                                failures.add(hierarchy + "\n " + stepFailuresStr);

                        }
                    }

                }
            }
        }

        if(failures.size()>0)
        {
            System.out.println("failures: \n");
            String failuresStr ="";
            for (int i = 0; i < failures.size() ; i++) {
                String fail = failures.get(i);
                failuresStr=failuresStr+(i+1)+". "+fail+"\n\n";
            }
            System.out.println(failuresStr);
            Assert.fail(failuresStr);
        }
    }


    private Map<String, Map<String, Object>> getLinkDetails()
    {
        String url = "https://api-ssl.bitly.com/v4/groups/Bk9e7bqGxsV/bitlinks?page=1&size=100";
        Map<String, Map<String, Object>> linkmap = new LinkedHashMap<>();
        while(!"".equals(url))
        {
            System.out.println(url);
            Response response = RestAssured.given()
                    .header("Authorization", "Bearer 0d52df02dd5866529890ca736cbf67dcb0842f6c")
                    .get(url);
            url = response.jsonPath().getString("pagination.next");
            List<Map<String, Object>> lst = response.jsonPath().getList("links");
            for(Map<String, Object> link: lst)
                linkmap.put((String) link.get("id"), link);
        }
        return linkmap;
    }

    private Map<String, Object> getLinkDetailByID(String id)
    {
        String url = "https://api-ssl.bitly.com/v4/bitlinks/"+id;
        System.out.println(url);
            Response response = RestAssured.given()
                    .header("Authorization", "Bearer 0d52df02dd5866529890ca736cbf67dcb0842f6c")
                    .get(url);
            if(response.statusCode()==200)
                return (Map<String, Object> ) response.jsonPath().get();
            else
                return null;
    }

}

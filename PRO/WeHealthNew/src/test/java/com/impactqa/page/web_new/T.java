package com.impactqa.page.web_new;

import com.impactqa.utilities.FrameworkConfig;
import io.restassured.path.json.JsonPath;

import java.io.File;
import java.util.List;
import java.util.Map;

public class T {
    public static void main(String[] args) {



        JsonPath jsonpath = JsonPath.from(new File(System.getProperty("user.dir") + "/" + FrameworkConfig.getStringEnvProperty("JsonTemplatepath") + "/gov.azdhs.covidwatch.json"));
        String region = "State of Arizona";
        List<Map<String, String>> steps = jsonpath.get("findAll { it.name=='" + region + "' }[0].nextStepsNoSignificantExposure");
        System.out.println(steps.get(0).get("description"));
        
    }
}

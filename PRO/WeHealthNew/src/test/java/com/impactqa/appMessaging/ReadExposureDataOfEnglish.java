package com.impactqa.appMessaging;
import com.impactqa.utilities.ExcelUtil;
import com.impactqa.utilities.FrameworkConfig;
import com.jayway.jsonpath.JsonPath;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;
/**
 * @author  Sonu Kumar
 * @since   2021-15-06
 * @description This class conatins all functionality of app messaging functionality.
 */

public class ReadExposureDataOfEnglish {
    ExcelUtil excel = new ExcelUtil();
    HashMap<String, List<String>> jsonActualNextStep = new HashMap<>();
    HashMap<String, List<String>> jsonActualWhereIsMyCode = new HashMap<>();
    HashMap<String, List<String>> jsonActualWhereIsVaccine = new HashMap<>();
    Properties pro = FrameworkConfig.loadFrameworkConfigProperties();
    String weHealthDemoApi = pro.getProperty("weHealthApi");

    public void setDataWorkbook() {
        String spreadSheetLocation = pro.getProperty("spreadSheetFileLocation");
        String spreadSheetFileName = pro.getProperty("spreadSheetName");
        excel.setWorkbook(spreadSheetLocation, spreadSheetFileName);

    }

    public List<String> readData(String exposerType) {
        List<String> values = null;
        if (exposerType.equals("No Exposure"))
            values = excel.getCellsValue(5, 2, 5);
        else if (exposerType.equals("Low Exposure"))
            values = excel.getCellsValue(5, 8, 11);
        else if (exposerType.equals("High Exposure"))
            values = excel.getCellsValue(5, 14, 17);
        else if (exposerType.equals("Verified Positive"))
            values = excel.getCellsValue(5, 20, 23);
        else if (exposerType.equals("Where is my code"))
            values = excel.getCellsValue(5, 26, 27);
        else if (exposerType.equals("Where is my Vaccine"))
            values = excel.getCellsValue(5, 32, 35);

        return values;
    }

    // Fetching all data of nextStep:
    public void getJsonDataOfNextStep() {
        Response jsonResponse = RestAssured.given().get(weHealthDemoApi);
        String[] exposerTypes = {"No Exposure", "Low Exposure", "High Exposure", "Verified Positive"};
        for (int i = 0; i < 4; i++) {
            List<String> jsonDatas = new ArrayList<>();
            for (int j = 0; j < 4; j++) {
                String s = JsonPath.read(jsonResponse.asString(),"$.messaging[\"us-az-maricopa\"].en.nextSteps["+i+"].steps["+j+"].description");
                jsonDatas.add(s);
            }
            jsonActualNextStep.put(exposerTypes[i], jsonDatas);
        }
    }

    // Fetching all data of where is my Vaccine:
    public void getJsonDataOfVaccinationInfo(){
        Response jsonResponse = RestAssured.given().get(weHealthDemoApi);
        String[] exposerTypeVaccine = {"Where is my Vaccine"};
        List<String> jsonVaccineInfo = new ArrayList<>();
        for(int i=0; i<4;i++) {
            String s3 =  JsonPath.read(jsonResponse.asString(), "$.messaging[\"us-az-maricopa\"].en.vaccinationInfo["+i+"].description");
            jsonVaccineInfo.add(s3);
        }
        int i=0;
        jsonActualWhereIsVaccine.put(exposerTypeVaccine[i],jsonVaccineInfo);

    }

    // Fetching all data of where is my Code:
    public void getDataOfVaccinationCodeInfo(){
        Response jsonResponse = RestAssured.given().get(weHealthDemoApi);
        String[] exposerTypeCode = {"Where is my code"};
        List<String> jsonDataCode = new ArrayList<>();
        for(int i=0; i<2;i++) {
            String s2 =  JsonPath.read(jsonResponse.asString(), "$.messaging[\"us-az-maricopa\"].en.verificationCodeInfo["+i+"].description");
            jsonDataCode.add(s2);
        }
        int i=0;
        jsonActualWhereIsMyCode.put(exposerTypeCode [i], jsonDataCode);
    }

    // ValidateNext Nextstep:
    public boolean validateJsonNextStep(String exposerType) {
        List<String> jsonDatas = jsonActualNextStep .get(exposerType);
        List<String> strings = readData(exposerType);
        for (String message : strings) {
            boolean flag = false;
            for (String jsonData : jsonDatas) {
                if (message.equals(jsonData)) {
                    flag = true;
                    System.out.println(message + " : Json is matching perfectly");
                    break;
                }
            }
            if (flag == false) {
                System.out.println(message + " : is not found in the Json");
            }
        }

        return false;
    }

// Validate Vaccine Information  inside the json
public boolean validateVaccineInfo(String exposureType){
    List<String> jsonVaccineDataInfo = jsonActualWhereIsVaccine.get(exposureType);
    List<String> whereIsMyCodeInfo = readData(exposureType);
    for (String messageOfWhereIsMyVaccine: whereIsMyCodeInfo) {
        boolean flag = false;
        for (String jsonData :jsonVaccineDataInfo){

            if(messageOfWhereIsMyVaccine.equals(jsonData)){
                flag = true;
                System.out.println(messageOfWhereIsMyVaccine + "Json data matching perfectly");
                break;
            }

        }
        if (flag==false){
            System.out.println(messageOfWhereIsMyVaccine +": is not found in the json");
        }

    }
    return  false;

}
    // ValidateNext Where is My Code inside the json.
    public  boolean validateWhereIsMyCodeJson(String exposureType){
        List<String>jsonDataCode = jsonActualWhereIsMyCode.get(exposureType);
        List<String> dataWhereMyCode = readData(exposureType);
        for (String messageOfWhereIsMyCode:dataWhereMyCode) {
            boolean flag = false;
            for (String jsonData: jsonDataCode) {
                if(messageOfWhereIsMyCode.equals(jsonData)) {
                    flag = true;
                    System.out.println(messageOfWhereIsMyCode + " : Json data matching perfectly");
                    break;
                }
            }
            if (flag == false){
                System.out.println(messageOfWhereIsMyCode +": is not found in the json");
            }

        }
        return false;

    }

    public static void main(String[] args) {
        ReadExposureDataOfEnglish readExposerData = new ReadExposureDataOfEnglish();
        readExposerData.setDataWorkbook();
        readExposerData.getJsonDataOfNextStep();
        readExposerData.validateJsonNextStep("No Exposure");
        readExposerData.validateJsonNextStep("High Exposure");
        readExposerData.validateJsonNextStep("Low Exposure");
        readExposerData.validateJsonNextStep("Verified Positive");
        readExposerData.getJsonDataOfVaccinationInfo();
        readExposerData.validateVaccineInfo("Where is my Vaccine");
        readExposerData.getDataOfVaccinationCodeInfo();
        readExposerData.validateWhereIsMyCodeJson("Where is my code");

    }
}

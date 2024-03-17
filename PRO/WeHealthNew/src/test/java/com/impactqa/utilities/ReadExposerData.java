package com.impactqa.utilities;
import com.jayway.jsonpath.JsonPath;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ReadExposerData {
    static ExcelUtil excelUtil2 = new ExcelUtil();
    HashMap<String, List<String>> jsonActualNextStep = new HashMap<>();
    HashMap<String, List<String>> jsonActualWhereIsMyCode = new HashMap<>();
    HashMap<String, List<String>> jsonActualWhereIsVaccine = new HashMap<>();

    public static void setDataWorkbook() {
        excelUtil2.setWorkbook("F:\\ReadDataFrom_Excel\\Maricopa_County.xlsx", "English App Settings & Messagin");
        //excelUtil2.setWorkbook("F:\\ReadDataFrom_Excel\\App Messaging AZ-WMAT_FAIR 2021-05-25.xlsx", "App Settings & Messaging");

    }

    public List<String> readData(String exposerType) {
        List<String> values = null;
        if (exposerType.equals("No Exposure"))
            values = excelUtil2.getCellsValue(5, 2, 5);
        else if (exposerType.equals("Low Exposure"))
            values = excelUtil2.getCellsValue(5, 8, 11);
        else if (exposerType.equals("High Exposure"))
            values = excelUtil2.getCellsValue(5, 14, 17);
        else if (exposerType.equals("Verified Positive"))
            values = excelUtil2.getCellsValue(5, 20, 23);
        else if (exposerType.equals("Where is my code"))
            values = excelUtil2.getCellsValue(5, 26, 27);
        else if (exposerType.equals("Where is my Vaccine"))
            values = excelUtil2.getCellsValue(5, 32, 35);

        return values;
    }

    public static void main(String[] args) {
        setDataWorkbook();
        String exposerType = "No Exposure";
        List<Map<String, String>> values = null;
        if (exposerType.equals("No Exposure"))
            values = excelUtil2.getRowsValue(2, 5);
        System.out.println(values);

        //        else if (exposerType.equals("Low Exposure"))
//            values = excelUtil2.getCellsValue(5, 8, 11);
//        else if (exposerType.equals("High Exposure"))
//            values = excelUtil2.getCellsValue(5, 14, 17);
//        else if (exposerType.equals("Verified Positive"))
//            values = excelUtil2.getCellsValue(5, 20, 23);
//        else if (exposerType.equals("Where is my code"))
//            values = excelUtil2.getCellsValue(5, 26, 27);
//        else if (exposerType.equals("Where is my Vaccine"))
//            values = excelUtil2.getCellsValue(5, 32, 35);

    }

    // Fetching all data of nextStep:
    public void getJsonDataOfNextStep() {
        Response jsonResponse = RestAssured.given().get("https://org-wehealth-demo.firebaseio.com/gov-azdhs-covidwatch.json");
        String[] exposerTypes = {"No Exposure", "Low Exposure", "High Exposure", "Verified Positive"};
        for (int i = 0; i < 4; i++) {
            List<String> jsonDatas = new ArrayList<>();
            for (int j = 0; j < 4; j++) {
                String s = JsonPath.read(jsonResponse.asString(), "$.messaging[\"us-az-maricopa\"].en.nextSteps[" + i + "].steps[" + j + "].description");
                jsonDatas.add(s);
            }
            jsonActualNextStep.put(exposerTypes[i], jsonDatas);
        }
    }

    //Fetching data of Where is my Code json
    public void getDataOfVaccinationCodeInfo() {
        Response jsonResponse = RestAssured.given().get("https://org-wehealth-demo.firebaseio.com/gov-azdhs-covidwatch.json");
        String[] exposerTypeCode = {"Where is my code"};

        List<String> jsonDataCode = new ArrayList<>();
        for (int i = 0; i < 2; i++) {
            String s2 = JsonPath.read(jsonResponse.asString(), "$.messaging[\"us-az-maricopa\"].en.verificationCodeInfo[" + i + "].description");
            jsonDataCode.add(s2);
        }
        int i = 0;
        jsonActualWhereIsMyCode.put(exposerTypeCode[i], jsonDataCode);
    }

    // fetching data of  Where is my vaccine Info json
    public void getJsonDataOfVaccinationInfo() {
        Response jsonResponse = RestAssured.given().get("https://org-wehealth-demo.firebaseio.com/gov-azdhs-covidwatch.json");
        String[] exposerTypeVaccine = {"Where is my Vaccine"};
        List<String> jsonVaccineInfo = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            String s3 = JsonPath.read(jsonResponse.asString(), "$.messaging[\"us-az-maricopa\"].en.vaccinationInfo[" + i + "].description");
            jsonVaccineInfo.add(s3);
        }
        int i = 0;
        jsonActualWhereIsVaccine.put(exposerTypeVaccine[i], jsonVaccineInfo);

    }

    // ValidateNext Nextstep of json.
    public boolean validateJsonNextStep(String exposerType) {
        List<String> jsonDatas = jsonActualNextStep.get(exposerType);
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

    // ValidateNext Where is My Code inside the json.
    public boolean validateWhereIsMyCodeJson(String exposureType) {
        List<String> jsonDataCode = jsonActualWhereIsMyCode.get(exposureType);
        List<String> dataWhereMyCode = readData(exposureType);
        for (String messageOfWhereIsMyCode : dataWhereMyCode) {
            boolean flag = false;
            for (String jsonData : jsonDataCode) {
                if (messageOfWhereIsMyCode.equals(jsonData)) {
                    flag = true;
                    System.out.println(messageOfWhereIsMyCode + " : Json data matching perfectly");
                    break;
                }
            }
            if (flag == false) {
                System.out.println(messageOfWhereIsMyCode + ": is not found in the json");
            }

        }
        return false;

    }

    // Validate Vaccine Information  inside the json
    public boolean validateVaccineInfo(String exposureType) {
        List<String> jsonVaccineDataInfo = jsonActualWhereIsVaccine.get(exposureType);
        List<String> whereIsMyCodeInfo = readData(exposureType);
        for (String messageOfWhereIsMyVaccine : whereIsMyCodeInfo) {
            boolean flag = false;
            for (String jsonData : jsonVaccineDataInfo) {

                if (messageOfWhereIsMyVaccine.equals(jsonData)) {
                    flag = true;
                    System.out.println(messageOfWhereIsMyVaccine + "Json data matching perfectly");
                    break;
                }

            }
            if (flag == false) {
                System.out.println(messageOfWhereIsMyVaccine + ": is not found in the json");
            }

        }
        return false;

    }


//    public static void main(String[] args) {
//        ReadExposerData readExposerData = new ReadExposerData();
//        readExposerData.setDataWorkbook();
//        readExposerData.getJsonDataOfNextStep();
//        readExposerData.validateJsonNextStep("No Exposure");
//        readExposerData.validateJsonNextStep("High Exposure");
//        readExposerData.validateJsonNextStep("Low Exposure");
//        readExposerData.validateJsonNextStep("Verified Positive");
//        readExposerData.getDataOfVaccinationCodeInfo();
//        readExposerData.validateWhereIsMyCodeJson("Where is my code");
//        readExposerData.getJsonDataOfVaccinationInfo();
//        readExposerData.validateVaccineInfo("Where is my Vaccine");
//
//
//    }


//    public void testWeHealth() throws JsonProcessingException {
//        ObjectMapper objectMapper = new ObjectMapper();
//        String jsonResponse = RestAssured.given().get("https://org-wehealth-demo.firebaseio.com/gov-azdhs-covidwatch.json").asString();
//        WeHealthResponseDto weHealthResponseDto = objectMapper.readValue(jsonResponse, WeHealthResponseDto.class);
//        weHealthResponseDto.getAppState().getHighExposur();
//    }
}
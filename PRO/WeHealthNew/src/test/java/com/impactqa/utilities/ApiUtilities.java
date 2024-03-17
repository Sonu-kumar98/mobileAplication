
package com.impactqa.utilities;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.testng.Assert;

public class ApiUtilities {

    public static void main(String[] args) {
        System.out.println(getVerificationCode("Arizona"));
    }
    public static String getVerificationCode(String state){

        RequestSpecification req = RestAssured.given()
                .log().all()
                .header("content-type", "application/json")
                .body("{\n" +
                        "  \"symptomDate\": \"2021-03-08\",\n" +
                        "  \"testDate\": \"\",\n" +
                        "  \"testType\": \"confirmed\",\n" +
                        "  \"tzOffset\": 0,\n" +
                        "  \"padding\": \"66666666666666666666\"\n" +
                        "}");

        if ("Arizona".equals(state))
            req.header("x-api-key", "8BFF236A6919433DA4B312733A98BCCA");
        else
            req.header("x-api-key", "A92B726EA8244FE5A167974499B4ECF1");

        Response res = req.post("https://adminapi.verification.api.wehealth.org/api/issue");
        if (res.statusCode() != 200)
            Assert.fail("Status code should  be 200");
        String verificationCode = res.jsonPath()
                .getString("code");
        return verificationCode;
    }


}

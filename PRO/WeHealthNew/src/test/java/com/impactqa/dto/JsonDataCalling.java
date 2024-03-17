package com.impactqa.dto;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.RestAssured;
import io.restassured.response.Response;

public class JsonDataCalling {

    public static void main(String[] args) {

        Response jsonResponse = RestAssured.given().get("https://org-wehealth-demo.firebaseio.com/gov-azdhs-covidwatch.json");

        try{

            ObjectMapper objectMapper = new ObjectMapper();
            WeHealthResponseDto  weHealthResponseDto=  objectMapper.readValue(jsonResponse.asString(), WeHealthResponseDto.class);
            System.out.println(weHealthResponseDto.getMessaging());



        }catch(Exception e){
            e.printStackTrace();

        }




    }
}

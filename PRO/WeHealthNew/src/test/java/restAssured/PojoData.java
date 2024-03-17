// Made by Sonu kumar
package restAssured;
import com.impactqa.utilities.ExcelUtil;
import com.impactqa.utilities.FrameworkConfig;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.testng.Assert;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Map;
import java.util.Properties;
public class PojoData {
    Response res;
    public  String fetchData(String state, Calendar symptomsDate, Calendar testingDate) throws IOException {

        Properties prop = FrameworkConfig.loadFrameworkConfigProperties();
        String url = prop.getProperty("ArizonaDemo");
        String apikeyArizona= prop.getProperty("ArizonaApikey");
        String apikeyBermuda= prop.getProperty("BermudaApikey");
        System.out.println(apikeyArizona);
        System.out.println(apikeyBermuda);
        String symptomDateStr = "";
        String testingDateStr = "";

        if(testingDate!=null)
            testingDateStr =  new SimpleDateFormat("yyyy-MM-dd").format(testingDate.getTime());

        if(symptomsDate!=null)
            symptomDateStr =  new SimpleDateFormat("yyyy-MM-dd").format(symptomsDate.getTime());

        PojoRequest data = new PojoRequest(symptomDateStr, testingDateStr, "confirmed", 0, "66666666666666666666");

        RequestSpecification req = RestAssured.given()
                .log().all()
                .header("content-type", "application/json")
                .body(data);

        if ("Arizona".equals(state))
            req.header("x-api-key", apikeyArizona);
        else
            req.header("x-api-key", apikeyBermuda);
       res = req.post(url);

        if (res.statusCode() != 200)
            Assert.fail("Status code should  be 200");

        String verificationCode = res.jsonPath()
                .getString("code");
       System.out.println(verificationCode);

        return verificationCode;
    }


    // calling the pojoResponse class
    public void responsePojo(){
      JsonPath jp = new JsonPath(res.asString());
      PojoResponse response =new PojoResponse(jp.getString("padding"),jp.getString("uuid"),jp.getString("code"),jp.getString("expiresAt"),jp.getInt("expiresAtTimestamp"),
              jp.getString("longExpiresAt"),jp.getInt("longExpiresAtTimestamp"));

     String code = response.getCode();
     System.out.println(code);

  }
}





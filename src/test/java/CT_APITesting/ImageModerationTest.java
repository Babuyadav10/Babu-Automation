package CT_APITesting;

import io.restassured.RestAssured;
import io.restassured.authentication.PreemptiveBasicAuthScheme;
import io.restassured.config.LogConfig;
import org.json.JSONObject;
import org.testng.annotations.Test;

import java.io.FileOutputStream;
import java.io.PrintStream;

public class ImageModerationTest extends BaseTest{
    String appId = GlobalClassTest.prop.getProperty("appId");

    @Test(description = "Verify image Moderation functionality")
    public void imageModeration()
    {
        MessageIDTest b=new MessageIDTest();
             b.messageID();
       String a =MessageIDTest.msgID1;
       JSONObject data1 = new JSONObject(a);

        JSONObject userJson = new JSONObject();
        userJson.put("hook","before_message");
        userJson.put("appId",appId);
        userJson.put("data",data1.get("data"));

        MessageIDTest.getCommon().
                auth().preemptive().basic("user_jLNZeF33dedqvGez","nXbUmk749aRCTgUq").
                body(userJson.toString(1)).
                when().
                post("https://image-moderation-"+GlobalClassTest.prop.getProperty("region")+"."+GlobalClassTest.prop.getProperty("domainName")+"/v1/analyze").
                then().log().body().
                statusCode(200);
    }

    @Test(description = "Verify image Moderation invalid auth functionality")
    public void imageModerationInvalidAuth()
    {
        MessageIDTest b=new MessageIDTest();
        b.messageID();
        String a =MessageIDTest.msgID1;
        JSONObject data1 = new JSONObject(a);

        JSONObject userJson = new JSONObject();
        userJson.put("hook","before_message");
        userJson.put("appId",appId);
        userJson.put("data",data1.get("data"));

        MessageIDTest.getCommon().
                auth().preemptive().basic("user_jLNZeF33dedqvGez1","nXbUmk749aRCTgUq").
                body(userJson.toString(1)).
                when().
                post("https://image-moderation-"+GlobalClassTest.prop.getProperty("region")+"."+GlobalClassTest.prop.getProperty("domainName")+"/v1/analyze").
                then().log().body().
                statusCode(401);
    }

//    @Test(description = "Verify image Moderation empty data functionality.")
//    public void imageModerationEmptyData()
//    {
//        JSONObject userJson = new JSONObject();
//        userJson.put("hook","before_message");
//        userJson.put("appId",appId);
//        userJson.put("data","");
//
//        MessageIDTest.getCommon().
//                auth().preemptive().basic("user_jLNZeF33dedqvGez","nXbUmk749aRCTgUq").
//                body(userJson.toString(1)).
//                when().
//                post("https://image-moderation-"+GlobalClassTest.prop.getProperty("region")+"."+GlobalClassTest.prop.getProperty("domainName")+"/v1/analyze").
//                then().log().body().
//                statusCode(200);
//    }
}

package CT_APITesting;

import io.restassured.specification.RequestSpecification;
import org.json.JSONArray;
import org.json.JSONObject;
import org.testng.annotations.Test;

import javax.swing.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.given;

public class MessageIDTest extends BaseTest {
    String apiKey = GlobalClassTest.prop.getProperty("apiKey");
    public static String msgID;
    public static String msgID1;
    @Test(description = "Verify MessageID for Extension")
    public void messageID()
    {
//        JSONObject userJson = new JSONObject();
//        userJson.put("receiver", "supergroup");
//        userJson.put("receiverType", "group");
//        userJson.put("category", "message");
//        userJson.put("type", "text");
//        userJson.put("data", "{\"text\": \"sending the message for getting msgID\"}");


       JSONArray userJsonArray = new JSONArray();
        JSONObject userJson = new JSONObject();
        userJson.put("name", "hi.png");
        userJson.put("extension", "png");
        userJson.put("mimeType", "image/png");
        userJson.put("url", "https://data-eu.cometchat.io/assets/images/avatars/ironman.png");

          userJsonArray.put(userJson);
         JSONObject userJson1 = new JSONObject();
        userJson1.put("attachments",userJsonArray);
        userJson1.put("text", "https://www.netflix.com");

        JSONObject userJson2 = new JSONObject();
        userJson2.put("data", userJson1);
        userJson2.put("receiverType", "user");
        userJson2.put("category", "message");
        userJson2.put("type", "image");
        userJson2.put("receiver", "superhero1");

        String responseBody= given()
               .header("onBehalfOf", "superhero1")
               .header("apiKey",apiKey).header("Content-Type", "application/json").header("Accept","application/json").
                body(userJson2.toString(1)).
                when().
                post("/messages").
                then().
                assertThat().statusCode(200).extract().body().asString();
        msgID1 = responseBody;
        PathFinder(responseBody);
        msgID = js.getString("data.id");
        System.out.println("msgID :" +msgID);
    }
    static   String appId = GlobalClassTest.prop.getProperty("appId");
   static String authToken = GlobalClassTest.prop.getProperty("authToken");

    public static RequestSpecification getCommon()
    {
        RequestSpecification httpRequestObject;
        httpRequestObject = given();
        httpRequestObject.header("Content-Type", "application/json");
        httpRequestObject.header("appId", appId);
        httpRequestObject.header("authToken", authToken);
        httpRequestObject.header("chatAPIVersion", "v3.0");
        httpRequestObject.header("Accept", "application/json");
        return httpRequestObject;
    }

}

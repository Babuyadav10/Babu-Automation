package CT_APITesting;

import org.json.JSONObject;
import org.testng.annotations.Test;
public class LinkPreviewTest extends BaseTest {
    String appId = GlobalClassTest.prop.getProperty("appId");

    @Test(description = "Verify link preview functionality")
    public void linkPreview()
    {
        MessageIDTest b=new MessageIDTest();
        b.messageID();
        String a =MessageIDTest.msgID1;
        JSONObject data1 = new JSONObject(a);

        JSONObject userJson = new JSONObject();
        userJson.put("hook","after_message");
        userJson.put("appId",appId);
        userJson.put("data",data1.get("data"));

        MessageIDTest.getCommon().
                auth().preemptive().basic("user_yT3sGmZ8nuCyESmL","bBeVG4zC4a4G9Gut").
                body(userJson.toString(1)).
                when().
                post("https://link-preview-"+GlobalClassTest.prop.getProperty("region")+"."+GlobalClassTest.prop.getProperty("domainName")+"/v1/generate-preview").
                then().log().body().
                statusCode(200);
    }

    @Test(description = "Verify link preview empty data functionality")
    public void linkPreviewEmptyData()
    {
        JSONObject userJson = new JSONObject();
        userJson.put("hook","after_message");
        userJson.put("appId",appId);
        userJson.put("data","www.google.com1");

        MessageIDTest.getCommon().
                auth().preemptive().basic("user_yT3sGmZ8nuCyESmL","bBeVG4zC4a4G9Gut").
                body(userJson.toString(1)).
                when().
                post("https://link-preview-"+GlobalClassTest.prop.getProperty("region")+"."+GlobalClassTest.prop.getProperty("domainName")+"/v1/generate-preview").
                then().log().body().
                statusCode(200);
    }

    @Test(description = "Verify link preview invalid auth functionality")
    public void linkPreviewInvalidAuth()
    {
        JSONObject userJson = new JSONObject();
        userJson.put("hook","after_message");
        userJson.put("appId",appId);
        userJson.put("data","www.google.com1");

        MessageIDTest.getCommon().
                auth().preemptive().basic("user_yT3sGmZ8nuCyESmLbabu","bBeVG4zC4a4G9Gut").
                body(userJson.toString(1)).
                when().
                post("https://link-preview-"+GlobalClassTest.prop.getProperty("region")+"."+GlobalClassTest.prop.getProperty("domainName")+"/v1/generate-preview").
                then().log().body().
                statusCode(401);
    }

}

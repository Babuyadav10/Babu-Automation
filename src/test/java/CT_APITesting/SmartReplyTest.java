package CT_APITesting;

import org.json.JSONObject;
import org.testng.annotations.Test;

public class SmartReplyTest extends BaseTest{


    @Test(description = "Verify smartReply functionality")
    public void smartReply()
    {
        MessageIDTest b=new MessageIDTest();
        b.messageID();
        String a =MessageIDTest.msgID1;
        JSONObject data1 = new JSONObject(a);

        JSONObject userJson = new JSONObject();
        userJson.put("data",data1.get("data"));

        MessageIDTest.getCommon().
                auth().preemptive().basic("user_bBeVG4zC4a4G9Gut","yF4n7V74rHXQUYbQ").
                body(userJson.toString(1)).
                when().log().all().
                post("https://smart-reply-"+GlobalClassTest.prop.getProperty("region")+"."+GlobalClassTest.prop.getProperty("domainName")+"/v1/fetch-reply").
                then().log().body().
                statusCode(200);
    }



}


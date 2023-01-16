package CT_APITesting;

import io.restassured.filter.log.LogDetail;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.hamcrest.Matchers;
import org.json.JSONObject;
import org.testng.annotations.Test;

import static com.google.common.base.Predicates.equalTo;

public class DisappearingMessageTest extends BaseTest{

       long time= 1766768780000L;
    @Test(description = "Verify DisappearingMessage functionality")
    public void disappearingMessage()
    {
        MessageIDTest b=new MessageIDTest();
        b.messageID();

        JSONObject userJson = new JSONObject();
        userJson.put("msgId",MessageIDTest.msgID);
        userJson.put("timeInMS",time);

         MessageIDTest.getCommon().
                body(userJson.toString(1)).
                when().
                delete("https://disappearing-messages-"+GlobalClassTest.prop.getProperty("region")+"."+GlobalClassTest.prop.getProperty("domainName")+"/v1/disappear").
                then().log().body().
                statusCode(200);
    }

    @Test(description = "Verify DisappearingMessage invalid msgID functionality")
    public void disappearingMessageInvalidMsgId()
    {
        JSONObject userJson = new JSONObject();
        userJson.put("msgId",-2);
        userJson.put("timeInMS",time);

        MessageIDTest.getCommon().
                body(userJson.toString(1)).
                when().
                delete("https://disappearing-messages-"+GlobalClassTest.prop.getProperty("region")+"."+GlobalClassTest.prop.getProperty("domainName")+"/v1/disappear").
                then().log().body().
                statusCode(400);
    }

    @Test(description = "Verify DisappearingMessage invalid timeInMS functionality..")
    public void disappearingMessageInvalidTimeInMS()
    {
        MessageIDTest b=new MessageIDTest();
        b.messageID();

        JSONObject userJson = new JSONObject();
        userJson.put("msgId",MessageIDTest.msgID);
        userJson.put("timeInMS",12345);

          MessageIDTest.getCommon().
                body(userJson.toString(1)).
                when().
                delete("https://disappearing-messages-"+GlobalClassTest.prop.getProperty("region")+"."+GlobalClassTest.prop.getProperty("domainName")+"/v1/disappear").
                then().
                  log().ifValidationFails(LogDetail.BODY).statusCode(400);
    }

}

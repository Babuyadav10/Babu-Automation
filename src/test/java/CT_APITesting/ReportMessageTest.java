package CT_APITesting;

import io.restassured.http.Method;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;
import static io.restassured.RestAssured.given;

public class ReportMessageTest extends BaseTest {
    public String MsgId;
    public String createdReasonId;
    @Test(description = "Verify ReportMessage functionality")

    public void reportMessageValidMsgId()
    {
        MessageIDTest b=new MessageIDTest();
                    b.messageID();
        MsgId=MessageIDTest.msgID;

        JSONObject userJson = new JSONObject();
        userJson.put("msgId", MsgId);
        userJson.put("reason", "Misbehaving");

        MessageIDTest.getCommon().
                body(userJson.toString()).
                when().
                post("https://report-message-"+GlobalClassTest.prop.getProperty("region")+"."+GlobalClassTest.prop.getProperty("domainName")+"/v1/report").
                then().log().body().
                assertThat().statusCode(200);
    }

    @Test(description = "Verify report Message for Invalid MsgId..")

    public void reportMessageInvalidMsgId()
    {
        JSONObject userJson = new JSONObject();
        userJson.put("msgId",-2);
        userJson.put("reason", "Misbehaving");

        MessageIDTest.getCommon().
                body(userJson.toString()).
                when().
                post("https://report-message-"+GlobalClassTest.prop.getProperty("region")+"."+GlobalClassTest.prop.getProperty("domainName")+"/v1/report").
                then().log().body().
                assertThat().statusCode(417);
    }
    @Test(description = "Verify GetReport For Message.", dependsOnMethods = {"reportMessageValidMsgId"})

    public void GetReportForMessage()
    {
        String responseBody= MessageIDTest.getCommon().
                when().
                get("https://report-message-"+GlobalClassTest.prop.getProperty("region")+"."+GlobalClassTest.prop.getProperty("domainName")+"/v1/reports").
                then().log().body().
                assertThat().statusCode(200).extract().body().asString();

        PathFinder(responseBody);
        Assert.assertEquals(js.getString("data[-1].reportedMessage.msgId"), MsgId);
         createdReasonId= js.getString("data[-1].reasons[0].id");
        System.out.println("createdReasonId  " + createdReasonId);
    }

    @Test(description = "Verify TakeReportAction functionality", dependsOnMethods = {"GetReportForMessage"})

    public void TakeReportAction()
    {
        System.out.println("createdReasonId : " + createdReasonId);

        JSONObject userJson = new JSONObject();
        userJson.put("action", "delete");
        userJson.put("reasonId", new String[]{createdReasonId});
        userJson.put("msgId", MsgId);

        MessageIDTest.getCommon().
                body(userJson.toString()).
                when().
                post("https://report-message-"+GlobalClassTest.prop.getProperty("region")+"."+GlobalClassTest.prop.getProperty("domainName")+"/v1/take-action").
                then().log().body().
                assertThat().statusCode(200);
    }

}

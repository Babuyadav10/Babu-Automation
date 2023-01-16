package CT_APITesting;

import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;

public class PinMessageTest extends BaseTest {
    public String pinMsgId;
    public String createdPinMsgId;
    @Test(description = "Verify pin message functionality")
    public void pinMessage()
    {
        MessageIDTest b=new MessageIDTest();
                      b.messageID();
               pinMsgId=MessageIDTest.msgID;
               JSONObject userJson = new JSONObject();
                userJson.put("msgId",pinMsgId);

        String responseBody= MessageIDTest.getCommon().
                body(userJson.toString(1)).
                when().
                post("https://pin-message-"+GlobalClassTest.prop.getProperty("region")+"."+GlobalClassTest.prop.getProperty("domainName")+"/v1/pin").
                then().
                assertThat().statusCode(200).extract().body().asString();
        System.out.println(responseBody);
    }
    @Test(description = "Verify fetch pin message functionality", dependsOnMethods = {"pinMessage"})
    public void pinMessageFetch()
    {
        String responseBody= MessageIDTest.getCommon().
                queryParam("receiver","supergroup").
                queryParam("receiverType","group").
                when().
                get("https://pin-message-"+GlobalClassTest.prop.getProperty("region")+"."+GlobalClassTest.prop.getProperty("domainName")+"/v1/fetch").
                then().
                assertThat().statusCode(200).extract().body().asString();
        System.out.println(responseBody);
        PathFinder(responseBody);
       // createdPinMsgId= js.getString("data.pinnedMessages[-1].id");
    }

    @Test(description = "Verify unPin message functionality",dependsOnMethods = {"pinMessageFetch"})
    public void unPinMessage()
    {
        JSONObject userJson = new JSONObject();
        userJson.put("msgId",pinMsgId);
        userJson.put("receiverType","group");
        userJson.put("receiver","supergroup");

        String responseBody= MessageIDTest.getCommon().
                body(userJson.toString(1)).
                when().
                delete("https://pin-message-"+GlobalClassTest.prop.getProperty("region")+"."+GlobalClassTest.prop.getProperty("domainName")+"/v1/unpin").
                then().
                assertThat().statusCode(200).extract().body().asString();
        System.out.println(responseBody);
    }

}

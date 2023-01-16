package CT_APITesting;

import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;

public class SaveMessageTest extends BaseTest {
    public String saveMsgId;
    public String createdSaveMsgId;
    @Test(description = "Verify save message functionality")
    public void saveMessage()
    {
        MessageIDTest b=new MessageIDTest();
                  b.messageID();
            saveMsgId=MessageIDTest.msgID;
        JSONObject userJson = new JSONObject();
        userJson.put("msgId",saveMsgId);

        String responseBody= MessageIDTest.getCommon().
                body(userJson.toString(1)).
                when().
                post("https://save-message-"+GlobalClassTest.prop.getProperty("region")+"."+GlobalClassTest.prop.getProperty("domainName")+"/v1/save").
                then().
                assertThat().statusCode(200).extract().body().asString();
        System.out.println(responseBody);
    }

    @Test(description = "Verify save message Invalid msgId functionality")
    public void saveMessageInvalid()
    {
        JSONObject userJson = new JSONObject();
        userJson.put("msgId","100000000000000000000");

        String responseBody= MessageIDTest.getCommon().
                body(userJson.toString(1)).
                when().
                post("https://save-message-"+GlobalClassTest.prop.getProperty("region")+"."+GlobalClassTest.prop.getProperty("domainName")+"/v1/save").
                then().
                assertThat().statusCode(400).extract().body().asString();
        System.out.println(responseBody);
         PathFinder(responseBody);
        Assert.assertEquals(js.getString("error.code"), "ERR_CHAT_API_FAILURE");
    }
    @Test(description = "Verify save message fetch functionality.", dependsOnMethods = {"saveMessage"})
    public void saveMessageFetch()
    {
        String responseBody= MessageIDTest.getCommon().
                when().
                get("https://save-message-"+GlobalClassTest.prop.getProperty("region")+"."+GlobalClassTest.prop.getProperty("domainName")+"/v1/fetch").
                then().
                assertThat().statusCode(200).extract().body().asString();
        System.out.println(responseBody);
        PathFinder(responseBody);
        createdSaveMsgId= js.getString("data.savedMessages[-1].id");
    }
    @Test(description = "Verify unSave message functionality..", dependsOnMethods = {"saveMessageFetch"})
    public void unSaveMessage()
    {
        JSONObject userJson = new JSONObject();
        userJson.put("msgId",createdSaveMsgId);

        String responseBody= MessageIDTest.getCommon().
                body(userJson.toString(1)).
                when().
                delete("https://save-message-"+GlobalClassTest.prop.getProperty("region")+"."+GlobalClassTest.prop.getProperty("domainName")+"/v1/unsave").
                then().
                assertThat().statusCode(200).extract().body().asString();
        System.out.println(responseBody);
    }
}

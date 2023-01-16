package CT_APITesting;

import org.json.JSONObject;
import org.testng.annotations.Test;
public class MessageTranslateTest extends BaseTest{

    @Test(description = "Verify Message Translate functionality")
    public void messageTranslate()
    {
        JSONObject userJson = new JSONObject();
        userJson.put("text","If you want good software product then You must need great tester");
        userJson.put("languages",new String[]{"hi"});

        MessageIDTest.getCommon().
                body(userJson.toString(1)).
                when().
                post("https://message-translation-"+GlobalClassTest.prop.getProperty("region")+"."+GlobalClassTest.prop.getProperty("domainName")+"/v2/translate").
                then().log().body().
                statusCode(200);
    }

    @Test(description = "Verify Message Translate into many language functionality")
    public void messageTranslateUrdu()
    {
        JSONObject userJson = new JSONObject();
        userJson.put("text","If you want good software product then You must need great tester");
        userJson.put("languages",new String[]{"hi","ur","af","bn","hr"});

        MessageIDTest.getCommon().
                body(userJson.toString(1)).
                when().
                post("https://message-translation-"+GlobalClassTest.prop.getProperty("region")+"."+GlobalClassTest.prop.getProperty("domainName")+"/v2/translate").
                then().log().body().
                assertThat().statusCode(200);
    }

    @Test(description = "Verify Message Translate invalid language code functionality")
    public void messageTranslateInvalidCode()
    {
        JSONObject userJson = new JSONObject();
        userJson.put("text","If you want good software product then You must need great tester");
        userJson.put("languages",new String[]{"bu"});

         MessageIDTest.getCommon().
                body(userJson.toString(1)).
                when().
                post("https://message-translation-"+GlobalClassTest.prop.getProperty("region")+"."+GlobalClassTest.prop.getProperty("domainName")+"/v2/translate").
                then().log().body().
                assertThat().statusCode(200);
    }

    @Test(description = "Verify Message Translate without array format functionality")
    public void messageTranslateWithoutArray()
    {
        JSONObject userJson = new JSONObject();
        userJson.put("text","If you want good software product then You must need great tester");
        userJson.put("languages","hi");

        MessageIDTest.getCommon().
                body(userJson.toString(1)).
                when().
                post("https://message-translation-"+GlobalClassTest.prop.getProperty("region")+"."+GlobalClassTest.prop.getProperty("domainName")+"/v2/translate").
                then().log().body().
                assertThat().statusCode(400);
    }

}

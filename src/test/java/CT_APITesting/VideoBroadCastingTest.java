package CT_APITesting;

import org.json.JSONObject;
import org.testng.annotations.Test;

public class VideoBroadCastingTest extends BaseTest{

    @Test(description = "Verify videoBroadcasting  for user functionality")
    public void videoBroadcastingForUser()
    {
        JSONObject userJson = new JSONObject();
        userJson.put("receiver","superhero2");
        userJson.put("receiverType","user");

        MessageIDTest.getCommon().
                body(userJson.toString(1)).
                when().
                post("https://broadcast-"+GlobalClassTest.prop.getProperty("region")+"."+GlobalClassTest.prop.getProperty("domainName")+"/v1/broadcast").
                then().log().body();
              //  assertThat().statusCode(200);
    }

    @Test(description = "Verify videoBroadcasting  for group functionality")
    public void videoBroadcastingGroup()
    {
        JSONObject userJson = new JSONObject();
        userJson.put("receiver","supergroup");
        userJson.put("receiverType","group");

       MessageIDTest.getCommon().
                body(userJson.toString(1)).
                when().
                post("https://broadcast-"+GlobalClassTest.prop.getProperty("region")+"."+GlobalClassTest.prop.getProperty("domainName")+"/v1/broadcast").
                then().log().body().
                assertThat().statusCode(200);
    }

    @Test(description = "Verify videoBroadcasting for invalid user functionality")
    public void videoBroadcastingInvalidUser()
    {
        JSONObject userJson = new JSONObject();
        userJson.put("receiver","superhero200");
        userJson.put("receiverType","user");

         MessageIDTest.getCommon().
                body(userJson.toString(1)).
                when().
                post("https://broadcast-"+GlobalClassTest.prop.getProperty("region")+"."+GlobalClassTest.prop.getProperty("domainName")+"/v1/broadcast").
                then().log().body().
                assertThat().statusCode(417);
    }

    @Test(description = "Verify videoBroadcasting for invalid group functionality")
    public void videoBroadcastingInvalidGroup()
    {
        JSONObject userJson = new JSONObject();
        userJson.put("receiver","supergroup100");
        userJson.put("receiverType","group");

        MessageIDTest.getCommon().
                body(userJson.toString(1)).
                when().
                post("https://broadcast-"+GlobalClassTest.prop.getProperty("region")+"."+GlobalClassTest.prop.getProperty("domainName")+"/v1/broadcast").
                then().log().body().
                assertThat().statusCode(417);
    }

}

package CT_APITesting;

import org.json.JSONObject;
import org.testng.annotations.Test;

public class WhiteBoardTest extends BaseTest{

    @Test(description = "Verify WhiteBoard for user functionality")
    public void whiteBoardUser()
    {
        JSONObject userJson = new JSONObject();
        userJson.put("receiver","superhero1");
        userJson.put("receiverType","user");

        MessageIDTest.getCommon().
                body(userJson.toString(1)).
                when().
                post("https://whiteboard-"+GlobalClassTest.prop.getProperty("region")+"."+GlobalClassTest.prop.getProperty("domainName")+"/v1/create").
                then().log().body().
                assertThat().statusCode(200);
    }


    @Test(description = "Verify WhiteBoard for group functionality")
    public void whiteBoardGroup()
    {
        JSONObject userJson = new JSONObject();
        userJson.put("receiver","supergroup");
        userJson.put("receiverType","group");

        MessageIDTest.getCommon().
                body(userJson.toString(1)).
                when().
                post("https://whiteboard-"+GlobalClassTest.prop.getProperty("region")+"."+GlobalClassTest.prop.getProperty("domainName")+"/v1/create").
                then().log().body().
                assertThat().statusCode(200);
    }

    @Test(description = "Verify WhiteBoard for invalid group functionality")
    public void whiteBoardGroupInvalid()
    {
        JSONObject userJson = new JSONObject();
        userJson.put("receiver","supergroup1000");
        userJson.put("receiverType","group");

        MessageIDTest.getCommon().
                body(userJson.toString(1)).
                when().
                post("https://whiteboard-"+GlobalClassTest.prop.getProperty("region")+"."+GlobalClassTest.prop.getProperty("domainName")+"/v1/create").
                then().log().body().
                assertThat().statusCode(417).extract().body().asString();
    }

    @Test(description = "Verify WhiteBoard  for invalid user functionality")
    public void whiteBoardUserInvalid()
    {
        JSONObject userJson = new JSONObject();
        userJson.put("receiver","superhero10000");
        userJson.put("receiverType","user");

        MessageIDTest.getCommon().
                body(userJson.toString(1)).
                when().
                post("https://whiteboard-"+GlobalClassTest.prop.getProperty("region")+"."+GlobalClassTest.prop.getProperty("domainName")+"/v1/create").
                then().log().body().
                assertThat().statusCode(417);
    }


}

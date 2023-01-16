package CT_APITesting;

import io.restassured.RestAssured;
import io.restassured.authentication.PreemptiveBasicAuthScheme;
import org.json.JSONObject;
import org.testng.annotations.Test;

public class RichMediaTest extends BaseTest {

    String appId = GlobalClassTest.prop.getProperty("appId");
    @Test(description = "Verify rich media preview functionality")
    public void richMedia()
    {

        MessageIDTest b=new MessageIDTest();
        b.messageID();
        String a =MessageIDTest.msgID1;

        JSONObject data1 = new JSONObject(a);

        JSONObject userJson = new JSONObject();
        userJson.put("appId",appId);
        userJson.put("data",data1.get("data"));

        String responseBody= MessageIDTest.getCommon().
                auth().preemptive().basic("user_m4jH5BmPcgy89e2i","ZgyGafkksN9YQ9kX").
                body(userJson.toString(1)).
                when().log().body().
                post("https://rich-media-"+GlobalClassTest.prop.getProperty("region")+"."+GlobalClassTest.prop.getProperty("domainName")+"/v1/preview").
                then().
                assertThat().statusCode(200).extract().body().asString();
        System.out.println(responseBody);
    }

}

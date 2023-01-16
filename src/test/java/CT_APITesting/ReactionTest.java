package CT_APITesting;
import io.restassured.http.Method;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;



import static io.restassured.RestAssured.given;

public class ReactionTest extends BaseTest {

@Test(description = "Verify Reaction emoji functionality")
    public void ReactionValidEmoji()
{
           MessageIDTest b=new MessageIDTest();
                       b.messageID();

        RequestSpecification httpRequestObject = MessageIDTest.getCommon();

        JSONObject userJson = new JSONObject();
        userJson.put("emoji", ":rocket:");
        userJson.put("msgId",MessageIDTest.msgID);

        httpRequestObject.body(userJson.toString());
        Response response = httpRequestObject.request(Method.POST, "https://reactions-"+GlobalClassTest.prop.getProperty("region")+"."+GlobalClassTest.prop.getProperty("domainName")+"/v1/react");

        String responseBody = response.getBody().asString();
        System.out.println(responseBody);
        int statusCode = response.getStatusCode();
        System.out.println(statusCode);
        Assert.assertEquals(statusCode, 200);

    }

    @Test(description = "Verify reaction invalid emoji functionality")

    public void ReactionInvalidEmoji()
    {

        RequestSpecification httpRequestObject = MessageIDTest.getCommon();

        JSONObject userJson = new JSONObject();
        userJson.put("emoji", ":rocketbabu:");
        userJson.put("msgId", MessageIDTest.msgID);

        httpRequestObject.body(userJson.toString());
        Response response = httpRequestObject.request(Method.POST, "https://reactions-"+GlobalClassTest.prop.getProperty("region")+"."+GlobalClassTest.prop.getProperty("domainName")+"/v1/react");

        String responseBody = response.getBody().asString();
        System.out.println(responseBody);
        int statusCode = response.getStatusCode();
        System.out.println(statusCode);
        Assert.assertEquals(statusCode, 200);

    }
    @Test(description = "Verify reaction empty emoji functionality.")
    public void ReactionEmptyEmoji()
    {
        RequestSpecification httpRequestObject =MessageIDTest.getCommon();

        JSONObject userJson = new JSONObject();
        userJson.put("emoji", " ");
        userJson.put("msgId", MessageIDTest.msgID);

        httpRequestObject.body(userJson.toString());
        Response response = httpRequestObject.request(Method.POST, "https://reactions-"+GlobalClassTest.prop.getProperty("region")+"."+GlobalClassTest.prop.getProperty("domainName")+"/v1/react");

        String responseBody = response.getBody().asString();
        System.out.println(responseBody);
        int statusCode = response.getStatusCode();
        System.out.println(statusCode);
        Assert.assertEquals(statusCode, 200);
    }
}

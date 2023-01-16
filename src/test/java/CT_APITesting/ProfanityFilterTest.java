package CT_APITesting;
import io.restassured.RestAssured;
import io.restassured.authentication.PreemptiveBasicAuthScheme;
import io.restassured.http.Method;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;
import static io.restassured.RestAssured.given;

public class ProfanityFilterTest extends BaseTest {

    @Test(description = "Verify Profanity functionality")
    public void profaneWords()
    {
        PreemptiveBasicAuthScheme authScheme=new PreemptiveBasicAuthScheme();
        authScheme.setUserName("user_4p5DsJstpbxeqjX9");
        authScheme.setPassword("tFyVQt3HpDrQxscE");
        RestAssured.authentication=authScheme;

        RequestSpecification httpRequestObject =MessageIDTest.getCommon();

        JSONObject userJson = new JSONObject();
        userJson.put("text","aids");
        JSONObject userJson1 = new JSONObject();
        userJson1.put("data",userJson);
        JSONObject userJson2 = new JSONObject();
        userJson2.put("data",userJson1);
        userJson2.put("appId",GlobalClassTest.prop.getProperty("appId"));

        httpRequestObject.body(userJson2.toString());
        Response response = httpRequestObject.request(Method.POST, "https://profanity-filter-"+GlobalClassTest.prop.getProperty("region")+"."+GlobalClassTest.prop.getProperty("domainName")+"/v1/filter");

        String responseBody = response.getBody().asString();
        System.out.println(responseBody);
        int statusCode = response.getStatusCode();
        System.out.println(statusCode);
        Assert.assertEquals(statusCode, 200);
    }

    @Test(description = "Verify Profanity empty text functionality")
    public void profanityEmptyText()
    {
        PreemptiveBasicAuthScheme authScheme=new PreemptiveBasicAuthScheme();
        authScheme.setUserName("user_4p5DsJstpbxeqjX9");
        authScheme.setPassword("tFyVQt3HpDrQxscE");
        RestAssured.authentication=authScheme;

        RequestSpecification httpRequestObject =MessageIDTest.getCommon();

        JSONObject userJson = new JSONObject();
        userJson.put("text","");
        JSONObject userJson1 = new JSONObject();
        userJson1.put("data",userJson);
        JSONObject userJson2 = new JSONObject();
        userJson2.put("data",userJson1);
        userJson2.put("appId",GlobalClassTest.prop.getProperty("appId"));

        httpRequestObject.body(userJson2.toString());
        Response response = httpRequestObject.request(Method.POST, "https://profanity-filter-"+GlobalClassTest.prop.getProperty("region")+"."+GlobalClassTest.prop.getProperty("domainName")+"/v1/filter");

        String responseBody = response.getBody().asString();
        System.out.println(responseBody);
        int statusCode = response.getStatusCode();
        System.out.println(statusCode);
        Assert.assertEquals(statusCode, 200);
    }
}

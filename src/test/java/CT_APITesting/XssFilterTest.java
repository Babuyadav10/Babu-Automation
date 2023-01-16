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

public class XssFilterTest extends BaseTest {

    @Test(description = "Verify Xss filter functionality")

    public void hasXSSword() {

        PreemptiveBasicAuthScheme authScheme=new PreemptiveBasicAuthScheme();
        authScheme.setUserName("user_4Z3cjG7k2eIv46kc");
        authScheme.setPassword("3LtDr182aod1096V");
        RestAssured.authentication=authScheme;

        RequestSpecification httpRequestObject =MessageIDTest.getCommon();

        JSONObject userJson = new JSONObject();
        userJson.put("text","<iframe>Hi! babu testing extension</iframe><script>alert('Hacked!')</script>");

        JSONObject userJson1 = new JSONObject();
        userJson1.put("data",userJson);

        JSONObject userJson2 = new JSONObject();
        userJson2.put("data",userJson1);
        userJson2.put("appId",GlobalClassTest.prop.getProperty("appId"));

        httpRequestObject.body(userJson2.toString());
        Response response = httpRequestObject.request(Method.POST, "https://xss-filter-"+GlobalClassTest.prop.getProperty("region")+"."+GlobalClassTest.prop.getProperty("domainName")+"/v1/filter");

        String responseBody = response.getBody().asString();
        System.out.println("responseBody : " + responseBody);

        int statusCode = response.getStatusCode();
        System.out.println("statusCode is  " + statusCode);
        Assert.assertEquals(statusCode, 200);

    }

    @Test(description = "Verify Xss filter empty functionality")

    public void xssFilterEmptyText() {

        PreemptiveBasicAuthScheme authScheme=new PreemptiveBasicAuthScheme();
        authScheme.setUserName("user_4Z3cjG7k2eIv46kc");
        authScheme.setPassword("3LtDr182aod1096V");
        RestAssured.authentication=authScheme;

        RequestSpecification httpRequestObject = MessageIDTest.getCommon();

        JSONObject userJson = new JSONObject();
        userJson.put("text","");

        JSONObject userJson1 = new JSONObject();
        userJson1.put("data",userJson);

        JSONObject userJson2 = new JSONObject();
        userJson2.put("data",userJson1);
        userJson2.put("appId",GlobalClassTest.prop.getProperty("appId"));

        httpRequestObject.body(userJson2.toString());
        Response response = httpRequestObject.request(Method.POST, "https://xss-filter-us.cometchat.io/v1/filter");

        String responseBody = response.getBody().asString();
        System.out.println("responseBody : " + responseBody);

        int statusCode = response.getStatusCode();
        System.out.println("statusCode is  " + statusCode);
        Assert.assertEquals(statusCode, 400);

    }

}

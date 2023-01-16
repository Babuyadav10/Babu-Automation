package CT_APITesting;

import io.restassured.RestAssured;
import io.restassured.authentication.PreemptiveBasicAuthScheme;
import io.restassured.http.Method;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;

public class MentionsTest extends BaseTest {

    @Test(description = "Verify mentions for User functionality")
    public void mentionsForUser()
    {
        PreemptiveBasicAuthScheme authScheme=new PreemptiveBasicAuthScheme();
        authScheme.setUserName("user_yvgswMrMc8FZkgNM");
        authScheme.setPassword("V75XF5Tevw8fgv2g");
        RestAssured.authentication=authScheme;

        RequestSpecification httpRequestObject =MessageIDTest.getCommon();

        JSONObject userJson = new JSONObject();
        userJson.put("text","@{Cyclops|superhero5} How are you?");
        JSONObject userJson1 = new JSONObject();
        userJson1.put("data",userJson);
        userJson1.put("id","2");
        userJson1.put("sender","superhero2");
        userJson1.put("receiverType","user");
        userJson1.put("receiver","superhero3");
        userJson1.put("category","message");
        userJson1.put("type","text");

        JSONObject userJson2 = new JSONObject();
        userJson2.put("data",userJson1);
        userJson2.put("appId",GlobalClassTest.prop.getProperty("appId"));
        userJson2.put("trigger","after_message");

        httpRequestObject.body(userJson2.toString());
        Response response = httpRequestObject.request(Method.POST, "https://mentions-"+GlobalClassTest.prop.getProperty("region")+"."+GlobalClassTest.prop.getProperty("domainName")+"/v1/save");

        String responseBody = response.getBody().asString();
        System.out.println(responseBody);

        int statusCode = response.getStatusCode();
        System.out.println(statusCode);
        Assert.assertEquals(statusCode, 200);
    }
    @Test(description = "Verify mentions for Group functionality")
    public void mentionsForGroup()
    {
        PreemptiveBasicAuthScheme authScheme=new PreemptiveBasicAuthScheme();
        authScheme.setUserName("user_yvgswMrMc8FZkgNM");
        authScheme.setPassword("V75XF5Tevw8fgv2g");
        RestAssured.authentication=authScheme;

        RequestSpecification httpRequestObject = MessageIDTest.getCommon();

        JSONObject userJson = new JSONObject();
        userJson.put("text","@{Cyclops|superhero3} How are you?");
        JSONObject userJson1 = new JSONObject();
        userJson1.put("data",userJson);
        userJson1.put("id","2");
        userJson1.put("sender","superhero2");
        userJson1.put("receiverType","group");
        userJson1.put("receiver","supergroup");
        userJson1.put("category","message");
        userJson1.put("type","text");

        JSONObject userJson2 = new JSONObject();
        userJson2.put("data",userJson1);
        userJson2.put("appId",GlobalClassTest.prop.getProperty("appId"));
        userJson2.put("trigger","after_message");

        httpRequestObject.body(userJson2.toString());
        Response response = httpRequestObject.request(Method.POST, "https://mentions-"+GlobalClassTest.prop.getProperty("region")+"."+GlobalClassTest.prop.getProperty("domainName")+"/v1/save");

        String responseBody = response.getBody().asString();
        System.out.println(responseBody);
        int statusCode = response.getStatusCode();
        System.out.println(statusCode);
        Assert.assertEquals(statusCode, 200);
    }
    @Test(description = " Verify Mentions Fetch Valid AuthToken functionality.", dependsOnMethods = {"mentionsForUser"})
    public void MentionsFetchValidAuthForUser()
    {
        RequestSpecification httpRequestObject = MessageIDTest.getCommon();
        Response response = httpRequestObject.request(Method.GET, "https://mentions-"+GlobalClassTest.prop.getProperty("region")+"."+GlobalClassTest.prop.getProperty("domainName")+"/v1/fetch");

        String responseBody = response.getBody().asString();
        System.out.println(responseBody);
        int statusCode = response.getStatusCode();
        System.out.println(statusCode);
        Assert.assertEquals(statusCode, 200);
    }
    @Test(description = "Verify MentionsFetch Invalid AuthToken..")
    public void MentionsFetchInvalidAuthToken() {

        RequestSpecification httpRequestObject =MessageIDTest.getCommon();
        httpRequestObject.header("authToken", "superhero2_16505291305ed1ccb531a41829e4b96759ed0204");

        Response response = httpRequestObject.request(Method.GET, "https://mentions-"+GlobalClassTest.prop.getProperty("region")+"."+GlobalClassTest.prop.getProperty("domainName")+"/v1/fetch");

        String responseBody = response.getBody().asString();
        System.out.println(responseBody);
        int statusCode = response.getStatusCode();
        System.out.println(statusCode);
        Assert.assertEquals(statusCode, 401);
    }

    @Test(description = "Verify Mentions Fetch Valid AuthToken for group.", dependsOnMethods = {"mentionsForGroup"})
    public void MentionsFetchValidAuthTokenForGroup() {

        RequestSpecification httpRequestObject =MessageIDTest.getCommon();
        httpRequestObject.header("authToken", GlobalClassTest.prop.getProperty("authToken"));

        Response response = httpRequestObject.request(Method.GET, "https://mentions-"+GlobalClassTest.prop.getProperty("region")+"."+GlobalClassTest.prop.getProperty("domainName")+"/v1/fetch");

        String responseBody = response.getBody().asString();
        System.out.println(responseBody);
        int statusCode = response.getStatusCode();
        System.out.println(statusCode);
        Assert.assertEquals(statusCode, 200);
    }
    @Test(description = "Verify Mentions Fetch Invalid AuthToken for group functionality")
    public void MentionsFetchInvalidAuthTokenForGroup()
    {
        RequestSpecification httpRequestObject = MessageIDTest.getCommon();
        httpRequestObject.header("authToken", "superhero2_16505291305ed1ccb531a41829e4b96759ed0204");

        Response response = httpRequestObject.request(Method.GET, "https://mentions-"+GlobalClassTest.prop.getProperty("region")+"."+GlobalClassTest.prop.getProperty("domainName")+"/v1/fetch");

        String responseBody = response.getBody().asString();
        System.out.println(responseBody);
        int statusCode = response.getStatusCode();
        System.out.println(statusCode);
        Assert.assertEquals(statusCode, 401);
    }

}

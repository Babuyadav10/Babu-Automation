package CT_APITesting;

import io.restassured.RestAssured;
import io.restassured.authentication.PreemptiveBasicAuthScheme;
import io.restassured.http.Method;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;

public class DataMaskingTest extends BaseTest {

    @Test(description = "Verify DataMasking functionality")
    public void dataMaskingSubmittedText()
    {
        PreemptiveBasicAuthScheme authScheme=new PreemptiveBasicAuthScheme();
        authScheme.setUserName("user_7EgIYTHC45gJ0oCL");
        authScheme.setPassword("ZbS1dEVJFvrcv6ky");
        RestAssured.authentication=authScheme;

        RequestSpecification httpRequestObject = MessageIDTest.getCommon();

        JSONObject userJson = new JSONObject();
        userJson.put("text","Please mask the mail babu@cometchat.com hello hello.56@babu.com its great work babu.@cometchat.com ");
        JSONObject userJson1 = new JSONObject();
        userJson1.put("data",userJson);
        JSONObject userJson2 = new JSONObject();
        userJson2.put("data",userJson1);
        userJson2.put("appId",GlobalClassTest.prop.getProperty("appId"));

        httpRequestObject.body(userJson2.toString());
        Response response = httpRequestObject.request(Method.POST, "https://data-masking-"+GlobalClassTest.prop.getProperty("region")+"."+GlobalClassTest.prop.getProperty("domainName")+"/v1/filter");

        String responseBody = response.getBody().asString();
        System.out.println(responseBody);
        int statusCode = response.getStatusCode();
        System.out.println("statusCode is  " + statusCode);
        Assert.assertEquals(statusCode, 200);
    }

    @Test(description = "Verify DataMasking empty text functionality")
    public void dataMaskingEmptyText()
    {
        PreemptiveBasicAuthScheme authScheme=new PreemptiveBasicAuthScheme();
        authScheme.setUserName("user_7EgIYTHC45gJ0oCL");
        authScheme.setPassword("ZbS1dEVJFvrcv6ky");
        RestAssured.authentication=authScheme;

        RequestSpecification httpRequestObject = MessageIDTest.getCommon();

        JSONObject userJson = new JSONObject();
        userJson.put("text"," ");
        JSONObject userJson1 = new JSONObject();
        userJson1.put("data",userJson);
        JSONObject userJson2 = new JSONObject();
        userJson2.put("data",userJson1);
        userJson2.put("appId",GlobalClassTest.prop.getProperty("appId"));

        httpRequestObject.body(userJson2.toString());
        Response response = httpRequestObject.request(Method.POST, "https://data-masking-"+GlobalClassTest.prop.getProperty("region")+"."+GlobalClassTest.prop.getProperty("domainName")+"/v1/filter");

        String responseBody = response.getBody().asString();
        System.out.println(responseBody);
        int statusCode = response.getStatusCode();
        System.out.println("statusCode is  " + statusCode);
        Assert.assertEquals(statusCode, 200);
    }
}

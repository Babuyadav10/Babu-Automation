package CT_APITesting;

import com.sun.org.apache.xpath.internal.objects.XString;
import groovy.lang.GString;
import io.restassured.RestAssured;
import io.restassured.http.Method;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.json.JSONArray;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;


import static io.restassured.RestAssured.given;

public class FriendsTest extends BaseTest {
    public static String uName;
    String friendName = "superhero1";
    String mainApiKey = "5a6d4f539bd2858d6841d90076dbd7c30cb7607b";

    @Test(description = "Verify add the friends functionality")

    public void addFriends() {

        UsersTest ut = new UsersTest();
        ut.ptc = -1;
        ut.createUser();

        System.out.println("user id name = " + uName);

        RequestSpecification httpRequestObject = RestAssured.given();

        JSONObject userJson = new JSONObject();
        userJson.put("accepted", new String[]{friendName});

        httpRequestObject.header("Content-Type", "application/json");
        httpRequestObject.header("apiKey", mainApiKey);
        httpRequestObject.header("Accept", "application/json");

        httpRequestObject.body(userJson.toString());
        Response response = httpRequestObject.request(Method.POST, "/users/" + uName + "/friends");

        String responseBody = response.getBody().asString();
        System.out.println("responseBody : " + responseBody);

        int statusCode = response.getStatusCode();
        System.out.println("statusCode is  " + statusCode);
        Assert.assertEquals(statusCode, 200);



    }

    @Test(description = "Verify list friends",dependsOnMethods = {"addFriends"})
    public void listFriends() {

        RequestSpecification httpRequestObject = RestAssured.given();

        httpRequestObject.header("Content-Type", "application/json");
        httpRequestObject.header("apiKey", mainApiKey);
        httpRequestObject.header("Accept", "application/json");

        Response response = httpRequestObject.request(Method.GET,"/users/"+friendName+"/friends");

        String responseBody = response.getBody().asString();
        System.out.println("responseBody : " + responseBody);

        int statusCode=response.getStatusCode();
        System.out.println("statusCode is  " + statusCode);
        Assert.assertEquals(statusCode,200);


    }
    @Test(description = "Verify list friends",dependsOnMethods = {"listFriends"})
    public void DeleteFriends() {

        RequestSpecification httpRequestObject = RestAssured.given();

        JSONObject userJson = new JSONObject();
        userJson.put("friends", new String[]{friendName});

        httpRequestObject.header("Content-Type", "application/json");
        httpRequestObject.header("apiKey", mainApiKey);
        httpRequestObject.header("Accept", "application/json");

        httpRequestObject.body(userJson.toString());
        Response response = httpRequestObject.request(Method.DELETE, "/users/" + uName + "/friends");

        String responseBody = response.getBody().asString();
        System.out.println("responseBody : " + responseBody);

        int statusCode = response.getStatusCode();
        System.out.println("statusCode is  " + statusCode);
        Assert.assertEquals(statusCode, 200);



    }


}

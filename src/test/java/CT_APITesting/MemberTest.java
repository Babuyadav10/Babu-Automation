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

public class MemberTest extends BaseTest {

    public static String gName;
    public static String UName;

    String ontheBehalfUser="superhero1";

    String userType="participants";

    String scopeType="admin";
    String mainApiKey = "10ca9c4268ffa7ef032de02e8606da7e3bf67b4f";

    @Test(priority = 0, description = "Verify add the members functionality")

    public void addMembers() {

        GroupTest gt = new GroupTest();
        gt.intc = -1;
        gt.createGroups();

        UsersTest ut = new UsersTest();
        ut.ptc = -1;
        ut.createUser();

        System.out.println("group id name = " + gName);
        System.out.println("user id name = " + UName);

       RequestSpecification httpRequestObject = RestAssured.given();

        JSONObject userJson = new JSONObject();
            userJson.put("participants",new String[]{UName});

        httpRequestObject.header("Content-Type", "application/json");
        httpRequestObject.header("apiKey", mainApiKey);
        httpRequestObject.header("Accept", "application/json");
        httpRequestObject.header("onBehalfOf",ontheBehalfUser);

        httpRequestObject.body(userJson.toString());
        Response response = httpRequestObject.request(Method.POST,"/groups/"+gName+"/members");

        String responseBody = response.getBody().asString();
        System.out.println("responseBody : " + responseBody);

    }



    @Test(description = "Verify list Members",dependsOnMethods = {"addMembers"})
    public void listMembers() {

        String responseBody = given().
                header("apiKey", mainApiKey).
                header("Accept", "application/json").
                header("Content-Type", "application/json").
                when().
                get("/groups/"+gName+"/members").
                then().
                assertThat().statusCode(200).extract().body().asString();

        System.out.println(responseBody);

        //PathFinder(responseBody);
      //  Assert.assertEquals(js.getString("data[0].guid"), createdGuid);
    }


    @Test(description = "Verify change scope",dependsOnMethods = {"listMembers"})
    public void ChangeScope() {

        RequestSpecification httpRequestObject = RestAssured.given();

        JSONObject userJson = new JSONObject();
        userJson.put("scope",scopeType);

        httpRequestObject.header("Content-Type", "application/json");
        httpRequestObject.header("apiKey", mainApiKey);
        httpRequestObject.header("Accept", "application/json");
        httpRequestObject.header("onBehalfOf",ontheBehalfUser);

        httpRequestObject.body(userJson.toString());
        Response response = httpRequestObject.request(Method.PUT,"/groups/"+gName+"/members/"+UName);

        String responseBody = response.getBody().asString();
        System.out.println("responseBody : " + responseBody);
    }

    @Test(description = "Verify kick user",dependsOnMethods = {"ChangeScope"})
    public void KickUser() {

        RequestSpecification httpRequestObject = RestAssured.given();

       // JSONObject userJson = new JSONObject();
      //  userJson.put("scope",scopeType);

        httpRequestObject.header("Content-Type", "application/json");
        httpRequestObject.header("apiKey", mainApiKey);
        httpRequestObject.header("Accept", "application/json");
        httpRequestObject.header("onBehalfOf",ontheBehalfUser);

        // httpRequestObject.body(userJson.toString());
        Response response = httpRequestObject.request(Method.DELETE,"/groups/"+gName+"/members/"+UName);

        String responseBody = response.getBody().asString();
        System.out.println("responseBody : " + responseBody);
    }

}








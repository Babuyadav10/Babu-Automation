package CT_APITesting;

import com.sun.org.apache.xpath.internal.objects.XString;
import groovy.lang.GString;
import io.restassured.RestAssured;
import io.restassured.http.Method;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.json.JSONArray;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;


import static io.restassured.RestAssured.given;

public class MemberTest extends BaseTest {

    String apiKey = GlobalClassTest.prop.getProperty("apiKey");

    public RequestSpecification getCommon() {
        RequestSpecification httpRequestObject;
        httpRequestObject = given();
        httpRequestObject.header("apiKey", apiKey);
        httpRequestObject.header("Content-Type", "application/json");
        httpRequestObject.header("Accept", "application/json");
        return httpRequestObject;
    }
    //--------------------------------createUser-----------------------------
/*
    @Test(description = "Verity create users functionality")
    public void createUser() {

        for (int i = 1; i < 500; ++i) {

            String userID = "babu" + i;
            String username = "babu" + i;

            JSONObject userJson = new JSONObject();
            userJson.put("uid", userID);
            userJson.put("name", username);
            userJson.put("avatar", "https://data-us.cometchat.io/227263ed046613af/avatars/cbdbdb676a84.png");

            getCommon().

                    body(userJson.toString(1)).
                    when().
                    post("/users").
                    then().log().body();

        }
    }

 */



        @Test(description = "Verity create groups functionality")
        public void createGroup ()
        {
            for (int i = 1; i < 200; ++i) {

               // String guid = getRandomString("guid");
               // String groupName = getRandomString("groupName");

                String guid = "babuGroup" + i;
                String groupName = "babuGroup" + i;

                JSONObject userJson = new JSONObject();
                userJson.put("guid", guid);
                userJson.put("name", groupName);
                userJson.put("type", "public");
                userJson.put("description", "This group is created by automation scripts");

                JSONObject userJson2 = new JSONObject();
                userJson2.put("participants", new String[]{"superhero3"});
                userJson2.put("moderators", new String[]{"superhero2"});
                userJson2.put("admins", new String[]{"superhero1"});

                userJson.put("members", userJson2);
                userJson.put("tags", new String[]{"tag1", "tag2"});

                getCommon().
                        header("onBehalfOf", "superhero1").
                        body(userJson.toString(1)).
                        when().
                        post("/groups").
                        then().log().body();
            }
        }




    }









    //   @Test( description = "Verify add the members functionality")

    /*
    public void addMembers() {


        GroupTest gt = new GroupTest();
       // gt.intc = -1;
        gt.createGroups();

        UsersTest ut = new UsersTest();
        ut.ptc = -1;
        ut.createUser();

        System.out.println("group id name = " + gName);
        System.out.println("user id name = " + UName);

       RequestSpecification httpRequestObject = given();

        JSONObject userJson = new JSONObject();
            userJson.put("participants",new String[]{UName});

        httpRequestObject.header("Content-Type", "application/json");
        httpRequestObject.header("apiKey", mainApiKey);
        httpRequestObject.header("Accept", "application/json");
        httpRequestObject.header("onBehalfOf",ontheBehalfUser);

        httpRequestObject.body(userJson.toString());
        Response response = httpRequestObject.request(Method.POST,"/groups/"+gName+"/members");

        String responseBody = response.getBody().asString();
        if (bs !=-1)
        System.out.println("responseBody : " + responseBody);

        int statusCode=response.getStatusCode();
        if (bs !=-1)
        System.out.println("statusCode is  " + statusCode);
        Assert.assertEquals(statusCode,200);

        Assert.assertEquals(responseBody.contains("added"),true);

    }



    @Test(description = "Verify list Members",dependsOnMethods = {"addMembers"})
    public void listMembers() {

        RequestSpecification httpRequestObject = RestAssured.given();

        httpRequestObject.header("Content-Type", "application/json");
        httpRequestObject.header("apiKey", mainApiKey);
        httpRequestObject.header("Accept", "application/json");

        Response response = httpRequestObject.request(Method.GET,"/groups/"+gName+"/members");

        String responseBody = response.getBody().asString();
        System.out.println("responseBody : " + responseBody);

        int statusCode=response.getStatusCode();
        System.out.println("statusCode is  " + statusCode);
        Assert.assertEquals(statusCode,200);

        PathFinder(responseBody);
        Assert.assertEquals(js.getString("data[0].uid"),UName);

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

        int statusCode=response.getStatusCode();
        System.out.println("statusCode is  " + statusCode);
        Assert.assertEquals(statusCode,200);
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

        int statusCode=response.getStatusCode();
        System.out.println("statusCode is  " + statusCode);
        Assert.assertEquals(statusCode,200);
    }

     */










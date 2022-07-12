package CT_APITesting;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class AuthTokenTest extends BaseTest {
    String createdAuthToken;
    String createdUserName;
    String mainApiKey= "10ca9c4268ffa7ef032de02e8606da7e3bf67b4f";
    String uid;

    @Test(priority = 0,description = "verify Auth token functionality")
    public void createAuthToken(){
        String responseUsersBody=given().
                header("apiKey",mainApiKey).
                header("Accept","application/json").
                when().
                get("/users").
                then().
                assertThat().statusCode(200).extract().body().asString();

        PathFinder(responseUsersBody);
         uid=js.getString("data[0].uid");
        System.out.println(uid);
        //Assert.assertEquals(js.getString("data[0].uid"),createdUserId);

        String responseBody=given().
                header("apiKey",mainApiKey).header("Content-Type","application/json").header("Accept","application/json").
                body("{\"force\": false}").
                when().
                post("/users/"+uid+"/auth_tokens").
                then().
                assertThat().statusCode(200).extract().body().asString();

        System.out.println(responseBody);
        PathFinder(responseBody);
        createdAuthToken = js.getString("data.authToken");
        System.out.println(createdAuthToken);

    }

    @Test(priority = 1,description = "Verify list of auth tokens")
    public void listAuthtoken()
    {

        String responseBody=given().
                header("apiKey",mainApiKey).
                header("Accept","application/json").
                when().
                get("/users/"+uid+"/auth_tokens").
                then().
                assertThat().statusCode(200).extract().body().asString();

        System.out.println(responseBody);

        PathFinder(responseBody);
        Assert.assertEquals(js.getString("data[0].authToken"),createdAuthToken);


    }

    @Test(priority = 2,description = "verify get authtoken")
    public void getAuthToken()
    {
        JSONObject userJson = new JSONObject();
        String responseBody=given().
                header("apiKey",mainApiKey).
                header("Accept","application/json").
                when().
                get("/users/"+uid+"/auth_tokens/"+createdAuthToken+"").
                then().assertThat().statusCode(200).extract().body().asString();

        System.out.println(responseBody);

        PathFinder(responseBody);
        Assert.assertEquals(js.getString("data.authToken"),createdAuthToken);




    }

    @Test(priority = 3,description = "Verify reactivate users")
    public void reactivateUsers(){


       /* String responseBody=given().
                header("apiKey",mainApiKey).
                header("Accept","application/json").
                header("Content-Type","application/json").
             //   body("{\"uidsToActivate\": [\""+createdUserId+"\"]}").
                when().
                put("/users").
                then().assertThat().statusCode(200).extract().body().asString();

        System.out.println(responseBody);

        PathFinder(responseBody);
        String str =js.getString("data.reactivatedUids");

      //  Assert.assertEquals(str.substring(1,str.length() - 1),createdUserId);*/


    }

    @Test(priority = 4,description = "Verify get roles functionality")
    public void getRoles(){
       /* String responseBody=given().
                header("apiKey",mainApiKey).
               header("onBehalfOf",createdUserId).
                header("Accept","application/json").
              get("/users/"+createdUserId).then().
                //assertThat().statusCode(200).extract().body().asString();

        System.out.println(responseBody);

        PathFinder(responseBody);

        Assert.assertEquals(createdUserId,js.getString("data.uid"));*/


    }

    @Test(priority = 5,description = "Verify update roles functionality")
    public void updateRoles(){

        /*String updatedRoleName= getRandomString("newRoleName");



        JSONObject userJson = new JSONObject();
        userJson.put("name",updatedRoleName);
        userJson.put("description","test descriptin of update role"+updatedRoleName);


        String responseBody=given().
                header("apiKey",mainApiKey).
                header("Accept","application/json").
                header("Content-Type","application/json").
                body(userJson.toString(1)).
                when().
                put("/roles/"+createdRole).then().
                assertThat().statusCode(200).extract().body().asString();

        System.out.println(responseBody);

        PathFinder(responseBody);

        Assert.assertEquals(updatedRoleName,js.getString("data.name"));
*/



    }

    @Test(priority = 6,description = "Verify delete auth token functionality")
    public void deleteAuthToken()

    {
        given().
                header("apiKey",mainApiKey).
                header("Accept","application/json").
                when().
                delete("/users/"+uid+"/auth_tokens/"+createdAuthToken+"").
                then().assertThat().statusCode(200);



    }




}

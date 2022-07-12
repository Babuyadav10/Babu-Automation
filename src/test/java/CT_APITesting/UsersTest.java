package CT_APITesting;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class UsersTest extends BaseTest {
    String createdUserId;
    String createdUserName;
    String mainApiKey= "10ca9c4268ffa7ef032de02e8606da7e3bf67b4f";
    String responseBody;

    @Test(priority = 0,description = "Verity create users functionality")
    public void createUser(){
        String userID=getRandomString("userID");
        String username= getRandomString("UserName");

        JSONObject userJson = new JSONObject();
        userJson.put("uid",userID);
        userJson.put("name",username);

        String responseBody=given().
                header("apiKey",mainApiKey).header("Content-Type","application/json").header("Accept","application/json").
                body(userJson.toString(1)).
                when().
                post("/users").
                then().
                assertThat().statusCode(200).extract().body().asString();

        System.out.println(responseBody);
        PathFinder(responseBody);
        createdUserId = js.getString("data.uid");
        createdUserName=js.getString("data.name");
        System.out.println(createdUserId);

    }

    @Test(priority = 1,description = "Verify List users functionality")
    public void listUsers()
    {

        String responseBody=given().
                header("apiKey",mainApiKey).
                header("Accept","application/json").
                when().
                get("/users").
                then().
                assertThat().statusCode(200).extract().body().asString();

        System.out.println(responseBody);
        saveResponseBodyClassLevel(responseBody);

        PathFinder(responseBody);
        Assert.assertEquals(js.getString("data[0].uid"),createdUserId);


    }

    @Test(priority = 2,description = "Verify deactivate users functionality")
    public void deactivateUsers()
    {
        JSONObject userJson = new JSONObject();
        userJson.put("uidsToDeactivate",createdUserId);
        String responseBody=given().
                header("apiKey",mainApiKey).
                header("Accept","application/json").
                header("Content-Type","application/json").
                body("{\"uidsToDeactivate\": [\""+createdUserId+"\"]}").
                when().
                delete("/users").
                then().assertThat().statusCode(200).extract().body().asString();

        System.out.println(responseBody);

        PathFinder(responseBody);
        String str =js.getString("data.deactivatedUids");

        Assert.assertEquals(str.substring(1,str.length() - 1),createdUserId);



    }

    @Test(priority = 3,description = "Verify reactivate users functionality")
    public void reactivateUsers(){


        String responseBody=given().
                header("apiKey",mainApiKey).
                header("Accept","application/json").
                header("Content-Type","application/json").
                body("{\"uidsToActivate\": [\""+createdUserId+"\"]}").
                when().
                put("/users").
                then().assertThat().statusCode(200).extract().body().asString();

        System.out.println(responseBody);

        PathFinder(responseBody);
        String str =js.getString("data.reactivatedUids");

        Assert.assertEquals(str.substring(1,str.length() - 1),createdUserId);


    }

    @Test(priority = 4,description = "Verify get users functionality")
    public void getUsers(){
        String responseBody=given().
                header("apiKey",mainApiKey).
                header("onBehalfOf",createdUserId).
                header("Accept","application/json").
                get("/users/"+createdUserId).then().
                assertThat().statusCode(200).extract().body().asString();



        System.out.println(responseBody);

        PathFinder(responseBody);

        Assert.assertEquals(createdUserId,js.getString("data.uid"));


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

    @Test(priority = 6,description = "Verify delete users functionality")
    public void deleteUsers()

    {
        given().
                header("apiKey",mainApiKey).
                header("Accept","application/json").
                header("Content-Type","application/json").
                when().
                delete("/users/"+createdUserId).
                then().assertThat().statusCode(200);



    }

    public void saveResponseBodyClassLevel(String s)
    {
        responseBody=s;
    }

    @Test(priority = 7,description = "Verify block users functionality")
    public void blockUsers()
    {
        PathFinder(responseBody);

        String responseBody=given().
                header("apiKey",mainApiKey).
                header("Content-Type","application/json").
                header("Accept","application/json").
                body("{\"blockedUids\": [\""+js.getString("data[2].uid")+"\"]}").
                when().
                post("/users/"+js.getString("data[3].uid")+"/blockedusers").
                then().
                assertThat().statusCode(200).extract().body().asString();




    }

    @Test(priority = 8,description = "Verify list of blocked users ")
    public void listOfBlockedUsers()
    {
        PathFinder(responseBody);

        String response=given().
                header("apiKey",mainApiKey).
                header("Accept","application/json").
                when().
                get("/users/"+js.getString("data[3].uid")+"/blockedusers").
                then().
                assertThat().statusCode(200).extract().body().asString();


    }


    @Test(priority = 9,description = "Verify unblock users ")
    public void unBlockUsers()
    {
        PathFinder(responseBody);

        String response=given().
                header("apiKey",mainApiKey).
                header("Accept","application/json").
                header("Content-Type","application/json").
                body("{\"blockedUids\": [\""+js.getString("data[2].uid")+"\"]}").
                when().
                delete("/users/"+js.getString("data[3].uid")+"/blockedusers").
                then().
                assertThat().statusCode(200).extract().body().asString();


    }




}

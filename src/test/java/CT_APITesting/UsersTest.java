package CT_APITesting;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class UsersTest extends BaseTest {
   public String createdUserId;
    String createdUserName;
    String mainApiKey= "10ca9c4268ffa7ef032de02e8606da7e3bf67b4f";
    String responseBody;

    int ptc=0;


    @Test(description = "Verity create users functionality")
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
 if(ptc!=-1)
        System.out.println(responseBody);
        PathFinder(responseBody);
        createdUserId = js.getString("data.uid");
        createdUserName=js.getString("data.name");

        if(ptc!=-1)
        System.out.println(createdUserId);

        MemberTest.UName=createdUserId;

        FriendsTest.uName=createdUserId;

        MessageTest.UName=createdUserId;

    }

    @Test(description = "Verify List users functionality",dependsOnMethods = {"createUser"})
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

    @Test(description = "Verify deactivate users functionality",dependsOnMethods = {"listUsers"})
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

    @Test(description = "Verify reactivate users functionality",dependsOnMethods = {"deactivateUsers"})
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

    @Test(description = "Verify get users functionality",dependsOnMethods = {"reactivateUsers"})
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

    @Test(description = "Verify update roles functionality",dependsOnMethods = {"getUsers"})
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

    @Test(description = "Verify delete users functionality",dependsOnMethods = {"updateRoles"})
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

    @Test(description = "Verify block users functionality",dependsOnMethods = {"deleteUsers"})
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

    @Test(description = "Verify list of blocked users ",dependsOnMethods = {"blockUsers"})
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


    @Test(description = "Verify unblock users ",dependsOnMethods = {"listOfBlockedUsers"})
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

package CT_APITesting;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class UsersTest extends BaseTest {
    String createdUserId;
    String createdUserName;
    String mainApiKey= "10ca9c4268ffa7ef032de02e8606da7e3bf67b4f";

    @Test(priority = 0)
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

    @Test(priority = 1)
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

        PathFinder(responseBody);
        Assert.assertEquals(js.getString("data[0].uid"),createdUserId);


    }

    @Test(priority = 2)
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

    @Test(priority = 3)
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

    @Test(priority = 4)
    public void getRoles(){
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

    @Test(priority = 5)
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

    @Test(priority = 6)
    public void deleteRoles()

    {
        given().
                header("apiKey",mainApiKey).
                header("Accept","application/json").
                header("Content-Type","application/json").
                when().
                delete("/users/"+createdUserId).
                then().assertThat().statusCode(200);



    }


}

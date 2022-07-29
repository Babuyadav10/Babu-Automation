package CT_APITesting;

import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class GroupTest extends BaseTest {

    //public HashMap<String,Object> userJson;

    //        String Email,AccessToken;
//        ArrayList<String> emailValues = new ArrayList<String>();
//        int EmailCounts;
    String createdGuid;
    String createdGroupName;
    String createdGroupType = "public";
    String mainApiKey = "10ca9c4268ffa7ef032de02e8606da7e3bf67b4f";


    @Test(priority = 0, description = "Verify create group functionality")
    public void createGroups() {

        String guid = getRandomString("guid");
        String groupName = getRandomString("groupName");
        // String groupType = getRandomString("groupType");

        JSONObject userJson = new JSONObject();
        userJson.put("guid", guid);
        userJson.put("name", groupName);
        userJson.put("type", createdGroupType);
        userJson.put("description", "anim amet voluptate non");
        String responseBody = given().
                header("apiKey", mainApiKey).header("Content-Type", "application/json").header("Accept", "application/json").
                body(userJson.toString(1)).
                when().
                post("/groups").
                then().
                assertThat().statusCode(200).extract().body().asString();

        System.out.println(responseBody);
        PathFinder(responseBody);
        createdGuid = js.getString("data.guid");
        createdGroupName = js.getString("data.name");
        createdGroupType = js.getString("data.type");
        System.out.println(createdGuid);

    }


    @Test(priority = 1, description = "Verify list Groups")
    public void listGroups() {

        String responseBody = given().
                header("apiKey", mainApiKey).
                header("Accept", "application/json").
                header("Content-Type", "application/json").
                when().
                get("/groups").
                then().
                assertThat().statusCode(200).extract().body().asString();

        System.out.println(responseBody);

        PathFinder(responseBody);
        Assert.assertEquals(js.getString("data[0].guid"), createdGuid);
    }

    @Test(priority = 2, description = "verify get groups")
    public void getGroups() {
        String responseBody = given().
                header("apiKey", mainApiKey).
                header("Accept", "application/json").
                header("Content-Type", "application/json").
                get("/groups/" + createdGuid).then().
                assertThat().statusCode(200).extract().body().asString();

        System.out.println(responseBody);

        PathFinder(responseBody);

        Assert.assertEquals(createdGuid, js.getString("data.guid"));


    }

    @Test(priority = 3, description = "Verify update Group functionality")
    public void updateGroups() {

        String updatedGroupName = getRandomString("newGroupName");


        JSONObject userJson = new JSONObject();
        userJson.put("name", updatedGroupName);
        userJson.put("description", "test description of update group" + updatedGroupName);


        String responseBody = given().
                header("apiKey", mainApiKey).
                header("Accept", "application/json").
                header("Content-Type", "application/json").
                body(userJson.toString(1)).
                when().
                put("/groups/" + createdGuid).then().
                assertThat().statusCode(200).extract().body().asString();

        System.out.println(responseBody);

        PathFinder(responseBody);

        Assert.assertEquals(updatedGroupName, js.getString("data.name"));


    }


    @Test(priority = 4, description = "Verify delete Groups functionality")
    public void deleteGroups() {
        given().
                header("apiKey", mainApiKey).
                header("Accept", "application/json").
                when().
                delete("/groups/" + createdGuid).
                then().assertThat().statusCode(200);


    }
}
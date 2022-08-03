package CT_APITesting;

import com.sun.org.apache.xpath.internal.objects.XString;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;



import static io.restassured.RestAssured.given;

public class GroupTest extends BaseTest {

    //public HashMap<String,Object> userJson;

    //        String Email,AccessToken;
//        ArrayList<String> emailValues = new ArrayList<String>();
//        int EmailCounts;
   public String createdGuid;
     int intc=0;
    String createdGroupName;
    String createdGroupType = "public";
    String mainApiKey = "10ca9c4268ffa7ef032de02e8606da7e3bf67b4f";

    String responseBody;


    @Test(description = "Verify create group functionality")
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
                header("apiKey", mainApiKey).header("onBehalfOf","superhero1").header("Content-Type", "application/json").header("Accept", "application/json").
                body(userJson.toString(1)).
                when().
                post("/groups").
                then().
                assertThat().statusCode(200).extract().body().asString();
        if (intc !=-1)
        System.out.println(responseBody);
        PathFinder(responseBody);
        createdGuid = js.getString("data.guid");
        createdGroupName = js.getString("data.name");
        createdGroupType = js.getString("data.type");
    if (intc !=-1)
        System.out.println(createdGuid);

        MemberTest.gName=createdGuid;


    }




    @Test(description = "Verify list Groups",dependsOnMethods = {"createGroups"})
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

    @Test(description = "verify get groups",dependsOnMethods = {"listGroups"})
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

    @Test(description = "Verify update Group functionality",dependsOnMethods = {"getGroups"})
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


    @Test(description = "Verify delete Groups functionality",dependsOnMethods = {"updateGroups"})
    public void deleteGroups() {
        given().
                header("apiKey", mainApiKey).
                header("Accept", "application/json").
                when().
                delete("/groups/" + createdGuid).
                then().assertThat().statusCode(200);


    }



}
package CT_APITesting;

import org.json.JSONObject;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class GroupTest extends BaseTest {

    //public HashMap<String,Object> userJson;

    //        String Email,AccessToken;
//        ArrayList<String> emailValues = new ArrayList<String>();
//        int EmailCounts;
    String createdGuid;
    String createdGroupName;
    String createdGroupType="public";
    String mainApiKey = "10ca9c4268ffa7ef032de02e8606da7e3bf67b4f";


    @Test(priority = 0, description = "Verify create group functionality")
    public void createGroups() {

        String guid = getRandomString("guid");
        String groupName = getRandomString("groupName");
       // String groupType = getRandomString("groupType");

        JSONObject userJson = new JSONObject();
        userJson.put("guid", guid);
        userJson.put("name", groupName);
        userJson.put("type",createdGroupType);
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
}
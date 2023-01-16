package CT_APITesting;

        import io.restassured.RestAssured;
        import io.restassured.http.Method;
        import io.restassured.path.json.JsonPath;
        import io.restassured.response.Response;
        import io.restassured.specification.RequestSpecification;
        import org.json.JSONObject;
        import org.testng.Assert;
        import org.testng.annotations.Test;

        import static io.restassured.RestAssured.given;
        import static org.testng.Assert.*;

public class BabuTest extends BaseTest {

    String createdGuid;

    String createdGroupName;
    String createdGroupType = "public";
    String mainApiKey = "c7dcd3ff228dfa6445111f73ce6a4f7cdefc7b11";

    @Test(priority = 0, description = "Verify add the members functionality")
    public void addMembers() {

        String guid = getRandomString("guid");
        String groupName = getRandomString("groupName");

        RequestSpecification httpRequestObject = RestAssured.given();

        JSONObject userJson = new JSONObject();
        userJson.put("guid",guid);
        userJson.put("name",groupName);
        userJson.put("type",createdGroupType);


        httpRequestObject.header("Content-Type", "application/json");
        httpRequestObject.header("apiKey", mainApiKey);
        httpRequestObject.header("Accept", "application/json");

        httpRequestObject.body(userJson.toString());
        // header("guid","gName").
        Response response = httpRequestObject.request(Method.POST, "/groups");

        String responseBody = response.getBody().asString();
        System.out.println("responseBody is " + responseBody);

//        public void PathFinder(String Response){
//            JsonPath bs = new JsonPath(Response);
//        }

//        PathFinder(responseBody);
//        createdGuid = js.getString("data.guid");
//        createdGroupName = js.getString("data.name");
//        createdGroupType = js.getString("data.type");
//        System.out.println("createdGuid =" +createdGuid);

                   int statusCode=response.getStatusCode();
        System.out.println("statusCode is = " + statusCode);
        Assert.assertEquals(statusCode,200);

      //  System.out.println("statusCode is = " + statusCode);


      //  MemberTest mt=new MemberTest();
      //  mt.groupId(createdGuid);

        // post("/groups/"+this.gName+"/members").


        // assertThat().statusCode(200).extract().body().asString();

      //  System.out.println("group id name" + gName);

         /*   String responseBody = given().
                header("apiKey", mainApiKey).
                header("Accept", "application/json").
                header("Content-Type", "application/json").
                when().
                get("/groups/"+gName+"/members").
                then().
                assertThat().statusCode(200).extract().body().asString();

        System.out.println(responseBody);

      */


    }

}

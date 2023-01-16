package CT_APITesting;

import io.restassured.path.json.JsonPath;
import io.restassured.specification.RequestSpecification;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class BannedUserTest extends BaseTest{

   // String responseBody;
    String apiKey = GlobalClassTest.prop.getProperty("apiKey");

    @Test(description = "verify banned user functionality")
    public void banUser(){
       // System.out.println(GroupTest.userNameId);
        String  responseBody= given().
              //  header("onBehalfOf",GroupTest.userNameId).
              header("apiKey",apiKey).header("Accept","application/json").
               pathParam("guid","supergroup").
               pathParam("uid","superhero3").
               when().
               post("/groups/{guid}/bannedusers/{uid}").
               then().log().body().
                statusCode(404).extract().body().asString();

        JsonPath jp = new JsonPath(responseBody);
        Assert.assertEquals(jp.getString("error.code"),"ERR_NOT_A_MEMBER");
             // log().body().
             // assertThat().extract().body().asString();
              //  assertThat().statusCode(200).extract().body().asString();
       // System.out.println(responseBody);
    }
}

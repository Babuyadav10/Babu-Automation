package CT_APITesting;

import io.restassured.RestAssured;
import io.restassured.authentication.PreemptiveBasicAuthScheme;
import io.restassured.http.Method;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class ReportUserTest extends BaseTest {
    String appId = GlobalClassTest.prop.getProperty("appId");
    String UserReasonId;

    @Test(description = "Verify report user functionality")

    public void ReportUser() {

        JSONObject userJson = new JSONObject();
        userJson.put("uid", "superhero3");
        userJson.put("reason", "Misbehaving");

        MessageIDTest.getCommon().
                body(userJson.toString()).
                when().
                post("https://report-user-"+GlobalClassTest.prop.getProperty("region")+"."+GlobalClassTest.prop.getProperty("domainName")+"/v1/report").
                then().log().body().
                assertThat().statusCode(200);
    }

    @Test(description = " Verify Get Report For user", dependsOnMethods = {"ReportUser"})

    public void GetReportForUser()
    {
       String responseBody= MessageIDTest.getCommon().
                when().
                get("https://report-user-"+GlobalClassTest.prop.getProperty("region")+"."+GlobalClassTest.prop.getProperty("domainName")+"/v1/reports").
                then().log().body().
                assertThat().statusCode(200).extract().body().asString();

       PathFinder(responseBody);
       Assert.assertEquals(js.getString("data[0].reportedUser.appId"), appId);
        UserReasonId= js.getString("data[0].reasons[0].id");
        System.out.println("UserReasonId  " + UserReasonId);
    }

    @Test(description = "Verify TakeReportAction for user", dependsOnMethods = {"GetReportForUser"})
    public void TakeReportActionForUser()
    {
        JSONObject userJson = new JSONObject();
        userJson.put("action", "block");
        userJson.put("reasonId", new String[]{UserReasonId});
        userJson.put("reportedUid", "superhero3");
        userJson.put("uid", "superhero2");

        MessageIDTest.getCommon().
                body(userJson.toString()).
                when().
                post("https://report-user-"+GlobalClassTest.prop.getProperty("region")+"."+GlobalClassTest.prop.getProperty("domainName")+"/v1/take-action").
                then().log().body().
                assertThat().statusCode(200);
    }
}

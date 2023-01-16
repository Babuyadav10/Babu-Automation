package CT_APITesting;

import io.restassured.RestAssured;
import io.restassured.authentication.PreemptiveBasicAuthScheme;
import org.json.JSONObject;
import org.testng.annotations.Test;

public class SentimentAnalysisTest extends BaseTest{

    String appId= GlobalClassTest.prop.getProperty("appId");
    @Test(description = "Verify sentiments analysis functionality")
    public void sentimentAnalysis()
    {
//        PreemptiveBasicAuthScheme authScheme=new PreemptiveBasicAuthScheme();
//        authScheme.setUserName("user_s3NQNuR25ztmchH7");
//        authScheme.setPassword("gWr5aZvjn33CALYD");
//        RestAssured.authentication=authScheme;


        JSONObject userJson = new JSONObject();
        userJson.put("text","Get lost you fool");
        JSONObject userJson2 = new JSONObject();
        userJson2.put("data",userJson);
        JSONObject userJson3 = new JSONObject();
        userJson3.put("data",userJson2);
        userJson3.put("appId",appId);

        String responseBody= MessageIDTest.getCommon().
                auth().preemptive().basic("user_s3NQNuR25ztmchH7","gWr5aZvjn33CALYD").
                body(userJson3.toString(1)).
                when().
                post("https://sentiment-analysis-"+GlobalClassTest.prop.getProperty("region")+"."+GlobalClassTest.prop.getProperty("domainName")+"/v1/analyze").
                then().
                assertThat().statusCode(200).extract().body().asString();
        System.out.println(responseBody);
    }

}

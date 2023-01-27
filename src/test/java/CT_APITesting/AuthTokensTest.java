package CT_APITesting;

import io.restassured.specification.RequestSpecification;
import org.json.JSONObject;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class AuthTokensTest extends BaseTest{

     public String authToken1;
    public String authToken2;

    public RequestSpecification getCommon()
    {
        RequestSpecification httpRequestObject;
        httpRequestObject = given();
        httpRequestObject.header("apiKey",GlobalClassTest.prop.getProperty("apiKey"));
        httpRequestObject.header("Content-Type", "application/json");
        httpRequestObject.header("Accept", "application/json");
        return httpRequestObject;
    }

    //------------------------authToken false---------------------------

    @Test(description = "verify authToken functionality")
    public void createAuthToken()
    {
        JSONObject userJson=new JSONObject();
        userJson.put("force",false);

        String responseBody=getCommon().
                pathParam("uid","superhero1").
                body(userJson.toString(1)).
                when().
                post("/users/{uid}/auth_tokens").
                then().log().body().
                assertThat().statusCode(200).extract().body().asString();
        System.out.println(responseBody);
        PathFinder(responseBody);
        authToken1=js.getString("data.authToken");

    }

    @Test(description = "verify authToken invalid uid functionality",dependsOnMethods = {"createAuthToken"})
    public void createAuthTokenInvalidUID()
    {
        JSONObject userJson=new JSONObject();
        userJson.put("force",false);

        String responseBody=getCommon().
                pathParam("uid","superhero1000").
                body(userJson.toString(1)).
                when().
                post("/users/{uid}/auth_tokens").
                then().log().body().
                assertThat().statusCode(404).extract().body().asString();
        System.out.println(responseBody);

    }

    //------------------------authToken true---------------------------

    @Test(description = "verify authToken With true functionality",dependsOnMethods = {"createAuthToken"})
    public void createAuthTokenTrue()
    {
        JSONObject userJson=new JSONObject();
        userJson.put("force",true);

        String responseBody=getCommon().
                pathParam("uid","superhero1").
                body(userJson.toString(1)).
                when().
                post("/users/{uid}/auth_tokens").
                then().log().body().
                assertThat().statusCode(200).extract().body().asString();
        System.out.println(responseBody);
        PathFinder(responseBody);
        authToken2=js.getString("data.authToken");

    }

    @Test(description = "verify authToken With true invalid UID functionality",dependsOnMethods = {"createAuthToken"})
    public void createAuthTokenTrueInvalidUID()
    {
        JSONObject userJson=new JSONObject();
        userJson.put("force",true);

        String responseBody=getCommon().
                pathParam("uid","superhero1000").
                body(userJson.toString(1)).
                when().
                post("/users/{uid}/auth_tokens").
                then().log().body().
                assertThat().statusCode(404).extract().body().asString();
        System.out.println(responseBody);

    }

    //------------------------authToken empty data in body---------------------------

    @Test(description = "verify authToken empty data in body",dependsOnMethods = {"createAuthToken"})
    public void createAuthTokenEmpty()
    {
        String responseBody=getCommon().
                pathParam("uid","superhero1").
                when().
                post("/users/{uid}/auth_tokens").
                then().log().body().
                assertThat().statusCode(200).extract().body().asString();
        System.out.println(responseBody);

    }

    @Test(description = "verify authToken empty data in body invalid UID",dependsOnMethods = {"createAuthToken"})
    public void createAuthTokenEmptyInvalidUID()
    {
        String responseBody=getCommon().
                pathParam("uid","superhero1000").
                when().
                post("/users/{uid}/auth_tokens").
                then().log().body().
                assertThat().statusCode(404).extract().body().asString();
        System.out.println(responseBody);

    }

    //------------------------listAuthToken---------------------------

    @Test(description = "Verify listAuthToken",dependsOnMethods = {"createAuthTokenEmptyInvalidUID"})
    public void listAuthToken()
    {
        String responseBody=getCommon().
                pathParam("uid","superhero1").
                when().
                get("/users/{uid}/auth_tokens").
                then().
                assertThat().statusCode(200).extract().body().asString();
        System.out.println(responseBody);

    }

    @Test(description = "Verify listAuthToken invalid uid",dependsOnMethods = {"listAuthToken"})
    public void listAuthTokenInvalidUID()
    {
        String responseBody=getCommon().
                pathParam("uid","superhero1000").
                when().
                get("/users/{uid}/auth_tokens").
                then().
                assertThat().statusCode(404).extract().body().asString();
        System.out.println(responseBody);

    }

    @Test(description = "Verify listAuthToken empty uid",dependsOnMethods = {"listAuthToken"})
    public void listAuthTokenEmptyUID()
    {
        String responseBody=getCommon().
                pathParam("uid"," ").
                when().
                get("/users/{uid}/auth_tokens").
                then().
                assertThat().statusCode(404).extract().body().asString();
        System.out.println(responseBody);

    }


    //------------------------getAuthToken for false---------------------------

    @Test(description = "Verify getAuthToken for false",dependsOnMethods = {"listAuthTokenEmptyUID"})
    public void getAuthToken()
    {
        String responseBody=getCommon().
                pathParam("uid","superhero1").
                pathParam("authToken",authToken1).
                when().
                get("/users/{uid}/auth_tokens/{authToken}").
                then().log().body().
                assertThat().statusCode(200).extract().body().asString();
        System.out.println(responseBody);

    }


    @Test(description = "Verify getAuthToken invalid uid for false",dependsOnMethods = {"getAuthToken"})
    public void getAuthTokenInvalidUID()
    {
        String responseBody=getCommon().
                pathParam("uid","superhero1000").
                pathParam("authToken",authToken1).
                when().
                get("/users/{uid}/auth_tokens/{authToken}").
                then().log().body().
                assertThat().statusCode(404).extract().body().asString();
        System.out.println(responseBody);

    }

    @Test(description = "Verify getAuthToken invalid authToken for false",dependsOnMethods = {"getAuthToken"})
    public void getAuthTokenInvalidAuthToken()
    {
        String responseBody=getCommon().
                pathParam("uid","superhero1").
                pathParam("authToken","helloThisInvalidAuthToken").
                when().
                get("/users/{uid}/auth_tokens/{authToken}").
                then().log().body().
                assertThat().statusCode(404).extract().body().asString();
        System.out.println(responseBody);

    }

    @Test(description = "Verify getAuthToken invalid authToken & User for false",dependsOnMethods = {"getAuthToken"})
    public void getAuthTokenInvalidAuthTokenUID()
    {
        String responseBody=getCommon().
                pathParam("uid","superhero1000").
                pathParam("authToken","helloThisInvalidAuthToken").
                when().
                get("/users/{uid}/auth_tokens/{authToken}").
                then().log().body().
                assertThat().statusCode(404).extract().body().asString();
        System.out.println(responseBody);

    }



    //------------------------getAuthToken for true---------------------------

    @Test(description = "Verify getAuthToken for true",dependsOnMethods = {"getAuthToken"})
    public void getAuthTokenTrue()
    {
        String responseBody=getCommon().
                pathParam("uid","superhero1").
                pathParam("authToken",authToken2).
                when().
                get("/users/{uid}/auth_tokens/{authToken}").
                then().log().body().
                assertThat().statusCode(200).extract().body().asString();
        System.out.println(responseBody);

    }

    @Test(description = "Verify getAuthToken invalid uid for true",dependsOnMethods = {"getAuthToken"})
    public void getAuthTokenInvalidUIDTrue()
    {
        String responseBody=getCommon().
                pathParam("uid","superhero1000").
                pathParam("authToken",authToken2).
                when().
                get("/users/{uid}/auth_tokens/{authToken}").
                then().log().body().
                assertThat().statusCode(404).extract().body().asString();
        System.out.println(responseBody);

    }

    @Test(description = "Verify getAuthToken invalid authToken for true",dependsOnMethods = {"getAuthToken"})
    public void getAuthTokenInvalidAuthTokenTrue()
    {
        String responseBody=getCommon().
                pathParam("uid","superhero1").
                pathParam("authToken","helloThisInvalidAuthToken").
                when().
                get("/users/{uid}/auth_tokens/{authToken}").
                then().log().body().
                assertThat().statusCode(404).extract().body().asString();
        System.out.println(responseBody);

    }

    @Test(description = "Verify getAuthToken invalid authToken & User for true",dependsOnMethods = {"getAuthToken"})
    public void getAuthTokenInvalidAuthTokenUIDTrue()
    {
        String responseBody=getCommon().
                pathParam("uid","superhero1000").
                pathParam("authToken","helloThisInvalidAuthToken").
                when().
                get("/users/{uid}/auth_tokens/{authToken}").
                then().log().body().
                assertThat().statusCode(404).extract().body().asString();
        System.out.println(responseBody);

    }

    //------------------------updateAuthToken ---------------------------

    @Test(description = "verify updateAuthToken functionality",dependsOnMethods = {"getAuthTokenInvalidAuthTokenUIDTrue"})
    public void updateAuthToken()
    {
        JSONObject userJson1=new JSONObject();
        userJson1.put("newKey","additional data You can use here");
        JSONObject userJson=new JSONObject();
        userJson.put("platform","web");
        userJson.put("userAgent","cometchat");
        userJson.put("appInfo",userJson1);

        String responseBody=getCommon().
                pathParam("uid","superhero1").
                pathParam("authToken",authToken1).
                body(userJson.toString(1)).
                when().
                put("/users/{uid}/auth_tokens/{authToken}").
                then().log().body().
                assertThat().statusCode(200).extract().body().asString();
        System.out.println(responseBody);

    }

    @Test(description = "Verify getAuthToken after updated authToken",dependsOnMethods = {"updateAuthToken"})
    public void getAuthTokenAfterUpdate()
    {
        String responseBody=getCommon().
                pathParam("uid","superhero1").
                pathParam("authToken",authToken1).
                when().
                get("/users/{uid}/auth_tokens/{authToken}").
                then().log().body().
                assertThat().statusCode(200).extract().body().asString();
        System.out.println(responseBody);

    }

    @Test(description = "verify updateAuthToken invalid uid",dependsOnMethods = {"updateAuthToken"})
    public void updateAuthTokenInvalidUID()
    {
        JSONObject userJson1=new JSONObject();
        userJson1.put("newKey","additional data You can use here");
        JSONObject userJson=new JSONObject();
        userJson.put("platform","web");
        userJson.put("userAgent","cometchat");
        userJson.put("appInfo",userJson1);

        String responseBody=getCommon().
                pathParam("uid","superhero1000").
                pathParam("authToken",authToken1).
                body(userJson.toString(1)).
                when().
                put("/users/{uid}/auth_tokens/{authToken}").
                then().log().body().
                assertThat().statusCode(404).extract().body().asString();
        System.out.println(responseBody);

    }

    @Test(description = "verify updateAuthToken invalid authToken",dependsOnMethods = {"updateAuthToken"})
    public void updateAuthTokenInvalidAuthToken()
    {
        JSONObject userJson1=new JSONObject();
        userJson1.put("newKey","additional data You can use here");
        JSONObject userJson=new JSONObject();
        userJson.put("platform","web");
        userJson.put("userAgent","cometchat");
        userJson.put("appInfo",userJson1);

        String responseBody=getCommon().
                pathParam("uid","superhero1").
                pathParam("authToken","InvalidAUth1234356").
                body(userJson.toString(1)).
                when().
                put("/users/{uid}/auth_tokens/{authToken}").
                then().log().body().
                assertThat().statusCode(404).extract().body().asString();
        System.out.println(responseBody);

    }

    @Test(description = "verify updateAuthToken invalid uid and authToken",dependsOnMethods = {"updateAuthToken"})
    public void updateAuthTokenInvalidAuthTokenUID()
    {
        JSONObject userJson1=new JSONObject();
        userJson1.put("newKey","additional data You can use here");
        JSONObject userJson=new JSONObject();
        userJson.put("platform","web");
        userJson.put("userAgent","cometchat");
        userJson.put("appInfo",userJson1);

        String responseBody=getCommon().
                pathParam("uid","superhero1000").
                pathParam("authToken","InvalidAUth1234356").
                body(userJson.toString(1)).
                when().
                put("/users/{uid}/auth_tokens/{authToken}").
                then().log().body().
                assertThat().statusCode(404).extract().body().asString();
        System.out.println(responseBody);

    }

    //------------------------deleteAuthToken---------------------------

    @Test(description = "Verify deleteAuthToken",dependsOnMethods = {"updateAuthTokenInvalidAuthTokenUID"})
    public void deleteAuthToken()
    {
        String responseBody=getCommon().
                pathParam("uid","superhero1").
                pathParam("authToken",authToken2).
                when().
                delete("/users/{uid}/auth_tokens/{authToken}").
                then().log().body().
                assertThat().statusCode(200).extract().body().asString();
        System.out.println(responseBody);

    }

    @Test(description = "Verify deleteAuthToken with true",dependsOnMethods = {"deleteAuthToken"})
    public void deleteAuthTokenTrue()
    {
        String responseBody=getCommon().
                pathParam("uid","superhero1").
                pathParam("authToken",authToken1).
                when().
                delete("/users/{uid}/auth_tokens/{authToken}").
                then().log().body().
                assertThat().statusCode(200).extract().body().asString();
        System.out.println(responseBody);

    }

    @Test(description = "Verify deleteAuthToken with true which is already deleted",dependsOnMethods = {"deleteAuthToken"})
    public void deleteAuthTokenTrueAlreadyDeleted()
    {
        String responseBody=getCommon().
                pathParam("uid","superhero1").
                pathParam("authToken",authToken1).
                when().
                delete("/users/{uid}/auth_tokens/{authToken}").
                then().log().body().
                assertThat().statusCode(404).extract().body().asString();
        System.out.println(responseBody);

    }

    @Test(description = "Verify deleteAuthToken invalid uid",dependsOnMethods = {"deleteAuthToken"})
    public void deleteAuthTokenInvalidUID()
    {
        String responseBody=getCommon().
                pathParam("uid","superhero1000").
                pathParam("authToken",authToken2).
                when().
                delete("/users/{uid}/auth_tokens/{authToken}").
                then().log().body().
                assertThat().statusCode(404).extract().body().asString();
        System.out.println(responseBody);

    }

    @Test(description = "Verify deleteAuthToken invalid authToken",dependsOnMethods = {"deleteAuthToken"})
    public void deleteAuthTokenInvalidAuthToken()
    {
        String responseBody=getCommon().
                pathParam("uid","superhero1").
                pathParam("authToken","helloThisInvalidAuthToken").
                when().
                delete("/users/{uid}/auth_tokens/{authToken}").
                then().log().body().
                assertThat().statusCode(404).extract().body().asString();
        System.out.println(responseBody);

    }

    @Test(description = "Verify deleteAuthToken invalid authToken & User",dependsOnMethods = {"deleteAuthToken"})
    public void deleteAuthTokenInvalidAuthTokenUID()
    {
        String responseBody=getCommon().
                pathParam("uid","superhero1000").
                pathParam("authToken","helloThisInvalidAuthToken").
                when().
                delete("/users/{uid}/auth_tokens/{authToken}").
                then().log().body().
                assertThat().statusCode(404).extract().body().asString();
        System.out.println(responseBody);

    }


}

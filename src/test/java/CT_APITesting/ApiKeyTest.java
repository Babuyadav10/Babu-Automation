package CT_APITesting;
import io.restassured.http.Method;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class ApiKeyTest extends BaseTest {
    public String createdName;
    public String createdApiKey;
    public String updatedCreatedApiKey;
    String apiKey = GlobalClassTest.prop.getProperty("apiKey");
    public RequestSpecification getCommon()
    {
        RequestSpecification httpRequestObject;
        httpRequestObject = given();
        httpRequestObject.header("apiKey", apiKey);
        httpRequestObject.header("Content-Type", "application/json");
        httpRequestObject.header("Accept", "application/json");
        return httpRequestObject;
    }

    @Test(description = "Verify apiKeys functionality")

    public void createApiKey() {

        String name1 = getRandomString("name");
        RequestSpecification httpRequestObject = getCommon();

        JSONObject userJson = new JSONObject();
        userJson.put("name", name1);
        userJson.put("scope", "fullAccess");

        httpRequestObject.body(userJson.toString());
        Response response = httpRequestObject.request(Method.POST, "/apikeys");

        String responseBody = response.getBody().asString();
        System.out.println("responseBody : " + responseBody);
        int statusCode = response.getStatusCode();
        System.out.println("statusCode is  " + statusCode);
        Assert.assertEquals(statusCode, 200);
        PathFinder(responseBody);
        Assert.assertEquals(js.getString("data.name"),name1);
    }
    @Test(description = "Verify create ApiKey Scope AuthOnly functionality")

    public void createApiKeyScopeAuthOnly() {

        String name1 = getRandomString("name1");
        RequestSpecification httpRequestObject = getCommon();

        JSONObject userJson = new JSONObject();
        userJson.put("name", name1);
        userJson.put("scope", "authOnly");

        httpRequestObject.body(userJson.toString());
        Response response = httpRequestObject.request(Method.POST, "/apikeys");

        String responseBody = response.getBody().asString();
        System.out.println("responseBody : " + responseBody);
        int statusCode = response.getStatusCode();
        System.out.println("statusCode is  " + statusCode);
        Assert.assertEquals(statusCode, 200);
        PathFinder(responseBody);
        Assert.assertEquals(js.getString("data.name"),name1);
    }

    @Test(description = "Verify create ApiKey Without Scope functionality")

    public void createApiKeyWithoutScope() {

        String name1 = getRandomString("name1");
        RequestSpecification httpRequestObject = getCommon();

        JSONObject userJson = new JSONObject();
        userJson.put("name", name1);

        httpRequestObject.body(userJson.toString());
        Response response = httpRequestObject.request(Method.POST, "/apikeys");

        String responseBody = response.getBody().asString();
        System.out.println("responseBody : " + responseBody);
        int statusCode = response.getStatusCode();
        System.out.println("statusCode is  " + statusCode);
        Assert.assertEquals(statusCode, 200);
        PathFinder(responseBody);
       Assert.assertEquals(js.getString("data.name"),name1);
       createdName= js.getString("data.name");
    }
    @Test(description = "Verify create ApiKey Scope AuthOnly functionality",dependsOnMethods = {"createApiKey"})

    public void createApiKeyNameTaken() {

        RequestSpecification httpRequestObject = getCommon();

        JSONObject userJson = new JSONObject();
        userJson.put("name", createdName);
        userJson.put("scope", "authOnly");

        httpRequestObject.body(userJson.toString());
        Response response = httpRequestObject.request(Method.POST, "/apikeys");

        String responseBody = response.getBody().asString();
        System.out.println("responseBody : " + responseBody);
        int statusCode = response.getStatusCode();
        System.out.println("statusCode is  " + statusCode);
        Assert.assertEquals(statusCode, 400);
    }
    @Test(description = "Verify create ApiKey Without Scope & name functionality")

    public void createApiKeyWithoutNameAndScope() {

        RequestSpecification httpRequestObject = getCommon();

        JSONObject userJson = new JSONObject();
        userJson.put("name", " ");
        userJson.put("scope", " ");

        httpRequestObject.body(userJson.toString());
        Response response = httpRequestObject.request(Method.POST, "/apikeys");

        String responseBody = response.getBody().asString();
        System.out.println("responseBody : " + responseBody);
        int statusCode = response.getStatusCode();
        System.out.println("statusCode is  " + statusCode);
        Assert.assertEquals(statusCode, 400);
    }

    @Test(description = "Verify list apikey")
    public void listApikey() {

        RequestSpecification httpRequestObject = getCommon();
        Response response = httpRequestObject.request(Method.GET, "/apikeys");

        String responseBody = response.getBody().asString();
        System.out.println("responseBody : " + responseBody);
        int statusCode = response.getStatusCode();
        System.out.println("statusCode is  " + statusCode);
        Assert.assertEquals(statusCode, 200);
        PathFinder(responseBody);
        //Assert.assertEquals(js.getString("data.name"),name1);
        createdApiKey= js.getString("data[0].apiKey");
    }
    @Test(description = "Verify list apikey by scope")
    public void listApikeyByScope() {

        RequestSpecification httpRequestObject = getCommon();
        Response response = httpRequestObject.queryParam("scope", "authOnly").get("/apikeys");

        String responseBody = response.getBody().asString();
        System.out.println("responseBody : " + responseBody);
        int statusCode = response.getStatusCode();
        System.out.println("statusCode is  " + statusCode);
        Assert.assertEquals(statusCode, 200);
    }
    @Test(description = "Verify list apikey by searchKey")
    public void listApikeyBySearchKey() {

        RequestSpecification httpRequestObject = getCommon();
        Response response = httpRequestObject.queryParam("searchKey", "name").get("/apikeys");

        String responseBody = response.getBody().asString();
        System.out.println("responseBody : " + responseBody);
        int statusCode = response.getStatusCode();
        System.out.println("statusCode is  " + statusCode);
        Assert.assertEquals(statusCode, 200);
    }
    @Test(description = "Verify list apikey by Invalid scope")
    public void listApikeyByInvalidScope() {

        RequestSpecification httpRequestObject = getCommon();
        Response response = httpRequestObject.queryParam("scope", "authOnlys").get("/apikeys");

        String responseBody = response.getBody().asString();
        System.out.println("responseBody : " + responseBody);
        int statusCode = response.getStatusCode();
        System.out.println("statusCode is  " + statusCode);
        Assert.assertEquals(statusCode, 200);
        PathFinder(responseBody);
        Assert.assertEquals(js.getString("data"),"[]");
    }
    @Test(description = "Verify list apikey by Invalid endpoints")
    public void listApikeyByInvalidEndpoints() {

        RequestSpecification httpRequestObject = getCommon();
        Response response = httpRequestObject.queryParam("scope", "authOnlys").get("/apikeyss");

        String responseBody = response.getBody().asString();
        System.out.println("responseBody : " + responseBody);
        int statusCode = response.getStatusCode();
        System.out.println("statusCode is  " + statusCode);
        Assert.assertEquals(statusCode, 404);
        PathFinder(responseBody);
        Assert.assertEquals(js.getString("error.message"),"The API endpoint is invalid. Please verify the API call.");
    }
    @Test(description = "Verify Get apikey")
    public void getApikey() {

        RequestSpecification httpRequestObject = getCommon();
        Response response = httpRequestObject.request(Method.GET, "/apikeys/"+createdApiKey);

        String responseBody = response.getBody().asString();
        System.out.println("responseBody : " + responseBody);
        int statusCode = response.getStatusCode();
        System.out.println("statusCode is  " + statusCode);
        Assert.assertEquals(statusCode, 200);
    }

    @Test(description = "Verify Get apikey invalid")
    public void getApikeyInvalid() {

        RequestSpecification httpRequestObject = getCommon();
        Response response = httpRequestObject.request(Method.GET, "/apikeys/{apiKey}","babu");

        String responseBody = response.getBody().asString();
        System.out.println("responseBody : " + responseBody);
        int statusCode = response.getStatusCode();
        System.out.println("statusCode is  " + statusCode);
        Assert.assertEquals(statusCode, 404);
    }
    @Test(description = "Verify Get apikey empty")
    public void getApikeyEmpty() {

        RequestSpecification httpRequestObject = getCommon();
        Response response = httpRequestObject.request(Method.GET, "/apikeys/{apiKey}"," ");

        String responseBody = response.getBody().asString();
        System.out.println("responseBody : " + responseBody);
        int statusCode = response.getStatusCode();
        System.out.println("statusCode is  " + statusCode);
        Assert.assertEquals(statusCode, 404);
    }
    @Test(description = "Verify update apiKeys functionality")

    public void updateApiKey() {

        String name2 = getRandomString("updatedName");
        RequestSpecification httpRequestObject = getCommon();

        JSONObject userJson = new JSONObject();
        userJson.put("name", name2);
        userJson.put("scope", "fullAccess");

        httpRequestObject.body(userJson.toString());
        Response response = httpRequestObject.request(Method.PUT, "/apikeys/"+createdApiKey);

        String responseBody = response.getBody().asString();
        System.out.println("responseBody : " + responseBody);
        int statusCode = response.getStatusCode();
        System.out.println("statusCode is  " + statusCode);
        Assert.assertEquals(statusCode, 200);
        PathFinder(responseBody);
        updatedCreatedApiKey= js.getString("data.apiKey");

    }
    @Test(description = "Verify update apiKeys empty name functionality")

    public void updateApiKeyEmptyName() {

        RequestSpecification httpRequestObject = getCommon();

        JSONObject userJson = new JSONObject();
        userJson.put("name", " ");
        userJson.put("scope", "fullAccess");

        httpRequestObject.body(userJson.toString());
        Response response = httpRequestObject.request(Method.PUT, "/apikeys/"+createdApiKey);

        String responseBody = response.getBody().asString();
        System.out.println("responseBody : " + responseBody);
        int statusCode = response.getStatusCode();
        System.out.println("statusCode is  " + statusCode);
        Assert.assertEquals(statusCode, 400);
    }
    @Test(description = "Verify update apiKeys empty name functionality")

    public void updateApiKeyEmptyScope() {

        String name2 = getRandomString("name2");
        RequestSpecification httpRequestObject = getCommon();

        JSONObject userJson = new JSONObject();
        userJson.put("name", name2);
        userJson.put("scope", " ");

        httpRequestObject.body(userJson.toString());
        Response response = httpRequestObject.request(Method.PUT, "/apikeys/"+createdApiKey);

        String responseBody = response.getBody().asString();
        System.out.println("responseBody : " + responseBody);
        int statusCode = response.getStatusCode();
        System.out.println("statusCode is  " + statusCode);
        Assert.assertEquals(statusCode, 400);
    }
    @Test(description = "Verify update apiKeys only name functionality")

    public void updateApiKeyOnlyName() {

        String name2 = getRandomString("name2");
        RequestSpecification httpRequestObject = getCommon();

        JSONObject userJson = new JSONObject();
        userJson.put("name", name2);

        httpRequestObject.body(userJson.toString());
        Response response = httpRequestObject.request(Method.PUT, "/apikeys/"+createdApiKey);

        String responseBody = response.getBody().asString();
        System.out.println("responseBody : " + responseBody);
        int statusCode = response.getStatusCode();
        System.out.println("statusCode is  " + statusCode);
        Assert.assertEquals(statusCode, 200);
    }
    @Test(description = "Verify update apiKeys only scope functionality")

    public void updateApiKeyOnlyScope() {

        RequestSpecification httpRequestObject = getCommon();

        JSONObject userJson = new JSONObject();
        userJson.put("scope", "fullAccess");

        httpRequestObject.body(userJson.toString());
        Response response = httpRequestObject.request(Method.PUT, "/apikeys/"+createdApiKey);

        String responseBody = response.getBody().asString();
        System.out.println("responseBody : " + responseBody);
        int statusCode = response.getStatusCode();
        System.out.println("statusCode is  " + statusCode);
        Assert.assertEquals(statusCode, 200);
    }
    @Test(description = "Verify invalid apiKeys functionality")

    public void updateApiKeyInvalid() {

        String name2 = getRandomString("name2");
        RequestSpecification httpRequestObject = getCommon();

        JSONObject userJson = new JSONObject();
        userJson.put("name", name2);
        userJson.put("scope", "fullAccess");

        httpRequestObject.body(userJson.toString());
        Response response = httpRequestObject.request(Method.PUT, "/apikeys/{apiKey}","babuinvalidapikey");

        String responseBody = response.getBody().asString();
        System.out.println("responseBody : " + responseBody);
        int statusCode = response.getStatusCode();
        System.out.println("statusCode is  " + statusCode);
        Assert.assertEquals(statusCode, 404);
    }
    @Test(description = "Verify empty path params apiKeys functionality")

    public void updateApiKeyEmptyPath() {

        String name2 = getRandomString("name2");
        RequestSpecification httpRequestObject = getCommon();

        JSONObject userJson = new JSONObject();
        userJson.put("name", name2);
        userJson.put("scope", "fullAccess");

        httpRequestObject.body(userJson.toString());
        Response response = httpRequestObject.request(Method.PUT, "/apikeys/{apiKey}"," ");

        String responseBody = response.getBody().asString();
        System.out.println("responseBody : " + responseBody);
        int statusCode = response.getStatusCode();
        System.out.println("statusCode is  " + statusCode);
        Assert.assertEquals(statusCode, 404);
    }
    @Test(description = "Verify delete apiKeys functionality", dependsOnMethods = {"listApikey"})
    public void deleteApiKey() {

        RequestSpecification httpRequestObject = getCommon();

        Response response = httpRequestObject.request(Method.DELETE, "/apikeys/"+createdApiKey);

        String responseBody = response.getBody().asString();
        System.out.println("responseBody : " + responseBody);
        int statusCode = response.getStatusCode();
        System.out.println("statusCode is  " + statusCode);
        Assert.assertEquals(statusCode, 200);
    }

    @Test(description = "Verify delete apiKeys functionality")
    public void deleteApiKeyEmptyPath() {

        RequestSpecification httpRequestObject = getCommon();

        Response response = httpRequestObject.request(Method.DELETE, "/apikeys/{apiKeys}"," ");

        String responseBody = response.getBody().asString();
        System.out.println("responseBody : " + responseBody);
        int statusCode = response.getStatusCode();
        System.out.println("statusCode is  " + statusCode);
        Assert.assertEquals(statusCode, 404);
    }
    @Test(description = "Verify delete apiKeys invalid functionality")
    public void deleteApiKeyInvalid() {

        RequestSpecification httpRequestObject = getCommon();

        Response response = httpRequestObject.request(Method.DELETE, "/apikeys/{apiKeys}","b37fb2a1702c62bcf8f506a1c4880c53be8418a5bs");

        String responseBody = response.getBody().asString();
        System.out.println("responseBody : " + responseBody);
        int statusCode = response.getStatusCode();
        System.out.println("statusCode is  " + statusCode);
        Assert.assertEquals(statusCode, 404);
    }

}

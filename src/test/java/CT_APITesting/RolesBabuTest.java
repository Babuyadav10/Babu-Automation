package CT_APITesting;

import io.restassured.http.Method;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class RolesBabuTest extends BaseTest{
        String createdRole;
        String createdRoleName;
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
    public  JSONObject printGetResponse(Response response, int status )
    {
        JSONObject ResponseJson = new JSONObject();
        String responseBody = response.getBody().asString();
        System.out.println("responseBody : " + responseBody);
        int statusCode = response.getStatusCode();
        System.out.println("statusCode is  " + statusCode);
        ResponseJson.put("statusCode", statusCode);
        ResponseJson.put("responseBody", responseBody);
        Assert.assertEquals(statusCode, status);
        PathFinder(responseBody);
       return ResponseJson;
    }
    @Test(description = "Verify create roles valid role & name functionality")
        public void createRoles() {

            String role = getRandomString("roleID");
            String roleName = getRandomString("roleName");
            RequestSpecification httpRequestObject = getCommon();

            JSONObject userJson = new JSONObject();
            userJson.put("role", role);
            userJson.put("name", roleName);
            userJson.put("description", "role & name are randomly created");

            httpRequestObject.body(userJson.toString());
            Response response = httpRequestObject.request(Method.POST, "/roles");

             printGetResponse(response,200);
            createdRole = js.getString("data.role");
            createdRoleName = js.getString("data.name");
        }
    @Test(description = "Verify create roles already taken functionality", dependsOnMethods = {"createRoles"})
    public void createRolesAlreadyTaken() {

        String roleName = getRandomString("roleName");
        RequestSpecification httpRequestObject = getCommon();

        JSONObject userJson = new JSONObject();
        userJson.put("role", createdRole);
        userJson.put("name", roleName);
        userJson.put("description", "Roles Already Taken method");

        httpRequestObject.body(userJson.toString());
        Response response = httpRequestObject.request(Method.POST, "/roles");

        printGetResponse(response,400);
        Assert.assertEquals(js.getString("error.details.role[0]"),"The role has already been taken.");
    }
    @Test(description = "Verify empty role functionality")
    public void createRolesEmptyRole() {

        String roleName = getRandomString("roleName");
        RequestSpecification httpRequestObject = getCommon();

        JSONObject userJson = new JSONObject();
        userJson.put("role","");
        userJson.put("name", roleName);
        userJson.put("description", "empty role");

        httpRequestObject.body(userJson.toString());
        Response response = httpRequestObject.request(Method.POST, "/roles");

        String responseBody = response.getBody().asString();
        System.out.println("responseBody : " + responseBody);
        int statusCode = response.getStatusCode();
        System.out.println("statusCode is  " + statusCode);
        Assert.assertEquals(statusCode, 400);
        PathFinder(responseBody);
        Assert.assertEquals(js.getString("error.details.role[0]"),"The role field is required.");
    }
    @Test(description = "Verify empty name functionality")
    public void createRolesEmptyName() {

        String role = getRandomString("roleID");
        RequestSpecification httpRequestObject = getCommon();

        JSONObject userJson = new JSONObject();
        userJson.put("role",role);
        userJson.put("name", "");
        userJson.put("description", "empty name");

        httpRequestObject.body(userJson.toString());
        Response response = httpRequestObject.request(Method.POST, "/roles");

        String responseBody = response.getBody().asString();
        System.out.println("responseBody : " + responseBody);
        int statusCode = response.getStatusCode();
        System.out.println("statusCode is  " + statusCode);
        Assert.assertEquals(statusCode, 400);
        PathFinder(responseBody);
        Assert.assertEquals(js.getString("error.details.name[0]"),"The name field is required.");
    }
    @Test(description = "Verify empty role & name functionality")
    public void createRolesEmpty() {

        RequestSpecification httpRequestObject = getCommon();

        JSONObject userJson = new JSONObject();
        userJson.put("role","");
        userJson.put("name", "");
        userJson.put("description", "empty role & name");

        httpRequestObject.body(userJson.toString());
        Response response = httpRequestObject.request(Method.POST, "/roles");

        String responseBody = response.getBody().asString();
        System.out.println("responseBody : " + responseBody);
        int statusCode = response.getStatusCode();
        System.out.println("statusCode is  " + statusCode);
        Assert.assertEquals(statusCode, 400);
        PathFinder(responseBody);
        Assert.assertEquals(js.getString("error.details.role[0]"),"The role field is required.");
        Assert.assertEquals(js.getString("error.details.name[0]"),"The name field is required.");
    }
    @Test(description = "Verify list roles")
    public void listRoles() {

        RequestSpecification httpRequestObject = getCommon();
        Response response = httpRequestObject.request(Method.GET, "/roles");

        String responseBody = response.getBody().asString();
        System.out.println("responseBody : " + responseBody);
        int statusCode = response.getStatusCode();
        System.out.println("statusCode is  " + statusCode);
        Assert.assertEquals(statusCode, 200);
    }
    @Test(description = "Verify list roles invalid endpoints")
    public void listRolesInvalidEndpoint() {

        RequestSpecification httpRequestObject = getCommon();
        Response response = httpRequestObject.request(Method.GET, "/roless");

        JSONObject ResponseJson =printGetResponse(response,404);
        Assert.assertEquals(js.getString("error.message"),"The API endpoint is invalid. Please verify the API call.");
    }
    @Test(description = "Verify Get role")
    public void getRole() {

        System.out.println("createdRole is  " + createdRole);
        RequestSpecification httpRequestObject = getCommon();
        Response response = httpRequestObject.request(Method.GET, "/roles/"+createdRole);

        String responseBody = response.getBody().asString();
        System.out.println("responseBody : " + responseBody);
        int statusCode = response.getStatusCode();
        System.out.println("statusCode is  " + statusCode);
        Assert.assertEquals(statusCode, 200);
    }

    @Test(description = "Verify Get role invalid")
    public void getRoleInvalid() {

        RequestSpecification httpRequestObject = getCommon();
        Response response = httpRequestObject.request(Method.GET, "/roles/{role}","InvalidRole");

        String responseBody = response.getBody().asString();
        System.out.println("responseBody : " + responseBody);
        int statusCode = response.getStatusCode();
        System.out.println("statusCode is  " + statusCode);
        Assert.assertEquals(statusCode, 404);
    }
    @Test(description = "Verify Get role empty")
    public void getRoleEmpty() {

        RequestSpecification httpRequestObject = getCommon();
        Response response = httpRequestObject.request(Method.GET, "/roles/{role}"," ");

        String responseBody = response.getBody().asString();
        System.out.println("responseBody : " + responseBody);
        int statusCode = response.getStatusCode();
        System.out.println("statusCode is  " + statusCode);
        Assert.assertEquals(statusCode, 404);
    }

}

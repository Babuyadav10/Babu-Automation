package CT_APITesting;

import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class RolesTest extends BaseTest {

        //public HashMap<String,Object> userJson;

//        String Email,AccessToken;
//        ArrayList<String> emailValues = new ArrayList<String>();
//        int EmailCounts;
        String createdRole;
        String createdRoleName;
        String mainApiKey= "10ca9c4268ffa7ef032de02e8606da7e3bf67b4f";



        @Test(description = "Verify create roles functionality")
        public void createRoles(){

                String role=getRandomString("roleID");
                String roleName= getRandomString("roleName");

                JSONObject userJson = new JSONObject();
                userJson.put("role",role);
                userJson.put("name",roleName);
                userJson.put("description","anim amet voluptate non");
                String responseBody=given().
                        header("apiKey",mainApiKey).header("Content-Type","application/json").header("Accept","application/json").
                        body(userJson.toString(1)).
                        when().
                        post("/roles").
                        then().
                        assertThat().statusCode(200).extract().body().asString();

                System.out.println(responseBody);
               PathFinder(responseBody);
               createdRole = js.getString("data.role");
               createdRoleName=js.getString("data.name");
               System.out.println(createdRole);

        }

        @Test(description = "Verify list roles functionality",dependsOnMethods = {"createRoles"})
        public void listRoles(){

                String responseBody=given().
                        header("apiKey",mainApiKey).
                        header("Accept","application/json").
                        when().
                        get("/roles").
                        then().
                        assertThat().statusCode(200).extract().body().asString();

                System.out.println(responseBody);

                PathFinder(responseBody);
                Assert.assertEquals(js.getString("data[0].role"),createdRole);
        }

        @Test(description = "verify get roles functionality",dependsOnMethods = {"listRoles"})
        public void getRoles(){
                String responseBody=given().
                        header("apiKey",mainApiKey).
                        header("Accept","application/json").
                        get("/roles/"+createdRole).then().
                        assertThat().statusCode(200).extract().body().asString();

                System.out.println(responseBody);

                PathFinder(responseBody);

                Assert.assertEquals(createdRole,js.getString("data.role"));


        }

        @Test(description = "Verify update roles functionality",dependsOnMethods = {"getRoles"})
        public void updateRoles(){

                String updatedRoleName= getRandomString("newRoleName");



                JSONObject userJson = new JSONObject();
                userJson.put("name",updatedRoleName);
                userJson.put("description","test descriptin of update role"+updatedRoleName);


                String responseBody=given().
                        header("apiKey",mainApiKey).
                        header("Accept","application/json").
                        header("Content-Type","application/json").
                        body(userJson.toString(1)).
                        when().
                        put("/roles/"+createdRole).then().
                        assertThat().statusCode(200).extract().body().asString();

                System.out.println(responseBody);

                PathFinder(responseBody);

                Assert.assertEquals(updatedRoleName,js.getString("data.name"));




        }

        @Test(description = "Verify delete roles functionality",dependsOnMethods = {"updateRoles"})
        public void deleteRoles()

        {
                      given().
                        header("apiKey",mainApiKey).
                        header("Accept","application/json").
                        when().
                        delete("/roles/"+createdRole).
                        then().assertThat().statusCode(200);



        }
}

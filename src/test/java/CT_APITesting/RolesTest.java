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



        @Test(priority = 0)
        public void createRoles(){

                String role=getRandomString("roleID");
                String roleName= getRandomString("roleName");

                JSONObject userJson = new JSONObject();
                userJson.put("role",role);
                userJson.put("name",roleName);
                userJson.put("description","anim amet voluptate non");



                String responseBody=given().
//                        pathParam("appId","208125083d6b2e3d").pathParam("region","us").
                        header("apiKey",mainApiKey).header("Content-Type","application/json").header("Accept","application/json").
                        body(userJson.toString(1)).
                        when().
                        post("/roles").
                        then().
                        assertThat().statusCode(200).extract().body().asString();

                System.out.println(responseBody);




//                String responseDetails = given().queryParam("page","2").header("Content-Type","application/json")
//                        .when().get("api/users")
//                        .then().assertThat().statusCode(200).body("page",equalTo(2)).extract().body().asString();

               PathFinder(responseBody);
               createdRole = js.getString("data.role");

               createdRoleName=js.getString("data.name");

               System.out.println(createdRole);
//                EmailCounts = js.getInt("per_page");
//
//                for (int i=0;i<EmailCounts;i++){
//                        Email = js.getString("data.email["+i+"]");
//                        emailValues.add(Email);
//                }



               // System.out.println("ResponseBody:- "+responseDetails);
//                System.out.println("Email ID:- "+Email);
//                System.out.println("**********:- "+emailValues);
        }

        @Test(priority = 1)
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

//
//                for(int j=0;j<emailValues.size();j++) {
//                        String responseLogin = given().body("{\n" +
//                                "    \"email\": \"" + emailValues.get(j) + "\",\n" +
//                                "    \"password\": \"cityslicka\"\n" +
//                                "}").header("Content-Type", "application/json")
//                                .when().post("api/login")
//                                .then().assertThat().statusCode(200)
//                                .extract().asString();
//
//                        PathFinder(responseLogin);
//                        AccessToken = js.getString("token");
//                        System.out.println("Access Token:- "+AccessToken+" Email ID:- "+emailValues.get(j));
//                }

                //System.out.println("ResponseBody:- "+responseLogin);

        }

        @Test(priority = 2)
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

        @Test(priority = 3)
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

        @Test(priority = 4)
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

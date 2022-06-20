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
        String createdAPIKey;
        String createdAPIName;
        String mainApiKey= "11e05c7614500a86adb556ad94bcab118afb202d";



        @Test(priority = 0)
        public void createRoles(){

                String apiKeyname=getRandomString("apiKeyName");

                JSONObject userJson = new JSONObject();
                userJson.put("name",apiKeyname);
                userJson.put("scope","authOnly");



                String responseBody=given().
//                        pathParam("appId","208125083d6b2e3d").pathParam("region","us").
                        header("apiKey",mainApiKey).header("Content-Type","application/json").header("Accept","application/json").
                        body(userJson.toString(1)).
                        when().
                        post("/apikeys").
                        then().
                        assertThat().statusCode(200).extract().body().asString();

                System.out.println(responseBody);




//                String responseDetails = given().queryParam("page","2").header("Content-Type","application/json")
//                        .when().get("api/users")
//                        .then().assertThat().statusCode(200).body("page",equalTo(2)).extract().body().asString();

               PathFinder(responseBody);
               createdAPIKey = js.getString("data.apiKey");

               createdAPIName=js.getString("data.name");

               System.out.println(createdAPIKey);
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
                        queryParam("scope","authOnly").
                        header("apiKey",mainApiKey).
                        header("Accept","application/json").
                        when().
                        get("/apikeys").
                        then().
                        assertThat().statusCode(200).extract().body().asString();

                System.out.println(responseBody);

                PathFinder(responseBody);
                Assert.assertEquals(js.getString("data[0].apiKey"),createdAPIKey);

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
                        get("/apikeys/"+createdAPIKey).then().
                        assertThat().statusCode(200).extract().body().asString();

                System.out.println(responseBody);

                PathFinder(responseBody);

                Assert.assertEquals(createdAPIName,js.getString("data.name"));


        }

        @Test(priority = 3)
        public void updateRoles(){

                String updatedApiKeyName= getRandomString("NewApiKeyName");



                JSONObject userJson = new JSONObject();
                userJson.put("name",updatedApiKeyName);
                userJson.put("scope","authOnly");


                String responseBody=given().
                        header("apiKey",mainApiKey).
                        header("Accept","application/json").
                        header("Content-Type","application/json").
                        body(userJson.toString(1)).
                        when().
                        put("/apikeys/"+createdAPIKey).then().
                        assertThat().statusCode(200).extract().body().asString();

                System.out.println(responseBody);

                PathFinder(responseBody);

                Assert.assertEquals(updatedApiKeyName,js.getString("data.name"));




        }

        @Test(priority = 4)
        public void deleteRoles()

        {
                        given().
                        header("apiKey","11e05c7614500a86adb556ad94bcab118afb202d").
                        header("Accept","application/json").
                        when().
                        delete("/apikeys/"+createdAPIKey).
                        then().assertThat().statusCode(200);



        }
}

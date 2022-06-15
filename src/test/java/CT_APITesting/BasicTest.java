package CT_APITesting;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

import io.restassured.RestAssured;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.HashMap;

public class BasicTest extends BaseTest {

        public HashMap<String,Object> userJson;

//        String Email,AccessToken;
//        ArrayList<String> emailValues = new ArrayList<String>();
//        int EmailCounts;
        String createdAPIKey;

        @Test(priority = 0)
        public void userDetails(){


                RestAssured.given().
                        pathParam("appId","")

                String responseDetails = given().queryParam("page","2").header("Content-Type","application/json")
                        .when().get("api/users")
                        .then().assertThat().statusCode(200).body("page",equalTo(2)).extract().body().asString();

                PathFinder(responseDetails);
//                Email = js.getString("data.email[0]");
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
        public void login(){
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
}

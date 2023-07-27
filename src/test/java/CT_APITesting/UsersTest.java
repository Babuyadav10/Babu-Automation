package CT_APITesting;
import io.restassured.path.json.JsonPath;
import io.restassured.specification.RequestSpecification;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class UsersTest extends BaseTest {
   public static String createdUserId;
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
      //--------------------------------createUser-----------------------------

    @Test(description = "Verity create users functionality")
    public void createUser(){
        String userID=getRandomString("userID");
        String username= getRandomString("UserName");

        JSONObject userJson = new JSONObject();
        userJson.put("uid",userID);
        userJson.put("name",username);

        String responseBody=getCommon().
              //  header("apiKey",mainApiKey).header("Content-Type","application/json").header("Accept","application/json").
                body(userJson.toString(1)).
                when().
                post("/users").
                then().
                assertThat().statusCode(200).extract().body().asString();
        System.out.println(responseBody);
        PathFinder(responseBody);
        createdUserId = js.getString("data.uid");

    }

    @Test(description = "Verity create users uid already taken functionality",dependsOnMethods = {"createUser"})
    public void createUserAlreadyTaken() {
        String username = getRandomString("UserName");

        JSONObject userJson = new JSONObject();
        userJson.put("uid", createdUserId);
        userJson.put("name", username);

        String responseBody = getCommon().
                body(userJson.toString(1)).
                when().
                post("/users").
                then().
                assertThat().statusCode(400).extract().body().asString();

        System.out.println(responseBody);
        PathFinder(responseBody);
        String message =js.getString("error.message");
        Assert.assertEquals(js.getString("error.message"),message);
    }
    @Test(description = "Verity create users uid empty functionality",dependsOnMethods = {"createUser"})
    public void createUserUidEmpty() {
        String username = getRandomString("UserName");

        JSONObject userJson = new JSONObject();
        userJson.put("uid", "");
        userJson.put("name", username);

        String responseBody = getCommon().
                body(userJson.toString(1)).
                when().
                post("/users").
                then().
                assertThat().statusCode(400).extract().body().asString();
        System.out.println(responseBody);
        PathFinder(responseBody);
        Assert.assertEquals(js.getString("error.details.uid[0]"),"The uid field is required.");

    }
    @Test(description = "Verity create users name empty functionality",dependsOnMethods = {"createUser"})
    public void createUserNameEmpty() {
        String userID=getRandomString("userID");

        JSONObject userJson = new JSONObject();
        userJson.put("uid", userID);
        userJson.put("name", "");

        String responseBody = getCommon().
                body(userJson.toString(1)).
                when().
                post("/users").
                then().
                assertThat().statusCode(400).extract().body().asString();
        System.out.println(responseBody);
        PathFinder(responseBody);
        Assert.assertEquals(js.getString("error.details.name[0]"),"The name field is required.");

    }

    //--------------------------------listUsers---------------------------------

    @Test(description = "Verify List users functionality",dependsOnMethods = {"createUser"})
    public void listUsers()
    {
        String responseBody=getCommon().
                when().
                get("/users").
                then().
                assertThat().statusCode(200).extract().body().asString();
        System.out.println(responseBody);

    }
    @Test(description = "Verify List users invalid endpoints functionality",dependsOnMethods = {"createUser"})
    public void listUsersInvalidEndPoint()
    {
        String responseBody=getCommon().
                when().
                get("/userss").
                then().
                assertThat().statusCode(404).extract().body().asString();
        System.out.println(responseBody);
        PathFinder(responseBody);
        Assert.assertEquals(js.getString("error.message"),"The API endpoint is invalid. Please verify the API call.");
    }

    //--------------------------------getUsers---------------------------------

    @Test(description = "Verify get users functionality",dependsOnMethods = {"listUsers"})
    public void getUsers()
    {
        String responseBody=getCommon().
                get("/users/"+createdUserId).
                then().
                assertThat().statusCode(200).extract().body().asString();
        System.out.println(responseBody);
        PathFinder(responseBody);
        Assert.assertEquals(createdUserId,js.getString("data.uid"));
    }
    @Test(description = "Verify get users invalid uid functionality",dependsOnMethods = {"listUsers"})
    public void getUsersInvalid()
    {
        String responseBody=getCommon().
                pathParam("uid", "babuInvalid").
                get("/users/{uid}").
                then().
                assertThat().statusCode(404).extract().body().asString();
        System.out.println(responseBody);
        PathFinder(responseBody);
        Assert.assertEquals(js.getString("error.message"),"The UID babuinvalid does not exist, please make sure you have created a user with UID babuinvalid.");
    }
    @Test(description = "Verify get users empty uid functionality",dependsOnMethods = {"listUsers"})
    public void getUsersEmptyUid()
    {
        String responseBody=getCommon().
                get("/users/{uid}"," ").
                then().
                assertThat().statusCode(404).extract().body().asString();
        System.out.println(responseBody);

        JsonPath bs = new JsonPath(responseBody);
         Assert.assertEquals(bs.getString("error.message"),"The UID %20 does not exist, please make sure you have created a user with UID %20.");
    }

    //--------------------------------deactivateUsers-----------------------------------

    @Test(description = "Verify deactivate users functionality",dependsOnMethods = {"getUsersEmptyUid"})
    public void deactivateUsers()
    {
        JSONObject userJson = new JSONObject();
        userJson.put("uidsToDeactivate",new String[]{createdUserId,"InvalidUser"});
        String responseBody=given().
                header("apiKey",apiKey).header("Accept","application/json").header("Content-Type","application/json").
              //  body("{\"uidsToDeactivate\": [\""+createdUserId+"\"]}").
                body(userJson.toString(1)).
                when().
                delete("/users").
                then().assertThat().statusCode(200).extract().body().asString();
        System.out.println(responseBody);

    }

    @Test(description = "Verify deactivate users already functionality",dependsOnMethods = {"deactivateUsers"})
    public void deactivateUsersAlready()
    {
        JSONObject userJson = new JSONObject();
        userJson.put("uidsToDeactivate",new String[]{createdUserId,"InvalidUserUID"});
        String responseBody=getCommon().
                body(userJson.toString(1)).
                when().
                delete("/users").
                then().assertThat().statusCode(200).extract().body().asString();
        System.out.println(responseBody);

        JsonPath bs = new JsonPath(responseBody);
        Assert.assertEquals(bs.getString("data.alreadyDeactivatedUids[0]"),createdUserId);
    }

    @Test(description = "Verify deactivate invalid users functionality",dependsOnMethods = {"deactivateUsers"})
    public void deactivateInvalidUsers()
    {
        JSONObject userJson = new JSONObject();
        userJson.put("uidsToDeactivate",new String[]{"InvalidUserUID"});
        String responseBody=getCommon().
                body(userJson.toString(1)).
                when().
                delete("/users").
                then().assertThat().statusCode(200).extract().body().asString();

        System.out.println(responseBody);
        JsonPath bs = new JsonPath(responseBody);
        Assert.assertEquals(bs.getString("data.notFound[0]"),"InvalidUserUID");
    }

    //--------------------------------reactivateUser-----------------------------------

    @Test(description = "Verify reactivate users Deactivated UID functionality",dependsOnMethods = {"deactivateInvalidUsers"})
    public void reactivateUsersDeactivatedUID(){

        String responseBody=given().
                header("apiKey",apiKey).header("Accept","application/json").header("Content-Type","application/json").
                body("{\"uidsToActivate\": [\""+createdUserId+"\"]}").
                when().
                put("/users").
                then().assertThat().statusCode(200).extract().body().asString();
        System.out.println(responseBody);

        PathFinder(responseBody);
        Assert.assertEquals(js.getString("data.reactivatedUids[0]"),createdUserId);

    }


    @Test(description = "Verify reactivate users Non Deactivated UID functionality",dependsOnMethods = {"deactivateInvalidUsers"})
    public void reactivateUsersNonDeactivatedUID(){

        String responseBody=getCommon().
                body("{\"uidsToActivate\": [\""+createdUserId+"\"]}").
                when().
                put("/users").
                then().assertThat().statusCode(200).extract().body().asString();
        System.out.println(responseBody);

    }

    @Test(description = "Verify reactivate users Invalid UID functionality",dependsOnMethods = {"deactivateInvalidUsers"})
    public void reactivateUsesInvalidUID(){

        String responseBody=getCommon().
                body("{\"uidsToActivate\": [\"InvalidUID\"]}").
                when().
                put("/users").
                then().assertThat().statusCode(200).extract().body().asString();
        System.out.println(responseBody);

        PathFinder(responseBody);
         Assert.assertEquals(js.getString("data"),"[notFound:[InvalidUID]]");
    }

    //--------------------------------updateUser-----------------------------------


    @Test(description = "Verify update user functionality",dependsOnMethods = {"reactivateUsesInvalidUID"})
    public void updateUser(){

        String newUpdatedName= getRandomString("updatedUser");

        JSONObject userJson = new JSONObject();
        userJson.put("name",newUpdatedName);
        userJson.put("avatar","https://data-us.cometchat.io/227263ed046613af/avatars/cbdbdb676a84.png");
        userJson.put("link","https://data-us.cometchat.io/227263ed046613af/avatars/cbdbdb676a84.png");
        userJson.put("role","default");
        userJson.put("metadata","{\"email\":\"user@email.com\", \"contactNumber\":\"0123456789\"}");
        userJson.put("tags",new String[]{"tag1","tag2"});

        String responseBody=getCommon().
                body(userJson.toString(1)).
                when().
                put("/users/"+createdUserId).
                then().
                assertThat().statusCode(200).extract().body().asString();
        System.out.println(responseBody);

     PathFinder(responseBody);
     Assert.assertEquals(js.getString("data.name"),newUpdatedName);

    }

    @Test(description = "Verify update user invalid url functionality",dependsOnMethods = {"reactivateUsesInvalidUID"})
    public void updateUserInvalidAvatar(){

        String newUpdatedName= getRandomString("updatedUser");

        JSONObject userJson = new JSONObject();
        userJson.put("name",newUpdatedName);
        userJson.put("avatar","Invalid:https://data-us.cometchat.io/227263ed046613af/avatars/cbdbdb676a84.png");
        userJson.put("link","https://data-us.cometchat.io/227263ed046613af/avatars/cbdbdb676a84.png");
        userJson.put("role","default");
        userJson.put("metadata","{\"email\":\"user@email.com\", \"contactNumber\":\"0123456789\"}");
        userJson.put("tags",new String[]{"qa tag1","qa tag2"});

        String responseBody=getCommon().
                body(userJson.toString(1)).
                when().
                put("/users/"+createdUserId).
                then().
                assertThat().statusCode(400).extract().body().asString();
        System.out.println(responseBody);

        PathFinder(responseBody);
        Assert.assertEquals(js.getString("error.details.avatar[0]"),"The avatar must be a valid URL.");
    }

    @Test(description = "Verify update user empty name functionality",dependsOnMethods = {"reactivateUsesInvalidUID"})
    public void updateUserEmptyName(){

        JSONObject userJson = new JSONObject();
        userJson.put("name","");
        userJson.put("avatar","https://data-us.cometchat.io/227263ed046613af/avatars/cbdbdb676a84.png");
        userJson.put("link","https://data-us.cometchat.io/227263ed046613af/avatars/cbdbdb676a84.png");
        userJson.put("role","default");
        userJson.put("metadata","{\"email\":\"user@email.com\", \"contactNumber\":\"0123456789\"}");
        userJson.put("tags",new String[]{"qa tag1","qa tag2"});

        String responseBody=getCommon().
                body(userJson.toString(1)).
                when().
                put("/users/"+createdUserId).
                then().
                assertThat().statusCode(400).extract().body().asString();

        System.out.println(responseBody);

        PathFinder(responseBody);
        Assert.assertEquals(js.getString("error.details.name[0]"),"The name field must have a value.");
    }


    @Test(description = "Verify update user empty tags functionality",dependsOnMethods = {"reactivateUsesInvalidUID"})
    public void updateUserEmptyTags(){

        String newUpdatedName= getRandomString("updateUser");

        JSONObject userJson = new JSONObject();
        userJson.put("name",newUpdatedName);
        userJson.put("avatar","https://data-us.cometchat.io/227263ed046613af/avatars/cbdbdb676a84.png");
        userJson.put("link","https://data-us.cometchat.io/227263ed046613af/avatars/cbdbdb676a84.png");
        userJson.put("role","default");
        userJson.put("metadata","{\"email\":\"user@email.com\", \"contactNumber\":\"0123456789\"}");
        userJson.put("tags",new String[]{" "});

        String responseBody=getCommon().
                body(userJson.toString(1)).
                when().
                put("/users/"+createdUserId).
                then().
                assertThat().statusCode(400).extract().body().asString();
        System.out.println(responseBody);

        PathFinder(responseBody);
        Assert.assertEquals(js.getString("error.details[\"tags.0\"][0]"), "The tags.0 field is required.");
    }


    //-----------------------blockUsers---------------------------------------------

    @Test(description = "Verify block users functionality",dependsOnMethods = {"updateUserEmptyTags"})
    public void blockUsers()
    {
        JSONObject userJson = new JSONObject();
        userJson.put("blockedUids",new String[]{createdUserId});

        String responseBody=getCommon().
                pathParam("uid","superhero1").
                body(userJson.toString(1)).
                when().
                post("/users/{uid}/blockedusers").
                then().
               assertThat().statusCode(200).extract().body().asString();
        System.out.println(responseBody);

    }

    @Test(description = "Verify block users invalid UID path params",dependsOnMethods = {"updateUserEmptyTags"})
    public void blockUsersInvalidUIDPathParams()
    {

        JSONObject userJson = new JSONObject();
        userJson.put("blockedUids",new String[]{"superhero1"});

        String responseBody=getCommon().
                pathParam("uid","superhero1000").
                body(userJson.toString(1)).
                when().
                post("/users/{uid}/blockedusers").
                then().
                assertThat().statusCode(404).extract().body().asString();
        System.out.println(responseBody);

    }


    @Test(description = "Verify block users invalid UID in body",dependsOnMethods = {"updateUserEmptyTags"})
    public void blockUsersInvalidUID()
    {

        JSONObject userJson = new JSONObject();
        userJson.put("blockedUids",new String[]{"InvalidUIDForBlockUSER"});

        String responseBody=getCommon().
                pathParam("uid","superhero1").
                body(userJson.toString(1)).
                when().
                post("/users/{uid}/blockedusers").
                then().
                assertThat().statusCode(200).extract().body().asString();
        System.out.println(responseBody);

    }

    @Test(description = "Verify block users empty UID in body",dependsOnMethods = {"updateUserEmptyTags"})
    public void blockUsersEmptyUID()
    {
        JSONObject userJson = new JSONObject();
        userJson.put("blockedUids",new String[]{" "});

        String responseBody=getCommon().
                pathParam("uid","superhero1").
                body(userJson.toString(1)).
                when().
                post("/users/{uid}/blockedusers").
                then().
                assertThat().statusCode(400).extract().body().asString();
        System.out.println(responseBody);

    }

    //-----------------------listOfBlockedUsers---------------------------------------------

    @Test(description = "Verify list of blocked users ",dependsOnMethods = {"blockUsersEmptyUID"})
    public void listOfBlockedUsers()
    {
        String responseBody=getCommon().
                pathParam("uid","superhero1").
                when().
                get("/users/{uid}/blockedusers").
                then().
                assertThat().statusCode(200).extract().body().asString();
        System.out.println(responseBody);

    }

    @Test(description = "Verify list of blocked users don't block ",dependsOnMethods = {"blockUsersEmptyUID"})
    public void listOfBlockedUsersDoNotBlock()
    {
        String responseBody=getCommon().
                pathParam("uid","superhero2").
                when().
                get("/users/{uid}/blockedusers").
                then().
                assertThat().statusCode(200).extract().body().asString();
        System.out.println(responseBody);

    }

    @Test(description = "Verify list of blocked users invalid uid",dependsOnMethods = {"blockUsersEmptyUID"})
    public void listOfBlockedUsersInvalidUID()
    {
        String responseBody=getCommon().
                pathParam("uid","superhero1000").
                when().
                get("/users/{uid}/blockedusers").
                then().
                assertThat().statusCode(404).extract().body().asString();
        System.out.println(responseBody);

    }

    //------------------------------unBlockUsers---------------------------------


    @Test(description = "Verify unblock users ",dependsOnMethods = {"listOfBlockedUsersInvalidUID"})
    public void unBlockUsers()
    {
        String responseBody=getCommon().
                pathParam("uid","superhero1").
              //  body("{\"blockedUids\": [\""+js.getString("data[2].uid")+"\"]}").
              //  body("{\"blockedUids\": [\""+blockedUIDForUnblock+"\"]}").

                body("{\"blockedUids\": [\""+createdUserId+"\"]}").
                when().
                delete("/users/{uid}/blockedusers").
                then().
                assertThat().statusCode(200).extract().body().asString();
        System.out.println(responseBody);

    }


    @Test(description = "Verify unblock users for unblock uid",dependsOnMethods = {"listOfBlockedUsersInvalidUID"})
    public void unBlockUsersForUnBlockedUID()
    {
        String responseBody=getCommon().
                pathParam("uid","superhero1").
                body("{\"blockedUids\": [\"superhero2\"]}").
                when().
                delete("/users/{uid}/blockedusers").
                then().
                assertThat().statusCode(200).extract().body().asString();
        System.out.println(responseBody);

    }


    @Test(description = "Verify unblock users invalid uid in path params ",dependsOnMethods = {"listOfBlockedUsersInvalidUID"})
    public void unBlockUsersInvalidUIDPath()
    {
        String responseBody=getCommon().
                pathParam("uid","superhero1000").
                //  body("{\"blockedUids\": [\""+js.getString("data[2].uid")+"\"]}").
                body("{\"blockedUids\": [\"superhero4\"]}").
                when().
                delete("/users/{uid}/blockedusers").
                then().
                assertThat().statusCode(404).extract().body().asString();
        System.out.println(responseBody);

    }


    //--------------------------------deleteUsers-----------------------------------

    @Test(description = "Verify delete users functionality", dependsOnMethods = {"unBlockUsersInvalidUIDPath"})
    public void deleteUsers() {
        JSONObject userJson = new JSONObject();
        userJson.put("permanent",false);

        String responseBody=getCommon().
                body(userJson.toString(1)).
                when().
                delete("/users/"+createdUserId).
                then().
                assertThat().statusCode(200).extract().body().asString();
        System.out.println(responseBody);

        PathFinder(responseBody);
        Assert.assertEquals(js.getString("data.deactivatedUids[0]"), createdUserId);

    }

    @Test(description = "Verify delete users functionality", dependsOnMethods = {"unBlockUsersInvalidUIDPath"})
    public void deleteUsersTrue() {
        JSONObject userJson = new JSONObject();
        userJson.put("permanent",true);

        String responseBody=getCommon().
                body(userJson.toString(1)).
                when().
                delete("/users/"+createdUserId).
                then().
                assertThat().statusCode(200).extract().body().asString();
        System.out.println(responseBody);

    }

    @Test(description = "Verify delete users invalid uid", dependsOnMethods = {"unBlockUsersInvalidUIDPath"})
    public void deleteUsersInValid() {
        JSONObject userJson = new JSONObject();
        userJson.put("permanent",true);

        String responseBody=getCommon().
                when().
                body(userJson.toString(1)).
                delete("/users/{uid}","InvalidUIDBabu").
                then().
                assertThat().statusCode(404).extract().body().asString();
        System.out.println(responseBody);
        PathFinder(responseBody);
        Assert.assertEquals(js.getString("error.message"), "The UID invaliduidbabu does not exist, please make sure you have created a user with UID invaliduidbabu.");
    }
    @Test(description = "Verify delete users functionality", dependsOnMethods = {"unBlockUsersInvalidUIDPath"})
    public void deleteUsersEmpty() {
        String responseBody=getCommon().
                when().
                delete("/users/{uid}"," ").
                then().
                assertThat().statusCode(404).extract().body().asString();
        System.out.println(responseBody);
        PathFinder(responseBody);
        Assert.assertEquals(js.getString("error.message"), "The UID %20 does not exist, please make sure you have created a user with UID %20.");
    }



}

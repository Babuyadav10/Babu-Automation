package CT_APITesting;
import io.restassured.path.json.JsonPath;
import io.restassured.specification.RequestSpecification;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class UsersTest extends BaseTest {
   public static String createdUserId;
   public String deactivatedUID;
    String createdUserName;
     String UIDForBlockUser;

    String blockedUIDForUnblock;
    String s1="superhero1"; // This user will block or unblock to other Users
    String apiKey = GlobalClassTest.prop.getProperty("apiKey");
   String responseBody;

    int ptc=0;
    public RequestSpecification getCommon()
    {
        RequestSpecification httpRequestObject;
        httpRequestObject = given();
        httpRequestObject.header("apiKey", apiKey);
        httpRequestObject.header("Content-Type", "application/json");
        httpRequestObject.header("Accept", "application/json");
        return httpRequestObject;
    }
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
        System.out.println("responseBody : " +responseBody);
        PathFinder(responseBody);
        createdUserId = js.getString("data.uid");
        createdUserName=js.getString("data.name");


    //    GroupTest.userNameId = createdUserId;



//        if(ptc!=-1)
//        System.out.println("responseBody : " +responseBody);
//
//        PathFinder(responseBody);
//        createdUserId = js.getString("data.uid");
//        createdUserName=js.getString("data.name");
//
//        if(ptc!=-1)
//        System.out.println(createdUserId);
//
//        MemberTest.UName=createdUserId;
//
//        FriendsTest.uName=createdUserId;
//
//        MessageTest.UName=createdUserId;
//
//     //   ReactionTest.userId=createdUserId;

    }
    @Test(description = "Verity create users uid already taken functionality", dependsOnMethods = {"createUser"})
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

        System.out.println("responseBody : " + responseBody);
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
        System.out.println("responseBody : " + responseBody);
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
        System.out.println("responseBody : " + responseBody);
        PathFinder(responseBody);
        Assert.assertEquals(js.getString("error.details.name[0]"),"The name field is required.");
    }
    @Test(description = "Verify List users functionality",dependsOnMethods = {"createUser"})
    public void listUsers()
    {
        String responseBody=getCommon().
                when().
                get("/users").
                then().
                assertThat().statusCode(200).extract().body().asString();

        System.out.println(responseBody);
       // saveResponseBodyClassLevel(responseBody);

        PathFinder(responseBody);
        Assert.assertEquals(js.getString("data[0].uid"),createdUserId);
        UIDForBlockUser= js.getString("data[0].uid");
        System.out.println("UIDForBlockUser :" +UIDForBlockUser);
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
    @Test(description = "Verify deactivate users functionality",dependsOnMethods = {"listUsers"})
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
        JsonPath bs = new JsonPath(responseBody);
         deactivatedUID=bs.getString("data.deactivatedUids[0]");
        System.out.println("deactivatedUID" +deactivatedUID);
        Assert.assertEquals(bs.getString("data.deactivatedUids[0]"),createdUserId);


//        PathFinder(responseBody);
//        String str =js.getString("data.deactivatedUids");
//
//        Assert.assertEquals(str.substring(1,str.length() - 1),createdUserId);
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

    @Test(description = "Verify reactivate users Deactivated UID functionality",dependsOnMethods = {"deactivateUsers"})
    public void reactivateUsersDeactivatedUID(){

        String responseBody=given().
                header("apiKey",apiKey).header("Accept","application/json").header("Content-Type","application/json").
                body("{\"uidsToActivate\": [\""+createdUserId+"\"]}").
                when().
                put("/users").
                then().assertThat().statusCode(200).extract().body().asString();

        System.out.println(responseBody);

        PathFinder(responseBody);
        Assert.assertEquals(js.getString("data.reactivatedUids[0]"),deactivatedUID);

      //  String str =js.getString("data.reactivatedUids");
     //   Assert.assertEquals(str.substring(1,str.length() - 1),createdUserId);
    }
    @Test(description = "Verify reactivate users Non Deactivated UID functionality",dependsOnMethods = {"getUsers"})
    public void reactivateUsersNonDeactivatedUID(){

        String responseBody=getCommon().
                body("{\"uidsToActivate\": [\""+createdUserId+"\"]}").
                when().
                put("/users").
                then().assertThat().statusCode(200).extract().body().asString();

        System.out.println(responseBody);

        PathFinder(responseBody);
       // Assert.assertEquals(js.getString("data.reactivatedUids[0]"),deactivatedUID);
    }
    @Test(description = "Verify reactivate users Invalid UID functionality",dependsOnMethods = {"deactivateUsers"})
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

    @Test(description = "Verify update roles functionality",dependsOnMethods = {"reactivateUsersDeactivatedUID"})
    public void updateUser(){

        String newUpdatedName= getRandomString("newUpdatedName");

        JSONObject userJson = new JSONObject();
        userJson.put("name",newUpdatedName);
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
                assertThat().statusCode(200).extract().body().asString();

        System.out.println(responseBody);

     PathFinder(responseBody);
     Assert.assertEquals(js.getString("data.name"),newUpdatedName);
    }
    @Test(description = "Verify update roles functionality",dependsOnMethods = {"reactivateUsersDeactivatedUID"})
    public void updateUserInvalidAvatar(){

        String newUpdatedName= getRandomString("newUpdatedName");

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
    @Test(description = "Verify update roles functionality",dependsOnMethods = {"reactivateUsersDeactivatedUID"})
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
    @Test(description = "Verify update roles functionality",dependsOnMethods = {"reactivateUsersDeactivatedUID"})
    public void updateUserEmptyTags(){

        String newUpdatedName= getRandomString("newUpdatedName");

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
    @Test(description = "Verify delete users functionality", dependsOnMethods = {"unBlockUsers"})
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
//    @Test(description = "Verify delete users functionality",dependsOnMethods = {"unBlockUsers"})
//    public void deleteUsersPermanent() {
//        JSONObject userJson = new JSONObject();
//        userJson.put("permanent",true);
//
//        String responseBody=getCommon().
//                when().
//                body(userJson.toString(1)).
//                delete("/users/"+createdUserId).
//                then().
//                assertThat().statusCode(200).extract().body().asString();
//        System.out.println(responseBody);
//        PathFinder(responseBody);
//        Assert.assertEquals(js.getString("data.success"), "true");
//    }
    @Test(description = "Verify delete users functionality", dependsOnMethods = {"unBlockUsers"})
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
    @Test(description = "Verify delete users functionality", dependsOnMethods = {"unBlockUsers"})
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

    @Test(description = "Verify block users functionality",dependsOnMethods = {"updateUser"})
    public void blockUsers()
    {
//        PathFinder(responseBody);
//
//        String responseBody=given().
//                header("apiKey",apiKey).
//                header("Content-Type","application/json").
//                header("Accept","application/json").
//                body("{\"blockedUids\": [\""+js.getString("data[2].uid")+"\"]}").
//                when().
//                post("/users/"+js.getString("data[3].uid")+"/blockedusers").
//                then().
//                assertThat().statusCode(200).extract().body().asString();

        PathFinder(responseBody);
        JSONObject userJson = new JSONObject();
        userJson.put("blockedUids",new String[]{createdUserId});

        String responseBody=getCommon().
                body(userJson.toString(1)).
                when().
                post("/users/"+s1+"/blockedusers").
                then().
               assertThat().statusCode(200).extract().body().asString();
        System.out.println(responseBody);

    }
    @Test(description = "Verify block users invalid UID functionality",dependsOnMethods = {"updateUser"})
    public void blockUsersInvalidUIDPathParams()
    {
        PathFinder(responseBody);
        JSONObject userJson = new JSONObject();
        userJson.put("blockedUids",new String[]{"superhero2"});

        String responseBody=getCommon().
                body(userJson.toString(1)).
                when().
                post("/users/"+"superhero10"+"/blockedusers").
                then().
                assertThat().statusCode(404).extract().body().asString();
        System.out.println(responseBody);

    }
    @Test(description = "Verify block users invalid UID functionality",dependsOnMethods = {"updateUser"})
    public void blockUsersInvalidUID()
    {
        PathFinder(responseBody);
        JSONObject userJson = new JSONObject();
        userJson.put("blockedUids",new String[]{"InvalidUIDForBlockUSER"});

        String responseBody=getCommon().
                body(userJson.toString(1)).
                when().
                post("/users/"+s1+"/blockedusers").
                then().
                assertThat().statusCode(200).extract().body().asString();
        System.out.println(responseBody);

    }
    @Test(description = "Verify block users invalid UID functionality",dependsOnMethods = {"updateUser"})
    public void blockUsersEmptyUID()
    {
        PathFinder(responseBody);
        JSONObject userJson = new JSONObject();
        userJson.put("blockedUids",new String[]{" "});

        String responseBody=getCommon().
                body(userJson.toString(1)).
                when().
                post("/users/"+s1+"/blockedusers").
                then().
                assertThat().statusCode(200).extract().body().asString();
        System.out.println(responseBody);

    }
    @Test(description = "Verify list of blocked users ",dependsOnMethods = {"blockUsers"})
    public void listOfBlockedUsers()
    {
        String responseBody=getCommon().
                when().
                get("/users/"+s1+"/blockedusers").
                then().
                assertThat().statusCode(200).extract().body().asString();
        System.out.println(responseBody);
        PathFinder(responseBody);
         blockedUIDForUnblock = js.getString("data[0]");
    }

    @Test(description = "Verify list of blocked users ",dependsOnMethods = {"blockUsers"})
    public void listOfBlockedUsersDoNotBlock()
    {
        String responseBody=getCommon().
                when().
                get("/users/"+"superhero3"+"/blockedusers").
                then().
                assertThat().statusCode(200).extract().body().asString();
        System.out.println(responseBody);
        PathFinder(responseBody);

    }
    @Test(description = "Verify list of blocked users ",dependsOnMethods = {"blockUsers"})
    public void listOfBlockedUsersInvalidUID()
    {
        String responseBody=getCommon().
                when().
                get("/users/"+"superhero10"+"/blockedusers").
                then().
                assertThat().statusCode(404).extract().body().asString();
        System.out.println(responseBody);
        PathFinder(responseBody);

    }
    @Test(description = "Verify unblock users ",dependsOnMethods = {"listOfBlockedUsers"})
    public void unBlockUsers()
    {
        String responseBody=getCommon().
              //  body("{\"blockedUids\": [\""+js.getString("data[2].uid")+"\"]}").
                      body("{\"blockedUids\": [\""+blockedUIDForUnblock+"\"]}").
                when().
                delete("/users/"+s1+"/blockedusers").
                then().
                assertThat().statusCode(200).extract().body().asString();
        System.out.println(responseBody);
        PathFinder(responseBody);
    }
    @Test(description = "Verify unblock users ",dependsOnMethods = {"listOfBlockedUsers"})
    public void unBlockUsersForUnBlockedUID()
    {
        String responseBody=getCommon().
                //  body("{\"blockedUids\": [\""+js.getString("data[2].uid")+"\"]}").
                        body("{\"blockedUids\": [\"superhero4\"]}").
                when().
                delete("/users/"+s1+"/blockedusers").
                then().
                assertThat().statusCode(200).extract().body().asString();
        System.out.println(responseBody);
        PathFinder(responseBody);
    }
    @Test(description = "Verify unblock users ",dependsOnMethods = {"listOfBlockedUsers"})
    public void unBlockUsersInvalidUIDPath()
    {
        String responseBody=getCommon().
                //  body("{\"blockedUids\": [\""+js.getString("data[2].uid")+"\"]}").
                        body("{\"blockedUids\": [\"superhero4\"]}").
                when().
                delete("/users/"+"superhero11"+"/blockedusers").
                then().
                assertThat().statusCode(404).extract().body().asString();
        System.out.println(responseBody);
        PathFinder(responseBody);
    }

}

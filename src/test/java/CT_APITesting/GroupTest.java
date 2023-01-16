package CT_APITesting;

import com.sun.org.apache.xpath.internal.objects.XString;
import io.restassured.specification.RequestSpecification;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.get;
import static io.restassured.RestAssured.given;

public class GroupTest extends BaseTest {
   public String createdGuidPassword;
   String createdGroupNamePassword;
    String createdGroupTypeName;
   String  createdGroupTypePassword;
 public static String  createdGuidPublic;
    String  createdGroupNamePublic;
    String createdGroupTypePublic;

    String   createdGuidPrivate;
    String createdGroupNamePrivate;
    String createdGroupTypePrivate;
  public static String userNameId;
    String createdGroupType[] = {"public","private","password"};
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
    public  void printGetResponse(String responseBody)
    {
        System.out.println(responseBody);
        PathFinder(responseBody);
    }
/*
    @Test(description = "Verify create group functionality")
    public void createGroups() {

        for (int i = 0; i < createdGroupType.length; i++) {
            String guid = getRandomString("guid");
            String groupName = getRandomString("groupName");
            String passwordForGroup = getRandomString("password");
            RequestSpecification httpRequestObject = getCommon();

            JSONObject userJson = new JSONObject();
            userJson.put("guid", guid);
            userJson.put("name", groupName);
            userJson.put("type", createdGroupType[i]);
            if (createdGroupType[i] == "password") {
                userJson.put("password", passwordForGroup);
            }
            userJson.put("description", "This group is created by automation scripts");

            httpRequestObject.header("onBehalfOf", "superhero1");

            httpRequestObject.body(userJson.toString());
            Response response = httpRequestObject.request(Method.POST, "/groups");

            printGetResponse(response, 200);
        }
    }

//        String responseBody = given().
//                header("apiKey", apiKey).header("onBehalfOf","superhero1").header("Content-Type", "application/json").header("Accept", "application/json").
//                body(userJson.toString(1)).
//                when().
//                post("/groups").
//                then().
//                assertThat().statusCode(200).extract().body().asString();
//        if (intc !=-1)
//        System.out.println(responseBody);
//        PathFinder(responseBody);
//        createdGuid = js.getString("data.guid");
//        createdGroupName = js.getString("data.name");
//        createdGroupType = js.getString("data.type");
//    if (intc !=-1)
//        System.out.println(createdGuid);
//
//        MemberTest.gName=createdGuid;
//
//        MessageTest.gName=createdGuid;




   /*

 */
    @Test(description = "Verify create group functionality")
    public void createGroups() {

        UsersTest a= new UsersTest();
        a.createUser();
        userNameId= UsersTest.createdUserId;

        System.out.println("userNameId :"+userNameId);

        for (int i = 0; i < createdGroupType.length; i++) {
            String guid = getRandomString("guid");
            String groupName = getRandomString("groupName");
            String passwordForGroup = getRandomString("password");

            JSONObject userJson = new JSONObject();
            userJson.put("guid", guid);
            userJson.put("name", groupName);
            userJson.put("type", createdGroupType[i]);
            if (createdGroupType[i] == "password") {
                userJson.put("password", passwordForGroup);
            }
            userJson.put("description", "This group is created by automation scripts GroupType :"+createdGroupType );

            JSONObject userJson2 = new JSONObject();
            userJson2.put("participants",new String[]{"superhero3"});
            userJson2.put("moderators",new String[]{"superhero2"});
            userJson2.put("admins",new String[]{"superhero1"});

            userJson.put("members", userJson2);
            userJson.put("tags", new String[]{"tag1","tag2"});

         String   responseBody = getCommon().
                    header("onBehalfOf", userNameId).
                    body(userJson.toString(1)).
                    when().
                      post("/groups").
                    then().
                    assertThat().statusCode(200).extract().body().asString();
         System.out.println(responseBody);
            PathFinder(responseBody);
            createdGroupTypeName = js.getString("data.type");

            if (createdGroupType[i] == "password") {
                System.out.println("createdGroupType password :" +createdGroupTypeName);

                createdGuidPassword = js.getString("data.guid");
                createdGroupNamePassword = js.getString("data.name");
                createdGroupTypePassword = js.getString("data.type");

            } else if (createdGroupType[i] == "private") {
                System.out.println("createdGroupType Private :" +createdGroupTypeName);
                createdGuidPrivate = js.getString("data.guid");
                createdGroupNamePrivate = js.getString("data.name");
                createdGroupTypePrivate = js.getString("data.type");
            }
            else {
                System.out.println("createdGroupType Public :" + createdGroupTypeName);
                createdGuidPublic = js.getString("data.guid");
                createdGroupNamePublic = js.getString("data.name");
                createdGroupTypePublic = js.getString("data.type");
            }
        }
    }
    @Test(description = "Verify list Groups",dependsOnMethods = {"createGroups"})
    public void listGroups() {
        String responseBody = getCommon().
                header("onBehalfOf", userNameId).
                when().
                get("/groups").
                then().
                assertThat().statusCode(200).extract().body().asString();

        System.out.println(responseBody);
        PathFinder(responseBody);
        Assert.assertEquals(js.getString("data[0].guid"), createdGuidPassword);
        Assert.assertEquals(js.getString("data[1].guid"), createdGuidPrivate);
        Assert.assertEquals(js.getString("data[2].guid"), createdGuidPublic);
    }

    @Test(description = "Verify list Groups",dependsOnMethods = {"listGroups"})
    public void listGroupsByType() {
        String responseBody = getCommon().
                header("onBehalfOf", userNameId).
                queryParam("type", "public").
                when().
                get("/groups").
                then().
                assertThat().statusCode(200).extract().body().asString();
        System.out.println(responseBody);
    }

    @Test(description = "Verify list Groups",dependsOnMethods = {"listGroups"})
    public void listGroupsTypePrivate() {
        String responseBody = getCommon().
                header("onBehalfOf", userNameId).
                queryParam("type", "private").
                when().
                get("/groups").
                then().
                assertThat().statusCode(200).extract().body().asString();
        System.out.println(responseBody);
    }

    @Test(description = "Verify list Groups",dependsOnMethods = {"listGroups"})
    public void listGroupsTypePassword() {
        String responseBody = getCommon().
                header("onBehalfOf", userNameId).
                queryParam("type", "password").
                when().
                get("/groups").
                then().
                assertThat().statusCode(200).extract().body().asString();
        System.out.println(responseBody);
    }

    @Test(description = "Verify list Groups",dependsOnMethods = {"listGroups"})
    public void listGroupsWithTagTrue() {
        String responseBody = getCommon().
                header("onBehalfOf", userNameId).
                queryParam("withTags", true).
                when().
                get("/groups").
                then().
                assertThat().statusCode(200).extract().body().asString();
        System.out.println(responseBody);
    }

    @Test(description = "Verify list Groups",dependsOnMethods = {"listGroups"})
    public void listGroupsWithTagFalse() {
        String responseBody = getCommon().
                header("onBehalfOf", userNameId).
                queryParam("withTags", false).
                when().
                get("/groups").
                then().
                assertThat().statusCode(200).extract().body().asString();
        System.out.println(responseBody);
    }

    @Test(description = "Verify list Groups",dependsOnMethods = {"listGroups"})
    public void listGroupsWithTagName() {
        String responseBody = getCommon().
                header("onBehalfOf", userNameId).
                queryParam("withTags", true).
                queryParam("tags", "tag1").
                when().
                get("/groups").
                then().
                assertThat().statusCode(200).extract().body().asString();
        System.out.println(responseBody);
    }

    @Test(description = "Verify list Groups",dependsOnMethods = {"listGroups"})
    public void listGroupsWithInvalidTag() {
        String responseBody = getCommon().
                header("onBehalfOf", userNameId).
                queryParam("withTags", true).
                queryParam("tags", "tag5").
                when().
                get("/groups").
                then().
                assertThat().statusCode(200).extract().body().asString();
        System.out.println(responseBody);
    }
    @Test(description = "Verify list Groups",dependsOnMethods = {"listGroups"})
    public void listGroupsSortOrderASC() {
        String responseBody = getCommon().
                header("onBehalfOf", userNameId).
                queryParam("sortBy", "name").
                queryParam("sortOrder", "asc").
                when().
                get("/groups").
                then().
                assertThat().statusCode(200).extract().body().asString();
        System.out.println(responseBody);
    }

    @Test(description = "Verify list Groups",dependsOnMethods = {"listGroups"})
    public void listGroupsSortOrderDESC() {
        String responseBody = getCommon().
                header("onBehalfOf", userNameId).
                queryParam("sortBy", "name").
                queryParam("sortOrder", "DESC").
                when().
                get("/groups").
                then().
                assertThat().statusCode(200).extract().body().asString();
        System.out.println(responseBody);
    }

    @Test(description = "Verify list Groups",dependsOnMethods = {"listGroups"})
    public void listGroupsSearchKey() {
        String responseBody = getCommon().
                header("onBehalfOf", userNameId).
                queryParam("searchKey", "babu").
                when().
                get("/groups").
                then().
                assertThat().statusCode(200).extract().body().asString();
        System.out.println(responseBody);
    }

    @Test(description = "verify get groups",dependsOnMethods = {"listGroups"})
    public void getGroupsPublic() {
        String responseBody =given().
                header("apiKey",apiKey).header("Accept","application/json").
                when().
                get("/groups/" + createdGuidPublic).
                then().
                assertThat().statusCode(200).extract().body().asString();
        System.out.println(responseBody);
        PathFinder(responseBody);
        System.out.println(createdGuidPublic);
        Assert.assertEquals(js.getString("data.guid"), createdGuidPublic);
    }

    @Test(description = "verify get groups",dependsOnMethods = {"listGroups"})
    public void getGroupsPrivate() {
        String responseBody =given().
                header("apiKey",apiKey).header("Accept","application/json").
                when().
                get("/groups/" + createdGuidPrivate).
                then(). log().body().
                assertThat().statusCode(200).extract().body().asString();
         printGetResponse( responseBody);
        Assert.assertEquals(js.getString("data.guid"), createdGuidPrivate);
    }

    @Test(description = "verify get groups",dependsOnMethods = {"listGroups"})
    public void getGroupsPassword() {
        String responseBody =given().
                header("apiKey",apiKey).header("Accept","application/json").
                when().
                get("/groups/" + createdGuidPassword).
                then(). log().body().
                assertThat().statusCode(200).extract().body().asString();
         printGetResponse( responseBody);
        Assert.assertEquals(js.getString("data.guid"), createdGuidPassword);
    }
    @Test(description = "verify get groups",dependsOnMethods = {"listGroups"})
    public void getGroupsInvalidEndPoint() {
        String responseBody =given().
                header("apiKey",apiKey).header("Accept","application/json").
                when().
                get("/groupss/" + createdGuidPublic).
                then(). log().body().
                assertThat().statusCode(404).extract().body().asString();
        System.out.println(responseBody);
       PathFinder(responseBody);
       Assert.assertEquals(js.getString("error.message"),"The API endpoint is invalid. Please verify the API call.");
    }

    @Test(description = "verify get groups")
    public void getGroupsInvalid() {
        String responseBody =given().
                header("apiKey",apiKey).header("Accept","application/json").
                when().
                get("/groups/{guid}","InvalidGUID").
                then(). log().body().
                assertThat().statusCode(404).extract().body().asString();
        System.out.println(responseBody);
    }

    @Test(description = "verify get groups")
    public void getGroupsEmpty() {
        String responseBody =given().
                header("apiKey",apiKey).header("Accept","application/json").
                when().
                get("/groups/{guid}"," ").
                then(). log().body().
                assertThat().statusCode(404).extract().body().asString();
        System.out.println(responseBody);
    }

    @Test(description = "Verify update Group functionality",dependsOnMethods = {"getGroupsPublic"})
    public void updateGroupsPublic() {
        String updatedGroupName = getRandomString("publicUpdatedGName");

        JSONObject userJson = new JSONObject();
        userJson.put("name", updatedGroupName);
        userJson.put("description", "test description of update group" + updatedGroupName);

        String responseBody = getCommon().
                body(userJson.toString(1)).
                when().
                put("/groups/" + createdGuidPublic).then().
                assertThat().statusCode(200).extract().body().asString();

        System.out.println(responseBody);
        PathFinder(responseBody);
        Assert.assertEquals(js.getString("data.name"),updatedGroupName);
    }

    @Test(description = "Verify update Group functionality",dependsOnMethods = {"updateGroupsPublic"})
    public void updateGroupsPublicToPrivate() {
        String updatedGroupName = getRandomString("updatedGUIDType");

        JSONObject userJson = new JSONObject();
        userJson.put("name", updatedGroupName);
        userJson.put("type", "private");
        userJson.put("description", "group type public is changed into private");

        String responseBody = getCommon().
                body(userJson.toString(1)).
                when().
                put("/groups/" + createdGuidPublic).then().log().body().
                assertThat().statusCode(200).extract().body().asString();
        System.out.println(responseBody);
    }

    @Test(description = "Verify update Group functionality",dependsOnMethods = {"updateGroupsPublicToPrivate"})
    public void updateGroupsPrivateToPassword() {
        String updatedGroupName = getRandomString("updatedGUIDType");

        JSONObject userJson = new JSONObject();
        userJson.put("name", updatedGroupName);
        userJson.put("type", "password");
        userJson.put("password","babu");
        userJson.put("description", "group type private is changed into password");

        String responseBody = getCommon().
                body(userJson.toString(1)).
                when().
                put("/groups/" + createdGuidPublic).then().
                assertThat().statusCode(200).extract().body().asString();

        System.out.println(responseBody);
    }

    @Test(description = "Verify update Group functionality",dependsOnMethods = {"getGroupsPrivate"})
    public void updateGroupsPrivate() {
        String updatedGroupName = getRandomString("privateUpdatedGName");

        JSONObject userJson = new JSONObject();
        userJson.put("name", updatedGroupName);
        userJson.put("description", "test description of update group" + updatedGroupName);

        String responseBody = getCommon().
                body(userJson.toString(1)).
                when().
                put("/groups/" + createdGuidPrivate).then().
                assertThat().statusCode(200).extract().body().asString();

        System.out.println(responseBody);
        PathFinder(responseBody);
        Assert.assertEquals(js.getString("data.name"),updatedGroupName);
    }

    @Test(description = "Verify update Group functionality",dependsOnMethods = {"updateGroupsPrivate"})
    public void updateGroupsPrivateToPublic() {
        String updatedGroupName = getRandomString("updatedGUIDType");

        JSONObject userJson = new JSONObject();
        userJson.put("name", updatedGroupName);
        userJson.put("type", "public");
        userJson.put("description", "group type private is changed into public");

        String responseBody = getCommon().
                body(userJson.toString(1)).
                when().
                put("/groups/" + createdGuidPrivate).then().log().body().
                assertThat().statusCode(200).extract().body().asString();

        System.out.println(responseBody);
    }


    @Test(description = "Verify update Group functionality",dependsOnMethods = {"updateGroupsPrivateToPublic"})
    public void updateGroupsPublicToPassword() {
        String updatedGroupName = getRandomString("updatedGUIDType");

        JSONObject userJson = new JSONObject();
        userJson.put("name", updatedGroupName);
        userJson.put("type", "password");
        userJson.put("password", " ");
        userJson.put("description", "group type public is changed into password");

        String responseBody = getCommon().
                body(userJson.toString(1)).
                when().
                put("/groups/" + createdGuidPrivate).then().
                assertThat().statusCode(200).extract().body().asString();

        System.out.println(responseBody);
        PathFinder(responseBody);
        Assert.assertEquals(js.getString("data.name"),updatedGroupName);
    }


    @Test(description = "Verify update Group functionality",dependsOnMethods = {"getGroupsPassword"})
    public void updateGroupsPassword() {
        String updatedGroupName = getRandomString("passwordUpdatedGName");

        JSONObject userJson = new JSONObject();
        userJson.put("name", updatedGroupName);
        userJson.put("description", "test description of update group" + updatedGroupName);

        String responseBody = getCommon().
                body(userJson.toString(1)).
                when().
                put("/groups/" + createdGuidPassword).then().
                assertThat().statusCode(200).extract().body().asString();

        System.out.println(responseBody);
        PathFinder(responseBody);
        Assert.assertEquals(js.getString("data.name"),updatedGroupName);
    }


    @Test(description = "Verify delete Groups functionality",dependsOnMethods = {"removeFriends"})
    public void deleteGroupsPublic() {
        String responseBody= given().
                header("apiKey", apiKey).
                header("Accept", "application/json").
                when().
                delete("/groups/" + createdGuidPublic).
                then().assertThat().statusCode(200).extract().body().asString();
        System.out.println(responseBody);
    }

    @Test(description = "Verify delete Groups functionality",dependsOnMethods = {"deleteGroupsPublic"})
    public void deleteGroupsPrivate() {
        String responseBody= given().
                header("apiKey", apiKey).
                header("Accept", "application/json").
                when().
                delete("/groups/" + createdGuidPrivate).
                then().assertThat().statusCode(200).extract().body().asString();;
        System.out.println(responseBody);
    }

    @Test(description = "Verify delete Groups functionality",dependsOnMethods = {"deleteGroupsPrivate"})
    public void deleteGroupsPassword() {
        String responseBody= given().
                header("apiKey", apiKey).
                header("Accept", "application/json").
                when().
                delete("/groups/" + createdGuidPassword).
                then().assertThat().statusCode(200).extract().body().asString();
        System.out.println(responseBody);
    }

    @Test(description = "Verify delete users functionality", dependsOnMethods = {"deleteGroupsPassword"})
    public void deleteUsers() {
        JSONObject userJson = new JSONObject();
        userJson.put("permanent",true);

        String responseBody=getCommon().
                body(userJson.toString(1)).
                when().
                delete("/users/"+userNameId).
                then().
                assertThat().statusCode(200).extract().body().asString();
        System.out.println(responseBody);
    }

    @Test(description = "verify banned user functionality", dependsOnMethods = {"updateGroupsPrivateToPassword"})
    public void banUser(){

        String responseBody= given().
                   header("onBehalfOf",GroupTest.userNameId).header("apiKey",apiKey).header("Accept","application/json").
                pathParam("guid",createdGuidPublic).
                pathParam("uid","superhero3").
                when().
                post("/groups/{guid}/bannedusers/{uid}").
                then().
                        assertThat().statusCode(200).extract().body().asString();
        System.out.println(responseBody);
    }

    @Test(description = "verify banned user invalid functionality", dependsOnMethods = {"updateGroupsPrivateToPassword"})
    public void banUserInvalid(){

        String responseBody= given().
                header("onBehalfOf",GroupTest.userNameId).header("apiKey",apiKey).header("Accept","application/json").
                pathParam("guid",createdGuidPublic).
                pathParam("uid","superhero30").
                when().
                post("/groups/{guid}/bannedusers/{uid}").
                then().
                assertThat().statusCode(404).extract().body().asString();
        System.out.println(responseBody);
    }

    @Test(description = "verify banned user invalid guid functionality", dependsOnMethods = {"updateGroupsPrivateToPassword"})
    public void banUserInvalidGUID(){

        String responseBody= given().
                header("onBehalfOf",GroupTest.userNameId).header("apiKey",apiKey).header("Accept","application/json").
                pathParam("guid","invalidGUId").
                pathParam("uid","superhero3").
                when().
                post("/groups/{guid}/bannedusers/{uid}").
                then().
                assertThat().statusCode(404).extract().body().asString();
        System.out.println(responseBody);
    }

    @Test(description = "verify banned user functionality", dependsOnMethods = {"updateGroupsPrivateToPassword"})
    public void banUserInvalidGuidUid(){

        String responseBody= given().
                header("onBehalfOf",GroupTest.userNameId).header("apiKey",apiKey).header("Accept","application/json").
                pathParam("guid","invalidGUID").
                pathParam("uid","InvalidUID").
                when().
                post("/groups/{guid}/bannedusers/{uid}").
                then().
                assertThat().statusCode(404).extract().body().asString();
        System.out.println(responseBody);
    }

    @Test(description = "verify banned user not member of group functionality", dependsOnMethods = {"updateGroupsPrivateToPassword"})
    public void banUserNotMemberGroup(){

        String responseBody= given().
                header("onBehalfOf",GroupTest.userNameId).header("apiKey",apiKey).header("Accept","application/json").
                pathParam("guid",createdGuidPublic).
                pathParam("uid","superhero4").
                when().
                post("/groups/{guid}/bannedusers/{uid}").
                then().
                assertThat().statusCode(404).extract().body().asString();
        System.out.println(responseBody);
    }


    @Test(description = "verify banned user list functionality", dependsOnMethods = {"banUser"})
    public void banUserList(){

        String responseBody= given().
                header("onBehalfOf",GroupTest.userNameId).header("apiKey",apiKey).header("Accept","application/json").
                pathParam("guid",createdGuidPublic).
                when().
                get("/groups/{guid}/bannedusers").
                then().
                assertThat().statusCode(200).extract().body().asString();
        System.out.println(responseBody);
    }

    @Test(description = "verify banned user list functionality", dependsOnMethods = {"banUser"})
    public void banUserListInvalidGuid(){

        String responseBody= given().
                header("onBehalfOf",GroupTest.userNameId).header("apiKey",apiKey).header("Accept","application/json").
                pathParam("guid","InvalidGuid").
                when().
                get("/groups/{guid}/bannedusers").
                then().
                assertThat().statusCode(404).extract().body().asString();
        System.out.println(responseBody);
    }



    @Test(description = "verify banned user functionality", dependsOnMethods = {"banUserList"})
    public void unBanUser(){

        String responseBody= given().
                header("onBehalfOf",GroupTest.userNameId).header("apiKey",apiKey).header("Accept","application/json").
                pathParam("guid",createdGuidPublic).
                pathParam("uid","superhero3").
                when().
                delete("/groups/{guid}/bannedusers/{uid}").
                then().
                assertThat().statusCode(200).extract().body().asString();
        System.out.println(responseBody);
    }


    @Test(description = "verify banned user functionality", dependsOnMethods = {"banUserList"})
    public void unBanUserNotOnBehalfOf(){

        String responseBody= given().
                header("apiKey",apiKey).header("Accept","application/json").
                pathParam("guid",createdGuidPublic).
                pathParam("uid","superhero3").
                when().
                delete("/groups/{guid}/bannedusers/{uid}").
                then().
                assertThat().statusCode(404).extract().body().asString();
        System.out.println(responseBody);
    }

    @Test(description = "verify banned user functionality", dependsOnMethods = {"banUserList"})
    public void unBanUserNotMember(){

        String responseBody= given().
                header("onBehalfOf",GroupTest.userNameId).header("apiKey",apiKey).header("Accept","application/json").
                pathParam("guid",createdGuidPublic).
                pathParam("uid","superhero4").
                when().
                delete("/groups/{guid}/bannedusers/{uid}").
                then().
                assertThat().statusCode(404).extract().body().asString();
        System.out.println(responseBody);
    }

    @Test(description = "verify banned user functionality", dependsOnMethods = {"banUserList"})
    public void unBanUserInvalid(){

        String responseBody= given().
                header("onBehalfOf",GroupTest.userNameId).header("apiKey",apiKey).header("Accept","application/json").
                pathParam("guid",createdGuidPublic).
                pathParam("uid","superhero10").
                when().
                delete("/groups/{guid}/bannedusers/{uid}").
                then().
                assertThat().statusCode(404).extract().body().asString();
        System.out.println(responseBody);
    }


    @Test(description = "verify banned user functionality", dependsOnMethods = {"banUserList"})
    public void unBanUserInvalidGuid(){

        String responseBody= given().
                header("onBehalfOf",GroupTest.userNameId).header("apiKey",apiKey).header("Accept","application/json").
                pathParam("guid","InvalidGuid").
                pathParam("uid","superhero3").
                when().
                delete("/groups/{guid}/bannedusers/{uid}").
                then().
                assertThat().statusCode(404).extract().body().asString();
        System.out.println(responseBody);
    }

    @Test(description = "verify banned user functionality", dependsOnMethods = {"banUserList"})
    public void unBanUserInvalidBoth(){

        String responseBody= given().
                header("onBehalfOf",GroupTest.userNameId).header("apiKey",apiKey).header("Accept","application/json").
                pathParam("guid","InvalidGuid").
                pathParam("uid","InvalidUid").
                when().
                delete("/groups/{guid}/bannedusers/{uid}").
                then().
                assertThat().statusCode(404).extract().body().asString();
        System.out.println(responseBody);
    }

    @Test(description = "verify add member functionality",dependsOnMethods = {"unBanUserInvalidBoth"})
    public void addMember()
    {
        JSONObject userJson = new JSONObject();
        userJson.put("participants",new String[]{"superhero4"});

        String responseBody= getCommon().
                header("onBehalfOf",GroupTest.userNameId).
                pathParam("guid",createdGuidPublic).
                body(userJson.toString(1)).
                when().
                post("/groups/{guid}/members").
                then().
                assertThat().statusCode(200).extract().body().asString();
        System.out.println(responseBody);
    }

    @Test(description = "verify add member already ban user functionality",dependsOnMethods = {"addMember"})
    public void addMemberAlreadyBanUser()
    {
        JSONObject userJson = new JSONObject();
        userJson.put("participants",new String[]{"superhero4"});

        String responseBody= getCommon().
                header("onBehalfOf",GroupTest.userNameId).
                pathParam("guid",createdGuidPublic).
                body(userJson.toString(1)).
                when().
                post("/groups/{guid}/members").
                then().
                assertThat().statusCode(200).extract().body().asString();
        System.out.println(responseBody);
    }

    @Test(description = "verify add member already added functionality",dependsOnMethods = {"addMember"})
    public void addMemberAlreadyAdded()
    {
        JSONObject userJson = new JSONObject();
        userJson.put("participants",new String[]{"superhero4"});

        String responseBody= getCommon().
                        header("onBehalfOf",GroupTest.userNameId).
                        pathParam("guid",createdGuidPublic).
                body(userJson.toString(1)).
                when().
                post("/groups/{guid}/members").
                then().
                assertThat().statusCode(200).extract().body().asString();
        System.out.println(responseBody);
    }

    @Test(description = "verify add member already added functionality",dependsOnMethods = {"addMemberAlreadyAdded"})
    public void addMemberWithDifferentScope()
    {
        JSONObject userJson = new JSONObject();
        userJson.put("admins",new String[]{"superhero4"});

        String responseBody= getCommon().
                header("onBehalfOf",GroupTest.userNameId).
                pathParam("guid",createdGuidPublic).
                body(userJson.toString(1)).
                when().
                post("/groups/{guid}/members").
                then().
                assertThat().statusCode(200).extract().body().asString();
        System.out.println(responseBody);
    }

    @Test(description = "verify add member invalid uid functionality",dependsOnMethods = {"addMemberWithDifferentScope"})
    public void addMemberInvalidUid()
    {
        JSONObject userJson = new JSONObject();
        userJson.put("participants",new String[]{"superhero1000"});

        String responseBody= getCommon().
                        header("onBehalfOf",GroupTest.userNameId).
                        pathParam("guid",createdGuidPublic).
                body(userJson.toString(1)).
                when().
                post("/groups/{guid}/members").
                then().
                assertThat().statusCode(200).extract().body().asString();
        System.out.println(responseBody);
    }

    @Test(description = "verify add member invalid guid functionality",dependsOnMethods = {"addMemberInvalidUid"})
    public void addMemberInvalidGuid()
    {
        JSONObject userJson = new JSONObject();
        userJson.put("participants",new String[]{"superhero4"});

        String responseBody= getCommon().
                        header("onBehalfOf",GroupTest.userNameId).
                        pathParam("guid","InvalidGuid100").
                body(userJson.toString(1)).
                when().
                post("/groups/{guid}/members").
                then().
                assertThat().statusCode(404).extract().body().asString();
        System.out.println(responseBody);
    }

    @Test(description = "verify  list member functionality", dependsOnMethods = {"addMemberInvalidGuid"})
    public void listMember()
    {
        String responseBody= getCommon().
                        header("onBehalfOf",GroupTest.userNameId).
                        pathParam("guid",createdGuidPublic).
                when().
                get("/groups/{guid}/members").
                then().
                assertThat().statusCode(200).extract().body().asString();
        System.out.println(responseBody);
    }

    @Test(description = "verify  list member without OnBehalfOf functionality", dependsOnMethods = {"listMember"})
    public void listMemberWithoutOnBehalfOf()
    {
        String responseBody= getCommon().
                        pathParam("guid",createdGuidPublic).
                when().
                get("/groups/{guid}/members").
                then().
                assertThat().statusCode(200).extract().body().asString();
        System.out.println(responseBody);
    }

    @Test(description = "verify  list member invalid Guid functionality", dependsOnMethods = {"listMemberWithoutOnBehalfOf"})
    public void listMemberInvalidGuid()
    {
        String responseBody= getCommon().
                pathParam("guid","InvalidGuid100").
                when().
                get("/groups/{guid}/members").
                then().
                assertThat().statusCode(404).extract().body().asString();
        System.out.println(responseBody);
    }

    @Test(description = "verify change scope functionality", dependsOnMethods = {"listMemberInvalidGuid"})
    public void changeScope()
    {
        JSONObject userJson = new JSONObject();
        userJson.put("scope","moderator");

        String responseBody= getCommon().
                        header("onBehalfOf",GroupTest.userNameId).
                        pathParam("guid",createdGuidPublic).
                        pathParam("uid","superhero4").
                body(userJson.toString(1)).
                when().
                put("/groups/{guid}/members/{uid}").
                then().
                assertThat().statusCode(200).extract().body().asString();
        System.out.println(responseBody);
    }

    @Test(description = "verify change scope without onBehalf functionality", dependsOnMethods = {"changeScope"})
    public void changeScopeWithoutOnBehalfOf()
    {
        JSONObject userJson = new JSONObject();
        userJson.put("scope","moderator");

        String responseBody= getCommon().
                pathParam("guid",createdGuidPublic).
                pathParam("uid","superhero4").
                body(userJson.toString(1)).
                when().
                put("/groups/{guid}/members/{uid}").
                then().log().body().
                assertThat().statusCode(403).extract().body().asString();
        System.out.println(responseBody);
    }

    @Test(description = "verify change scope With Same Scope functionality", dependsOnMethods = {"changeScopeWithoutOnBehalfOf"})
    public void changeScopeWithSameScope()
    {
        JSONObject userJson = new JSONObject();
        userJson.put("scope","moderator");

        String responseBody= getCommon().
                header("onBehalfOf",GroupTest.userNameId).
                pathParam("guid",createdGuidPublic).
                pathParam("uid","superhero4").
                body(userJson.toString(1)).
                when().
                put("/groups/{guid}/members/{uid}").
                then().log().body().
                assertThat().statusCode(403).extract().body().asString();
        System.out.println(responseBody);
    }

    @Test(description = "verify change scope Invalid Uid functionality", dependsOnMethods = {"changeScopeWithSameScope"})
    public void changeScopeInvalidUid()
    {
        JSONObject userJson = new JSONObject();
        userJson.put("scope","moderator");

        String responseBody= getCommon().
                header("onBehalfOf",GroupTest.userNameId).
                pathParam("guid",createdGuidPublic).
                pathParam("uid","superhero1000").
                body(userJson.toString(1)).
                when().
                put("/groups/{guid}/members/{uid}").
                then().
                assertThat().statusCode(404).extract().body().asString();
        System.out.println(responseBody);
    }

    @Test(description = "verify change scope Invalid Guid functionality", dependsOnMethods = {"changeScopeInvalidUid"})
    public void changeScopeInvalidGuid()
    {
        JSONObject userJson = new JSONObject();
        userJson.put("scope","moderator");

        String responseBody= getCommon().
                header("onBehalfOf",GroupTest.userNameId).
                pathParam("guid","supergroup1000").
                pathParam("uid","superhero4").
                body(userJson.toString(1)).
                when().
                put("/groups/{guid}/members/{uid}").
                then().
                assertThat().statusCode(404).extract().body().asString();
        System.out.println(responseBody);
    }


    @Test(description = "verify kick member user functionality",dependsOnMethods = {"changeScopeInvalidGuid"})
    public void kickMember()
    {
        String responseBody= getCommon().
                        header("onBehalfOf",GroupTest.userNameId).
                        pathParam("guid",createdGuidPublic).
                pathParam("uid","superhero4").
                when().
                delete("/groups/{guid}/members/{uid}").
                then().
                assertThat().statusCode(200).extract().body().asString();
        System.out.println(responseBody);
    }

    @Test(description = "verify kick member not user functionality",dependsOnMethods = {"kickMember"})
    public void kickMemberNotMember()
    {
        String responseBody= getCommon().
                header("onBehalfOf",GroupTest.userNameId).
                pathParam("guid",createdGuidPublic).
                pathParam("uid","superhero500").
                when().
                delete("/groups/{guid}/members/{uid}").
                then().
                assertThat().statusCode(404).extract().body().asString();
        System.out.println(responseBody);
    }

    @Test(description = "verify kick member user functionality",dependsOnMethods = {"kickMemberNotMember"})
    public void kickMemberInvalidUid()
    {
        String responseBody= getCommon().
                        header("onBehalfOf",GroupTest.userNameId).
                        pathParam("guid",createdGuidPublic).
                pathParam("uid","superhero5000").
                when().
                delete("/groups/{guid}/members/{uid}").
                then().
                assertThat().statusCode(404).extract().body().asString();
        System.out.println(responseBody);
    }

    @Test(description = "verify kick member user functionality",dependsOnMethods = {"kickMemberInvalidUid"})
    public void kickMemberInvalidUidGuid()
    {
        String responseBody= getCommon().
                header("onBehalfOf",GroupTest.userNameId).
                pathParam("guid","supergroup1000").
                pathParam("uid","superhero5000").
                when().
                delete("/groups/{guid}/members/{uid}").
                then().
                assertThat().statusCode(404).extract().body().asString();
        System.out.println(responseBody);
    }

    @Test(description = "verify add friends functionality",dependsOnMethods = {"kickMemberInvalidUidGuid"})
    public void addFriends()
    {
        JSONObject userJson = new JSONObject();
        userJson.put("accepted",new String[]{"superhero4"});

        String responseBody= getCommon().
                pathParam("uid",userNameId).
                body(userJson.toString(1)).
                when().
                post("/users/{uid}/friends").
                then().log().body().
                assertThat().statusCode(200).extract().body().asString();
        System.out.println(responseBody);
    }

    @Test(description = "verify friends list functionality",dependsOnMethods = {"addFriends"})
    public void friendsList()
    {
        String responseBody= getCommon().
                pathParam("uid",userNameId).
                when().
                get("/users/{uid}/friends").
                then().log().body().
                assertThat().statusCode(200).extract().body().asString();
        System.out.println(responseBody);
    }

    @Test(description = "verify remove friends functionality",dependsOnMethods = {"friendsList"})
    public void removeFriends()
    {
        JSONObject userJson = new JSONObject();
        userJson.put("friends",new String[]{"superhero4"});

        String responseBody= getCommon().
                pathParam("uid",userNameId).
                body(userJson.toString(1)).
                when().
                delete("/users/{uid}/friends").
                then().log().body().
                assertThat().statusCode(200).extract().body().asString();
        System.out.println(responseBody);
    }

}

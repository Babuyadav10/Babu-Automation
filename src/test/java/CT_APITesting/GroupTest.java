package CT_APITesting;

import io.restassured.specification.RequestSpecification;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class GroupTest extends BaseTest {
   public static String createdGuidPassword;
   String createdGroupNamePassword;
    String createdGroupTypeName;
   String  createdGroupTypePassword;
 public static String  createdGuidPublic;
    String  createdGroupNamePublic;
    String createdGroupTypePublic;

    public static String   createdGuidPrivate;
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

    //------------------------createGroups public, private, password----------------------

    @Test(description = "Verify create group functionality")
    public void createGroups() {

        UsersTest a= new UsersTest();
        a.createUser();
        userNameId= UsersTest.createdUserId;

        System.out.println("userNameId :"+userNameId);

        for (int i = 0; i < createdGroupType.length; i++)
        {
            String guid = getRandomString("guid");
            String groupName = getRandomString("groupName");
            String passwordForGroup = getRandomString("password");

            JSONObject userJson = new JSONObject();
            userJson.put("guid", guid);
            userJson.put("name", groupName);
            userJson.put("type", createdGroupType[i]);

            if (createdGroupType[i] == "password")
            {
                userJson.put("password", passwordForGroup);
            }
            userJson.put("description", "This group is created by automation scripts" );

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

            if (createdGroupType[i] == "password")
            {
                System.out.println("createdGroupType password :" +createdGroupTypeName);

                createdGuidPassword = js.getString("data.guid");
                createdGroupNamePassword = js.getString("data.name");
                createdGroupTypePassword = js.getString("data.type");

            } else if (createdGroupType[i] == "private")
            {
                System.out.println("createdGroupType Private :" +createdGroupTypeName);
                createdGuidPrivate = js.getString("data.guid");
                createdGroupNamePrivate = js.getString("data.name");
                createdGroupTypePrivate = js.getString("data.type");
            }
            else
            {
                System.out.println("createdGroupType Public :" + createdGroupTypeName);
                createdGuidPublic = js.getString("data.guid");
                createdGroupNamePublic = js.getString("data.name");
                createdGroupTypePublic = js.getString("data.type");
            }
        }
    }


    //--------------------------------listGroups-----------------------------------------

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
//        Assert.assertEquals(js.getString("data[0].guid"), createdGuidPassword);
//        Assert.assertEquals(js.getString("data[1].guid"), createdGuidPrivate);
//        Assert.assertEquals(js.getString("data[2].guid"), createdGuidPublic);

    }

    @Test(description = "Verify listGroups by group type public",dependsOnMethods = {"listGroups"})
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


    @Test(description = "Verify listGroups by group type private ",dependsOnMethods = {"listGroups"})
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


    @Test(description = "Verify listGroups by group type password",dependsOnMethods = {"listGroups"})
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

    //-----------------------------listGroups with tags----------------------------

    @Test(description = "Verify listGroups with tags true",dependsOnMethods = {"listGroups"})
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

    @Test(description = "Verify listGroups with tags false",dependsOnMethods = {"listGroups"})
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

    //-----------------------------listGroups withTags and tagsName----------------------------

    @Test(description = "Verify listGroups withTags and tagsName",dependsOnMethods = {"listGroups"})
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

    @Test(description = "Verify listGroups withTags and tagsName invalid",dependsOnMethods = {"listGroups"})
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

    //--------------------------------listGroups sortBy and sortOrder------------------------------

    @Test(description = "Verify listGroups sortBy and sortOrder asc" ,dependsOnMethods = {"listGroups"})
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

    @Test(description = "Verify listGroups sortBy and sortOrder DESC",dependsOnMethods = {"listGroups"})
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

    //--------------------------------listGroups SearchKey------------------------------

    @Test(description = "Verify listGroups SearchKey",dependsOnMethods = {"listGroups"})
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

    //--------------------------------getGroups------------------------------

    @Test(description = "verify getGroups public",dependsOnMethods = {"listGroupsSearchKey"})
    public void getGroupsPublic()
    {
        String responseBody =getCommon().
               // header("apiKey",apiKey).header("Accept","application/json").
                when().
                get("/groups/" + createdGuidPublic).
                then().
                assertThat().statusCode(200).extract().body().asString();
        System.out.println(responseBody);
        PathFinder(responseBody);
        System.out.println(createdGuidPublic);
        Assert.assertEquals(js.getString("data.guid"), createdGuidPublic);
    }

    @Test(description = "verify getGroups private",dependsOnMethods = {"listGroupsSearchKey"})
    public void getGroupsPrivate()
    {
        String responseBody =getCommon().
                when().
                get("/groups/" + createdGuidPrivate).
                then(). log().body().
                assertThat().statusCode(200).extract().body().asString();
         PathFinder( responseBody);
        Assert.assertEquals(js.getString("data.guid"), createdGuidPrivate);
    }

    @Test(description = "verify getGroups password",dependsOnMethods = {"listGroupsSearchKey"})
    public void getGroupsPassword()
    {
        String responseBody =getCommon().
                when().
                get("/groups/" + createdGuidPassword).
                then(). log().body().
                assertThat().statusCode(200).extract().body().asString();
        PathFinder( responseBody);
        Assert.assertEquals(js.getString("data.guid"), createdGuidPassword);
    }

    @Test(description = "verify getGroups invalid endPoint",dependsOnMethods = {"listGroupsSearchKey"})
    public void getGroupsInvalidEndPoint()
    {
        String responseBody =getCommon().
                when().
                get("/groupss/" + createdGuidPublic).
                then(). log().body().
                assertThat().statusCode(404).extract().body().asString();
        System.out.println(responseBody);
       PathFinder(responseBody);
       Assert.assertEquals(js.getString("error.message"),"The API endpoint is invalid. Please verify the API call.");
    }

    @Test(description = "verify getGroups guid invalid ",dependsOnMethods = {"listGroupsSearchKey"})
    public void getGroupsInvalid()
    {
        String responseBody =getCommon().
                when().
                get("/groups/{guid}","InvalidGUID").
                then(). log().body().
                assertThat().statusCode(404).extract().body().asString();
        System.out.println(responseBody);
    }

    @Test(description = "verify getGroups empty in path params",dependsOnMethods = {"listGroupsSearchKey"})
    public void getGroupsEmpty()
    {
        String responseBody =getCommon().
                when().
                get("/groups/{guid}"," ").
                then(). log().body().
                assertThat().statusCode(404).extract().body().asString();
        System.out.println(responseBody);
    }

    //---------------------------updateGroups-----------------------------------
    //---------------------------groupType change public to private to password----------------------

    @Test(description = "Verify updateGroup for group public",dependsOnMethods = {"getGroupsEmpty"})
    public void updateGroupsPublic()
    {
        String updatedGroupName = getRandomString("publicUpdatedGName");

        JSONObject userJson = new JSONObject();
        userJson.put("name", updatedGroupName);
        userJson.put("description", "Group name has been updated:" + updatedGroupName);

        String responseBody = getCommon().
                body(userJson.toString(1)).
                when().
                put("/groups/" + createdGuidPublic).
                then().
                assertThat().statusCode(200).extract().body().asString();
        System.out.println(responseBody);

        PathFinder(responseBody);
        Assert.assertEquals(js.getString("data.name"),updatedGroupName);
    }

    @Test(description = "Verify updateGroup groupType has changed public to private",dependsOnMethods = {"updateGroupsPublic"})
    public void updateGroupsPublicToPrivate()
    {
        String updatedGroupName = getRandomString("updatedGUIDType");

        JSONObject userJson = new JSONObject();
        userJson.put("name", updatedGroupName);
        userJson.put("type", "private");
        userJson.put("description", "group type has been changed public into private");

        String responseBody = getCommon().
                body(userJson.toString(1)).
                when().
                put("/groups/" + createdGuidPublic).
                then().
                assertThat().statusCode(200).extract().body().asString();
        System.out.println(responseBody);
    }

    @Test(description = "Verify updateGroup groupType has changed private to password",dependsOnMethods = {"updateGroupsPublicToPrivate"})
    public void updateGroupsPrivateToPassword()
    {
        String updatedGroupName = getRandomString("updatedGUIDType");

        JSONObject userJson = new JSONObject();
        userJson.put("name", updatedGroupName);
        userJson.put("type", "password");
        userJson.put("password","babu");
        userJson.put("description", "group type has been changed private into password");

        String responseBody = getCommon().
                body(userJson.toString(1)).
                when().
                put("/groups/" + createdGuidPublic).
                then().
                assertThat().statusCode(200).extract().body().asString();
        System.out.println(responseBody);
    }

    //---------------------------groupType change private to public to password----------------------

    @Test(description = "Verify updateGroup for group private",dependsOnMethods = {"updateGroupsPrivateToPassword"})
    public void updateGroupsPrivate()
    {
        String updatedGroupName = getRandomString("privateUpdatedGName");

        JSONObject userJson = new JSONObject();
        userJson.put("name", updatedGroupName);
        userJson.put("description", "groupType private name is updated" + updatedGroupName);

        String responseBody = getCommon().
                body(userJson.toString(1)).
                when().
                put("/groups/" + createdGuidPrivate).
                then().
                assertThat().statusCode(200).extract().body().asString();
        System.out.println(responseBody);

        PathFinder(responseBody);
        Assert.assertEquals(js.getString("data.name"),updatedGroupName);
    }

    @Test(description = "Verify updateGroup groupType has changed private to public",dependsOnMethods = {"updateGroupsPrivate"})
    public void updateGroupsPrivateToPublic()
    {
        String updatedGroupName = getRandomString("updatedGUIDType");

        JSONObject userJson = new JSONObject();
        userJson.put("name", updatedGroupName);
        userJson.put("type", "public");
        userJson.put("description", "group type private has been changed into public");

        String responseBody = getCommon().
                body(userJson.toString(1)).
                when().
                put("/groups/" + createdGuidPrivate).
                then().
                assertThat().statusCode(200).extract().body().asString();
        System.out.println(responseBody);
    }


    @Test(description = "Verify updateGroup groupType has changed public to password",dependsOnMethods = {"updateGroupsPrivateToPublic"})
    public void updateGroupsPublicToPassword()
    {
        String updatedGroupName = getRandomString("updatedGUIDType");

        JSONObject userJson = new JSONObject();
        userJson.put("name", updatedGroupName);
        userJson.put("type", "password");
        userJson.put("password", " ");
        userJson.put("description", "group type public has been changed into password");

        String responseBody = getCommon().
                body(userJson.toString(1)).
                when().
                put("/groups/" + createdGuidPrivate).
                then().
                assertThat().statusCode(200).extract().body().asString();
        System.out.println(responseBody);

        PathFinder(responseBody);
        Assert.assertEquals(js.getString("data.name"),updatedGroupName);
    }

//---------------------------groupType change password to public to private----------------------

    @Test(description = "Verify updateGroup for group password",dependsOnMethods = {"updateGroupsPublicToPassword"})
    public void updateGroupsPassword()
    {
        String updatedGroupName = getRandomString("passwordUpdatedGName");

        JSONObject userJson = new JSONObject();
        userJson.put("name", updatedGroupName);
        userJson.put("description", "Group password name has been changed" + updatedGroupName);

        String responseBody = getCommon().
                body(userJson.toString(1)).
                when().
                put("/groups/" + createdGuidPassword).
                then().
                assertThat().statusCode(200).extract().body().asString();
        System.out.println(responseBody);

        PathFinder(responseBody);
        Assert.assertEquals(js.getString("data.name"),updatedGroupName);
    }


    //---------------------------banUser----------------------------------------

    @Test(description = "verify bannedUser for group", dependsOnMethods = {"updateGroupsPassword"})
    public void banUser(){

        String responseBody= getCommon().
                header("onBehalfOf",GroupTest.userNameId).
                pathParam("guid",createdGuidPublic).
                pathParam("uid","superhero3").
                when().
                post("/groups/{guid}/bannedusers/{uid}").
                then().
                assertThat().statusCode(200).extract().body().asString();
        System.out.println(responseBody);
    }

    @Test(description = "verify bannedUser uid invalid", dependsOnMethods = {"banUser"})
    public void banUserInvalid()
    {
        String responseBody= getCommon().
                header("onBehalfOf",userNameId).
                pathParam("guid",createdGuidPublic).
                pathParam("uid","superhero1000").
                when().
                post("/groups/{guid}/bannedusers/{uid}").
                then().
                assertThat().statusCode(404).extract().body().asString();
        System.out.println(responseBody);
    }

    @Test(description = "verify bannedUser invalid guid functionality", dependsOnMethods = {"banUser"})
    public void banUserInvalidGUID()
    {
        String responseBody= getCommon().
                header("onBehalfOf",userNameId).
                pathParam("guid","supergroup10000").
                pathParam("uid","superhero3").
                when().
                post("/groups/{guid}/bannedusers/{uid}").
                then().
                assertThat().statusCode(404).extract().body().asString();
        System.out.println(responseBody);
    }

    @Test(description = "verify bannedUser invalid both guid & uid", dependsOnMethods = {"banUser"})
    public void banUserInvalidGuidUid()
    {
        String responseBody= getCommon().
                header("onBehalfOf",userNameId).
                pathParam("guid","supergroup1000").
                pathParam("uid","superhero1000").
                when().
                post("/groups/{guid}/bannedusers/{uid}").
                then().
                assertThat().statusCode(404).extract().body().asString();
        System.out.println(responseBody);
    }

    @Test(description = "verify bannedUser uid is not member of group ", dependsOnMethods = {"banUser"})
    public void banUserNotMemberGroup()
    {
        String responseBody= getCommon().
                header("onBehalfOf",userNameId).
                pathParam("guid",createdGuidPublic).
                pathParam("uid","superhero5").
                when().
                post("/groups/{guid}/bannedusers/{uid}").
                then().
                assertThat().statusCode(404).extract().body().asString();
        System.out.println(responseBody);
    }

    //------------------------------banUserList----------------------------------------

    @Test(description = "verify bannedUserList functionality", dependsOnMethods = {"banUserNotMemberGroup"})
    public void banUserList()
    {
        String responseBody= getCommon().
                header("onBehalfOf",userNameId).
                pathParam("guid",createdGuidPublic).
                when().
                get("/groups/{guid}/bannedusers").
                then().
                assertThat().statusCode(200).extract().body().asString();
        System.out.println(responseBody);
    }

    @Test(description = "verify bannedUserList for invalid guid in pathParams", dependsOnMethods = {"banUserList"})
    public void banUserListInvalidGuid()
    {
        String responseBody= getCommon().
                header("onBehalfOf",userNameId).
                pathParam("guid","supergroup10000").
                when().
                get("/groups/{guid}/bannedusers").
                then().
                assertThat().statusCode(404).extract().body().asString();
        System.out.println(responseBody);
    }

    //------------------------------unBanUser----------------------------------------

    @Test(description = "verify unBanUser functionality", dependsOnMethods = {"banUserListInvalidGuid"})
    public void unBanUser()
    {
        String responseBody= getCommon().
                header("onBehalfOf",userNameId).
                pathParam("guid",createdGuidPublic).
                pathParam("uid","superhero3").
                when().
                delete("/groups/{guid}/bannedusers/{uid}").
                then().
                assertThat().statusCode(200).extract().body().asString();
        System.out.println(responseBody);
    }


    @Test(description = "verify unBanUser not OnBehalfOf ", dependsOnMethods = {"unBanUser"})
    public void unBanUserNotOnBehalfOf()
    {
        String responseBody= getCommon().
                pathParam("guid",createdGuidPublic).
                pathParam("uid","superhero3").
                when().
                delete("/groups/{guid}/bannedusers/{uid}").
                then().
                assertThat().statusCode(404).extract().body().asString();
        System.out.println(responseBody);
    }

    @Test(description = "verify unBanUser the user is not member of group", dependsOnMethods = {"unBanUserNotOnBehalfOf"})
    public void unBanUserNotMember()
    {
        String responseBody= getCommon().
                header("onBehalfOf",userNameId).
                pathParam("guid",createdGuidPublic).
                pathParam("uid","superhero4").
                when().
                delete("/groups/{guid}/bannedusers/{uid}").
                then().
                assertThat().statusCode(404).extract().body().asString();
        System.out.println(responseBody);
    }

    @Test(description = "verify unBanUser invalid uid in pathParams", dependsOnMethods = {"unBanUserNotMember"})
    public void unBanUserInvalid()
    {
        String responseBody= getCommon().
                header("onBehalfOf",userNameId).
                pathParam("guid",createdGuidPublic).
                pathParam("uid","superhero1000").
                when().
                delete("/groups/{guid}/bannedusers/{uid}").
                then().
                assertThat().statusCode(404).extract().body().asString();
        System.out.println(responseBody);
    }


    @Test(description = "verify unBanUser invalid guid in pathParams", dependsOnMethods = {"unBanUserInvalid"})
    public void unBanUserInvalidGuid(){

        String responseBody= getCommon().
                header("onBehalfOf",userNameId).
                pathParam("guid","supergroup1000").
                pathParam("uid","superhero3").
                when().
                delete("/groups/{guid}/bannedusers/{uid}").
                then().
                assertThat().statusCode(404).extract().body().asString();
        System.out.println(responseBody);
    }

    @Test(description = "verify unBanUser invalid both uid & guid in pathParams", dependsOnMethods = {"unBanUserInvalidGuid"})
    public void unBanUserInvalidBoth(){

        String responseBody= getCommon().
                header("onBehalfOf",userNameId).
                pathParam("guid","supergroup1000").
                pathParam("uid","superhero1000").
                when().
                delete("/groups/{guid}/bannedusers/{uid}").
                then().
                assertThat().statusCode(404).extract().body().asString();
        System.out.println(responseBody);
    }


     //------------------------------addMember for group-----------------------------------

    @Test(description = "verify addMember functionality",dependsOnMethods = {"unBanUserInvalidBoth"})
    public void addMember()
    {
        JSONObject userJson = new JSONObject();
        userJson.put("participants",new String[]{"superhero4"});

        String responseBody= getCommon().
                header("onBehalfOf",userNameId).
                pathParam("guid",createdGuidPublic).
                body(userJson.toString(1)).
                when().
                post("/groups/{guid}/members").
                then().
                assertThat().statusCode(200).extract().body().asString();
        System.out.println(responseBody);
    }


//    @Test(description = "verify add member already ban user functionality",dependsOnMethods = {"addMember"})
//    public void addMemberAlreadyBanUser()
//    {
//        JSONObject userJson = new JSONObject();
//        userJson.put("participants",new String[]{"superhero4"});
//
//        String responseBody= getCommon().
//                header("onBehalfOf",GroupTest.userNameId).
//                pathParam("guid",createdGuidPublic).
//                body(userJson.toString(1)).
//                when().
//                post("/groups/{guid}/members").
//                then().
//                assertThat().statusCode(200).extract().body().asString();
//        System.out.println(responseBody);
//    }


    @Test(description = "verify addMember already added member",dependsOnMethods = {"addMember"})
    public void addMemberAlreadyAdded()
    {
        JSONObject userJson = new JSONObject();
        userJson.put("participants",new String[]{"superhero4"});

        String responseBody= getCommon().
                        header("onBehalfOf",userNameId).
                        pathParam("guid",createdGuidPublic).
                body(userJson.toString(1)).
                when().
                post("/groups/{guid}/members").
                then().
                assertThat().statusCode(200).extract().body().asString();
        System.out.println(responseBody);
    }

    @Test(description = "verify addMember already added member with different scope",dependsOnMethods = {"addMemberAlreadyAdded"})
    public void addMemberWithDifferentScope()
    {
        JSONObject userJson = new JSONObject();
        userJson.put("admins",new String[]{"superhero4"});

        String responseBody= getCommon().
                header("onBehalfOf",userNameId).
                pathParam("guid",createdGuidPublic).
                body(userJson.toString(1)).
                when().
                post("/groups/{guid}/members").
                then().
                assertThat().statusCode(200).extract().body().asString();
        System.out.println(responseBody);
    }

    @Test(description = "verify addMember invalid uid in body",dependsOnMethods = {"addMemberWithDifferentScope"})
    public void addMemberInvalidUid()
    {
        JSONObject userJson = new JSONObject();
        userJson.put("participants",new String[]{"superhero1000"});

        String responseBody= getCommon().
                        header("onBehalfOf",userNameId).
                        pathParam("guid",createdGuidPublic).
                body(userJson.toString(1)).
                when().
                post("/groups/{guid}/members").
                then().
                assertThat().statusCode(200).extract().body().asString();
        System.out.println(responseBody);
    }

    @Test(description = "verify addMember invalid guid in pathParams",dependsOnMethods = {"addMemberInvalidUid"})
    public void addMemberInvalidGuid()
    {
        JSONObject userJson = new JSONObject();
        userJson.put("participants",new String[]{"superhero5"});

        String responseBody= getCommon().
                header("onBehalfOf",userNameId).
                pathParam("guid","supergroup1000").
                body(userJson.toString(1)).
                when().
                post("/groups/{guid}/members").
                then().
                assertThat().statusCode(404).extract().body().asString();
        System.out.println(responseBody);
    }


    //------------------------listMember for group-------------------------------

    @Test(description = "verify  listMember functionality", dependsOnMethods = {"addMemberInvalidGuid"})
    public void listMember()
    {
        String responseBody= getCommon().
                header("onBehalfOf",userNameId).
                pathParam("guid",createdGuidPublic).
                when().
                get("/groups/{guid}/members").
                then().
                assertThat().statusCode(200).extract().body().asString();
        System.out.println(responseBody);
    }

    @Test(description = "verify listMember without OnBehalfOf ", dependsOnMethods = {"listMember"})
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

    @Test(description = "verify listMember invalid Guid ", dependsOnMethods = {"listMemberWithoutOnBehalfOf"})
    public void listMemberInvalidGuid()
    {
        String responseBody= getCommon().
                pathParam("guid","supergroup1000").
                when().
                get("/groups/{guid}/members").
                then().
                assertThat().statusCode(404).extract().body().asString();
        System.out.println(responseBody);
    }

    //------------------------changeScope for group----------------------

    @Test(description = "verify changeScope functionality", dependsOnMethods = {"listMemberInvalidGuid"})
    public void changeScope()
    {
        JSONObject userJson = new JSONObject();
        userJson.put("scope","moderator");

        String responseBody= getCommon().
                        header("onBehalfOf",userNameId).
                        pathParam("guid",createdGuidPublic).
                        pathParam("uid","superhero4").
                body(userJson.toString(1)).
                when().
                put("/groups/{guid}/members/{uid}").
                then().
                assertThat().statusCode(200).extract().body().asString();
        System.out.println(responseBody);
    }

    @Test(description = "verify changeScope without onBehalf ", dependsOnMethods = {"changeScope"})
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

    @Test(description = "verify changeScope with already Same Scope ", dependsOnMethods = {"changeScopeWithoutOnBehalfOf"})
    public void changeScopeWithSameScope()
    {
        JSONObject userJson = new JSONObject();
        userJson.put("scope","moderator");

        String responseBody= getCommon().
                header("onBehalfOf",userNameId).
                pathParam("guid",createdGuidPublic).
                pathParam("uid","superhero4").
                body(userJson.toString(1)).
                when().
                put("/groups/{guid}/members/{uid}").
                then().log().body().
                assertThat().statusCode(403).extract().body().asString();
        System.out.println(responseBody);
    }

    @Test(description = "verify changeScope Invalid Uid ", dependsOnMethods = {"changeScopeWithSameScope"})
    public void changeScopeInvalidUid()
    {
        JSONObject userJson = new JSONObject();
        userJson.put("scope","moderator");

        String responseBody= getCommon().
                header("onBehalfOf",userNameId).
                pathParam("guid",createdGuidPublic).
                pathParam("uid","superhero1000").
                body(userJson.toString(1)).
                when().
                put("/groups/{guid}/members/{uid}").
                then().
                assertThat().statusCode(404).extract().body().asString();
        System.out.println(responseBody);
    }

    @Test(description = "verify changeScope Invalid Guid ", dependsOnMethods = {"changeScopeInvalidUid"})
    public void changeScopeInvalidGuid()
    {
        JSONObject userJson = new JSONObject();
        userJson.put("scope","moderator");

        String responseBody= getCommon().
                header("onBehalfOf",userNameId).
                pathParam("guid","supergroup1000").
                pathParam("uid","superhero4").
                body(userJson.toString(1)).
                when().
                put("/groups/{guid}/members/{uid}").
                then().
                assertThat().statusCode(404).extract().body().asString();
        System.out.println(responseBody);
    }

    //--------------------------------kickMember for group----------------------

    @Test(description = "verify kickMember user functionality",dependsOnMethods = {"changeScopeInvalidGuid"})
    public void kickMember()
    {
        String responseBody= getCommon().
                header("onBehalfOf",userNameId).
                pathParam("guid",createdGuidPublic).
                pathParam("uid","superhero4").
                when().
                delete("/groups/{guid}/members/{uid}").
                then().
                assertThat().statusCode(200).extract().body().asString();
        System.out.println(responseBody);
    }

    @Test(description = "verify kickMember which is not member of group",dependsOnMethods = {"kickMember"})
    public void kickMemberNotMember()
    {
        String responseBody= getCommon().
                header("onBehalfOf",userNameId).
                pathParam("guid",createdGuidPublic).
                pathParam("uid","superhero5").
                when().
                delete("/groups/{guid}/members/{uid}").
                then().
                assertThat().statusCode(404).extract().body().asString();
        System.out.println(responseBody);
    }

    @Test(description = "verify kickMember invalid uid",dependsOnMethods = {"kickMemberNotMember"})
    public void kickMemberInvalidUid()
    {
        String responseBody= getCommon().
                        header("onBehalfOf",userNameId).
                        pathParam("guid",createdGuidPublic).
                pathParam("uid","superhero1000").
                when().
                delete("/groups/{guid}/members/{uid}").
                then().
                assertThat().statusCode(404).extract().body().asString();
        System.out.println(responseBody);
    }

    @Test(description = "verify kickMember invalid guid",dependsOnMethods = {"kickMemberInvalidUid"})
    public void kickMemberInvalidUidGuid()
    {
        String responseBody= getCommon().
                header("onBehalfOf",userNameId).
                pathParam("guid","supergroup1000").
                pathParam("uid","superhero5000").
                when().
                delete("/groups/{guid}/members/{uid}").
                then().
                assertThat().statusCode(404).extract().body().asString();
        System.out.println(responseBody);
    }

    //-----------------------------addFriends-------------------------------------


    @Test(description = "verify addFriends functionality",dependsOnMethods = {"kickMemberInvalidUidGuid"})
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

    @Test(description = "verify friendsList functionality",dependsOnMethods = {"addFriends"})
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

    @Test(description = "verify removeFriends functionality",dependsOnMethods = {"friendsList"})
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



    //---------------------------deleteGroup----------------------------------------


    @Test(description = "Verify deleteGroups for group public",dependsOnMethods = {"removeFriends"})
    public void deleteGroupsPublic()
    {
        String responseBody= getCommon().
                when().
                delete("/groups/" + createdGuidPublic).
                then().
                assertThat().statusCode(200).extract().body().asString();
        System.out.println(responseBody);
    }

    @Test(description = "Verify deleteGroups for group private",dependsOnMethods = {"deleteGroupsPublic"})
    public void deleteGroupsPrivate()
    {
        String responseBody= getCommon().
                when().
                delete("/groups/" + createdGuidPrivate).
                then().
                assertThat().statusCode(200).extract().body().asString();;
        System.out.println(responseBody);
    }

    @Test(description = "Verify deleteGroups for group password",dependsOnMethods = {"deleteGroupsPrivate"})
    public void deleteGroupsPassword()
    {
        String responseBody= getCommon().
                when().
                delete("/groups/" + createdGuidPassword).
                then().
                assertThat().statusCode(200).extract().body().asString();
        System.out.println(responseBody);
    }

    @Test(description = "Verify deleteUsers which has been performing all action on group", dependsOnMethods = {"deleteGroupsPassword"})
    public void deleteUsers()
    {
        JSONObject userJson = new JSONObject();
        userJson.put("permanent",true);

        String responseBody=getCommon().
                body(userJson.toString(1)).
                when().
                delete("/users/"+userNameId).
                then().
                assertThat().
                statusCode(200).extract().body().asString();
        System.out.println(responseBody);
    }



}

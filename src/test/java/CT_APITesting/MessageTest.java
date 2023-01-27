package CT_APITesting;

import io.restassured.specification.RequestSpecification;
import org.json.JSONArray;
import org.json.JSONObject;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class MessageTest extends BaseTest{

    public String PublicGroupMsgID; // Accessing PublicGroupMsgID for msgID for group conversation
    public String userMsgID;  // Accessing userMsgID for msgID for user to user conversation
    public static String userUID;  // Accessing userUID for onBehalfOf in Header for both user to user & group conversation
    public String PublicGroupGUID;  // Accessing GUID for public group
    public String PrivateGroupGUID;  // Accessing GUID for private group
    public String PasswordGroupGUID;
    public RequestSpecification getCommon()
    {
        RequestSpecification httpRequestObject;
        httpRequestObject = given();
        httpRequestObject.header("apiKey",GlobalClassTest.prop.getProperty("apiKey"));
        httpRequestObject.header("Content-Type", "application/json");
        httpRequestObject.header("Accept", "application/json");
        return httpRequestObject;
    }

    //------------------------sendMessage text user to user-------------------------

    @Test(description = "Verify sendMessage text User to user onBehalfOf")
    public void sendMsgTextUserToUser()
    {
        /*
        UsersTest a= new UsersTest();
               a.createUser();
         userUID= UsersTest.createdUserId;
         System.out.println(userUID);

         */

        GroupTest b=new GroupTest();
                b.createGroups();
               PublicGroupGUID=GroupTest.createdGuidPublic;         // Accessing GUID for public group
               PrivateGroupGUID=GroupTest.createdGuidPrivate;       // Accessing GUID for private group
               PasswordGroupGUID=GroupTest.createdGuidPassword;

               userUID= GroupTest.userNameId; // Accessing userUID for onBehalfOf in Header for both user & group

          System.out.println(PublicGroupGUID);
           System.out.println(userUID);

        JSONObject userJson1 = new JSONObject();
        userJson1.put("text","Hello Babu,Message is sending through automation script");

         JSONObject userJson = new JSONObject();
        userJson.put("receiver","superhero1");
        userJson.put("receiverType","user");
        userJson.put("category","message");
        userJson.put("type","text");
        userJson.put("data",userJson1);

        String responseBody=getCommon().
                header("onBehalfOf",userUID).
                body(userJson.toString(1)).
                when().
                post("/messages").
                then().
                assertThat().statusCode(200).extract().body().asString();
        System.out.println(responseBody);

        PathFinder(responseBody);
        userMsgID = js.getString("data.id");   // Accessing userMsgID for msgID for user to user conversation

    }

    @Test(description = "Verify sendMessage text with tags User to user onBehalfOf",dependsOnMethods = {"sendMsgTextUserToUser"})
    public void sendMsgTextUserToUserWithTags()
    {
        JSONObject userJson1 = new JSONObject();
        userJson1.put("text","Hello Babu,Message is sending through automation script with tags");

        JSONObject userJson = new JSONObject();
        userJson.put("receiver","superhero1");
        userJson.put("receiverType","user");
        userJson.put("category","message");
        userJson.put("type","text");
        userJson.put("tags",new String[] {"tag1","tag2"});
        userJson.put("data",userJson1);

        String responseBody=getCommon().
                header("onBehalfOf",userUID).
                body(userJson.toString(1)).
                when().
                post("/messages").
                then().
                assertThat().statusCode(200).extract().body().asString();
        System.out.println(responseBody);

    }

    @Test(description = "Verify sendMessage text User to user without onBehalfOf",dependsOnMethods = {"sendMsgTextUserToUser"})
    public void sendMsgTextUserToUserWithOutOnBehalfOf()
    {
        JSONObject userJson1 = new JSONObject();
        userJson1.put("text","Hello Babu,Message is sending through automation script without onBehalfOf");

        JSONObject userJson = new JSONObject();
        userJson.put("receiver","superhero1");
        userJson.put("receiverType","user");
        userJson.put("category","message");
        userJson.put("type","text");
        userJson.put("data",userJson1);

        String responseBody=getCommon().
                body(userJson.toString(1)).
                when().
                post("/messages").
                then().
                assertThat().statusCode(200).extract().body().asString();
        System.out.println(responseBody);

    }

    @Test(description = "Verify sendMessage text with tags User to user without onBehalfOf",dependsOnMethods = {"sendMsgTextUserToUser"})
    public void sendMsgTextUserToUserWithTagsWithOutOnBehalfOf()
    {
        JSONObject userJson1 = new JSONObject();
        userJson1.put("text","Hello Babu,Message is sending through automation script with tags without onBehalfOf");

        JSONObject userJson = new JSONObject();
        userJson.put("receiver","superhero1");
        userJson.put("receiverType","user");
        userJson.put("category","message");
        userJson.put("type","text");
        userJson.put("tags",new String[]{"tag1","tag2"});
        userJson.put("data",userJson1);

       String responseBody=getCommon().
                body(userJson.toString(1)).
                when().
                post("/messages").
                then().
                assertThat().statusCode(200).extract().body().asString();
        System.out.println(responseBody);

    }

    //------------------------sendMessage image user to user-------------------------

    @Test(description = "Verify sendMessage image User to user onBehalfOf",dependsOnMethods = {"sendMsgTextUserToUser"})
    public void sendMsgImageUserToUser()
    {
        JSONObject userJson = new JSONObject();
        userJson.put("name", "hi.png");
        userJson.put("extension", "png");
        userJson.put("mimeType", "image/png");
        userJson.put("url", "https://data-eu.cometchat.io/assets/images/avatars/ironman.png");

        JSONArray userJsonArray = new JSONArray();
        userJsonArray.put(userJson);

        JSONObject userJson1 = new JSONObject();
        userJson1.put("attachments",userJsonArray);

        JSONObject userJson2 = new JSONObject();
        userJson2.put("receiver","superhero1");
        userJson2.put("receiverType","user");
        userJson2.put("category","message");
        userJson2.put("type","image");
        userJson2.put("data",userJson1);

        String responseBody=getCommon().
                header("onBehalfOf",userUID).
                body(userJson2.toString(1)).
                when().
                post("/messages").
                then().
                assertThat().statusCode(200).extract().body().asString();
        System.out.println(responseBody);

    }

    @Test(description = "Verify sendMessage image User to user with tags onBehalfOf",dependsOnMethods = {"sendMsgTextUserToUser"})
    public void sendMsgImageUserToUserWithTags()
    {
        JSONObject userJson = new JSONObject();
        userJson.put("name", "hi.png");
        userJson.put("extension", "png");
        userJson.put("mimeType", "image/png");
        userJson.put("url", "https://data-eu.cometchat.io/assets/images/avatars/ironman.png");

        JSONArray userJsonArray = new JSONArray();
        userJsonArray.put(userJson);

        JSONObject userJson1 = new JSONObject();
        userJson1.put("attachments",userJsonArray);


        JSONObject userJson2 = new JSONObject();
        userJson2.put("receiver","superhero1");
        userJson2.put("receiverType","user");
        userJson2.put("category","message");
        userJson2.put("type","image");
        userJson2.put("tags",new String[]{"tag1","tag2"});
        userJson2.put("data",userJson1);

       String responseBody=getCommon().
               header("onBehalfOf",userUID).
               body(userJson2.toString(1)).
                when().log().body().
                post("/messages").
                then().
                assertThat().statusCode(200).extract().body().asString();
        System.out.println(responseBody);

    }

    @Test(description = "Verify sendMessage image User to user without onBehalfOf",dependsOnMethods = {"sendMsgTextUserToUser"})
    public void sendMsgImageUserToUserWithOutOnBehalfOf()
    {
        JSONObject userJson = new JSONObject();
        userJson.put("name", "hi.png");
        userJson.put("extension", "png");
        userJson.put("mimeType", "image/png");
        userJson.put("url", "https://data-eu.cometchat.io/assets/images/avatars/ironman.png");

        JSONArray userJsonArray = new JSONArray();
        userJsonArray.put(userJson);

        JSONObject userJson1 = new JSONObject();
        userJson1.put("attachments",userJsonArray);

        JSONObject userJson2 = new JSONObject();
        userJson2.put("receiver","superhero1");
        userJson2.put("receiverType","user");
        userJson2.put("category","message");
        userJson2.put("type","image");
        userJson2.put("data",userJson1);

         String responseBody=getCommon().
                 body(userJson2.toString(1)).
                when().
                post("/messages").
                then().
                assertThat().statusCode(200).extract().body().asString();
        System.out.println(responseBody);

    }

    @Test(description = "Verify sendMessage image User to user with tags without onBehalfOf",dependsOnMethods = {"sendMsgTextUserToUser"})
    public void sendMsgImageUserToUserTagsWithOutOnBehalfOf()
    {
        JSONObject userJson = new JSONObject();
        userJson.put("name", "hi.png");
        userJson.put("extension", "png");
        userJson.put("mimeType", "image/png");
        userJson.put("url", "https://data-eu.cometchat.io/assets/images/avatars/ironman.png");

        JSONArray userJsonArray = new JSONArray();
        userJsonArray.put(userJson);

        JSONObject userJson1 = new JSONObject();
        userJson1.put("attachments",userJsonArray);

        JSONObject userJson2 = new JSONObject();
        userJson2.put("receiver","superhero1");
        userJson2.put("receiverType","user");
        userJson2.put("category","message");
        userJson2.put("type","image");
        userJson2.put("tags",new String[]{"tag1","tag2"});
        userJson2.put("data",userJson1);

        String responseBody=getCommon().
                body(userJson2.toString(1)).
                when().
                post("/messages").
                then().
                assertThat().statusCode(200).extract().body().asString();
        System.out.println(responseBody);

    }

//------------------------sendMessage image & text user to user-------------------------

    @Test(description = "Verify sendMessage image & text User to user onBehalfOf",dependsOnMethods = {"sendMsgTextUserToUser"})
    public void sendMsgImageTextUserToUser()
    {
        JSONObject userJson = new JSONObject();
        userJson.put("name", "hi.png");
        userJson.put("extension", "png");
        userJson.put("mimeType", "image/png");
        userJson.put("url", "https://data-eu.cometchat.io/assets/images/avatars/ironman.png");

        JSONArray userJsonArray = new JSONArray();
        userJsonArray.put(userJson);

        JSONObject userJson1 = new JSONObject();
        userJson1.put("attachments",userJsonArray);
        userJson1.put("text","Hello Babu, Sending message image & text through automation script");

        JSONObject userJson2 = new JSONObject();
        userJson2.put("receiver","superhero1");
        userJson2.put("receiverType","user");
        userJson2.put("category","message");
        userJson2.put("type","image");
        userJson2.put("data",userJson1);

      String responseBody=getCommon().
                header("onBehalfOf",userUID).
                body(userJson2.toString(1)).
                when().
                post("/messages").
                then().
                assertThat().statusCode(200).extract().body().asString();
        System.out.println(responseBody);

    }

    @Test(description = "Verify sendMessage image & text User to user without onBehalfOf",dependsOnMethods = {"sendMsgTextUserToUser"})
    public void sendMsgImageTextUserToUserWithoutOnBehalfOf()
    {
        JSONObject userJson = new JSONObject();
        userJson.put("name", "hi.png");
        userJson.put("extension", "png");
        userJson.put("mimeType", "image/png");
        userJson.put("url", "https://data-eu.cometchat.io/assets/images/avatars/ironman.png");

        JSONArray userJsonArray = new JSONArray();
        userJsonArray.put(userJson);

        JSONObject userJson1 = new JSONObject();
        userJson1.put("attachments",userJsonArray);
        userJson1.put("text","Hello Babu, Sending message image & text through automation script");

        JSONObject userJson2 = new JSONObject();
        userJson2.put("receiver","superhero1");
        userJson2.put("receiverType","user");
        userJson2.put("category","message");
        userJson2.put("type","image");
        userJson2.put("data",userJson1);

        String responseBody=getCommon().
                body(userJson2.toString(1)).
                when().
                post("/messages").
                then().
                assertThat().statusCode(200).extract().body().asString();
        System.out.println(responseBody);

    }

    @Test(description = "Verify sendMessage image & text User to user with tags onBehalfOf",dependsOnMethods = {"sendMsgTextUserToUser"})
    public void sendMsgImageTextUserToUserTags()
    {
        JSONObject userJson = new JSONObject();
        userJson.put("name", "hi.png");
        userJson.put("extension", "png");
        userJson.put("mimeType", "image/png");
        userJson.put("url", "https://data-eu.cometchat.io/assets/images/avatars/ironman.png");

        JSONArray userJsonArray = new JSONArray();
        userJsonArray.put(userJson);

        JSONObject userJson1 = new JSONObject();
        userJson1.put("attachments",userJsonArray);
        userJson1.put("text","Hello Babu, Sending message image & text through automation script");


        JSONObject userJson2 = new JSONObject();
        userJson2.put("receiver","superhero1");
        userJson2.put("receiverType","user");
        userJson2.put("category","message");
        userJson2.put("type","image");
        userJson2.put("tags",new String[]{"tag1","tag2"});
        userJson2.put("data",userJson1);

       String responseBody=getCommon().
               header("onBehalfOf",userUID).
                body(userJson2.toString(1)).
                when().
                post("/messages").
                then().
                assertThat().statusCode(200).extract().body().asString();
        System.out.println(responseBody);

    }

    @Test(description = "Verify sendMessage image & text User to user with tags without onBehalfOf",dependsOnMethods = {"sendMsgTextUserToUser"})
    public void sendMsgImageTextUserToUserTagsWithoutOnBehalfOf()
    {
        JSONObject userJson = new JSONObject();
        userJson.put("name", "hi.png");
        userJson.put("extension", "png");
        userJson.put("mimeType", "image/png");
        userJson.put("url", "https://data-eu.cometchat.io/assets/images/avatars/ironman.png");

        JSONArray userJsonArray = new JSONArray();
        userJsonArray.put(userJson);

        JSONObject userJson1 = new JSONObject();
        userJson1.put("attachments",userJsonArray);
        userJson1.put("text","Hello Babu, Sending message image & text through automation script");

        JSONObject userJson2 = new JSONObject();
        userJson2.put("receiver","superhero1");
        userJson2.put("receiverType","user");
        userJson2.put("category","message");
        userJson2.put("type","image");
        userJson2.put("tags",new String[]{"tag1","tag2"});
        userJson2.put("data",userJson1);

       String responseBody=getCommon().
                body(userJson2.toString(1)).
                when().
                post("/messages").
                then().
                assertThat().statusCode(200).extract().body().asString();
        System.out.println(responseBody);

    }

    //------------------------sendMessage text user to MultiUser-------------------------

    @Test(description = "Verify sendMessage text User to MultiUser onBehalfOf",dependsOnMethods = {"sendMsgTextUserToUser"})
    public void sendMsgTextUserToMultiUser()
    {
        JSONObject userJson1 = new JSONObject();
        userJson1.put("text","Hello Babu, Sending message image & text  to MultiUser through automation script");

        JSONObject userJson = new JSONObject();
        userJson.put("uids",new String[]{"superhero1","superhero2"});

        JSONObject userJson2 = new JSONObject();
        userJson2.put("multipleReceivers",userJson);
        userJson2.put("receiverType","user");
        userJson2.put("receiver","superhero1000");
        userJson2.put("category","message");
        userJson2.put("type","text");
        userJson2.put("data",userJson1);

        String responseBody=getCommon().
                header("onBehalfOf",userUID).
                body(userJson2.toString(1)).
                when().
                post("/messages").
                then().
                assertThat().statusCode(200).extract().body().asString();
        System.out.println(responseBody);

    }

    @Test(description = "Verify sendMessage text User to MultiUser without onBehalfOf",dependsOnMethods = {"sendMsgTextUserToUser"})
    public void sendMsgTextUserToMultiUserWithoutOnBehalfOf()
    {
        JSONObject userJson1 = new JSONObject();
        userJson1.put("text","Hello Babu, Sending message image & text through automation script");

        JSONObject userJson = new JSONObject();
        userJson.put("uids",new String[]{"superhero1","superhero2"});

        JSONObject userJson2 = new JSONObject();
        userJson2.put("multipleReceivers",userJson);
       // userJson2.put("receiverType","user");
        userJson2.put("category","message");
        userJson2.put("type","text");
        userJson2.put("data",userJson1);

        String responseBody=getCommon().
                body(userJson2.toString(1)).
                when().
                post("/messages").
                then().
                assertThat().statusCode(200).extract().body().asString();
        System.out.println(responseBody);

    }

    @Test(description = "Verify sendMessage text User to MultiUser with tags onBehalfOf",dependsOnMethods = {"sendMsgTextUserToUser"})
    public void sendMsgTextUserToMultiUserTags()
    {
        JSONObject userJson1 = new JSONObject();
        userJson1.put("text","Hello Babu, Sending message image & text through automation script");

        JSONObject userJson = new JSONObject();
        userJson.put("uids",new String[]{"superhero1","superhero2"});

        JSONObject userJson2 = new JSONObject();
        userJson2.put("multipleReceivers",userJson);
        userJson2.put("receiverType","user");
        userJson2.put("receiver","superhero1000");
        userJson2.put("category","message");
        userJson2.put("type","text");
        userJson2.put("tags",new String[]{"tag1","tag2"});
        userJson2.put("data",userJson1);

        String responseBody=getCommon().
                header("onBehalfOf",userUID).
                body(userJson2.toString(1)).
                when().
                post("/messages").
                then().
                assertThat().statusCode(200).extract().body().asString();
        System.out.println(responseBody);

    }

    @Test(description = "Verify sendMessage text User to MultiUser with tags without onBehalfOf",dependsOnMethods = {"sendMsgTextUserToUser"})
    public void sendMsgTextUserToMultiUserTagsWithoutOnBehalfOf()
    {
        JSONObject userJson1 = new JSONObject();
        userJson1.put("text","Hello Babu, Sending message image & text through automation script");

        JSONObject userJson = new JSONObject();
        userJson.put("uids",new String[]{"superhero1","superhero2"});

        JSONObject userJson2 = new JSONObject();
        userJson2.put("multipleReceivers",userJson);
        userJson2.put("receiverType","user");
        userJson2.put("category","message");
        userJson2.put("type","text");
        userJson2.put("tags",new String[]{"tag1","tag2"});
        userJson2.put("data",userJson1);

        String responseBody=getCommon().
                body(userJson2.toString(1)).
                when().
                post("/messages").
                then().
                assertThat().statusCode(200).extract().body().asString();
        System.out.println(responseBody);

    }

    //------------------------sendMessage image user to MultiUser-------------------------


    @Test(description = "Verify sendMessage image User to multiUser onBehalfOf",dependsOnMethods = {"sendMsgTextUserToUser"})
    public void sendMsgImageUserToMultiUser()
    {
        JSONObject userJson = new JSONObject();
        userJson.put("name", "hi.png");
        userJson.put("extension", "png");
        userJson.put("mimeType", "image/png");
        userJson.put("url", "https://data-eu.cometchat.io/assets/images/avatars/ironman.png");

        JSONArray userJsonArray = new JSONArray();
        userJsonArray.put(userJson);

        JSONObject userJson1 = new JSONObject();
        userJson1.put("attachments",userJsonArray);

        JSONObject userJson3 = new JSONObject();
        userJson3.put("uids",new String[]{"superhero2","superhero3"});

        JSONObject userJson2 = new JSONObject();
        userJson2.put("receiver","superhero1");
        userJson2.put("multipleReceivers",userJson3);
        userJson2.put("receiverType","user");
        userJson2.put("receiver","superhero1000");
        userJson2.put("category","message");
        userJson2.put("type","image");
        userJson2.put("data",userJson1);

        String responseBody=getCommon().
                header("onBehalfOf",userUID).
                body(userJson2.toString(1)).
                when().
                post("/messages").
                then().
                assertThat().statusCode(200).extract().body().asString();
        System.out.println(responseBody);

    }

    @Test(description = "Verify sendMessage image User to multiUser with tags onBehalfOf",dependsOnMethods = {"sendMsgTextUserToUser"})
    public void sendMsgImageUserToMultiUserWithTags()
    {
        JSONObject userJson = new JSONObject();
        userJson.put("name", "hi.png");
        userJson.put("extension", "png");
        userJson.put("mimeType", "image/png");
        userJson.put("url", "https://data-eu.cometchat.io/assets/images/avatars/ironman.png");

        JSONArray userJsonArray = new JSONArray();
        userJsonArray.put(userJson);

        JSONObject userJson3 = new JSONObject();
        userJson3.put("uids",new String[]{"superhero1","superhero2"});

        JSONObject userJson1 = new JSONObject();
        userJson1.put("attachments",userJsonArray);

        JSONObject userJson2 = new JSONObject();
        userJson2.put("multipleReceivers",userJson3);
        userJson2.put("receiverType","user");
        userJson2.put("receiver","superhero1000");
        userJson2.put("category","message");
        userJson2.put("type","image");
        userJson2.put("tags",new String[]{"tag1","tag2"});
        userJson2.put("data",userJson1);

      String responseBody=getCommon().
                header("onBehalfOf",userUID).
                body(userJson2.toString(1)).
                when().
                post("/messages").
                then().
                assertThat().statusCode(200).extract().body().asString();
        System.out.println(responseBody);

    }

    @Test(description = "Verify sendMessage image User to multiUser without onBehalfOf",dependsOnMethods = {"sendMsgTextUserToUser"})
    public void sendMsgImageUserToMultiUserWithOutOnBehalfOf()
    {
        JSONObject userJson = new JSONObject();
        userJson.put("name", "hi.png");
        userJson.put("extension", "png");
        userJson.put("mimeType", "image/png");
        userJson.put("url", "https://data-eu.cometchat.io/assets/images/avatars/ironman.png");

        JSONArray userJsonArray = new JSONArray();
        userJsonArray.put(userJson);

        JSONObject userJson1 = new JSONObject();
        userJson1.put("attachments",userJsonArray);

        JSONObject userJson3 = new JSONObject();
        userJson3.put("uids",new String[]{"superhero2","superhero3"});

        JSONObject userJson2 = new JSONObject();
        userJson2.put("receiver","superhero1");
        userJson2.put("multipleReceivers",userJson3);
        userJson2.put("receiverType","user");
        userJson2.put("category","message");
        userJson2.put("type","image");
        userJson2.put("data",userJson1);

        String responseBody=getCommon().
                body(userJson2.toString(1)).
                when().
                post("/messages").
                then().
                assertThat().statusCode(200).extract().body().asString();
        System.out.println(responseBody);

    }

    @Test(description = "Verify sendMessage image User to MultiUser with tags without onBehalfOf",dependsOnMethods = {"sendMsgTextUserToUser"})
    public void sendMsgImageUserToMultiUserTagsWithOutOnBehalfOf()
    {
        JSONObject userJson = new JSONObject();
        userJson.put("name", "hi.png");
        userJson.put("extension", "png");
        userJson.put("mimeType", "image/png");
        userJson.put("url", "https://data-eu.cometchat.io/assets/images/avatars/ironman.png");

        JSONArray userJsonArray = new JSONArray();
        userJsonArray.put(userJson);

        JSONObject userJson3 = new JSONObject();
        userJson3.put("uids",new String[]{"superhero1","superhero2"});

        JSONObject userJson1 = new JSONObject();
        userJson1.put("attachments",userJsonArray);

        JSONObject userJson2 = new JSONObject();
        userJson2.put("multipleReceivers",userJson3);
        userJson2.put("category","message");
        userJson2.put("type","image");
        userJson2.put("tags",new String[]{"tag1","tag2"});
        userJson2.put("data",userJson1);

        String responseBody=getCommon().
                body(userJson2.toString(1)).
                when().
                post("/messages").
                then().
                assertThat().statusCode(200).extract().body().asString();
        System.out.println(responseBody);

    }

    //------------------------sendMessage image & text user to MultiUser-------------------------

    @Test(description = "Verify sendMessage image & text User to MultiUser onBehalfOf",dependsOnMethods = {"sendMsgTextUserToUser"})
    public void sendMsgImageTextUserToMultiUser()
    {
        JSONObject userJson = new JSONObject();
        userJson.put("name", "hi.png");
        userJson.put("extension", "png");
        userJson.put("mimeType", "image/png");
        userJson.put("url", "https://data-eu.cometchat.io/assets/images/avatars/ironman.png");

        JSONArray userJsonArray = new JSONArray();
        userJsonArray.put(userJson);

        JSONObject userJson3 = new JSONObject();
        userJson3.put("uids",new String[]{"superhero1","superhero2"});

        JSONObject userJson1 = new JSONObject();
        userJson1.put("attachments",userJsonArray);
        userJson1.put("text","Hello Babu,sending message image & text through automation");

        JSONObject userJson2 = new JSONObject();
        userJson2.put("multipleReceivers",userJson3);
      //  userJson2.put("receiverType","user");
        userJson2.put("category","message");
        userJson2.put("type","image");
        userJson2.put("data",userJson1);

        String responseBody=getCommon().
                header("onBehalfOf",userUID).
                body(userJson2.toString(1)).
                when().
                post("/messages").
                then().
                assertThat().statusCode(200).extract().body().asString();
        System.out.println(responseBody);

    }

    @Test(description = "Verify sendMessage image & text User to MultiUser without onBehalfOf",dependsOnMethods = {"sendMsgTextUserToUser"})
    public void sendMsgImageTextUserToMultiUserWithoutOnBehalfOf()
    {
        JSONObject userJson = new JSONObject();
        userJson.put("name", "hi.png");
        userJson.put("extension", "png");
        userJson.put("mimeType", "image/png");
        userJson.put("url", "https://data-eu.cometchat.io/assets/images/avatars/ironman.png");

        JSONArray userJsonArray = new JSONArray();
        userJsonArray.put(userJson);

        JSONObject userJson3 = new JSONObject();
        userJson3.put("uids",new String[]{"superhero1","superhero2"});

        JSONObject userJson1 = new JSONObject();
        userJson1.put("attachments",userJsonArray);
        userJson1.put("text","Hello Babu,sending message image & text through automation");

        JSONObject userJson2 = new JSONObject();
        userJson2.put("multipleReceivers",userJson3);
        userJson2.put("receiverType","user");
        userJson2.put("receiver","superhero1000");
        userJson2.put("category","message");
        userJson2.put("type","image");
        userJson2.put("data",userJson1);

        String responseBody=getCommon().
                body(userJson2.toString(1)).
                when().
                post("/messages").
                then().
                assertThat().statusCode(200).extract().body().asString();
        System.out.println(responseBody);

    }

    @Test(description = "Verify sendMessage image & text User to MultiUser with tags onBehalfOf",dependsOnMethods = {"sendMsgTextUserToUser"})
    public void sendMsgImageTextUserToMultiUserTags()
    {
        JSONObject userJson = new JSONObject();
        userJson.put("name", "hi.png");
        userJson.put("extension", "png");
        userJson.put("mimeType", "image/png");
        userJson.put("url", "https://data-eu.cometchat.io/assets/images/avatars/ironman.png");

        JSONArray userJsonArray = new JSONArray();
        userJsonArray.put(userJson);

        JSONObject userJson3 = new JSONObject();
        userJson3.put("uids",new String[]{"superhero1","superhero2"});

        JSONObject userJson1 = new JSONObject();
        userJson1.put("attachments",userJsonArray);
        userJson1.put("text","Hello Babu, Sending message image & text through automation script");

        JSONObject userJson2 = new JSONObject();
        userJson2.put("multipleReceivers",userJson3);
        userJson2.put("receiverType","user");
        userJson2.put("receiver","superhero1000");
        userJson2.put("category","message");
        userJson2.put("type","image");
        userJson2.put("tags",new String[]{"tag1","tag2"});
        userJson2.put("data",userJson1);

        String responseBody=getCommon().
                header("onBehalfOf",userUID).
                body(userJson2.toString(1)).
                when().
                post("/messages").
                then().
                assertThat().statusCode(200).extract().body().asString();
        System.out.println(responseBody);

    }

    @Test(description = "Verify sendMessage image & text User to MultiUser with tags without onBehalfOf",dependsOnMethods = {"sendMsgTextUserToUser"})
    public void sendMsgImageTextUserToMultiUserTagsWithoutOnBehalfOf()
    {
        JSONObject userJson = new JSONObject();
        userJson.put("name", "hi.png");
        userJson.put("extension", "png");
        userJson.put("mimeType", "image/png");
        userJson.put("url", "https://data-eu.cometchat.io/assets/images/avatars/ironman.png");

        JSONArray userJsonArray = new JSONArray();
        userJsonArray.put(userJson);

        JSONObject userJson3 = new JSONObject();
        userJson3.put("uids",new String[]{"superhero1","superhero2"});

        JSONObject userJson1 = new JSONObject();
        userJson1.put("attachments",userJsonArray);
        userJson1.put("text","Hello Babu, Sending message image & text through automation script");

        JSONObject userJson2 = new JSONObject();
        userJson2.put("multipleReceivers",userJson3);
        userJson2.put("category","message");
        userJson2.put("type","image");
        userJson2.put("tags",new String[]{"tag1","tag2"});
        userJson2.put("data",userJson1);

      String responseBody=getCommon().
                body(userJson2.toString(1)).
                when().
                post("/messages").
                then().
                assertThat().statusCode(200).extract().body().asString();
        System.out.println(responseBody);

    }

    //------------------------sendMessage text user to Group-------------------------

    @Test(description = "Verify sendMessage text User to Group onBehalfOf",dependsOnMethods = {"sendMsgImageTextUserToMultiUserTagsWithoutOnBehalfOf"})
    public void sendMsgTextUserToGroup()
    {
        JSONObject userJson1 = new JSONObject();
        userJson1.put("text","Hello Babu,Message is sending to group through automation script");

        JSONObject userJson = new JSONObject();
        userJson.put("receiverType","group");
        userJson.put("receiver",PublicGroupGUID);      //create a new group for receiver
        userJson.put("type","text");
        userJson.put("category","message");
        userJson.put("data",userJson1);

        String responseBody=getCommon().
                header("onBehalfOf",userUID).
                body(userJson.toString(1)).
                when().
                post("/messages").
                then().
                assertThat().statusCode(200).extract().body().asString();
        System.out.println(responseBody);

        PathFinder(responseBody);
        PublicGroupMsgID = js.getString("data.id"); // Accessing PublicGroupMsgID for msgID for group conversation

    }

    @Test(description = "Verify sendMessage text with tags User to Group onBehalfOf",dependsOnMethods = {"sendMsgImageTextUserToMultiUserTagsWithoutOnBehalfOf"})
    public void sendMsgTextUserToGroupWithTags()
    {
        JSONObject userJson1 = new JSONObject();
        userJson1.put("text","Hello Babu,Message is sending to group through automation script");

        JSONObject userJson = new JSONObject();
        userJson.put("receiverType","group");
        userJson.put("receiver",PublicGroupGUID);             //create a new group for receiver
        userJson.put("tags",new String[]{"tag1","tag2"});
        userJson.put("type","text");
        userJson.put("category","message");
        userJson.put("data",userJson1);

        String responseBody=getCommon().
                header("onBehalfOf",userUID).
                body(userJson.toString(1)).
                when().
                post("/messages").
                then().
                assertThat().statusCode(200).extract().body().asString();
        System.out.println(responseBody);
    }

    @Test(description = "Verify sendMessage text User to Group without onBehalfOf",dependsOnMethods = {"sendMsgImageTextUserToMultiUserTagsWithoutOnBehalfOf"})
    public void sendMsgTextUserToGroupWithOutOnBehalfOf()
    {
        JSONObject userJson1 = new JSONObject();
        userJson1.put("text","Hello Babu,Message is sending to group through automation script");

        JSONObject userJson = new JSONObject();
        userJson.put("receiverType","group");
        userJson.put("receiver",PublicGroupGUID);    //create a new group for receiver
        userJson.put("type","text");
        userJson.put("category","message");
        userJson.put("data",userJson1);

        String responseBody=getCommon().
                body(userJson.toString(1)).
                when().
                post("/messages").
                then().
                assertThat().statusCode(200).extract().body().asString();
        System.out.println(responseBody);

    }

    @Test(description = "Verify sendMessage text with tags User to Group without onBehalfOf",dependsOnMethods = {"sendMsgImageTextUserToMultiUserTagsWithoutOnBehalfOf"})
    public void sendMsgTextUserToGroupWithTagsWithOutOnBehalfOf()
    {
        JSONObject userJson1 = new JSONObject();
        userJson1.put("text","Hello Babu,Message is sending to group through automation script");

        JSONObject userJson = new JSONObject();
        userJson.put("receiverType","group");
        userJson.put("receiver",PublicGroupGUID);    //create a new group for receiver
        userJson.put("tags",new String[]{"tag1","tag2"});
        userJson.put("type","text");
        userJson.put("category","message");
        userJson.put("data",userJson1);

        String responseBody=getCommon().
                body(userJson.toString(1)).
                when().
                post("/messages").
                then().
                assertThat().statusCode(200).extract().body().asString();
        System.out.println(responseBody);
    }

    //------------------------sendMessage image user to Group-------------------------

    @Test(description = "Verify sendMessage image User to Group onBehalfOf",dependsOnMethods = {"sendMsgImageTextUserToMultiUserTagsWithoutOnBehalfOf"})
    public void sendMsgImageUserToGroup()
    {
        JSONObject userJson = new JSONObject();
        userJson.put("name", "hi.png");
        userJson.put("extension", "png");
        userJson.put("mimeType", "image/png");
        userJson.put("url", "https://data-eu.cometchat.io/assets/images/avatars/ironman.png");

        JSONArray userJsonArray = new JSONArray();
        userJsonArray.put(userJson);

        JSONObject userJson1 = new JSONObject();
        userJson1.put("attachments",userJsonArray);

        JSONObject userJson2 = new JSONObject();
        userJson2.put("receiverType","group");
        userJson2.put("receiver",PublicGroupGUID);   //create a new group for receiver
        userJson2.put("type","image");
        userJson2.put("category","message");
        userJson2.put("data",userJson1);

        String responseBody=getCommon().
                header("onBehalfOf",userUID).
                body(userJson2.toString(1)).
                when().
                post("/messages").
                then().
                assertThat().statusCode(200).extract().body().asString();
        System.out.println(responseBody);

    }

    @Test(description = "Verify sendMessage image User to Group with tags onBehalfOf",dependsOnMethods = {"sendMsgImageTextUserToMultiUserTagsWithoutOnBehalfOf"})
    public void sendMsgImageUserToGroupWithTags()
    {
        JSONObject userJson = new JSONObject();
        userJson.put("name", "hi.png");
        userJson.put("extension", "png");
        userJson.put("mimeType", "image/png");
        userJson.put("url", "https://data-eu.cometchat.io/assets/images/avatars/ironman.png");

        JSONArray userJsonArray = new JSONArray();
        userJsonArray.put(userJson);

        JSONObject userJson1 = new JSONObject();
        userJson1.put("attachments",userJsonArray);

        JSONObject userJson2 = new JSONObject();
        userJson2.put("receiverType","group");
        userJson2.put("receiver",PublicGroupGUID);   //create a new group for receiver
        userJson2.put("tags",new String[]{"tag1","tag2"});
        userJson2.put("type","image");
        userJson2.put("category","message");
        userJson2.put("data",userJson1);

        String responseBody=getCommon().
                header("onBehalfOf",userUID).
                body(userJson2.toString(1)).
                when().
                post("/messages").
                then().
                assertThat().statusCode(200).extract().body().asString();
        System.out.println(responseBody);

    }

    @Test(description = "Verify sendMessage image User to Group without onBehalfOf",dependsOnMethods = {"sendMsgImageTextUserToMultiUserTagsWithoutOnBehalfOf"})
    public void sendMsgImageUserToGroupWithOutOnBehalfOf()
    {
        JSONObject userJson = new JSONObject();
        userJson.put("name", "hi.png");
        userJson.put("extension", "png");
        userJson.put("mimeType", "image/png");
        userJson.put("url", "https://data-eu.cometchat.io/assets/images/avatars/ironman.png");

        JSONArray userJsonArray = new JSONArray();
        userJsonArray.put(userJson);

        JSONObject userJson1 = new JSONObject();
        userJson1.put("attachments",userJsonArray);

        JSONObject userJson2 = new JSONObject();
        userJson2.put("receiverType","group");
        userJson2.put("receiver",PublicGroupGUID);  //create a new group for receiver
        userJson2.put("type","image");
        userJson2.put("category","message");
        userJson2.put("data",userJson1);

        String responseBody=getCommon().
                body(userJson2.toString(1)).
                when().
                post("/messages").
                then().
                assertThat().statusCode(200).extract().body().asString();
        System.out.println(responseBody);

    }

    @Test(description = "Verify sendMessage image User to Group with tags without onBehalfOf",dependsOnMethods = {"sendMsgImageTextUserToMultiUserTagsWithoutOnBehalfOf"})
    public void sendMsgImageUserToGroupTagsWithOutOnBehalfOf()
    {
        JSONObject userJson = new JSONObject();
        userJson.put("name", "hi.png");
        userJson.put("extension", "png");
        userJson.put("mimeType", "image/png");
        userJson.put("url", "https://data-eu.cometchat.io/assets/images/avatars/ironman.png");

        JSONArray userJsonArray = new JSONArray();
        userJsonArray.put(userJson);

        JSONObject userJson1 = new JSONObject();
        userJson1.put("attachments",userJsonArray);

        JSONObject userJson2 = new JSONObject();
        userJson2.put("receiverType","group");
        userJson2.put("receiver",PublicGroupGUID);          //create a new group for receiver
        userJson2.put("tags",new String[]{"tag1","tag2"});
        userJson2.put("type","image");
        userJson2.put("category","message");
        userJson2.put("data",userJson1);

       String responseBody=getCommon().
                body(userJson2.toString(1)).
                when().
                post("/messages").
                then().
                assertThat().statusCode(200).extract().body().asString();
        System.out.println(responseBody);

    }

    //------------------------sendMessage image & text user to Group-------------------------

    @Test(description = "Verify sendMessage image & text User to Group onBehalfOf",dependsOnMethods = {"sendMsgImageUserToGroupTagsWithOutOnBehalfOf"})
    public void sendMsgImageTextUserToGroup()
    {
        JSONObject userJson = new JSONObject();
        userJson.put("name", "hi.png");
        userJson.put("extension", "png");
        userJson.put("mimeType", "image/png");
        userJson.put("url", "https://data-eu.cometchat.io/assets/images/avatars/ironman.png");

        JSONArray userJsonArray = new JSONArray();
        userJsonArray.put(userJson);

        JSONObject userJson1 = new JSONObject();
        userJson1.put("attachments",userJsonArray);
        userJson1.put("text","Hello Babu, sending both image & text");

        JSONObject userJson2 = new JSONObject();
        userJson2.put("receiverType","group");
        userJson2.put("receiver",PublicGroupGUID);  //create a new group for receiver
        userJson2.put("type","image");
        userJson2.put("category","message");
        userJson2.put("data",userJson1);

       String responseBody=getCommon().
                header("onBehalfOf",userUID).
                body(userJson2.toString(1)).
                when().log().body().
                post("/messages").
                then().
                assertThat().statusCode(200).extract().body().asString();
        System.out.println(responseBody);

    }

    @Test(description = "Verify sendMessage image & text User to Group without onBehalfOf",dependsOnMethods = {"sendMsgImageUserToGroupTagsWithOutOnBehalfOf"})
    public void sendMsgImageTextUserToGroupWithoutOnBehalfOf()
    {
        JSONObject userJson = new JSONObject();
        userJson.put("name", "hi.png");
        userJson.put("extension", "png");
        userJson.put("mimeType", "image/png");
        userJson.put("url", "https://data-eu.cometchat.io/assets/images/avatars/ironman.png");

        JSONArray userJsonArray = new JSONArray();
        userJsonArray.put(userJson);

        JSONObject userJson1 = new JSONObject();
        userJson1.put("attachments",userJsonArray);
        userJson1.put("text","Hello Babu, sending both image & text");

        JSONObject userJson2 = new JSONObject();
        userJson2.put("receiverType","group");
        userJson2.put("receiver",PublicGroupGUID);
        userJson2.put("type","image");
        userJson2.put("category","message");
        userJson2.put("data",userJson1);

       String responseBody=getCommon().
                body(userJson2.toString(1)).
                when().
                post("/messages").
                then().
                assertThat().statusCode(200).extract().body().asString();
        System.out.println(responseBody);

    }

    @Test(description = "Verify sendMessage image & text User to Group with tags onBehalfOf",dependsOnMethods = {"sendMsgImageUserToGroupTagsWithOutOnBehalfOf"})
    public void sendMsgImageTextUserToGroupTags()
    {
        JSONObject userJson = new JSONObject();
        userJson.put("name", "hi.png");
        userJson.put("extension", "png");
        userJson.put("mimeType", "image/png");
        userJson.put("url", "https://data-eu.cometchat.io/assets/images/avatars/ironman.png");

        JSONArray userJsonArray = new JSONArray();
        userJsonArray.put(userJson);

        JSONObject userJson1 = new JSONObject();
        userJson1.put("attachments",userJsonArray);
        userJson1.put("text","Hello Babu, sending both image & text");

        JSONObject userJson2 = new JSONObject();
        userJson2.put("receiverType","group");
        userJson2.put("receiver",PublicGroupGUID);     //create a new group for receiver
        userJson2.put("tags",new String[]{"tag1","tag2"});
        userJson2.put("type","image");
        userJson2.put("category","message");
        userJson2.put("data",userJson1);

        String responseBody=getCommon().
                header("onBehalfOf",userUID).
                body(userJson2.toString(1)).
                when().
                post("/messages").
                then().
                assertThat().statusCode(200).extract().body().asString();
        System.out.println(responseBody);

    }

    @Test(description = "Verify sendMessage image & text User to Group with tags without onBehalfOf",dependsOnMethods = {"sendMsgImageUserToGroupTagsWithOutOnBehalfOf"})
    public void sendMsgImageTextUserToGroupTagsWithoutOnBehalfOf()
    {
        JSONObject userJson = new JSONObject();
        userJson.put("name", "hi.png");
        userJson.put("extension", "png");
        userJson.put("mimeType", "image/png");
        userJson.put("url", "https://data-eu.cometchat.io/assets/images/avatars/ironman.png");

        JSONArray userJsonArray = new JSONArray();
        userJsonArray.put(userJson);

        JSONObject userJson1 = new JSONObject();
        userJson1.put("attachments",userJsonArray);
        userJson1.put("text","Hello Babu, sending both image & text");

        JSONObject userJson2 = new JSONObject();
        userJson2.put("receiverType","group");
        userJson2.put("receiver",PublicGroupGUID);          //create a new group for receiver
        userJson2.put("tags",new String[]{"tag1","tag2"});
        userJson2.put("type","image");
        userJson2.put("category","message");
        userJson2.put("data",userJson1);

        String responseBody=getCommon().
                body(userJson2.toString(1)).
                when().
                post("/messages").
                then().
                assertThat().statusCode(200).extract().body().asString();
        System.out.println(responseBody);

    }

    //------------------------sendMessage text user to MultiGroup-------------------------

    @Test(description = "Verify sendMessage text User to MultiGroup onBehalfOf",dependsOnMethods = {"sendMsgImageTextUserToGroupTagsWithoutOnBehalfOf"})
    public void sendMsgTextUserToMultiGroup()
    {

        JSONObject userJson1 = new JSONObject();
        userJson1.put("text","Hello Babu, Sending message image & text through automation script");

        JSONObject userJson = new JSONObject();
        userJson.put("guids",new String[]{"supergroup",PublicGroupGUID});  //put here new group

        JSONObject userJson2 = new JSONObject();
        userJson2.put("multipleReceivers",userJson);
        userJson2.put("receiverType","user");
        userJson2.put("category","message");
        userJson2.put("type","text");
        userJson2.put("data",userJson1);

        String responseBody=getCommon().
                header("onBehalfOf",userUID).
                body(userJson2.toString(1)).
                when().log().body().
                post("/messages").
                then().
                assertThat().statusCode(200).extract().body().asString();
        System.out.println(responseBody);
        PathFinder(responseBody);

        // found bug.......................................

    }

    @Test(description = "Verify SendMessage text User to MultiGroup without onBehalfOf",dependsOnMethods = {"sendMsgImageTextUserToGroupTagsWithoutOnBehalfOf"})
    public void sendMsgTextUserToMultiGroupWithoutOnBehalfOf()
    {
        JSONObject userJson1 = new JSONObject();
        userJson1.put("text","Hello Babu, Sending message image & text through automation script");

        JSONObject userJson = new JSONObject();
        userJson.put("guids",new String[]{"supergroup",PublicGroupGUID}); //put here new group

        JSONObject userJson2 = new JSONObject();
        userJson2.put("multipleReceivers",userJson);
      //  userJson2.put("receiverType","user");
        userJson2.put("category","message");
        userJson2.put("type","text");
        userJson2.put("data",userJson1);

       String responseBody=getCommon().
                body(userJson2.toString(1)).
                when().
                post("/messages").
                then().
                assertThat().statusCode(200).extract().body().asString();
        System.out.println(responseBody);

        // found bug -----------------------------------------
    }

    @Test(description = "Verify SendMessage text User to MultiGroup with tags onBehalfOf",dependsOnMethods = {"sendMsgImageTextUserToGroupTagsWithoutOnBehalfOf"})
    public void sendMsgTextUserToMultiGroupTags()
    {

        JSONObject userJson1 = new JSONObject();
        userJson1.put("text","Hello Babu, Sending message image & text through automation script");

        JSONObject userJson = new JSONObject();
        userJson.put("guids",new String[]{"supergroup",PublicGroupGUID});   //put here new group name

        JSONObject userJson2 = new JSONObject();
        userJson2.put("multipleReceivers",userJson);
        userJson2.put("category","message");
        userJson2.put("type","text");
        userJson2.put("tags",new String[]{"tag1","tag2"});
        userJson2.put("data",userJson1);

        String responseBody=getCommon().
                header("onBehalfOf",userUID).
                body(userJson2.toString(1)).
                when().
                post("/messages").
                then().
                assertThat().statusCode(200).extract().body().asString();
        System.out.println(responseBody);

        // found bug -----------------------------------------

    }

    @Test(description = "Verify SendMessage text User to MultiGroup with tags without onBehalfOf",dependsOnMethods = {"sendMsgImageTextUserToGroupTagsWithoutOnBehalfOf"})
    public void sendMsgTextUserToMultiGroupTagsWithoutOnBehalfOf()
    {
        JSONObject userJson1 = new JSONObject();
        userJson1.put("text","Hello Babu, Sending message image & text through automation script");

        JSONObject userJson = new JSONObject();
        userJson.put("guids",new String[]{"supergroup",PublicGroupGUID});  //put here new group name

        JSONObject userJson2 = new JSONObject();
        userJson2.put("multipleReceivers",userJson);
        userJson2.put("category","message");
        userJson2.put("type","text");
        userJson2.put("tags",new String[]{"tag1","tag2"});
        userJson2.put("data",userJson1);

        String responseBody=getCommon().
                body(userJson2.toString(1)).
                when().log().body().
                post("/messages").
                then().
                assertThat().statusCode(200).extract().body().asString();
        System.out.println(responseBody);

        // found bug ------------------------------------------

    }

    //------------------------sendMessage image user to MultiGroup-------------------------

    @Test(description = "Verify sendMessage image User to MultiGroup onBehalfOf",dependsOnMethods = {"sendMsgImageTextUserToGroupTagsWithoutOnBehalfOf"})
    public void sendMsgImageUserToMultiGroup()
    {
        JSONObject userJson = new JSONObject();
        userJson.put("name", "hi.png");
        userJson.put("extension", "png");
        userJson.put("mimeType", "image/png");
        userJson.put("url", "https://data-eu.cometchat.io/assets/images/avatars/ironman.png");

        JSONArray userJsonArray = new JSONArray();
        userJsonArray.put(userJson);

        JSONObject userJson1 = new JSONObject();
        userJson1.put("attachments",userJsonArray);

        JSONObject userJson3 = new JSONObject();
        userJson3.put("guids",new String[]{"supergroup",PublicGroupGUID});  //put here new group name

        JSONObject userJson2 = new JSONObject();
        userJson2.put("multipleReceivers",userJson3);
        userJson2.put("category","message");
        userJson2.put("type","image");
        userJson2.put("data",userJson1);

        String responseBody=getCommon().
                header("onBehalfOf",userUID).
                body(userJson2.toString(1)).
                when().
                post("/messages").
                then().
                assertThat().statusCode(200).extract().body().asString();
        System.out.println(responseBody);

        // found bug----------------------------------------------------------

    }

    @Test(description = "Verify sendMessage image User to MultiGroup with tags onBehalfOf",dependsOnMethods = {"sendMsgImageTextUserToGroupTagsWithoutOnBehalfOf"})
    public void sendMsgImageUserToMultiGroupWithTags()
    {
        JSONObject userJson = new JSONObject();
        userJson.put("name", "hi.png");
        userJson.put("extension", "png");
        userJson.put("mimeType", "image/png");
        userJson.put("url", "https://data-eu.cometchat.io/assets/images/avatars/ironman.png");

        JSONArray userJsonArray = new JSONArray();
        userJsonArray.put(userJson);

        JSONObject userJson1 = new JSONObject();
        userJson1.put("attachments",userJsonArray);

        JSONObject userJson3 = new JSONObject();
        userJson3.put("guids",new String[]{"supergroup",PublicGroupGUID});  //put here new group name

        JSONObject userJson2 = new JSONObject();
        userJson2.put("multipleReceivers",userJson3);
        userJson2.put("category","message");
        userJson2.put("type","image");
        userJson2.put("tags",new String[]{"tag1","tag2"});
        userJson2.put("data",userJson1);

        String responseBody=getCommon().
                header("onBehalfOf",userUID).
                body(userJson2.toString(1)).
                when().
                post("/messages").
                then().
                assertThat().statusCode(200).extract().body().asString();
        System.out.println(responseBody);

            // found bug----------------------------------------------------
    }

    @Test(description = "Verify sendMessage image User to MultiGroup without onBehalfOf",dependsOnMethods = {"sendMsgImageTextUserToGroupTagsWithoutOnBehalfOf"})
    public void sendMsgImageUserToMultiGroupWithOutOnBehalfOf()
    {
        JSONObject userJson = new JSONObject();
        userJson.put("name", "hi.png");
        userJson.put("extension", "png");
        userJson.put("mimeType", "image/png");
        userJson.put("url", "https://data-eu.cometchat.io/assets/images/avatars/ironman.png");

        JSONArray userJsonArray = new JSONArray();
        userJsonArray.put(userJson);

        JSONObject userJson1 = new JSONObject();
        userJson1.put("attachments",userJsonArray);

        JSONObject userJson3 = new JSONObject();
        userJson3.put("guids",new String[]{"supergroup",PublicGroupGUID});  //put here new group name

        JSONObject userJson2 = new JSONObject();
        userJson2.put("multipleReceivers",userJson3);
        userJson2.put("category","message");
        userJson2.put("type","image");
        userJson2.put("data",userJson1);

        String responseBody=getCommon().
                body(userJson2.toString(1)).
                when().
                post("/messages").
                then().
                assertThat().statusCode(200).extract().body().asString();
        System.out.println(responseBody);

                // found bug-----------------------------------
    }

    @Test(description = "Verify sendMessage image User to MultiGroup with tags without onBehalfOf",dependsOnMethods = {"sendMsgImageTextUserToGroupTagsWithoutOnBehalfOf"})
    public void sendMsgImageUserToMultiGroupTagsWithOutOnBehalfOf()
    {
        JSONObject userJson = new JSONObject();
        userJson.put("name", "hi.png");
        userJson.put("extension", "png");
        userJson.put("mimeType", "image/png");
        userJson.put("url", "https://data-eu.cometchat.io/assets/images/avatars/ironman.png");

        JSONArray userJsonArray = new JSONArray();
        userJsonArray.put(userJson);

        JSONObject userJson1 = new JSONObject();
        userJson1.put("attachments",userJsonArray);

        JSONObject userJson3 = new JSONObject();
        userJson3.put("guids",new String[]{"supergroup",PublicGroupGUID});  //put here new group name

        JSONObject userJson2 = new JSONObject();
        userJson2.put("multipleReceivers",userJson3);
        userJson2.put("category","message");
        userJson2.put("type","image");
        userJson2.put("tags",new String[]{"tag1","tag2"});
        userJson2.put("data",userJson1);

        String responseBody=getCommon().
                body(userJson2.toString(1)).
                when().
                post("/messages").
                then().
                assertThat().statusCode(200).extract().body().asString();
        System.out.println(responseBody);

        // found bug----------------------------------------------------------

    }

    //------------------------sendMessage image & text user to MultiGroup-------------------------

    @Test(description = "Verify sendMessage image & text User to MultiGroup onBehalfOf",dependsOnMethods = {"sendMsgImageTextUserToGroupTagsWithoutOnBehalfOf"})
    public void sendMsgImageTextUserToMultiGroup()
    {
        JSONObject userJson = new JSONObject();
        userJson.put("name", "hi.png");
        userJson.put("extension", "png");
        userJson.put("mimeType", "image/png");
        userJson.put("url", "https://data-eu.cometchat.io/assets/images/avatars/ironman.png");

        JSONArray userJsonArray = new JSONArray();
        userJsonArray.put(userJson);

        JSONObject userJson1 = new JSONObject();
        userJson1.put("attachments",userJsonArray);
        userJson1.put("text","Hello Babu, Sending message image & text through automation script");

        JSONObject userJson3 = new JSONObject();
        userJson3.put("guids",new String[]{"supergroup",PublicGroupGUID});  //put here new group name

        JSONObject userJson2 = new JSONObject();
        userJson2.put("multipleReceivers",userJson3);
        userJson2.put("category","message");
        userJson2.put("type","image");
        userJson2.put("data",userJson1);

        String responseBody=getCommon().
                header("onBehalfOf",userUID).
                body(userJson2.toString(1)).
                when().log().body().
                post("/messages").
                then().
                assertThat().statusCode(200).extract().body().asString();
        System.out.println(responseBody);

                // found bug-------------------------------------------
    }

    @Test(description = "Verify sendMessage image & text User to MultiGroup without onBehalfOf",dependsOnMethods = {"sendMsgImageTextUserToGroupTagsWithoutOnBehalfOf"})
    public void sendMsgImageTextUserToMultiGroupWithoutOnBehalfOf()
    {
        JSONObject userJson = new JSONObject();
        userJson.put("name", "hi.png");
        userJson.put("extension", "png");
        userJson.put("mimeType", "image/png");
        userJson.put("url", "https://data-eu.cometchat.io/assets/images/avatars/ironman.png");

        JSONArray userJsonArray = new JSONArray();
        userJsonArray.put(userJson);

        JSONObject userJson1 = new JSONObject();
        userJson1.put("attachments",userJsonArray);
        userJson1.put("text","Hello Babu, Sending message image & text through automation script");

        JSONObject userJson3 = new JSONObject();
        userJson3.put("guids",new String[]{"supergroup",PublicGroupGUID});  //put here new group name

        JSONObject userJson2 = new JSONObject();
        userJson2.put("multipleReceivers",userJson3);
        userJson2.put("category","message");
        userJson2.put("type","image");
        userJson2.put("data",userJson1);

        String responseBody=getCommon().
                body(userJson2.toString(1)).
                when().log().body().
                post("/messages").
                then().
                assertThat().statusCode(200).extract().body().asString();
        System.out.println(responseBody);

            // found bug-------------------------------
    }

    @Test(description = "Verify sendMessage image & text User to MultiGroup with tags onBehalfOf",dependsOnMethods = {"sendMsgImageTextUserToGroupTagsWithoutOnBehalfOf"})
    public void sendMsgImageTextUserToMultiGroupTags()
    {
        JSONObject userJson = new JSONObject();
        userJson.put("name", "hi.png");
        userJson.put("extension", "png");
        userJson.put("mimeType", "image/png");
        userJson.put("url", "https://data-eu.cometchat.io/assets/images/avatars/ironman.png");

        JSONArray userJsonArray = new JSONArray();
        userJsonArray.put(userJson);

        JSONObject userJson1 = new JSONObject();
        userJson1.put("attachments",userJsonArray);
        userJson1.put("text","Hello Babu, Sending message image & text through automation script");

        JSONObject userJson3 = new JSONObject();
        userJson3.put("guids",new String[]{"supergroup",PublicGroupGUID});  //put here new group name

        JSONObject userJson2 = new JSONObject();
        userJson2.put("multipleReceivers",userJson3);
        userJson2.put("category","message");
        userJson2.put("type","image");
        userJson2.put("tags",new String[]{"tag1","tag2"});
        userJson2.put("data",userJson1);

        String responseBody=getCommon().
                header("onBehalfOf",userUID).
                body(userJson2.toString(1)).
                when().log().body().
                post("/messages").
                then().
                assertThat().statusCode(200).extract().body().asString();
        System.out.println(responseBody);

        // found bug-------------------------------------------
    }

    @Test(description = "Verify sendMessage image & text User to MultiGroup with tags without onBehalfOf",dependsOnMethods = {"sendMsgImageTextUserToGroupTagsWithoutOnBehalfOf"})
    public void sendMsgImageTextUserToMultiGroupTagsWithoutOnBehalfOf()
    {
        JSONObject userJson = new JSONObject();
        userJson.put("name", "hi.png");
        userJson.put("extension", "png");
        userJson.put("mimeType", "image/png");
        userJson.put("url", "https://data-eu.cometchat.io/assets/images/avatars/ironman.png");

        JSONArray userJsonArray = new JSONArray();
        userJsonArray.put(userJson);

        JSONObject userJson1 = new JSONObject();
        userJson1.put("attachments",userJsonArray);
        userJson1.put("text","Hello Babu, Sending message image & text through automation script");

        JSONObject userJson3 = new JSONObject();
        userJson3.put("guids",new String[]{"supergroup",PublicGroupGUID});  //put here new group name

        JSONObject userJson2 = new JSONObject();
        userJson2.put("multipleReceivers",userJson3);
        userJson2.put("category","message");
        userJson2.put("type","image");
        userJson2.put("tags",new String[]{"tag1","tag2"});
        userJson2.put("data",userJson1);

        String responseBody=getCommon().
                body(userJson2.toString(1)).
                when().log().body().
                post("/messages").
                then().
                assertThat().statusCode(200).extract().body().asString();
        System.out.println(responseBody);

        // found bug-------------------------------------------

    }

    //------------------------sendMessage text user to multipleReceivers [both users & groups]-----------------

    @Test(description = "Verify sendMessage text User to MultipleReceiversUserGroups onBehalfOf",dependsOnMethods = {"sendMsgImageTextUserToGroupTagsWithoutOnBehalfOf"})
    public void sendMsgTextToMultipleReceiversUserGroups()
    {
        JSONObject userJson = new JSONObject();
        userJson.put("uids",new String[]{"superhero1","superhero2"});
        userJson.put("guids",new String[]{"supergroup",PublicGroupGUID});    //put here new group name

        JSONObject userJson1 = new JSONObject();
        userJson1.put("text","Hello Babu, Sending message image & text through automation script");

        JSONObject userJson2 = new JSONObject();
        userJson2.put("category","message");
        userJson2.put("type","text");
        userJson2.put("multipleReceivers",userJson);
        userJson2.put("data",userJson1);

       String responseBody=getCommon().
                header("onBehalfOf",userUID).
                body(userJson2.toString(1)).
                when().
                post("/messages").
                then().
                assertThat().statusCode(200).extract().body().asString();
       System.out.println(responseBody);

       // found bug ------------------------------------------------------

    }


    @Test(description = "Verify sendMessage text User to MultipleReceiversUserGroups without onBehalfOf",dependsOnMethods = {"sendMsgImageTextUserToGroupTagsWithoutOnBehalfOf"})
    public void sendMsgTextToMultipleReceiversUserGroupsWithoutOnBehalfOf()
    {
        JSONObject userJson = new JSONObject();
        userJson.put("uids",new String[]{"superhero1","superhero2"});
        userJson.put("guids",new String[]{"supergroup",PublicGroupGUID});    //put here new group name

        JSONObject userJson1 = new JSONObject();
        userJson1.put("text","Hello Babu, Sending message image & text through automation script");

        JSONObject userJson2 = new JSONObject();
        userJson2.put("category","message");
        userJson2.put("type","text");
        userJson2.put("multipleReceivers",userJson);
        userJson2.put("data",userJson1);

        String responseBody=getCommon().
                body(userJson2.toString(1)).
                when().
                post("/messages").
                then().
                assertThat().statusCode(200).extract().body().asString();
        System.out.println(responseBody);

        // found bug ------------------------------------------------------


    }


    @Test(description = "Verify sendMessage text User to MultipleReceiversUserGroups with tags onBehalfOf",dependsOnMethods = {"sendMsgImageTextUserToGroupTagsWithoutOnBehalfOf"})
    public void sendMsgTextToMultipleReceiversUserGroupsTags()
    {
        JSONObject userJson = new JSONObject();
        userJson.put("uids",new String[]{"superhero1","superhero2"});
        userJson.put("guids",new String[]{"supergroup",PublicGroupGUID});    //put here new group name

        JSONObject userJson1 = new JSONObject();
        userJson1.put("text","Hello Babu, Sending message image & text through automation script");

        JSONObject userJson2 = new JSONObject();
        userJson2.put("category","message");
        userJson2.put("type","text");
        userJson2.put("multipleReceivers",userJson);
        userJson2.put("tags",new String[]{"tag1","tag2"});
        userJson2.put("data",userJson1);

        String responseBody=getCommon().
                header("onBehalfOf",userUID).
                body(userJson2.toString(1)).
                when().log().body().
                post("/messages").
                then().
                assertThat().statusCode(200).extract().body().asString();
        System.out.println(responseBody);

        // found bug ------------------------------------------------------


    }

    @Test(description = "Verify sendMessage text User to MultipleReceiversUserGroups with tags without onBehalfOf",dependsOnMethods = {"sendMsgImageTextUserToGroupTagsWithoutOnBehalfOf"})
    public void sendMsgTextToMultipleReceiversUserGroupsTagsWithoutOnBehalfOf()
    {
        JSONObject userJson = new JSONObject();
        userJson.put("uids",new String[]{"superhero1","superhero2"});
        userJson.put("guids",new String[]{"supergroup",PublicGroupGUID});    //put here new group name

        JSONObject userJson1 = new JSONObject();
        userJson1.put("text","Hello Babu, Sending message image & text through automation script");

        JSONObject userJson2 = new JSONObject();
        userJson2.put("category","message");
        userJson2.put("type","text");
        userJson2.put("multipleReceivers",userJson);
        userJson2.put("tags",new String[]{"tag1","tag2"});
        userJson2.put("data",userJson1);

        String responseBody=getCommon().
                body(userJson2.toString(1)).
                when().log().body().
                post("/messages").
                then().
                assertThat().statusCode(200).extract().body().asString();
        System.out.println(responseBody);

        // found bug ------------------------------------------------------

    }

    //------------------------sendMessage image user to multipleReceivers [both users & groups]-----------------

    @Test(description = "Verify sendMessage image User to MultipleReceiversUserGroups onBehalfOf",dependsOnMethods = {"sendMsgImageTextUserToGroupTagsWithoutOnBehalfOf"})
    public void sendMsgImageToMultipleReceiversUserGroups()
    {
        JSONObject userJson = new JSONObject();
        userJson.put("uids",new String[]{"superhero1","superhero2"});
        userJson.put("guids",new String[]{"supergroup",PublicGroupGUID});    //put here new group name

        JSONObject userJson3 = new JSONObject();
        userJson3.put("name", "hi.png");
        userJson3.put("extension", "png");
        userJson3.put("mimeType", "image/png");
        userJson3.put("url", "https://data-eu.cometchat.io/assets/images/avatars/ironman.png");

        JSONArray userJsonArray = new JSONArray();
        userJsonArray.put(userJson3);

        JSONObject userJson1 = new JSONObject();
        userJson1.put("attachments",userJsonArray);

        JSONObject userJson2 = new JSONObject();
        userJson2.put("category","message");
        userJson2.put("type","image");
        userJson2.put("multipleReceivers",userJson);
        userJson2.put("data",userJson1);

        String responseBody=getCommon().
                header("onBehalfOf",userUID).
                body(userJson2.toString(1)).
                when().log().body().
                post("/messages").
                then().log().body().
                assertThat().statusCode(200).extract().body().asString();

        // found bug--------------------------------------------------

    }

    @Test(description = "Verify sendMessage image User to MultipleReceiversUserGroups without onBehalfOf",dependsOnMethods = {"sendMsgImageTextUserToGroupTagsWithoutOnBehalfOf"})
    public void sendMsgImageToMultipleReceiversUserGroupsWithoutOnBehalfOf()
    {
        JSONObject userJson = new JSONObject();
        userJson.put("uids",new String[]{"superhero1","superhero2"});
        userJson.put("guids",new String[]{"supergroup",PublicGroupGUID});

        JSONObject userJson3 = new JSONObject();
        userJson3.put("name", "hi.png");
        userJson3.put("extension", "png");
        userJson3.put("mimeType", "image/png");
        userJson3.put("url", "https://data-eu.cometchat.io/assets/images/avatars/ironman.png");

        JSONArray userJsonArray = new JSONArray();
        userJsonArray.put(userJson3);

        JSONObject userJson1 = new JSONObject();
        userJson1.put("attachments",userJsonArray);

        JSONObject userJson2 = new JSONObject();
        userJson2.put("category","message");
        userJson2.put("type","image");
        userJson2.put("multipleReceivers",userJson);
        userJson2.put("data",userJson1);

        String responseBody=getCommon().
                body(userJson2.toString(1)).
                when().log().body().
                post("/messages").
                then().log().body().
                assertThat().statusCode(200).extract().body().asString();

        // found bug-------------------------------------------------------

    }


    @Test(description = "Verify sendMessage image User to MultipleReceiversUserGroups with tags onBehalfOf",dependsOnMethods = {"sendMsgImageTextUserToGroupTagsWithoutOnBehalfOf"})
    public void sendMsgImageToMultipleReceiversUserGroupsTags()
    {

        JSONObject userJson = new JSONObject();
        userJson.put("uids",new String[]{"superhero1","superhero2"});
        userJson.put("guids",new String[]{"supergroup",PublicGroupGUID});

        JSONObject userJson3 = new JSONObject();
        userJson3.put("name", "hi.png");
        userJson3.put("extension", "png");
        userJson3.put("mimeType", "image/png");
        userJson3.put("url", "https://data-eu.cometchat.io/assets/images/avatars/ironman.png");

        JSONArray userJsonArray = new JSONArray();
        userJsonArray.put(userJson3);

        JSONObject userJson1 = new JSONObject();
        userJson1.put("attachments",userJsonArray);

        JSONObject userJson2 = new JSONObject();
        userJson2.put("category","message");
        userJson2.put("type","image");
        userJson2.put("multipleReceivers",userJson);
        userJson2.put("tags",new String[]{"tag1","tag2"});
        userJson2.put("data",userJson1);

        String responseBody=getCommon().
                header("onBehalfOf",userUID).
                body(userJson2.toString(1)).
                when().log().body().
                post("/messages").
                then().log().body().
                assertThat().statusCode(200).extract().body().asString();

        // found bug--------------------------------------------

    }



    @Test(description = "Verify sendMessage image User to MultipleReceiversUserGroups with tags without onBehalfOf",dependsOnMethods = {"sendMsgImageTextUserToGroupTagsWithoutOnBehalfOf"})
    public void sendMsgImageToMultipleReceiversUserGroupsTagsWithoutOnBehalfOf()
    {

        JSONObject userJson = new JSONObject();
        userJson.put("uids",new String[]{"superhero1","superhero2"});
        userJson.put("guids",new String[]{"supergroup",PublicGroupGUID});

        JSONObject userJson3 = new JSONObject();
        userJson3.put("name", "hi.png");
        userJson3.put("extension", "png");
        userJson3.put("mimeType", "image/png");
        userJson3.put("url", "https://data-eu.cometchat.io/assets/images/avatars/ironman.png");

        JSONArray userJsonArray = new JSONArray();
        userJsonArray.put(userJson3);

        JSONObject userJson1 = new JSONObject();
        userJson1.put("attachments",userJsonArray);

        JSONObject userJson2 = new JSONObject();
        userJson2.put("category","message");
        userJson2.put("type","image");
        userJson2.put("multipleReceivers",userJson);
        userJson2.put("tags",new String[]{"tag1","tag2"});
        userJson2.put("data",userJson1);

        String responseBody=getCommon().
                body(userJson2.toString(1)).
                when().log().body().
                post("/messages").
                then().log().body().
                assertThat().statusCode(200).extract().body().asString();

        // found bug------------------------------------------------

    }


//------------------------sendMessage image & text user to multipleReceivers [both users & groups]-----------------



    @Test(description = "Verify sendMessage image & text User to MultipleReceiversUserGroups onBehalfOf",dependsOnMethods = {"sendMsgImageTextUserToGroupTagsWithoutOnBehalfOf"})
    public void sendMsgImageTextToMultipleReceiversUserGroups()
    {

        JSONObject userJson = new JSONObject();
        userJson.put("uids",new String[]{"superhero1","superhero2"});
        userJson.put("guids",new String[]{"supergroup",PublicGroupGUID});

        JSONObject userJson3 = new JSONObject();
        userJson3.put("name", "hi.png");
        userJson3.put("extension", "png");
        userJson3.put("mimeType", "image/png");
        userJson3.put("url", "https://data-eu.cometchat.io/assets/images/avatars/ironman.png");

        JSONArray userJsonArray = new JSONArray();
        userJsonArray.put(userJson3);

        JSONObject userJson1 = new JSONObject();
        userJson1.put("text","Hello Babu, Sending message image & text through automation script");
        userJson1.put("attachments",userJsonArray);

        JSONObject userJson2 = new JSONObject();
        userJson2.put("category","message");
        userJson2.put("type","image");
        userJson2.put("multipleReceivers",userJson);
        userJson2.put("data",userJson1);

        String responseBody=getCommon().
                header("onBehalfOf",userUID).
                body(userJson2.toString(1)).
                when().log().body().
                post("/messages").
                then().log().body().
                assertThat().statusCode(200).extract().body().asString();

    }

    @Test(description = "Verify sendMessage image & text User to MultipleReceiversUserGroups without onBehalfOf",dependsOnMethods = {"sendMsgImageTextUserToGroupTagsWithoutOnBehalfOf"})
    public void sendMsgImageTextToMultipleReceiversUserGroupsWithoutOnBehalfOf()
    {

        JSONObject userJson = new JSONObject();
        userJson.put("uids",new String[]{"superhero1","superhero2"});
        userJson.put("guids",new String[]{"supergroup",PublicGroupGUID});

        JSONObject userJson3 = new JSONObject();
        userJson3.put("name", "hi.png");
        userJson3.put("extension", "png");
        userJson3.put("mimeType", "image/png");
        userJson3.put("url", "https://data-eu.cometchat.io/assets/images/avatars/ironman.png");

        JSONArray userJsonArray = new JSONArray();
        userJsonArray.put(userJson3);

        JSONObject userJson1 = new JSONObject();
        userJson1.put("text","Hello Babu, Sending message image & text through automation script");
        userJson1.put("attachments",userJsonArray);

        JSONObject userJson2 = new JSONObject();
        userJson2.put("category","message");
        userJson2.put("type","image");
        userJson2.put("multipleReceivers",userJson);
        userJson2.put("data",userJson1);

        String responseBody=getCommon().
                body(userJson2.toString(1)).
                when().log().body().
                post("/messages").
                then().log().body().
                assertThat().statusCode(200).extract().body().asString();

    }


    @Test(description = "Verify sendMessage image & text User to MultipleReceiversUserGroups with tags onBehalfOf",dependsOnMethods = {"sendMsgImageTextUserToGroupTagsWithoutOnBehalfOf"})
    public void sendMsgImageTextToMultipleReceiversUserGroupsTags()
    {

        JSONObject userJson = new JSONObject();
        userJson.put("uids",new String[]{"superhero1","superhero2"});
        userJson.put("guids",new String[]{"supergroup",PublicGroupGUID});

        JSONObject userJson3 = new JSONObject();
        userJson3.put("name", "hi.png");
        userJson3.put("extension", "png");
        userJson3.put("mimeType", "image/png");
        userJson3.put("url", "https://data-eu.cometchat.io/assets/images/avatars/ironman.png");

        JSONArray userJsonArray = new JSONArray();
        userJsonArray.put(userJson3);

        JSONObject userJson1 = new JSONObject();
        userJson1.put("text","Hello Babu, Sending message image & text through automation script");
        userJson1.put("attachments",userJsonArray);

        JSONObject userJson2 = new JSONObject();
        userJson2.put("category","message");
        userJson2.put("type","image");
        userJson2.put("multipleReceivers",userJson);
        userJson2.put("tags",new String[]{"tag1","tag2"});
        userJson2.put("data",userJson1);

        String responseBody=getCommon().
                header("onBehalfOf",userUID).
                body(userJson2.toString(1)).
                when().log().body().
                post("/messages").
                then().log().body().
                assertThat().statusCode(200).extract().body().asString();

    }


    @Test(description = "Verify sendMessage image & text User to MultipleReceiversUserGroups with tags without onBehalfOf",dependsOnMethods = {"sendMsgImageTextUserToGroupTagsWithoutOnBehalfOf"})
    public void sendMsgImageTextToMultipleReceiversUserGroupsTagsWithoutOnBehalfOf()
    {

        JSONObject userJson = new JSONObject();
        userJson.put("uids",new String[]{"superhero1","superhero2"});
        userJson.put("guids",new String[]{"supergroup",PublicGroupGUID});

        JSONObject userJson3 = new JSONObject();
        userJson3.put("name", "hi.png");
        userJson3.put("extension", "png");
        userJson3.put("mimeType", "image/png");
        userJson3.put("url", "https://data-eu.cometchat.io/assets/images/avatars/ironman.png");

        JSONArray userJsonArray = new JSONArray();
        userJsonArray.put(userJson3);

        JSONObject userJson1 = new JSONObject();
        userJson1.put("text","Hello Babu, Sending message image & text through automation script");
        userJson1.put("attachments",userJsonArray);

        JSONObject userJson2 = new JSONObject();
        userJson2.put("category","message");
        userJson2.put("type","image");
        userJson2.put("multipleReceivers",userJson);
        userJson2.put("tags",new String[]{"tag1","tag2"});
        userJson2.put("data",userJson1);

        String responseBody=getCommon().
                body(userJson2.toString(1)).
                when().log().body().
                post("/messages").
                then().log().body().
                assertThat().statusCode(200).extract().body().asString();

    }



      //---------------------------------------------------------------------------


    //-------------------------listMessage/getMessage/updateMessage/deleteMessage-----------------------


    //------------------------listMessage-------------------------

        @Test(description = "Verify listMessage onBehalfOf",dependsOnMethods = {"sendMsgImageTextToMultipleReceiversUserGroupsTagsWithoutOnBehalfOf"})
        public void listMessageOnBehalfOf()
        {
            String responseBody=getCommon().
                    header("onBehalfOf",userUID).
                    when().
                    get("/messages").
                    then().
                    assertThat().statusCode(200).extract().body().asString();
            System.out.println(responseBody);

        }

        @Test(description = "Verify listMessage without onBehalfOf",dependsOnMethods = {"listMessageOnBehalfOf"})
        public void listMessageWithOutOnBehalfOf()
        {
            String responseBody=getCommon().
                    when().
                    get("/messages").
                    then().
                    assertThat().statusCode(200).extract().body().asString();
            System.out.println(responseBody);
        }

        @Test(description = "Verify listMessage invalid user onBehalfOf",dependsOnMethods = {"listMessageWithOutOnBehalfOf"})
        public void listMessageInvalidUser()
        {
            String responseBody=getCommon().
                    header("onBehalfOf","superhero1000").
                    when().
                    get("/messages").
                    then().
                    assertThat().statusCode(404).extract().body().asString();
            System.out.println(responseBody);
        }

        @Test(description = "Verify listMessage searchKey",dependsOnMethods = {"listMessageInvalidUser"})
        public void listMessageBySearchKey()
        {
            String responseBody=getCommon().
                    queryParam("searchKey","hello babu").
                    when().
                    get("/messages").
                    then().
                    assertThat().statusCode(200).extract().body().asString();
            System.out.println(responseBody);
        }

        @Test(description = "Verify listMessage searchKey invalid text",dependsOnMethods = {"listMessageBySearchKey"})
        public void listMessageBySearchKeyInvalidText()
        {
            String responseBody=getCommon().
                    queryParam("searchKey","hello customer4755996").
                    when().
                    get("/messages").
                    then().
                    assertThat().statusCode(200).extract().body().asString();
            System.out.println(responseBody);
        }

        @Test(description = "Verify listMessage receiverType user",dependsOnMethods = {"listMessageBySearchKeyInvalidText"})
        public void listMessageByReceiverTypeUser()
        {
            String responseBody=getCommon().
                    queryParam("receiverType","user").
                    when().
                    get("/messages").
                    then().
                    assertThat().statusCode(200).extract().body().asString();
            System.out.println(responseBody);
        }

        @Test(description = "Verify listMessage receiverType group",dependsOnMethods = {"listMessageByReceiverTypeUser"})
        public void listMessageByReceiverTypeGroup()
        {
            String responseBody=getCommon().
                    queryParam("receiverType","group").
                    when().
                    get("/messages").
                    then().
                    assertThat().statusCode(200).extract().body().asString();
            System.out.println(responseBody);
        }

        @Test(description = "Verify listMessage receiverType invalid",dependsOnMethods = {"listMessageByReceiverTypeGroup"})
        public void listMessageByReceiverTypeInvalid()
        {
            String responseBody=getCommon().
                    queryParam("receiverType","customer1000").
                    when().
                    get("/messages").
                    then().
                    assertThat().statusCode(200).extract().body().asString();
            System.out.println(responseBody);
        }

        @Test(description = "Verify listMessage id",dependsOnMethods = {"listMessageByReceiverTypeInvalid"})
        public void listMessageById()
        {
            String responseBody=getCommon().
                    queryParam("id",20).
                    when().
                    get("/messages").
                    then().
                    assertThat().statusCode(200).extract().body().asString();
            System.out.println(responseBody);
        }

        @Test(description = "Verify listMessage category",dependsOnMethods = {"listMessageById"})
        public void listMessageByCategory()
        {
            String responseBody=getCommon().
                    queryParam("category","message").
                    when().
                    get("/messages").
                    then().
                    assertThat().statusCode(200).extract().body().asString();
            System.out.println(responseBody);
        }

        @Test(description = "Verify listMessage category invalid",dependsOnMethods = {"listMessageByCategory"})
        public void listMessageByCategoryInvalid()
        {
            String responseBody=getCommon().
                    queryParam("category","messages").
                    when().
                    get("/messages").
                    then().
                    assertThat().statusCode(400).extract().body().asString();
            System.out.println(responseBody);
        }

        @Test(description = "Verify listMessage type image",dependsOnMethods = {"listMessageByCategoryInvalid"})
        public void listMessageByTypeImage()
        {
            String responseBody=getCommon().
                    queryParam("type","image").
                    when().
                    get("/messages").
                    then().
                    assertThat().statusCode(200).extract().body().asString();
            System.out.println(responseBody);
        }

        @Test(description = "Verify listMessage type text",dependsOnMethods = {"listMessageByTypeImage"})
        public void listMessageByTypeText()
        {
            String responseBody=getCommon().
                    queryParam("type","text").
                    when().
                    get("/messages").
                    then().
                    assertThat().statusCode(200).extract().body().asString();
            System.out.println(responseBody);
        }

        @Test(description = "Verify listMessage type invalid",dependsOnMethods = {"listMessageByTypeText"})
        public void listMessageByTypeInvalid()
        {
            String responseBody=getCommon().
                    queryParam("type","texts").
                    when().
                    get("/messages").
                    then().
                    assertThat().statusCode(400).extract().body().asString();
            System.out.println(responseBody);
        }

        @Test(description = "Verify listMessage hideDeleted",dependsOnMethods = {"listMessageByTypeInvalid"})
        public void listMessageByHideDeleted()
        {
            String responseBody=getCommon().
                    queryParam("hideDeleted",true).
                    when().
                    get("/messages").
                    then().
                    assertThat().statusCode(200).extract().body().asString();
            System.out.println(responseBody);
        }

        @Test(description = "Verify listMessage onlyDeleted",dependsOnMethods = {"listMessageByHideDeleted"})
        public void listMessageByOnlyDeleted()
        {
            String responseBody=getCommon().
                    queryParam("onlyDeleted",true).
                    when().
                    get("/messages").
                    then().
                    assertThat().statusCode(200).extract().body().asString();
            System.out.println(responseBody);
        }

        @Test(description = "Verify listMessage limit 100",dependsOnMethods = {"listMessageByOnlyDeleted"})
        public void listMessageByLimit100()
        {
            String responseBody=getCommon().
                    queryParam("limit",100).
                    when().
                    get("/messages").
                    then().
                    assertThat().statusCode(200).extract().body().asString();
            System.out.println(responseBody);
        }

        @Test(description = "Verify listMessage limit 10",dependsOnMethods = {"listMessageByLimit100"})
        public void listMessageByLimit10()
        {
            String responseBody=getCommon().
                    queryParam("limit",10).
                    when().
                    get("/messages").
                    then().
                    assertThat().statusCode(200).extract().body().asString();
            System.out.println(responseBody);
        }

        @Test(description = "Verify listMessage limit 1000",dependsOnMethods = {"listMessageByLimit10"})
        public void listMessageByLimit1000()
        {
            String responseBody=getCommon().
                    queryParam("limit",1000).
                    when().
                    get("/messages").
                    then().
                    assertThat().statusCode(200).extract().body().asString();
            System.out.println(responseBody);
        }

        @Test(description = "Verify listMessage withTags",dependsOnMethods = {"listMessageByLimit1000"})
        public void listMessageByWithTags()
        {
            String responseBody=getCommon().
                    queryParam("withTags",true).
                    when().
                    get("/messages").
                    then().
                    assertThat().statusCode(200).extract().body().asString();
            System.out.println(responseBody);
        }

        @Test(description = "Verify listMessage tags",dependsOnMethods = {"listMessageByWithTags"})
        public void listMessageByTags()
        {
            String responseBody=getCommon().
                    queryParam("withTags",true).
                    queryParam("tags","tag1").
                    when().
                    get("/messages").
                    then().
                    assertThat().statusCode(200).extract().body().asString();
            System.out.println(responseBody);
        }

        @Test(description = "Verify listMessage Timestamp",dependsOnMethods = {"listMessageByTags"})
        public void listMessageByTimestamp()
        {
            String responseBody=getCommon().
                    queryParam("fromTimestamp",1673856125).
                    queryParam("toTimestamp",1673856342).
                    when().
                    get("/messages").
                    then().
                    assertThat().statusCode(200).extract().body().asString();
            System.out.println(responseBody);
        }


        //------------------------getMessage-------------------------


        @Test(description = "Verify getMessage without onBehalfOf " ,dependsOnMethods = {"listMessageByTags"})
        public void getMessageWithoutOnBehalfOf()
        {
            getCommon().
                    when().
                    get("/messages/{id}",userMsgID).
                    then().log().body().
                    statusCode(200);
        }

        @Test(description = "Verify getMessage onBehalfOf " ,dependsOnMethods = {"getMessageWithoutOnBehalfOf"})
        public void getMessageOnBehalfOf()
        {
            getCommon().
                    header("onBehalfOf",userUID).
                     when().
                    get("/messages/{id}",userMsgID).
                    then().log().body().
                    statusCode(200);
        }

        @Test(description = "Verify getMessage invalid user onBehalfOf " ,dependsOnMethods = {"getMessageOnBehalfOf"})
        public void getMessageOnBehalfOfInvalidUser()
        {
            getCommon().
                    header("onBehalfOf","superhero1000").
                    when().
                    get("/messages/{id}",userMsgID).
                    then().log().body().
                    statusCode(404);
        }

        @Test(description = "Verify getMessage not access user onBehalfOf " ,dependsOnMethods = {"getMessageOnBehalfOfInvalidUser"})
        public void getMessageOnBehalfOfNotAccess()
        {
            getCommon().
                    header("onBehalfOf","superhero5").
                    when().
                    get("/messages/{id}",userMsgID).
                    then().log().body().
                    statusCode(403);
        }


        //------------------------updateMessage-------------------------


        @Test(description = "Verify updateMessage without onBehalfOf " ,dependsOnMethods = {"getMessageOnBehalfOfNotAccess"})
        public void updateMessageWithoutOnBehalfOf()
        {
            JSONObject userJson1 = new JSONObject();
            userJson1.put("text","Hii cometChat this message is updated through automation");

            JSONObject userJson = new JSONObject();
            userJson.put("data",userJson1);

            String responseBody=getCommon().
                    body(userJson.toString(1)).
                    when().
                    put("/messages/{id}",userMsgID).
                    then().
                    assertThat().statusCode(200).extract().body().asString();
            System.out.println(responseBody);
        }

        @Test(description = "Verify updateMessage onBehalfOf " ,dependsOnMethods = {"updateMessageWithoutOnBehalfOf"})
        public void updateMessageOnBehalfOf()
        {
            JSONObject userJson1 = new JSONObject();
            userJson1.put("text","Hii cometChat this message is updated through automation");

            JSONObject userJson = new JSONObject();
            userJson.put("data",userJson1);

            String responseBody=getCommon().
                    header("onBehalfOf",userUID).
                    body(userJson.toString(1)).
                    when().
                    put("/messages/{id}",userMsgID).
                    then().
                    assertThat().statusCode(200).extract().body().asString();
            System.out.println(responseBody);
        }


        @Test(description = "Verify updateMessage onBehalfOf invalid user " ,dependsOnMethods = {"updateMessageOnBehalfOf"})
        public void updateMessageOnBehalfOfInvalidUser()
        {
            JSONObject userJson1 = new JSONObject();
            userJson1.put("text","Hii cometChat this message is updated through automation");

            JSONObject userJson = new JSONObject();
            userJson.put("data",userJson1);

            String responseBody=getCommon().
                    header("onBehalfOf","superhero1000").
                    body(userJson.toString(1)).
                    when().
                    put("/messages/{id}",userMsgID).
                    then().
                    assertThat().statusCode(404).extract().body().asString();
            System.out.println(responseBody);
        }

        @Test(description = "Verify updateMessage onBehalfOf not access user " ,dependsOnMethods = {"updateMessageOnBehalfOfInvalidUser"})
        public void updateMessageOnBehalfOfNotAccess()
        {
            JSONObject userJson1 = new JSONObject();
            userJson1.put("text","Hii cometChat this message is updated through automation");

            JSONObject userJson = new JSONObject();
            userJson.put("data",userJson1);

            String responseBody=getCommon().
                    header("onBehalfOf","superhero5").
                    body(userJson.toString(1)).
                    when().
                    put("/messages/{id}",userMsgID).
                    then().
                    assertThat().statusCode(403).extract().body().asString();
            System.out.println(responseBody);
        }


        //------------------------updateMessage with tags-------------------------


        @Test(description = "Verify updateMessage text & tag without onBehalfOf " ,dependsOnMethods = {"updateMessageOnBehalfOfNotAccess"})
        public void updateMessageTagWithoutOnBehalfOf()
        {
            JSONObject userJson1 = new JSONObject();
            userJson1.put("text","Hii cometChat this message is updated through automation");

            JSONObject userJson = new JSONObject();
            userJson.put("data",userJson1);
            userJson.put("tags",new String[]{"updatedTag1","updatedTag2"});

            String responseBody=getCommon().
                    body(userJson.toString(1)).
                    when().
                    put("/messages/{id}",userMsgID).
                    then().
                    assertThat().statusCode(200).extract().body().asString();
            System.out.println(responseBody);
        }

        @Test(description = "Verify updateMessage text & tags onBehalfOf " ,dependsOnMethods = {"updateMessageTagWithoutOnBehalfOf"})
        public void updateMessageTagOnBehalfOf()
        {
            JSONObject userJson1 = new JSONObject();
            userJson1.put("text","Hii cometChat this message is updated through automation");

            JSONObject userJson = new JSONObject();
            userJson.put("data",userJson1);
            userJson.put("tags",new String[]{"updatedTag1","updatedTag2"});

            String responseBody=getCommon().
                    header("onBehalfOf",userUID).
                    body(userJson.toString(1)).
                    when().
                    put("/messages/{id}",userMsgID).
                    then().
                    assertThat().statusCode(200).extract().body().asString();
            System.out.println(responseBody);
        }

        @Test(description = "Verify updateMessage text & empty tags onBehalfOf " ,dependsOnMethods = {"updateMessageTagOnBehalfOf"})
        public void updateMessageEmptyTagOnBehalfOf()
        {
            JSONObject userJson1 = new JSONObject();
            userJson1.put("text","Hii cometChat this message is updated through automation");

            JSONObject userJson = new JSONObject();
            userJson.put("data",userJson1);
            userJson.put("tags",new String[]{ " "});

            String responseBody=getCommon().
                    header("onBehalfOf",userUID).
                    body(userJson.toString(1)).
                    when().
                    put("/messages/{id}",userMsgID).
                    then().
                    assertThat().statusCode(400).extract().body().asString();
            System.out.println(responseBody);
        }


        //------------------------deleteMessage-------------------------
        // deleteMessage has been kept in last



        //------------------------threadMessage text into user-------------------------

        @Test(description = "Verify sendThreadMessage text User to user onBehalfOf",dependsOnMethods = {"updateMessageEmptyTagOnBehalfOf"})
        public void sendThreadMsgTextUserToUser()
        {
            JSONObject userJson1 = new JSONObject();
            userJson1.put("text","Hello customer,This is thread message through automation");

            JSONObject userJson = new JSONObject();
            userJson.put("receiver","superhero1");
            userJson.put("receiverType","user");
            userJson.put("category","message");
            userJson.put("type","text");
            userJson.put("data",userJson1);

            String responseBody=getCommon().
                    header("onBehalfOf",userUID).
                    pathParam("id",userMsgID).
                    body(userJson.toString(1)).
                    when().
                    post("/messages/{id}/thread").
                    then().
                    assertThat().statusCode(200).extract().body().asString();
            System.out.println(responseBody);

        }

        @Test(description = "Verify sendThreadMessage text User to user without onBehalfOf",dependsOnMethods = {"sendThreadMsgTextUserToUser"})
        public void sendThreadMsgTextUserToUserWithoutOnBehalfOf()
        {
            JSONObject userJson1 = new JSONObject();
            userJson1.put("text","Hello customer,This is thread message through automation");

            JSONObject userJson = new JSONObject();
            userJson.put("receiver","superhero1");
            userJson.put("receiverType","user");
            userJson.put("category","message");
            userJson.put("type","text");
            userJson.put("data",userJson1);

            String responseBody=getCommon().
                    pathParam("id",userMsgID).
                    body(userJson.toString(1)).
                    when().
                    post("/messages/{id}/thread").
                    then().log().body().
                    assertThat().statusCode(400).extract().body().asString();
            System.out.println(responseBody);

            // found bug----------------------------

        }

        //------------------------threadMessage text into group-------------------------

        @Test(description = "Verify sendThreadMessage text group onBehalfOf",dependsOnMethods = {"sendThreadMsgTextUserToUserWithoutOnBehalfOf"})
        public void sendThreadMsgTextGroup()
        {
            JSONObject userJson1 = new JSONObject();
            userJson1.put("text","Hello customer,This is thread message through automation");

            JSONObject userJson = new JSONObject();
            userJson.put("receiver",PublicGroupGUID);
            userJson.put("receiverType","group");
            userJson.put("category","message");
            userJson.put("type","text");
            userJson.put("data",userJson1);

            String responseBody=getCommon().
                    header("onBehalfOf",userUID).
                    pathParam("id",PublicGroupMsgID).      // need a group msgID
                    body(userJson.toString(1)).
                    when().
                    post("/messages/{id}/thread").
                    then().
                    assertThat().statusCode(200).extract().body().asString();
            System.out.println(responseBody);

        }

        @Test(description = "Verify sendThreadMessage text group without onBehalfOf",dependsOnMethods = {"sendThreadMsgTextGroup"})
        public void sendThreadMsgTextGroupWithoutOnBehalfOf()
        {
            JSONObject userJson1 = new JSONObject();
            userJson1.put("text","Hello customer,This is thread message through automation");

            JSONObject userJson = new JSONObject();
            userJson.put("receiver",PublicGroupGUID);
            userJson.put("receiverType","group");
            userJson.put("category","message");
            userJson.put("type","text");
            userJson.put("data",userJson1);

            String responseBody=getCommon().
                    pathParam("id",PublicGroupMsgID).   // need a group msgID
                    body(userJson.toString(1)).
                    when().
                    post("/messages/{id}/thread").
                    then().
                    assertThat().statusCode(200).extract().body().asString();
            System.out.println(responseBody);

        }

        //
        //------------------------threadMessage image into user-------------------------

        @Test(description = "Verify sendThreadMessage image User to user onBehalfOf",dependsOnMethods = {"sendThreadMsgTextGroupWithoutOnBehalfOf"})
        public void sendThreadMsgImageUserToUser()
        {
            JSONObject userJson1 = new JSONObject();
            userJson1.put("name", "hi.png");
            userJson1.put("extension", "png");
            userJson1.put("mimeType", "image/png");
            userJson1.put("url", "https://data-eu.cometchat.io/assets/images/avatars/ironman.png");

            JSONArray userJsonArray = new JSONArray();
            userJsonArray.put(userJson1);

            JSONObject userJson2 = new JSONObject();
            userJson2.put("attachments",userJsonArray);

            JSONObject userJson = new JSONObject();
            userJson.put("receiver","superhero1");
            userJson.put("receiverType","user");
            userJson.put("category","message");
            userJson.put("type","image");
            userJson.put("data",userJson2);

            String responseBody=getCommon().
                    header("onBehalfOf",userUID).
                    pathParam("id",userMsgID).
                    body(userJson.toString(1)).
                    when().
                    post("/messages/{id}/thread").
                    then().
                    assertThat().statusCode(200).extract().body().asString();
            System.out.println(responseBody);

        }

        @Test(description = "Verify sendThreadMessage image User to user without onBehalfOf",dependsOnMethods = {"sendThreadMsgImageUserToUser"})
        public void sendThreadMsgImageUserToUserWithoutOnBehalfOf()
        {
            JSONObject userJson1 = new JSONObject();
            userJson1.put("name", "hi.png");
            userJson1.put("extension", "png");
            userJson1.put("mimeType", "image/png");
            userJson1.put("url", "https://data-eu.cometchat.io/assets/images/avatars/ironman.png");

            JSONArray userJsonArray = new JSONArray();
            userJsonArray.put(userJson1);

            JSONObject userJson2 = new JSONObject();
            userJson2.put("attachments",userJsonArray);

            JSONObject userJson = new JSONObject();
            userJson.put("receiver","superhero1");
            userJson.put("receiverType","user");
            userJson.put("category","message");
            userJson.put("type","image");
            userJson.put("data",userJson2);

            String responseBody=getCommon().
                    pathParam("id",userMsgID).
                    body(userJson.toString(1)).
                    when().
                    post("/messages/{id}/thread").
                    then().
                    assertThat().statusCode(400).extract().body().asString();
            System.out.println(responseBody);

            // found bug---------------------------------

        }

        //------------------------threadMessage image into group-------------------------

        @Test(description = "Verify sendThreadMessage text group onBehalfOf",dependsOnMethods = {"sendThreadMsgImageUserToUserWithoutOnBehalfOf"})
        public void sendThreadMsgImageGroup()
        {
            JSONObject userJson1 = new JSONObject();
            userJson1.put("name", "hi.png");
            userJson1.put("extension", "png");
            userJson1.put("mimeType", "image/png");
            userJson1.put("url", "https://data-eu.cometchat.io/assets/images/avatars/ironman.png");

            JSONArray userJsonArray = new JSONArray();
            userJsonArray.put(userJson1);

            JSONObject userJson2 = new JSONObject();
            userJson2.put("attachments",userJsonArray);

            JSONObject userJson = new JSONObject();
            userJson.put("receiver",PublicGroupGUID);  // put group name here
            userJson.put("receiverType","group");
            userJson.put("category","message");
            userJson.put("type","image");
            userJson.put("data",userJson2);

            String responseBody=getCommon().
                    header("onBehalfOf",userUID).
                    pathParam("id",PublicGroupMsgID).      // need a group msgID
                    body(userJson.toString(1)).
                    when().
                    post("/messages/{id}/thread").
                    then().
                    assertThat().statusCode(200).extract().body().asString();
            System.out.println(responseBody);

        }

        @Test(description = "Verify sendThreadMessage text group without onBehalfOf",dependsOnMethods = {"sendThreadMsgImageGroup"})
        public void sendThreadMsgImageGroupWithoutOnBehalfOf()
        {
            JSONObject userJson1 = new JSONObject();
            userJson1.put("name", "hi.png");
            userJson1.put("extension", "png");
            userJson1.put("mimeType", "image/png");
            userJson1.put("url", "https://data-eu.cometchat.io/assets/images/avatars/ironman.png");

            JSONArray userJsonArray = new JSONArray();
            userJsonArray.put(userJson1);

            JSONObject userJson2 = new JSONObject();
            userJson2.put("attachments",userJsonArray);

            JSONObject userJson = new JSONObject();
            userJson.put("receiver",PublicGroupGUID);
            userJson.put("receiverType","group");
            userJson.put("category","message");
            userJson.put("type","image");
            userJson.put("data",userJson2);

            String responseBody=getCommon().
                    pathParam("id",PublicGroupMsgID).   // need a group msgID
                    body(userJson.toString(1)).
                    when().
                    post("/messages/{id}/thread").
                    then().
                    assertThat().statusCode(200).extract().body().asString();
            System.out.println(responseBody);

        }

        // text and image
        //------------------------threadMessage text & image into user-------------------------

        @Test(description = "Verify sendThreadMessage text & image User to user onBehalfOf",dependsOnMethods = {"sendThreadMsgImageGroupWithoutOnBehalfOf"})
        public void sendThreadMsgTextImageUserToUser()
        {
            JSONObject userJson1 = new JSONObject();
            userJson1.put("name", "hi.png");
            userJson1.put("extension", "png");
            userJson1.put("mimeType", "image/png");
            userJson1.put("url", "https://data-eu.cometchat.io/assets/images/avatars/ironman.png");

            JSONArray userJsonArray = new JSONArray();
            userJsonArray.put(userJson1);

            JSONObject userJson2 = new JSONObject();
            userJson2.put("attachments",userJsonArray);
            userJson2.put("text","This is thread message text & image together");

            JSONObject userJson = new JSONObject();
            userJson.put("receiver","superhero1");
            userJson.put("receiverType","user");
            userJson.put("category","message");
            userJson.put("type","image");
            userJson.put("data",userJson2);

            String responseBody=getCommon().
                    header("onBehalfOf",userUID).
                    pathParam("id",userMsgID).
                    body(userJson.toString(1)).
                    when().log().body().
                    post("/messages/{id}/thread").
                    then().log().body().
                    assertThat().statusCode(200).extract().body().asString();
            System.out.println(responseBody);

        }

        @Test(description = "Verify sendThreadMessage text & image User to user without onBehalfOf",dependsOnMethods = {"sendThreadMsgTextImageUserToUser"})
        public void sendThreadMsgTextImageUserToUserWithoutOnBehalfOf()
        {
            JSONObject userJson1 = new JSONObject();
            userJson1.put("name", "hi.png");
            userJson1.put("extension", "png");
            userJson1.put("mimeType", "image/png");
            userJson1.put("url", "https://data-eu.cometchat.io/assets/images/avatars/ironman.png");

            JSONArray userJsonArray = new JSONArray();
            userJsonArray.put(userJson1);

            JSONObject userJson2 = new JSONObject();
            userJson2.put("attachments",userJsonArray);
            userJson2.put("text","This is thread message text & image together");

            JSONObject userJson = new JSONObject();
            userJson.put("receiver","superhero1");
            userJson.put("receiverType","user");
            userJson.put("category","message");
            userJson.put("type","image");
            userJson.put("data",userJson2);

            String responseBody=getCommon().
                    pathParam("id",userMsgID).
                    body(userJson.toString(1)).
                    when().
                    post("/messages/{id}/thread").
                    then().log().body().
                    assertThat().statusCode(400).extract().body().asString();
            System.out.println(responseBody);

            // found bug------------------------------

        }

        //------------------------threadMessage text & image into group-------------------------

        @Test(description = "Verify sendThreadMessage text & image group onBehalfOf",dependsOnMethods = {"sendThreadMsgTextImageUserToUserWithoutOnBehalfOf"})
        public void sendThreadMsgTextImageGroup()
        {
            JSONObject userJson1 = new JSONObject();
            userJson1.put("name", "hi.png");
            userJson1.put("extension", "png");
            userJson1.put("mimeType", "image/png");
            userJson1.put("url", "https://data-eu.cometchat.io/assets/images/avatars/ironman.png");

            JSONArray userJsonArray = new JSONArray();
            userJsonArray.put(userJson1);

            JSONObject userJson2 = new JSONObject();
            userJson2.put("attachments",userJsonArray);
            userJson2.put("text","This is thread message text & image together");

            JSONObject userJson = new JSONObject();
            userJson.put("receiver",PublicGroupGUID);
            userJson.put("receiverType","group");
            userJson.put("category","message");
            userJson.put("type","image");
            userJson.put("data",userJson2);

            String responseBody=getCommon().
                    header("onBehalfOf",userUID).
                    pathParam("id",PublicGroupMsgID).      // need a group msgID
                    body(userJson.toString(1)).
                    when().
                    post("/messages/{id}/thread").
                    then().
                    assertThat().statusCode(200).extract().body().asString();
            System.out.println(responseBody);

        }

        @Test(description = "Verify sendThreadMessage text & image group without onBehalfOf",dependsOnMethods = {"sendThreadMsgTextImageGroup"})
        public void sendThreadMsgTextImageGroupWithoutOnBehalfOf()
        {
            JSONObject userJson1 = new JSONObject();
            userJson1.put("name", "hi.png");
            userJson1.put("extension", "png");
            userJson1.put("mimeType", "image/png");
            userJson1.put("url", "https://data-eu.cometchat.io/assets/images/avatars/ironman.png");

            JSONArray userJsonArray = new JSONArray();
            userJsonArray.put(userJson1);

            JSONObject userJson2 = new JSONObject();
            userJson2.put("attachments",userJsonArray);
            userJson2.put("text","This is thread message text & image together");

            JSONObject userJson = new JSONObject();
            userJson.put("receiver",PublicGroupGUID);
            userJson.put("receiverType","group");
            userJson.put("category","message");
            userJson.put("type","image");
            userJson.put("data",userJson2);

            String responseBody=getCommon().
                    pathParam("id",PublicGroupMsgID).   // need a group msgID
                    body(userJson.toString(1)).
                    when().
                    post("/messages/{id}/thread").
                    then().
                    assertThat().statusCode(200).extract().body().asString();
            System.out.println(responseBody);

        }

        //------------------------listThreadMessage -------------------------

        //------------------------listThreadMessage for user-------------------------


        @Test(description = "Verify listThreadMessage user without onBehalfOf",dependsOnMethods = {"sendThreadMsgTextImageGroupWithoutOnBehalfOf"})
        public void listThreadMsgUserWithoutOnBehalfOf()
        {
            String responseBody=getCommon().
                    pathParam("id",userMsgID).   // Id of the message whose thread messages are to be fetched.
                    when().
                    get("/messages/{id}/thread").
                    then().
                    assertThat().statusCode(200).extract().body().asString();
            System.out.println(responseBody);

        }

        @Test(description = "Verify listThreadMessage user without onBehalfOf",dependsOnMethods = {"listThreadMsgUserWithoutOnBehalfOf"})
        public void listThreadMsgUserOnBehalfOf()
        {
            String responseBody=getCommon().
                    header("onBehalfOf",userUID).
                    pathParam("id",userMsgID).   // need a thread user msgID
                    when().
                    get("/messages/{id}/thread").
                    then().
                    assertThat().statusCode(200).extract().body().asString();
            System.out.println(responseBody);

        }

        //------------------------listThreadMessage for group-------------------------

        @Test(description = "Verify listThreadMessage group without onBehalfOf",dependsOnMethods = {"listThreadMsgUserOnBehalfOf"})
        public void listThreadMsgGroupWithoutOnBehalfOf()
        {
            String responseBody=getCommon().
                    pathParam("id",PublicGroupMsgID).   // need a thread group msgID
                    when().
                    get("/messages/{id}/thread").
                    then().
                    assertThat().statusCode(200).extract().body().asString();
            System.out.println(responseBody);

        }

        @Test(description = "Verify listThreadMessage group without onBehalfOf",dependsOnMethods = {"listThreadMsgGroupWithoutOnBehalfOf"})
        public void listThreadMsgGroupOnBehalfOf()
        {
            String responseBody=getCommon().
                    header("onBehalfOf",userUID).
                    pathParam("id",PublicGroupMsgID).   // need a thread group msgID
                    when().
                    get("/messages/{id}/thread").
                    then().
                    assertThat().statusCode(200).extract().body().asString();
            System.out.println(responseBody);

        }

//------------------------Get User Messages-------------------------


        @Test(description = "Verify getUserMessage  without onBehalfOf",dependsOnMethods = {"listThreadMsgGroupOnBehalfOf"})
        public void getUserMessageWithoutOnBehalfOf()
        {
            String responseBody=getCommon().
                    pathParam("uid","superhero1").
                    when().
                    get("/users/{uid}/messages").
                    then().
                    assertThat().statusCode(200).extract().body().asString();
            System.out.println(responseBody);

        }

        @Test(description = "Verify getUserMessage  onBehalfOf",dependsOnMethods = {"getUserMessageWithoutOnBehalfOf"})
        public void getUserMessageOnBehalfOf()
        {
            String responseBody=getCommon().
                    header("onBehalfOf",userUID).
                    pathParam("uid","superhero1").
                    when().
                    get("/users/{uid}/messages").
                    then().
                    assertThat().statusCode(200).extract().body().asString();
            System.out.println(responseBody);

        }

        @Test(description = "Verify getUserMessage  by searchKey ",dependsOnMethods = {"getUserMessageOnBehalfOf"})
        public void getUserMessageBYSearchKey()
        {
            String responseBody=getCommon().
                    header("onBehalfOf",userUID).
                    queryParam("searchKey","hello babu").
                    pathParam("uid","superhero1").
                    when().
                    get("/users/{uid}/messages").
                    then().
                    assertThat().statusCode(200).extract().body().asString();
            System.out.println(responseBody);

        }

        @Test(description = "Verify getUserMessage  by unread ",dependsOnMethods = {"getUserMessageBYSearchKey"})
        public void getUserMessageByUnread()
        {
            String responseBody=getCommon().
                    header("onBehalfOf",userUID).
                    queryParam("unread",true).
                    pathParam("uid","superhero1").
                    when().
                    get("/users/{uid}/messages").
                    then().
                    assertThat().statusCode(200).extract().body().asString();
            System.out.println(responseBody);

        }

        @Test(description = "Verify getUserMessage by type only text ",dependsOnMethods = {"getUserMessageByUnread"})
        public void getUserMessageByTypeText()
        {
            String responseBody=getCommon().
                    header("onBehalfOf",userUID).
                    queryParam("type","text").
                    pathParam("uid","superhero1").
                    when().
                    get("/users/{uid}/messages").
                    then().
                    assertThat().statusCode(200).extract().body().asString();
            System.out.println(responseBody);

        }

        @Test(description = "Verify getUserMessage by type only image ",dependsOnMethods = {"getUserMessageByTypeText"})
        public void getUserMessageByTypeImage()
        {
            String responseBody=getCommon().
                    header("onBehalfOf",userUID).
                    queryParam("type","image").
                    pathParam("uid","superhero1").
                    when().
                    get("/users/{uid}/messages").
                    then().log().body().
                    assertThat().statusCode(200).extract().body().asString();
            System.out.println(responseBody);

            // found bug----------------------------------

        }

        @Test(description = "Verify getUserMessage by category only image ",dependsOnMethods = {"getUserMessageByTypeImage"})
        public void getUserMessageByCategory()
        {
            String responseBody=getCommon().
                    header("onBehalfOf",userUID).
                    queryParam("category","message").
                    pathParam("uid","superhero1").
                    when().
                    get("/users/{uid}/messages").
                    then().
                    assertThat().statusCode(200).extract().body().asString();
            System.out.println(responseBody);

        }

        @Test(description = "Verify getUserMessage by perPage ",dependsOnMethods = {"getUserMessageByCategory"})
        public void getUserMessageByPerPage()
        {
            String responseBody=getCommon().
                    header("onBehalfOf",userUID).
                    queryParam("perPage",99).
                    pathParam("uid","superhero1").
                    when().
                    get("/users/{uid}/messages").
                    then().
                    assertThat().statusCode(200).extract().body().asString();
            System.out.println(responseBody);

        }

        @Test(description = "Verify getUserMessage by limit ",dependsOnMethods = {"getUserMessageByPerPage"})
        public void getUserMessageByLimit()
        {
            String responseBody=getCommon().
                    header("onBehalfOf",userUID).
                    queryParam("limit",200).
                    pathParam("uid","superhero1").
                    when().
                    get("/users/{uid}/messages").
                    then().
                    assertThat().statusCode(200).extract().body().asString();
            System.out.println(responseBody);

        }

        //------------------------Get Group Messages-------------------------


        @Test(description = "Verify getGroupMessage  without onBehalfOf",dependsOnMethods = {"getUserMessageByLimit"})
        public void getGroupMessageWithoutOnBehalfOf()
        {
            String responseBody=getCommon().
                    pathParam("guid",PublicGroupGUID).
                    when().
                    get("/groups/{guid}/messages").
                    then().
                    assertThat().statusCode(200).extract().body().asString();
            System.out.println(responseBody);

        }

        @Test(description = "Verify getGroupMessage  onBehalfOf",dependsOnMethods = {"getGroupMessageWithoutOnBehalfOf"})
        public void getGroupMessageOnBehalfOf()
        {
            String responseBody=getCommon().
                    header("onBehalfOf",userUID).
                    pathParam("guid",PublicGroupGUID).
                    when().
                    get("/groups/{guid}/messages").
                    then().
                    assertThat().statusCode(200).extract().body().asString();
            System.out.println(responseBody);

        }

        @Test(description = "Verify getGroupMessage  by searchKey ",dependsOnMethods = {"getGroupMessageOnBehalfOf"})
        public void getGroupMessageBYSearchKey()
        {
            String responseBody=getCommon().
                    header("onBehalfOf",userUID).
                    queryParam("searchKey","hello babu").
                    pathParam("guid",PublicGroupGUID).
                    when().
                    get("/groups/{guid}/messages").
                    then().
                    assertThat().statusCode(200).extract().body().asString();
            System.out.println(responseBody);

        }

        @Test(description = "Verify getGroupMessage  by unread ",dependsOnMethods = {"getGroupMessageBYSearchKey"})
        public void getGroupMessageByUnread()
        {
            String responseBody=getCommon().
                    header("onBehalfOf",userUID).
                    queryParam("unread",true).
                    pathParam("guid",PublicGroupGUID).
                    when().
                    get("/groups/{guid}/messages").
                    then().
                    assertThat().statusCode(200).extract().body().asString();
            System.out.println(responseBody);

        }

        @Test(description = "Verify getGroupMessage by type only text ",dependsOnMethods = {"getGroupMessageByUnread"})
        public void getGroupMessageByTypeText()
        {
            String responseBody=getCommon().
                    header("onBehalfOf",userUID).
                    queryParam("type","text").
                    pathParam("guid",PublicGroupGUID).
                    when().
                    get("/groups/{guid}/messages").
                    then().
                    assertThat().statusCode(200).extract().body().asString();
            System.out.println(responseBody);

        }

        @Test(description = "Verify getGroupMessage by type only image ",dependsOnMethods = {"getGroupMessageByTypeText"})
        public void getGroupMessageByTypeImage()
        {
            String responseBody=getCommon().
                    header("onBehalfOf",userUID).
                    queryParam("type","image").
                    pathParam("guid",PublicGroupGUID).
                    when().
                    get("/groups/{guid}/messages").
                    then().
                    assertThat().statusCode(200).extract().body().asString();
            System.out.println(responseBody);

        }

        @Test(description = "Verify getGroupMessage by category only image ",dependsOnMethods = {"getGroupMessageByTypeImage"})
        public void getGroupMessageByCategory()
        {
            String responseBody=getCommon().
                    header("onBehalfOf",userUID).
                    queryParam("category","message").
                    pathParam("guid",PublicGroupGUID).
                    when().
                    get("/groups/{guid}/messages").
                    then().
                    assertThat().statusCode(200).extract().body().asString();
            System.out.println(responseBody);

        }

        @Test(description = "Verify getGroupMessage by perPage ",dependsOnMethods = {"getGroupMessageByCategory"})
        public void getGroupMessageByPerPage()
        {
            String responseBody=getCommon().
                    header("onBehalfOf",userUID).
                    queryParam("perPage",99).
                    pathParam("guid",PublicGroupGUID).
                    when().
                    get("/groups/{guid}/messages").
                    then().
                    assertThat().statusCode(200).extract().body().asString();
            System.out.println(responseBody);

        }

        @Test(description = "Verify getGroupMessage by limit ",dependsOnMethods = {"getGroupMessageByPerPage"})
        public void getGroupMessageByLimit()
        {
            String responseBody=getCommon().
                    header("onBehalfOf",userUID).
                    queryParam("limit",200).
                    pathParam("guid",PublicGroupGUID).
                    when().
                    get("/groups/{guid}/messages").
                    then().
                    assertThat().statusCode(200).extract().body().asString();
            System.out.println(responseBody);

        }

       //-----------------------------deleteMessage----------------------------------

    @Test(description = "Verify deleteMessage without onBehalfOf " ,dependsOnMethods = {"getGroupMessageByLimit"})
    public void deleteMessageWithoutOnBehalfOf()
    {
        JSONObject userJson = new JSONObject();
        userJson.put("permanent",false);

        String responseBody=getCommon().
                body(userJson.toString(1)).
                when().
                delete("/messages/{id}",userMsgID).
                then().
                assertThat().statusCode(200).extract().body().asString();
        System.out.println(responseBody);
    }

    @Test(description = "Verify deleteMessage onBehalfOf " ,dependsOnMethods = {"deleteMessageWithoutOnBehalfOf"})
    public void deleteMessageOnBehalfOf()
    {
        JSONObject userJson = new JSONObject();
        userJson.put("permanent",true);

        String responseBody=getCommon().
                header("onBehalfOf",userUID).
                body(userJson.toString(1)).
                when().
                delete("/messages/{id}",userMsgID).
                then().
                assertThat().statusCode(200).extract().body().asString();
        System.out.println(responseBody);
    }


    @Test(description = "Verify deleteMessage onBehalfOf invalid user " ,dependsOnMethods = {"deleteMessageOnBehalfOf"})
    public void deleteMessageOnBehalfOfInvalidUser()
    {
        JSONObject userJson = new JSONObject();
        userJson.put("permanent",false);

        String responseBody=getCommon().
                header("onBehalfOf","superhero1000").
                body(userJson.toString(1)).
                when().
                delete("/messages/{id}",userMsgID).
                then().
                assertThat().statusCode(404).extract().body().asString();
        System.out.println(responseBody);
    }

    @Test(description = "Verify deleteMessage onBehalfOf not access user " ,dependsOnMethods = {"deleteMessageOnBehalfOfInvalidUser"})
    public void deleteMessageOnBehalfOfNotAccess()
    {
        String responseBody=getCommon().
                header("onBehalfOf","superhero5").
                when().
                delete("/messages/{id}",userMsgID).
                then().
                assertThat().statusCode(404).extract().body().asString();
        System.out.println(responseBody);
    }






      //  -------------------------------------------------------------------------------


    @Test(description = "Verify delete User for SendMessage",dependsOnMethods = {"deleteMessageOnBehalfOfNotAccess"})
    public void deleteUser()
    {
        JSONObject userJson = new JSONObject();
        userJson.put("permanent",true);
        getCommon().
                body(userJson.toString(1)).
                when().
                delete("/users/"+userUID).
                then().log().body().
                statusCode(200);
    }

    @Test(description = "Verify deleteGroups for group public",dependsOnMethods = {"deleteUser"})
    public void deleteGroupsPublic()
    {
        String responseBody= getCommon().
                when().
                delete("/groups/" + PublicGroupGUID).
                then().
                assertThat().statusCode(200).extract().body().asString();
        System.out.println(responseBody);
    }



    }










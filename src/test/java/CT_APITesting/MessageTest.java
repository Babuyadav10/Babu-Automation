package CT_APITesting;

import io.restassured.specification.RequestSpecification;
import org.json.JSONArray;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;
import static io.restassured.RestAssured.given;

public class MessageTest extends BaseTest{

    public static String userUID;
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
        UsersTest a= new UsersTest();
               a.createUser();
        userUID= UsersTest.createdUserId;
         System.out.println(userUID);

        JSONObject userJson1 = new JSONObject();
        userJson1.put("text","Hello Babu,Message is sending through automation script");
        userJson1.put("category","message");
        userJson1.put("type","text");

         JSONObject userJson = new JSONObject();
        userJson.put("receiver","superhero1");
        userJson.put("receiverType","user");
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
        Assert.assertEquals(js.getString("data.data.text"),"Hello Babu,Message is sending through automation script");

    }

    @Test(description = "Verify sendMessage text with tags User to user onBehalfOf")
    public void sendMsgTextUserToUserWithTags()
    {
        JSONObject userJson1 = new JSONObject();
        userJson1.put("text","Hello Babu,Message is sending through automation script with tags");
        userJson1.put("category","message");
        userJson1.put("type","text");
        userJson1.put("tags",new String[] {"tag1","tag2"});

        JSONObject userJson = new JSONObject();
        userJson.put("receiver","superhero1");
        userJson.put("receiverType","user");
        userJson.put("data",userJson1);

        String responseBody=getCommon().
               // header("onBehalfOf",userUID).
                body(userJson.toString(1)).
                when().
                post("/messages").
                then().
                assertThat().statusCode(200).extract().body().asString();
        System.out.println(responseBody);
        PathFinder(responseBody);
        Assert.assertEquals(js.getString("data.data.tags[0]"),"tag1");

    }

    @Test(description = "Verify sendMessage text User to user without onBehalfOf")
    public void sendMsgTextUserToUserWithOutOnBehalfOf()
    {
        JSONObject userJson1 = new JSONObject();
        userJson1.put("text","Hello Babu,Message is sending through automation script without onBehalfOf");
        userJson1.put("category","message");
        userJson1.put("type","text");

        JSONObject userJson = new JSONObject();
        userJson.put("receiver","superhero1");
        userJson.put("receiverType","user");
        userJson.put("data",userJson1);

        String responseBody=getCommon().
                body(userJson.toString(1)).
                when().
                post("/messages").
                then().
                assertThat().statusCode(200).extract().body().asString();
        System.out.println(responseBody);
        PathFinder(responseBody);
        Assert.assertEquals(js.getString("data.data.text"),"Hello Babu,Message is sending through automation script without onBehalfOf");

    }

    @Test(description = "Verify sendMessage text with tags User to user without onBehalfOf")
    public void sendMsgTextUserToUserWithTagsWithOutOnBehalfOf()
    {
        JSONObject userJson1 = new JSONObject();
        userJson1.put("text","Hello Babu,Message is sending through automation script with tags without onBehalfOf");
        userJson1.put("category","message");
        userJson1.put("type","text");
        userJson1.put("tags",new String[]{"tag1","tag2"});

        JSONObject userJson = new JSONObject();
        userJson.put("receiver","superhero1");
        userJson.put("receiverType","user");
        userJson.put("data",userJson1);

       String responseBody=getCommon().
                body(userJson.toString(1)).
                when().
                post("/messages").
                then().
                assertThat().statusCode(200).extract().body().asString();
        System.out.println(responseBody);
        PathFinder(responseBody);
        Assert.assertEquals(js.getString("data.data.text"),"Hello Babu,Message is sending through automation script with tags without onBehalfOf");

    }

    //------------------------sendMessage image user to user-------------------------

    @Test(description = "Verify sendMessage image User to user onBehalfOf")
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
               // header("onBehalfOf",userUID).
                body(userJson2.toString(1)).
                when().
                post("/messages").
                then().
                assertThat().statusCode(200).extract().body().asString();
        System.out.println(responseBody);
        PathFinder(responseBody);
       Assert.assertEquals(js.getString("data.type"),"image");

    }

    @Test(description = "Verify sendMessage image User to user with tags onBehalfOf")
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
        userJson1.put("tags",new String[]{"tag1","tag2"});

        JSONObject userJson2 = new JSONObject();
        userJson2.put("receiver","superhero1");
        userJson2.put("receiverType","user");
        userJson2.put("category","message");
        userJson2.put("type","image");
        userJson2.put("data",userJson1);

       String responseBody=getCommon().
                // header("onBehalfOf",userUID).
                        body(userJson2.toString(1)).
                when().
                post("/messages").
                then().
                assertThat().statusCode(200).extract().body().asString();
        System.out.println(responseBody);
        PathFinder(responseBody);
       Assert.assertEquals(js.getString("data.data.tags[0]"),"tag1");

    }

    @Test(description = "Verify sendMessage image User to user without onBehalfOf")
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
        PathFinder(responseBody);
        Assert.assertEquals(js.getString("data.type"),"image");

    }

    @Test(description = "Verify sendMessage image User to user with tags without onBehalfOf")
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
        userJson1.put("tags",new String[]{"tag1","tag2"});

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
        PathFinder(responseBody);
        Assert.assertEquals(js.getString("data.data.tags[0]"),"tag1");

    }

//------------------------sendMessage image & text user to user-------------------------

    @Test(description = "Verify sendMessage image & text User to user onBehalfOf")
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
                // header("onBehalfOf",userUID).
                body(userJson2.toString(1)).
                when().
                post("/messages").
                then().
                assertThat().statusCode(200).extract().body().asString();
        System.out.println(responseBody);
        PathFinder(responseBody);
        Assert.assertEquals(js.getString("data.data.text"),"Hello Babu, Sending message image & text through automation script");

    }

    @Test(description = "Verify SendMessage image & text User to user without onBehalfOf")
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
        PathFinder(responseBody);
        Assert.assertEquals(js.getString("data.data.text"),"Hello Babu, Sending message image & text through automation script");

    }

    @Test(description = "Verify sendMessage image & text User to user with tags onBehalfOf")
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
        userJson1.put("tags",new String[]{"tag1","tag2"});

        JSONObject userJson2 = new JSONObject();
        userJson2.put("receiver","superhero1");
        userJson2.put("receiverType","user");
        userJson2.put("category","message");
        userJson2.put("type","image");
        userJson2.put("data",userJson1);

       String responseBody=getCommon().
                // header("onBehalfOf",userUID).
                body(userJson2.toString(1)).
                when().
                post("/messages").
                then().
                assertThat().statusCode(200).extract().body().asString();
        System.out.println(responseBody);
        PathFinder(responseBody);
        Assert.assertEquals(js.getString("data.data.tags[0]"),"tag1");

    }

    @Test(description = "Verify sendMessage image & text User to user with tags without onBehalfOf")
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
        userJson1.put("tags",new String[]{"tag1","tag2"});

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
        PathFinder(responseBody);
        Assert.assertEquals(js.getString("data.data.tags[1]"),"tag2");

    }

    //------------------------sendMessage text user to MultiUser-------------------------

    @Test(description = "Verify sendMessage text User to MultiUser onBehalfOf")
    public void sendMsgTextUserToMultiUser()
    {
        JSONObject userJson1 = new JSONObject();
        userJson1.put("text","Hello Babu, Sending message image & text  to MultiUser through automation script");

        JSONObject userJson = new JSONObject();
        userJson.put("uids",new String[]{"superhero1","superhero2"});

        JSONObject userJson2 = new JSONObject();
        userJson2.put("multipleReceivers",userJson);
        userJson2.put("receiverType","user");
        userJson2.put("category","message");
        userJson2.put("type","text");
        userJson2.put("data",userJson1);

        String responseBody=getCommon().
                // header("onBehalfOf",userUID).
                        body(userJson2.toString(1)).
                when().
                post("/messages").
                then().
                assertThat().statusCode(200).extract().body().asString();
        System.out.println(responseBody);
        PathFinder(responseBody);
        Assert.assertEquals(js.getString("data.uids.superhero1.type"),"text");
        Assert.assertEquals(js.getString("data.uids.superhero2.type"),"text");

    }

    @Test(description = "Verify sendMessage text User to MultiUser without onBehalfOf")
    public void sendMsgTextUserToMultiUserWithoutOnBehalfOf()
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
        userJson2.put("data",userJson1);

        String responseBody=getCommon().
                body(userJson2.toString(1)).
                when().
                post("/messages").
                then().
                assertThat().statusCode(200).extract().body().asString();
        System.out.println(responseBody);
        PathFinder(responseBody);
        Assert.assertEquals(js.getString("data.uids.superhero2.data.text"),"Hello Babu, Sending message image & text through automation script");

    }

    @Test(description = "Verify sendMessage text User to MultiUser with tags onBehalfOf")
    public void sendMsgTextUserToMultiUserTags()
    {
        JSONObject userJson1 = new JSONObject();
        userJson1.put("text","Hello Babu, Sending message image & text through automation script");
        userJson1.put("tags",new String[]{"tag1","tag2"});

        JSONObject userJson = new JSONObject();
        userJson.put("uids",new String[]{"superhero1","superhero2"});

        JSONObject userJson2 = new JSONObject();
        userJson2.put("multipleReceivers",userJson);
        userJson2.put("receiverType","user");
        userJson2.put("category","message");
        userJson2.put("type","text");
        userJson2.put("data",userJson1);

        String responseBody=getCommon().
                // header("onBehalfOf",userUID).
                        body(userJson2.toString(1)).
                when().
                post("/messages").
                then().
                assertThat().statusCode(200).extract().body().asString();
        System.out.println(responseBody);
        PathFinder(responseBody);
        Assert.assertEquals(js.getString("data.uids.superhero1.data.tags[0]"),"tag1");

    }

    @Test(description = "Verify sendMessage text User to MultiUser with tags without onBehalfOf")
    public void sendMsgTextUserToMultiUserTagsWithoutOnBehalfOf()
    {
        JSONObject userJson1 = new JSONObject();
        userJson1.put("text","Hello Babu, Sending message image & text through automation script");
        userJson1.put("tags",new String[]{"tag1","tag2"});

        JSONObject userJson = new JSONObject();
        userJson.put("uids",new String[]{"superhero1","superhero2"});

        JSONObject userJson2 = new JSONObject();
        userJson2.put("multipleReceivers",userJson);
        userJson2.put("receiverType","user");
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
        PathFinder(responseBody);
        Assert.assertEquals(js.getString("data.uids.superhero1.data.text"),"Hello Babu, Sending message image & text through automation script");

    }

    //------------------------sendMessage image user to MultiUser-------------------------


    @Test(description = "Verify sendMessage image User to multiUser onBehalfOf")
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
        userJson2.put("category","message");
        userJson2.put("type","image");
        userJson2.put("data",userJson1);

        String responseBody=getCommon().
                // header("onBehalfOf",userUID).
                        body(userJson2.toString(1)).
                when().
                post("/messages").
                then().
                assertThat().statusCode(200).extract().body().asString();
        System.out.println(responseBody);
        PathFinder(responseBody);
        Assert.assertEquals(js.getString("data.uids.superhero2.type"),"image");
        Assert.assertEquals(js.getString("data.uids.superhero3.type"),"image");
        Assert.assertEquals(js.getString("data.uids.superhero1.type"),"image");

    }

    @Test(description = "Verify sendMessage image User to multiUser with tags onBehalfOf")
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
        userJson1.put("tags",new String[]{"tag1","tag2"});

        JSONObject userJson2 = new JSONObject();
        userJson2.put("multipleReceivers",userJson3);
        userJson2.put("receiverType","user");
        userJson2.put("category","message");
        userJson2.put("type","image");
        userJson2.put("data",userJson1);

      String responseBody=getCommon().
                // header("onBehalfOf",userUID).
                        body(userJson2.toString(1)).
                when().
                post("/messages").
                then().
                assertThat().statusCode(200).extract().body().asString();
        System.out.println(responseBody);
        PathFinder(responseBody);
       Assert.assertEquals(js.getString("data.uids.superhero2.data.tags[0]"),"tag1");

    }

    @Test(description = "Verify sendMessage image User to multiUser without onBehalfOf")
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
        PathFinder(responseBody);
        Assert.assertEquals(js.getString("data.uids.superhero2.type"),"image");
        Assert.assertEquals(js.getString("data.uids.superhero3.type"),"image");
        Assert.assertEquals(js.getString("data.uids.superhero1.type"),"image");

    }

    @Test(description = "Verify sendMessage image User to MultiUser with tags without onBehalfOf")
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
        userJson1.put("tags",new String[]{"tag1","tag2"});

        JSONObject userJson2 = new JSONObject();
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
        PathFinder(responseBody);
        Assert.assertEquals(js.getString("data.uids.superhero2.data.tags[0]"),"tag1");

    }

    //------------------------sendMessage image & text user to MultiUser-------------------------

    @Test(description = "Verify sendMessage image & text User to MultiUser onBehalfOf")
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
        userJson2.put("receiverType","user");
        userJson2.put("category","message");
        userJson2.put("type","image");
        userJson2.put("data",userJson1);

        String responseBody=getCommon().
                // header("onBehalfOf",userUID).
                body(userJson2.toString(1)).
                when().
                post("/messages").
                then().
                assertThat().statusCode(200).extract().body().asString();
        System.out.println(responseBody);
        PathFinder(responseBody);
        Assert.assertEquals(js.getString("data.uids.superhero1.data.text"),"Hello Babu,sending message image & text through automation");
        Assert.assertEquals(js.getString("data.uids.superhero1.data.attachments[0].url"),"https://data-eu.cometchat.io/assets/images/avatars/ironman.png");

    }

    @Test(description = "Verify sendMessage image & text User to MultiUser without onBehalfOf")
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
        PathFinder(responseBody);
        Assert.assertEquals(js.getString("data.uids.superhero1.data.text"),"Hello Babu,sending message image & text through automation");
        Assert.assertEquals(js.getString("data.uids.superhero1.data.attachments[0].url"),"https://data-eu.cometchat.io/assets/images/avatars/ironman.png");

    }

    @Test(description = "Verify sendMessage image & text User to MultiUser with tags onBehalfOf")
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
        userJson1.put("tags",new String[]{"tag1","tag2"});

        JSONObject userJson2 = new JSONObject();
        userJson2.put("multipleReceivers",userJson3);
        userJson2.put("receiverType","user");
        userJson2.put("category","message");
        userJson2.put("type","image");
        userJson2.put("data",userJson1);

        String responseBody=getCommon().
                // header("onBehalfOf",userUID).
                        body(userJson2.toString(1)).
                when().
                post("/messages").
                then().
                assertThat().statusCode(200).extract().body().asString();
        System.out.println(responseBody);
        PathFinder(responseBody);
        Assert.assertEquals(js.getString("data.uids.superhero1.data.tags[0]"),"tag1");

    }

    @Test(description = "Verify sendMessage image & text User to MultiUser with tags without onBehalfOf")
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
        userJson1.put("tags",new String[]{"tag1","tag2"});

        JSONObject userJson2 = new JSONObject();
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
        PathFinder(responseBody);
        Assert.assertEquals(js.getString("data.uids.superhero1.data.tags[1]"),"tag2");

    }

    //------------------------sendMessage text user to Group-------------------------

    @Test(description = "Verify sendMessage text User to Group onBehalfOf")
    public void sendMsgTextUserToGroup()
    {
//        UsersTest a= new UsersTest();
//        a.createUser();
//        userUID= UsersTest.createdUserId;
//        System.out.println(userUID);

        JSONObject userJson1 = new JSONObject();
        userJson1.put("text","Hello Babu,Message is sending to group through automation script");

        JSONObject userJson = new JSONObject();
        userJson.put("receiverType","group");
        userJson.put("receiver","supergroup");
        userJson.put("type","text");
        userJson.put("category","message");
        userJson.put("data",userJson1);

        String responseBody=getCommon().
               // header("onBehalfOf",userUID).
                body(userJson.toString(1)).
                when().
                post("/messages").
                then().
                assertThat().statusCode(200).extract().body().asString();
        System.out.println(responseBody);

    }

    @Test(description = "Verify sendMessage text with tags User to Group onBehalfOf")
    public void sendMsgTextUserToGroupWithTags()
    {
        JSONObject userJson1 = new JSONObject();
        userJson1.put("text","Hello Babu,Message is sending to group through automation script");

        JSONObject userJson = new JSONObject();
        userJson.put("receiverType","group");
        userJson.put("receiver","supergroup");
        userJson.put("tags",new String[]{"tag1","tag2"});
        userJson.put("type","text");
        userJson.put("category","message");
        userJson.put("data",userJson1);

        String responseBody=getCommon().
                // header("onBehalfOf",userUID).
                        body(userJson.toString(1)).
                when().
                post("/messages").
                then().
                assertThat().statusCode(200).extract().body().asString();
        System.out.println(responseBody);
    }

    @Test(description = "Verify sendMessage text User to Group without onBehalfOf")
    public void sendMsgTextUserToGroupWithOutOnBehalfOf()
    {
        JSONObject userJson1 = new JSONObject();
        userJson1.put("text","Hello Babu,Message is sending to group through automation script");

        JSONObject userJson = new JSONObject();
        userJson.put("receiverType","group");
        userJson.put("receiver","supergroup");
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

    @Test(description = "Verify sendMessage text with tags User to Group without onBehalfOf")
    public void sendMsgTextUserToGroupWithTagsWithOutOnBehalfOf()
    {
        JSONObject userJson1 = new JSONObject();
        userJson1.put("text","Hello Babu,Message is sending to group through automation script");

        JSONObject userJson = new JSONObject();
        userJson.put("receiverType","group");
        userJson.put("receiver","supergroup");
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

    @Test(description = "Verify sendMessage image User to Group onBehalfOf")
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
        userJson2.put("receiver","supergroup");
        userJson2.put("type","image");
        userJson2.put("category","message");
        userJson2.put("data",userJson1);

        String responseBody=getCommon().
                // header("onBehalfOf",userUID).
                        body(userJson2.toString(1)).
                when().
                post("/messages").
                then().
                assertThat().statusCode(200).extract().body().asString();
        System.out.println(responseBody);
        PathFinder(responseBody);

    }

    @Test(description = "Verify sendMessage image User to Group with tags onBehalfOf")
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
        userJson2.put("receiver","supergroup");
        userJson2.put("tags",new String[]{"tag1","tag2"});
        userJson2.put("type","image");
        userJson2.put("category","message");
        userJson2.put("data",userJson1);

        String responseBody=getCommon().
                // header("onBehalfOf",userUID).
                        body(userJson2.toString(1)).
                when().
                post("/messages").
                then().
                assertThat().statusCode(200).extract().body().asString();
        System.out.println(responseBody);
        PathFinder(responseBody);

    }

    @Test(description = "Verify sendMessage image User to Group without onBehalfOf")
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
        userJson2.put("receiver","supergroup");
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
        PathFinder(responseBody);

    }

    @Test(description = "Verify sendMessage image User to Group with tags without onBehalfOf")
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
        userJson2.put("receiver","supergroup");
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
        PathFinder(responseBody);

    }

    //------------------------sendMessage image & text user to Group-------------------------

    @Test(description = "Verify sendMessage image & text User to Group onBehalfOf")
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
        userJson2.put("receiver","supergroup");
        userJson2.put("type","image");
        userJson2.put("category","message");
        userJson2.put("data",userJson1);

       String responseBody=getCommon().
                // header("onBehalfOf",userUID).
                        body(userJson2.toString(1)).
                when().
                post("/messages").
                then().
                assertThat().statusCode(200).extract().body().asString();
        System.out.println(responseBody);
        PathFinder(responseBody);


    }

    @Test(description = "Verify sendMessage image & text User to Group without onBehalfOf")
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
        userJson2.put("receiver","supergroup");
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
        PathFinder(responseBody);


    }

    @Test(description = "Verify sendMessage image & text User to Group with tags onBehalfOf")
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
        userJson2.put("receiver","supergroup");
        userJson2.put("tags",new String[]{"tag1","tag2"});
        userJson2.put("type","image");
        userJson2.put("category","message");
        userJson2.put("data",userJson1);

        String responseBody=getCommon().
                // header("onBehalfOf",userUID).
                        body(userJson2.toString(1)).
                when().
                post("/messages").
                then().
                assertThat().statusCode(200).extract().body().asString();
        System.out.println(responseBody);
        PathFinder(responseBody);


    }

    @Test(description = "Verify sendMessage image & text User to Group with tags without onBehalfOf")
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
        userJson2.put("receiver","supergroup");
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
        PathFinder(responseBody);

    }

    //------------------------sendMessage text user to MultiGroup-------------------------

    @Test(description = "Verify sendMessage text User to MultiGroup onBehalfOf")
    public void sendMsgTextUserToMultiGroup()
    {

        JSONObject userJson1 = new JSONObject();
        userJson1.put("text","Hello Babu, Sending message image & text through automation script");

        JSONObject userJson = new JSONObject();
        userJson.put("guids",new String[]{"supergroup","supergroupBabu"});

        JSONObject userJson2 = new JSONObject();
        userJson2.put("multipleReceivers",userJson);
        userJson2.put("receiverType","user");
        userJson2.put("category","message");
        userJson2.put("type","text");
        userJson2.put("data",userJson1);

        String responseBody=getCommon().
                // header("onBehalfOf",userUID).
                        body(userJson2.toString(1)).
                when().log().body().
                post("/messages").
                then().
                assertThat().statusCode(200).extract().body().asString();
        System.out.println(responseBody);
        PathFinder(responseBody);

        // found bug.......................................

    }

    @Test(description = "Verify SendMessage text User to MultiGroup without onBehalfOf")
    public void sendMsgTextUserToMultiGroupWithoutOnBehalfOf()
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
        userJson2.put("data",userJson1);

       String responseBody=getCommon().
                body(userJson2.toString(1)).
                when().
                post("/messages").
                then().
                assertThat().statusCode(200).extract().body().asString();
        System.out.println(responseBody);
        PathFinder(responseBody);

    }

    @Test(description = "Verify SendMessage text User to MultiGroup with tags onBehalfOf")
    public void sendMsgTextUserToMultiGroupTags()
    {

        JSONObject userJson1 = new JSONObject();
        userJson1.put("text","Hello Babu, Sending message image & text through automation script");
        userJson1.put("tags",new String[]{"tag1","tag2"});

        JSONObject userJson = new JSONObject();
        userJson.put("uids",new String[]{"superhero1","superhero2"});

        JSONObject userJson2 = new JSONObject();
        userJson2.put("multipleReceivers",userJson);
        userJson2.put("receiverType","user");
        userJson2.put("category","message");
        userJson2.put("type","text");
        userJson2.put("data",userJson1);

        String responseBody=getCommon().
                // header("onBehalfOf",userUID).
                        body(userJson2.toString(1)).
                when().
                post("/messages").
                then().
                assertThat().statusCode(200).extract().body().asString();
        System.out.println(responseBody);
        PathFinder(responseBody);

    }

    @Test(description = "Verify SendMessage text User to MultiGroup with tags without onBehalfOf")
    public void sendMsgTextUserToMultiGroupTagsWithoutOnBehalfOf()
    {
        JSONObject userJson1 = new JSONObject();
        userJson1.put("text","Hello Babu, Sending message image & text through automation script");
        userJson1.put("tags",new String[]{"tag1","tag2"});

        JSONObject userJson = new JSONObject();
        userJson.put("uids",new String[]{"superhero1","superhero2"});

        JSONObject userJson2 = new JSONObject();
        userJson2.put("multipleReceivers",userJson);
        userJson2.put("receiverType","user");
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
        PathFinder(responseBody);

    }

    //------------------------sendMessage image user to MultiGroup-------------------------

    @Test(description = "Verify SendMessage image User to MultiGroup onBehalfOf")
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

        JSONObject userJson2 = new JSONObject();
        userJson2.put("receiver","superhero1");
        userJson2.put("receiverType","user");
        userJson2.put("category","message");
        userJson2.put("type","image");
        userJson2.put("data",userJson1);

        String responseBody=getCommon().
                // header("onBehalfOf",userUID).
                        body(userJson2.toString(1)).
                when().
                post("/messages").
                then().
                assertThat().statusCode(200).extract().body().asString();
        System.out.println(responseBody);
        PathFinder(responseBody);

    }

    @Test(description = "Verify SendMessage image User to MultiGroup with tags onBehalfOf")
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
        userJson1.put("tags",new String[]{"tag1","tag2"});

        JSONObject userJson2 = new JSONObject();
        userJson2.put("receiver","superhero1");
        userJson2.put("receiverType","user");
        userJson2.put("category","message");
        userJson2.put("type","image");
        userJson2.put("data",userJson1);

       String responseBody=getCommon().
                // header("onBehalfOf",userUID).
                        body(userJson2.toString(1)).
                when().
                post("/messages").
                then().
                assertThat().statusCode(200).extract().body().asString();
        System.out.println(responseBody);
        PathFinder(responseBody);

    }

    @Test(description = "Verify SendMessage image User to MultiGroup without onBehalfOf")
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
        PathFinder(responseBody);

    }

    @Test(description = "Verify SendMessage image User to MultiGroup with tags without onBehalfOf")
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
        userJson1.put("tags",new String[]{"tag1","tag2"});

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
        PathFinder(responseBody);

    }

    //------------------------sendMessage image & text user to MultiGroup-------------------------

    @Test(description = "Verify SendMessage image & text User to MultiGroup onBehalfOf")
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

        JSONObject userJson2 = new JSONObject();
        userJson2.put("receiver","superhero1");
        userJson2.put("receiverType","user");
        userJson2.put("category","message");
        userJson2.put("type","image");
        userJson2.put("data",userJson1);

        String responseBody=getCommon().
                // header("onBehalfOf",userUID).
                        body(userJson2.toString(1)).
                when().
                post("/messages").
                then().
                assertThat().statusCode(200).extract().body().asString();
        System.out.println(responseBody);
        PathFinder(responseBody);

    }

    @Test(description = "Verify SendMessage image & text User to MultiGroup without onBehalfOf")
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
        PathFinder(responseBody);

    }

    @Test(description = "Verify SendMessage image & text User to MultiGroup with tags onBehalfOf")
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
        userJson1.put("tags",new String[]{"tag1","tag2"});

        JSONObject userJson2 = new JSONObject();
        userJson2.put("receiver","superhero1");
        userJson2.put("receiverType","user");
        userJson2.put("category","message");
        userJson2.put("type","image");
        userJson2.put("data",userJson1);

       String responseBody=getCommon().
                // header("onBehalfOf",userUID).
                        body(userJson2.toString(1)).
                when().
                post("/messages").
                then().
                assertThat().statusCode(200).extract().body().asString();
        System.out.println(responseBody);
        PathFinder(responseBody);

    }

    @Test(description = "Verify SendMessage image & text User to MultiGroup with tags without onBehalfOf")
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
        userJson1.put("tags",new String[]{"tag1","tag2"});

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
        PathFinder(responseBody);

    }

    @Test(description = "Verify delete User for SendMessage")
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

    //------------------------sendMessage text user to multipleReceivers [both users & groups]-----------------

  /*
    @Test(description = "Verify sendMessage text User to MultipleReceiversUserGroups onBehalfOf")
    public void sendMsgTextToMultipleReceiversUserGroups()
    {

    }

    @Test(description = "Verify sendMessage text User to MultipleReceiversUserGroups without onBehalfOf")
    public void sendMsgTextToMultipleReceiversUserGroupsWithoutOnBehalfOf()
    {

    }


    @Test(description = "Verify sendMessage text User to MultipleReceiversUserGroups with tags onBehalfOf")
    public void sendMsgTextToMultipleReceiversUserGroupsTags()
    {

    }



    @Test(description = "Verify sendMessage text User to MultipleReceiversUserGroups with tags without onBehalfOf")
    public void sendMsgTextToMultipleReceiversUserGroupsTagsWithoutOnBehalfOf()
    {

    }


//------------------------sendMessage image user to multipleReceivers [both users & groups]-----------------

    @Test(description = "Verify sendMessage image User to MultipleReceiversUserGroups onBehalfOf")
    public void sendMsgImageToMultipleReceiversUserGroups()
    {

    }

    @Test(description = "Verify sendMessage image User to MultipleReceiversUserGroups without onBehalfOf")
    public void sendMsgImageToMultipleReceiversUserGroupsWithoutOnBehalfOf()
    {

    }


    @Test(description = "Verify sendMessage image User to MultipleReceiversUserGroups with tags onBehalfOf")
    public void sendMsgImageToMultipleReceiversUserGroupsTags()
    {

    }



    @Test(description = "Verify sendMessage image User to MultipleReceiversUserGroups with tags without onBehalfOf")
    public void sendMsgImageToMultipleReceiversUserGroupsTagsWithoutOnBehalfOf()
    {

    }


//------------------------sendMessage image & text user to multipleReceivers [both users & groups]-----------------



    @Test(description = "Verify sendMessage image & text User to MultipleReceiversUserGroups onBehalfOf")
    public void sendMsgImageTextToMultipleReceiversUserGroups()
    {

    }

    @Test(description = "Verify sendMessage image & text User to MultipleReceiversUserGroups without onBehalfOf")
    public void sendMsgImageTextToMultipleReceiversUserGroupsWithoutOnBehalfOf()
    {

    }


    @Test(description = "Verify sendMessage image & text User to MultipleReceiversUserGroups with tags onBehalfOf")
    public void sendMsgImageTextToMultipleReceiversUserGroupsTags()
    {

    }


    @Test(description = "Verify sendMessage image & text User to MultipleReceiversUserGroups with tags without onBehalfOf")
    public void sendMsgImageTextToMultipleReceiversUserGroupsTagsWithoutOnBehalfOf()
    {

    }


   */



}



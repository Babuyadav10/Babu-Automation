package CT_APITesting;

import io.restassured.specification.RequestSpecification;
import org.json.JSONArray;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class MessageListTest extends BaseTest{

    public String msgId;
    public RequestSpecification getCommon()
    {
        RequestSpecification httpRequestObject;
        httpRequestObject = given();
        httpRequestObject.header("apiKey",GlobalClassTest.prop.getProperty("apiKey"));
        httpRequestObject.header("Content-Type", "application/json");
        httpRequestObject.header("Accept", "application/json");
        return httpRequestObject;
    }

    //------------------------listMessage-------------------------

    @Test(description = "Verify listMessage onBehalfOf")
    public void listMessageOnBehalfOf()
    {
        String responseBody=getCommon().
              //  header("onBehalfOf",userUID).
                when().
                get("/messages").
                then().
                assertThat().statusCode(200).extract().body().asString();
        System.out.println(responseBody);
        PathFinder(responseBody);
        msgId = js.getString("data[2].id");
    }

    @Test(description = "Verify listMessage without onBehalfOf")
    public void listMessageWithOutOnBehalfOf()
    {
        String responseBody=getCommon().
                when().
                get("/messages").
                then().
                assertThat().statusCode(200).extract().body().asString();
        System.out.println(responseBody);
    }

    @Test(description = "Verify listMessage invalid user onBehalfOf")
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

    @Test(description = "Verify listMessage searchKey")
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

    @Test(description = "Verify listMessage searchKey invalid text")
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

    @Test(description = "Verify listMessage receiverType user")
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

    @Test(description = "Verify listMessage receiverType group")
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

    @Test(description = "Verify listMessage receiverType invalid")
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

    @Test(description = "Verify listMessage id")
    public void listMessageById()
    {
        String responseBody=getCommon().
                queryParam("id","20").
                when().
                get("/messages").
                then().
                assertThat().statusCode(200).extract().body().asString();
        System.out.println(responseBody);
    }

    @Test(description = "Verify listMessage category")
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

    @Test(description = "Verify listMessage category invalid")
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

    @Test(description = "Verify listMessage type image")
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

    @Test(description = "Verify listMessage type text")
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

    @Test(description = "Verify listMessage type invalid")
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

    @Test(description = "Verify listMessage hideDeleted")
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

    @Test(description = "Verify listMessage onlyDeleted")
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

    @Test(description = "Verify listMessage limit 100")
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

    @Test(description = "Verify listMessage limit 10")
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

    @Test(description = "Verify listMessage limit 1000")
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

    @Test(description = "Verify listMessage withTags")
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

    @Test(description = "Verify listMessage tags")
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

    @Test(description = "Verify listMessage Timestamp")
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


    @Test(description = "Verify getMessage without onBehalfOf " ,dependsOnMethods = {"listMessageOnBehalfOf"})
    public void getMessageWithoutOnBehalfOf()
    {
        getCommon().
                when().
                get("/messages/{id}",msgId).
                then().log().body().
                statusCode(200);
    }

    @Test(description = "Verify getMessage onBehalfOf " ,dependsOnMethods = {"listMessageOnBehalfOf"})
    public void getMessageOnBehalfOf()
    {
        getCommon().
               // header("onBehalfOf",userUID);
                when().
                get("/messages/{id}",msgId).
                then().log().body().
                statusCode(200);
    }

    @Test(description = "Verify getMessage invalid user onBehalfOf " ,dependsOnMethods = {"listMessageOnBehalfOf"})
    public void getMessageOnBehalfOfInvalidUser()
    {
        getCommon().
                header("onBehalfOf","superhero100").
                when().
                get("/messages/{id}",msgId).
                then().log().body().
                statusCode(404);
    }

    @Test(description = "Verify getMessage not access user onBehalfOf " ,dependsOnMethods = {"listMessageOnBehalfOf"})
    public void getMessageOnBehalfOfNotAccess()
    {
        getCommon().
                header("onBehalfOf","superhero5").
                when().
                get("/messages/{id}",msgId).
                then().log().body().
                statusCode(403);
    }


    //------------------------updateMessage-------------------------


    @Test(description = "Verify updateMessage without onBehalfOf " ,dependsOnMethods = {"listMessageOnBehalfOf"})
    public void updateMessageWithoutOnBehalfOf()
    {
        JSONObject userJson1 = new JSONObject();
        userJson1.put("text","Hii cometChat this message is updated through automation");

        JSONObject userJson = new JSONObject();
        userJson.put("data",userJson1);

        String responseBody=getCommon().
                body(userJson.toString(1)).
                when().
                put("/messages/{id}",msgId).
                then().
                assertThat().statusCode(200).extract().body().asString();
        System.out.println(responseBody);
    }

    @Test(description = "Verify updateMessage onBehalfOf " ,dependsOnMethods = {"listMessageOnBehalfOf"})
    public void updateMessageOnBehalfOf()
    {
        JSONObject userJson1 = new JSONObject();
        userJson1.put("text","Hii cometChat this message is updated through automation");

        JSONObject userJson = new JSONObject();
        userJson.put("data",userJson1);

        String responseBody=getCommon().
                // header("onBehalfOf",userUID).
                body(userJson.toString(1)).
                when().
                put("/messages/{id}",msgId).
                then().
                assertThat().statusCode(200).extract().body().asString();
        System.out.println(responseBody);
    }


    @Test(description = "Verify updateMessage onBehalfOf invalid user " ,dependsOnMethods = {"listMessageOnBehalfOf"})
    public void updateMessageOnBehalfOfInvalidUser()
    {
        JSONObject userJson1 = new JSONObject();
        userJson1.put("text","Hii cometChat this message is updated through automation");

        JSONObject userJson = new JSONObject();
        userJson.put("data",userJson1);

        String responseBody=getCommon().
                header("onBehalfOf","superhero100").
                body(userJson.toString(1)).
                when().
                put("/messages/{id}",msgId).
                then().
                assertThat().statusCode(404).extract().body().asString();
        System.out.println(responseBody);
    }

    @Test(description = "Verify updateMessage onBehalfOf not access user " ,dependsOnMethods = {"listMessageOnBehalfOf"})
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
                put("/messages/{id}",msgId).
                then().
                assertThat().statusCode(403).extract().body().asString();
        System.out.println(responseBody);
    }


    //------------------------updateMessage with tags-------------------------


    @Test(description = "Verify updateMessage text & tag without onBehalfOf " ,dependsOnMethods = {"listMessageOnBehalfOf"})
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
                put("/messages/{id}",msgId).
                then().
                assertThat().statusCode(200).extract().body().asString();
        System.out.println(responseBody);
    }

    @Test(description = "Verify updateMessage text & tags onBehalfOf " ,dependsOnMethods = {"listMessageOnBehalfOf"})
    public void updateMessageTagOnBehalfOf()
    {
        JSONObject userJson1 = new JSONObject();
        userJson1.put("text","Hii cometChat this message is updated through automation");

        JSONObject userJson = new JSONObject();
        userJson.put("data",userJson1);
        userJson.put("tags",new String[]{"updatedTag1","updatedTag2"});

        String responseBody=getCommon().
                // header("onBehalfOf",userUID).
                        body(userJson.toString(1)).
                when().
                put("/messages/{id}",msgId).
                then().
                assertThat().statusCode(200).extract().body().asString();
        System.out.println(responseBody);
    }

    @Test(description = "Verify updateMessage text & empty tags onBehalfOf " ,dependsOnMethods = {"listMessageOnBehalfOf"})
    public void updateMessageEmptyTagOnBehalfOf()
    {
        JSONObject userJson1 = new JSONObject();
        userJson1.put("text","Hii cometChat this message is updated through automation");

        JSONObject userJson = new JSONObject();
        userJson.put("data",userJson1);
        userJson.put("tags",new String[]{ " "});

        String responseBody=getCommon().
                // header("onBehalfOf",userUID).
                        body(userJson.toString(1)).
                when().
                put("/messages/{id}",msgId).
                then().
                assertThat().statusCode(400).extract().body().asString();
        System.out.println(responseBody);
    }


    //------------------------deleteMessage-------------------------


    @Test(description = "Verify deleteMessage without onBehalfOf " ,dependsOnMethods = {"listMessageOnBehalfOf"})
    public void deleteMessageWithoutOnBehalfOf()
    {
        JSONObject userJson = new JSONObject();
        userJson.put("permanent",false);

        String responseBody=getCommon().
                body(userJson.toString(1)).
                when().
                delete("/messages/{id}",msgId).
                then().
                assertThat().statusCode(200).extract().body().asString();
        System.out.println(responseBody);
    }

    @Test(description = "Verify deleteMessage onBehalfOf " ,dependsOnMethods = {"listMessageOnBehalfOf"})
    public void deleteMessageOnBehalfOf()
    {
        JSONObject userJson = new JSONObject();
        userJson.put("permanent",true);

        String responseBody=getCommon().
                // header("onBehalfOf",userUID).
                        body(userJson.toString(1)).
                when().
                delete("/messages/{id}",msgId).
                then().
                assertThat().statusCode(200).extract().body().asString();
        System.out.println(responseBody);
    }


    @Test(description = "Verify deleteMessage onBehalfOf invalid user " ,dependsOnMethods = {"listMessageOnBehalfOf"})
    public void deleteMessageOnBehalfOfInvalidUser()
    {
        JSONObject userJson = new JSONObject();
        userJson.put("permanent",false);

        String responseBody=getCommon().
                header("onBehalfOf","superhero100").
                body(userJson.toString(1)).
                when().
                delete("/messages/{id}",msgId).
                then().
                assertThat().statusCode(404).extract().body().asString();
        System.out.println(responseBody);
    }

    @Test(description = "Verify deleteMessage onBehalfOf not access user " ,dependsOnMethods = {"listMessageOnBehalfOf"})
    public void deleteMessageOnBehalfOfNotAccess()
    {
        String responseBody=getCommon().
                header("onBehalfOf","superhero5").
                when().
                delete("/messages/{id}",msgId).
                then().
                assertThat().statusCode(403).extract().body().asString();
        System.out.println(responseBody);
    }

    //------------------------threadMessage text into user-------------------------

    @Test(description = "Verify sendThreadMessage text User to user onBehalfOf",dependsOnMethods = {"listMessageOnBehalfOf"})
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
               // header("onBehalfOf",userUID).
                pathParam("id",msgId).
                body(userJson.toString(1)).
                when().
                post("/messages/{id}/thread").
                then().
                assertThat().statusCode(200).extract().body().asString();
        System.out.println(responseBody);

    }

    @Test(description = "Verify sendThreadMessage text User to user without onBehalfOf",dependsOnMethods = {"listMessageOnBehalfOf"})
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
                pathParam("id",msgId).
                body(userJson.toString(1)).
                when().
                post("/messages/{id}/thread").
                then().
                assertThat().statusCode(200).extract().body().asString();
        System.out.println(responseBody);

    }

    //------------------------threadMessage text into group-------------------------

    @Test(description = "Verify sendThreadMessage text group onBehalfOf",dependsOnMethods = {"listMessageOnBehalfOf"})
    public void sendThreadMsgTextGroup()
    {
        JSONObject userJson1 = new JSONObject();
        userJson1.put("text","Hello customer,This is thread message through automation");

        JSONObject userJson = new JSONObject();
        userJson.put("receiver","supergroup");
        userJson.put("receiverType","group");
        userJson.put("category","message");
        userJson.put("type","text");
        userJson.put("data",userJson1);

        String responseBody=getCommon().
                // header("onBehalfOf",userUID).
                        pathParam("id",msgId).      // need a group msgID
                body(userJson.toString(1)).
                when().
                post("/messages/{id}/thread").
                then().
                assertThat().statusCode(200).extract().body().asString();
        System.out.println(responseBody);

    }

    @Test(description = "Verify sendThreadMessage text group without onBehalfOf",dependsOnMethods = {"listMessageOnBehalfOf"})
    public void sendThreadMsgTextGroupWithoutOnBehalfOf()
    {
        JSONObject userJson1 = new JSONObject();
        userJson1.put("text","Hello customer,This is thread message through automation");

        JSONObject userJson = new JSONObject();
        userJson.put("receiver","supergroup");
        userJson.put("receiverType","group");
        userJson.put("category","message");
        userJson.put("type","text");
        userJson.put("data",userJson1);

        String responseBody=getCommon().
                pathParam("id",msgId).   // need a group msgID
                body(userJson.toString(1)).
                when().
                post("/messages/{id}/thread").
                then().
                assertThat().statusCode(200).extract().body().asString();
        System.out.println(responseBody);

    }

    //
    //------------------------threadMessage image into user-------------------------

    @Test(description = "Verify sendThreadMessage image User to user onBehalfOf",dependsOnMethods = {"listMessageOnBehalfOf"})
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
                // header("onBehalfOf",userUID).
                pathParam("id",msgId).
                body(userJson.toString(1)).
                when().
                post("/messages/{id}/thread").
                then().
                assertThat().statusCode(200).extract().body().asString();
        System.out.println(responseBody);

    }

    @Test(description = "Verify sendThreadMessage image User to user without onBehalfOf",dependsOnMethods = {"listMessageOnBehalfOf"})
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
                pathParam("id",msgId).
                body(userJson.toString(1)).
                when().
                post("/messages/{id}/thread").
                then().
                assertThat().statusCode(200).extract().body().asString();
        System.out.println(responseBody);

    }

    //------------------------threadMessage image into group-------------------------

    @Test(description = "Verify sendThreadMessage text group onBehalfOf",dependsOnMethods = {"listMessageOnBehalfOf"})
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
        userJson.put("receiver","supergroup");
        userJson.put("receiverType","group");
        userJson.put("category","message");
        userJson.put("type","image");
        userJson.put("data",userJson2);

        String responseBody=getCommon().
                // header("onBehalfOf",userUID).
                        pathParam("id",msgId).      // need a group msgID
                        body(userJson.toString(1)).
                when().
                post("/messages/{id}/thread").
                then().
                assertThat().statusCode(200).extract().body().asString();
        System.out.println(responseBody);

    }

    @Test(description = "Verify sendThreadMessage text group without onBehalfOf",dependsOnMethods = {"listMessageOnBehalfOf"})
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
        userJson.put("receiver","supergroup");
        userJson.put("receiverType","group");
        userJson.put("category","message");
        userJson.put("type","image");
        userJson.put("data",userJson2);

        String responseBody=getCommon().
                pathParam("id",msgId).   // need a group msgID
                        body(userJson.toString(1)).
                when().
                post("/messages/{id}/thread").
                then().
                assertThat().statusCode(200).extract().body().asString();
        System.out.println(responseBody);

    }

// text image
    //------------------------threadMessage text & image into user-------------------------

    @Test(description = "Verify sendThreadMessage text & image User to user onBehalfOf",dependsOnMethods = {"listMessageOnBehalfOf"})
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
                // header("onBehalfOf",userUID).
                        pathParam("id",msgId).
                body(userJson.toString(1)).
                when().log().body().
                post("/messages/{id}/thread").
                then().log().body().
                assertThat().statusCode(200).extract().body().asString();
        System.out.println(responseBody);

    }

    @Test(description = "Verify sendThreadMessage text & image User to user without onBehalfOf",dependsOnMethods = {"listMessageOnBehalfOf"})
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
                pathParam("id",msgId).
                body(userJson.toString(1)).
                when().
                post("/messages/{id}/thread").
                then().
                assertThat().statusCode(200).extract().body().asString();
        System.out.println(responseBody);

    }

    //------------------------threadMessage text & image into group-------------------------

    @Test(description = "Verify sendThreadMessage text & image group onBehalfOf",dependsOnMethods = {"listMessageOnBehalfOf"})
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
        userJson.put("receiver","supergroup");
        userJson.put("receiverType","group");
        userJson.put("category","message");
        userJson.put("type","image");
        userJson.put("data",userJson2);

        String responseBody=getCommon().
                // header("onBehalfOf",userUID).
                        pathParam("id",msgId).      // need a group msgID
                        body(userJson.toString(1)).
                when().
                post("/messages/{id}/thread").
                then().
                assertThat().statusCode(200).extract().body().asString();
        System.out.println(responseBody);

    }

    @Test(description = "Verify sendThreadMessage text & image group without onBehalfOf",dependsOnMethods = {"listMessageOnBehalfOf"})
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
        userJson.put("receiver","supergroup");
        userJson.put("receiverType","group");
        userJson.put("category","message");
        userJson.put("type","image");
        userJson.put("data",userJson2);

        String responseBody=getCommon().
                pathParam("id",msgId).   // need a group msgID
                        body(userJson.toString(1)).
                when().
                post("/messages/{id}/thread").
                then().
                assertThat().statusCode(200).extract().body().asString();
        System.out.println(responseBody);

    }

    //------------------------listThreadMessage -------------------------

    //------------------------listThreadMessage for user-------------------------


    @Test(description = "Verify listThreadMessage user without onBehalfOf",dependsOnMethods = {"listMessageOnBehalfOf"})
    public void listThreadMsgUserWithoutOnBehalfOf()
    {
        String responseBody=getCommon().
                pathParam("id",msgId).   // need a thread user msgID
                when().
                get("/messages/{id}/thread").
                then().
                assertThat().statusCode(200).extract().body().asString();
        System.out.println(responseBody);

    }

    @Test(description = "Verify listThreadMessage user without onBehalfOf",dependsOnMethods = {"listMessageOnBehalfOf"})
    public void listThreadMsgUserOnBehalfOf()
    {
        String responseBody=getCommon().
              //  header("onBehalfOf",userID).
                pathParam("id",msgId).   // need a thread user msgID
                        when().
                get("/messages/{id}/thread").
                then().
                assertThat().statusCode(200).extract().body().asString();
        System.out.println(responseBody);

    }

    //------------------------listThreadMessage for group-------------------------

    @Test(description = "Verify listThreadMessage group without onBehalfOf",dependsOnMethods = {"listMessageOnBehalfOf"})
    public void listThreadMsgGroupWithoutOnBehalfOf()
    {
        String responseBody=getCommon().
                pathParam("id",msgId).   // need a thread group msgID
                 when().
                get("/messages/{id}/thread").
                then().
                assertThat().statusCode(200).extract().body().asString();
        System.out.println(responseBody);

    }

    @Test(description = "Verify listThreadMessage group without onBehalfOf",dependsOnMethods = {"listMessageOnBehalfOf"})
    public void listThreadMsgGroupOnBehalfOf()
    {
        String responseBody=getCommon().
                //  header("onBehalfOf",userID).
                pathParam("id",msgId).   // need a thread group msgID
                 when().
                get("/messages/{id}/thread").
                then().
                assertThat().statusCode(200).extract().body().asString();
        System.out.println(responseBody);

    }

//------------------------Get User Messages-------------------------


    @Test(description = "Verify getUserMessage  without onBehalfOf",dependsOnMethods = {"listMessageOnBehalfOf"})
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

    @Test(description = "Verify getUserMessage  onBehalfOf",dependsOnMethods = {"listMessageOnBehalfOf"})
    public void getUserMessageOnBehalfOf()
    {
        String responseBody=getCommon().
                //  header("onBehalfOf",userID).
                pathParam("uid","superhero1").
                when().
                get("/users/{uid}/messages").
                then().
                assertThat().statusCode(200).extract().body().asString();
        System.out.println(responseBody);

    }

    @Test(description = "Verify getUserMessage  by searchKey ",dependsOnMethods = {"listMessageOnBehalfOf"})
    public void getUserMessageBYSearchKey()
    {
        String responseBody=getCommon().
                //  header("onBehalfOf",userID).
                queryParam("searchKey","hello babu").
                pathParam("uid","superhero1").
                when().
                get("/users/{uid}/messages").
                then().
                assertThat().statusCode(200).extract().body().asString();
        System.out.println(responseBody);

    }

    @Test(description = "Verify getUserMessage  by unread ",dependsOnMethods = {"listMessageOnBehalfOf"})
    public void getUserMessageByUnread()
    {
        String responseBody=getCommon().
                //  header("onBehalfOf",userID).
                        queryParam("unread",true).
                pathParam("uid","superhero1").
                when().
                get("/users/{uid}/messages").
                then().
                assertThat().statusCode(200).extract().body().asString();
        System.out.println(responseBody);

    }

    @Test(description = "Verify getUserMessage by type only text ",dependsOnMethods = {"listMessageOnBehalfOf"})
    public void getUserMessageByTypeText()
    {
        String responseBody=getCommon().
                //  header("onBehalfOf",userID).
                        queryParam("type","text").
                pathParam("uid","superhero1").
                when().
                get("/users/{uid}/messages").
                then().
                assertThat().statusCode(200).extract().body().asString();
        System.out.println(responseBody);

    }

    @Test(description = "Verify getUserMessage by type only image ",dependsOnMethods = {"listMessageOnBehalfOf"})
    public void getUserMessageByTypeImage()
    {
        String responseBody=getCommon().
                //  header("onBehalfOf",userID).
                        queryParam("type","Image").
                pathParam("uid","superhero1").
                when().
                get("/users/{uid}/messages").
                then().
                assertThat().statusCode(200).extract().body().asString();
        System.out.println(responseBody);

    }

    @Test(description = "Verify getUserMessage by category only image ",dependsOnMethods = {"listMessageOnBehalfOf"})
    public void getUserMessageByCategory()
    {
        String responseBody=getCommon().
                //  header("onBehalfOf",userID).
                        queryParam("category","message").
                pathParam("uid","superhero1").
                when().
                get("/users/{uid}/messages").
                then().
                assertThat().statusCode(200).extract().body().asString();
        System.out.println(responseBody);

    }

    @Test(description = "Verify getUserMessage by perPage ",dependsOnMethods = {"listMessageOnBehalfOf"})
    public void getUserMessageByPerPage()
    {
        String responseBody=getCommon().
                //  header("onBehalfOf",userID).
                        queryParam("perPage",99).
                pathParam("uid","superhero1").
                when().
                get("/users/{uid}/messages").
                then().
                assertThat().statusCode(200).extract().body().asString();
        System.out.println(responseBody);

    }

    @Test(description = "Verify getUserMessage by limit ",dependsOnMethods = {"listMessageOnBehalfOf"})
    public void getUserMessageByLimit()
    {
        String responseBody=getCommon().
                //  header("onBehalfOf",userID).
                        queryParam("limit",200).
                pathParam("uid","superhero1").
                when().
                get("/users/{uid}/messages").
                then().
                assertThat().statusCode(200).extract().body().asString();
        System.out.println(responseBody);

    }

    //------------------------Get Group Messages-------------------------


    @Test(description = "Verify getGroupMessage  without onBehalfOf",dependsOnMethods = {"listMessageOnBehalfOf"})
    public void getGroupMessageWithoutOnBehalfOf()
    {
        String responseBody=getCommon().
                pathParam("guid","supergroup").
                when().
                get("/groups/{guid}/messages").
                then().
                assertThat().statusCode(200).extract().body().asString();
        System.out.println(responseBody);

    }

    @Test(description = "Verify getGroupMessage  onBehalfOf",dependsOnMethods = {"listMessageOnBehalfOf"})
    public void getGroupMessageOnBehalfOf()
    {
        String responseBody=getCommon().
                //  header("onBehalfOf",userID).
                        pathParam("guid","supergroup").
                when().
                get("/groups/{guid}/messages").
                then().
                assertThat().statusCode(200).extract().body().asString();
        System.out.println(responseBody);

    }

    @Test(description = "Verify getGroupMessage  by searchKey ",dependsOnMethods = {"listMessageOnBehalfOf"})
    public void getGroupMessageBYSearchKey()
    {
        String responseBody=getCommon().
                //  header("onBehalfOf",userID).
                        queryParam("searchKey","hello babu").
                pathParam("guid","supergroup").
                when().
                get("/groups/{guid}/messages").
                then().
                assertThat().statusCode(200).extract().body().asString();
        System.out.println(responseBody);

    }

    @Test(description = "Verify getGroupMessage  by unread ",dependsOnMethods = {"listMessageOnBehalfOf"})
    public void getGroupMessageByUnread()
    {
        String responseBody=getCommon().
                //  header("onBehalfOf",userID).
                        queryParam("unread",true).
                pathParam("guid","supergroup").
                when().
                get("/groups/{guid}/messages").
                then().
                assertThat().statusCode(200).extract().body().asString();
        System.out.println(responseBody);

    }

    @Test(description = "Verify getGroupMessage by type only text ",dependsOnMethods = {"listMessageOnBehalfOf"})
    public void getGroupMessageByTypeText()
    {
        String responseBody=getCommon().
                //  header("onBehalfOf",userID).
                        queryParam("type","text").
                pathParam("guid","supergroup").
                when().
                get("/groups/{guid}/messages").
                then().
                assertThat().statusCode(200).extract().body().asString();
        System.out.println(responseBody);

    }

    @Test(description = "Verify getGroupMessage by type only image ",dependsOnMethods = {"listMessageOnBehalfOf"})
    public void getGroupMessageByTypeImage()
    {
        String responseBody=getCommon().
                //  header("onBehalfOf",userID).
                        queryParam("type","Image").
                pathParam("guid","supergroup").
                when().
                get("/groups/{guid}/messages").
                then().
                assertThat().statusCode(200).extract().body().asString();
        System.out.println(responseBody);

    }

    @Test(description = "Verify getGroupMessage by category only image ",dependsOnMethods = {"listMessageOnBehalfOf"})
    public void getGroupMessageByCategory()
    {
        String responseBody=getCommon().
                //  header("onBehalfOf",userID).
                        queryParam("category","message").
                pathParam("guid","supergroup").
                when().
                get("/groups/{guid}/messages").
                then().
                assertThat().statusCode(200).extract().body().asString();
        System.out.println(responseBody);

    }

    @Test(description = "Verify getGroupMessage by perPage ",dependsOnMethods = {"listMessageOnBehalfOf"})
    public void getGroupMessageByPerPage()
    {
        String responseBody=getCommon().
                //  header("onBehalfOf",userID).
                        queryParam("perPage",99).
                pathParam("guid","supergroup").
                when().
                get("/groups/{guid}/messages").
                then().
                assertThat().statusCode(200).extract().body().asString();
        System.out.println(responseBody);

    }

    @Test(description = "Verify getGroupMessage by limit ",dependsOnMethods = {"listMessageOnBehalfOf"})
    public void getGroupMessageByLimit()
    {
        String responseBody=getCommon().
                //  header("onBehalfOf",userID).
                        queryParam("limit",200).
                pathParam("guid","supergroup").
                when().
                get("/groups/{guid}/messages").
                then().
                assertThat().statusCode(200).extract().body().asString();
        System.out.println(responseBody);

    }

}

package CT_APITesting;

import io.restassured.specification.RequestSpecification;
import org.json.JSONObject;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class ConversationTest extends BaseTest{


    public RequestSpecification getCommon()
    {
        RequestSpecification httpRequestObject;
        httpRequestObject = given();
        httpRequestObject.header("apiKey",GlobalClassTest.prop.getProperty("apiKey"));
        httpRequestObject.header("Content-Type", "application/json");
        httpRequestObject.header("Accept", "application/json");
        return httpRequestObject;
    }


    //-------------------listConversation-----------------------------------

    @Test(description = "Verify listConversation without onBehalfOf")
    public void listConversationWithoutOnBehalfOf()
    {
        String responseBody=getCommon().
                header("onBehalfOf","superhero1").
                when().
                get("/conversations").
                then().
                assertThat().statusCode(200).extract().body().asString();
        System.out.println(responseBody);

    }

    @Test(description = "Verify listConversation onBehalfOf")
    public void listConversationOnBehalfOf()
    {
        String responseBody=getCommon().
                header("onBehalfOf","superhero1"). // here put userUID for onBehalf
                when().
                get("/conversations").
                then().
                assertThat().statusCode(200).extract().body().asString();
        System.out.println(responseBody);

    }


    //-------------------listConversation for conversationType-----------------------------------

    @Test(description = "Verify listConversation conversationType user without onBehalfOf")
    public void listConversationConversationType()
    {
        String responseBody=getCommon().
                queryParam("conversationType","user").
                when().
                get("/conversations").
                then().
                assertThat().statusCode(200).extract().body().asString();
        System.out.println(responseBody);

    }

    @Test(description = "Verify listConversation conversationType user onBehalfOf")
    public void listConversationConversationTypeOnBehalfOf()
    {
        String responseBody=getCommon().
                header("onBehalfOf","superhero1"). // here put userUID for onBehalf
                queryParam("conversationType","user").
                when().
                get("/conversations").
                then().
                assertThat().statusCode(200).extract().body().asString();
        System.out.println(responseBody);

    }

    @Test(description = "Verify listConversation conversationType group without onBehalfOf")
    public void listConversationConversationTypeGroup()
    {
        String responseBody=getCommon().
                queryParam("conversationType","group").
                when().
                get("/conversations").
                then().
                assertThat().statusCode(200).extract().body().asString();
        System.out.println(responseBody);

    }

    @Test(description = "Verify listConversation conversationType group onBehalfOf")
    public void listConversationConversationTypeGroupOnBehalfOf()
    {
        String responseBody=getCommon().
                header("onBehalfOf","superhero1"). // here put userUID for onBehalf
                        queryParam("conversationType","group").
                when().
                get("/conversations").
                then().
                assertThat().statusCode(200).extract().body().asString();
        System.out.println(responseBody);

    }

    //-------------------listConversation withTags for user-----------------------------------

    @Test(description = "Verify listConversation withTags user without onBehalfOf")
    public void listConversationWithTags()
    {
        String responseBody=getCommon().
                queryParam("withTags",true).
                queryParam("conversationType","user").
                when().
                get("/conversations").
                then().
                assertThat().statusCode(200).extract().body().asString();
        System.out.println(responseBody);

    }

    @Test(description = "Verify listConversation withTags user onBehalfOf")
    public void listConversationWithTagsOnBehalfOf()
    {
        String responseBody=getCommon().
                header("onBehalfOf","superhero1"). // here put userUID for onBehalf
                queryParam("withTags",true).
                queryParam("conversationType","user").
                when().
                get("/conversations").
                then().
                assertThat().statusCode(200).extract().body().asString();
        System.out.println(responseBody);

    }


    @Test(description = "Verify listConversation withTags  with false for user without onBehalfOf")
    public void listConversationWithTagsFalse()
    {
        String responseBody=getCommon().
                queryParam("withTags",false).
                queryParam("conversationType","user").
                when().
                get("/conversations").
                then().
                assertThat().statusCode(200).extract().body().asString();
        System.out.println(responseBody);

    }

    @Test(description = "Verify listConversation withTags with false for user onBehalfOf")
    public void listConversationWithTagsFalseOnBehalfOf()
    {
        String responseBody=getCommon().
                header("onBehalfOf","superhero1"). // here put userUID for onBehalf
                queryParam("withTags",false).
                queryParam("conversationType","user").
                when().
                get("/conversations").
                then().
                assertThat().statusCode(200).extract().body().asString();
        System.out.println(responseBody);

    }


    //-------------------listConversation withTags for group-----------------------------------

    @Test(description = "Verify listConversation withTags group without onBehalfOf")
    public void listConversationWithTagsGroup()
    {
        String responseBody=getCommon().
                queryParam("withTags",true).
                queryParam("conversationType","group").
                when().
                get("/conversations").
                then().
                assertThat().statusCode(200).extract().body().asString();
        System.out.println(responseBody);

    }

    @Test(description = "Verify listConversation withTags group onBehalfOf")
    public void listConversationWithTagsOnBehalfOfGroup()
    {
        String responseBody=getCommon().
                header("onBehalfOf","superhero1"). // here put userUID for onBehalf
                queryParam("withTags",true).
                queryParam("conversationType","group").
                when().
                get("/conversations").
                then().
                assertThat().statusCode(200).extract().body().asString();
        System.out.println(responseBody);

    }


    @Test(description = "Verify listConversation withTags  with false for group without onBehalfOf")
    public void listConversationWithTagsFalseGroup()
    {
        String responseBody=getCommon().
                queryParam("withTags",false).
                queryParam("conversationType","group").
                when().
                get("/conversations").
                then().
                assertThat().statusCode(200).extract().body().asString();
        System.out.println(responseBody);

    }

    @Test(description = "Verify listConversation withTags with false for group onBehalfOf")
    public void listConversationWithTagsFalseOnBehalfOfGroup()
    {
        String responseBody=getCommon().
                header("onBehalfOf","superhero1"). // here put userUID for onBehalf
                queryParam("withTags",false).
                queryParam("conversationType","group").
                when().
                get("/conversations").
                then().
                assertThat().statusCode(200).extract().body().asString();
        System.out.println(responseBody);

    }


    //-------------------listConversation withUserAndGroupTags-----------------------------------

    @Test(description = "Verify listConversation withUserAndGroupTags without onBehalfOf")
    public void listConversationWithUserAndGroupTags()
    {
        String responseBody=getCommon().
                queryParam("withUserAndGroupTags","tag1").
                when().
                get("/conversations").
                then().
                assertThat().statusCode(200).extract().body().asString();
        System.out.println(responseBody);

    }

    @Test(description = "Verify listConversation withUserAndGroupTags onBehalfOf")
    public void listConversationWithUserAndGroupTagsOnBehalfOf()
    {
        String responseBody=getCommon().
                // header("onBehalfOf","superhero1").
                queryParam("withUserAndGroupTags","tag1").
                when().
                get("/conversations").
                then().
                assertThat().statusCode(200).extract().body().asString();
        System.out.println(responseBody);

    }


    //-------------------listConversation userTags-----------------------------------

    @Test(description = "Verify listConversation userTags without onBehalfOf")
    public void listConversationUserTags()
    {
        String responseBody=getCommon().
                queryParam("userTags","tag1").
                when().
                get("/conversations").
                then().
                assertThat().statusCode(200).extract().body().asString();
        System.out.println(responseBody);

    }

    @Test(description = "Verify listConversation userTags onBehalfOf")
    public void listConversationUserTagsOnBehalfOf()
    {
        String responseBody=getCommon().
                // header("onBehalfOf","superhero1").
                        queryParam("userTags","tag1").
                when().
                get("/conversations").
                then().
                assertThat().statusCode(200).extract().body().asString();
        System.out.println(responseBody);

    }

    //-------------------listConversation groupTags-----------------------------------

    @Test(description = "Verify listConversation groupTags without onBehalfOf")
    public void listConversationGroupTags()
    {
        String responseBody=getCommon().
                queryParam("groupTags","tag1").
                when().
                get("/conversations").
                then().
                assertThat().statusCode(200).extract().body().asString();
        System.out.println(responseBody);

    }

    @Test(description = "Verify listConversation groupTags onBehalfOf")
    public void listConversationGroupTagsOnBehalfOf()
    {
        String responseBody=getCommon().
                // header("onBehalfOf","superhero1").
                        queryParam("groupTags","tag1").
                when().
                get("/conversations").
                then().
                assertThat().statusCode(200).extract().body().asString();
        System.out.println(responseBody);

    }


    //-------------------listConversation unread with true-----------------------------------

    @Test(description = "Verify listConversation unread without onBehalfOf")
    public void listConversationUnread()
    {
        String responseBody=getCommon().
                queryParam("unread",true).
                when().
                get("/conversations").
                then().
                assertThat().statusCode(200).extract().body().asString();
        System.out.println(responseBody);

    }

    @Test(description = "Verify listConversation unread onBehalfOf")
    public void listConversationUnreadOnBehalfOf()
    {
        String responseBody=getCommon().
                // header("onBehalfOf","superhero1").
                queryParam("unread",true).
                when().
                get("/conversations").
                then().
                assertThat().statusCode(200).extract().body().asString();
        System.out.println(responseBody);

    }


    //-------------------listConversation unread with false-----------------------------------

    @Test(description = "Verify listConversation unread false without onBehalfOf")
    public void listConversationUnreadFalse()
    {
        String responseBody=getCommon().
                queryParam("unread",false).
                when().
                get("/conversations").
                then().
                assertThat().statusCode(200).extract().body().asString();
        System.out.println(responseBody);

    }

    @Test(description = "Verify listConversation unread false onBehalfOf")
    public void listConversationUnreadFalseOnBehalfOf()
    {
        String responseBody=getCommon().
                // header("onBehalfOf","superhero1").
                        queryParam("unread",false).
                when().
                get("/conversations").
                then().
                assertThat().statusCode(200).extract().body().asString();
        System.out.println(responseBody);

    }

    //-------------------listConversation perPage-----------------------------------

    @Test(description = "Verify listConversation perPage without onBehalfOf")
    public void listConversationPerPage()
    {
        String responseBody=getCommon().
                queryParam("perPage",101).
                when().
                get("/conversations").
                then().
                assertThat().statusCode(200).extract().body().asString();
        System.out.println(responseBody);

    }

    @Test(description = "Verify listConversation perPage onBehalfOf")
    public void listConversationPerPageOnBehalfOf()
    {
        String responseBody=getCommon().
                // header("onBehalfOf","superhero1").
                queryParam("perPage",101).
                when().
                get("/conversations").
                then().
                assertThat().statusCode(200).extract().body().asString();
        System.out.println(responseBody);

    }

    //-------------------getUserConversation-----------------------------------

    @Test(description = "Verify getUserConversation")
    public void getUserConversation()
    {
        String responseBody=getCommon().
                header("onBehalfOf","superhero1").
                pathParam("uid","superhero2").
                when().
                get("/users/{uid}/conversation").
                then().log().body().
                assertThat().statusCode(200).extract().body().asString();
        System.out.println(responseBody);

    }

    //-------------------updateUserConversation-----------------------------------

    @Test(description = "Verify updateUserConversation")
    public void updateUserConversation()
    {
        JSONObject userJson = new JSONObject();
        userJson.put("tags",new String[]{"tag1 get conversation for all conversation"});

        String responseBody=getCommon().
                header("onBehalfOf","superhero1").
                pathParam("uid","superhero2").
                body(userJson.toString(1)).
                when().
                put("/users/{uid}/conversation").
                then().log().body().
                assertThat().statusCode(200).extract().body().asString();
        System.out.println(responseBody);

    }

    //-------------------resetUserConversation-----------------------------------

    @Test(description = "Verify resetUserConversation")
    public void resetUserConversation()
    {
        JSONObject userJson = new JSONObject();
        userJson.put("conversationWith","superhero1");
        userJson.put("deleteMessagesPermanently",false);

        String responseBody=getCommon().
                header("onBehalfOf","superhero1").
                pathParam("uid","superhero2").
                body(userJson.toString(1)).
                when().
                delete("/users/{uid}/conversation").
                then().log().body().
                assertThat().statusCode(200).extract().body().asString();
        System.out.println(responseBody);

    }


    //-------------------getGroupConversation for group-----------------------------------

    @Test(description = "Verify getGroupConversation")
    public void getGroupConversation()
    {
        String responseBody=getCommon().
                header("onBehalfOf","superhero2").
                pathParam("guid","supergroup").
                when().
                get("/groups/{guid}/conversation").
                then().log().body().
                assertThat().statusCode(200).extract().body().asString();
        System.out.println(responseBody);

    }

    //-------------------getConversation for update group-----------------------------------

    @Test(description = "Verify updateGroupConversation")
    public void updateGroupConversation()
    {
        JSONObject userJson = new JSONObject();
        userJson.put("tags",new String[]{"tag1 get conversation for group"});

        String responseBody=getCommon().
                header("onBehalfOf","superhero1").
                pathParam("guid","supergroup").
                body(userJson.toString(1)).
                when().
                put("/groups/{guid}/conversation").
                then().log().body().
                assertThat().statusCode(200).extract().body().asString();
        System.out.println(responseBody);

    }

    //-------------------resetGroupConversation-----------------------------------

    @Test(description = "Verify resetGroupConversation")
    public void resetGroupConversation()
    {
        String responseBody=getCommon().
                header("onBehalfOf","superhero1").
                pathParam("guid","supergroup").
                when().
                delete("/groups/{guid}/conversation").
                then().log().body().
                assertThat().statusCode(200).extract().body().asString();
        System.out.println(responseBody);

    }


}

package CT_APITesting;

import io.restassured.specification.RequestSpecification;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class MessageListTest extends UsersTest{


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
                assertThat().statusCode(400).extract().body().asString();
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


    @Test(description = "Verify getMessage ")
    public void getMessage()
    {
        String responseBody=getCommon().
                queryParam("fromTimestamp",1673856125).
                queryParam("toTimestamp",1673856342).
                when().
                get("/messages").
                then().
                assertThat().statusCode(200).extract().body().asString();
        System.out.println(MessageTest.userUID);


    }


}

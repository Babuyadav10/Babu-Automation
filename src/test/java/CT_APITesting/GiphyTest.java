package CT_APITesting;

import org.testng.annotations.Test;
public class GiphyTest extends BaseTest {

    @Test(description = "Verify giphy functionality")
    public void giphy()
    {
        MessageIDTest.getCommon().
                when().
                get("https://gifs-giphy-"+GlobalClassTest.prop.getProperty("region")+"."+GlobalClassTest.prop.getProperty("domainName")+"/v1/trending").
                then().log().body().
                assertThat().statusCode(200).extract().body().asString();
    }

    @Test(description = "Verify giphy limit params functionality")
    public void giphyLimit()
    {
        String responseBody= MessageIDTest.getCommon().
                queryParam("limit",10).
                when().
                get("https://gifs-giphy-"+GlobalClassTest.prop.getProperty("region")+"."+GlobalClassTest.prop.getProperty("domainName")+"/v1/trending").
                then().
                assertThat().statusCode(200).extract().body().asString();
        System.out.println(responseBody);
    }

    @Test(description = "Verify giphy limit & offset functionality")
    public void giphyLimitOffset()
    {
        String responseBody= MessageIDTest.getCommon().
                queryParam("offset",1).
                queryParam("limit",5).
                when().
                get("https://gifs-giphy-"+GlobalClassTest.prop.getProperty("region")+"."+GlobalClassTest.prop.getProperty("domainName")+"/v1/trending").
                then().
                assertThat().statusCode(200).extract().body().asString();
        System.out.println(responseBody);
    }

    @Test(description = "Verify search Giphy functionality")
    public void searchGiphy()
    {
        String responseBody= MessageIDTest.getCommon().
                queryParam("offset",1).
                queryParam("limit",5).
                queryParam("query","babu").
                when().
                get("https://gifs-giphy-"+GlobalClassTest.prop.getProperty("region")+"."+GlobalClassTest.prop.getProperty("domainName")+"/v1/search").
                then().
                assertThat().statusCode(200).extract().body().asString();
        System.out.println(responseBody);
    }

    @Test(description = "Verify search giphy without query functionality")
    public void searchGiphyWithoutQuery()
    {
        String responseBody= MessageIDTest.getCommon().
                queryParam("offset",1).
                queryParam("limit",5).
                when().
                get("https://gifs-giphy-"+GlobalClassTest.prop.getProperty("region")+"."+GlobalClassTest.prop.getProperty("domainName")+"/v1/search").
                then().
                assertThat().statusCode(400).extract().body().asString();
        System.out.println(responseBody);
    }

    @Test(description = "Verify search giphy empty query functionality")
    public void searchGiphyEmptyQuery()
    {
        String responseBody= MessageIDTest.getCommon().
                queryParam("offset",1).
                queryParam("limit",5).
                queryParam("query"," ").
                when().
                get("https://gifs-giphy-"+GlobalClassTest.prop.getProperty("region")+"."+GlobalClassTest.prop.getProperty("domainName")+"/v1/search").
                then().log().body().
                assertThat().statusCode(200).extract().body().asString();
        System.out.println(responseBody);
    }

    @Test(description = "Verify fetch credential functionality")
    public void fetchCredential()
    {
        String responseBody= MessageIDTest.getCommon().
                queryParam("offset",1).
                queryParam("limit",5).
                queryParam("query","babu").
                when().
                get("https://gifs-giphy-"+GlobalClassTest.prop.getProperty("region")+"."+GlobalClassTest.prop.getProperty("domainName")+"/v1/search").
                then().
                assertThat().statusCode(200).extract().body().asString();
        System.out.println(responseBody);
    }

    @Test(description = "Verify fetch credential without query functionality")
    public void fetchCredentialWithoutQuery()
    {
        String responseBody= MessageIDTest.getCommon().
                queryParam("offset",1).
                queryParam("limit",5).
                when().
                get("https://gifs-giphy-"+GlobalClassTest.prop.getProperty("region")+"."+GlobalClassTest.prop.getProperty("domainName")+"/v1/search").
                then().
                assertThat().statusCode(400).extract().body().asString();
        System.out.println(responseBody);
    }

    @Test(description = "Verify fetch credential only query functionality")
    public void fetchCredentialOnlyQuery()
    {
        String responseBody= MessageIDTest.getCommon().
                queryParam("query","babu").
                when().
                get("https://gifs-giphy-"+GlobalClassTest.prop.getProperty("region")+"."+GlobalClassTest.prop.getProperty("domainName")+"/v1/search").
                then().
                assertThat().statusCode(200).extract().body().asString();
        System.out.println(responseBody);
    }

}


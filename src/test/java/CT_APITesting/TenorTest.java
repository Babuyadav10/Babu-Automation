package CT_APITesting;

import org.testng.annotations.Test;

public class TenorTest extends BaseTest{

    @Test(description = "Verify tenor functionality")
    public void tenor()
    {
        String responseBody= MessageIDTest.getCommon().
                when().
                get("https://gifs-tenor-"+GlobalClassTest.prop.getProperty("region")+"."+GlobalClassTest.prop.getProperty("domainName")+"/v1/trending").
                then().
                assertThat().statusCode(200).extract().body().asString();
        System.out.println(responseBody);
    }

    @Test(description = "Verify tenor for limit functionality")
    public void tenorLimit()
    {
        String responseBody= MessageIDTest.getCommon().
                queryParam("limit",10).
                when().
                get("https://gifs-tenor-"+GlobalClassTest.prop.getProperty("region")+"."+GlobalClassTest.prop.getProperty("domainName")+"/v1/trending").
                then().
                assertThat().statusCode(200).extract().body().asString();
        System.out.println(responseBody);
    }

    @Test(description = "Verify tenor for limit & offset functionality")
    public void tenorLimitOffset()
    {
        String responseBody= MessageIDTest.getCommon().
                queryParam("offset",1).
                queryParam("limit",5).
                when().
                get("https://gifs-tenor-"+GlobalClassTest.prop.getProperty("region")+"."+GlobalClassTest.prop.getProperty("domainName")+"/v1/trending").
                then().
                assertThat().statusCode(200).extract().body().asString();
        System.out.println(responseBody);
    }

    @Test(description = "Verify search tenor for query functionality")
    public void searchTenor()
    {
        String responseBody= MessageIDTest.getCommon().
                queryParam("offset",1).
                queryParam("limit",5).
                queryParam("query","babu").
                when().
                get("https://gifs-tenor-"+GlobalClassTest.prop.getProperty("region")+"."+GlobalClassTest.prop.getProperty("domainName")+"/v1/search").
                then().
                assertThat().statusCode(200).extract().body().asString();
        System.out.println(responseBody);
    }

    @Test(description = "Verify search tenor without query functionality")
    public void searchTenorWithoutQuery()
    {
        String responseBody= MessageIDTest.getCommon().
                queryParam("offset",1).
                queryParam("limit",5).
                when().
                get("https://gifs-tenor-"+GlobalClassTest.prop.getProperty("region")+"."+GlobalClassTest.prop.getProperty("domainName")+"/v1/search").
                then().
                assertThat().statusCode(400).extract().body().asString();
        System.out.println(responseBody);
    }

    @Test(description = "Verify search tenor  for empty query functionality")
    public void searchTenorEmptyQuery()
    {
        String responseBody= MessageIDTest.getCommon().
                queryParam("offset",1).
                queryParam("limit",5).
                queryParam("query"," ").
                when().
                get("https://gifs-tenor-"+GlobalClassTest.prop.getProperty("region")+"."+GlobalClassTest.prop.getProperty("domainName")+"/v1/search").
                then().
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
                get("https://gifs-tenor-"+GlobalClassTest.prop.getProperty("region")+"."+GlobalClassTest.prop.getProperty("domainName")+"/v1/search").
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
                get("https://gifs-tenor-"+GlobalClassTest.prop.getProperty("region")+"."+GlobalClassTest.prop.getProperty("domainName")+"/v1/search").
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
                get("https://gifs-tenor-"+GlobalClassTest.prop.getProperty("region")+"."+GlobalClassTest.prop.getProperty("domainName")+"/v1/search").
                then().
                assertThat().statusCode(200).extract().body().asString();
        System.out.println(responseBody);
    }

}

package CT_APITesting;

import org.testng.annotations.Test;
public class GfycatTest extends BaseTest{

    @Test(description = "Verify gfyCat Fetch functionality")
    public void gfyCatFetch()
    {
         MessageIDTest.getCommon().
                when().
                get("https://gifs-gfycat-"+GlobalClassTest.prop.getProperty("region")+"."+GlobalClassTest.prop.getProperty("domainName")+"/v1/trending").
                then().log().body().
                statusCode(200);
    }

    @Test(description = "Verify gfyCat search functionality..")
    public void gfyCatSearch()
    {
        MessageIDTest.getCommon().
                queryParam("offset",1).
                queryParam("limit",5).
                queryParam("query","babu").
                when().
                get("https://gifs-gfycat-"+GlobalClassTest.prop.getProperty("region")+"."+GlobalClassTest.prop.getProperty("domainName")+"/v1/search").
                then().log().body().
                statusCode(200);
    }

    @Test(description = "Verify gfyCat search empty functionality")
    public void gfyCatSearchEmpty()
    {
        MessageIDTest.getCommon().
                queryParam("offset",1).
                queryParam("limit",5).
                queryParam("query","").
                when().
                get("https://gifs-gfycat-"+GlobalClassTest.prop.getProperty("region")+"."+GlobalClassTest.prop.getProperty("domainName")+"/v1/search").
                then().log().body().
                statusCode(400);
    }

    @Test(description = "Verify gfyCat search invalid functionality.")
    public void gfyCatSearchInvalid()
    {
        MessageIDTest.getCommon().
                queryParam("query","भारत").
                when().
                get("https://gifs-gfycat-"+GlobalClassTest.prop.getProperty("region")+"."+GlobalClassTest.prop.getProperty("domainName")+"/v1/search").
                then().log().body().
                statusCode(400);
    }

}

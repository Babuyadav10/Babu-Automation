package CT_APITesting;

import org.testng.annotations.Test;

public class StipopTest extends BaseTest{


    @Test(description = "Verify StiPop functionality")
    public void stiPop()
    {
        MessageIDTest.getCommon().
                when().
                get("https://stickers-stipop-"+GlobalClassTest.prop.getProperty("region")+"."+GlobalClassTest.prop.getProperty("domainName")+"/v1/trending").
                then().log().body().
                statusCode(200);
    }

    @Test(description = "Verify StiPop SearchBy Limit functionality")
    public void stiPopSearchByLimit()
    {
        MessageIDTest.getCommon().
                queryParam("limit",2).
                when().
                get("https://stickers-stipop-"+GlobalClassTest.prop.getProperty("region")+"."+GlobalClassTest.prop.getProperty("domainName")+"/v1/trending").
                then().log().body().
                statusCode(200);
    }

    @Test(description = "Verify StiPop searchBy Page functionality")
    public void stiPopSearchByPage()
    {
        MessageIDTest.getCommon().
                queryParam("pageNumber",2).
                when().
                get("https://stickers-stipop-"+GlobalClassTest.prop.getProperty("region")+"."+GlobalClassTest.prop.getProperty("domainName")+"/v1/trending").
                then().log().body().
                statusCode(200);
    }

    @Test(description = "Verify StiPop searchBy limit & Page functionality")
    public void stiPopSearchByLimitPage()
    {
        MessageIDTest.getCommon().
                queryParam("pageNumber",2).
                queryParam("limit",1).
                when().
                get("https://stickers-stipop-"+GlobalClassTest.prop.getProperty("region")+"."+GlobalClassTest.prop.getProperty("domainName")+"/v1/trending").
                then().log().body().
                statusCode(200);
    }


    @Test(description = "Verify StiPop invalid endpoint functionality")
    public void stiPopInvalidEndpoint()
    {
        MessageIDTest.getCommon().
                when().
                get("https://stickers-stipop-"+GlobalClassTest.prop.getProperty("region")+"."+GlobalClassTest.prop.getProperty("domainName")+"/v1/trendings").
                then().log().body().
                statusCode(403);
    }

    @Test(description = "Verify StiPop search.. functionality")
    public void stiPopSearch()
    {
        MessageIDTest.getCommon().
                queryParam("query","happy").
                when().
                get("https://stickers-stipop-"+GlobalClassTest.prop.getProperty("region")+"."+GlobalClassTest.prop.getProperty("domainName")+"/v1/search").
                then().log().body().
                statusCode(200);
    }

    @Test(description = "Verify StiPop search..by limit in query functionality")
    public void stiPopSByLimit()
    {
        MessageIDTest.getCommon().
                queryParam("query","happy").
                queryParam("limit",2).
                when().
                get("https://stickers-stipop-"+GlobalClassTest.prop.getProperty("region")+"."+GlobalClassTest.prop.getProperty("domainName")+"/v1/search").
                then().log().body().
                statusCode(200);
    }

    @Test(description = "Verify StiPop search..by page & Limit in query functionality")
    public void stiPopSByPage()
    {
        MessageIDTest.getCommon().
                queryParam("query","happy").
                queryParam("pageNumber",1).
                queryParam("limit",2).
                when().
                get("https://stickers-stipop-"+GlobalClassTest.prop.getProperty("region")+"."+GlobalClassTest.prop.getProperty("domainName")+"/v1/search").
                then().log().body().
                statusCode(200);
    }

    @Test(description = "Verify StiPop search..by lang in query functionality")
    public void stiPopSByLang()
    {
        MessageIDTest.getCommon().
                queryParam("query","happy").
                queryParam("lang","en").
                when().
                get("https://stickers-stipop-"+GlobalClassTest.prop.getProperty("region")+"."+GlobalClassTest.prop.getProperty("domainName")+"/v1/search").
                then().log().body().
                statusCode(200);
    }

    @Test(description = "Verify StiPop Search Without Query functionality")
    public void stiPopSearchWithoutQuery()
    {
        MessageIDTest.getCommon().
                queryParam("lang","en").
                when().
                get("https://stickers-stipop-"+GlobalClassTest.prop.getProperty("region")+"."+GlobalClassTest.prop.getProperty("domainName")+"/v1/search").
                then().log().body().
                statusCode(400);
    }

}

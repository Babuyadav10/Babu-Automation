package CT_APITesting;


import org.testng.annotations.Test;

public class StickersTest extends BaseTest{

    @Test(description = "Verify Stickers fetch functionality")
    public void stickersFetch()
    {
        String responseBody= MessageIDTest.getCommon().
                when().
                get("https://stickers-"+GlobalClassTest.prop.getProperty("region")+"."+GlobalClassTest.prop.getProperty("domainName")+"/v1/fetch").
                then().
                assertThat().statusCode(200).extract().body().asString();
        System.out.println(responseBody);
    }


    @Test(description = "Verify Stickers trending functionality")
    public void stickersTrending()
    {
        String responseBody= MessageIDTest.getCommon().
                when().
                get("https://stickers-stipop-"+GlobalClassTest.prop.getProperty("region")+"."+GlobalClassTest.prop.getProperty("domainName")+"/v1/trending").
                then().
                assertThat().statusCode(200).extract().body().asString();
        System.out.println(responseBody);
    }

    @Test(description = "Verify Stickers search functionality")
    public void stickersSearch()
    {
        String responseBody= MessageIDTest.getCommon().
                queryParam("query","happy").
                when().
                get("https://stickers-stipop-"+GlobalClassTest.prop.getProperty("region")+"."+GlobalClassTest.prop.getProperty("domainName")+"/v1/search").
                then().
                assertThat().statusCode(200).extract().body().asString();
        System.out.println(responseBody);
    }


    @Test(description = "Verify Stickers search for invalid query functionality")
    public void stickersSearchInvalidQuery()
    {
        String responseBody= MessageIDTest.getCommon().
                queryParam("query","happybabucometchat").
                when().
                get("https://stickers-stipop-"+GlobalClassTest.prop.getProperty("region")+"."+GlobalClassTest.prop.getProperty("domainName")+"/v1/search").
                then().
                assertThat().statusCode(200).extract().body().asString();
        System.out.println(responseBody);
    }

}

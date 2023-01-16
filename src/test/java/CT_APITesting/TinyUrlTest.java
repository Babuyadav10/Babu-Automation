package CT_APITesting;

import org.json.JSONObject;
import org.testng.annotations.Test;

public class TinyUrlTest extends BaseTest {

    @Test(description = "Verify Tiny URL functionality")
    public void tinyURL()
    {
        JSONObject userJson = new JSONObject();
        userJson.put("text","Login to CometChat Pro Dashboard here: https://app.cometchat.com/ \\nThis is our documentation: https://app.developerhub.io/cometchat/v2/overview/extensions \\nBlock our calender here: https://calendar.google.com/calendar/u/0/r?tab=rc");

        String responseBody= MessageIDTest.getCommon().
                body(userJson.toString(1)).
                when().
                post("https://url-shortener-tinyurl-"+GlobalClassTest.prop.getProperty("region")+"."+GlobalClassTest.prop.getProperty("domainName")+"/v1/shorten").
                then().log().body().
                assertThat().statusCode(200).extract().body().asString();
        System.out.println(responseBody);
    }

    @Test(description = "Verify Invalid url functionality")
    public void tinyURLInvalid()
    {
        JSONObject userJson = new JSONObject();
        userJson.put("text","Login to CometChat Pro Dashboard here: https://app.cometchat.combabu/");

        String responseBody= MessageIDTest.getCommon().
                body(userJson.toString(1)).
                when().
                post("https://url-shortener-tinyurl-"+GlobalClassTest.prop.getProperty("region")+"."+GlobalClassTest.prop.getProperty("domainName")+"/v1/shorten").
                then().log().body().
                assertThat().statusCode(200).extract().body().asString();
        System.out.println(responseBody);
    }

    @Test(description = "Verify empty url functionality")
    public void tinyURLEmpty()
    {
        JSONObject userJson = new JSONObject();
        userJson.put("text"," ");

        String responseBody= MessageIDTest.getCommon().
                body(userJson.toString(1)).
                when().
                post("https://url-shortener-tinyurl-"+GlobalClassTest.prop.getProperty("region")+"."+GlobalClassTest.prop.getProperty("domainName")+"/v1/shorten").
                then().log().body().
                assertThat().statusCode(200).extract().body().asString();
        System.out.println(responseBody);
    }

    @Test(description = "Verify only text functionality")
    public void tinyURLOnlyTxt()
    {
        JSONObject userJson = new JSONObject();
        userJson.put("text","passing only test");

        String responseBody= MessageIDTest.getCommon().
                body(userJson.toString(1)).
                when().
                post("https://url-shortener-tinyurl-"+GlobalClassTest.prop.getProperty("region")+"."+GlobalClassTest.prop.getProperty("domainName")+"/v1/shorten").
                then().log().body().
                assertThat().statusCode(200).extract().body().asString();
        System.out.println(responseBody);
    }
}

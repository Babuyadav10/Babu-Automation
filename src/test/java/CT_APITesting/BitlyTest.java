package CT_APITesting;

import org.json.JSONObject;
import org.testng.annotations.Test;
public class BitlyTest extends BaseTest {

    @Test(description = "Verify Bitly functionality")
    public void bitly()
    {
        JSONObject userJson = new JSONObject();
        userJson.put("text","Login to CometChat Pro Dashboard here: https://app.cometchat.com/ \\nThis is our documentation: https://app.developerhub.io/cometchat/v2/overview/extensions \\nBlock our calender here: https://calendar.google.com/calendar/u/0/r?tab=rc");

         MessageIDTest.getCommon().
                body(userJson.toString(1)).
                when().
                post("https://url-shortener-bitly-"+GlobalClassTest.prop.getProperty("region")+"."+GlobalClassTest.prop.getProperty("domainName")+"/v1/shorten").
                then().log().body().
                assertThat().statusCode(200).extract().body().asString();

    }

    @Test(description = "Verify Bitly empty text functionality")
    public void bitlyEmptyText()
    {
        JSONObject userJson = new JSONObject();
        userJson.put("text"," ");

        MessageIDTest.getCommon().
                body(userJson.toString(1)).
                when().
                post("https://url-shortener-bitly-"+GlobalClassTest.prop.getProperty("region")+"."+GlobalClassTest.prop.getProperty("domainName")+"/v1/shorten").
                then().log().body().
                assertThat().statusCode(200).extract().body().asString();
    }

    @Test(description = "Verify Bitly invalid url in text functionality")
    public void bitlyInvalidURL()
    {
        JSONObject userJson = new JSONObject();
        userJson.put("text","Login to www.babu.com");

        MessageIDTest.getCommon().
                body(userJson.toString(1)).
                when().
                post("https://url-shortener-bitly-"+GlobalClassTest.prop.getProperty("region")+"."+GlobalClassTest.prop.getProperty("domainName")+"/v1/shorten").
                then().log().body().
                assertThat().statusCode(417);
    }

}

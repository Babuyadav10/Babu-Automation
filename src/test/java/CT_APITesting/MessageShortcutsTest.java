package CT_APITesting;

import org.json.JSONObject;
import org.testng.annotations.Test;

public class MessageShortcutsTest extends BaseTest {
    @Test(description = "Verify message shortcuts functionality")
    public void messageShortcuts()
    {
        JSONObject userJson1 = new JSONObject();
        userJson1.put("!hbd","Happy Birthday! 🥳");
        userJson1.put("!wc","You're welcome!");
        userJson1.put("!ty","Hey! Thanks a lot! \uD83D\uDE0A");
        userJson1.put("!cu","Hey see you soon!");
           JSONObject userJson = new JSONObject();
           userJson.put("shortcuts",userJson1);

           MessageIDTest.getCommon().
                body(userJson.toString(1)).
                when().
                post("https://message-shortcuts-"+GlobalClassTest.prop.getProperty("region")+"."+GlobalClassTest.prop.getProperty("domainName")+"/v1/update").
                then().log().body().
                statusCode(200);
    }

    @Test(description = "Verify message shortcuts fetch functionality.", dependsOnMethods = {"messageShortcuts"})
    public void messageShortcutsFetch()
    {
        MessageIDTest.getCommon().
                when().
                get("https://message-shortcuts-"+GlobalClassTest.prop.getProperty("region")+"."+GlobalClassTest.prop.getProperty("domainName")+"/v1/fetch").
                then().log().body().
                statusCode(200);
    }

    @Test(description = "Verify message shortcuts invalid format functionality..")
    public void messageShortcutsInvalidFormat()
    {
        JSONObject userJson1 = new JSONObject();
        userJson1.put("hbd","Happy Birthday! 🥳");
        JSONObject userJson = new JSONObject();
        userJson.put("shortcuts",userJson1);

        MessageIDTest.getCommon().
                body(userJson.toString(1)).
                when().
                post("https://message-shortcuts-"+GlobalClassTest.prop.getProperty("region")+"."+GlobalClassTest.prop.getProperty("domainName")+"/v1/update").
                then().log().body().
                statusCode(200);
    }
}

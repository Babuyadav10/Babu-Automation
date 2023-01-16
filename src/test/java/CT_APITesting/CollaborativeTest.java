package CT_APITesting;

import org.json.JSONObject;
import org.testng.annotations.Test;
public class CollaborativeTest extends BaseTest{

    @Test(description = "Verify collaborative Documents user functionality")
    public void collaborativeDocuments()
    {
        JSONObject userJson = new JSONObject();
        userJson.put("receiver","superhero2");
        userJson.put("receiverType","user");

        MessageIDTest.getCommon().
                body(userJson.toString(1)).
                when().
                post("https://document-"+GlobalClassTest.prop.getProperty("region")+"."+GlobalClassTest.prop.getProperty("domainName")+"/v1/create").
                then().log().body().
                statusCode(200);
    }

    @Test(description = "Verify collaborative Documents for group functionality")
    public void collaborativeDocumentsGroup()
    {
        JSONObject userJson = new JSONObject();
        userJson.put("receiver","supergroup");
        userJson.put("receiverType","group");

        MessageIDTest.getCommon().
                body(userJson.toString(1)).
                when().
                post("https://document-"+GlobalClassTest.prop.getProperty("region")+"."+GlobalClassTest.prop.getProperty("domainName")+"/v1/create").
                then().log().body().
                statusCode(200);
    }

    @Test(description = "Verify collaborative Documents invalid user functionality")
    public void collaborativeDocumentsInvalidUser()
    {
        JSONObject userJson = new JSONObject();
        userJson.put("receiver","superhero20");
        userJson.put("receiverType","user");

         MessageIDTest.getCommon().
                body(userJson.toString(1)).
                when().
                post("https://document-"+GlobalClassTest.prop.getProperty("region")+"."+GlobalClassTest.prop.getProperty("domainName")+"/v1/create").
                then().log().body().
                statusCode(417);
    }

    @Test(description = "Verify collaborative Documents invalid group functionality..")
    public void collaborativeDocumentsInvalidGroup()
    {
        JSONObject userJson = new JSONObject();
        userJson.put("receiver","supergroup100");
        userJson.put("receiverType","group");

        MessageIDTest.getCommon().
                body(userJson.toString(1)).
                when().
                post("https://document-"+GlobalClassTest.prop.getProperty("region")+"."+GlobalClassTest.prop.getProperty("domainName")+"/v1/create").
                then().log().body().
                statusCode(417);
    }
}

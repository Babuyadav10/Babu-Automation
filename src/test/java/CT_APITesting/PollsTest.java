package CT_APITesting;
import io.restassured.http.Method;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class PollsTest  extends BaseTest {
    public String pollGroupMessageId;
    public String pollUserMessageId;
    @Test(description = "Verify create polls for group functionality")
    public void createPollsForGroup()
    {
        RequestSpecification httpRequestObject = MessageIDTest.getCommon();

        JSONObject userJson = new JSONObject();
        userJson.put("question", "Who is best tester");
        userJson.put("options", new String[]{"babu","sam","kat"});
        userJson.put("receiver", "supergroup");
        userJson.put("receiverType", "group");

        httpRequestObject.body(userJson.toString());
        Response response = httpRequestObject.request(Method.POST, "https://polls-"+GlobalClassTest.prop.getProperty("region")+"."+GlobalClassTest.prop.getProperty("domainName")+"/v1/create");

        String responseBody = response.getBody().asString();
        System.out.println("responseBody : " + responseBody);
        int statusCode = response.getStatusCode();
        Assert.assertEquals(statusCode, 200);

        PathFinder(responseBody);
        pollGroupMessageId = js.getString("data.message.data.id");
    }

    @Test(description = "Verify create Polls For User functionality")
    public void createPollsForUser()
    {
        RequestSpecification httpRequestObject = MessageIDTest.getCommon();

        JSONObject userJson = new JSONObject();
        userJson.put("question", "Who is best automation tester");
        userJson.put("options", new String[]{"babu1","sam1","kat1"});
        userJson.put("receiver", "superhero3");
        userJson.put("receiverType", "user");

        httpRequestObject.body(userJson.toString());
        Response response = httpRequestObject.request(Method.POST, "https://polls-"+GlobalClassTest.prop.getProperty("region")+"."+GlobalClassTest.prop.getProperty("domainName")+"/v1/create");

        String responseBody = response.getBody().asString();
        System.out.println("responseBody : " + responseBody);
        int statusCode = response.getStatusCode();
        Assert.assertEquals(statusCode, 200);

        PathFinder(responseBody);
        pollUserMessageId = js.getString("data.message.data.id");

    }

    @Test(description = "Verify create Polls For empty question functionality")
    public void createPollsEmptyQuestion()
    {
        RequestSpecification httpRequestObject = MessageIDTest.getCommon();

        JSONObject userJson = new JSONObject();
        userJson.put("question", " ");
        userJson.put("options", new String[]{"babu1","sam1","kat1"});
        userJson.put("receiver", "supergroup");
        userJson.put("receiverType", "group");

        httpRequestObject.body(userJson.toString());
        Response response = httpRequestObject.request(Method.POST, "https://polls-"+GlobalClassTest.prop.getProperty("region")+"."+GlobalClassTest.prop.getProperty("domainName")+"/v1/create");

        String responseBody = response.getBody().asString();
        System.out.println("responseBody : " + responseBody);
        int statusCode = response.getStatusCode();
        System.out.println("statusCode is  " + statusCode);
        Assert.assertEquals(statusCode, 400);

    }

    @Test(description = "Verify create Polls For empty option functionality")
    public void createPollsEmptyOptions()
    {
        RequestSpecification httpRequestObject =MessageIDTest.getCommon();

        JSONObject userJson = new JSONObject();
        userJson.put("question", "Options is empty ");
        userJson.put("options", new String[]{" "});
        userJson.put("receiver", "supergroup");
        userJson.put("receiverType", "group");

        httpRequestObject.body(userJson.toString());
        Response response = httpRequestObject.request(Method.POST, "https://polls-"+GlobalClassTest.prop.getProperty("region")+"."+GlobalClassTest.prop.getProperty("domainName")+"/v1/create");

        String responseBody = response.getBody().asString();
        System.out.println("responseBody : " + responseBody);
        int statusCode = response.getStatusCode();
        System.out.println("statusCode is  " + statusCode);
        Assert.assertEquals(statusCode, 400);
    }

    @Test(description = "Verify votes polls for group", dependsOnMethods = {"createPollsForGroup"})

    public void votesPollsForGroup()
    {
        System.out.println("pollGroupMessageId : " + pollGroupMessageId);

        RequestSpecification httpRequestObject =MessageIDTest.getCommon();

        JSONObject userJson = new JSONObject();
        userJson.put("vote", "1");
        userJson.put("id", pollGroupMessageId);

        httpRequestObject.header("authToken",GlobalClassTest.prop.getProperty("authToken"));

        httpRequestObject.body(userJson.toString());
        Response response = httpRequestObject.request(Method.POST, "https://polls-"+GlobalClassTest.prop.getProperty("region")+"."+GlobalClassTest.prop.getProperty("domainName")+"/v1/vote");

        String responseBody = response.getBody().asString();
        System.out.println("responseBody : " + responseBody);
        int statusCode = response.getStatusCode();
        System.out.println("statusCode is  " + statusCode);
        Assert.assertEquals(statusCode, 200);
    }

    @Test(description = "Verify votes polls for user", dependsOnMethods = {"createPollsForUser"})

    public void votesPollsForUser()
    {
        RequestSpecification httpRequestObject = MessageIDTest.getCommon();

        JSONObject userJson = new JSONObject();
        userJson.put("vote", "2");
        userJson.put("id", pollUserMessageId);

        httpRequestObject.header("authToken",GlobalClassTest.prop.getProperty("authToken3"));

        httpRequestObject.body(userJson.toString());
        Response response = httpRequestObject.request(Method.POST, "https://polls-"+GlobalClassTest.prop.getProperty("region")+"."+GlobalClassTest.prop.getProperty("domainName")+"/v1/vote");

        String responseBody = response.getBody().asString();
        System.out.println("responseBody : " + responseBody);
        int statusCode = response.getStatusCode();
        System.out.println("statusCode is  " + statusCode);
        Assert.assertEquals(statusCode, 200);

    }

    @Test(description = "Verify votes polls for empty vote option", dependsOnMethods = {"createPollsForGroup"})

    public void votesPollsEmptyVoteOption()
    {

        RequestSpecification httpRequestObject =MessageIDTest.getCommon();

        JSONObject userJson = new JSONObject();
        userJson.put("vote", " ");
        userJson.put("id", pollGroupMessageId);

        httpRequestObject.header("authToken",GlobalClassTest.prop.getProperty("authToken3"));

        httpRequestObject.body(userJson.toString());
        Response response = httpRequestObject.request(Method.POST, "https://polls-"+GlobalClassTest.prop.getProperty("region")+"."+GlobalClassTest.prop.getProperty("domainName")+"/v1/vote");

        String responseBody = response.getBody().asString();
        System.out.println("responseBody : " + responseBody);

        int statusCode = response.getStatusCode();
        System.out.println("statusCode is  " + statusCode);
        Assert.assertEquals(statusCode, 400);
    }

    @Test(description = "Verify votes polls for empty id..", dependsOnMethods = {"createPollsForUser"})

    public void votesPollsEmptyId() {

        RequestSpecification httpRequestObject = MessageIDTest.getCommon();

        JSONObject userJson = new JSONObject();
        userJson.put("vote", "2");
        userJson.put("id","");

        httpRequestObject.header("authToken",GlobalClassTest.prop.getProperty("authToken3"));

        httpRequestObject.body(userJson.toString());
        Response response = httpRequestObject.request(Method.POST, "https://polls-"+GlobalClassTest.prop.getProperty("region")+"."+GlobalClassTest.prop.getProperty("domainName")+"/v1/vote");

        String responseBody = response.getBody().asString();
        System.out.println("responseBody : " + responseBody);

        int statusCode = response.getStatusCode();
        System.out.println("statusCode is  " + statusCode);
        Assert.assertEquals(statusCode, 400);

    }

    @Test(description = "Verify Polls get Result For Group", dependsOnMethods = {"votesPollsForGroup"})

    public void getResultForGroup()
    {
        System.out.println("pollGroupMessageId : " + pollGroupMessageId);
        RequestSpecification httpRequestObject = MessageIDTest.getCommon();

        httpRequestObject.queryParam("id",pollGroupMessageId);
        httpRequestObject.header("authToken",GlobalClassTest.prop.getProperty("authToken"));

      Response response = httpRequestObject.request(Method.GET, "https://polls-"+GlobalClassTest.prop.getProperty("region")+"."+GlobalClassTest.prop.getProperty("domainName")+"/v1/results");

        String responseBody = response.getBody().asString();
        System.out.println("responseBody : " + responseBody);
        int statusCode = response.getStatusCode();
        System.out.println("statusCode is  " + statusCode);
        Assert.assertEquals(statusCode, 200);
    }

    @Test(description = "Verify Polls get Result For user", dependsOnMethods = {"votesPollsForUser"})

    public void getResultForUser()
    {
        System.out.println("pollGroupMessageId : " + pollUserMessageId);
        RequestSpecification httpRequestObject = MessageIDTest.getCommon();

        httpRequestObject.queryParam("id",pollUserMessageId);
        httpRequestObject.header("authToken",GlobalClassTest.prop.getProperty("authToken"));

        Response response = httpRequestObject.request(Method.GET, "https://polls-"+GlobalClassTest.prop.getProperty("region")+"."+GlobalClassTest.prop.getProperty("domainName")+"/v1/results");

        String responseBody = response.getBody().asString();
        System.out.println("responseBody : " + responseBody);
        int statusCode = response.getStatusCode();
        System.out.println("statusCode is  " + statusCode);
        Assert.assertEquals(statusCode, 200);
    }

}

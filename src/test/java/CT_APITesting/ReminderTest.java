package CT_APITesting;

import org.json.JSONObject;
import org.testng.annotations.Test;

public class ReminderTest extends BaseTest {
    long  BASE = 1704479143000L;
    String reminderId;
    @Test(description = "Verify reminder functionality")
    public void reminder()
    {
        JSONObject userJson = new JSONObject();
        userJson.put("isCustom",true);
        userJson.put("about","this is in msg in usRegion");
        userJson.put("timeInMS",BASE);

        String responseBody= MessageIDTest.getCommon().
                body(userJson.toString(1)).
                when().
                post("https://reminders-us.cometchat.io/v1/reminder").
                then().log().body().
                assertThat().statusCode(200).extract().body().asString();
        System.out.println(responseBody);
        PathFinder(responseBody);
        reminderId = js.getString("data.reminderId");
    }
    @Test(description = "Verify reminder update functionality", dependsOnMethods = {"reminder"})
    public void reminderUpdate()
    {
        JSONObject userJson = new JSONObject();
        userJson.put("reminderId",reminderId);
        userJson.put("isCustom",true);
        userJson.put("about","Doing something else");
        userJson.put("timeInMS",BASE);

        String responseBody= MessageIDTest.getCommon().
                body(userJson.toString(1)).
                when().
                put("https://reminders-us.cometchat.io/v1/reminder").
                then().
                assertThat().statusCode(200).extract().body().asString();
        System.out.println(responseBody);

    }
    @Test(description = "Verify reminder delete functionality", dependsOnMethods = {"reminderUpdate"})
    public void reminderDelete()
    {
        JSONObject userJson = new JSONObject();
        userJson.put("reminderId",reminderId);

        String responseBody= MessageIDTest.getCommon().
                body(userJson.toString(1)).
                when().
                delete("https://reminders-us.cometchat.io/v1/reminder").
                then().
                assertThat().statusCode(200).extract().body().asString();
        System.out.println(responseBody);

    }

    @Test(description = "Verify fetch reminder functionality", dependsOnMethods = {"reminderDelete"})
    public void fetchReminder()
    {
        String responseBody= MessageIDTest.getCommon().
                when().
                get("https://reminders-"+GlobalClassTest.prop.getProperty("region")+"."+GlobalClassTest.prop.getProperty("domainName")+"/v1/fetch").
                then().
                assertThat().statusCode(200).extract().body().asString();
        System.out.println(responseBody);
    }
}

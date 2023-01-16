package CT_APITesting;

import org.json.JSONObject;
import org.testng.annotations.Test;

public class ImageThumbnailTest extends BaseTest{


    @Test(description = "Verify image thumbnail functionality")
    public void imageThumbnail()
    {
        MessageIDTest b=new MessageIDTest();
        b.messageID();
        String a =MessageIDTest.msgID1;
        JSONObject data1 = new JSONObject(a);

        JSONObject userJson = new JSONObject();
        userJson.put("data",data1.get("data"));

        MessageIDTest.getCommon().
                auth().preemptive().basic("user_Z2m3FFAtnFFJGz2h","x4cDnjPj8zpssnUD").
                body(userJson.toString(1)).
                when().
                post("https://thumbnail-generator-"+GlobalClassTest.prop.getProperty("region")+"."+GlobalClassTest.prop.getProperty("domainName")+"/generate").
                then().log().body().
                statusCode(200);
    }

    @Test(description = "Verify image thumbnail invalid authToken functionality")
    public void imageThumbnailInvalidAuth()
    {
        MessageIDTest b=new MessageIDTest();
        b.messageID();
        String a =MessageIDTest.msgID1;
        JSONObject data1 = new JSONObject(a);

        JSONObject userJson = new JSONObject();
        userJson.put("data",data1.get("data"));

        MessageIDTest.getCommon().
                auth().preemptive().basic("user_Z2m3FFAtnFFJGz2h1","x4cDnjPj8zpssnUD").
                body(userJson.toString(1)).
                when().
                post("https://thumbnail-generator-"+GlobalClassTest.prop.getProperty("region")+"."+GlobalClassTest.prop.getProperty("domainName")+"/generate").
                then().log().body().
                statusCode(401);
    }

//    @Test(description = "Verify image thumbnail empty data functionality")
//    public void imageThumbnailEmptyData()
//    {
////        MessageIDTest b=new MessageIDTest();
////        b.messageID();
////        String a =MessageIDTest.msgID1;
////        JSONObject data1 = new JSONObject(a);
//
//        JSONObject userJson = new JSONObject();
//        userJson.put("data"," ");
//
//        MessageIDTest.getCommon().
//                auth().preemptive().basic("user_Z2m3FFAtnFFJGz2h","x4cDnjPj8zpssnUD").
//                body(userJson.toString(1)).
//                when().
//                post("https://thumbnail-generator-"+GlobalClassTest.prop.getProperty("region")+"."+GlobalClassTest.prop.getProperty("domainName")+"/generate").
//                then().log().body().
//                statusCode(400);
//    }


}

package CT_APITesting;

import io.restassured.http.Method;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;
import static org.hamcrest.Matchers.equalTo;
import static io.restassured.RestAssured.given;

public class MessageTest extends BaseTest {

    public static String gName;
    public static String UName;
    String receiverName = "superhero1";

   public  static String idName1;
    public  static String idName2;

    public  static String idName3;
    public  static String  gidName1;
    public  String idName;

    String[] multiReciever = {"superhero2", "superhero3", "superhero4"};

    String[] booleanType = {"false", "true"};
    String mainApiKey = "10ca9c4268ffa7ef032de02e8606da7e3bf67b4f";


    @Test(description = "Verify add the members functionality")

    public void SendMessage() {

       /*
        GroupTest gt = new GroupTest();
        gt.intc = -1;
        gt.createGroups();

        UsersTest ut = new UsersTest();
        ut.ptc = -1;
        ut.createUser();

        */

        //  MemberTest.gName=gName;
        //  MemberTest.UName=UName;
        MemberTest mt1 = new MemberTest();
        mt1.bs = -1;
        mt1.addMembers();


        //  System.out.println("group id name = " + gName);
        //  System.out.println("user id name = " + UName);

        RequestSpecification httpRequestObject = given();

        JSONObject userJson = new JSONObject();
        userJson.put("receiver", "superhero5");
        userJson.put("receiverType", "user");
        userJson.put("category", "message");
        userJson.put("type", "text");
        // userJson.put("data","{\"text\": \"Hi Tom!\", \"metadata\":{\"key1\":\"value1\",\"key2\":\"value2\"}}");
        userJson.put("data", "{\"text\": \"babu is the great tester\"}");


        JSONObject userJson1 = new JSONObject();
        userJson1.put("uids", multiReciever);
        userJson1.put("guids", new String[]{gName});

        userJson.put("multipleReceivers", userJson1);


        httpRequestObject.header("Content-Type", "application/json");
        httpRequestObject.header("apiKey", mainApiKey);
        httpRequestObject.header("Accept", "application/json");
        httpRequestObject.header("onBehalfOf", UName);

        httpRequestObject.body(userJson.toString());
        Response response = httpRequestObject.request(Method.POST, "/messages");

        String responseBody = response.getBody().asString();
        System.out.println("responseBody : " + responseBody);

        int statusCode = response.getStatusCode();

        System.out.println("statusCode is  " + statusCode);
        Assert.assertEquals(statusCode, 200);
       PathFinder(responseBody);

        Assert.assertEquals(js.getString("data.uids.superhero2.data.text"), "babu is the great tester");
      //  Assert.assertEquals(responseBody.contains("babu is the great tester1"), true);


    }

    @Test(description = "Verify list Members", dependsOnMethods = {"SendMessage"})

    public void ListMessage() {

        RequestSpecification httpRequestObject = given();


        httpRequestObject.header("Content-Type", "application/json");
        httpRequestObject.header("apiKey", mainApiKey);
        httpRequestObject.header("Accept", "application/json");
       httpRequestObject.header("onBehalfOf", UName);

        Response response = httpRequestObject.request(Method.GET, "/messages");

        String responseBody = response.getBody().asString();
        System.out.println("responseBody : " + responseBody);

        response.prettyPrint();

        JsonPath extractor= response.jsonPath();
        String idName= extractor.get("data[1].id");
        String GidName= extractor.get("data[0].id");
        System.out.println("id: " +idName);
        System.out.println("id: " +GidName);

        MessageTest.idName1=idName;
        MessageTest.gidName1=GidName;

    }





/*
        RequestSpecification httpRequestObject = RestAssured.given();

        // httpRequestObject.queryParam("receiverType", "group");

        httpRequestObject.header("Content-Type", "application/json");
        httpRequestObject.header("apiKey", mainApiKey);
        httpRequestObject.header("Accept", "application/json");
        httpRequestObject.header("onBehalfOf", UName);

        Response response = httpRequestObject.request(Method.GET, "/messages");

      String responseBody = response.getBody().asString();
        //   System.out.println("responseBody : " + responseBody);

        //      int statusCode = response.getStatusCode();

        //       System.out.println("statusCode is  " + statusCode);
        //      Assert.assertEquals(statusCode, 200);

        // Assert.assertEquals(responseBody.contains("babu is the great tester"),true);

        JsonPath extractor = response.jsonPath();
        String id = extractor.get("id");
        System.out.println("id: " + id);



 */






    @Test(description = "Verify get Members", dependsOnMethods = {"ListMessage"})

    public void GetMessage() {

        System.out.println("idName : " + idName1);


        RequestSpecification httpRequestObject = given();

      // httpRequestObject.queryParam("id", idName1);

        httpRequestObject.header("Content-Type", "application/json");
        httpRequestObject.header("apiKey", mainApiKey);
        httpRequestObject.header("Accept", "application/json");

      //  System.out.println("idName : " + idName);
        Response response = httpRequestObject.request(Method.GET, "/messages/"+idName1);

       String responseBody = response.getBody().asString();

      //  response.prettyPrint();

      System.out.println("responseBody : " + responseBody);

  //   JsonPath extractor= response.jsonPath();
    // String idName= extractor.get("data[1].id");
    //  System.out.println("id: " +idName);


        // String userId = StringUtils.substringAfter(locationHeader, "/messages/");
        //  System.out.println(userId);






    }


    @Test (description = "Verify get Members", dependsOnMethods = {"ListMessage"})

    public void GetGroupMessage() {

        System.out.println("groupName : " + gidName1);


        RequestSpecification httpRequestObject = given();

        httpRequestObject.header("Content-Type", "application/json");
        httpRequestObject.header("apiKey", mainApiKey);
        httpRequestObject.header("Accept", "application/json");
     //   httpRequestObject.header("onBehalfOf", gidName1);

        Response response = httpRequestObject.request(Method.GET, "/messages/"+gidName1);

        String responseBody = response.getBody().asString();

        //  response.prettyPrint();

        System.out.println("responseBody : " + responseBody);

        //   JsonPath extractor= response.jsonPath();
        // String idName= extractor.get("data[1].id");
        //  System.out.println("id: " +idName);


        // String userId = StringUtils.substringAfter(locationHeader, "/messages/");
        //  System.out.println(userId);






    }





    @Test(description = "Verify list Members", dependsOnMethods = {"GetMessage"})

    public void updateMessage() {

        RequestSpecification httpRequestObject = given();

        JSONObject userJson = new JSONObject();

       // userJson.put("data", "{\"text\": \"babu is tester\"}");
       // userJson.put("[\"data", "{\"text\": \"babu is tester\"}\"]");

        userJson.put("text","hello");

        JSONObject userJson1 = new JSONObject();
        userJson1.put("data",userJson);


        httpRequestObject.header("Content-Type", "application/json");
        httpRequestObject.header("apiKey", mainApiKey);
        httpRequestObject.header("Accept", "application/json");
       // httpRequestObject.header("onBehalfOf", UName);

        httpRequestObject.body(userJson1.toString());
        Response response = httpRequestObject.request(Method.PUT, "/messages/"+idName1);

        String responseBody = response.getBody().asString();
        System.out.println("responseBody : " + responseBody);



    }

    @Test(description = "Verify get Members", dependsOnMethods = {"updateMessage"})

    public void DeleteMessage() {

        System.out.println("idName : " + idName1);


        RequestSpecification httpRequestObject = given();

        JSONObject userJson = new JSONObject();
        userJson.put("permanent","true");

        httpRequestObject.header("Content-Type", "application/json");
        httpRequestObject.header("apiKey", mainApiKey);
        httpRequestObject.header("Accept", "application/json");
      //  httpRequestObject.header("onBehalfOf", UName);

        Response response = httpRequestObject.request(Method.DELETE, "/messages/"+idName1);

        String responseBody = response.getBody().asString();

        //  response.prettyPrint();

      //  System.out.println("responseBody : " + responseBody);

        response.prettyPrint();
        JsonPath extractor= response.jsonPath();
        String idName2= extractor.get("data.data.entities.on.entity.id");
        System.out.println("id actual: " +idName2);
        MessageTest.idName3=idName2;

    }

    @Test(description = "Verify get after  delete id", dependsOnMethods = {"DeleteMessage"})

    public void GetAfterDeleteMessage() {



       // System.out.println("id : " + idName1);


        RequestSpecification httpRequestObject = given();

        // httpRequestObject.queryParam("id", idName1);

        httpRequestObject.header("Content-Type", "application/json");
        httpRequestObject.header("apiKey", mainApiKey);
        httpRequestObject.header("Accept", "application/json");

        //  System.out.println("idName : " + idName);
        Response response = httpRequestObject.request(Method.GET, "/messages/"+idName1);

        String responseBody = response.getBody().asString();

        //  response.prettyPrint();

        System.out.println("responseBody : " + responseBody);

    }







}

package RealtedTest;

import CT_APITesting.BaseTest;
import CT_APITesting.CriticalJsonTest;
import CT_APITesting.TakeTest;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class PojoTest extends BaseTest {



    @Test(priority = 0)
    public void userDetails(){
        TakeTest tt = given().header("Content-Type","application/json")
                .body(userJson)
                .when().post("api/users").as(TakeTest.class);


        System.out.println(tt.getName());
        System.out.println(tt.getId());
        System.out.println(tt.getJob());
        System.out.println(tt.getCreatedAt());
    }

    @Test(priority = 1)
    public void userDetails1(){
        CriticalJsonTest tt = given().queryParam("page","2").header("Content-Type","application/json").log().all()
                .when().get("api/users").as(CriticalJsonTest.class);


        System.out.println(tt.getPage());
        System.out.println(tt.getPer_page());
        System.out.println(tt.getTotal());
        System.out.println(tt.getTotal_pages());
        System.out.println("*************************");
        System.out.println(tt.getAd().getCompany());
        System.out.println(tt.getAd().getText());
        System.out.println(tt.getAd().getUrl());
        System.out.println("*************************");
        int ListDetails = tt.getData().size();

        for(int i=0;i<ListDetails;i++) {
            System.out.println(tt.getData().get(i).getId());
            System.out.println(tt.getData().get(i).getEmail());
            System.out.println(tt.getData().get(i).getFirst_name());
            System.out.println(tt.getData().get(i).getLast_name());
            System.out.println(tt.getData().get(i).getAvatar());
        }
    }
}

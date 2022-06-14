package CT_APITesting;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Properties;

public class BaseTest {

    Properties prop;
    JsonPath js;
    File fs;
    FileInputStream fis;
    XSSFWorkbook wb;
    XSSFSheet sh;
    int TotalRowCount;
    public HashMap<String,Object> userJson;


    @BeforeClass
    public void StartApiTest() throws Throwable{
        prop = new Properties();
        InputStream fi = new FileInputStream("./Properties/Global.properties");
        prop.load(fi);
        String URI = prop.getProperty("baseURL");

        RestAssured.baseURI = URI;
        userJson = new HashMap<>();
//        userJson.put("name","Rohit");
//        userJson.put("job","QA");
    }

    @AfterClass
    public void EndAPITest() {
    }

    public void PathFinder(String Response){
        js = new JsonPath(Response);
    }

    public void CSV(String FilePath)throws Throwable{
        fs = new File(FilePath);
        fis = new FileInputStream(fs);
        wb = new XSSFWorkbook(fis);
        sh = wb.getSheetAt(0);
        TotalRowCount = sh.getLastRowNum();
        System.out.println("Total Number Of Rows:-"+TotalRowCount);
    }

    public void CreateJson(){
        HashMap<String,Object> userJson = new HashMap<>();
        userJson.put("name","Rohit");
        userJson.put("job","QA");
    }

}

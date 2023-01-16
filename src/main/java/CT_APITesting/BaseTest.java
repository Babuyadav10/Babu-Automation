package CT_APITesting;

import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import io.restassured.RestAssured;
import io.restassured.config.LogConfig;
import io.restassured.filter.log.LogDetail;
import io.restassured.path.json.JsonPath;
import org.apache.http.client.ClientProtocolException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.xssf.usermodel.XSSFSheet;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.annotations.*;


import java.io.*;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Properties;
import java.util.Random;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class BaseTest {
    public static ExtentTest extentTest;
    public static String baseDir;
    public static ExtentReports report = null;
    static String reportName = null;
    public static String mailSubject, userType, reportFoldername, URL;
    public static String templatename, memberLogin, campaignName;
    public static String clientType;
    Properties prop;
    JsonPath js;
    File fs;
    FileInputStream fis;
    XSSFWorkbook wb;
    XSSFSheet sh;
    int TotalRowCount;


    @Parameters({ "client", "toggle", "url" })
    @BeforeSuite
    public void beforeSuiteFunction(@Optional("") String client, @Optional("") String toggle, @Optional("") String url) {

        baseDir = "./Report_Zip/";


    }

    @Parameters({ "userType", "templateName", "campaignName", "memberLogin" })
    @BeforeTest(alwaysRun = true)
    public void beforeTestFunction(@Optional("") String userType, @Optional String templateName,
                                   @Optional String campaignName, @Optional String memberLogin, @Optional ITestContext ctx)
            throws JsonIOException, JsonSyntaxException, InterruptedException, InvalidFormatException, IOException {



        // Code for generating Extent report
        reportName = ctx.getName() + "_Report" + ".html";
        String suiteName = ctx.getCurrentXmlTest().getSuite().getName();
        System.out.println(reportName);
        report = new ExtentReports("./Report/" + suiteName + "/" + reportName);
        reportFoldername = suiteName;
        report.addSystemInfo("Test Suite", ctx.getCurrentXmlTest().getSuite().getName());

        BaseTest.userType = userType;
        BaseTest.campaignName = campaignName;
        BaseTest.templatename = templateName;
        BaseTest.memberLogin = memberLogin;

    }

    @BeforeMethod(alwaysRun = true)
    public void beforeMethodFunction(ITestContext ctx, Method method) {

        String testScenario = method.getAnnotation(org.testng.annotations.Test.class).description();
        testScenario = (testScenario == "") ? "scenario not mentioned" : testScenario;

        String testXMLFileName = ctx.getCurrentXmlTest().getSuite().getFileName();
        String methodName = method.getName();
        String testDesription = "(method name = " + methodName + ")" + testXMLFileName;

        report.loadConfig(new File("./extent-config.xml"));
        extentTest = report.startTest(testScenario, testDesription);
        System.out.println("we are in " + method.getName());
    }


    @BeforeClass
    public void StartApiTest() throws Throwable{
        prop = new Properties();
        InputStream fi = new FileInputStream("./Properties/Global.properties");
        prop.load(fi);
        String URI = prop.getProperty("baseURL");

        RestAssured.baseURI = URI;

       // userJson = new HashMap<>();
//        userJson.put("name","Rohit");
//        userJson.put("job","QA");
    }

    public String getRandomString(String prefix) {

        Random rand = new Random();
        int rand_int1 = rand.nextInt(1000000);
        String randomString = prefix + rand_int1;
        return randomString;
    }

    @AfterMethod(alwaysRun = true)
    public void afterMethodFunction(Method method, ITestResult result) throws ClientProtocolException, IOException {
        if (result.getStatus() == ITestResult.FAILURE) {
            extentTest.log(LogStatus.FAIL, "Test Case Failed: " + result.getName());
            extentTest.log(LogStatus.FAIL, "Test Case Failed reason: " + result.getThrowable());
        } else if (result.getStatus() == ITestResult.SKIP) {
            extentTest.log(LogStatus.SKIP, "Test Case Skipped: " + result.getName());
        } else if (result.getStatus() == ITestResult.SUCCESS) {
            extentTest.log(LogStatus.PASS, "Test Case Passed: " + result.getName());
        }
        report.endTest(extentTest);
        report.flush();

        String testScenario = method.getAnnotation(org.testng.annotations.Test.class).description();
        testScenario = (testScenario == "") ? "scenario not mentioned" : testScenario;

    }

    @AfterClass
    public void EndAPITest() {
    }

    @AfterSuite
    public static void zipFolder()throws IOException
    {
        String sourceFile = "./Report/Regression_Test";
        FileOutputStream fos = new FileOutputStream("./Reports_Zip/Automation_Test_Report.zip");
        ZipOutputStream zipOut = new ZipOutputStream(fos);
        File fileToZip = new File(sourceFile);

        zipFile(fileToZip, fileToZip.getName(), zipOut);
        zipOut.close();
        fos.close();

    }

    private static void zipFile(File fileToZip, String fileName, ZipOutputStream zipOut) throws IOException {
        if (fileToZip.isHidden()) {
            return;
        }
        if (fileToZip.isDirectory()) {
            if (fileName.endsWith("/")) {
                zipOut.putNextEntry(new ZipEntry(fileName));
                zipOut.closeEntry();
            } else {
                zipOut.putNextEntry(new ZipEntry(fileName + "/"));
                zipOut.closeEntry();
            }
            File[] children = fileToZip.listFiles();
            for (File childFile : children) {
                zipFile(childFile, fileName + "/" + childFile.getName(), zipOut);
            }
            return;
        }
        FileInputStream fis = new FileInputStream(fileToZip);
        ZipEntry zipEntry = new ZipEntry(fileName);
        zipOut.putNextEntry(zipEntry);
        byte[] bytes = new byte[1024];
        int length;
        while ((length = fis.read(bytes)) >= 0) {
            zipOut.write(bytes, 0, length);
        }
        fis.close();
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

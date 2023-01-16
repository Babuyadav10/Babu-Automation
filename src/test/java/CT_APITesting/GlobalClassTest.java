package CT_APITesting;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class GlobalClassTest {

   static  Properties prop;

   // void GlobalClassTest() throws IOException
    static
    {
        try {
            prop = new Properties();
            InputStream fi = new FileInputStream("./Properties/Global.properties");
            prop.load(fi);
         }
        catch(Exception ext) {}

    }

}

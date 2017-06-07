import org.apache.log4j.*;

import java.util.Date;

/**
 * Created by sergeygo on 14.11.2016.
 */
public class MainZipLogFiles {
    public static Logger logger = Logger.getLogger("test.MainSeparateDebugAndInfoLogFiles");

    static {
        PropertyConfigurator.configure(MainZipLogFiles.class.getResource("./log4j.zip.properties"));
    }

    public static void main(String[] args) {
        while(true) {
            logger.info(".............................................................................................");
        }
    }
}

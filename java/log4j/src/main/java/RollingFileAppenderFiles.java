import org.apache.log4j.Appender;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.apache.log4j.RollingFileAppender;

import java.util.Enumeration;

/**
 * Created by sergeygo on 14.11.2016.
 */
public class RollingFileAppenderFiles {
    public static Logger logger = Logger.getLogger("test.MainSeparateDebugAndInfoLogFiles");

    static {
        PropertyConfigurator.configure(RollingFileAppenderFiles.class.getResource("./log4j.rollingFileAppender.properties"));
    }

    public static void main(String[] args) throws InterruptedException {
        final Enumeration allAppenders = Logger.getRootLogger().getAllAppenders();
        while(allAppenders.hasMoreElements()) {
            System.err.println("" + ((Appender)allAppenders.nextElement()).getName());
        }
        ((RollingFileAppender)Logger.getRootLogger().getAppender("file")).rollOver();
        while(true) {
            Thread.sleep(1000);
            logger.info(".............................................................................................");
        }
    }
}

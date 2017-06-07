import org.apache.log4j.*;

import java.util.Date;

/**
 * Created by sergeygo on 14.11.2016.
 */
public class MainSeparateDebugAndInfoLogFiles {
    public static Logger logger = Logger.getLogger("test.MainSeparateDebugAndInfoLogFiles");
    public static Level initialRootLoggerLevel;

    static {
        PropertyConfigurator.configure(MainZipLogFiles.class.getResource("./log4j.separateDebugAndInfo.properties"));
        initialRootLoggerLevel = Logger.getRootLogger().getLevel();
        Logger.getRootLogger().setLevel(Level.TRACE);
    }

    public static void main(String[] args) {
        logger.info("platform start: info log");
        logger.debug("platform start: debug log");

        Logger.getRootLogger().setLevel(initialRootLoggerLevel);
        final AppenderSkeleton dlog = (AppenderSkeleton)Logger.getRootLogger().getAppender("DLOG");
        dlog.setThreshold(Priority.toPriority("OFF"));

        logger.info("after platform start: info log");
        logger.debug("platform start: debug log");

        Date currentDate = new Date();

        logger.info("current date " + currentDate);
    }
}

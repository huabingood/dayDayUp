import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;


public class Log4jUtils {
    public static Logger logger;

    static{
        String log4jPropertiesPath="/home/yhb/coding/dayDayUp/BigData_mvn/conf/log4j.properties";
        System.setProperty("log4j.configuration",log4jPropertiesPath);
        logger = LogManager.getLogger("日志收集");
    }

}

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Test;

import DDL.MyDDL;

import static org.junit.Assert.assertEquals;

/**
 * 实际上测试类时不需要继承TestCase父类的
 */
public class MyHBaseTest {

    private static Log log = LogFactory.getLog("日志采集1：");

    @Test
    public void createTableTest(){
        boolean b = new MyDDL().myCreateTable();
        assertEquals(true,b);
        log.error("失败");
    }

}

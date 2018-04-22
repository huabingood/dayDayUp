
import DML.MyDML;
import com.amazonaws.annotation.SdkTestInternalApi;
import org.apache.hadoop.hbase.util.Bytes;
import org.junit.Test;

import DDL.MyDDL;

import static org.junit.Assert.assertEquals;

/**
 * 实际上测试类时不需要继承TestCase父类的
 */
public class MyHBaseTest {


    @Test
    public void createTableTest(){
        boolean b = new MyDDL().myCreateTable();
        assertEquals(true,b);
    }

    @Test
    public void  myDeleteTableTest(){
        boolean b = new MyDDL().myDeleteTable();
        assertEquals(true,b);
    }

    @Test
    public void myInsert2HBaseTableTest(){
        boolean b = new MyDML().insert2HBaseTable();
        assertEquals(true,b);
    }

    @Test
    public void alterHbaseTableDataTest(){
        boolean b = new MyDML().alterHbaseTableData();
        assertEquals(true,b);
    }

    @Test
    public void deleteDataTest(){
        boolean b = new MyDML().deleteData();
        assertEquals(true,b);
    }

    @Test
    public void getOneTest(){
        // System.out.println(Integer.parseInt("\\x00\\x00\\x00\\x00\\x00\\x00\\x04\\x14",8));
        // System.out.println(Bytes.toBytes(1044));
        new MyDML().getOne();
    }

    @Test
    public void getAllTest(){
        new MyDML().getAll();
    }

    @Test
    public void scanFilterTest(){
        new MyDML().scanFilter();
    }
}

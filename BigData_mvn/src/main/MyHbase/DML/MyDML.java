package DML;

import DDL.MyDDL;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.filter.*;
import org.apache.hadoop.hbase.util.Bytes;
import org.apache.log4j.PropertyConfigurator;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;

public class MyDML {
    /**
     * 记录日志
     * 但是好像每个类都要写这么一句，但是不知道怎么改进
     */
    private static Log myLog = LogFactory.getLog(MyDDL.class);
    static {
        PropertyConfigurator.configure("/home/yhb/coding/dayDayUp/BigData_mvn/conf/log4j.properties");
    }


    private static Configuration conf = null;
    private static Connection connection = null;
    private HTable hTable = null;
    // 参数设置
    public static final String HBASE_ZK_QUORUM="hbase.zookeeper.quorum";
    public static final String HBASE_ZK_QUORUM_VALUE="huabingood";
    public static final String HBASE_ZK_PROPERTY_CLIRNTPORT="hbase.zookeeper.property.clientPort";
    public static final String HBASE_ZK_PROPERTY_CLIRNTPORT_VALUE="2181";

    // 因为起一个获取配置信息然后创建Hbase连接是一个比较耗费的资源的过程
    // 因此，最好创建一个连接池，让大家共用，这里的ConnectionFactory实际上就是一个连接池
    // 这里的 static{} 实际上是对静态成员变量的显式声明
    static {
        conf = HBaseConfiguration.create();
        conf.set(HBASE_ZK_QUORUM,HBASE_ZK_QUORUM_VALUE);
        conf.set(HBASE_ZK_PROPERTY_CLIRNTPORT,HBASE_ZK_PROPERTY_CLIRNTPORT_VALUE);
        try {
            connection = ConnectionFactory.createConnection(conf);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

@Test
    public void insertOne(){
        Table table = null;

        try {
            // 获取表，不走表管理器
            table = connection.getTable(TableName.valueOf("huabingood:hellohbase"));
                // 通常一个put就是一个rowkey的数据
                Put put = new Put(Bytes.toBytes(9999L));
                // 使用addColumn(),而非add()，已经过时了。第一个参数是列簇，第二个时列名，第三个时value
                put.addColumn(Bytes.toBytes("cf1"),Bytes.toBytes("name"), Bytes.toBytes("hyw"+9999));
                // 这个时带时间戳，时间戳通常为long类型，而不是bytes[] 类型
                put.addColumn(Bytes.toBytes("cf1"),Bytes.toBytes("id"),System.currentTimeMillis(),Bytes.toBytes(2));
            table.put(put);  // 写到表中
            myLog.info("数据写入成功！");

        } catch (IOException e) {
            e.printStackTrace();
            myLog.error("反正这个错误时系统抛出的，跟我没关系。");
        }finally {
            try {
                table.close();
                connection.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }


    /**
     * 往表中写数据
     * rowkey级别的插入数据
     * 步骤：获取表；获取要插入的rowkey的数据；将数据写入表中
     * 返回true为成功
     * @return false
     */
    public boolean insert2HBaseTable(){
        boolean b = false;
        Table table = null;

        try {
            // 获取表，不走表管理器
            table = connection.getTable(TableName.valueOf("huabingood:hellohbase"));
            ArrayList<Put> puts = new ArrayList<Put>();
            for(int i = 0;i<20;i++){
                // 产生指定范围的随机数
                // Math.round(Math.random()*(Max-Min)+Min)
                long myRandom = Math.round(Math.random()*(9999-1000)+1000);
                // 通常一个put就是一个rowkey的数据
                Put put = new Put(Bytes.toBytes(myRandom));
                // 使用addColumn(),而非add()，已经过时了。第一个参数是列簇，第二个时列名，第三个时value
                put.addColumn(Bytes.toBytes("cf1"),Bytes.toBytes("name"), Bytes.toBytes("hyw"+myRandom));
                // 这个时带时间戳，时间戳通常为long类型，而不是bytes[] 类型
                put.addColumn(Bytes.toBytes("cf1"),Bytes.toBytes("id"),System.currentTimeMillis(),Bytes.toBytes(i));
                puts.add(put);
            }
            table.put(puts);  // 写到表中
            b=true;
            myLog.info("数据写入成功！");

        } catch (IOException e) {
            e.printStackTrace();
            myLog.error("反正这个错误时系统抛出的，跟我没关系。");
        }finally {
            try {
                table.close();
                connection.close();
            } catch (IOException e) {
                e.printStackTrace();
                myLog.error("table没有关掉，不知道为什么");
            }
        }

        return b;
    }

    /**
     * 修改已有的rowkey中cell的数据
     * 实际上就是将原有的数据覆盖掉
     * @return
     */
    public boolean alterHbaseTableData(){
        boolean b = false;
        Table table = null;

        try {
            table = connection.getTable(TableName.valueOf("huabingood:hellohbase"));
            Put put = new Put(Bytes.toBytes(9673));
            put.addColumn(Bytes.toBytes("cf1"),Bytes.toBytes("name"),Bytes.toBytes("huabingood"));
            table.put(put);
            b = true;
            myLog.info("数据插入成功！");
        } catch (IOException e) {
            e.printStackTrace();
            myLog.error("修改数据时，系统报错。");
        }finally {
            try {
                table.close();
                connection.close();
            } catch (IOException e) {
                e.printStackTrace();
                myLog.error("插入数据完成后，关闭表报错");
            }
        }
        return b;
    }

    /**
     * 删除就是直接删除rowkey对应的数据
     * 这里需要一个Delete对象，告知删除的rowkey
     * @return
     */
    public boolean deleteData(){
        boolean b = false;
        Table table = null;

        try {
            table = connection.getTable(TableName.valueOf("huabingood:hellohbase"));
            Delete delete = new Delete(Bytes.toBytes(1044));
            table.delete(delete);
            b = true;
            myLog.info("删除数据成功");
        } catch (IOException e) {
            e.printStackTrace();
            myLog.error("删除表中数据时系统报错。");
        }finally {
            try {
                table.close();
                connection.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return b;
    }

    /**
     * 根据rowkey查询一条数据
     * 根据rowkey获取Get对象，然后get返回Result对象，解析即可。
     *
     */
    public void getOne(){
        Table table = null;

        try {
            table = connection.getTable(TableName.valueOf("huabingood:hellohbase"));
            // 根据rowkey创建一个Get对象，然后获取数据
            // 注意数据类型一定要对啊
            Get get = new Get(Bytes.toBytes(9999l));
            // Get get = new Get("\\x00\\x00\\x00\\x00\\x00\\x00&\\xDB");
            Result result = table.get(get);
            // getValue(列簇,列名)
            String cf1_name = Bytes.toString(result.getValue(Bytes.toBytes("cf1"),Bytes.toBytes("name")));
            int cf1_id = Bytes.toInt(result.getValue(Bytes.toBytes("cf1"),Bytes.toBytes("id")));
            System.out.println("cf1:name:"+cf1_name+"\t"+"cf1:id:"+cf1_id);
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                table.close();
                connection.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    /**
     * 全表扫描
     * 支持扫描某个字段
     * 支持rowkey的范围扫描
     */
    public void getAll(){
        Table table = null;
        ResultScanner rs = null;

        Scan scan = new Scan();
        // 范围扫描,好像不能模糊匹配
        scan.setStartRow(Bytes.toBytes(1000l));
        scan.setStopRow(Bytes.toBytes(2000l));
        // 只获取某个列
        scan.addColumn(Bytes.toBytes("cf1"),Bytes.toBytes("name"));

        try {
            table = connection.getTable(TableName.valueOf("huabingood:hellohbase"));
            // 获取全表扫描的结果集
            rs = table.getScanner(scan);
            for(Result result:rs){
                long rowkey = Bytes.toLong(result.getRow());
                String cf1_name = Bytes.toString(result.getValue(Bytes.toBytes("cf1"),Bytes.toBytes("name")));
                int cf1_id=0;
                //int cf1_id = Bytes.toInt(result.getValue(Bytes.toBytes("cf1"),Bytes.toBytes("id")));
                System.out.println("rowkye:"+rowkey+",cf1_name:"+cf1_name+",cf2_id:"+cf1_id+";");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                table.close();
                connection.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void scanFilter(){
        Table table = null;

        Scan scan = new Scan();
        // 列值过滤器
        // 参数分别为：列簇，列名，过滤条件，列值
        // 实际上就是SQL中的where条件中的内容，比如这里的GREATER就是cell值大于hyw9604
        /*SingleColumnValueFilter filter = new SingleColumnValueFilter(
                Bytes.toBytes("cf1"),Bytes.toBytes("name"),
                CompareFilter.CompareOp.GREATER,
                Bytes.toBytes("hyw9604")
        );
        scan.setFilter(filter);*/

        // rowkey过滤器
        // 参数：比较条件，跟谁比较
        RowFilter rowFilter = new RowFilter(CompareFilter.CompareOp.EQUAL,
                new RegexStringComparator("^4"));

        // 看下来的话，好像二进制的无法使用正则进行匹配
        /*RowFilter rowFilter = new RowFilter(CompareFilter.CompareOp.EQUAL,
                new BinaryComparator(Bytes.toBytes("^4l")));*/
        scan.setFilter(rowFilter);

        try {
            table = connection.getTable(TableName.valueOf("huabingood:hellohbase"));
            ResultScanner rs = table.getScanner(scan);

            for(Result result:rs){
                long rowkey = Bytes.toLong(result.getRow());
                String cf1_name = Bytes.toString(result.getValue(Bytes.toBytes("cf1"),Bytes.toBytes("name")));
                int cf1_id = Bytes.toInt(result.getValue(Bytes.toBytes("cf1"),Bytes.toBytes("id")));
                System.out.println("rowkye:"+rowkey+",cf1_name:"+cf1_name+",cf2_id:"+cf1_id+";");
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                table.close();
                connection.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

}

package hbase.Utils;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.*;
import org.apache.hadoop.hbase.client.*;

import java.io.IOException;


public class getHbaseConnection {
    // 服务器端连接是以conf为单位的。最好多个客户端公用一个conf，减轻服务器端压力
    private static Configuration conf = null;
    private static Connection connection = null;

    // static{} 这种方式是显式的对静态变量初始化，这种代码只会被初始化一次，
    // 且在类第一次被装载的时候
    static {
        conf = HBaseConfiguration.create();
        conf.set("hbase.zookeeper.quorum","localhost"); // zk地址
        conf.set("hbase.zookeeper.property.clientPort", "2181"); // zk端口号
        try {
            connection = ConnectionFactory.createConnection(conf);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }





}

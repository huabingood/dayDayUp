package hadoop.hdfs;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.Iterator;
import java.util.Map;

public class HDFSIO {
    /**
     * 获取HDFS相关的配置文件，进行文件和IO操作
     * 第一步：创建Configuration对象，主要用来获取HDFS的配置相关信息
     * 指定该Configuration连接的hdfs的NN路径。实际上这里可以设置很多HDFS中没有设置的内容，优先级是最高的
     * 第二步：。根据Configuration创建FileSystem对象。由于FileSystem是静态类，只能使用get方法获取。
     * 实际上在Configuration中不指定HDFS路径的话，可以在这里通过URI的方式指定
     * 此时务必注意FileSystem的最后一个参数是该HDFS系统的用户名
     */

    FileSystem fs = null;
    Configuration conf = null;

    @Before
    public void init(){
        conf = new Configuration();
        // 指定HDFS路径的方法一
        conf.set("fs.defaultFS","hdfs://huabingood01:9000");
        conf.set("fs.hdfs.impl","org.apache.hadoop.hdfs.DistributedFileSystem");

        try {
            fs = FileSystem.get(conf);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // 指定hdfs路径的方法二
        /*try {
            fs = FileSystem.get(new URI("hdfs://huabingood01:9000"),conf,"hadoop");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }*/


    }

    /**
     * 主要用来遍历conf中所有的配置信息
     * Entry是java中map的内部接口，用于遍历map
     * Configuration会返回一个迭代器，主要是键值对，遍历键值对即可遍历所有conf的配置信息
     */
    @Test
    public void testConfiguration(){
        Iterator<Map.Entry<String,String>> it = conf.iterator();
        while (it.hasNext()){
            Map.Entry<String,String> entry = it.next();
            System.out.println(entry.getKey() +" : " + entry.getValue());
        }
    }

    /**
     * 在hdfs上创建路径
     */
    @Test
    public void testMkdir(){
        //Path path = new Path("/hyw/huabingood");
        try {
            boolean b = fs.mkdirs(new Path("/huabingood/abc"));
            if(b){
                System.out.println("创建成功！");
            } else{
                System.err.println("创建失败！");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     *
     */
    @Test
    public void testCopy2Local(){
        Path hdfsPath = new Path("/hyw/test/hadoop-2.6.0-cdh5.10.0.tar.gz");
        Path localPath = new Path("/home/huabingood/Downloads");
        try {
            fs.copyToLocalFile(hdfsPath,localPath);
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                fs.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Test
    public void testLsHDFS() throws Exception {
        FileStatus[] listStatus = fs.listStatus(new Path("/"));
        for (FileStatus fileStatus : listStatus) {
            System.out.println(fileStatus);
        }
    }

}

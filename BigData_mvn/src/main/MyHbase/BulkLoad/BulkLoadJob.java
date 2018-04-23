package BulkLoad;

import DDL.MyDDL;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.io.ImmutableBytesWritable;

import org.apache.hadoop.hbase.mapreduce.LoadIncrementalHFiles;
import org.apache.hadoop.hbase.util.Bytes;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;

import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.log4j.PropertyConfigurator;
import org.apache.hadoop.hbase.mapreduce.HFileOutputFormat2;

import java.io.IOException;
import java.net.URI;

/**
 * 自己写bulkload将数据转成Hfile，然后加载道HBase中，据说这种导数据的速度很快
 *
 * 使用到的hdfs上的文件内容，中间使用\t分割
 * key1    cf1:col1        value1
 * key1    cf1:col2        value2
 * key1    cf1:col1        value3
 * key4    cf1:col1        value4
 *
 * 在hbase shell中创建表的语句(建表的列簇必须和给定的列簇一样。或者直接指定列簇，文件内容都是cell)
 * create "huabinogood:bulkloadtest","cf1"
 *
 * 目前已知的bug:网上的好多版本较老，很多方法都已经要被抛弃掉了，最好找来源码看看
 *
 * @author huabingood@qq.com
 */
public class BulkLoadJob {
    /**
     * 记录日志
     * 但是好像每个类都要写这么一句，但是不知道怎么改进
     */
    private static Log myLog = LogFactory.getLog(MyDDL.class);
    static {
        PropertyConfigurator.configure("/home/yhb/coding/dayDayUp/BigData_mvn/conf/log4j.properties");
    }

    /**
     * 这实际上是一个类，因为要被main方法调用，所以时静态的
     * 这里主要的也就是一个map函数
     */
    public static class BuloLoadMap extends
            Mapper<LongWritable,Text,ImmutableBytesWritable,Put>{
        public void map(LongWritable key, Text value,Context context){
            String[] words  = value.toString().split("\t");
            String rowkey = words[0];
            String cf = words[1].split(":")[0];
            String cn = words[1].split(":")[1];
            String cellValue = words[2];
            ImmutableBytesWritable rowkyeBytes = new ImmutableBytesWritable(Bytes.toBytes(rowkey));


            Put put = new Put(Bytes.toBytes(rowkey));
            put.addColumn(Bytes.toBytes(cf),Bytes.toBytes(cn),Bytes.toBytes(cellValue));

            try {
                context.write(rowkyeBytes,put);
            } catch (IOException e) {
                e.printStackTrace();
                myLog.error("反正我也不知道是啥错误。");
            } catch (InterruptedException e) {
                e.printStackTrace();
                myLog.error("反正我也不知道是啥错误。");
            }
        }
    }

    public static void main(String[] args){
        String inpaht="/d/bigData/a.txt";
        String outpath="/d/bigData/output";

        Configuration configuration = HBaseConfiguration.create();
        // 如果打成jar包运行的话，这个可以不用指定了
        configuration.set("fs.defaultFS", "hdfs://huabingood:9000");

        Job job = null;

        try {
            job = Job.getInstance(configuration,"jobName");
            job.setJarByClass(BulkLoadJob.class);
            job.setMapperClass(BuloLoadMap.class);

            job.setMapOutputKeyClass(ImmutableBytesWritable.class);
            job.setMapOutputValueClass(Put.class);


            // speculation
            // 推断执行设为false
            job.setSpeculativeExecution(false);
            job.setReduceSpeculativeExecution(false);

            /**
             * 下面的内容跟HBase导数据相关
             */
            // 获取一个HBase表
            Connection connection = null;
            connection = ConnectionFactory.createConnection(configuration);
            Table table = connection.getTable(TableName.valueOf("hfiletable"));

            // 判断hdfs上的输出路径是否存在，存在的话就删掉
            FileSystem fs = FileSystem.get(URI.create("/"),configuration);
            Path path = new Path(outpath);
            if(fs.exists(path)){
                fs.delete(path,true);
            }

            // 设置输入输出文件路径
            FileInputFormat.setInputPaths(job,new Path(inpaht));
            FileOutputFormat.setOutputPath(job,path);

            // 这里不知道为什么需要的是Htable而不是table
            // 看起来好像过时了一样
            HTable hTable = new HTable(configuration,"hfiletable");
            HFileOutputFormat2.configureIncrementalLoad(job, hTable);

            // 如果任务完成后，就开始加载数据
            if(job.waitForCompletion(true)){
                LoadIncrementalHFiles loader = new LoadIncrementalHFiles(configuration);
                loader.doBulkLoad(path,hTable);
            }


        } catch (IOException e) {
            e.printStackTrace();
        }catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}

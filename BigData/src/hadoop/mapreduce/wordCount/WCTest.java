package hadoop.mapreduce.wordCount;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;

import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.log4j.Logger;

import java.io.IOException;

/**
 * 运行MR程序的主类
 * 注意：如果在IDEA中运行在yarn界面是没有任何输出的，只有打成jar包运行在yarn界面才有输出
 *
 * 过程如下：
 *  1.设置configuration参数，准备hadoop环境
 *  2.设置输入输出路径
 *  3.根据设置生成job，供application调用，然后就是设置job运行时的各种参数了：
 *  4.告知yarn运行job，以及输入输出的路径
 *  5.提交job并运行
 *
 * job参数设置
 *  1.告知application jar包的所有文件在哪里。setJarByClass()
 *  2.告知application运行的maerp，reducer分别是jar包中的那个文件。
 *  3.告诉application mapper，reducer数据的key，value分别是什么数据类型
 *
 *
 */
public class WCTest {
    public static void main(String[] args){
        Logger logger = Logger.getLogger("file");
        // 获取配置文件
        Configuration conf = new Configuration();
        // 如果打成jar包运行的话，这个可以不用指定了
        conf.set("fs.defaultFS", "hdfs://huabingood01:9000");

        // 设置文件的输入输出路径
        Path inPath = new Path("/hyw/inPath/test.txt");
        Path outPath = new Path("/hyw/outPath/path05");
        try {
            // 生成job
            Job job = Job.getInstance(conf);

            //告知yarn，jar包在什么地方。这句话的意思是获取WCTest.class所在jar包的路径
            job.setJarByClass(WCTest.class);

            // 告诉系统运行的mapper，reducer类分别是jar包中的哪个文件
            job.setMapperClass(WCMapper.class);
            job.setReducerClass(WCReducer.class);

            // 告知系统maper输出的数据类型
            job.setMapOutputKeyClass(Text.class);
            job.setMapOutputValueClass(IntWritable.class);

            // 告知系统reducer输出的数据类型
            job.setOutputKeyClass(Text.class);
            job.setOutputValueClass(IntWritable.class);

            // 告知yarn运行的job和输出输出的hdfs路径
            FileInputFormat.setInputPaths(job,inPath);
            FileOutputFormat.setOutputPath(job,outPath);

            // 只是提交，并不返回
            //job.submit();
            // 不仅提交，还返回运行状态
            boolean result = job.waitForCompletion(true);
            System.exit(result?0:1);

        } catch (IOException e) {
            e.printStackTrace();
            logger.error(e);
        } catch (ClassNotFoundException e){
            e.printStackTrace();
            logger.error(e);
        } catch (InterruptedException e){
            e.printStackTrace();
            logger.error(e);
        }

    }
}

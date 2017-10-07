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

public class WCTest {
    public static void main(String[] args){
        Logger logger = Logger.getLogger("file");
        Configuration conf = new Configuration();
        conf.set("fs.defaultFS", "hdfs://huabingood01:9000");

        Path inPath = new Path("/hyw/inPath/test.txt");
        Path outPath = new Path("/hyw/outPath/path05");
        try {
            Job job = Job.getInstance(conf);
            job.setJarByClass(WCTest.class);

            job.setMapperClass(WCMapper.class);
            job.setReducerClass(WCReducer.class);

            job.setMapOutputKeyClass(Text.class);
            job.setMapOutputValueClass(IntWritable.class);

            job.setOutputKeyClass(Text.class);
            job.setOutputValueClass(IntWritable.class);

            FileInputFormat.setInputPaths(job,inPath);
            FileOutputFormat.setOutputPath(job,outPath);


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

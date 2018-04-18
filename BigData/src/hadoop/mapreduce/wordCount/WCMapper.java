package hadoop.mapreduce.wordCount;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;

/**
 * MR采用的分总法，先将总体划分为各个小的模块（HDFS已经做好了），然后对各个小模块进行运算
 * 最后将总的运算结果进行汇总。
 *
 * 根据原理，我们推出map阶段的主要过程
 * 1.继承mapper类，注意要写泛型，使用hadoop自己的数据类型，主要是加了序列化
 *  a.加序列化的原因：整个mapreduce阶段的数据传输都是通过KV进行传输的，在接收后，我们希望依旧能解析出KV出来
 * 2.重写map方法（在IDEA中使用快捷键ctrl+o调出快速重写）
 *  a.map方法会抛出异常，注意处理
 *  b.传入的参数共三种：输入的key,输入的value,传给reducer的内容
 *      输入的key:输入的key是文本的行数（第几行）
 *      输入的value:是输入的改行文本的内容。因为是做词频统计，所以这里我们必须将一行的内容根据某中特殊的符号将其划分为一个个单词
 *      传给reducer的内容：通常也应该是KV对，形式应该是：<单词，1><单词，1>这种形式（注意每个单词后面的value都是1）。在传输是，
 *   系统已经改我们封装好了一个数据结构：Context，我们只需往这里面传数据内容即可。
 *      因为一行的单词是很多个，所以我们必须将改行中的所有内容都传给Context才行。
 *   c.需要注意的是，由于我们使用的是序列化后的数据类型，所以在处理是，如果使用到java的数据类型，两者之间必须进行转换
 *   d.读完一行内容并处理后，并不意味着结束，我们需要循环将整个文本都读取完。所幸的是：系统会自动一行行的读取文本并帮我们处理完成。
 * 我们需要做的仅仅是写好处理一行的逻辑。
 *
 * @author huabingood@qq.com
 */
public class WCMapper extends Mapper<LongWritable,Text,Text,IntWritable> {

    @Override
    protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
        // 获取一行的内容
        String line = value.toString();
        // 将改行的内容按空格切分为一个个单词
        String[] words = line.split(" ");
        // 采用<单词，1>的形式，将改行的所有单词都写进传给reducer的数据结构中
        for(String word:words){
            // 注意hadoop数据类型和java数据类型的转换
            Text outKey = new Text(word);
            // 就词频统计而言，这个value值必须是1
            IntWritable outValue = new IntWritable(1);
            context.write(outKey,outValue);
        }
    }
}

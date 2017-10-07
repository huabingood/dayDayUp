package hadoop.mapreduce.wordCount;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;

/**
 * 词频统计中将各个mapper中的数据进行汇总输出
 * 在这里reducer是需要的，然而，并不总是需要的。
 *
 * reducer的过程如下：
 *  1.继承reducer类，要写泛型。分别是入的KV的类型，输出的KV的类型。
 *  2.重写reduce方法，主要抛出异常。
 *  3.对词频进行统计：我们知道map阶段传递过来的值是：<单词，1><单词，1>的形式，我们只需将相同单词的value相加，即可得出该单词的词频。
 *  实际上Context已经对map传输过来的数据做了优化，它输出的格式是：
 *  < 单词1，{1,1,1,1,1,1,1,1} >
 *  < 单词2，{1,1,1,1,1,1} >
 *  这里面，前面的key值是<单词，1><单词，1>这种形式的第一个单词会放到context的key中，然后将后面所有的1,放到一个迭代器中，作为value
 *  4.然后我们将计算的结果：<单词，词频>写到输出的文件中即可。
 *
 *
 * @author huabingood@qq.com
 */
public class WCReducer extends Reducer<Text,IntWritable,Text,IntWritable> {

    @Override
    protected void reduce(Text key, Iterable<IntWritable> values, Context context) throws IOException, InterruptedException {

        int count=0;
        // 遍历迭代器中所有的1，相加得到词频
        for(IntWritable value:values){
            count += value.get();
        }
        // hadoop数据类型和java数据类型的转换
        IntWritable outValue = new IntWritable(count);

        // 输出
        context.write(key,outValue);
    }
}

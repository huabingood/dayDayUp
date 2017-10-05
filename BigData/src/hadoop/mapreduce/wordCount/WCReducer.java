package hadoop.mapreduce.wordCount;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;

public class WCReducer extends Reducer<Text,IntWritable,Text,IntWritable> {

    @Override
    protected void reduce(Text key, Iterable<IntWritable> values, Context context) throws IOException, InterruptedException {

        int count=0;

        for(IntWritable value:values){
            count += value.get();
        }

        IntWritable outValue = new IntWritable(count);

        context.write(key,outValue);
    }
}

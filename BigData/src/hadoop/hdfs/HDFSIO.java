package hadoop.hdfs;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.Iterator;
import java.util.Map;

public class HDFSIO {
    FileSystem fs = null;
    Configuration conf = null;

    @Before
    public void init(){
        conf = new Configuration();
        conf.set("fs.defaultFS","hdfs://huabingood01:9000");

        try {
            fs = FileSystem.get(conf);
        } catch (IOException e) {
            e.printStackTrace();
        }

        /*try {
            fs = FileSystem.get(new URI("hdfs://huabingood01:9000"),conf);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }*/


    }


    @Test
    public void testConfiguration(){
        Iterator<Map.Entry<String,String>> it = conf.iterator();
        if(it.hasNext()){
            Map.Entry<String,String> entry = it.next();
            System.out.println(entry.getKey() +" : " + entry.getValue());
        }
    }


}

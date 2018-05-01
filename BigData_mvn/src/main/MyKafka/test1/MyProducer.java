package test1;


import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.util.Properties;

public class MyProducer {
    public static void main(String[] args){
        String TOPIC = "myKafka";

        Properties properties = new Properties();
        // 键值序列化的方式
        properties.put("key.serializer","org.apache.kafka.common.serialization.StringSerializer");
        properties.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        // request.required.acks,设置发送数据是否需要服务端的反馈(详见笔记)
        properties.put("request.required.acks","1");
        // borker的地址
        properties.put("bootstrap.servers", "huabingood:9092");


        // 创建生产者
        Producer<String,String> producer = new KafkaProducer<String, String>(
                properties
        );

        // 发送信息
        for(int messsageNo = 1;messsageNo<100;messsageNo++){
            String messsageStr = new String("message_"+messsageNo);
            // 发送数据调用producer的send方法
            // 第一个通常是发给那个主题，第二个通常时发送的内容
            producer.send(new ProducerRecord<String, String>(TOPIC,messsageStr));
        }
    }
}

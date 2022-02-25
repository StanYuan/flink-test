package com.flink.test.utils;


import org.apache.kafka.clients.producer.KafkaProducer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Properties;

/**
 * Desc: 往kafka中写数据,可以使用这个main函数进行测试
 * Created by zhisheng on 2019-02-17
 * Blog: http://www.54tianzhisheng.cn/tags/Flink/
 */
public class KafkaUtil {
    public static final String broker_list = "localhost:9092";
    public static final String topic = "iris";  //kafka topic 需要和 flink 程序用同一个 topic
    private static Logger logger = LoggerFactory.getLogger(KafkaUtil.class);

    public static final String[] tradeTypeArray = new String[]{
            "QUERY", "PAY", "REFUND", "REVOKE"
    };

    public static void writeToKafka() {
        Properties props = new Properties();
        props.put("bootstrap.servers", broker_list);
        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        KafkaProducer producer = new KafkaProducer<String, String>(props);

//        for (int i = 1; i <= 10; i++) {
//            int numRandom = (int) (Math.random() * 9 + 1);
//            int tradeRandom = (int) (Math.random() * 3 + 1);
//            MerchantTradeEvent merchantTrade = new MerchantTradeEvent("merNo_000" + numRandom, tradeTypeArray[tradeRandom], System.currentTimeMillis());
//            ProducerRecord record = new ProducerRecord<String, String>(topic, null, null, GsonUtil.toJson(merchantTrade));
//            producer.send(record);
//            logger.info("发送数据: " + GsonUtil.toJson(merchantTrade));
//        }
        producer.flush();
    }

}

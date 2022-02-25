package com.flink.test.app;

import com.alibaba.fastjson.JSONObject;
import com.flink.test.entity.SMData;
import com.flink.test.sink.MerchantAccSink;
import com.flink.test.utils.DateUtil;
import org.apache.flink.api.common.RuntimeExecutionMode;
import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.api.common.functions.ReduceFunction;
import org.apache.flink.api.common.serialization.SimpleStringSchema;
import org.apache.flink.api.java.functions.KeySelector;
import org.apache.flink.api.java.tuple.Tuple3;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.datastream.KeyedStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaConsumer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Properties;

/**
 * @version v1.0
 * @Description: 历史规则统计Flink算子
 * @Author: dong.yuan
 * @Date: 2022/2/22 11:30
 */
public class HistoryStatisticsJob {
    private static Logger logger = LoggerFactory.getLogger(RealTimeStatisticsJob.class);

    public static void main(String[] args) throws Exception {
        logger.info("历史数据统计job开始.....");
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        env.setRuntimeMode(RuntimeExecutionMode.AUTOMATIC);
        //设置flink-kafka-connector源配置
        Properties props = new Properties();
        props.put("bootstrap.servers", "localhost:9092");
        props.put("zookeeper.connect", "localhost:2181");
        props.put("group.id", "kafka-stream-in-group");
        //每5秒定时检测kafka分区变动
        props.put("flink.partition-discovery.interval-millis", "5000");
        props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        props.put("auto.offset.reset", "latest");
        props.put("auto.commit.interval.ms", "2000");

        DataStream<Tuple3<String, String, String>> tuple3DataStream = env.addSource(new FlinkKafkaConsumer<>("kafka-stream-in", new SimpleStringSchema(), props))
                .map(new MapFunction<String, Tuple3<String, String, String>>() {
                    @Override
                    public Tuple3<String, String, String> map(String stream) throws Exception {
                        logger.info("steam come in:{}", stream);
                        SMData smData = JSONObject.parseObject(stream, SMData.class);
                        Date tradeDate = DateUtil.getDateTimeFormat(smData.getTradeTime());
                        return Tuple3.of(smData.getMerNo(), DateUtil.getDateFormat(tradeDate), smData.getTradeAmt());
                    }
                });
        for(int i =0; i< 2; i++){
            KeyedStream<Tuple3<String, String, String>, String> keyedStream = tuple3DataStream.keyBy(new KeySelector<Tuple3<String, String, String>, String>() {

                @Override
                public String getKey(Tuple3<String, String, String> tuple3) throws Exception {
                    // flow_key和flow_date作为key
                    return tuple3.f0 + ":" + tuple3.f1;
                }
            });

            DataStream<Tuple3<String, String, String>> reduceStream = keyedStream.reduce(new ReduceFunction<Tuple3<String, String, String>>() {
                @Override
                public Tuple3<String, String, String> reduce(Tuple3<String, String, String> t1, Tuple3<String, String, String> t2) throws Exception {
                    return Tuple3.of(t1.f0, t1.f1, new BigDecimal(t1.f2).add(new BigDecimal(t2.f2)).toString());
                }
            });
            //reduceStream.addSink(new MerchantAccSink());
            reduceStream.print();
        }
//        reduceStream.print();
        env.execute("历史数据分组统计");
    }


}



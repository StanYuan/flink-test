package com.flink.test.stradgy.impl;

import com.alibaba.fastjson.JSONObject;
import com.flink.test.entity.StreamSourceType;
import com.flink.test.stradgy.StreamSourceProcessor;
import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.api.common.serialization.SimpleStringSchema;
import org.apache.flink.api.java.utils.ParameterTool;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaConsumer;
import org.apache.flink.util.Collector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @version v1.0
 * @Description:
 * @Author: dong.yuan
 * @Date: 2022/2/24 15:45
 */
public class KafkaSourceProcessor implements StreamSourceProcessor<JSONObject> {

    private Logger logger = LoggerFactory.getLogger(KafkaSourceProcessor.class);

    @Override
    public DataStream<JSONObject> processSource(StreamExecutionEnvironment env, ParameterTool parameterTool) {
        DataStream<JSONObject> sourceStream = env.addSource(new FlinkKafkaConsumer<>(parameterTool.get("kafka.source.topic"), new SimpleStringSchema(), parameterTool.getProperties()))
                //转换为SMData对象
                .map(new MapFunction<String, JSONObject>() {
                    @Override
                    public JSONObject map(String stream) throws Exception {
                        return JSONObject.parseObject(stream);
                    }
                });
        return sourceStream;
    }

    @Override
    public StreamSourceType applySource() {
        return StreamSourceType.KAFKA;
    }
}

package com.flink.test.stradgy.impl;

import com.alibaba.fastjson.JSONObject;
import com.flink.test.entity.*;
import com.flink.test.stradgy.StreamSourceProcessor;
import org.apache.commons.beanutils.BeanUtils;
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

import java.util.List;

/**
 * @version v1.0
 * @Description:
 * @Author: dong.yuan
 * @Date: 2022/2/24 15:45
 */
//public class KafkaSourceProcessor implements StreamSourceProcessor<T> {
//
//    @Override
//    public DataStream<T> processSource(StreamExecutionEnvironment env, ParameterTool parameterTool) {
//        return env.addSource(new FlinkKafkaConsumer<>(parameterTool.get("kafka.source.topic"), new SimpleStringSchema(), parameterTool.getProperties()))
//                //转换为SMData对象
//                .flatMap(new FlatMapFunction<String, T>() {
//                    @Override
//                    public void flatMap(String source, Collector<T> collector) throws Exception {
//                        StatisticsCalReqInfo statisticsCalReqInfo = JSONObject.parseObject(source, StatisticsCalReqInfo.class);
//                        SMData smData = JSONObject.parseObject(statisticsCalReqInfo.getData(), SMData.class);
//                        collector.collect(smData);
//                    }
//                });
//    }

//}

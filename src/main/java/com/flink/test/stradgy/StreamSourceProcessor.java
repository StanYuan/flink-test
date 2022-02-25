package com.flink.test.stradgy;

import com.flink.test.entity.StreamSourceType;
import org.apache.flink.api.java.utils.ParameterTool;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

import java.util.Properties;

/**
 * @version v1.0
 * @Description: 源处理器
 * @Author: dong.yuan
 * @Date: 2022/2/24 15:40
 */
public interface StreamSourceProcessor<T> {

    DataStream<T> processSource(StreamExecutionEnvironment env, ParameterTool parameterTool);

    StreamSourceType applySource();

}

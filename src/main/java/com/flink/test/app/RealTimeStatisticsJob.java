package com.flink.test.app;

import com.alibaba.fastjson.JSONObject;
import com.flink.test.entity.FlowStatistics;
import com.flink.test.entity.SMData;
import com.flink.test.entity.StatisticsCalObj;
import com.flink.test.entity.StatisticsCalReqInfo;
import com.flink.test.stradgy.StreamSourceProcessor;
import com.flink.test.stradgy.impl.StreamFunctionRoute;
import com.flink.test.utils.DateUtil;
import org.apache.flink.api.common.ExecutionConfig;
import org.apache.flink.api.common.RuntimeExecutionMode;
import org.apache.flink.api.common.functions.AggregateFunction;
import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.api.java.utils.ParameterTool;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.datastream.KeyedStream;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.windowing.WindowFunction;
import org.apache.flink.streaming.api.windowing.windows.TimeWindow;
import org.apache.flink.util.Collector;
import org.apache.http.util.Asserts;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;

/**
 * @version v1.0
 * @Description:
 * @Author: dong.yuan
 * @Date: 2022/2/21 18:27
 */
public class RealTimeStatisticsJob {

    private static Logger logger = LoggerFactory.getLogger(RealTimeStatisticsJob.class);

    public static void main(String[] args) throws Exception {
        logger.info("实时数据统计job开始.....");
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        env.setRuntimeMode(RuntimeExecutionMode.AUTOMATIC);

        //获取每个作业进程的配置，默认都在classpath下的app.properties
        String path = ParameterTool.fromSystemProperties().get("filePath", "app.properties");
        ParameterTool parameterTool = ParameterTool.fromPropertiesFile(ClassLoader.getSystemResourceAsStream(path));
        env.getConfig().setGlobalJobParameters(parameterTool);
        ExecutionConfig.GlobalJobParameters globalJobParameters = env.getConfig().getGlobalJobParameters();
        Map<String, String> propertiesMap = globalJobParameters.toMap();

        //配置flink-kafka-connector源
        Asserts.notNull(propertiesMap.get("stream.source.type"), "stream.source.type");
        StreamSourceProcessor<StatisticsCalObj> sourceProcessor = StreamFunctionRoute.sourceProcessor(propertiesMap.get("stream.source.type"));

        //flatmap拆分为多个流
        DataStream<StatisticsCalObj> sourceStream = sourceProcessor.processSource(env, parameterTool);

        KeyedStream<StatisticsCalObj, String> keyedStream = sourceStream.keyBy(StatisticsCalObj::getCondition);



//                设置流数据的水印，用于解决数据流的延时和乱序问题，此处设置最大延时时间为2秒
//                .assignTimestampsAndWatermarks(WatermarkStrategy.<SMData>forBoundedOutOfOrderness(Duration.ofSeconds(1)).withTimestampAssigner(((smData, timestamp) -> smData.getEventTime())));

//        按商户号分组
//        SMStreamWithWaterMark.keyBy(SMData::getMerNo).window(TumblingEventTimeWindows.of(Time.seconds(3)))
//                .aggregate(new SMCountAggFunction(), new smAggResultFunction())
//                        .print();
                //.addSink(new SinkToMySQL());

        env.execute("risk rule cal execute");
    }

    //订单数据聚合算法接口
    private static class SMCountAggFunction implements AggregateFunction<SMData, String, String> {

        @Override
        public String createAccumulator() {
            return "0";
        }

        @Override
        public String add(SMData smData, String acc) {
            return new BigDecimal(smData.getTradeAmt()).add(new BigDecimal(acc)).toString();
        }

        @Override
        public String getResult(String acc) {
            return acc;
        }

        @Override
        public String merge(String acc1, String acc2) {
            return new BigDecimal(acc1).add(new BigDecimal(acc2)).toString();
        }
    }

    //聚合结果输出格式接口
    private static class smAggResultFunction implements WindowFunction<String, String, String, TimeWindow> {

        @Override
        public void apply(String key, TimeWindow timeWindow, Iterable<String> aggregateResult, Collector<String> collector) throws Exception {
            FlowStatistics flowStatistics = new FlowStatistics();
            flowStatistics.setFlowKey(key);
            flowStatistics.setStatisticsValue(aggregateResult.iterator().next());
            flowStatistics.setRuleCode("SM_LIMIT_DAY_AMT");
            long timeEnd = timeWindow.getEnd();
            Date statisticsDate = new Date(timeEnd);
            flowStatistics.setFlowDate(DateUtil.getDateFormat(statisticsDate, "yyyy-MM-dd"));
            collector.collect(JSONObject.toJSONString(flowStatistics));
        }
    }
}

package com.flink.test.entity;

import lombok.Data;

/**
 * @version v1.0
 * @Description:
 * @Author: dong.yuan
 * @Date: 2022/2/25 16:09
 */
@Data
public class StatisticsCalMeta {

    //规则编码
    private String ruleCode;

    //统计条件
    private String condition;

    //统计字段
    private String field;

    //统计方式 累计:SUM 计数：COUNT
    private String method;

    //统计频率 年，月，日，小时，分钟，秒，历史
    private String frequency;

}

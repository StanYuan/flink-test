package com.flink.test.entity;

import lombok.Data;

/**
 * @version v1.0
 * @Description:
 * @Author: dong.yuan
 * @Date: 2022/2/25 17:21
 */
@Data
public class StatisticsCalObj {

    private String data;

    //统计条件
    private String condition;

    //统计字段
    private String field;

    //统计方式 累计:SUM 计数：COUNT
    private String method;

    //统计频率 年，月，日，小时，分钟，秒，历史
    private String frequency;
}

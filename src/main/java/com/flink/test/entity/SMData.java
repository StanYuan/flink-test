package com.flink.test.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * @version v1.0
 * @Description:
 * @Author: dong.yuan
 * @Date: 2022/2/21 15:28
 */
@Data
public class SMData implements Serializable {

    private String tradeAmt;

    private String agentNo;

    private String merTypeName;

    private String merOrderNo;

    private String openId;

    private String companyName;

    private String merName;

    private String orderFee;

    private String tradeTime;

    private String activityCode;

    private String acctNo;

    private String merNo;

    private String phoneNum;

    private String tradeStatus;

    private String merMcc;

    private String merType;

    private String merLoginNo;

    private String payWayCode;

    private String channelCode;

    private Long eventTime;

    private String ruleCode;

    //统计条件
    private String condition;

    //统计字段
    private String field;

    //统计方式 累计:SUM 计数：COUNT
    private String method;

    //统计频率 年，月，日，小时，分钟，秒，历史
    private String frequency;

    private String flowKey;

    private String flowDate;

    //统计结果
    private String fieldValue;

    private Integer cnt = 1;

//    public SMData(String ruleCode, String condition, String flowDate, String statisticsResult) {
//        this.ruleCode = ruleCode;
//        this.condition = condition;
//        this.flowDate = flowDate;
//        this.statisticsResult = statisticsResult;
//    }


}

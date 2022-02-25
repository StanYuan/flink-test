package com.flink.test.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

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

}

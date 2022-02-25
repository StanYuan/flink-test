package com.flink.test.entity;

import lombok.Data;

/**
 * @version v1.0
 * @Description:
 * @Author: dong.yuan
 * @Date: 2022/2/21 15:53
 */
@Data
public class FlowStatistics {

    private String flowKey;

    private String flowDate;

    private String ruleCode;

    private String statisticsValue;

}

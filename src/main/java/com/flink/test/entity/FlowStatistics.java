package com.flink.test.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @version v1.0
 * @Description:
 * @Author: dong.yuan
 * @Date: 2022/2/21 15:53
 */
@Data
@AllArgsConstructor
public class FlowStatistics {

    private String statisticsKey;

    private String method;

    private String fieldValue;

}

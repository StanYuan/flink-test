package com.flink.test.entity;

import lombok.Data;
import java.util.List;

/**
 * @version v1.0
 * @Description:
 * @Author: dong.yuan
 * @Date: 2022/2/25 16:08
 */
@Data
public class StatisticsCalReqInfo {
    //请求数据
    private String data;

    //流量统计规则
    private List<StatisticsCalMeta> metaList;
}

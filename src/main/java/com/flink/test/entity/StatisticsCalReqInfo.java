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

    private String data;

    private List<StatisticsCalMeta> metaList;
}

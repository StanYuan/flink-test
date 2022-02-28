package com.flink.test.stradgy.impl;

import com.flink.test.stradgy.StreamReduceProcessor;

import java.math.BigDecimal;

/**
 * @Description
 * @Author Stan.Yuan
 * @Date 2022/2/27 17:05
 * @Version 1.0
 */
public class CountReduceProcessor implements StreamReduceProcessor {
    @Override
    public BigDecimal reduce(BigDecimal obj1, BigDecimal obj2) {
        return BigDecimal.ONE;
    }
}

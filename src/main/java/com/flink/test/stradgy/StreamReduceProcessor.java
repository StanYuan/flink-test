package com.flink.test.stradgy;

import java.math.BigDecimal;

/**
 * @Description
 * @Author Stan.Yuan
 * @Date 2022/2/27 17:02
 * @Version 1.0
 */
public interface StreamReduceProcessor {

    BigDecimal reduce(BigDecimal obj1, BigDecimal obj2);
}

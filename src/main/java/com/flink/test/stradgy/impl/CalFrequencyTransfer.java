package com.flink.test.stradgy.impl;

import com.flink.test.entity.StatisticsFrequency;
import com.flink.test.utils.DateUtil;

import java.util.Date;

/**
 * @Description
 * @Author Stan.Yuan
 * @Date 2022/2/27 20:21
 * @Version 1.0
 */
public class CalFrequencyTransfer{

    public static String transferFrequency(String frequency, Date tradeTime) {
        if(frequency.equalsIgnoreCase(StatisticsFrequency.YEAR.toString())){
            return DateUtil.getDateFormat(tradeTime, "yyyy");
        }
        if(frequency.equalsIgnoreCase(StatisticsFrequency.MONTH.toString())){
            return DateUtil.getDateFormat(tradeTime, "yyyyMM");
        }
        if(frequency.equalsIgnoreCase(StatisticsFrequency.DAY.toString())){
            return DateUtil.getDateFormat(tradeTime, "yyyyMMdd");
        }
        if(frequency.equalsIgnoreCase(StatisticsFrequency.HOUR.toString())){
            return DateUtil.getDateFormat(tradeTime, "yyyyMMddHH");
        }
        if(frequency.equalsIgnoreCase(StatisticsFrequency.MIN.toString())){
            return DateUtil.getDateFormat(tradeTime, "yyyyMMddHHmm");
        }
        if(frequency.equalsIgnoreCase(StatisticsFrequency.NONE.toString())){
            return "";
        }
        return null;
    }
}

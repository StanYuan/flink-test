package com.flink.test.stradgy.impl;

import com.flink.test.stradgy.StreamSourceProcessor;

/**
 * @version v1.0
 * @Description:
 * @Author: dong.yuan
 * @Date: 2022/2/24 15:57
 */
public class StreamFunctionRoute {

    public static StreamSourceProcessor sourceProcessor(String streamSourceType){
        switch (streamSourceType){
            case "KAFKA": {
                return new KafkaSourceProcessor();
            }
            case "FILE": {

            }
            case "SOCKET":{

            }
            default: {

            }
        }
        return null;
    }

}

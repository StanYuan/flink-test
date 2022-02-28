import com.alibaba.fastjson.JSON;
import com.flink.test.entity.SMData;

/**
 * @version v1.0
 * @Description:
 * @Author: dong.yuan
 * @Date: 2022/2/28 11:19
 */
public class AppTest {

    public static void main(String[] args) {
        String json = "{\"tradeAmt\":0.01,\"agentNo\":\"zl@cs.sh.cn\",\"merTypeName\":\"企业商户\",\"merOrderNo\":\"1642584308633\",\"openId\":\"2088002443460458\",\"companyName\":\"测试专用商户\",\"merName\":\"测试专用商户\",\"orderFee\":0.18,\"tradeTime\":\"2022-01-19 17:25:09\",\"acctNo\":\"b26903420620759040\",\"merNo\":\"b26903420620759040\",\"PhoneNum\":\"13564500053\",\"tradeStatus\":\"1\",\"merMcc\":\"5511\",\"merType\":\"00\",\"merLoginNo\":\"b26903420620759040\",\"payWayCode\":\"pay-zfb-native\",\"channelCode\":\"YL-ZFB-SM01\"}";
        SMData smData = JSON.parseObject(json, SMData.class);
//        JSONObject jsObj = JSON.parseObject(json);
        System.out.println(smData);
    }
}

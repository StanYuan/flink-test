package com.flink.test.sink;

import com.flink.test.entity.SMData;
import com.flink.test.utils.MD5Util;
import lombok.extern.slf4j.Slf4j;
import org.apache.flink.configuration.Configuration;
import org.apache.flink.streaming.api.functions.sink.RichSinkFunction;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * @version v1.0
 * @Description:
 * @Author: dong.yuan
 * @Date: 2022/2/22 14:16
 */
@Slf4j
public class MerchantAccSink extends RichSinkFunction<SMData> {

    private PreparedStatement insertPs;

    private PreparedStatement selectPs;

    private PreparedStatement updatePs;

    private Connection connection;

    @Override
    public void open(Configuration parameters) throws Exception {
        super.open(parameters);
        connection = getConnection();
        String selectSql = "select * from flow_statistics where flow_no = ?";
        String insertSql = "insert into flow_statistics(flow_no, flow_key, flow_date, rule_code, statistics_value) values(?, ?, ?, ?, ?);";
        String updateSql = "update flow_statistics set statistics_value = ? where flow_no = ?";
        if (connection != null) {
            selectPs = this.connection.prepareStatement(selectSql);
            insertPs = this.connection.prepareStatement(insertSql);
            updatePs = this.connection.prepareStatement(updateSql);
        }
    }

    @Override
    public void invoke(SMData smData, Context context) throws Exception {
        String flowNo = MD5Util.getMD5(smData.getFlowKey() + smData.getFlowDate() + smData.getRuleCode()).toUpperCase();
        selectPs.setString(1, flowNo);
        ResultSet resultSet = selectPs.executeQuery();
        if (!resultSet.first()) {
            log.info("======= 数据库未查到记录! flow_no:{}, flowDate:{}", flowNo, smData.getFlowDate());
            insertPs.setString(1, flowNo);
            insertPs.setString(2, smData.getFlowKey());
            insertPs.setString(3, smData.getFlowDate());
            insertPs.setString(4, smData.getRuleCode());
            insertPs.setString(5, smData.getStatisticsResult());
            insertPs.executeUpdate();
        } else {
            String statistics_value = resultSet.getString("statistics_value");
            log.info("======= 数据库查到记录! flow_no:{}, flowDate:{}, statisticsValue:{}", flowNo, smData.getFlowDate(), statistics_value);
            updatePs.setString(1, smData.getStatisticsResult());
            updatePs.setString(2, flowNo);
            updatePs.executeUpdate();
        }
    }

    @Override
    public void close() throws Exception {
        super.close();
    }

    private Connection getConnection() {
        Connection con = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            //注意，替换成自己本地的 mysql 数据库地址和用户名、密码
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/flink_demo?useSSL=false&useUnicode=true&characterEncoding=utf8&allowMultiQueries=true&serverTimezone=GMT%2B8", "root", "root");
        } catch (Exception e) {
            log.error("-----------mysql get connection has exception", e);
        }
        return con;
    }
}

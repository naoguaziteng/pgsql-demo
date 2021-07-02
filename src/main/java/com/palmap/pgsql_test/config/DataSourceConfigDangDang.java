/*
package com.palmap.pgsql_test.config;

import com.dangdang.ddframe.rdb.sharding.api.MasterSlaveDataSourceFactory;
import com.dangdang.ddframe.rdb.sharding.api.strategy.slave.MasterSlaveLoadBalanceStrategyType;
import com.dangdang.ddframe.rdb.sharding.keygen.DefaultKeyGenerator;
import com.dangdang.ddframe.rdb.sharding.keygen.KeyGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

*/
/**
 * @author hawk
 * @date 2021/7/1
 * @desc dangdang
 **//*

@Configuration
public class DataSourceConfig {
    @Autowired
    private DbMasterConfig masterConfig;

    @Autowired
    private DbSlave1Config slave1Config;

    @Autowired
    private DbSlave2Config slave2Config;

    @Bean
    public DataSource getDataSource() throws SQLException {
        return buildDataSource();
    }

    private DataSource buildDataSource() throws SQLException {
        //设置从库数据源集合
        Map<String, DataSource> slaveDataSourceMap = new HashMap<>();
        slaveDataSourceMap.put("slave1", slave1Config.slave1DataSource());
        slaveDataSourceMap.put("slave2", slave2Config.slave2DataSource());

        //获取数据源对象
        return MasterSlaveDataSourceFactory.createDataSource("masterSlave", "master"
                , masterConfig.masterDataSource(), slaveDataSourceMap, MasterSlaveLoadBalanceStrategyType.getDefaultStrategyType());
    }


    @Bean
    public KeyGenerator keyGenerator() {
        return new DefaultKeyGenerator();
    }
}
*/

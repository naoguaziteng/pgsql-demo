package com.palmap.pgsql_test.config;

import com.alibaba.druid.pool.DruidDataSource;
import io.shardingjdbc.core.api.MasterSlaveDataSourceFactory;
import io.shardingjdbc.core.api.config.MasterSlaveRuleConfiguration;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author hawk
 * @date 2021/7/1
 * @desc io.shardingjdbc.core 读写分离配置
 **/
@Configuration
@MapperScan(basePackages = "com.palmap.pgsql_test.mapper",
        sqlSessionTemplateRef = "sqlSessionTemplate")
public class DataSourceConfig {

    @Bean
    @ConfigurationProperties(prefix = "sharding.jdbc.datasource.master")
    public DataSource masterDataSource() {
        return new DruidDataSource();
    }

    @Bean
    @ConfigurationProperties(prefix = "sharding.jdbc.datasource.slave1")
    public DataSource slave1DataSource() {
        return new DruidDataSource();
    }

    @Bean
    @ConfigurationProperties(prefix = "sharding.jdbc.datasource.slave2")
    public DataSource slave2DataSource() {
        return new DruidDataSource();
    }

    @Bean("dataSource")
    public DataSource getMasterSlaveDataSource(@Qualifier("masterDataSource") DataSource master,
                                               @Qualifier("slave1DataSource") DataSource slave1,
                                               @Qualifier("slave2DataSource") DataSource slave2) throws SQLException {
        Map<String, DataSource> result = new HashMap<>();
        result.put("master", master);
        result.put("slave1", slave1);
        result.put("slave2", slave2);
        MasterSlaveRuleConfiguration masterSlaveRuleConfig = new MasterSlaveRuleConfiguration();
        masterSlaveRuleConfig.setName("master_slave");
        masterSlaveRuleConfig.setMasterDataSourceName("master");
        masterSlaveRuleConfig.setSlaveDataSourceNames(Arrays.asList("slave1", "slave2"));
        return MasterSlaveDataSourceFactory.createDataSource(result, masterSlaveRuleConfig, new LinkedHashMap<>());
    }

    @Bean
    public SqlSessionFactory sqlSessionFactory(@Qualifier("dataSource") DataSource dataSource) throws Exception {
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(dataSource);
        bean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath*:mapper/**/*.xml"));
        return bean.getObject();
    }

    @Bean
    public DataSourceTransactionManager transactionManager(@Qualifier("dataSource") DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

    @Bean
    public SqlSessionTemplate sqlSessionTemplate(@Qualifier("sqlSessionFactory") SqlSessionFactory sqlSessionFactory) throws Exception {
        return new SqlSessionTemplate(sqlSessionFactory);
    }
}

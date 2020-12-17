package com.kd.xxhyf.config;

import com.alibaba.druid.filter.Filter;
import com.alibaba.druid.pool.DruidDataSource;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.List;

@Configuration
@Data
public class DataBaseConfig {

    @Value("${jdbc.driverClass}")
    private String driverClassName;
    @Value("${jdbc.url}")
    private String url;
    @Value("${jdbc.username}")
    private String username;
    @Value("${jdbc.password}")
    private String password;
    @Value("${jdbc.initialSize}")
    private int initialSize;
    @Value("${jdbc.minIdle}")
    private int minIdle;
    @Value("${jdbc.maxActive}")
    private int maxActive;
    @Value("${jdbc.maxWait}")
    private long maxWait;
    @Value("${jdbc.timeBetweenEvictionRunsMillis}")
    private long timeBetweenEvictionRunsMillis;
    @Value("${jdbc.minEvictableIdleTimeMillis}")
    private long minEvictableIdleTimeMillis;
    @Value("${jdbc.testWhileIdle}")
    private boolean testWhileIdle;
    @Value("${jdbc.testOnBorrow}")
    private boolean testOnBorrow;
    @Value("${jdbc.testOnReturn}")
    private boolean testOnReturn;
    @Value("${jdbc.poolPreparedStatements}")
    private boolean poolPreparedStatements;
    @Value("${jdbc.maxPoolPreparedStatementPerConnectionSize}")
    private int maxPoolPreparedStatementPerConnectionSize;
    @Value("${jdbc.validationQuery}")
    private String validationQuery;
    @Value("${jdbc.filters}")
    private String filters;
    //@Value("${jdbc.proxyFilters}")
    private List<Filter> proxyFilters;


    @Bean("dataSource")
    public DruidDataSource getDataSource() throws SQLException {
        DruidDataSource druidDataSource = new DruidDataSource();
        druidDataSource.setDriverClassName(driverClassName);
        druidDataSource.setUrl(url);
        druidDataSource.setUsername(username);
        druidDataSource.setPassword(password);
        druidDataSource.setInitialSize(initialSize);
        druidDataSource.setMinIdle(minIdle);
        druidDataSource.setMaxActive(maxActive);
        druidDataSource.setMaxWait(maxWait);
        druidDataSource.setTimeBetweenEvictionRunsMillis(timeBetweenEvictionRunsMillis);
        druidDataSource.setMinEvictableIdleTimeMillis(minEvictableIdleTimeMillis);
        druidDataSource.setTestWhileIdle(testWhileIdle);
        druidDataSource.setTestOnBorrow(testOnBorrow);
        druidDataSource.setTestOnReturn(testOnReturn);
        druidDataSource.setPoolPreparedStatements(poolPreparedStatements);
        druidDataSource.setMaxPoolPreparedStatementPerConnectionSize(maxPoolPreparedStatementPerConnectionSize);
        druidDataSource.setValidationQuery(validationQuery);
        druidDataSource.setFilters(filters);
        //druidDataSource.setProxyFilters(proxyFilters);
        return druidDataSource;
    }

    @Bean
    public JdbcTemplate getJdbcTemplate(DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }
}

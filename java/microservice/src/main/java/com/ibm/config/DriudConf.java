package com.ibm.config;

import com.alibaba.druid.pool.DruidDataSource;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import javax.sql.DataSource;

/**
 * @Configuration用于定义配置类，可替换xml配置文件，被注解的类内部包含有一个或多个被@Bean注解的方法 相当于把当前类变成了xml
 */
@Configuration
public class DriudConf {

    /**
     * 配置基本属性
     * @return
     */
    @ConfigurationProperties(prefix = "spring.datasource")
    @Bean
    // @Scope 指定单例，多例
    public DataSource getSource() {
        return new DruidDataSource();
    }

    /**
     * 配置监控
     */

}

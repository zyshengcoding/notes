package com.ibm.config;

import org.mybatis.spring.boot.autoconfigure.ConfigurationCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * mybatis的配置类
 */
@Configuration
public class MybatisConf {
    /**
     * org.mybatis.spring.boot.autoconfigure.MybatisAutoConfiguration#configurationCustomizers
     */
    @Bean
    public ConfigurationCustomizer getConfigurationCustomizer() {
        return new ConfigurationCustomizer() {
            @Override
            public void customize(org.apache.ibatis.session.Configuration configuration) {
                configuration.setMapUnderscoreToCamelCase(true);  //开启驼峰规则
            }
        };
    }
}

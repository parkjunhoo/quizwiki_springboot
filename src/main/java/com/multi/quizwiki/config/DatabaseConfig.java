package com.multi.quizwiki.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import util.FileUtils;

@PropertySource("classpath:/application.properties")
@Configuration
public class DatabaseConfig {
	

    @Bean
    @ConfigurationProperties(prefix = "mybatis.configuration")
    public org.apache.ibatis.session.Configuration mybatisConfig() {
        return new org.apache.ibatis.session.Configuration();
    }
    
    @Bean
    public FileUtils fileUtils() {
        return new FileUtils();
    }
}

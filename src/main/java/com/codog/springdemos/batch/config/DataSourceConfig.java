package com.codog.springdemos.batch.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;

@Configuration
public class DataSourceConfig {

    @Bean("codogDb")
    @ConfigurationProperties(prefix = "spring.datasource.codog")
    @Primary
    public DataSource dataSource() {
        return DataSourceBuilder.create().build();
    }
}

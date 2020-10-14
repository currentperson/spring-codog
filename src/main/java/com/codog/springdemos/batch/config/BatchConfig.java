package com.codog.springdemos.batch.config;

import org.springframework.batch.core.configuration.annotation.DefaultBatchConfigurer;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

/**
 * @author : wangwenhan
 * @since : 2020/10/14
 */
@Configuration
@EnableBatchProcessing
public class BatchConfig
    extends DefaultBatchConfigurer {

    @Override
    public void setDataSource(DataSource dataSource) {
    }
}

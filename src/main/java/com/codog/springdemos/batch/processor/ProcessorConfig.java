package com.codog.springdemos.batch.processor;

import com.codog.springdemos.batch.bean.Girl;

import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.support.CompositeItemProcessor;
import org.springframework.batch.item.validator.BeanValidatingItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

/**
 * @author : wangwenhan
 * @since : 2020/11/21
 */
@Configuration
public class ProcessorConfig {

    @Autowired
    private QingGirlProcessor qingGirlProcessor;

    @Bean
    public BeanValidatingItemProcessor<Girl> girlBeanValidatingItemProcessor() throws Exception {
        BeanValidatingItemProcessor<Girl> validator = new BeanValidatingItemProcessor<>();
        validator.setFilter(true);
        validator.afterPropertiesSet();
        return validator;
    }

    @Bean
    public ItemProcessor<Girl, String> girlStringItemProcessor() throws Exception {
        List<ItemProcessor> list = new ArrayList<>();
        list.add(girlBeanValidatingItemProcessor());
        list.add(qingGirlProcessor);
        CompositeItemProcessor compositeItemProcessor =
            new CompositeItemProcessor<>();
        compositeItemProcessor.setDelegates(list);
        compositeItemProcessor.afterPropertiesSet();

        return compositeItemProcessor;
    }
}

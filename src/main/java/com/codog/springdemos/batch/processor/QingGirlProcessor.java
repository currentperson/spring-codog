package com.codog.springdemos.batch.processor;

import com.codog.springdemos.batch.bean.Girl;

import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

/**
 * @author : wangwenhan
 * @since : 2020/10/13
 */
@Component
public class QingGirlProcessor implements ItemProcessor<Girl, String> {

    @Override
    public String process(Girl girl) throws Exception {
        return girl.getName() + "说被摸了PP";
    }
}

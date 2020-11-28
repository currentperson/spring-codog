package com.codog.springdemos.batch.processor;

import com.codog.springdemos.batch.bean.Boy;

import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

/**
 * @author : wangwenhan
 * @since : 2020/11/28
 */
@Component
public class BoyProcessor implements ItemProcessor<Boy, String> {

    @Override
    public String process(Boy boy) throws Exception {
        return (boy.getAge() < 5) ? "混元太急拳法伺候" : "年轻人不讲武德, 我大意了没有闪";
    }
}

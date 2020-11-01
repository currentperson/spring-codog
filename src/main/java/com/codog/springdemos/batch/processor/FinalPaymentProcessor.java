package com.codog.springdemos.batch.processor;

import com.codog.springdemos.batch.bean.Commodity;

import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

/**
 * @author : wangwenhan
 * @since : 2020/11/1
 */
@Component
public class FinalPaymentProcessor implements ItemProcessor<Commodity, String> {

    @Override
    public String process(Commodity commodity) throws Exception {
        return "商品" + commodity.getName() + "付尾款了" + (commodity.getBuyerCount() * 100);
    }
}

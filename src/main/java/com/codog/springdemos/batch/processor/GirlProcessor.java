package com.codog.springdemos.batch.processor;

import com.codog.springdemos.batch.bean.Commodity;
import com.codog.springdemos.batch.bean.Girl;
import com.codog.springdemos.batch.bean.GoodsOrder;

import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

/**
 * @author : wangwenhan
 * @since : 2020/10/13
 */
@Component
public class GirlProcessor implements ItemProcessor<Commodity, GoodsOrder> {

    @Override
    public GoodsOrder process(Commodity commodity) throws Exception {
        System.out.println(commodity);
        final ArrayList<Girl> girlList = new ArrayList<>();
        for (int i = 0; i < commodity.getBuyerCount(); i++) {
            girlList.add(new Girl("girl" + i));
        }
        return new GoodsOrder(commodity, girlList);
    }
}

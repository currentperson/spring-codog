package com.codog.springdemos.batch.writer;

import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author : wangwenhan
 * @since : 2020/10/13
 */
@Component
public class FubaoWriter implements ItemWriter<String> {

    @Override
    public void write(List<? extends String> list) throws Exception {
        list.stream().forEach(item -> {
            if (item.contains("大文娱")) {
                throw new RuntimeException("异常异常");
            }
            System.out.println(item);
        });
    }
}

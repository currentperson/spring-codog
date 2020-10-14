package com.codog.springdemos.batch.writer;

import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author : wangwenhan
 * @since : 2020/10/13
 */
@Component
public class PrintWriter implements ItemWriter<Object> {

    @Override
    public void write(List<?> list) throws Exception {
        list.stream().forEach(System.out::println);
    }
}

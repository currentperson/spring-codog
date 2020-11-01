package com.codog.springdemos.batch.reader;


import lombok.Data;
import lombok.NoArgsConstructor;

import org.springframework.batch.item.ItemReader;

import java.util.List;

/**
 * @author : wangwenhan
 * @since : 2020/10/13
 */
@Data
@NoArgsConstructor
public class ListReader<T> implements ItemReader<T> {

    private List<T> sourceList;
    private int index = 0;

    public ListReader(List<T> sourceList) {
        this.sourceList = sourceList;
    }

    @Override
    public T read() throws Exception {
        return sourceList.size() > index ? sourceList.get(index++) : null;
    }
}

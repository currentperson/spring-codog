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
public class ListStepReader<T> implements ItemReader<T> {

    private List<T> sourceList;
    private int index = 0;
    private int finalIndex = 1;

    public ListStepReader(List<T> sourceList) {
        this.sourceList = sourceList;
    }

    public ListStepReader(List<T> sourceList, int index, int finalIndex) {
        this.sourceList = sourceList;
        this.index = index;
        this.finalIndex = finalIndex;
    }

    @Override
    public T read() throws Exception {
        return (finalIndex > index) ? sourceList.get(index++) : null;
    }
}

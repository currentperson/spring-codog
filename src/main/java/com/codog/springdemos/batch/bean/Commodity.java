package com.codog.springdemos.batch.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author : wangwenhan
 * @since : 2020/10/13
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Commodity {
    private String name;
    private Integer buyerCount;
}

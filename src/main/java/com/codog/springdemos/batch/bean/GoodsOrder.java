package com.codog.springdemos.batch.bean;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

/**
 * @author : wangwenhan
 * @since : 2020/10/13
 */
@Data
@AllArgsConstructor
public class GoodsOrder {
    private Commodity commodity;
    private List<Girl> buyerList;
}

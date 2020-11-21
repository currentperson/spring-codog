package com.codog.springdemos.batch.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

/**
 * @author : wangwenhan
 * @since : 2020/10/13
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Girl {

    @NotBlank
    private String name;
}

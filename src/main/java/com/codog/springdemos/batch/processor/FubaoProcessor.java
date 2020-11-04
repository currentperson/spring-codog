package com.codog.springdemos.batch.processor;

import com.codog.springdemos.batch.bean.Company;

import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * @author : wangwenhan
 * @since : 2020/11/4
 */
@Component
public class FubaoProcessor implements ItemProcessor<Company,String> {

    @Override
    public String process(Company company) throws Exception {
        if(Objects.equals(company.getName(),"蚂蚁金服")) {
            throw new RuntimeException("暂缓上市");
        }
        if(Objects.equals(company.getName(),"盒马")) {
            return null;
        }
        return company.getName() + "上市成功! 恭喜员工财务自由";
    }
}

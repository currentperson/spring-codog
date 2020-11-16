package com.codog.springdemos.batch.processor;

import com.codog.springdemos.batch.bean.Vote;

import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

/**
 * @author : wangwenhan
 * @since : 2020/11/1
 */
@Component
public class VoteProcessor implements ItemProcessor<Vote, String> {

    @Override
    public String process(Vote vote) throws Exception {
        return "投票给了" + vote.getName();
    }
}

package com.codog.springdemos.batch;

import com.codog.springdemos.batch.bean.Commodity;
import com.codog.springdemos.batch.bean.GoodsOrder;
import com.codog.springdemos.batch.processor.GirlProcessor;
import com.codog.springdemos.batch.reader.ListReader;
import com.codog.springdemos.batch.writer.PrintWriter;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author : wangwenhan
 * @since : 2020/10/13
 */
@Component
public class BatchDemo {

    @Autowired
    private JobBuilderFactory jobBuilderFactory;

    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    @Autowired
    protected JobLauncher jobLauncher;

    @Autowired
    private GirlProcessor girlProcessor;

    @Autowired
    private PrintWriter printWriter;

    @Scheduled(cron = "0 0/1 * * * ?")
    public void demo1() throws Exception {
        System.out.println("job starting");
        List<Commodity> commodityList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            commodityList.add(new Commodity("酒店" + i, i));
        }
        final ListReader<Commodity> reader = new ListReader<>();
        reader.setSourceList(commodityList);
        final Job girlJob = jobBuilderFactory.get("demo1-girlsInShanghaiJob")
            .flow(stepBuilderFactory.get("demo1-girlsInShanghaiStep")
                .<Commodity, GoodsOrder>chunk(2).reader(reader)
                .processor(girlProcessor).writer(printWriter).build())
            .end().build();
        jobLauncher.run(girlJob, new JobParametersBuilder().addDate("start_time",new Date()).toJobParameters());
    }
}

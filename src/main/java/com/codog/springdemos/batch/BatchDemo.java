package com.codog.springdemos.batch;

import com.codog.springdemos.batch.bean.Commodity;
import com.codog.springdemos.batch.bean.Company;
import com.codog.springdemos.batch.bean.Girl;
import com.codog.springdemos.batch.bean.GoodsOrder;
import com.codog.springdemos.batch.bean.Vote;
import com.codog.springdemos.batch.processor.FinalPaymentProcessor;
import com.codog.springdemos.batch.processor.FubaoProcessor;
import com.codog.springdemos.batch.processor.GirlProcessor;
import com.codog.springdemos.batch.processor.VoteProcessor;
import com.codog.springdemos.batch.reader.ListReader;
import com.codog.springdemos.batch.reader.ListStepReader;
import com.codog.springdemos.batch.writer.FubaoWriter;
import com.codog.springdemos.batch.writer.PrintWriter;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.SkipListener;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.job.builder.FlowBuilder;
import org.springframework.batch.core.job.flow.Flow;
import org.springframework.batch.core.job.flow.support.SimpleFlow;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.step.skip.AlwaysSkipItemSkipPolicy;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
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
    private FinalPaymentProcessor finalPaymentProcessor;

    @Autowired
    private FubaoProcessor fubaoProcessor;

    @Autowired
    private PrintWriter printWriter;

    @Autowired
    private FubaoWriter fubaoWriter;

    @Autowired
    private VoteProcessor voteProcessor;

    @Autowired
    private ItemProcessor girlStringItemProcessor;

    //@Scheduled(cron = "0 0/1 * * * ?")
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
        jobLauncher.run(girlJob, new JobParametersBuilder()
            .addDate("start_time", new Date()).toJobParameters());
    }

    //@Scheduled(cron = "0 0/1 * * * ?")
    public void demo4FinalPayments() throws Exception {
        final String JOB_NAME = "demo4FinalPayments";
        List<Commodity> commodityList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            commodityList.add(new Commodity("商品" + i, i));
        }
        final ListReader<Commodity> reader = new ListReader<>(commodityList);
        final Job girlJob = jobBuilderFactory.get(JOB_NAME)
            .flow(stepBuilderFactory.get(JOB_NAME)
                .<Commodity, String>chunk(2).reader(reader)
                .processor(finalPaymentProcessor).writer(printWriter).build())
            .end().build();
        jobLauncher.run(girlJob, new JobParametersBuilder()
            .addDate("start_time", new Date()).toJobParameters());
    }

    //@Scheduled(cron = "0 0/1 * * * ?")
    public void demo4Ali() throws Exception {
        final String JOB_NAME = "demo4Ali";
        List<Company> commodityList = Arrays.asList(new Company("蚂蚁金服"),
            new Company("菜鸟网络"),
            new Company("阿里云"),
            new Company("盒马"),
            new Company("大文娱"),
            new Company("飞猪"),
            new Company("阿里健康")
        );
        final ListReader<Company> reader = new ListReader<>(commodityList);
        final Job girlJob = jobBuilderFactory.get(JOB_NAME)
            .flow(stepBuilderFactory.get(JOB_NAME)
                .<Company, String>chunk(2).reader(reader)
                .processor(fubaoProcessor).writer(fubaoWriter).faultTolerant()
                .skipPolicy(new AlwaysSkipItemSkipPolicy()).listener(new SkipListener<Company, String>() {
                    @Override
                    public void onSkipInRead(Throwable throwable) {
                        System.out.println("onSkipInRead: " + throwable);
                    }

                    @Override
                    public void onSkipInProcess(Company company, Throwable throwable) {
                        System.out.println("onSkipInProcess: " + company.getName() + "暂缓上市");
                    }

                    @Override
                    public void onSkipInWrite(String s, Throwable throwable) {
                        System.out.println("onSkipInWrite: <del>" + s + "</del>上市失败");
                    }
                }).build())
            .end().build();
        jobLauncher.run(girlJob, new JobParametersBuilder()
            .addDate("start_time", new Date()).toJobParameters());
    }

    public TaskExecutor taskExecutor4Vote() {
        return new SimpleAsyncTaskExecutor("spring_batch_for_vote");
    }

    //@Scheduled(cron = "0 0/1 * * * ?")
    public void demo4Vote() throws Exception {
        final String JOB_NAME = "demo4Vote";
        List<Vote> voteList = new ArrayList<>();
        for (int i = 0; i < 16; i++) {
            voteList.add(new Vote(i % 2 == 0 ? "川建国" : "拜登"));
        }
        final Job job = jobBuilderFactory.get(JOB_NAME)
            .start(splitFlow(voteList)).end().build();
        jobLauncher.run(job, new JobParametersBuilder()
            .addDate("start_time", new Date()).toJobParameters());
    }

    public Flow splitFlow(List<Vote> voteList) {
        Flow[] flows = new Flow[4];
        for (int i = 0; i < flows.length; i++) {
            flows[i] = voteFlow(voteList, i + 1);
        }
        return new FlowBuilder<SimpleFlow>("splitVoteFlow")
            .split(taskExecutor4Vote())
            .add(flows)
            .build();
    }

    private Flow voteFlow(List<Vote> voteList, int i) {
        return new FlowBuilder<SimpleFlow>("splitVoteFlow" + i)
            .start(stepBuilderFactory.get("splitVoteStep" + i)
                .<Vote, String>chunk(2).reader(new ListStepReader<>(voteList, (i - 1) * 4, i * 4))
                .processor(voteProcessor).writer(printWriter).build())
            .build();
    }

    @Scheduled(cron = "0 0/1 * * * ?")
    public void demo4QingGirl() throws Exception {
        final String JOB_NAME = "demo4QingGirl";
        List<Girl> girlList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            girlList.add(i % 2 == 0 ? new Girl() : new Girl("唐靖"));
        }
        final ListReader<Girl> reader = new ListReader<>(girlList);
        final Job girlJob = jobBuilderFactory.get(JOB_NAME)
            .flow(stepBuilderFactory.get(JOB_NAME)
                .<Girl, String>chunk(2).reader(reader)
                .processor(girlStringItemProcessor).writer(printWriter).build())
            .end().build();
        jobLauncher.run(girlJob, new JobParametersBuilder()
            .addDate("start_time", new Date()).toJobParameters());
    }
}

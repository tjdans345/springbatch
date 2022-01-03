package com.meteor.springbatch.practice04.tasklet2;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.step.tasklet.MethodInvokingTaskletAdapter;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

// Tasklet Example 2 - MethodInvokingAdapter를 사용하여 구현하기
@Slf4j
@EnableBatchProcessing
@RequiredArgsConstructor
@Configuration
public class TaskletJobConfig {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    @Bean
    public Job TaskletJob() {

        return jobBuilderFactory.get("taskletJob")
                .start(TaskStep())
                .build();
    }

    @Bean
    public Step TaskStep() {
        return stepBuilderFactory.get("taskletStep")
                .tasklet(myTasklet()).build();
    }

    @Bean
    public CustomService service() {
        // 외부 비지니스 로직 클래스
        return new CustomService();
    }

    @Bean
    public MethodInvokingTaskletAdapter myTasklet() {
        MethodInvokingTaskletAdapter adapter = new MethodInvokingTaskletAdapter();
        adapter.setTargetObject(service());
        adapter.setTargetMethod("businessLogic");
        return adapter;
    }


}

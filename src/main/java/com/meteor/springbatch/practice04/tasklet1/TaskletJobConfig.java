package com.meteor.springbatch.practice04.tasklet1;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

// Tasklet Example 1 - Job Class 안에 Tasklet 구현하기(Lambda)
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
                .tasklet((contribution, chunkContext) -> {

                    // 비지니스 로직 작성 부분
                    for (int idx = 0; idx <10; idx++) {
                        log.info("[idx] = " + idx);
                    }

                    return RepeatStatus.FINISHED;
                }).build();
    }



}

package com.meteor.springbatch.practice04.tasklet3;

import com.meteor.springbatch.practice04.tasklet2.CustomService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.step.tasklet.MethodInvokingTaskletAdapter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

// Tasklet Example 3 - 외부 클래스를 사용하여 Tasklet 구현하기
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
                .tasklet(new BusinessTasklet())
                .build();
    }

}

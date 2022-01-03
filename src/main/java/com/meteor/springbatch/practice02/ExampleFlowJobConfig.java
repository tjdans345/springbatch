package com.meteor.springbatch.practice02;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
@RequiredArgsConstructor
@EnableBatchProcessing
public class ExampleFlowJobConfig {

    public JobBuilderFactory jobBuilderFactory;
    public StepBuilderFactory stepBuilderFactory;

    @Bean
    public Job ExampleJob() {

        return jobBuilderFactory.get("exampleJob")
                .start()

    }

    @Bean
    public Step startStep() {
        return stepBuilderFactory.get("startStep")
                .tasklet((contribution, chunkContext) -> {
                    log.info("Start Step");

                    String result = "COMPLETED";
//                    String result = "FAIL";
//                    String result = "UNKNOWN";
                    // Flow에서 on은 RepeatStatus가 아닌 ExitStatus를 바라봄.
                    if(result.equals("COMPLETED")) {
                        contribution.setExitStatus(ExitStatus.COMPLETED);
                    } else if (result.equals("FAIL")) {
                        contribution.setExitStatus(ExitStatus.FAILED);
                    } else if (result.equals("UNKNOWN")) {
                        contribution.setExitStatus(ExitStatus.STOPPED);
                    }
                    return RepeatStatus.FINISHED;
                }).build();
    }

    @Bean
    public Step failOverStep(){
        return stepBuilderFactory.get("nextStep")
                .tasklet((contribution, chunkContext) -> {
                    log.info("FailOver Step!");
                    return RepeatStatus.FINISHED;
                }).build();
    }

    @Bean
    public



}

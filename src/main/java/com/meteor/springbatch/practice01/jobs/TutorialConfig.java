package com.meteor.springbatch.practice01.jobs;

import com.meteor.springbatch.practice01.taskiets.TutorialTasklet;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class TutorialConfig {

    private final JobBuilderFactory jobBuilderFactory; // Job 빌더 생성용
    private final StepBuilderFactory stepBuilderFactory; // Step 빌더 생성용

    // JobBuilderFactory 를 통해서 tutorialJob 을 생성
    @Bean
    public Job tutorialJob() {
        return jobBuilderFactory.get("tutorialJob")
                .start(tutorialStep())
                .next(tutorialStep2())
                .build();
    }

    // StepBuilderFactory 를 통해서 tutorialStep 을 생성
    @Bean
    public Step tutorialStep() {
        return stepBuilderFactory.get("tutorialStep")
                .tasklet(new TutorialTasklet()) // Tasklet 설정
                .build();
    }

    @Bean
    public Step tutorialStep2() {
        return stepBuilderFactory.get("tutorialStep2222222222222222")
                .tasklet(new TutorialTasklet()) // Tasklet 설정
                .build();
    }

}

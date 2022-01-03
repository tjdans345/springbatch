package com.meteor.springbatch.chunkpractice.practice01;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.*;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.JdbcPagingItemReader;
import org.springframework.batch.item.database.Order;
import org.springframework.batch.item.database.PagingQueryProvider;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.batch.item.database.builder.JdbcPagingItemReaderBuilder;
import org.springframework.batch.item.database.support.SqlPagingQueryProviderFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.BeanPropertyRowMapper;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

/**
 * Chunk Example 2 - JPA 기반의 Batch Job 구현하기
 * 전체 금액이 10,000원 이상인 회원들에게 1,000원 캐시백을 주는 배치
 */

//@Slf4j
//@Configuration
//@RequiredArgsConstructor
//@EnableBatchProcessing
//public class ExampleJobConfig {
//
//    private final JobBuilderFactory jobBuilderFactory;
//    private final StepBuilderFactory stepBuilderFactory;
//    private final EntityManagerFactory entityManagerFactory;
//
//    @Bean
//    public Job ExampleJob() throws Exception {
//
//        Job exampleJob = jobBuilderFactory.get("exampleJob")
//                .start(Step())
//                .build();
//
//        return exampleJob;
//    }
//
//    @Bean
//    @JobScope
//    public Step Step() throws Exception {
//        return stepBuilderFactory.get("Step")
//                .<Member,Member>chunk(10)
//                .reader(reader())
//                .processor(processor())
//                .writer(writer())
//                .build();
//    }
//
//    @Bean
//    @StepScope
//    public JpaPagingItemReader<Member> reader() throws Exception {
//
//        Map<String,Object> parameterValues = new HashMap<>();
//        parameterValues.put("amount", 10000);
//
//        return new JpaPagingItemReaderBuilder<Member>()
//                .pageSize(10)
//                .parameterValues(parameterValues)
//                .queryString("SELECT p FROM Member p WHERE p.amount >= :amount ORDER BY id ASC")
//                .entityManagerFactory(entityManagerFactory)
//                .name("JpaPagingItemReader")
//                .build();
//    }
//
//    @Bean
//    @StepScope
//    public ItemProcessor<Member, Member> processor(){
//
//        return new ItemProcessor<Member, Member>() {
//            @Override
//            public Member process(Member member) throws Exception {
//
//                //1000원 추가 적립
//                member.setAmount(member.getAmount() + 1000);
//
//                return member;
//            }
//        };
//    }
//
//    @Bean
//    @StepScope
//    public JpaItemWriter<Member> writer(){
//        return new JpaItemWriterBuilder<Member>()
//                .entityManagerFactory(entityManagerFactory)
//                .build();
//    }
//}
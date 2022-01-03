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
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.BeanPropertyRowMapper;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

/**
 * Chunk Example 1 - Jdbc 기반의 Batch Job 구현하기
 * 전체 금액이 10,000원 이상인 회원들에게 1,000원 캐시백을 주는 배치
 */

@Slf4j
@Configuration
@RequiredArgsConstructor
@EnableBatchProcessing
public class ExampleJobConfig {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    private final DataSource dataSource;

    @Bean
    public Job ExampleJob() throws Exception {

        return jobBuilderFactory.get("exampleJob")
                .start(Step())
                .build();
    }

    @Bean
    @JobScope
    public Step Step() throws Exception {
        return stepBuilderFactory.get("Step")
                .<Member, Member>chunk(10)
                .reader(reader())
                .processor(processor())
                .writer(writer())
                .build();
    }

    @Bean
    @StepScope
    public JdbcPagingItemReader<Member> reader() throws Exception {

        Map<String, Object> parameterValues = new HashMap<>();
        parameterValues.put("amount", "10000");

        // pageSize 와 fetchSize 는 동일하게 설정
        return new JdbcPagingItemReaderBuilder<Member>()
                .pageSize(10)
                .fetchSize(10)
                .dataSource(dataSource)
                .rowMapper(new BeanPropertyRowMapper<>(Member.class))
                .queryProvider(customQueryProvider())
                .name("JdbcPagingItemReader")
                .build();

    }

    @Bean
    @StepScope
    public ItemProcessor<Member, Member> processor() {
        return new ItemProcessor<Member, Member>() {
            @Override
            public Member process(Member member) throws Exception {

                // 1000원 추가 적립
                member.setPoint(member.getPoint() + 1000);
                return member;
            }
        };
    }

    @Bean
    @StepScope
    public JdbcBatchItemWriter<Member> writer() {
        return new JdbcBatchItemWriterBuilder<Member>()
                .dataSource(dataSource)
                .sql("UPDATE MEMBER SET POINT = : :point WHERE ID = :id")
                .beanMapped()
                .build();
    }

    public PagingQueryProvider customQueryProvider() throws Exception {
        SqlPagingQueryProviderFactoryBean queryProviderFactoryBean = new SqlPagingQueryProviderFactoryBean();

        queryProviderFactoryBean.setDataSource(dataSource);

        queryProviderFactoryBean.setSelectClause("SELECT ID, NAME, EMAIL, NICK_NAME, STATUS, AMOUNT");
        queryProviderFactoryBean.setFromClause("FROM MEMBER ");
        queryProviderFactoryBean.setWhereClause("WHERE AMOUNT >= :amount");

        Map<String, Order> sortKey = new HashMap<>();
        sortKey.put("id", Order.ASCENDING);

        queryProviderFactoryBean.setSortKeys(sortKey);

        return queryProviderFactoryBean.getObject();
    }





}

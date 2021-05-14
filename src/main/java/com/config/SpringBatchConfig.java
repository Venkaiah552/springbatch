package com.config;


import com.mapper.UserRowMapper;
import com.model.User;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.JdbcPagingItemReader;
import org.springframework.batch.item.database.JpaPagingItemReader;
import org.springframework.batch.item.database.Order;
import org.springframework.batch.item.database.support.H2PagingQueryProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableBatchProcessing
public class SpringBatchConfig {

    @Autowired
    private DataSource dataSource;

    @Bean
    public Job job(JobBuilderFactory jobBuilderFactory,
                   StepBuilderFactory stepBuilderFactory,
                   ItemReader<User> itemReader,
                   ItemProcessor<User, User> itemProcessor,
                   ItemWriter<User> itemWriter
    ) throws Exception  {

        Step step= stepBuilderFactory.get("ETL-data-read")
                .<User, User>chunk(2)
                .reader(pagingItemReader())
                .processor(itemProcessor)
                .writer(itemWriter)
                .build();


        return jobBuilderFactory.get("ETL-Load")
                .incrementer(new RunIdIncrementer())
                .start(step)
                .build();
    }


    @Bean
    public JdbcPagingItemReader<User> pagingItemReader() {
        JdbcPagingItemReader<User> reader = new JdbcPagingItemReader<>();
        reader.setDataSource(this.dataSource);
        reader.setPageSize(10);
        //reader.setFetchSize(2);
        reader.setRowMapper(new UserRowMapper());

        Map<String, Order> sortKeys = new HashMap<>();
        sortKeys.put("id", Order.ASCENDING);

        H2PagingQueryProvider queryProvider = new H2PagingQueryProvider();
        queryProvider.setSelectClause("select *");
        queryProvider.setFromClause("from user");
        queryProvider.setSortKeys(sortKeys);


        reader.setQueryProvider(queryProvider);

        return reader;
    }



  /*  @Autowired
    private EntityManagerFactory entityManagerFactory;

    @Bean
    public JpaPagingItemReader<User> pagingItemReader() throws Exception  {
        JpaPagingItemReader<User> databaseReader = new JpaPagingItemReader<>();
        databaseReader.setEntityManagerFactory(entityManagerFactory);
        JpaQueryProviderImpl<User> jpaQueryProvider = new JpaQueryProviderImpl<>();
        jpaQueryProvider.setQuery("user.findAll");
        databaseReader.setQueryProvider(jpaQueryProvider);
        databaseReader.setPageSize(3);
        databaseReader.afterPropertiesSet();
        return databaseReader;
    }*/


}

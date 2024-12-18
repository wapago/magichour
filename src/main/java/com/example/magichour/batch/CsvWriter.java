package com.example.magichour.batch;

import com.example.magichour.entity.MovieEntity;
import com.example.magichour.repository.MovieRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;

@Configuration
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CsvWriter implements ItemWriter<MovieEntity> {

    @Value("${kmdb.sql}")
    private String insertSql;

    private final MovieRepository movieRepository;

    @Bean
    public JdbcBatchItemWriter<MovieEntity> JdbcBatchItemWriter(@Qualifier("datasource-data") DataSource dataSource) {

        return new JdbcBatchItemWriterBuilder<MovieEntity>()
                .dataSource(dataSource)
                .sql(insertSql)
                .itemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<>())
                .build();
    }

    @Override
    @Transactional
    public void write(Chunk<? extends MovieEntity> chunk) {
        movieRepository.saveAll(chunk);
    }
}

package com.example.magichour.batch;

import com.example.magichour.entity.MovieEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.JobRegistry;
import org.springframework.batch.core.configuration.support.JobRegistryBeanPostProcessor;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionException;
import org.springframework.transaction.TransactionStatus;

import javax.sql.DataSource;

@Configuration
@RequiredArgsConstructor
@Slf4j
public class FileItemReaderJobConfig {
    private final JobRepository jobRepository;
    private final JobRegistry jobRegistry;
    private final PlatformTransactionManager platformTransactionManager;
    private final CsvReader csvReader;
    private final CsvWriter csvWriter;
    private final CustomChunkListener customChunkListener;

    private static final int chunkSize = 1000;
    private final DataSource dataSource;

    @Value("${kmdb.purpose}")
    private String purpose;

    @Value("${kmdb.genre}")
    private String genre;

    @Bean
    public Job csvFileItemReaderJob() {
        return new JobBuilder("csvFileItemReaderJob", jobRepository)
                .start(csvFileItemReaderStep())
                .build();
    }

    @Bean
    public Step csvFileItemReaderStep() {
        return new StepBuilder("csvFileItemReaderStep", jobRepository)
                .<MovieEntity, MovieEntity> chunk(chunkSize, platformTransactionManager)
                .reader(csvReader.csvFileItemReader())
                .processor(processor())
                .writer(csvWriter.JdbcBatchItemWriter(dataSource))
                .listener(customChunkListener)
                .transactionManager(transactionManagerWithLogging())
                .build();
    }

    @Bean
    public PlatformTransactionManager transactionManagerWithLogging() {
        return new PlatformTransactionManager() {
            @Override
            public TransactionStatus getTransaction(TransactionDefinition definition) throws TransactionException {
                log.debug("트랜잭션 시작: " + definition.getName());
                return platformTransactionManager.getTransaction(definition);
            }

            @Override
            public void commit(TransactionStatus status) throws TransactionException {
                log.debug("트랜잭션 커밋");
                platformTransactionManager.commit(status);
            }

            @Override
            public void rollback(TransactionStatus status) throws TransactionException {
                log.debug("트랜잭션 롤백");
                platformTransactionManager.rollback(status);
            }
        };
    }

    @Bean
    public ItemProcessor<MovieEntity, MovieEntity> processor() {
        return item -> {
            boolean isMovie = item.getPurpose().equals(purpose) && !item.getGenre().equals(genre);
            if(isMovie) return item;

            return null;
        };
    }

    @Bean
    public JobRegistryBeanPostProcessor jobRegistryBeanPostProcessor() {
        JobRegistryBeanPostProcessor processor = new JobRegistryBeanPostProcessor();
        processor.setJobRegistry(jobRegistry);
        return processor;
    }
}

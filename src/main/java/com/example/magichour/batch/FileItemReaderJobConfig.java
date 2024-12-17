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
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionException;
import org.springframework.transaction.TransactionStatus;

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

    private static final int chunkSize = 10;
    private final String purpose = "극장용";

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
                .writer(csvWriter)
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
            boolean isMovie = item.getPurpose().equals(purpose);

            if(isMovie) {
                return item;
            }

            log.info(">>>>>>>> Filtered 제목: " + item.getMovieNm());
            log.info(">>>>>>>> Filtered PURPOSE: " + item.getPurpose());
            log.info(">>>>>>>> Filtered ID: " + item.getMovieId());

            return null;
        };
    }

    // JobRegistry에 수동으로 Job을 등록하기 위한 빈 추가
    @Bean
    public JobRegistryBeanPostProcessor jobRegistryBeanPostProcessor() {
        JobRegistryBeanPostProcessor processor = new JobRegistryBeanPostProcessor();
        processor.setJobRegistry(jobRegistry);
        return processor;
    }
}

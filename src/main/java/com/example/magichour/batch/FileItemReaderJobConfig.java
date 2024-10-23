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

@Configuration
@RequiredArgsConstructor
@Slf4j
public class FileItemReaderJobConfig {
    private final JobRepository jobRepository;
    private final JobRegistry jobRegistry;
    private final PlatformTransactionManager platformTransactionManager;
    private final CsvReader csvReader;
    private final CsvWriter csvWriter;

    private static final int chunkSize = 1000;
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
                .build();
    }

    @Bean
    public ItemProcessor<MovieEntity, MovieEntity> processor() {
        return item -> {
            boolean isMovie = item.getPurpose().equals(purpose);

            if(isMovie) {
                return item;
            }

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

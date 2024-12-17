package com.example.magichour.batch;

import com.example.magichour.entity.MovieEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.separator.DefaultRecordSeparatorPolicy;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;

@Configuration
@RequiredArgsConstructor
@Slf4j
public class CsvReader {

    @Value("${kmdb.filepath}")
    private String filePath;

    @Value("${kmdb.columns}")
    private String[] columnNames;

    @Bean
    public FlatFileItemReader<MovieEntity> csvFileItemReader() {
        FlatFileItemReader<MovieEntity> flatFileItemReader = new FlatFileItemReader<>();
        flatFileItemReader.setResource(new ClassPathResource(filePath));
        flatFileItemReader.setLinesToSkip(1); // header line skip
        flatFileItemReader.setEncoding("UTF-8");

        // 데이터 내부 개행 처리
        flatFileItemReader.setRecordSeparatorPolicy(new DefaultRecordSeparatorPolicy());

        DefaultLineMapper<MovieEntity> defaultLineMapper = new DefaultLineMapper<>();

        DelimitedLineTokenizer delimitedLineTokenizer = new DelimitedLineTokenizer(",");
        defaultLineMapper.setLineTokenizer(delimitedLineTokenizer);
        delimitedLineTokenizer.setNames(columnNames);

        BeanWrapperFieldSetMapper<MovieEntity> fieldSetMapper = new BeanWrapperFieldSetMapper<>();
        fieldSetMapper.setTargetType(MovieEntity.class);
        fieldSetMapper.setDistanceLimit(0);
        fieldSetMapper.setStrict(false);

        defaultLineMapper.setFieldSetMapper(fieldSetMapper);

        flatFileItemReader.setLineMapper(defaultLineMapper);

        return flatFileItemReader;
    }
}

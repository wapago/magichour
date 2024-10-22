package com.example.magichour.batch;

import com.example.magichour.entity.MovieEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.separator.DefaultRecordSeparatorPolicy;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

@Configuration
@RequiredArgsConstructor
@Slf4j
public class CsvReader {

    private static final String filePath = "/csv/kmdb_csv.csv";

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
        delimitedLineTokenizer.setNames("registerId", "registerNumber", "movieNm", "englishTitle", "originalTitle", "type", "purpose", "genre", "nation", "prodYear",
                                        "company", "directors", "actors", "script", "openDt", "runtime", "keyWord", "plots", "registerDate", "modifiedDate", "anonymous");

        defaultLineMapper.setFieldSetMapper(fieldSet -> {
            String movieId = fieldSet.readString("registerId") + fieldSet.readString("registerNumber");
            String movieNm = fieldSet.readString("movieNm");
            String genre = fieldSet.readString("genre");
            String nation = fieldSet.readString("nation");
            String prodYear = fieldSet.readString("prodYear");
            String company = fieldSet.readString("company");
            String directors = fieldSet.readString("directors");
            String actors = fieldSet.readString("actors");
            String script = fieldSet.readString("script");
            String openDt = fieldSet.readString("openDt");
            String runtime = fieldSet.readString("runtime");
            String keyWord = fieldSet.readString("keyWord");
            String plots = fieldSet.readString("plots");
            String registerDate = fieldSet.readString("registerDate");
            String modifiedDate = fieldSet.readString("modifiedDate");

            return new MovieEntity(movieId, movieNm, genre, nation, prodYear, company, directors, actors, script, openDt, runtime, keyWord, plots, registerDate, modifiedDate);
        });

        flatFileItemReader.setLineMapper(defaultLineMapper);

        return flatFileItemReader;
    }
}

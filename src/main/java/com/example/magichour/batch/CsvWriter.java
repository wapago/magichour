package com.example.magichour.batch;

import com.example.magichour.entity.MovieEntity;
import com.example.magichour.repository.MovieRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.Transactional;

@Configuration
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CsvWriter implements ItemWriter<MovieEntity> {
    private final MovieRepository movieRepository;

    @Override
    @Transactional
    public void write(Chunk<? extends MovieEntity> chunk) throws Exception {
        movieRepository.saveAll(chunk);
    }
}

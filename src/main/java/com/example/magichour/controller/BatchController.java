package com.example.magichour.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.configuration.JobRegistry;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.time.LocalDateTime;

@Controller
@ResponseBody
@Slf4j
public class BatchController {

    private final JobLauncher jobLauncher;
    private final JobRegistry jobRegistry;

    public BatchController(JobLauncher jobLauncher, JobRegistry jobRegistry) {
        this.jobLauncher = jobLauncher;
        this.jobRegistry = jobRegistry;
    }

    @GetMapping("/kmdbcsv")
    public String kmdbCsvBatch() throws Exception {
        log.info("============= KMDB_CSV BATCH STARTED ============");
        JobParameters jobParameters = new JobParametersBuilder()
                .addString("timestamp", LocalDateTime.now().toString())
                .toJobParameters();

        jobLauncher.run(jobRegistry.getJob("csvFileItemReaderJob"), jobParameters);

        return "ok";
    }
}

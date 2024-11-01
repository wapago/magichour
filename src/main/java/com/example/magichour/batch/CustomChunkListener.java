package com.example.magichour.batch;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.ChunkListener;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.scope.context.StepContext;
import org.springframework.stereotype.Component;


@Component
@Slf4j
public class CustomChunkListener implements ChunkListener {

    @Override
    public void beforeChunk(ChunkContext context) {
        log.debug("Chunk 시작 - JobName: {}, StepName: {}",
                context.getStepContext().getJobName(),
                context.getStepContext().getStepName());
    }

    @Override
    public void afterChunk(ChunkContext context) {
        StepContext stepContext = context.getStepContext();
        StepExecution stepExecution = stepContext.getStepExecution();
        long filterCount = stepExecution.getFilterCount();
        long readcount = stepExecution.getReadCount();
        long commitCount = stepExecution.getCommitCount();

        log.debug("filterCount = " + filterCount);
        log.debug("readcount = " + readcount);
        log.debug("commitCount = " + commitCount);
    }

    @Override
    public void afterChunkError(ChunkContext context) {
        log.error("Chunk 처리 중 에러 발생 - JobName: {}, StepName: {}",
                context.getStepContext().getJobName(),
                context.getStepContext().getStepName());
    }
}

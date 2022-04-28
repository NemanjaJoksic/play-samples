package org.joksin.playsamples.scheduler.job;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Data
public class PrintMessageJob1 extends AbstractJob {

    private String message;

    @Override
    public void execute() {
        log.info("PrintMessageJob1 - Job ID: {} | {}", id, message);
    }

}

package org.joksin.playsamples.scheduler.job;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Data
public class PrintMessageJob2 extends AbstractJob {

    private String message;

    @Override
    public void execute() {
        log.info("PrintMessageJob2 - Job ID: {} | {}", id, message);
    }

}

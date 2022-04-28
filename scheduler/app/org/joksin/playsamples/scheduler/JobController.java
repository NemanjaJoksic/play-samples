package org.joksin.playsamples.scheduler;

import akka.actor.ActorSystem;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.ImmutableMap;
import lombok.extern.slf4j.Slf4j;
import org.joksin.playsamples.scheduler.ec.JobExecutionContext;
import org.joksin.playsamples.scheduler.job.AbstractJob;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Results;
import scala.concurrent.duration.Duration;

import javax.inject.Inject;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Slf4j
public class JobController extends Controller {

    private final ActorSystem actorSystem;
    private final JobExecutionContext ec;
    private final ObjectMapper mapper;

    @Inject
    public JobController(ActorSystem actorSystem, JobExecutionContext ec) {
        this.actorSystem = actorSystem;
        this.ec = ec;
        this.mapper = new ObjectMapper();
    }

    public Result scheduleJob() throws JsonProcessingException {
        log.info("Executing JobController.scheduleJob()");
        AbstractJob job = mapper.treeToValue(request().body().asJson(), AbstractJob.class);

        String id = UUID.randomUUID().toString();
        job.setId(id);

        this.actorSystem
                .scheduler()
                .schedule(
                        Duration.create(job.getInitialDelay(), TimeUnit.SECONDS), //initialDelay
                        Duration.create(job.getInterval(), TimeUnit.SECONDS), //interval
                        () -> job.execute(),
                        this.ec
                );

        log.info("Job with ID {} and type {} has been created", id, job.getType());
        return Results.ok(Json.toJson(ImmutableMap.of("jobId", id)));
    }

}

package org.joksin.playsamples.scheduler.ec;

import akka.actor.ActorSystem;
import play.libs.concurrent.CustomExecutionContext;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class JobExecutionContext extends CustomExecutionContext {

    @Inject
    public JobExecutionContext(ActorSystem actorSystem) {
        super(actorSystem, "akka.actor.job-dispatcher");
    }

}

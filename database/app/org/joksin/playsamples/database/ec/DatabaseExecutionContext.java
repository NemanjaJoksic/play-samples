package org.joksin.playsamples.database.ec;

import akka.actor.ActorSystem;
import lombok.extern.slf4j.Slf4j;
import play.libs.concurrent.CustomExecutionContext;

import javax.inject.Inject;
import javax.inject.Singleton;

@Slf4j
@Singleton
public class DatabaseExecutionContext extends CustomExecutionContext {

    @Inject
    public DatabaseExecutionContext(ActorSystem actorSystem) {
        super(actorSystem, "akka.actor.database-dispatcher");
        log.info("DatabaseExecutionContext created");
    }

}

package org.joksin.playsamples.helloworld;

import com.google.common.collect.ImmutableMap;
import lombok.extern.slf4j.Slf4j;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Results;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

@Slf4j
public class HelloAsyncController extends Controller {

    public CompletionStage<Result> helloworld() {
        log.info("Executing HelloAsyncController.helloworld()");
        return CompletableFuture.supplyAsync(() -> Results.ok(Json.toJson(ImmutableMap.of("message", "Hello world!"))));
    }

    public CompletionStage<Result> hello(String name) {
        log.info("Executing HelloAsyncController.hello()");
        return CompletableFuture.supplyAsync(() -> Results.ok(Json.toJson(ImmutableMap.of("message", "Hello " + name + "!"))));
    }
    
}

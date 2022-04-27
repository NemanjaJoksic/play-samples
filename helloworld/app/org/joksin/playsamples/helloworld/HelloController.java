package org.joksin.playsamples.helloworld;

import com.google.common.collect.ImmutableMap;
import lombok.extern.slf4j.Slf4j;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Results;

@Slf4j
public class HelloController extends Controller {

    public Result helloworld() {
        log.info("Executing HelloController.helloworld()");
        return Results.ok(Json.toJson(ImmutableMap.of("message", "Hello world!")));
    }

    public Result hello(String name) {
        log.info("Executing HelloController.hello()");
        return Results.ok(Json.toJson(ImmutableMap.of("message", "Hello " + name + "!")));
    }

}

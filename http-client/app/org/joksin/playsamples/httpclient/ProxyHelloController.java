package org.joksin.playsamples.httpclient;

import lombok.extern.slf4j.Slf4j;
import play.libs.ws.WSClient;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Results;

import javax.inject.Inject;
import java.util.concurrent.CompletionStage;

@Slf4j
public class ProxyHelloController extends Controller {

    private final WSClient ws;

    @Inject
    public ProxyHelloController(WSClient ws) {
        this.ws = ws;
    }

    public CompletionStage<Result> helloworld() {
        log.info("Executing ProxyHelloController.helloworld()");
        return ws.url("http://localhost:9000/helloworld")
                .get()
                .thenApplyAsync(response -> Results.ok(response.asJson()));
    }

    public CompletionStage<Result> hello(String name) {
        log.info("Executing ProxyHelloController.hello()");
        return ws.url("http://localhost:9000/hello")
                .addQueryParameter("name", name)
                .get()
                .thenApplyAsync(response -> Results.ok(response.asJson()));
    }

}

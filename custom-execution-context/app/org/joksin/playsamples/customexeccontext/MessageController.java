package org.joksin.playsamples.customexeccontext;

import com.google.common.collect.ImmutableMap;
import lombok.extern.slf4j.Slf4j;
import org.joksin.playsamples.customexeccontext.service.MessageService;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Results;

import javax.inject.Inject;
import java.util.concurrent.CompletionStage;

@Slf4j
public class MessageController extends Controller {

    private final MessageService messageService;

    @Inject
    public MessageController(MessageService messageService) {
        this.messageService = messageService;
    }

    public CompletionStage<Result> getMessage() {
        log.info("Executing MessageController.getMessage()");
        return messageService
                    .generateMessage()
                    .thenApplyAsync(message -> Results.ok(Json.toJson(ImmutableMap.of("message", messageService.generateMessageSync()))));
    }

    public Result getMessageSync() {
        log.info("Executing MessageController.getMessageSync()");
        return Results.ok(Json.toJson(ImmutableMap.of("message", messageService.generateMessageSync())));
    }
}

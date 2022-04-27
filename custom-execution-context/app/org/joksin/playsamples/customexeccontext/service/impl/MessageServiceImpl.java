package org.joksin.playsamples.customexeccontext.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.joksin.playsamples.customexeccontext.service.MessageService;
import play.libs.concurrent.HttpExecutionContext;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

@Slf4j
@Singleton
public class MessageServiceImpl implements MessageService {

    private final HttpExecutionContext ec;

    @Inject
    public MessageServiceImpl(HttpExecutionContext ec) {
        this.ec = ec;
    }

    @Override
    public CompletionStage<String> generateMessage() {
        return CompletableFuture.supplyAsync(() -> generateMessageImpl(), ec.current());
    }

    @Override
    public String generateMessageSync() {
        return generateMessageImpl();
    }

    private String generateMessageImpl() {
        String messageId = UUID.randomUUID().toString();
        log.info("MessageServiceImpl is generating message with ID {}", messageId);
        return "Message ID: " + messageId;
    }

}

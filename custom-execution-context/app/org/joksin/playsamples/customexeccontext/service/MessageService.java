package org.joksin.playsamples.customexeccontext.service;

import com.google.inject.ImplementedBy;
import org.joksin.playsamples.customexeccontext.service.impl.MessageServiceImpl;

import java.util.concurrent.CompletionStage;

@ImplementedBy(MessageServiceImpl.class)
public interface MessageService {

    CompletionStage<String> generateMessage();

    String generateMessageSync();

}

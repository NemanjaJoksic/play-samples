package org.joksin.playsamples.scheduler.job;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.Data;

@Data
@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.EXISTING_PROPERTY,
        property = "type",
        defaultImpl = AbstractJob.class,
        visible = true
)
@JsonSubTypes({
        @JsonSubTypes.Type (value = PrintMessageJob1.class, name = "messagePrinter1"),
        @JsonSubTypes.Type (value = PrintMessageJob2.class, name = "messagePrinter2")
})
public abstract class AbstractJob {

    protected String id;
    protected String type;
    protected Integer initialDelay;
    protected Integer interval;

    public abstract void execute();

}

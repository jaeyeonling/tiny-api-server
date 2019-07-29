package com.jaeyeonling.tiny.domain.engine.task;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class EngineTaskResponse {

    private final long id;
    private final LocalDateTime createdDate;
    private final LocalDateTime lastModifiedDate;
    private final String description;
    private final boolean end;

    public EngineTaskResponse(final EngineTask engineTask) {
        this.id = engineTask.getId();
        this.createdDate = engineTask.getCreatedDate();
        this.lastModifiedDate = engineTask.getLastModifiedDate();
        this.description = engineTask.getDescription();
        this.end = engineTask.isEnd();
    }
}

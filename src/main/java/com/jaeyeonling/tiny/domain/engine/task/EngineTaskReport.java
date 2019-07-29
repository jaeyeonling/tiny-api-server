package com.jaeyeonling.tiny.domain.engine.task;

import lombok.Builder;
import lombok.Getter;

import javax.persistence.Embeddable;

@Embeddable
public class EngineTaskReport {

    @Getter
    private final String engineName;

    @Getter
    private final String description;

    @Getter
    private final long taskRunningTimeMillis;

    @Builder
    private EngineTaskReport(final String engineName,
                             final String description,
                             final long taskRunningTimeMillis) {
        this.engineName = engineName;
        this.description = description;
        this.taskRunningTimeMillis = taskRunningTimeMillis;
    }

    static EngineTaskReport of(final EngineTask engineTask) {
        return EngineTaskReport.builder()
                .engineName(engineTask.getEngineName())
                .description(engineTask.getDescription())
                .taskRunningTimeMillis(engineTask.getTaskRunningTimeMillis())
                .build();
    }
}

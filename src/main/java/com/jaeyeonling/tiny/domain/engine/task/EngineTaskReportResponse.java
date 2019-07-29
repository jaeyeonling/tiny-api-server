package com.jaeyeonling.tiny.domain.engine.task;

import lombok.Getter;

@Getter
class EngineTaskReportResponse {

    private final String engineName;
    private final String description;
    private final long taskRunningTimeMillis;

    EngineTaskReportResponse(final EngineTaskReport engineTaskReport) {
        this.engineName = engineTaskReport.getEngineName();
        this.description = engineTaskReport.getDescription();
        this.taskRunningTimeMillis = engineTaskReport.getTaskRunningTimeMillis();
    }
}

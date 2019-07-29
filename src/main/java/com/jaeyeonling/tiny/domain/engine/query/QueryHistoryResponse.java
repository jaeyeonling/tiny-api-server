package com.jaeyeonling.tiny.domain.engine.query;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
class QueryHistoryResponse {

    private final long id;
    private final LocalDateTime createdDate;
    private final String engineName;
    private final String engineDescription;

    QueryHistoryResponse(final QueryHistory queryHistory) {
        id = queryHistory.getId();
        createdDate = queryHistory.getCreatedDate();
        engineName = queryHistory.getEngineName();
        engineDescription = queryHistory.getEngineDescription();
    }
}

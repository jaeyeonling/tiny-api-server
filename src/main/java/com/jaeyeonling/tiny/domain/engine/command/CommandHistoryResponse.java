package com.jaeyeonling.tiny.domain.engine.command;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
class CommandHistoryResponse {

    private final long id;
    private final LocalDateTime createdDate;
    private final String engineName;
    private final String engineDescription;

    CommandHistoryResponse(final CommandHistory commandHistory) {
        id = commandHistory.getId();
        createdDate = commandHistory.getCreatedDate();
        engineName = commandHistory.getEngineName();
        engineDescription = commandHistory.getEngineDescription();
    }
}

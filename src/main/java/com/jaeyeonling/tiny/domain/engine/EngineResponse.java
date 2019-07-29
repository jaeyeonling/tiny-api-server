package com.jaeyeonling.tiny.domain.engine;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
class EngineResponse {

    private final Long id;
    private final LocalDateTime createdDate;
    private final LocalDateTime lastModifiedDate;
    private final String name;

    EngineResponse(final Engine engine) {
        this.id = engine.getId();
        this.createdDate = engine.getCreatedDate();
        this.lastModifiedDate = engine.getLastModifiedDate();
        this.name = engine.getName();
    }
}

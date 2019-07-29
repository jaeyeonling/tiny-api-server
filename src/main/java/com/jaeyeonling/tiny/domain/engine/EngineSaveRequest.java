package com.jaeyeonling.tiny.domain.engine;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Size;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class EngineSaveRequest {

    @Size(min = 4, max = 100)
    private String name;

    @Builder
    public EngineSaveRequest(final String name) {
        this.name = name;
    }

    Engine toEntity() {
        return Engine.builder()
                .name(name)
                .build();
    }
}

package com.jaeyeonling.tiny.domain.engine.task;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@ToString
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class EngineTaskOption {

    @NotBlank
    @Size(max = 1_000)
    private String description;

    private int complex;

    @Builder
    public EngineTaskOption(final String description,
                            final int complex) {
        this.description = description;
        this.complex = complex;
    }
}

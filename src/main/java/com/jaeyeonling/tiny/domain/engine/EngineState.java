package com.jaeyeonling.tiny.domain.engine;

import com.jaeyeonling.tiny.domain.engine.task.EngineTaskOption;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.time.LocalDateTime;
import java.util.Optional;

@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class EngineState {

    @Getter
    @Column(nullable = false, length = 1000)
    private String description;

    @Getter
    @Column(nullable = false)
    private LocalDateTime endAt;

    private EngineState(final String description,
                        final LocalDateTime endAt) {
        this.description = description;
        this.endAt = endAt;
    }

    public static EngineState of(final EngineTaskOption engineTaskOption) {
        return new EngineState(engineTaskOption.getDescription(), calculateEndAt(engineTaskOption.getComplex()));
    }

    private static LocalDateTime calculateEndAt(final Integer complex) {
        return Optional.ofNullable(complex)
                .map(LocalDateTime.now()::plusSeconds)
                .orElseGet(LocalDateTime::now);
    }

    public boolean isFinish(final LocalDateTime standardTime) {
        return standardTime.isAfter(endAt);
    }
}

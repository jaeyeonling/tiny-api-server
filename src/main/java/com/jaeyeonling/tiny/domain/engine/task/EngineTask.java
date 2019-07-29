package com.jaeyeonling.tiny.domain.engine.task;

import com.jaeyeonling.tiny.domain.engine.Engine;
import com.jaeyeonling.tiny.domain.engine.EngineState;
import com.jaeyeonling.tiny.global.domain.DateAuditEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.time.Duration;
import java.time.LocalDateTime;

@Table
@Entity
@DynamicUpdate
@DynamicInsert
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class EngineTask extends DateAuditEntity {

    @ManyToOne
    private Engine engine;

    @Embedded
    private EngineState engineState;

    @Builder
    EngineTask(final Engine engine,
               final EngineState engineState) {
        this.engine = engine;
        this.engineState = engineState;
    }

    public boolean isEnd() {
        return engineState.isFinish(getCreatedDate());
    }

    public String getDescription() {
        return engineState.getDescription();
    }

    public String getEngineName() {
        return engine.getName();
    }

    long getTaskRunningTimeMillis() {
        return calculateTaskRunningTime().toMillis();
    }

    public LocalDateTime getEndAt() {
        return engineState.getEndAt();
    }

    public EngineTaskReport report() {
        if (isNotEnd()) {
            throw new EngineTaskRunningException();
        }

        return EngineTaskReport.of(this);
    }

    private boolean isNotEnd() {
        return !isEnd();
    }

    private Duration calculateTaskRunningTime() {
        return Duration.between(getCreatedDate(), engineState.getEndAt());
    }
}

package com.jaeyeonling.tiny.domain.engine;

import com.jaeyeonling.tiny.domain.engine.command.EngineCommand;
import com.jaeyeonling.tiny.domain.engine.task.EngineTask;
import com.jaeyeonling.tiny.domain.engine.task.EngineTaskOption;
import com.jaeyeonling.tiny.global.domain.DateAuditEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Table(uniqueConstraints = {
        @UniqueConstraint(columnNames = {"name"})
})
@Entity
@DynamicUpdate
@DynamicInsert
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Engine extends DateAuditEntity {

    @Getter
    @Column(nullable = false, length = 100)
    private String name;

    @Builder
    public Engine(final String name) {
        this.name = name;
    }

    public EngineTask run(final EngineCommand command,
                          final EngineTaskOption engineTaskOption) {
        return EngineTask.builder()
                .engine(this)
                .engineState(command.execute(this, engineTaskOption))
                .build();
    }
}

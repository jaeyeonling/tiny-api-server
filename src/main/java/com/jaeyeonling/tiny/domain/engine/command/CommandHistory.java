package com.jaeyeonling.tiny.domain.engine.command;

import com.jaeyeonling.tiny.domain.engine.task.EngineTask;
import com.jaeyeonling.tiny.global.domain.DateAuditEntity;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Table
@Entity
@DynamicUpdate
@DynamicInsert
@NoArgsConstructor(access = AccessLevel.PROTECTED)
class CommandHistory extends DateAuditEntity {

    @ManyToOne(fetch = FetchType.EAGER)
    private EngineTask target;

    private CommandHistory(final EngineTask target) {
        this.target = target;
    }

    static CommandHistory of(final EngineTask engineTask) {
        return new CommandHistory(engineTask);
    }

    String getEngineDescription() {
        return target.getDescription();
    }

    String getEngineName() {
        return target.getEngineName();
    }
}


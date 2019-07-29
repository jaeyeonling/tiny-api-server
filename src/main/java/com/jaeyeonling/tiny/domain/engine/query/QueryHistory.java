package com.jaeyeonling.tiny.domain.engine.query;

import com.jaeyeonling.tiny.domain.engine.task.EngineTask;
import com.jaeyeonling.tiny.domain.engine.task.EngineTaskReport;
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
public class QueryHistory extends DateAuditEntity {

    @ManyToOne(fetch = FetchType.EAGER)
    private EngineTask target;

    private QueryHistory(final EngineTask target) {
        this.target = target;
    }

    static QueryHistory of(final EngineTask engineTask) {
        return new QueryHistory(engineTask);
    }

    public EngineTaskReport getReport() {
        return target.report();
    }

    String getEngineDescription() {
        return target.getDescription();
    }

    String getEngineName() {
        return target.getEngineName();
    }
}

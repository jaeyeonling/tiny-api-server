package com.jaeyeonling.tiny.domain.engine.query;

import com.jaeyeonling.tiny.domain.engine.task.EngineTask;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class QueryHistoryService {

    private final QueryHistoryRepository queryHistoryRepository;

    public QueryHistory logging(final EngineTask engineTask) {
        return queryHistoryRepository.save(QueryHistory.of(engineTask));
    }

    public List<QueryHistory> findAll() {
        return queryHistoryRepository.findAll();
    }

    Optional<QueryHistory> findBy(final long id) {
        return queryHistoryRepository.findById(id);
    }
}

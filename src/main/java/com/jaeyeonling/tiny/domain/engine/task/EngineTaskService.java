package com.jaeyeonling.tiny.domain.engine.task;

import com.jaeyeonling.tiny.domain.engine.command.CommandHistoryService;
import com.jaeyeonling.tiny.domain.engine.query.QueryHistoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EngineTaskService {

    private final EngineTaskRepository engineTaskRepository;
    private final CommandHistoryService commandHistoryService;
    private final QueryHistoryService queryHistoryService;

    public EngineTask save(final EngineTask engineTask) {
        final var saveEngineTask = engineTaskRepository.save(engineTask);
        commandHistoryService.logging(engineTask);

        return saveEngineTask;
    }

    List<EngineTask> findAll() {
        return engineTaskRepository.findAll();
    }

    Optional<EngineTask> findById(final long id) {
        return engineTaskRepository.findById(id);
    }

    EngineTaskReport report(final long id) {
        final var task = findById(id).orElseThrow(() -> new EngineTaskNotFoundException(id));
        final var queryHistory = queryHistoryService.logging(task);

        return queryHistory.getReport();
    }
}

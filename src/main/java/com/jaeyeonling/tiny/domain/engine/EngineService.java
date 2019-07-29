package com.jaeyeonling.tiny.domain.engine;

import com.jaeyeonling.tiny.domain.engine.command.EngineCommand;
import com.jaeyeonling.tiny.domain.engine.task.EngineTask;
import com.jaeyeonling.tiny.domain.engine.task.EngineTaskOption;
import com.jaeyeonling.tiny.domain.engine.task.EngineTaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
class EngineService {

    private final EngineCommand engineCommand;
    private final EngineTaskService engineTaskService;
    private final EngineRepository engineRepository;

    Engine save(final Engine engine) {
        return engineRepository.save(engine);
    }

    Optional<Engine> findById(final Long id) {
        return engineRepository.findById(id);
    }

    List<Engine> findAll() {
        return engineRepository.findAll();
    }

    Optional<EngineTask> run(final Long engineId,
                             final EngineTaskOption engineTaskOption) {
        return findById(engineId)
                .map(engine -> engine.run(engineCommand, engineTaskOption))
                .map(engineTaskService::save);
    }
}

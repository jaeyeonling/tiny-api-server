package com.jaeyeonling.tiny.domain.engine.command;

import com.jaeyeonling.tiny.domain.engine.task.EngineTask;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CommandHistoryService {

    private final CommandHistoryRepository commandHistoryRepository;

    public void logging(final EngineTask engineTask) {
        commandHistoryRepository.save(CommandHistory.of(engineTask));
    }

    public List<CommandHistory> findAll() {
        return commandHistoryRepository.findAll();
    }

    Optional<CommandHistory> findBy(final long id) {
        return commandHistoryRepository.findById(id);
    }
}

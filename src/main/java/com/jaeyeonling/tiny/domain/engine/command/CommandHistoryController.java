package com.jaeyeonling.tiny.domain.engine.command;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static java.util.stream.Collectors.toList;

@RestController
@RequestMapping("/engines/{engineId}/tasks/{taskId}/commands")
@RequiredArgsConstructor
public class CommandHistoryController {

    private final CommandHistoryService commandHistoryService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<CommandHistoryResponse> findAll() {
        return commandHistoryService.findAll()
                .stream()
                .map(CommandHistoryResponse::new)
                .collect(toList());
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public CommandHistoryResponse findBy(@PathVariable final long id) {
        return commandHistoryService.findBy(id)
                .map(CommandHistoryResponse::new)
                .orElseThrow(() -> new EngineTaskCommandHistoryNotFoundException(id));
    }
}

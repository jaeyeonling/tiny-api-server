package com.jaeyeonling.tiny.domain.engine.task;

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
@RequestMapping("/engines/{engineId}/tasks")
@RequiredArgsConstructor
public class EngineTaskController {

    private final EngineTaskService engineTaskService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<EngineTaskResponse> findAll() {
        return engineTaskService.findAll()
                .stream()
                .map(EngineTaskResponse::new)
                .collect(toList());
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public EngineTaskResponse findBy(@PathVariable final long id) {
        return engineTaskService.findById(id)
                .map(EngineTaskResponse::new)
                .orElseThrow(() -> new EngineTaskNotFoundException(id));
    }

    @GetMapping("/{id}/reports")
    @ResponseStatus(HttpStatus.OK)
    public EngineTaskReportResponse report(@PathVariable final long id) {
        return new EngineTaskReportResponse(engineTaskService.report(id));
    }
}

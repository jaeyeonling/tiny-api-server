package com.jaeyeonling.tiny.domain.engine.query;

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
@RequestMapping("/engines/{engineId}/tasks/{taskId}/queries")
@RequiredArgsConstructor
public class QueryHistoryController {

    private final QueryHistoryService queryHistoryService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<QueryHistoryResponse> findAll() {
        return queryHistoryService.findAll()
                .stream()
                .map(QueryHistoryResponse::new)
                .collect(toList());
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public QueryHistoryResponse findBy(@PathVariable final long id) {
        return queryHistoryService.findBy(id)
                .map(QueryHistoryResponse::new)
                .orElseThrow(() -> new EngineTaskQueryHistoryNotFoundException(id));
    }
}

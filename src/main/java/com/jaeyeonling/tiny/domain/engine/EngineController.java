package com.jaeyeonling.tiny.domain.engine;

import com.jaeyeonling.tiny.domain.engine.task.EngineTaskOption;
import com.jaeyeonling.tiny.domain.engine.task.EngineTaskResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

import static java.util.stream.Collectors.toList;

@RequestMapping("/engines")
@RestController
@RequiredArgsConstructor
public class EngineController {

    private final EngineService engineService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<EngineResponse> findAll() {
        return engineService.findAll()
                .stream()
                .map(EngineResponse::new)
                .collect(toList());
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public EngineResponse findBy(@PathVariable final Long id) {
        return engineService.findById(id)
                .map(EngineResponse::new)
                .orElseThrow(() -> new EngineNotFoundException(id));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public EngineResponse save(@RequestBody @Valid final EngineSaveRequest engineSaveRequest) {
        return new EngineResponse(engineService.save(engineSaveRequest.toEntity()));
    }

    @PostMapping("/{id}/run")
    @ResponseStatus(HttpStatus.CREATED)
    public EngineTaskResponse run(@PathVariable final Long id,
                                  @RequestBody @Valid final EngineTaskOption engineTaskOption) {
        return engineService.run(id, engineTaskOption)
                .map(EngineTaskResponse::new)
                .orElseThrow(() -> new EngineNotFoundException(id));
    }
}

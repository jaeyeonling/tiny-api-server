package com.jaeyeonling.tiny.domain.engine.command;

import com.jaeyeonling.tiny.domain.engine.Engine;
import com.jaeyeonling.tiny.domain.engine.EngineState;
import com.jaeyeonling.tiny.domain.engine.task.EngineTaskOption;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class SimpleEngineCommand implements EngineCommand {

    @Override
    public EngineState execute(final Engine engine,
                               final EngineTaskOption engineTaskOption) {
        if (log.isInfoEnabled()) {
            log.info("로직이 여기서 실행됩니다. [engine={}, options={}]", engine, engineTaskOption);
        }

        return EngineState.of(engineTaskOption);
    }
}

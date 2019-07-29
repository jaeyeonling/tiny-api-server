package com.jaeyeonling.tiny.domain.engine.command;

import com.jaeyeonling.tiny.domain.engine.Engine;
import com.jaeyeonling.tiny.domain.engine.EngineState;
import com.jaeyeonling.tiny.domain.engine.command.EngineCommand;
import com.jaeyeonling.tiny.domain.engine.task.EngineTaskOption;

public class MockEngineCommand implements EngineCommand {

    @Override
    public EngineState execute(final Engine engine,
                               final EngineTaskOption engineTaskOption) {
        return EngineState.of(engineTaskOption);
    }
}

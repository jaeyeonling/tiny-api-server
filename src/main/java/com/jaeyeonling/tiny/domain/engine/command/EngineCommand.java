package com.jaeyeonling.tiny.domain.engine.command;

import com.jaeyeonling.tiny.domain.engine.Engine;
import com.jaeyeonling.tiny.domain.engine.EngineState;
import com.jaeyeonling.tiny.domain.engine.task.EngineTaskOption;

public interface EngineCommand {

    EngineState execute(final Engine engine,
                        final EngineTaskOption engineTaskOption);
}

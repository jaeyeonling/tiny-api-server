package com.jaeyeonling.tiny.domain.engine.command;

import com.jaeyeonling.tiny.global.exception.ErrorStatus;
import com.jaeyeonling.tiny.global.exception.NonFieldPlatformException;

class EngineTaskCommandHistoryNotFoundException extends NonFieldPlatformException {

    private static final String ERROR_MESSAGE = "EngineTaskCommandHistory 를 찾을 수 없습니다. (입력 값: %d)";
    private static final ErrorStatus EXCEPTION_STATUS = ErrorStatus.ENGINE_TASK_COMMAND_HISTORY_NOT_FOUND;

    EngineTaskCommandHistoryNotFoundException(final long input) {
        super(String.format(ERROR_MESSAGE, input), EXCEPTION_STATUS);
    }
}
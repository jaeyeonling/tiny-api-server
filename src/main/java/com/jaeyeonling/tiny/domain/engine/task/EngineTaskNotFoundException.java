package com.jaeyeonling.tiny.domain.engine.task;

import com.jaeyeonling.tiny.global.exception.ErrorStatus;
import com.jaeyeonling.tiny.global.exception.NonFieldPlatformException;

class EngineTaskNotFoundException extends NonFieldPlatformException {

    private static final String ERROR_MESSAGE = "EngineTask 를 찾을 수 없습니다. (입력 값: %d)";
    private static final ErrorStatus EXCEPTION_STATUS = ErrorStatus.ENGINE_TASK_NOT_FOUND;

    EngineTaskNotFoundException(final long input) {
        super(String.format(ERROR_MESSAGE, input), EXCEPTION_STATUS);
    }
}
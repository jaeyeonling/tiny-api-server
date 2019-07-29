package com.jaeyeonling.tiny.domain.engine.task;

import com.jaeyeonling.tiny.global.exception.ErrorStatus;
import com.jaeyeonling.tiny.global.exception.NonFieldPlatformException;

class EngineTaskRunningException extends NonFieldPlatformException {

    private static final String ERROR_MESSAGE = "EngineTask 의 작업이 끝나지 않았습니다.";
    private static final ErrorStatus EXCEPTION_STATUS = ErrorStatus.ENGINE_TASK_NOT_COMPLETE;

    EngineTaskRunningException() {
        super(ERROR_MESSAGE, EXCEPTION_STATUS);
    }
}

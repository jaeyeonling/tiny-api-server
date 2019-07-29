package com.jaeyeonling.tiny.domain.engine;

import com.jaeyeonling.tiny.global.exception.ErrorStatus;
import com.jaeyeonling.tiny.global.exception.NonFieldPlatformException;

class EngineNotFoundException extends NonFieldPlatformException {

    private static final String ERROR_MESSAGE = "Engine 을 찾을 수 없습니다. (입력 값: %d)";
    private static final ErrorStatus EXCEPTION_STATUS = ErrorStatus.ENGINE_NOT_FOUND;

    EngineNotFoundException(final Long input) {
        super(String.format(ERROR_MESSAGE, input), EXCEPTION_STATUS);
    }
}

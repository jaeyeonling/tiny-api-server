package com.jaeyeonling.tiny.global.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorStatus {

    INTERNAL_SERVER_ERROR(-101, HttpStatus.INTERNAL_SERVER_ERROR),
    BAD_REQUEST(-102, HttpStatus.BAD_REQUEST),
    NOT_FOUND(-103, HttpStatus.NOT_FOUND),
    METHOD_ARGUMENT_NOT_VALID(-104, HttpStatus.BAD_REQUEST),

    ENGINE_NOT_FOUND(-201, HttpStatus.NOT_FOUND),

    ENGINE_TASK_NOT_FOUND(-301, HttpStatus.NOT_FOUND),
    ENGINE_TASK_NOT_COMPLETE(-302, HttpStatus.BAD_REQUEST),

    ENGINE_TASK_COMMAND_HISTORY_NOT_FOUND(-401, HttpStatus.NOT_FOUND),

    ENGINE_TASK_QUERY_HISTORY_NOT_FOUND(-501, HttpStatus.NOT_FOUND);

    private final int code;
    private final HttpStatus httpStatus;

    public int getRawHttpStatus() {
        return httpStatus.value();
    }
}

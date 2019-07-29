package com.jaeyeonling.tiny.global.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    private static final ResponseEntity<ErrorResponse> UNCAUGHT_RESPONSE = UncaughtException.getInstance().toResponse();

    @ExceptionHandler(PlatformException.class)
    public ResponseEntity<ErrorResponse> handlePlatformException(final PlatformException e) {
        if (log.isInfoEnabled()) {
            log.info("Exception", e);
        }

        return e.toResponse();
    }

    @ExceptionHandler(Throwable.class)
    public ResponseEntity<ErrorResponse> handleUncaughtException(final Throwable e) {
        if (log.isErrorEnabled()) {
            log.error("UncaughtException ", e);
        }

        return UNCAUGHT_RESPONSE;
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleMethodArgumentNotValidException(
            final MethodArgumentNotValidException e) {
        if (log.isErrorEnabled()) {
            log.error("MethodArgumentNotValidException", e);
        }

        return new RequestInvalidException(e.getBindingResult()).toResponse();
    }
}

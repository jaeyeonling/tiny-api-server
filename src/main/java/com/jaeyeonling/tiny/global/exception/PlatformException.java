package com.jaeyeonling.tiny.global.exception;

import lombok.Getter;
import org.springframework.http.ResponseEntity;

import java.util.List;

@Getter
public abstract class PlatformException extends RuntimeException {

    private final ErrorStatus errorStatus;

    PlatformException(final String message,
                      final ErrorStatus errorStatus) {
        super(message);

        this.errorStatus = errorStatus;
    }

    PlatformException(final ErrorStatus errorStatus) {
        this(errorStatus.name(), errorStatus);
    }

    abstract List<FieldError> fieldErrors();

    ResponseEntity<ErrorResponse> toResponse() {
        return ResponseEntity.status(errorStatus.getHttpStatus())
                .body(toBody());
    }

    ErrorResponse toBody() {
        return ErrorResponse.builder()
                .code(errorStatus.getCode())
                .message(getMessage())
                .fieldErrors(fieldErrors())
                .build();
    }

    int getRawHttpStatus() {
        return errorStatus.getRawHttpStatus();
    }
}

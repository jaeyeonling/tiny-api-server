package com.jaeyeonling.tiny.global.exception;

import lombok.Getter;

import java.util.Optional;

@Getter
class FieldError {

    private static final String BLANK = "";

    private final String field;
    private final String value;
    private final String reason;

    FieldError(final org.springframework.validation.FieldError fieldError) {
        this.field = fieldError.getField();
        this.value = parseValue(fieldError);
        this.reason = fieldError.getDefaultMessage();
    }

    private String parseValue(final org.springframework.validation.FieldError fieldError) {
        return Optional.ofNullable(fieldError.getRejectedValue())
                .map(Object::toString)
                .orElse(BLANK);
    }
}

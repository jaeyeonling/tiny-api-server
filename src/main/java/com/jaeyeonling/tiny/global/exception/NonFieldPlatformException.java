package com.jaeyeonling.tiny.global.exception;

import java.util.List;

import static java.util.Collections.emptyList;

public abstract class NonFieldPlatformException extends PlatformException {

    public NonFieldPlatformException(final ErrorStatus errorStatus) {
        super(errorStatus);
    }

    public NonFieldPlatformException(final String message,
                                     final ErrorStatus errorStatus) {
        super(message, errorStatus);
    }

    @Override
    List<FieldError> fieldErrors() {
        return emptyList();
    }
}

package com.jaeyeonling.tiny.global.exception;

import org.springframework.validation.BindingResult;

import java.util.Collections;
import java.util.List;

import static java.util.stream.Collectors.toList;

class RequestInvalidException extends PlatformException {

    private final List<FieldError> fieldErrors;

    RequestInvalidException(final BindingResult bindingResult) {
        super(ErrorStatus.METHOD_ARGUMENT_NOT_VALID);

        fieldErrors = bindingResult.getFieldErrors()
                .stream()
                .map(FieldError::new)
                .collect(toList());
    }

    @Override
    List<FieldError> fieldErrors() {
        return Collections.unmodifiableList(fieldErrors);
    }
}

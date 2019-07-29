package com.jaeyeonling.tiny.domain.notfound;

import com.jaeyeonling.tiny.global.exception.ErrorStatus;
import com.jaeyeonling.tiny.global.exception.NonFieldPlatformException;

class NotFoundException extends NonFieldPlatformException {

    private static final ErrorStatus EXCEPTION_STATUS = ErrorStatus.NOT_FOUND;

    NotFoundException() {
        super(EXCEPTION_STATUS);
    }
}

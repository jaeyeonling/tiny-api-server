package com.jaeyeonling.tiny.global.exception;

class UncaughtException extends NonFieldPlatformException {

    private static final ErrorStatus ERROR_STATUS = ErrorStatus.INTERNAL_SERVER_ERROR;

    private UncaughtException() {
        super(ERROR_STATUS);
    }

    static UncaughtException getInstance() {
        return LazyHolder.INSTANCE;
    }

    private static class LazyHolder {
        private static final UncaughtException INSTANCE = new UncaughtException();
    }
}

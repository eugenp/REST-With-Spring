package com.baeldung.common.web.exception;

public final class MyConflictException extends RuntimeException {

    public MyConflictException() {
        super();
    }

    public MyConflictException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public MyConflictException(final String message) {
        super(message);
    }

    public MyConflictException(final Throwable cause) {
        super(cause);
    }

}

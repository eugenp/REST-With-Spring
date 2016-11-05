package com.baeldung.common.web.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Thrown when user is forbidden to execute specified operation or access specified data.
 */
@ResponseStatus(value = HttpStatus.FORBIDDEN)
public class MyForbiddenException extends RuntimeException {

    public MyForbiddenException() {
        super();
    }

    public MyForbiddenException(final String message) {
        super(message);
    }

    public MyForbiddenException(final Throwable cause) {
        super(cause);
    }

}
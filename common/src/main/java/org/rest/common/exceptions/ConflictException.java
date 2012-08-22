package org.rest.common.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT)
public final class ConflictException extends RuntimeException {

    public ConflictException() {
        super();
    }

    public ConflictException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public ConflictException(final String message) {
        super(message);
    }

    public ConflictException(final Throwable cause) {
        super(cause);
    }

}

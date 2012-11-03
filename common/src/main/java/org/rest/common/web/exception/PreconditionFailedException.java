package org.rest.common.web.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.PRECONDITION_FAILED)
public final class PreconditionFailedException extends RuntimeException {

    public PreconditionFailedException() {
        super();
    }

    public PreconditionFailedException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public PreconditionFailedException(final String message) {
        super(message);
    }

    public PreconditionFailedException(final Throwable cause) {
        super(cause);
    }

}

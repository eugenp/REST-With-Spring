package org.baeldung.common.web.exceptions;

/**
 * Thrown when validation conflict error is found. Message used to describe the validation error.
 */
public class ValidationException extends RuntimeException {

    public ValidationException(final String message) {
        super(message);
    }

}

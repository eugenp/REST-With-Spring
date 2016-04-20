package org.baeldung.common.web;

import org.baeldung.common.web.exception.MyBadRequestException;
import org.baeldung.common.web.exception.MyConflictException;
import org.baeldung.common.web.exception.MyForbiddenException;
import org.baeldung.common.web.exception.MyResourceNotFoundException;
import org.springframework.http.HttpStatus;

/**
 * Simple static methods to be called at the start of your own methods to verify
 * correct arguments and state. If the Precondition fails, an {@link HttpStatus}
 * code is thrown
 */
public final class RestPreconditions {

    private RestPreconditions() {
        throw new AssertionError();
    }

    // API

    /**
     * Ensures that an object reference passed as a parameter to the calling method is not null.
     *
     * @param reference
     *            an object reference
     *
     * @return the non-null reference that was validated
     *
     * @throws MyResourceNotFoundException
     *             if {@code reference} is null
     */
    public static <T> T checkNotNull(final T reference) {
        return checkNotNull(reference, null);
    }

    /**
     * Ensures that an object reference passed as a parameter to the calling method is not null.
     *
     * @param reference
     *            an object reference
     * @param message
     *            the message of the exception if the check fails
     *
     * @return the non-null reference that was validated
     *
     * @throws MyResourceNotFoundException
     *             if {@code reference} is null
     */
    public static <T> T checkNotNull(final T reference, final String message) {
        if (reference == null) {
            throw new MyResourceNotFoundException(message);
        }
        return reference;
    }

    /**
     * Ensures that an object reference passed as a parameter to the calling
     * method is not null.
     *
     * @param reference
     *            an object reference
     * @return the non-null reference that was validated
     *
     * @throws MyBadRequestException
     *             if {@code reference} is null
     */
    public static <T> T checkRequestElementNotNull(final T reference) {
        return checkRequestElementNotNull(reference, null);
    }

    /**
     * Ensures that an object reference passed as a parameter to the calling method is not null.
     *
     * @param reference
     *            an object reference
     * @param message
     *            the message of the exception if the check fails
     *
     * @return the non-null reference that was validated
     *
     * @throws MyBadRequestException
     *             if {@code reference} is null
     */
    public static <T> T checkRequestElementNotNull(final T reference, final String message) {
        if (reference == null) {
            throw new MyBadRequestException(message);
        }
        return reference;
    }

    /**
     * Ensures the truth of an expression
     *
     * @param expression
     *            a boolean expression
     *
     * @throws MyConflictException
     *             if {@code expression} is false
     */
    public static void checkRequestState(final boolean expression) {
        checkRequestState(expression, null);
    }

    /**
     * Ensures the truth of an expression
     *
     * @param expression
     *            a boolean expression
     * @param message
     *            the message of the exception if the check fails
     *
     * @throws MyConflictException
     *             if {@code expression} is false
     */
    public static void checkRequestState(final boolean expression, final String message) {
        if (!expression) {
            throw new MyConflictException(message);
        }
    }

    /**
     * Ensures the truth of an expression related to the validity of the request
     *
     * @param expression
     *            a boolean expression
     *
     * @throws MyBadRequestException
     *             if {@code expression} is false
     */
    public static void checkIfBadRequest(final boolean expression) {
        checkIfBadRequest(expression, null);
    }

    /**
     * Ensures the truth of an expression related to the validity of the request
     *
     * @param expression
     *            a boolean expression
     * @param message
     *            the message of the exception if the check fails
     *
     * @throws MyBadRequestException
     *             if {@code expression} is false
     */
    public static void checkIfBadRequest(final boolean expression, final String message) {
        if (!expression) {
            throw new MyBadRequestException(message);
        }
    }

    /**
     * Check if some value was found, otherwise throw exception.
     *
     * @param expression
     *            has value true if found, otherwise false
     *
     * @throws MyResourceNotFoundException
     *             if expression is false, means value not found.
     */
    public static void checkFound(final boolean expression) {
        checkFound(expression, null);
    }

    /**
     * Check if some value was found, otherwise throw exception.
     *
     * @param expression
     *            has value true if found, otherwise false
     * @param message
     *            the message of the exception if the check fails
     *
     * @throws MyResourceNotFoundException
     *             if expression is false, means value not found.
     */
    public static void checkFound(final boolean expression, final String message) {
        if (!expression) {
            throw new MyResourceNotFoundException(message);
        }
    }

    /**
     * Check if some value was found, otherwise throw exception.
     *
     * @param expression
     *            has value true if found, otherwise false
     *
     * @throws MyResourceNotFoundException
     *             if expression is false, means value not found.
     */
    public static <T> T checkFound(final T resource) {
        return checkFound(resource, null);
    }

    /**
     * Check if some value was found, otherwise throw exception.
     *
     * @param expression
     *            has value true if found, otherwise false
     * @param message
     *            the message of the exception if the check fails
     *
     * @throws MyResourceNotFoundException
     *             if expression is false, means value not found.
     */
    public static <T> T checkFound(final T resource, final String message) {
        if (resource == null) {
            throw new MyResourceNotFoundException(message);
        }

        return resource;
    }

    /**
     * Check if some value was found, otherwise throw exception.
     *
     * @param expression
     *            has value true if found, otherwise false
     *
     * @throws MyForbiddenException
     *             if expression is false, means operation not allowed.
     */
    public static void checkAllowed(final boolean expression) {
        checkAllowed(expression, null);
    }

    /**
     * Check if some value was found, otherwise throw exception.
     *
     * @param expression
     *            has value true if found, otherwise false
     * @param message
     *            the message of the exception if the check fails
     *
     * @throws MyForbiddenException
     *             if expression is false, means operation not allowed.
     */
    public static void checkAllowed(final boolean expression, final String message) {
        if (!expression) {
            throw new MyForbiddenException(message);
        }
    }

}

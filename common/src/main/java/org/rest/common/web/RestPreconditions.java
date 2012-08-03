package org.rest.common.web;

import org.rest.common.exceptions.ConflictException;
import org.rest.common.exceptions.ResourceNotFoundException;

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
     * @return the non-null reference that was validated
     * @throws ResourceNotFoundException
     *             if {@code reference} is null
     */
    public static <T> T checkNotNull(final T reference) {
        if (reference == null) {
            throw new ResourceNotFoundException();
        }
        return reference;
    }

    /**
     * Ensures that an object reference passed as a parameter to the calling method is not null.
     * 
     * @param reference
     *            an object reference
     * @return the non-null reference that was validated
     * @throws ConflictException
     *             if {@code reference} is null
     */
    public static <T> T checkRequestElementNotNull(final T reference) {
        if (reference == null) {
            throw new ConflictException();
        }
        return reference;
    }

    public static void checkRequestState(final boolean expression) {
        if (!expression) {
            throw new ConflictException();
        }
    }

}

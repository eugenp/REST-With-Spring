package com.baeldung.common.web.exception;

import java.util.ArrayList;
import java.util.List;

public class ValidationErrorDTO {

    private final List<FieldErrorDTO> fieldErrors = new ArrayList<FieldErrorDTO>();

    public ValidationErrorDTO() {
        super();
    }

    //

    public final void addFieldError(final String path, final String message) {
        final FieldErrorDTO error = new FieldErrorDTO(path, message);
        fieldErrors.add(error);
    }

    public final List<FieldErrorDTO> getFieldErrors() {
        return fieldErrors;
    }

    //

    @Override
    public final String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append("ValidationErrorDTO [fieldErrors=").append(fieldErrors).append("]");
        return builder.toString();
    }

}
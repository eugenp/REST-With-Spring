package com.baeldung.rwsb.web.error;

import java.net.URI;

import org.hibernate.TransientObjectException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.ErrorResponse;
import org.springframework.web.ErrorResponseException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import jakarta.annotation.Nullable;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@ControllerAdvice
public class CustomExceptionsHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler({ EntityNotFoundException.class, TransientObjectException.class })
    public ProblemDetail resolveEntityNotFoundException2(Exception ex, ServletRequest request, HttpServletResponse response) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, "Invalid associated entity: " + ex.getMessage());
        problemDetail.setType(URI.create("https://example.com/errors/invalid-associated-entity"));
        problemDetail.setTitle("Invalid associated entity");
        return problemDetail;
    }

    @ExceptionHandler({ DataIntegrityViolationException.class })
    public ErrorResponse resolveDuplicatedKey(DataIntegrityViolationException ex) {
        ErrorResponseException response = new ErrorResponseException(HttpStatus.BAD_REQUEST);
        response.setDetail("Duplicated key:" + ex.getMessage());
        response.setType(URI.create("https://example.com/errors/duplicated-key"));
        response.setTitle("Duplicated key");
        response.getHeaders()
            .add("Custom-Header", "Value");
        return response;
    }

    @Override
    protected ResponseEntity<Object> handleExceptionInternal(Exception ex, @Nullable Object body, HttpHeaders headers, HttpStatusCode statusCode, WebRequest request) {
        ResponseEntity<Object> response = super.handleExceptionInternal(ex, body, headers, statusCode, request);

        if (response.getBody() instanceof ProblemDetail problemDetailBody) {
            problemDetailBody.setProperty("message", ex.getMessage());
            if (ex instanceof MethodArgumentNotValidException subEx) {
                BindingResult result = subEx.getBindingResult();
                problemDetailBody.setProperty("message", "Validation failed for object='" + result.getObjectName() + "'. " + "Error count: " + result.getErrorCount());
                problemDetailBody.setProperty("errors", result.getAllErrors());
            }
        }
        return response;
    }
}

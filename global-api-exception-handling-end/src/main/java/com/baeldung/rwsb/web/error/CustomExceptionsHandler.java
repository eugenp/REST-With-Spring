package com.baeldung.rwsb.web.error;

import org.hibernate.TransientObjectException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@ControllerAdvice
public class CustomExceptionsHandler {

    // @ExceptionHandler({ EntityNotFoundException.class })
    // public ModelAndView resolveEntityNotFoundException(JpaObjectRetrievalFailureException ex, ServletRequest request, HttpServletResponse response) {
    // request.setAttribute(RequestDispatcher.ERROR_STATUS_CODE, HttpStatus.BAD_REQUEST.value());
    // request.setAttribute(RequestDispatcher.ERROR_MESSAGE, "Associated entity not found: " + ex.getMessage());
    // ModelAndView mav = new ModelAndView();
    // mav.setViewName("/error");
    // return mav;
    // }

    // @ResponseBody
    // @ExceptionHandler({ EntityNotFoundException.class, TransientObjectException.class })
    // public CustomErrorBody resolveEntityNotFoundException(Exception ex, ServletRequest request, HttpServletResponse response) {
    // response.setStatus(HttpStatus.BAD_REQUEST.value());
    // return new CustomErrorBody("Invalid associated entity: " + ex.getMessage(), "INVALID_CAMPAIGN_ID");
    // }

    // @ExceptionHandler({ EntityNotFoundException.class, TransientObjectException.class })
    // public ResponseEntity<CustomErrorBody> resolveEntityNotFoundException(Exception ex) {
    // return ResponseEntity.badRequest()
    // .header("Custom-Header", "Value")
    // .body(new CustomErrorBody("Invalid associated entity: " + ex.getMessage(), "INVALID_CAMPAIGN_ID"));
    // }

    @ExceptionHandler({ EntityNotFoundException.class, TransientObjectException.class })
    public ModelAndView resolveEntityNotFoundException(Exception ex, ServletRequest request, HttpServletResponse response) {
        request.setAttribute(RequestDispatcher.ERROR_STATUS_CODE, HttpStatus.BAD_REQUEST.value());
        request.setAttribute(RequestDispatcher.ERROR_MESSAGE, "Invalid associated entity: " + ex.getMessage());
        ModelAndView mav = new ModelAndView();
        mav.setViewName("/error");
        return mav;
    }

    @ExceptionHandler({ DataIntegrityViolationException.class })
    public String resolveDuplicatedKey(ServletRequest request) {
        request.setAttribute(RequestDispatcher.ERROR_STATUS_CODE, HttpStatus.BAD_REQUEST.value());
        request.setAttribute(RequestDispatcher.ERROR_MESSAGE, "Duplicated key");
        return "/error";
    }

}

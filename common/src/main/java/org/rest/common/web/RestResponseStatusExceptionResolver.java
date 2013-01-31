package org.rest.common.web;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.AbstractHandlerExceptionResolver;

@Component
public class RestResponseStatusExceptionResolver extends AbstractHandlerExceptionResolver {

    // TODO: Order should be set in a @Configuration rather then hard-coded. It is done here due to POC style of implementation.
    public RestResponseStatusExceptionResolver() {
        setOrder(-99);
    }

    // API

    @Override
    protected ModelAndView doResolveException(final HttpServletRequest request, final HttpServletResponse response, final Object handler, final Exception ex) {
        try {
            if (ex instanceof IllegalArgumentException) {
                return handleIllegalArgument((IllegalArgumentException) ex, response, handler);
            } else if (ex instanceof IllegalStateException) {
                return handleIllegalState((IllegalStateException) ex, response, handler);
            } else if (ex instanceof InvalidDataAccessApiUsageException) {
                return handleInvalidDataAccessApiUsage((InvalidDataAccessApiUsageException) ex, response, handler);
            } else if (ex instanceof DataIntegrityViolationException) {
                return handleDataIntegrityViolation((DataIntegrityViolationException) ex, response, handler);
            } else if (ex instanceof DataAccessException) {
                return handleDataAccessException((DataAccessException) ex, response, handler);
            }
        } catch (final Exception handlerException) {
            logger.warn("Handling of [" + ex.getClass().getName() + "] resulted in Exception", handlerException);
        }
        return null;
    }

    private ModelAndView handleIllegalArgument(final IllegalArgumentException ex, final HttpServletResponse response, final Object handler) throws IOException {
        logException(handler, ex);
        response.sendError(HttpServletResponse.SC_CONFLICT);
        return new ModelAndView();
    }

    private ModelAndView handleIllegalState(final IllegalStateException ex, final HttpServletResponse response, final Object handler) throws IOException {
        logException(handler, ex);
        response.sendError(HttpServletResponse.SC_CONFLICT);
        return new ModelAndView();

        // XXX: In deleteByIdInternal SC_NOT_FOUND was used
    }

    private ModelAndView handleInvalidDataAccessApiUsage(final InvalidDataAccessApiUsageException ex, final HttpServletResponse response, final Object handler) throws IOException {
        logException(handler, ex);
        response.sendError(HttpServletResponse.SC_CONFLICT);
        return new ModelAndView();

        // XXX: In findPaginatedInternal SC_BAD_REQUEST was used
        // XXX: In deleteByIdInternal and countInternal SC_NOT_FOUND was used
    }

    private ModelAndView handleDataIntegrityViolation(final DataIntegrityViolationException ex, final HttpServletResponse response, final Object handler) throws IOException {
        logException(handler, ex);
        response.sendError(HttpServletResponse.SC_CONFLICT);
        return new ModelAndView();
    }

    private ModelAndView handleDataAccessException(final DataAccessException ex, final HttpServletResponse response, final Object handler) throws IOException {
        logException(handler, ex);
        response.sendError(HttpServletResponse.SC_CONFLICT);
        return new ModelAndView();

        // XXX: For deleteByIdInternal the exception wasn't mapped at all
    }

    protected void logException(final Object handler, final Exception ex) {
        final String message = buildEntityOperationLogMessage(handler, ex);

        logger.error(message);
        logger.warn(message, ex);
    }

    protected String buildEntityOperationLogMessage(final Object handler, final Exception ex) {
        final StringBuilder builder = new StringBuilder();

        builder.append(ex.getClass().getSimpleName()).append(" occured");

        if (handler != null && handler instanceof HandlerMethod) {
            final HandlerMethod handlerMethod = (HandlerMethod) handler;

            //@formatter:off
            builder.
            append(" in ").
            append(handlerMethod.getBeanType().getSimpleName()).
            append(".").
            append(handlerMethod.getMethod().getName());
            //@formatter:on
        }
        return builder.toString();
    }

}

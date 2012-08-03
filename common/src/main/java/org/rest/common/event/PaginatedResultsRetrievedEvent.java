package org.rest.common.event;

import java.io.Serializable;

import javax.servlet.http.HttpServletResponse;

import org.springframework.context.ApplicationEvent;
import org.springframework.web.util.UriComponentsBuilder;

public final class PaginatedResultsRetrievedEvent<T extends Serializable> extends ApplicationEvent {
    private final UriComponentsBuilder uriBuilder;
    private final HttpServletResponse response;
    private final int page;
    private final int totalPages;
    private final int pageSize;

    public PaginatedResultsRetrievedEvent(final Class<T> clazz, final UriComponentsBuilder uriBuilderToSet, final HttpServletResponse responseToSet, final int pageToSet, final int totalPagesToSet, final int pageSizeToSet) {
        super(clazz);

        uriBuilder = uriBuilderToSet;
        response = responseToSet;
        page = pageToSet;
        totalPages = totalPagesToSet;
        pageSize = pageSizeToSet;
    }

    // API

    public final UriComponentsBuilder getUriBuilder() {
        return uriBuilder;
    }

    public final HttpServletResponse getResponse() {
        return response;
    }

    public final int getPage() {
        return page;
    }

    public final int getTotalPages() {
        return totalPages;
    }

    public final int getPageSize() {
        return pageSize;
    }

    @SuppressWarnings("unchecked")
    public final Class<T> getClazz() {
        return (Class<T>) getSource();
    }

}

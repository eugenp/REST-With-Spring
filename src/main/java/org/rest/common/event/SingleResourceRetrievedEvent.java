package org.rest.common.event;

import java.io.Serializable;

import javax.servlet.http.HttpServletResponse;

import org.springframework.context.ApplicationEvent;
import org.springframework.web.util.UriComponentsBuilder;

public final class SingleResourceRetrievedEvent<T extends Serializable> extends ApplicationEvent {
    private final UriComponentsBuilder uriBuilder;
    private final HttpServletResponse response;

    public SingleResourceRetrievedEvent(final Class<T> clazz, final UriComponentsBuilder uriBuilderToSet, final HttpServletResponse responseToSet) {
	super(clazz);

	this.uriBuilder = uriBuilderToSet;
	this.response = responseToSet;
    }

    //

    public final UriComponentsBuilder getUriBuilder() {
	return this.uriBuilder;
    }

    public final HttpServletResponse getResponse() {
	return this.response;
    }

    @SuppressWarnings("unchecked")
    public final Class<T> getClazz() {
	return (Class<T>) this.getSource();
    }

}

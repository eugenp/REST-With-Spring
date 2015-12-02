package org.baeldung.common.web.events;

import javax.servlet.http.HttpServletResponse;

import org.baeldung.common.interfaces.IDto;
import org.springframework.context.ApplicationEvent;
import org.springframework.web.util.UriComponentsBuilder;

/**
 * Event that is fired when a single resource was retrieved.
 * <p/>
 * This event object contains all the information needed to create the URL for direct access to the resource
 *
 * @param <D>
 *            Type of the result that is being handled (commonly Dtos).
 */
public final class SingleResourceRetrievedEvent<D extends IDto> extends ApplicationEvent {
    private final UriComponentsBuilder uriBuilder;
    private final HttpServletResponse response;

    public SingleResourceRetrievedEvent(final Class<D> clazz, final UriComponentsBuilder uriBuilderToSet, final HttpServletResponse responseToSet) {
        super(clazz);

        uriBuilder = uriBuilderToSet;
        response = responseToSet;
    }

    //

    public final UriComponentsBuilder getUriBuilder() {
        return uriBuilder;
    }

    public final HttpServletResponse getResponse() {
        return response;
    }

    /**
     * The object on which the Event initially occurred.
     *
     * @return The object on which the Event initially occurred.
     */
    @SuppressWarnings("unchecked")
    public final Class<D> getClazz() {
        return (Class<D>) getSource();
    }

}

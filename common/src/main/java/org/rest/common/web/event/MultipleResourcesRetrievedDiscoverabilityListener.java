package org.rest.common.web.event;

import static org.rest.common.web.WebConstants.PATH_SEP;

import javax.servlet.http.HttpServletResponse;

import org.rest.common.event.MultipleResourcesRetrievedEvent;
import org.rest.common.util.LinkUtil;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import com.google.common.base.Preconditions;
import com.google.common.net.HttpHeaders;

@SuppressWarnings("rawtypes")
@Component
final class MultipleResourcesRetrievedDiscoverabilityListener implements ApplicationListener<MultipleResourcesRetrievedEvent> {

    public MultipleResourcesRetrievedDiscoverabilityListener() {
        super();
    }

    //

    @Override
    public final void onApplicationEvent(final MultipleResourcesRetrievedEvent ev) {
        Preconditions.checkNotNull(ev);

        discoverOtherRetrievalOperations(ev.getUriBuilder(), ev.getResponse(), ev.getClazz());
    }

    /**
     * - note: at this point, the URI is transformed into plural (added `s`) in a hardcoded way - this will change in the future
     */
    final void discoverOtherRetrievalOperations(final UriComponentsBuilder uriBuilder, final HttpServletResponse response, final Class clazz) {
        final String resourceName = clazz.getSimpleName().toString().toLowerCase();
        final String uriForResourceCreation = uriBuilder.path(PATH_SEP + resourceName + "s").build().encode().toUriString();

        final String linkHeaderValue = LinkUtil.createLinkHeader(uriForResourceCreation, LinkUtil.REL_COLLECTION);
        response.addHeader(HttpHeaders.LINK, linkHeaderValue);
    }

}

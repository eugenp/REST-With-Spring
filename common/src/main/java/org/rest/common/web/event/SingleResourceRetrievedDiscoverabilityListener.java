package org.rest.common.web.event;

import static org.rest.common.web.WebConstants.PATH_SEP;

import javax.servlet.http.HttpServletResponse;

import org.rest.common.event.SingleResourceRetrievedEvent;
import org.rest.common.util.LinkUtil;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import com.google.common.base.Preconditions;
import com.google.common.net.HttpHeaders;

@SuppressWarnings("rawtypes")
@Component
final class SingleResourceRetrievedDiscoverabilityListener implements ApplicationListener<SingleResourceRetrievedEvent> {

    public SingleResourceRetrievedDiscoverabilityListener() {
        super();
    }

    //

    @Override
    public final void onApplicationEvent(final SingleResourceRetrievedEvent ev) {
        Preconditions.checkNotNull(ev);

        discoverGetAllURI(ev.getUriBuilder(), ev.getResponse(), ev.getClazz());
    }

    final void discoverGetAllURI(final UriComponentsBuilder uriBuilder, final HttpServletResponse response, final Class clazz) {
        final String resourceName = clazz.getSimpleName().toString().toLowerCase();
        final String uriForResourceCreation = uriBuilder.path(PATH_SEP + resourceName).build().encode().toUriString();

        final String linkHeaderValue = LinkUtil.createLinkHeader(uriForResourceCreation, LinkUtil.REL_COLLECTION);
        response.addHeader(HttpHeaders.LINK, linkHeaderValue);
    }

}

package org.rest.web.controller.discovery;

import java.net.URI;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.rest.common.util.LinkUtil;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.util.UriTemplate;

import com.google.common.net.HttpHeaders;

@Controller
final class DiscoverabilityController {

    // API

    @RequestMapping(value = "/", method = RequestMethod.GET)
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public final void adminRoot(final HttpServletRequest request, final HttpServletResponse response) {
        final String rootUri = request.getRequestURL().toString();

        // TODO: add these in a more dynamic way, not hardcoded
        final URI uri = new UriTemplate("{rootUri}{resource}").expand(rootUri, "user");
        final String linkToEntity = LinkUtil.createLinkHeader(uri.toASCIIString(), LinkUtil.REL_COLLECTION);
        final String linkToTest = LinkUtil.createLinkHeader("test", LinkUtil.REL_COLLECTION);

        response.addHeader(HttpHeaders.LINK, LinkUtil.gatherLinkHeaders(linkToEntity, linkToTest));
    }

}

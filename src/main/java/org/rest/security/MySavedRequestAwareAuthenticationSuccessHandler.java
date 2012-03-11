package org.rest.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.util.StringUtils;

public class MySavedRequestAwareAuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
    private RequestCache requestCache = new HttpSessionRequestCache();

    public MySavedRequestAwareAuthenticationSuccessHandler() {
	super();
    }

    @Override
    public void onAuthenticationSuccess(final HttpServletRequest request, final HttpServletResponse response, final Authentication authentication) throws ServletException, IOException {
	final SavedRequest savedRequest = this.requestCache.getRequest(request, response);

	if (savedRequest == null) {
	    super.onAuthenticationSuccess(request, response, authentication);

	    return;
	}
	final String targetUrlParameter = this.getTargetUrlParameter();
	if (this.isAlwaysUseDefaultTargetUrl() || (targetUrlParameter != null && StringUtils.hasText(request.getParameter(targetUrlParameter)))) {
	    this.requestCache.removeRequest(request, response);
	    super.onAuthenticationSuccess(request, response, authentication);

	    return;
	}

	this.clearAuthenticationAttributes(request);

	// Use the DefaultSavedRequest URL
	// final String targetUrl = savedRequest.getRedirectUrl();
	// this.logger.debug( "Redirecting to DefaultSavedRequest Url: " + targetUrl );
	// this.getRedirectStrategy().sendRedirect( request, response, targetUrl );
    }

    public void setRequestCache(final RequestCache requestCacheToSet) {
	this.requestCache = requestCacheToSet;
    }

}

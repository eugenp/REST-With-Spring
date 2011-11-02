package org.rest.common.event;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import com.google.common.base.Preconditions;

@Component
public final class AddLinkHeaderListener implements ApplicationListener< EntityCreated >{
	
	@Override
	public final void onApplicationEvent( final EntityCreated entityCreatedEvent ){
		Preconditions.checkNotNull( entityCreatedEvent );
		
		final HttpServletRequest request = entityCreatedEvent.getRequest();
		final HttpServletResponse response = entityCreatedEvent.getResponse();
		final long idOfNewEntity = entityCreatedEvent.getIdOfNewEntity();
		
		this.addLinkHeaderOnEntityCreation( request, response, idOfNewEntity );
	}
	
	@SuppressWarnings( "unused" )
	final void addLinkHeaderOnEntityCreation( final HttpServletRequest request, final HttpServletResponse response, final long idOfNewEntity ){
		final String contextPath = request.getContextPath(); // /rest
		final String servletPath = request.getServletPath(); // /api
		final String pathInfo = request.getPathInfo(); // /admin/foo
		
		final String requestURI = request.getRequestURI(); // /rest/api/admin/foo
		final StringBuffer requestURL = request.getRequestURL(); // http://localhost:8080/rest/api/admin/foo
		
		response.addHeader( "Link", requestURL + "/" + idOfNewEntity );
	}
	
}

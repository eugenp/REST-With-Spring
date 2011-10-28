package org.rest.security;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

/**
 * Used to initiate authentication scheme.
 */
@Component( "restAuthenticationEntryPoint" )
public class RestAuthenticationEntryPoint implements AuthenticationEntryPoint{
	
	public RestAuthenticationEntryPoint(){
		super();
	}
	
	// API - public
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void commence( final HttpServletRequest request, final HttpServletResponse response, final AuthenticationException authException ) throws IOException{
		response.sendError( HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized" );
	}
	
}

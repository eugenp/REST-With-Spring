package org.rest.integration.security;

import org.rest.common.util.HttpConstants;
import org.rest.integration.ExamplePaths;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;

import com.google.common.base.Preconditions;
import com.jayway.restassured.RestAssured;
import com.jayway.restassured.response.Response;
import com.jayway.restassured.specification.RequestSpecification;

@Component
public final class SecurityComponent{
	private static final String JSESSIONID = "JSESSIONID";
	
	@Autowired
	ExamplePaths examplePaths;
	
	public static final String ADMIN_USERNAME = "eparaschiv";
	public static final String ADMIN_PASSWORD = "eparaschiv";
	
	// API - DO authentication
	
	final String authenticateByFormAsAdmin(){
		return this.authenticateByForm( ADMIN_USERNAME, ADMIN_PASSWORD );
	}
	
	private final String authenticateByForm( final String username, final String password ){
		final Response response = RestAssured.given().param( UsernamePasswordAuthenticationFilter.SPRING_SECURITY_FORM_USERNAME_KEY, username ).param( UsernamePasswordAuthenticationFilter.SPRING_SECURITY_FORM_PASSWORD_KEY, password ).post( this.examplePaths.getLoginURL() );
		
		Preconditions.checkState( response.getStatusCode() == 302 );
		
		return JSESSIONID + "=" + response.getCookie( JSESSIONID );
	}
	
	final RequestSpecification givenAuthenticatedByFrom( final String cookie ){
		return RestAssured.given().header( HttpConstants.COOKIE_HEADER, cookie );
	}
	public final RequestSpecification givenAuthenticatedByBasicAuth(){
		return RestAssured.given().auth().preemptive().basic( SecurityComponent.ADMIN_USERNAME, SecurityComponent.ADMIN_PASSWORD );
	}
	
}

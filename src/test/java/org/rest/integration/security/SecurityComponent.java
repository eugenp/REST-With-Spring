package org.rest.integration.security;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.StatusLine;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.message.BasicNameValuePair;
import org.rest.constants.HttpConstants;
import org.rest.integration.ExamplePaths;
import org.rest.integration.http.HttpClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import com.jayway.restassured.RestAssured;
import com.jayway.restassured.specification.RequestSpecification;

@Component
public final class SecurityComponent{
	private static final Logger LOG = LoggerFactory.getLogger( SecurityComponent.class );
	
	@Autowired
	ExamplePaths examplePaths;
	
	public static final String ADMIN_USERNAME = "eparaschiv";
	public static final String ADMIN_PASSWORD = "eparaschiv";
	
	// API - DO authentication
	
	public String authenticateAsAdmin(){
		String authResult = null;
		try{
			authResult = this.authenticate( ADMIN_USERNAME, ADMIN_PASSWORD );
		}
		catch( final URISyntaxException uriEx ){
			LOG.error( "authenticateAsAdmin", uriEx );
		}
		catch( final IOException ioEx ){
			LOG.error( "authenticateAsAdmin", ioEx );
		}
		
		return authResult;
	}
	
	public String authenticate( final String username, final String password ) throws URISyntaxException, IOException{
		final HttpResponse loginResponseFromServer = this.executeAuthentication( username, password );
		
		final StatusLine responseStatus = loginResponseFromServer.getStatusLine();
		Preconditions.checkState( responseStatus.getStatusCode() == 302 );
		
		return this.getSessionIdFromResponseHeaders( loginResponseFromServer );
	}
	
	public HttpResponse executeAuthentication( final String username, final String password ) throws URISyntaxException, IOException{
		final URI loginUri = new URL( this.examplePaths.getLoginURL() ).toURI();
		
		final HttpPost loginPostMethod = this.createLoginRequest( username, password, loginUri );
		
		return new HttpClient().execute( loginPostMethod );
	}
	
	/**
	 * Creates a HTTP POST request for login <br>
	 * @param username - the username to be used in the login process
	 * @param password - the password (clearText) to be used in the login process
	 * @param serverLoginURL - the REST URL of the login
	 * @return - a POST request to the login URL of the server (will not be null)
	 * @throws UnsupportedEncodingException for invalid serverLoginURL
	 */
	HttpPost createLoginRequest( final String username, final String password, final URI serverLoginURL ) throws UnsupportedEncodingException{
		Preconditions.checkNotNull( username );
		Preconditions.checkNotNull( password );
		Preconditions.checkNotNull( serverLoginURL );
		
		final HttpPost loginPostMethod = new HttpPost( serverLoginURL );
		final NameValuePair userKV = new BasicNameValuePair( UsernamePasswordAuthenticationFilter.SPRING_SECURITY_FORM_USERNAME_KEY, username );
		final NameValuePair passKV = new BasicNameValuePair( UsernamePasswordAuthenticationFilter.SPRING_SECURITY_FORM_PASSWORD_KEY, password );
		loginPostMethod.setEntity( new UrlEncodedFormEntity( Lists.newArrayList( userKV, passKV ) ) );
		
		Preconditions.checkNotNull( loginPostMethod );
		return loginPostMethod;
	}
	
	// API - consume authentication result
	
	/**
	 * @param loginResponse - the response from the login URL of the server (should be non null)
	 * @return - the session id contained by the argument response (will be non null)
	 */
	public String getSessionIdFromResponseHeaders( final HttpResponse loginResponse ){
		Preconditions.checkNotNull( loginResponse );
		
		final Header[] cookieHeaders = loginResponse.getHeaders( HttpConstants.SET_COOKIE_HEADER );
		Preconditions.checkNotNull( cookieHeaders );
		Preconditions.checkState( cookieHeaders.length == 1 );
		
		final String cookieInfo = cookieHeaders[0].getValue();
		final String sessionId = this.extractSessionId( cookieInfo );
		
		Preconditions.checkNotNull( sessionId );
		return sessionId;
	}
	
	/**
	 * Extracts the sessionId from the cookie info.
	 * @param cookieInfo string containing cookie information
	 * @return the sessionId
	 */
	private String extractSessionId( final String cookieInfo ){
		final int pos = cookieInfo.indexOf( ";" );
		return cookieInfo.substring( 0, pos );
	}
	
	// UNSORTED
	
	public final RequestSpecification givenAuthenticated( final String cookie ){
		return RestAssured.given().header( HttpConstants.COOKIE_HEADER, cookie );
	}
	
	/*
	public HttpResponse logout( final String sessionId ) throws URISyntaxException, ClientProtocolException, IOException{
		if( sessionId == null ){
			return null;
		}
		final URI logoutUri = new URL( SecurityComponent.LOGOUT_URL ).toURI();
		final HttpGet logoutGetMethod = new HttpGet( logoutUri );
		IntegrationTestHelper.decorateRequestWithAuthHeaders( logoutGetMethod, sessionId );
		
		return new HttpClient().execute( logoutGetMethod );
	}
	 */
	
}

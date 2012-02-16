package org.rest.sec.client;

import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.impl.client.DefaultHttpClient;
import org.rest.sec.dto.User;
import org.rest.testing.ExamplePaths;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class AuthenticationRESTTemplate{
	
	@Autowired private RestTemplate restTemplate;
	@Autowired private ExamplePaths paths;
	
	@Value( "${port}" ) private int port;
	@Value( "${host}" ) private String host;
	
	//
	
	public final ResponseEntity< User > authenticate( final String user, final String pass ){
		final HttpComponentsClientHttpRequestFactory requestFactory = (HttpComponentsClientHttpRequestFactory) restTemplate.getRequestFactory();
		final DefaultHttpClient httpClient = (DefaultHttpClient) requestFactory.getHttpClient();
		httpClient.getCredentialsProvider().setCredentials( new AuthScope( host, port, AuthScope.ANY_REALM ), new UsernamePasswordCredentials( user, pass ) );
		
		return restTemplate.exchange( paths.getAuthenticationUri(), HttpMethod.POST, new HttpEntity< String >( createHeaders() ), User.class );
	}
	
	// util
	
	final HttpHeaders createHeaders(){
		final HttpHeaders headers = new HttpHeaders(){
			{
				set( com.google.common.net.HttpHeaders.ACCEPT, MediaType.APPLICATION_XML_VALUE );
			}
		};
		return headers;
	}
	
}

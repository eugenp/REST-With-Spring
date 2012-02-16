package org.rest.sec.client;

import org.rest.sec.dto.User;
import org.rest.testing.ExamplePaths;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class AuthenticationRESTTemplate{
	
	@Autowired
	private RestTemplate restTemplate;
	
	@Autowired
	private ExamplePaths paths;
	
	//
	
	public final ResponseEntity< User > authenticate(){
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

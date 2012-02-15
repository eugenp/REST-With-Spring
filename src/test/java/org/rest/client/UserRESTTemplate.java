package org.rest.client;

import java.util.List;

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
public class UserRESTTemplate{
	
	@Autowired
	private RestTemplate restTemplate;
	
	@Autowired
	private ExamplePaths paths;
	
	//
	
	public final ResponseEntity< List > findAll(){
		return restTemplate.exchange( paths.getUserUri(), HttpMethod.GET, new HttpEntity< String >( createHeaders() ), List.class );
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

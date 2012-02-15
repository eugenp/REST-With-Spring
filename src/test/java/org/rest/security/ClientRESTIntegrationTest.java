package org.rest.security;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.rest.sec.dto.User;
import org.rest.spring.application.ApplicationTestConfig;
import org.rest.spring.persistence.jpa.PersistenceJPAConfig;
import org.rest.test.AbstractRESTIntegrationTest;
import org.rest.testing.ExamplePaths;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import org.springframework.web.client.RestTemplate;

@RunWith( SpringJUnit4ClassRunner.class )
@ContextConfiguration( classes = { ApplicationTestConfig.class, PersistenceJPAConfig.class },loader = AnnotationConfigContextLoader.class )
public class ClientRESTIntegrationTest extends AbstractRESTIntegrationTest{
	
	@Autowired
	private ExamplePaths paths;
	
	@Autowired
	private RestTemplate restTemplate;
	
	// tests
	
	// GET
	
	@SuppressWarnings( "rawtypes" )
	@Test
	public final void whenGetUsersWithAuthentication_then200IsReceived(){
		// When
		final ResponseEntity< List > response = restTemplate.exchange( paths.getUserUri(), HttpMethod.GET, new HttpEntity< String >( createHeaders() ), List.class );
		
		// Then
		assertThat( response.getStatusCode().value(), is( 200 ) );
	}
	
	@Test
	public final void whenAuthenticating_then201IsReceived(){
		// When
		final ResponseEntity< User > response = restTemplate.exchange( paths.getAuthenticationUri(), HttpMethod.POST, new HttpEntity< String >( createHeaders() ), User.class );
		
		// Then
		assertThat( response.getStatusCode().value(), is( 201 ) );
	}
	
	//
	
	final HttpHeaders createHeaders(){
		final HttpHeaders headers = new HttpHeaders(){
			{
				set( "Accept", MediaType.APPLICATION_XML_VALUE );
			}
		};
		return headers;
	}
	
}

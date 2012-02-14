package org.rest.security;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import java.util.List;

import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.AuthCache;
import org.apache.http.impl.auth.BasicScheme;
import org.apache.http.impl.client.BasicAuthCache;
import org.apache.http.impl.client.DefaultHttpClient;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.rest.sec.dto.User;
import org.rest.sec.model.Privilege;
import org.rest.spring.application.ApplicationTestConfig;
import org.rest.spring.persistence.jpa.PersistenceJPAConfig;
import org.rest.test.AbstractRESTIntegrationTest;
import org.rest.testing.ExamplePaths;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.converter.xml.MarshallingHttpMessageConverter;
import org.springframework.oxm.xstream.XStreamMarshaller;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import org.springframework.web.client.RestTemplate;

@RunWith( SpringJUnit4ClassRunner.class )
@ContextConfiguration( classes = { ApplicationTestConfig.class, PersistenceJPAConfig.class },loader = AnnotationConfigContextLoader.class )
public class UserSandboxRESTIntegrationTest extends AbstractRESTIntegrationTest{
	
	@Autowired
	private ExamplePaths paths;
	
	// tests
	
	// GET
	
	@SuppressWarnings( "rawtypes" )
	@Test
	public final void whenGetWithAuthentication_then200IsReceived(){
		final RestTemplate restTemplate = setUpRestTemplate();
		
		// When
		final ResponseEntity< List > response = restTemplate.exchange( paths.getUserUri(), HttpMethod.GET, new HttpEntity< String >( createHeaders() ), List.class );
		
		// Then
		assertThat( response.getStatusCode().value(), is( 200 ) );
	}
	
	@Test
	@Ignore( "TODO: review" )
	public final void whenAuthenticating_then201IsReceived(){
		final RestTemplate restTemplate = setUpRestTemplate();
		
		// When
		final ResponseEntity< User > response = restTemplate.exchange( paths.getAuthenticationUri(), HttpMethod.GET, new HttpEntity< String >( createHeaders() ), User.class );
		
		// Then
		assertThat( response.getStatusCode().value(), is( 201 ) );
	}
	
	//
	
	final HttpHeaders createHeaders(){
		final HttpHeaders headers = new HttpHeaders(){
			{
				set( "Accept", "application/xml" );
			}
		};
		return headers;
	}
	
	final RestTemplate setUpRestTemplate(){
		final HttpHost targetHost = new HttpHost( "localhost", 8081, "http" );
		
		final DefaultHttpClient httpclient = new DefaultHttpClient();
		httpclient.getCredentialsProvider().setCredentials( new AuthScope( targetHost.getHostName(), targetHost.getPort(), AuthScope.ANY_REALM ), new UsernamePasswordCredentials( "eparaschiv", "eparaschiv" ) );
		final AuthCache authCache = new BasicAuthCache();
		// Generate BASIC scheme object and add it to the local auth cache
		final BasicScheme basicAuth = new BasicScheme();
		authCache.put( targetHost, basicAuth );
		
		//
		final RestTemplate restTemplate = new RestTemplate( new HttpComponentsClientHttpRequestFactory( httpclient ){
			{
				setReadTimeout( 15000 );
			}
		} );
		restTemplate.getMessageConverters().add( marshallingHttpMessageConverter() );
		
		return restTemplate;
	}
	
	final XStreamMarshaller xstreamMarshaller(){
		final XStreamMarshaller xStreamMarshaller = new XStreamMarshaller();
		xStreamMarshaller.setAutodetectAnnotations( true );
		// xStreamMarshaller.setSupportedClasses( new Class[ ] { User.class, Privilege.class } );
		xStreamMarshaller.setAnnotatedClass( User.class );
		xStreamMarshaller.setAnnotatedClass( Privilege.class );
		
		// this.xstreamMarshaller().getXStream().addDefaultImplementation( java.util.HashSet.class, PersistentSet.class );
		
		return xStreamMarshaller;
	}
	final MarshallingHttpMessageConverter marshallingHttpMessageConverter(){
		final MarshallingHttpMessageConverter marshallingHttpMessageConverter = new MarshallingHttpMessageConverter();
		marshallingHttpMessageConverter.setMarshaller( xstreamMarshaller() );
		marshallingHttpMessageConverter.setUnmarshaller( xstreamMarshaller() );
		
		return marshallingHttpMessageConverter;
	}
	
}

package org.rest.client;

import java.util.List;

import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.impl.client.DefaultHttpClient;
import org.rest.common.IEntity;
import org.rest.sec.util.SecurityConstants;
import org.rest.testing.marshaller.IMarshaller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import com.google.common.base.Preconditions;

@SuppressWarnings( { "unchecked", "rawtypes" } )
public abstract class AbstractClientRestTemplate< T extends IEntity > implements IClientTemplate< T >{
	protected Class< T > clazz;
	
	@Autowired protected RestTemplate restTemplate;
	@Autowired @Qualifier( "xstreamMarshaller" ) protected IMarshaller marshaller;
	
	@Value( "${port}" ) private int port;
	@Value( "${host}" ) private String host;
	
	public AbstractClientRestTemplate( final Class< T > clazzToSet ){
		super();
		
		clazz = clazzToSet;
	}
	
	// API
	
	// API - find
	
	@Override
	public final T findOne( final long id ){
		try{
			final ResponseEntity< String > response = restTemplate.exchange( getURI() + "/" + id, HttpMethod.GET, new HttpEntity< String >( createHeaders() ), String.class );
			return marshaller.decode( response.getBody(), clazz );
		}
		catch( final HttpClientErrorException clientEx ){
			return null;
		}
	}
	public final T findOneByURI( final String uri ){
		final ResponseEntity< String > response = restTemplate.exchange( uri, HttpMethod.GET, new HttpEntity< String >( createHeaders() ), String.class );
		
		return marshaller.decode( response.getBody(), clazz );
	}
	
	// API - find all
	
	@Override
	public final List< T > findAll(){
		final ResponseEntity< List > findAllResponse = restTemplate.exchange( getURI(), HttpMethod.GET, new HttpEntity< String >( createHeaders() ), List.class );
		return findAllResponse.getBody();
	}
	
	public final ResponseEntity< List > findAllAsResponse(){
		return restTemplate.exchange( getURI(), HttpMethod.GET, new HttpEntity< String >( createHeaders() ), List.class );
	}
	
	// API - create
	
	@Override
	public final T create( final T resource ){
		final ResponseEntity responseEntity = restTemplate.exchange( getURI(), HttpMethod.POST, new HttpEntity< T >( resource, createHeaders() ), clazz );
		
		final String locationOfCreatedResource = responseEntity.getHeaders().getLocation().toString();
		
		Preconditions.checkNotNull( locationOfCreatedResource );
		return findOneByURI( locationOfCreatedResource );
	}
	public final String createAsURI( final T resource ){
		final ResponseEntity responseEntity = restTemplate.exchange( getURI(), HttpMethod.POST, new HttpEntity< T >( resource, createHeaders() ), List.class );
		
		final String locationOfCreatedResource = responseEntity.getHeaders().getLocation().toString();
		Preconditions.checkNotNull( locationOfCreatedResource );
		
		return locationOfCreatedResource;
	}
	
	// API - update
	
	@Override
	public final void update( final T resource ){
		final ResponseEntity responseEntity = restTemplate.exchange( getURI(), HttpMethod.PUT, new HttpEntity< T >( resource, createHeaders() ), clazz );
		Preconditions.checkState( responseEntity.getStatusCode().value() == 200 );
	}
	
	// API - delete
	
	@Override
	public final void delete( final long id ){
		final ResponseEntity< Object > deleteResourceResponse = restTemplate.exchange( getURI() + "/" + id, HttpMethod.DELETE, null, null );
		
		Preconditions.checkState( deleteResourceResponse.getStatusCode().value() == 204 );
	}
	
	// util
	
	protected final HttpHeaders createHeaders(){
		final HttpHeaders headers = new HttpHeaders(){
			{
				set( com.google.common.net.HttpHeaders.ACCEPT, marshaller.getMime() );
			}
		};
		return headers;
	}
	
	// template method
	
	public abstract String getURI();
	
	protected IClientTemplate< T > givenAuthenticated(){
		final HttpComponentsClientHttpRequestFactory requestFactory = (HttpComponentsClientHttpRequestFactory) restTemplate.getRequestFactory();
		final DefaultHttpClient httpClient = (DefaultHttpClient) requestFactory.getHttpClient();
		httpClient.getCredentialsProvider().setCredentials( new AuthScope( host, port, AuthScope.ANY_REALM ), new UsernamePasswordCredentials( SecurityConstants.ADMIN_USERNAME, SecurityConstants.ADMIN_PASSWORD ) );
		
		return this;
	}
	
	// entity

	@Override
	public abstract T createNewEntity();
	@Override
	public abstract void makeEntityInvalid( final T entity );
	
}

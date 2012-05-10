package org.rest.client;

import java.util.List;

import org.rest.client.marshall.IMarshaller;
import org.rest.common.IEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import com.google.common.base.Preconditions;

@SuppressWarnings( { "unchecked", "rawtypes" } )
public abstract class AbstractClientRESTTemplate< T extends IEntity > implements IClientTemplate< T >{
	protected Class< T > clazz;
	
	@Autowired protected RestTemplate restTemplate;
	@Autowired @Qualifier( "xstreamMarshaller" ) protected IMarshaller marshaller;
	
	public AbstractClientRESTTemplate( final Class< T > clazzToSet ){
		super();
		
		clazz = clazzToSet;
	}
	
	// API
	
	// find - one
	
	@Override
	public T findOne( final long id ){
		try{
			final ResponseEntity< String > response = restTemplate.exchange( getURI() + "/" + id, HttpMethod.GET, new HttpEntity< String >( createAcceptHeaders() ), String.class );
			return marshaller.decode( response.getBody(), clazz );
		}
		catch( final HttpClientErrorException clientEx ){
			return null;
		}
	}
	
	public T findOneByURI( final String uri ){
		final ResponseEntity< String > response = restTemplate.exchange( uri, HttpMethod.GET, new HttpEntity< String >( createAcceptHeaders() ), String.class );
		return marshaller.decode( response.getBody(), clazz );
	}
	
	// find - all
	
	@Override
	public List< T > findAll(){
		final ResponseEntity< List > findAllResponse = restTemplate.exchange( getURI(), HttpMethod.GET, new HttpEntity< String >( createAcceptHeaders() ), List.class );
		return findAllResponse.getBody();
	}
	
	public ResponseEntity< List > findAllAsResponse(){
		return restTemplate.exchange( getURI(), HttpMethod.GET, new HttpEntity< String >( createAcceptHeaders() ), List.class );
	}
	
	public final List< T > findAllByURI( final String uri ){
		final ResponseEntity< List > response = restTemplate.exchange( uri, HttpMethod.GET, new HttpEntity< T >( createAcceptHeaders() ), List.class );
		return response.getBody();
	}

	// create
	
	@Override
	public T create( final T resource ){
		final ResponseEntity responseEntity = restTemplate.exchange( getURI(), HttpMethod.POST, new HttpEntity< T >( resource, createContentTypeHeaders() ), clazz );
		
		final String locationOfCreatedResource = responseEntity.getHeaders().getLocation().toString();
		
		Preconditions.checkNotNull( locationOfCreatedResource );
		return findOneByURI( locationOfCreatedResource );
	}
	
	public String createAsURI( final T resource ){
		final ResponseEntity responseEntity = restTemplate.exchange( getURI(), HttpMethod.POST, new HttpEntity< T >( resource, createContentTypeHeaders() ), List.class );
		
		final String locationOfCreatedResource = responseEntity.getHeaders().getLocation().toString();
		Preconditions.checkNotNull( locationOfCreatedResource );
		
		return locationOfCreatedResource;
	}
	
	// update
	
	@Override
	public void update( final T resource ){
		final ResponseEntity responseEntity = restTemplate.exchange( getURI(), HttpMethod.PUT, new HttpEntity< T >( resource, createContentTypeHeaders() ), clazz );
		Preconditions.checkState( responseEntity.getStatusCode().value() == 200 );
	}
	
	// delete
	
	@Override
	public void delete( final long id ){
		final ResponseEntity< Object > deleteResourceResponse = restTemplate.exchange( getURI() + "/" + id, HttpMethod.DELETE, null, null );
		
		Preconditions.checkState( deleteResourceResponse.getStatusCode().value() == 204 );
	}
	
	@Override
	public void deleteAll(){
		throw new UnsupportedOperationException();
	}
	
	// util
	
	protected final HttpHeaders createAcceptHeaders(){
		final HttpHeaders headers = new HttpHeaders(){
			{
				set( com.google.common.net.HttpHeaders.ACCEPT, marshaller.getMime() );
			}
		};
		return headers;
	}
	
	protected final HttpHeaders createContentTypeHeaders(){
		final HttpHeaders headers = new HttpHeaders(){
			{
				set( com.google.common.net.HttpHeaders.CONTENT_TYPE, marshaller.getMime() );
			}
		};
		return headers;
	}
	
	// template method
	
	public abstract String getURI();
	
	public final IClientTemplate< T > givenAuthenticated(){
		if( isBasicAuth() ){
			basicAuth();
		}
		else{
			digestAuth();
		}
		
		return this;
	}
	
	protected abstract boolean isBasicAuth();
	
	protected abstract void basicAuth();
	
	protected abstract void digestAuth();
	
}

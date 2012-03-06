package org.rest.client.template;

import java.util.List;

import org.apache.http.HttpHeaders;
import org.rest.common.IEntity;
import org.rest.testing.marshaller.IMarshaller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.google.common.base.Preconditions;
import com.jayway.restassured.response.Response;

/**
 * Template for the consumption of the REST API <br>
 */
public abstract class AbstractRESTTemplate< T extends IEntity > implements IRESTTemplate< T >{
	
	@Autowired @Qualifier( "xstreamMarshaller" ) protected IMarshaller marshaller;
	
	protected final Class< T > clazz;
	
	public AbstractRESTTemplate( final Class< T > clazzToSet ){
		super();
		
		Preconditions.checkNotNull( clazzToSet );
		clazz = clazzToSet;
	}
	
	// search
	
	@Override
	public Response searchAsResponse( final Long id ){
		throw new UnsupportedOperationException();
	}
	@Override
	public List< T > search( final Long id ){
		throw new UnsupportedOperationException();
	}
	
	// findOne
	
	@Override
	public final T findOne( final long id ){
		final String resourceAsXML = findOneAsMime( getURI() + "/" + id );
		
		return marshaller.decode( resourceAsXML, clazz );
	}
	@Override
	public final Response findOneAsResponse( final String uriOfResource ){
		return givenAuthenticated().header( HttpHeaders.ACCEPT, marshaller.getMime() ).get( uriOfResource );
	}
	@Override
	public final Response findOneAsResponse( final String uriOfResource, final String acceptMime ){
		return givenAuthenticated().header( HttpHeaders.ACCEPT, acceptMime ).get( uriOfResource );
	}
	
	// findAll
	
	@SuppressWarnings( { "unchecked", "rawtypes" } )
	@Override
	public final List< T > findAll(){
		final Response findAllResponse = findOneAsResponse( getURI() );
		
		return marshaller.<List> decode( findAllResponse.getBody().asString(), List.class );
	}
	@Override
	public final Response findAllAsResponse(){
		return findOneAsResponse( getURI() );
	}
	
	// create
	
	@Override
	public final T create( final T resource ){
		final String uriForResourceCreation = createResourceAsURI( resource );
		final String resourceAsXML = findOneAsMime( uriForResourceCreation );
		
		return marshaller.decode( resourceAsXML, clazz );
	}
	@Override
	public final String createResourceAsURI( final T resource ){
		Preconditions.checkNotNull( resource );
		
		final String resourceAsString = marshaller.encode( resource );
		final Response response = givenAuthenticated().contentType( marshaller.getMime() ).body( resourceAsString ).post( getURI() );
		Preconditions.checkState( response.getStatusCode() == 201 );
		
		final String locationOfCreatedResource = response.getHeader( HttpHeaders.LOCATION );
		Preconditions.checkNotNull( locationOfCreatedResource );
		return locationOfCreatedResource;
	}
	@Override
	public final Response createAsResponse( final T resource ){
		Preconditions.checkNotNull( resource );
		
		final String resourceAsString = marshaller.encode( resource );
		return givenAuthenticated().contentType( marshaller.getMime() ).body( resourceAsString ).post( getURI() );
	}
	
	// update
	
	@Override
	public final void update( final T resource ){
		final Response updateResponse = updateAsResponse( resource );
		Preconditions.checkState( updateResponse.getStatusCode() == 200 );
	}
	@Override
	public final Response updateAsResponse( final T resource ){
		Preconditions.checkNotNull( resource );
		
		final String resourceAsString = marshaller.encode( resource );
		return givenAuthenticated().contentType( marshaller.getMime() ).body( resourceAsString ).put( getURI() );
	}
	
	// delete
	
	@Override
	public final void delete( final long id ){
		final Response deleteResponse = deleteAsResponse( getURI() + "/" + id );
		Preconditions.checkState( deleteResponse.getStatusCode() == 204 );
	}
	@Override
	public final Response deleteAsResponse( final String uriOfResource ){
		return givenAuthenticated().delete( uriOfResource );
	}
	
	// entity (non REST)
	
	@Override
	public void invalidate( final T entity ){
		throw new UnsupportedOperationException();
	}
	
	// util
	
	protected final String findOneAsMime( final String uriOfResource ){
		return givenAuthenticated().header( HttpHeaders.ACCEPT, marshaller.getMime() ).get( uriOfResource ).asString();
	}
	
	//
	
	public final String getMime(){
		return marshaller.getMime();
	}
	
	@Override
	public final IMarshaller getMarshaller(){
		return marshaller;
	}
	
}

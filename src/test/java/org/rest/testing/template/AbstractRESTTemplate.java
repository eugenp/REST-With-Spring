package org.rest.testing.template;

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
	
	@Autowired @Qualifier( "xstreamMarshaller" ) private IMarshaller marshaller;
	
	private final Class< T > clazz;
	
	public AbstractRESTTemplate( final Class< T > clazzToSet ){
		super();
		
		Preconditions.checkNotNull( clazzToSet );
		clazz = clazzToSet;
	}
	
	// entity (non REST)
	
	@Override
	public void makeEntityInvalid( final T entity ){
		throw new UnsupportedOperationException();
	}
	
	// create
	
	@Override
	public final String createResourceAsURI(){
		return createResourceAsURI( createNewEntity() );
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
	public final Response createResourceAsResponse(){
		return createResourceAsResponse( createNewEntity() );
	}
	@Override
	public final Response createResourceAsResponse( final T resource ){
		Preconditions.checkNotNull( resource );
		
		final String resourceAsString = marshaller.encode( resource );
		return givenAuthenticated().contentType( marshaller.getMime() ).body( resourceAsString ).post( getURI() );
	}
	
	// create and get
	
	@Override
	public final String createResourceAndGetAsMime( final String mime ){
		final String uriForResourceCreation = createResourceAsURI();
		final String createdResourceAsMime = getResourceAsMime( uriForResourceCreation, mime );
		
		return createdResourceAsMime;
	}
	@Override
	public final T createResourceAndGetAsEntity(){
		return createResourceAndGetAsEntity( createNewEntity() );
	}
	@Override
	public final T createResourceAndGetAsEntity( final T resource ){
		final String uriForResourceCreation = createResourceAsURI( resource );
		
		return getResourceAsEntity( uriForResourceCreation );
	}
	
	@Override
	public final Response createResourceAndGetAsResponse(){
		return createResourceAndGetAsResponse( createNewEntity() );
	}
	@Override
	public final Response createResourceAndGetAsResponse( final T resource ){
		final String uriForResourceCreation = createResourceAsURI( resource );
		
		return getResourceAsResponse( uriForResourceCreation );
	}
	
	// get
	
	@Override
	public final T getResourceAsEntity( final String uriOfResource ){
		final String resourceAsXML = getResourceAsMime( uriOfResource, marshaller.getMime() );
		
		return marshaller.decode( resourceAsXML, clazz );
	}
	public final T getResourceAsEntity( final Long idOfResource ){
		final String resourceAsXML = getResourceAsMime( getURI() + "/" + idOfResource, marshaller.getMime() );
		
		return marshaller.decode( resourceAsXML, clazz );
	}
	
	@Override
	public final String getResourceAsMime( final String uriOfResource, final String mime ){
		return givenAuthenticated().header( HttpHeaders.ACCEPT, mime ).get( uriOfResource ).asString();
	}
	
	@Override
	public final Response getResourceAsResponse( final String uriOfResource ){
		return givenAuthenticated().header( HttpHeaders.ACCEPT, marshaller.getMime() ).get( uriOfResource );
	}
	@Override
	public final Response getResourceAsResponse( final String uriOfResource, final String acceptMime ){
		return givenAuthenticated().header( HttpHeaders.ACCEPT, acceptMime ).get( uriOfResource );
	}
	
	// update
	
	@Override
	public final Response updateResourceAsResponse( final T resource ){
		Preconditions.checkNotNull( resource );
		
		final String resourceAsString = marshaller.encode( resource );
		return givenAuthenticated().contentType( marshaller.getMime() ).body( resourceAsString ).put( getURI() );
	}
	@Override
	public final Response updateResourceAsResponse( final String resourceAsMime ){
		Preconditions.checkNotNull( resourceAsMime );
		
		return givenAuthenticated().contentType( marshaller.getMime() ).body( resourceAsMime ).put( getURI() );
	}
	
	@Override
	public final T updateResourceAndGetAsEntity( final T resource ){
		updateResourceAsResponse( resource );
		return findOne( resource.getId() );
	}
	
	// delete
	
	@Override
	public final Response delete( final String uriOfResource ){
		return givenAuthenticated().delete( uriOfResource );
	}
	
	// valid DAO operations (in progress)
	
	@Override
	public final T findOne( final long id ){
		final String resourceAsXML = getResourceAsMime( getURI() + "/" + id, marshaller.getMime() );
		
		return marshaller.decode( resourceAsXML, clazz );
	}
	
	@SuppressWarnings( { "unchecked", "rawtypes" } )
	@Override
	public final List< T > findAll(){
		final Response findAllResponse = getResourceAsResponse( getURI() );
		
		return marshaller.<List> decode( findAllResponse.getBody().asString(), List.class );
	}
	
	@Override
	public final void delete( final long id ){
		final Response deleteResponse = delete( getURI() + "/" + id );
		Preconditions.checkState( deleteResponse.getStatusCode() == 204 );
	}
	
	@Override
	public final T create( final T resource ){
		return createResourceAndGetAsEntity( resource );
	}
	
	@Override
	public final void update( final T resource ){
		final Response updateResponse = updateResourceAsResponse( resource );
		Preconditions.checkState( updateResponse.getStatusCode() == 200 );
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

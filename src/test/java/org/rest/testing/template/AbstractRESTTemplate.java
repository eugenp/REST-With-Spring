package org.rest.testing.template;

import org.apache.http.HttpHeaders;
import org.rest.common.IEntity;
import org.rest.testing.marshaller.IMarshaller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.google.common.base.Preconditions;
import com.jayway.restassured.response.Response;
import com.jayway.restassured.specification.RequestSpecification;

/**
 * Template for the consumption of the REST API <br>
 */
public abstract class AbstractRESTTemplate< T extends IEntity > implements ITemplate< T >{
	
	@Autowired
	@Qualifier( "xstreamMarshaller" )
	private IMarshaller marshaller;
	
	private final Class< T > clazz;
	
	public AbstractRESTTemplate( final Class< T > clazzToSet ){
		super();
		
		Preconditions.checkNotNull( clazzToSet );
		this.clazz = clazzToSet;
	}
	
	// create
	
	@Override
	public final String createResourceAsURI(){
		return this.createResourceAsURI( createNewEntity() );
	}
	@Override
	public final String createResourceAsURI( final T resource ){
		Preconditions.checkNotNull( resource );
		
		final String resourceAsString = marshaller.encode( resource );
		final Response response = this.givenAuthenticated().contentType( marshaller.getMime() ).body( resourceAsString ).post( getURI() );
		
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
		return this.givenAuthenticated().contentType( marshaller.getMime() ).body( resourceAsString ).post( getURI() );
	}
	
	// create and get
	
	@Override
	public final String createResourceAndGetAsMime( final String mime ){
		final String uriForResourceCreation = this.createResourceAsURI();
		final String createdResourceAsMime = this.getResourceAsMime( uriForResourceCreation, mime );
		
		return createdResourceAsMime;
	}
	@Override
	public final T createResourceAndGetAsEntity(){
		return createResourceAndGetAsEntity( createNewEntity() );
	}
	@Override
	public final T createResourceAndGetAsEntity( final T resource ){
		final String uriForResourceCreation = this.createResourceAsURI( resource );
		
		return this.getResourceAsEntity( uriForResourceCreation );
	}

	@Override
	public final Response createResourceAndGetAsResponse(){
		return createResourceAndGetAsResponse( createNewEntity() );
	}
	@Override
	public final Response createResourceAndGetAsResponse( final T resource ){
		final String uriForResourceCreation = this.createResourceAsURI( resource );
		
		return this.getResourceAsResponse( uriForResourceCreation );
	}
	
	// get
	
	@Override
	public final T getResourceAsEntity( final String uriOfResource ){
		final String resourceAsXML = this.getResourceAsMime( uriOfResource, marshaller.getMime() );
		
		return this.marshaller.decode( resourceAsXML, clazz );
	}
	
	@Override
	public final String getResourceAsMime( final String uriOfResource, final String mime ){
		return this.givenAuthenticated().header( HttpHeaders.ACCEPT, mime ).get( uriOfResource ).asString();
	}
	
	@Override
	public final Response getResourceAsResponse( final String uriOfResource ){
		return this.givenAuthenticated().header( HttpHeaders.ACCEPT, marshaller.getMime() ).get( uriOfResource );
	}
	@Override
	public final Response getResourceAsResponse( final String uriOfResource, final String acceptMime ){
		return this.givenAuthenticated().header( HttpHeaders.ACCEPT, acceptMime ).get( uriOfResource );
	}
	
	// update
	
	@Override
	public final Response updateResourceAsResponse( final T resource ){
		Preconditions.checkNotNull( resource );
		
		final String resourceAsString = marshaller.encode( resource );
		return this.givenAuthenticated().contentType( marshaller.getMime() ).body( resourceAsString ).put( getURI() );
	}
	@Override
	public final Response updateResourceAsResponse( final String resourceAsMime ){
		Preconditions.checkNotNull( resourceAsMime );
		
		return this.givenAuthenticated().contentType( marshaller.getMime() ).body( resourceAsMime ).put( getURI() );
	}
	
	// delete
	
	@Override
	public final Response delete( final String uriOfResource ){
		final Response response = this.givenAuthenticated().delete( uriOfResource );
		return response;
	}
	
	//
	
	protected abstract RequestSpecification givenAuthenticated();
	
}

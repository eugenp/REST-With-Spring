package org.rest.web.common;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.internal.matchers.StringContains.containsString;

import org.junit.Ignore;
import org.junit.Test;
import org.rest.common.IEntity;
import org.rest.test.AbstractRESTIntegrationTest;
import org.rest.testing.template.IRESTTemplate;
import org.springframework.http.MediaType;

import com.google.common.base.Preconditions;
import com.jayway.restassured.response.Response;

public abstract class AbstractMimeRESTIntegrationTest< T extends IEntity > extends AbstractRESTIntegrationTest{
	
	private Class< T > clazz;
	
	public AbstractMimeRESTIntegrationTest( final Class< T > clazzToSet ){
		Preconditions.checkNotNull( clazzToSet );
		this.clazz = clazzToSet;
	}
	
	// tests
	
	// GET
	
	@Test
	@Ignore( "for now, json is not supported" )
	public final void givenResourceForIdExists_whenResourceOfThatIdIsRetrievedAsJson_then200IsReceived(){
		// Given
		final String uriForResourceCreation = getTemplate().createResourceAsURI( getTemplate().createNewEntity() );
		
		// When
		final Response res = getTemplate().findOneAsResponse( uriForResourceCreation, MediaType.APPLICATION_JSON.toString() );
		
		// Then
		assertThat( res.getStatusCode(), is( 200 ) );
	}
	@Test
	public final void givenResourceForIdExists_whenResourceOfThatIdIsRetrievedAsXML__then200IsReceived(){
		// Given
		final String uriForResourceCreation = getTemplate().createResourceAsURI( getTemplate().createNewEntity() );
		
		// When
		final Response res = getTemplate().findOneAsResponse( uriForResourceCreation, MediaType.APPLICATION_XML.toString() );
		
		// Then
		assertThat( res.getStatusCode(), is( 200 ) );
	}
	
	@Test
	@Ignore( "for now, json is not supported" )
	public final void givenRequestAcceptsJson_whenResourceIsRetrievedById_thenResponseContentTypeIsJson(){
		// Given
		final String uriForResourceCreation = getTemplate().createResourceAsURI( getTemplate().createNewEntity() );
		
		// When
		final Response res = getTemplate().findOneAsResponse( uriForResourceCreation, MediaType.APPLICATION_JSON.toString() );
		
		// Then
		assertThat( res.getContentType(), containsString( MediaType.APPLICATION_JSON.toString() ) );
	}
	@Test
	public final void givenRequestAcceptsXML_whenResourceIsRetrievedById__thenResponseContentTypeIsXML(){
		// Given
		final String uriForResourceCreation = getTemplate().createResourceAsURI( getTemplate().createNewEntity() );
		
		// When
		final Response res = getTemplate().findOneAsResponse( uriForResourceCreation, MediaType.APPLICATION_XML.toString() );
		
		// Then
		assertThat( res.getContentType(), containsString( MediaType.APPLICATION_XML.toString() ) );
	}
	
	@Test
	public final void givenResourceForIdExists_whenResourceIsRetrievedByIdAsXML_thenRetrievedResourceIsCorrect(){
		// Given
		final String uriForResourceCreation = getTemplate().createResourceAsURI( getTemplate().createNewEntity() );
		
		// When
		final Response resourceAsResponse = getTemplate().findOneAsResponse( uriForResourceCreation, MediaType.APPLICATION_XML.toString() );
		
		// Then
		getTemplate().getMarshaller().decode( resourceAsResponse.asString(), clazz );
	}
	
	// template method
	
	protected abstract IRESTTemplate< T > getTemplate();
	
}

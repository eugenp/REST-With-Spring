package org.rest.web.common;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.internal.matchers.StringContains.containsString;

import org.junit.Ignore;
import org.junit.Test;
import org.rest.common.IEntity;
import org.rest.test.integration.test.AbstractRESTIntegrationTest;
import org.rest.testing.template.ITemplate;
import org.springframework.http.MediaType;

import com.jayway.restassured.response.Response;

public abstract class AbstractMimeRESTIntegrationTest< T extends IEntity > extends AbstractRESTIntegrationTest{
	
	// tests
	
	// GET
	@Test
	@Ignore( "for now, json is not supported" )
	public final void givenResourceForIdExists_whenResourceOfThatIdIsRetrievedAsJson_then200IsReceived(){
		// Given
		final String uriForResourceCreation = getTemplate().createResourceAsURI();
		
		// When
		final Response res = getTemplate().getResourceAsResponse( uriForResourceCreation, MediaType.APPLICATION_JSON.toString() );
		
		// Then
		assertThat( res.getStatusCode(), is( 200 ) );
	}
	@Test
	public final void givenResourceForIdExists_whenResourceOfThatIdIsRetrievedAsXML__then200IsReceived(){
		// Given
		final String uriForResourceCreation = getTemplate().createResourceAsURI();
		
		// When
		final Response res = getTemplate().getResourceAsResponse( uriForResourceCreation, MediaType.APPLICATION_XML.toString() );
		
		// Then
		assertThat( res.getStatusCode(), is( 200 ) );
	}
	@Test
	public final void givenResourceForIdExists_whenResourceOfThatIdIsRetrievedAsAtom_then200IsReceived(){
		// Given
		final String uriForResourceCreation = getTemplate().createResourceAsURI();
		
		// When
		final Response res = getTemplate().getResourceAsResponse( uriForResourceCreation, MediaType.APPLICATION_ATOM_XML.toString() );
		
		// Then
		assertThat( res.getStatusCode(), is( 200 ) );
	}
	
	@Test
	@Ignore( "for now, json is not supported" )
	public final void givenRequestAcceptsJson_whenResourceIsRetrievedById_thenResponseContentTypeIsJson(){
		// Given
		final String uriForResourceCreation = getTemplate().createResourceAsURI( getTemplate().createNewEntity() );
		
		// When
		final Response res = getTemplate().getResourceAsResponse( uriForResourceCreation, MediaType.APPLICATION_JSON.toString() );
		
		// Then
		assertThat( res.getContentType(), containsString( MediaType.APPLICATION_JSON.toString() ) );
	}
	@Test
	public final void givenRequestAcceptsXML_whenResourceIsRetrievedById__thenResponseContentTypeIsXML(){
		// Given
		final String uriForResourceCreation = getTemplate().createResourceAsURI( getTemplate().createNewEntity() );
		
		// When
		final Response res = getTemplate().getResourceAsResponse( uriForResourceCreation, MediaType.APPLICATION_XML.toString() );
		
		// Then
		assertThat( res.getContentType(), containsString( MediaType.APPLICATION_XML.toString() ) );
	}
	@Test
	public final void givenRequestAcceptsAtom_whenResourceIsRetrievedById_thenResponseContentTypeIsAtom(){
		// Given
		final String uriForResourceCreation = getTemplate().createResourceAsURI( getTemplate().createNewEntity() );
		
		// When
		final Response res = getTemplate().getResourceAsResponse( uriForResourceCreation, MediaType.APPLICATION_ATOM_XML.toString() );
		
		// Then
		assertThat( res.getContentType(), containsString( MediaType.APPLICATION_ATOM_XML.toString() ) );
	}
	
	@Test
	public final void givenResourceForIdExists_whenResourceIsRetrievedByIdAsXML_thenRetrievedResourceIsCorrect(){
		// Given
		final String uriForResourceCreation = getTemplate().createResourceAsURI( getTemplate().createNewEntity() );
		
		// When
		getTemplate().getResourceAsResponse( uriForResourceCreation, MediaType.APPLICATION_XML.toString() );
		
		// Then
		// TODO
	}
	
	// template method
	
	protected abstract ITemplate< T > getTemplate();
	
}

package org.rest.web.common;

import static org.apache.commons.lang3.RandomStringUtils.randomNumeric;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertFalse;

import java.util.List;

import org.junit.Test;
import org.rest.common.IEntity;
import org.rest.test.AbstractRESTIntegrationTest;
import org.rest.testing.marshaller.IMarshaller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.jayway.restassured.response.Response;
import com.jayway.restassured.specification.RequestSpecification;

public abstract class AbstractPaginationRESTIntegrationTest< T extends IEntity > extends AbstractRESTIntegrationTest{
	
	@Autowired
	@Qualifier( "xstreamMarshaller" )
	IMarshaller marshaller;
	
	// tests
	
	// GET (paged)
	
	@Test
	public final void whenResourcesAreRetrievedPaged_thenNoExceptions(){
		givenAuthenticated().get( getURI() + "?page=1&size=10" );
	}
	@Test
	public final void whenResourcesAreRetrievedPaged_then200IsReceived(){
		// When
		final Response response = givenAuthenticated().get( getURI() + "?page=1&size=10" );
		
		// Then
		assertThat( response.getStatusCode(), is( 200 ) );
	}
	
	@Test
	public final void whenFirstPageOfResourcesAreRetrieved_thenResourcesPageIsReturned(){
		// When
		final Response response = givenAuthenticated().get( getURI() + "?page=1&size=10" );
		
		// Then
		assertFalse( marshaller.decode( response.asString(), List.class ).isEmpty() );
	}
	
	@Test
	public final void whenPageOfResourcesAreRetrievedOutOfBounds_then404IsReceived(){
		// When
		final Response response = givenAuthenticated().get( getURI() + "?page=" + randomNumeric( 5 ) + "&size=10" );
		
		// Then
		assertThat( response.getStatusCode(), is( 404 ) );
	}
	
	// template method
	
	protected abstract RequestSpecification givenAuthenticated();
	
	protected abstract String getURI();
	
	protected abstract T createNewEntity();
	
}

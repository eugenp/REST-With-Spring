package org.rest.web.common;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Ignore;
import org.junit.Test;
import org.rest.common.IEntity;
import org.rest.test.AbstractRESTIntegrationTest;
import org.rest.testing.security.AuthenticationUtil;
import org.rest.testing.template.IRESTTemplate;

import com.google.common.collect.Ordering;
import com.jayway.restassured.response.Response;
import com.jayway.restassured.specification.RequestSpecification;

public abstract class AbstractSortRESTIntegrationTest< T extends IEntity > extends AbstractRESTIntegrationTest{
	
	// tests
	
	// GET (paged)
	
	@Test
	public final void whenResourcesAreRetrievedSorted_thenNoExceptions(){
		givenAuthenticated().get( getURI() + "?page=0&size=41&sortBy=name" );
	}
	@Test
	public final void whenResourcesAreRetrievedSorted_then200IsReceived(){
		final Response response = givenAuthenticated().get( getURI() + "?page=0&size=1&sortBy=name" );
		
		assertThat( response.getStatusCode(), is( 200 ) );
	}
	
	@SuppressWarnings( "unchecked" )
	@Test
	// TODO: unignore
	@Ignore( "ONLY TEMPORARILY IGNORED" )
	public final void whenResourcesAreRetrievedSorted_thenResourcesAreIndeedOrdered(){
		getTemplate().createAsResponse( getTemplate().createNewEntity() );
		getTemplate().createAsResponse( getTemplate().createNewEntity() );
		
		// When
		final Response response = givenAuthenticated().get( getURI() + "?page=0&size=4&sortBy=name" );
		final List< T > resourcesPagedAndSorted = getTemplate().getMarshaller().decode( response.asString(), List.class );
		
		// Then
		assertTrue( getOrdering().isOrdered( resourcesPagedAndSorted ) );
	}
	@SuppressWarnings( "unchecked" )
	@Test
	public final void whenResourcesAreRetrievedNotSorted_thenResourcesAreNotOrdered(){
		getTemplate().createAsResponse( getTemplate().createNewEntity() );
		getTemplate().createAsResponse( getTemplate().createNewEntity() );
		
		// When
		final Response response = givenAuthenticated().get( getURI() + "?page=0&size=6" );
		final List< T > resourcesPagedAndSorted = getTemplate().getMarshaller().decode( response.asString(), List.class );
		
		// Then
		assertFalse( getOrdering().isOrdered( resourcesPagedAndSorted ) );
	}
	
	@Test
	public final void whenResourcesAreRetrievedByInvalidSorting_then400IsReceived(){
		// When
		final Response response = givenAuthenticated().get( getURI() + "?page=0&size=4&sortBy=invalid" );
		
		// Then
		assertThat( response.getStatusCode(), is( 400 ) );
	}
	
	// util
	
	protected final RequestSpecification givenAuthenticated(){
		return AuthenticationUtil.givenBasicAuthenticated();
	}
	
	// template method
	
	protected abstract Ordering< T > getOrdering();
	
	protected abstract String getURI();
	
	protected abstract T createNewEntity();
	
	protected abstract IRESTTemplate< T > getTemplate();
	
}

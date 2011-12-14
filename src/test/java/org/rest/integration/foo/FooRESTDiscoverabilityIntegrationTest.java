package org.rest.integration.foo;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.junit.matchers.JUnitMatchers.containsString;

import java.util.List;

import org.apache.http.HttpHeaders;
import org.hamcrest.Matchers;
import org.hamcrest.core.AnyOf;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.rest.common.util.HttpConstants;
import org.rest.common.util.RESTURIUtil;
import org.rest.integration.ExamplePaths;
import org.rest.integration.FooRESTTemplate;
import org.rest.integration.http.HTTPLinkHeaderUtils;
import org.rest.integration.security.SecurityComponent;
import org.rest.model.Foo;
import org.rest.spring.application.ApplicationConfig;
import org.rest.spring.persistence.jpa.PersistenceJPAConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import com.google.common.base.Predicates;
import com.google.common.collect.Iterables;
import com.jayway.restassured.response.Response;
import com.jayway.restassured.specification.RequestSpecification;

@RunWith( SpringJUnit4ClassRunner.class )
@ContextConfiguration( classes = { ApplicationConfig.class, PersistenceJPAConfig.class },loader = AnnotationConfigContextLoader.class )
@ActiveProfiles( "jpa" )
public class FooRESTDiscoverabilityIntegrationTest{
	@Autowired
	ExamplePaths paths;
	
	@Autowired
	FooRESTTemplate restTemplate;
	
	@Autowired
	SecurityComponent securityComponent;
	
	// tests
	
	// GET
	
	@Test
	public final void whenResourceIsRetrieved_thenURIToCreateANewResourceIsDiscoverable(){
		// Given
		final String uriOfNewlyCreatedResource = this.restTemplate.createResource();
		
		// When
		final Response response = this.givenAuthenticated().get( uriOfNewlyCreatedResource );
		
		// Then
		final String linkHeader = response.getHeader( "Link" );
		final String uriForResourceCreation = HTTPLinkHeaderUtils.extractSingleURI( linkHeader );
		final Response secondCreationResponse = this.givenAuthenticated().contentType( HttpConstants.MIME_JSON ).body( new Foo( randomAlphabetic( 6 ) ) ).post( uriForResourceCreation );
		assertThat( secondCreationResponse.getStatusCode(), is( 201 ) );
	}
	
	@Test
	public final void whenResourceIsRetrieved_thenURIToGetAllResourcesIsDiscoverable(){
		// Given
		final String uriOfExistingResource = this.restTemplate.createResource();
		
		// When
		final Response getResponse = this.givenAuthenticated().get( uriOfExistingResource );
		
		// Then
		final String uriToAllResources = HTTPLinkHeaderUtils.extractURIByRel( getResponse.getHeader( "Link" ), RESTURIUtil.REL_COLLECTION );
		
		final Response getAllResponse = this.givenAuthenticated().get( uriToAllResources );
		assertThat( getAllResponse.getStatusCode(), is( 200 ) );
	}
	
	@Test
	public final void whenGetIsDoneOnRoot_thenSomeURIAreDiscoverable(){
		// When
		final Response getOnRootResponse = this.givenAuthenticated().get( this.paths.getRootURL() );
		
		// Then
		final List< String > allURIsDiscoverableFromRoot = HTTPLinkHeaderUtils.extractAllURIs( getOnRootResponse.getHeader( HttpConstants.LINK_HEADER ) );
		
		assertThat( allURIsDiscoverableFromRoot, not( Matchers.<String> empty() ) );
	}
	@Test
	public final void whenGetIsDoneOnRoot_thenFooURIIsDiscoverable(){
		// When
		final Response getOnRootResponse = this.givenAuthenticated().get( this.paths.getRootURL() );
		
		// Then
		final List< String > allURIsDiscoverableFromRoot = HTTPLinkHeaderUtils.extractAllURIs( getOnRootResponse.getHeader( HttpConstants.LINK_HEADER ) );
		final int indexOfFooUri = Iterables.indexOf( allURIsDiscoverableFromRoot, Predicates.containsPattern( this.paths.getFooURL() ) );
		assertThat( indexOfFooUri, greaterThanOrEqualTo( 0 ) );
	}
	
	// GET (all)
	
	// POST
	
	@SuppressWarnings( "unchecked" )
	@Test
	public final void whenInvalidPOSTIsSentToValidURIOfResource_thenAllowHeaderListsTheAllowedActions(){
		// Given
		final String uriOfExistingResource = this.restTemplate.createResource();
		
		// When
		final Response res = this.givenAuthenticated().post( uriOfExistingResource );
		
		// Then
		final String allowHeader = res.getHeader( HttpHeaders.ALLOW );
		assertThat( allowHeader, AnyOf.<String> anyOf( containsString( "GET" ), containsString( "PUT" ), containsString( "DELETE" ) ) );
	}
	
	@Test
	public final void whenResourceIsCreated_thenURIOfTheNewlyCreatedResourceIsDiscoverable(){
		// When
		final Foo unpersistedResource = new Foo( randomAlphabetic( 6 ) );
		final Response createResp = this.givenAuthenticated().contentType( HttpConstants.MIME_JSON ).body( unpersistedResource ).post( this.paths.getFooURL() );
		final String uriOfNewlyCreatedResource = createResp.getHeader( HttpHeaders.LOCATION );
		
		// Then
		final Response response = this.givenAuthenticated().header( HttpHeaders.ACCEPT, HttpConstants.MIME_JSON ).get( uriOfNewlyCreatedResource );
		
		final Foo resourceFromServer = response.body().as( Foo.class );
		assertThat( unpersistedResource, equalTo( resourceFromServer ) );
	}
	
	// PUT
	
	// DELETE
	
	// util
	
	private final RequestSpecification givenAuthenticated(){
		return this.securityComponent.givenAuthenticatedByBasicAuth();
	}
	
}

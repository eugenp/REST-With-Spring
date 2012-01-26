package org.rest.web.root;

import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;

import java.util.List;

import org.hamcrest.Matchers;
import org.junit.Test;
import org.rest.common.util.HttpConstants;
import org.rest.test.integration.test.AbstractRESTIntegrationTest;
import org.rest.testing.ExamplePaths;
import org.rest.testing.security.SecurityUtil;
import org.rest.web.http.HTTPLinkHeaderUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.google.common.base.Predicates;
import com.google.common.collect.Iterables;
import com.jayway.restassured.response.Response;
import com.jayway.restassured.specification.RequestSpecification;

public class RootRESTDiscoverabilityIntegrationTest extends AbstractRESTIntegrationTest{
	
	@Autowired
	ExamplePaths paths;
	
	// tests
	
	// GET
	
	@Test
	public final void whenGetIsDoneOnRoot_thenSomeURIAreDiscoverable(){
		// When
		final Response getOnRootResponse = givenAuthenticated().get( paths.getRootUri() );
		
		// Then
		final List< String > allURIsDiscoverableFromRoot = HTTPLinkHeaderUtils.extractAllURIs( getOnRootResponse.getHeader( HttpConstants.LINK_HEADER ) );
		
		assertThat( allURIsDiscoverableFromRoot, not( Matchers.<String> empty() ) );
	}
	
	@Test
	public final void whenGetIsDoneOnRoot_thenEntityURIIsDiscoverable(){
		// When
		final Response getOnRootResponse = givenAuthenticated().get( paths.getRootUri() );
		
		// Then
		final List< String > allURIsDiscoverableFromRoot = HTTPLinkHeaderUtils.extractAllURIs( getOnRootResponse.getHeader( HttpConstants.LINK_HEADER ) );
		final int indexOfEntityUri = Iterables.indexOf( allURIsDiscoverableFromRoot, Predicates.containsPattern( paths.getFooUri() ) );
		assertThat( indexOfEntityUri, greaterThanOrEqualTo( 0 ) );
	}
	
	// util
	
	protected final RequestSpecification givenAuthenticated(){
		return SecurityUtil.givenBasicAuthenticatedAsAdmin();
	}
	
}

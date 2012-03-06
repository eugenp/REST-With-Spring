package org.rest.sec.web.role;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasItem;
import static org.junit.Assert.assertThat;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.rest.client.template.impl.PrivilegeRESTTemplateImpl;
import org.rest.client.template.impl.RoleRESTTemplateImpl;
import org.rest.sec.model.Role;
import org.rest.spring.client.ClientTestConfig;
import org.rest.spring.context.ContextTestConfig;
import org.rest.spring.testing.TestingTestConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import com.jayway.restassured.response.Response;
import com.jayway.restassured.specification.RequestSpecification;

@RunWith( SpringJUnit4ClassRunner.class )
@ContextConfiguration( classes = { ClientTestConfig.class, TestingTestConfig.class, ContextTestConfig.class },loader = AnnotationConfigContextLoader.class )
public class RoleSearchRESTIntegrationTest{
	
	@Autowired private RoleRESTTemplateImpl restTemplate;
	@Autowired private PrivilegeRESTTemplateImpl associationRestTemplate;
	
	public RoleSearchRESTIntegrationTest(){
		super();
	}
	
	// tests
	
	// search - by id
	
	@Test
	public final void givenResourceExists_whenResourceIfSearchedById_thenNoExceptions(){
		final Role existingResource = getTemplate().create( getTemplate().createNewEntity() );
		getTemplate().searchAsResponse( existingResource.getId(), null );
	}
	@Test
	public final void givenResourceExists_whenResourceIfSearchedById_then200IsReceived(){
		final Role existingResource = getTemplate().create( getTemplate().createNewEntity() );
		
		// When
		final Response searchResponse = getTemplate().searchAsResponse( existingResource.getId(), null );
		
		// Then
		assertThat( searchResponse.getStatusCode(), is( 200 ) );
	}
	
	@Test
	public final void givenResourceExists_whenResourceIfSearchedByIdAndUnmarshalled_thenNoException(){
		final Role existingResource = getTemplate().create( getTemplate().createNewEntity() );
		getTemplate().search( existingResource.getId(), null );
	}
	@Test
	public final void givenResourceExists_whenResourceIfSearchedByIdAndUnmarshalled_thenResourceIsFound(){
		final Role existingResource = getTemplate().create( getTemplate().createNewEntity() );
		
		// When
		final List< Role > found = getTemplate().search( existingResource.getId(), null );
		
		// Then
		assertThat( found, hasItem( existingResource ) );
	}
	
	// search - by name
	
	@Test
	public final void givenResourceExists_whenResourceIfSearchedByName_thenNoExceptions(){
		final Role existingResource = getTemplate().create( getTemplate().createNewEntity() );
		getTemplate().searchAsResponse( null, existingResource.getName() );
	}
	@Test
	public final void givenResourceExists_whenResourceIfSearchedByName_then200IsReceived(){
		final Role existingResource = getTemplate().create( getTemplate().createNewEntity() );
		
		// When
		final Response searchResponse = getTemplate().searchAsResponse( null, existingResource.getName() );
		
		// Then
		assertThat( searchResponse.getStatusCode(), is( 200 ) );
	}
	
	/*
	
	@Test
	public final void givenResourceExists_whenResourceIfSearchedByNameAndUnmarshalled_thenNoException(){
		final Role existingResource = getTemplate().create( getTemplate().createNewEntity() );
		getTemplate().search( existingResource.getId() );
	}
	@Test
	public final void givenResourceExists_whenResourceIfSearchedByNameAndUnmarshalled_thenResourceIsFound(){
		final Role existingResource = getTemplate().create( getTemplate().createNewEntity() );
		
		// When
		final List< Role > found = getTemplate().search( existingResource.getId() );
		
		// Then
		assertThat( found, hasItem( existingResource ) );
	}*/
	
	// template
	
	protected final Role createNewEntity(){
		return restTemplate.createNewEntity();
	}
	protected final RoleRESTTemplateImpl getTemplate(){
		return restTemplate;
	}
	protected final String getURI(){
		return getTemplate().getURI() + "/";
	}
	protected final void change( final Role resource ){
		resource.setName( randomAlphabetic( 6 ) );
	}
	protected final void invalidate( final Role resource ){
		getTemplate().invalidate( resource );
	}
	protected final RequestSpecification givenAuthenticated(){
		return getTemplate().givenAuthenticated();
	}
	
	// util
	
	final PrivilegeRESTTemplateImpl getAssociationTemplate(){
		return associationRestTemplate;
	}
	
}

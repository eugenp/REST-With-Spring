package org.rest.sec.web.role;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

import org.hamcrest.Matchers;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.rest.sec.model.Privilege;
import org.rest.sec.model.Role;
import org.rest.sec.testing.template.PrivilegeRESTTemplateImpl;
import org.rest.sec.testing.template.RoleRESTTemplateImpl;
import org.rest.spring.application.ContextTestConfig;
import org.rest.spring.persistence.jpa.PersistenceJPAConfig;
import org.rest.web.common.AbstractLogicRESTIntegrationTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import com.google.common.collect.Sets;
import com.jayway.restassured.response.Response;
import com.jayway.restassured.specification.RequestSpecification;

@RunWith( SpringJUnit4ClassRunner.class )
@ContextConfiguration( classes = { ContextTestConfig.class, PersistenceJPAConfig.class, ContextTestConfig.class },loader = AnnotationConfigContextLoader.class )
public class RoleLogicRESTIntegrationTest extends AbstractLogicRESTIntegrationTest< Role >{
	
	@Autowired private RoleRESTTemplateImpl restTemplate;
	@Autowired private PrivilegeRESTTemplateImpl associationRestTemplate;
	
	public RoleLogicRESTIntegrationTest(){
		super( Role.class );
	}
	
	// tests
	
	// find one
	
	@Test
	public final void givenResourceExists_whenResourceIsRetrievedByName_thenResourceIsCorrectlyRetrieved(){
		final Role newResource = getTemplate().createNewEntity();
		getTemplate().create( newResource );
		final Role existingResourceByName = getTemplate().findByName( newResource.getName() );
		assertEquals( newResource, existingResourceByName );
	}
	
	// TO SORT
	
	/**
	 * - note: this test ensures that a new User cannot automatically create new Privileges <br>
	 * - note: the standard way to do this is: first create the Privilege resource(s), then associate them with the new User resource and then create the User resource
	 */
	@Test
	public final void whenRoleIsCreatedWithNewPrivilege_then409IsReceived(){
		final Role newResource = getTemplate().createNewEntity();
		newResource.getPrivileges().add( associationRestTemplate.createNewEntity() );
		
		// When
		final Response response = getTemplate().createResourceAsResponse( newResource );
		
		// Then
		assertThat( response.getStatusCode(), is( 409 ) );
	}
	@Test
	public final void whenRoleIsCreatedWithExistingPrivilege_then201IsReceived(){
		final Privilege existingAssociation = associationRestTemplate.create( associationRestTemplate.createNewEntity() );
		final Role newResource = getTemplate().createNewEntity();
		newResource.getPrivileges().add( existingAssociation );
		
		// When
		final Response response = getTemplate().createResourceAsResponse( newResource );
		
		// Then
		assertThat( response.getStatusCode(), is( 201 ) );
	}
	
	/** - note: this may intermittently fail (investigate if that's the case) */
	@Test
	public final void whenResourceIsCreatedWithInvalidAssociation_then409IsReceived(){
		final Privilege invalidAssociation = associationRestTemplate.createNewEntity();
		invalidAssociation.setId( 1001l );
		final Role newResource = getTemplate().createNewEntity();
		newResource.getPrivileges().add( invalidAssociation );
		
		// When
		final Response response = getTemplate().createResourceAsResponse( newResource );
		
		// Then
		assertThat( response.getStatusCode(), is( 409 ) );
	}
	
	@Test
	public final void whenResourceIsRetrieved_thenAssociationsAreAlsoRetrieved(){
		final Role resource = getTemplate().create( getTemplate().createNewEntity() );
		assertThat( resource.getPrivileges(), not( Matchers.<Privilege> empty() ) );
	}
	
	@Test
	public final void whenScenario_getResource_getAssociationsById(){
		final Privilege existingAssociation = associationRestTemplate.create( associationRestTemplate.createNewEntity() );
		final Role resourceToCreate = getTemplate().createNewEntity();
		resourceToCreate.getPrivileges().add( existingAssociation );
		
		// When
		final Role existingResource = getTemplate().create( resourceToCreate );
		for( final Privilege associationOfResourcePotential : existingResource.getPrivileges() ){
			final Privilege existingAssociationOfResource = associationRestTemplate.getResourceAsEntity( associationRestTemplate.getURI() + "/" + associationOfResourcePotential.getId() );
			assertThat( existingAssociationOfResource, notNullValue() );
		}
	}
	
	@Test
	public final void whenScenario_changeAssociationOfResource(){
		final Role existingResource = getTemplate().create( getTemplate().createNewEntity() );
		final Privilege existingAssociation = associationRestTemplate.create( associationRestTemplate.createNewEntity() );
		existingResource.setPrivileges( Sets.newHashSet( existingAssociation ) );
		
		getTemplate().update( existingResource );
		final Role updatedResource = getTemplate().findOne( existingResource.getId() );
		assertThat( updatedResource.getPrivileges(), hasItem( existingAssociation ) );
	}
	
	// scenarios
	
	@Test
	public final void whenScenarioOfWorkingWithAssociations_thenTheChangesAreCorrectlyPersisted(){
		final Privilege existingAssociation = associationRestTemplate.create( associationRestTemplate.createNewEntity() );
		final Role resource1 = new Role( randomAlphabetic( 6 ), Sets.newHashSet( existingAssociation ) );
		
		final Role resource1ViewOfServerBefore = getTemplate().create( resource1 );
		assertThat( resource1ViewOfServerBefore.getPrivileges(), hasItem( existingAssociation ) );
		
		final Role resource2 = new Role( randomAlphabetic( 6 ), Sets.newHashSet( existingAssociation ) );
		getTemplate().createResourceAsResponse( resource2 );
		
		final Role resource1ViewOfServerAfter = getTemplate().findOne( resource1ViewOfServerBefore.getId() );
		assertThat( resource1ViewOfServerAfter.getPrivileges(), hasItem( existingAssociation ) );
	}
	
	@Test
	// TODO: unignore
	@Ignore( "IN PROGRESS" )
	public final void whenCreatingNewResourceWithExistingAssociations_thenNewResourceIsCorrectlyCreated(){
		final Privilege existingAssociation = getAssociationTemplate().create( getAssociationTemplate().createNewEntity() );
		final Role newResource = getTemplate().createNewEntity();
		newResource.getPrivileges().add( existingAssociation );
		getTemplate().create( newResource );
		
		final Role newResource2 = getTemplate().createNewEntity();
		newResource2.getPrivileges().add( existingAssociation );
		getTemplate().create( newResource2 );
	}
	
	// template
	
	@Override
	protected final Role createNewEntity(){
		return restTemplate.createNewEntity();
	}
	@Override
	protected final RoleRESTTemplateImpl getTemplate(){
		return restTemplate;
	}
	@Override
	protected final String getURI(){
		return getTemplate().getURI() + "/";
	}
	@Override
	protected final void change( final Role resource ){
		resource.setName( randomAlphabetic( 6 ) );
	}
	@Override
	protected final void makeInvalid( final Role resource ){
		getTemplate().makeEntityInvalid( resource );
	}
	@Override
	protected final RequestSpecification givenAuthenticated(){
		return getTemplate().givenAuthenticated();
	}
	
	// util
	
	final PrivilegeRESTTemplateImpl getAssociationTemplate(){
		return associationRestTemplate;
	}
	
}

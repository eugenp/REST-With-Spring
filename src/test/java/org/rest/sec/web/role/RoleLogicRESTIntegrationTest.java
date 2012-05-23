package org.rest.sec.web.role;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

import java.util.Set;

import org.hamcrest.Matchers;
import org.junit.Test;
import org.rest.sec.client.template.PrivilegeRESTTemplateImpl;
import org.rest.sec.client.template.RoleRESTTemplateImpl;
import org.rest.sec.model.Privilege;
import org.rest.sec.model.Role;
import org.rest.sec.test.SecLogicRESTIntegrationTest;
import org.springframework.beans.factory.annotation.Autowired;

import com.google.common.base.Preconditions;
import com.google.common.collect.Sets;
import com.jayway.restassured.response.Response;
import com.jayway.restassured.specification.RequestSpecification;

public class RoleLogicRESTIntegrationTest extends SecLogicRESTIntegrationTest< Role >{
	
	@Autowired private RoleRESTTemplateImpl restTemplate;
	@Autowired private PrivilegeRESTTemplateImpl associationRestTemplate;
	
	public RoleLogicRESTIntegrationTest(){
		super( Role.class );
	}
	
	// tests
	
	// find one
	
	@Test
	public final void givenResourceExists_whenResourceIsRetrievedByName_thenResourceIsCorrectlyRetrieved(){
		final Role newResource = getAPI().createNewEntity();
		getAPI().create( newResource );
		final Role existingResourceByName = getAPI().findByName( newResource.getName() );
		assertEquals( newResource, existingResourceByName );
	}
	
	@Test
	public final void whenResourceIsRetrieved_thenAssociationsAreAlsoRetrieved(){
		final Role existingResource = getAPI().create( getAPI().createNewEntity() );
		assertThat( existingResource.getPrivileges(), not( Matchers.<Privilege> empty() ) );
	}
	
	// create
	
	/**
	 * - note: this test ensures that a new User cannot automatically create new Privileges <br>
	 * - note: the standard way to do this is: first create the Privilege resource(s), then associate them with the new User resource and then create the User resource
	 */
	@Test
	public final void whenRoleIsCreatedWithNewPrivilege_then409IsReceived(){
		final Role newResource = getAPI().createNewEntity();
		newResource.getPrivileges().add( getAssociationTemplate().createNewEntity() );
		
		// When
		final Response response = getAPI().createAsResponse( newResource );
		
		// Then
		assertThat( response.getStatusCode(), is( 409 ) );
	}
	
	@Test
	public final void whenRoleIsCreatedWithExistingPrivilege_then201IsReceived(){
		final Privilege existingAssociation = getAssociationTemplate().create( getAssociationTemplate().createNewEntity() );
		final Role newResource = getAPI().createNewEntity();
		newResource.getPrivileges().add( existingAssociation );
		
		// When
		final Response response = getAPI().createAsResponse( newResource );
		
		// Then
		assertThat( response.getStatusCode(), is( 201 ) );
	}
	
	@Test
	public final void whenResourceIsCreatedWithInvalidAssociation_then409IsReceived(){
		final Privilege invalidAssociation = getAssociationTemplate().createNewEntity();
		getAssociationTemplate().invalidate( invalidAssociation );
		final Role newResource = getAPI().createNewEntity();
		newResource.getPrivileges().add( invalidAssociation );
		
		// When
		final Response response = getAPI().createAsResponse( newResource );
		
		// Then
		assertThat( response.getStatusCode(), is( 409 ) );
	}
	
	@Test
	public final void whenCreatingNewResourceWithExistingAssociation_thenAssociationsAreCorrectlyPersisted(){
		final Privilege existingAssociation = getAssociationTemplate().create( getAssociationTemplate().createNewEntity() );
		final Role resourceToCreate = getAPI().createNewEntity();
		resourceToCreate.getPrivileges().add( existingAssociation );
		
		// When
		final Role existingResource = getAPI().create( resourceToCreate );
		final Set< Privilege > associationsOfExistingResource = existingResource.getPrivileges();
		Preconditions.checkState( associationsOfExistingResource.size() == 1 );
		
		assertThat( existingAssociation, equalTo( associationsOfExistingResource.iterator().next() ) );
	}
	
	// update
	
	@Test
	public final void givenResourceExists_whenResourceIsUpdatedWithExistingAsscoaition_thenAssociationIsCorrectlyUpdated(){
		// Given
		final Role existingResource = getAPI().create( getAPI().createNewEntity() );
		final Privilege existingAssociation = getAssociationTemplate().create( getAssociationTemplate().createNewEntity() );
		existingResource.setPrivileges( Sets.newHashSet( existingAssociation ) );
		
		// When
		getAPI().update( existingResource );
		
		// Given
		final Role updatedResource = getAPI().findOne( existingResource.getId() );
		assertThat( updatedResource.getPrivileges(), hasItem( existingAssociation ) );
	}
	
	// scenarios
	
	@Test
	public final void whenScenarioOfWorkingWithAssociations_thenTheChangesAreCorrectlyPersisted(){
		final Privilege existingAssociation = getAssociationTemplate().create( getAssociationTemplate().createNewEntity() );
		final Role resource1 = new Role( randomAlphabetic( 6 ), Sets.newHashSet( existingAssociation ) );
		
		final Role resource1ViewOfServerBefore = getAPI().create( resource1 );
		assertThat( resource1ViewOfServerBefore.getPrivileges(), hasItem( existingAssociation ) );
		
		final Role resource2 = new Role( randomAlphabetic( 6 ), Sets.newHashSet( existingAssociation ) );
		getAPI().create( resource2 );
		
		final Role resource1ViewOfServerAfter = getAPI().findOne( resource1ViewOfServerBefore.getId() );
		assertThat( resource1ViewOfServerAfter.getPrivileges(), hasItem( existingAssociation ) );
	}
	
	@Test
	public final void whenCreatingNewResourceWithExistingAssociations_thenNewResourceIsCorrectlyCreated(){
		final Privilege existingAssociation = getAssociationTemplate().create( getAssociationTemplate().createNewEntity() );
		final Role newResource = getAPI().createNewEntity();
		newResource.getPrivileges().add( existingAssociation );
		getAPI().create( newResource );
		
		final Role newResource2 = getAPI().createNewEntity();
		newResource2.getPrivileges().add( existingAssociation );
		getAPI().create( newResource2 );
	}
	
	// template
	
	@Override
	protected final RoleRESTTemplateImpl getAPI(){
		return restTemplate;
	}
	
	@Override
	protected final String getURI(){
		return getAPI().getURI() + "/";
	}
	
	@Override
	protected final RequestSpecification givenAuthenticated(){
		return getAPI().givenAuthenticated();
	}
	
	// util
	
	final PrivilegeRESTTemplateImpl getAssociationTemplate(){
		return associationRestTemplate;
	}
	
}

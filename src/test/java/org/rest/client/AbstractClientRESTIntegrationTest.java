package org.rest.client;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.util.Random;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.rest.common.IEntity;
import org.rest.spring.application.ApplicationTestConfig;
import org.rest.spring.client.ClientTestConfig;
import org.rest.spring.persistence.jpa.PersistenceJPAConfig;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

@RunWith( SpringJUnit4ClassRunner.class )
@ContextConfiguration( classes = { ApplicationTestConfig.class, PersistenceJPAConfig.class, ClientTestConfig.class },loader = AnnotationConfigContextLoader.class )
public abstract class AbstractClientRESTIntegrationTest< T extends IEntity >{
	
	public AbstractClientRESTIntegrationTest(){
		super();
	}
	
	@Rule public ExpectedException thrown = ExpectedException.none();
	
	// tests
	
	// find one
	
	@Test
	public final void givenResourceExists_whenResourceIsRetrieved_thenResourceIsCorrectlyRetrieved(){
		// Given
		final T newResource = getTemplate().createNewEntity();
		final String uriOfExistingResource = getTemplate().createAsURI( newResource );
		
		// When
		final T createdResource = getTemplate().findOneByURI( uriOfExistingResource );
		
		// Then
		assertEquals( createdResource, newResource );
	}
	
	@Test
	public final void givenResourceDoesNotExist_whenResourceIsRetrieved_thenNoResourceIsReceived(){
		// When
		final T createdResource = getTemplate().findOne( randomPositiveLong() );
		
		// Then
		assertNull( createdResource );
	}
	
	// delete
	
	@Test
	public final void givenResourceExists_whenResourceIsDeleted_thenResourceNoLongerExists(){
		// Given
		final T existingResource = getTemplate().givenAuthenticated().create( getTemplate().createNewEntity() );
		
		// When
		getTemplate().delete( existingResource.getId() );
		
		// Then
		assertNull( getTemplate().findOne( existingResource.getId() ) );
	}
	
	// template method
	
	protected abstract AbstractClientRestTemplate< T > getTemplate();
	protected abstract String getURI();
	
	// util
	
	private final long randomPositiveLong(){
		long id = new Random().nextLong() * 10000;
		id = ( id < 0 ) ? ( -1 * id ) : id;
		return id;
	}
	
}

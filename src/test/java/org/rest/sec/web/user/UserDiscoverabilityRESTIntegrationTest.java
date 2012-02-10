package org.rest.sec.web.user;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;

import org.junit.Ignore;
import org.junit.runner.RunWith;
import org.rest.sec.dto.User;
import org.rest.sec.testing.template.UserRESTTemplateImpl;
import org.rest.spring.application.ApplicationTestConfig;
import org.rest.spring.persistence.jpa.PersistenceJPAConfig;
import org.rest.web.common.AbstractDiscoverabilityRESTIntegrationTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import com.jayway.restassured.specification.RequestSpecification;

@RunWith( SpringJUnit4ClassRunner.class )
@ContextConfiguration( classes = { ApplicationTestConfig.class, PersistenceJPAConfig.class },loader = AnnotationConfigContextLoader.class )
@Ignore( "user and principal work is still in progress" )
public class UserDiscoverabilityRESTIntegrationTest extends AbstractDiscoverabilityRESTIntegrationTest< User >{
	
	@Autowired
	private UserRESTTemplateImpl restTemplate;
	
	public UserDiscoverabilityRESTIntegrationTest(){
		super( User.class );
	}
	
	// tests
	
	// template method
	
	@Override
	protected final String getURI(){
		return getTemplate().getURI();
	}
	@Override
	protected final void change( final User resource ){
		resource.setName( randomAlphabetic( 6 ) );
	}
	@Override
	protected final User createNewEntity(){
		return restTemplate.createNewEntity();
	}
	@Override
	protected final RequestSpecification givenAuthenticated(){
		return getTemplate().givenAuthenticated();
	}
	
	@Override
	protected final UserRESTTemplateImpl getTemplate(){
		return restTemplate;
	}
	
}

package org.rest.sec.web.user;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;

import org.junit.runner.RunWith;
import org.rest.client.template.impl.UserRESTTemplateImpl;
import org.rest.sec.dto.User;
import org.rest.spring.testing.TestingTestConfig;
import org.rest.web.common.AbstractDiscoverabilityRESTIntegrationTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import com.jayway.restassured.specification.RequestSpecification;

@RunWith( SpringJUnit4ClassRunner.class )
@ContextConfiguration( classes = { TestingTestConfig.class },loader = AnnotationConfigContextLoader.class )
public class UserDiscoverabilityRESTIntegrationTest extends AbstractDiscoverabilityRESTIntegrationTest< User >{
	
	@Autowired private UserRESTTemplateImpl restTemplate;
	
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

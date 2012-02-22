package org.rest.sec.web.role;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;

import org.junit.runner.RunWith;
import org.rest.sec.model.Role;
import org.rest.sec.testing.template.RoleRESTTemplateImpl;
import org.rest.spring.application.ContextTestConfig;
import org.rest.spring.persistence.jpa.PersistenceJPAConfig;
import org.rest.web.common.AbstractDiscoverabilityRESTIntegrationTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import com.jayway.restassured.specification.RequestSpecification;

@RunWith( SpringJUnit4ClassRunner.class )
@ContextConfiguration( classes = { ContextTestConfig.class, PersistenceJPAConfig.class },loader = AnnotationConfigContextLoader.class )
public class RoleDiscoverabilityRESTIntegrationTest extends AbstractDiscoverabilityRESTIntegrationTest< Role >{
	
	@Autowired private RoleRESTTemplateImpl restTemplate;
	
	public RoleDiscoverabilityRESTIntegrationTest(){
		super( Role.class );
	}
	
	// tests
	
	// template method
	
	@Override
	protected final Role createNewEntity(){
		return restTemplate.createNewEntity();
	}
	@Override
	protected final String getURI(){
		return getTemplate().getURI();
	}
	@Override
	protected void change( final Role resource ){
		resource.setName( randomAlphabetic( 6 ) );
	}
	@Override
	protected RequestSpecification givenAuthenticated(){
		return getTemplate().givenAuthenticated();
	}
	
	@Override
	protected final RoleRESTTemplateImpl getTemplate(){
		return restTemplate;
	}
	
}

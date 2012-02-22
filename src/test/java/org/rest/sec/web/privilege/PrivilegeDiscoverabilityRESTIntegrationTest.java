package org.rest.sec.web.privilege;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;

import org.junit.runner.RunWith;
import org.rest.sec.model.Privilege;
import org.rest.sec.testing.template.PrivilegeRESTTemplateImpl;
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
public class PrivilegeDiscoverabilityRESTIntegrationTest extends AbstractDiscoverabilityRESTIntegrationTest< Privilege >{

	@Autowired private PrivilegeRESTTemplateImpl restTemplate;
	
	public PrivilegeDiscoverabilityRESTIntegrationTest(){
		super( Privilege.class );
	}
	
	// tests
	
	// template method
	
	@Override
	protected final Privilege createNewEntity(){
		return restTemplate.createNewEntity();
	}
	@Override
	protected final String getURI(){
		return getTemplate().getURI();
	}
	@Override
	protected void change( final Privilege resource ){
		resource.setName( randomAlphabetic( 6 ) );
	}
	@Override
	protected RequestSpecification givenAuthenticated(){
		return getTemplate().givenAuthenticated();
	}
	
	@Override
	protected final PrivilegeRESTTemplateImpl getTemplate(){
		return restTemplate;
	}
	
}

package org.rest.sec.web.privilege;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;

import org.junit.runner.RunWith;
import org.rest.sec.model.Privilege;
import org.rest.sec.testing.template.PrivilegeRESTTemplateImpl;
import org.rest.spring.application.ContextTestConfig;
import org.rest.spring.persistence.jpa.PersistenceJPAConfig;
import org.rest.web.common.AbstractLogicRESTIntegrationTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import com.jayway.restassured.specification.RequestSpecification;

@RunWith( SpringJUnit4ClassRunner.class )
@ContextConfiguration( classes = { ContextTestConfig.class, PersistenceJPAConfig.class },loader = AnnotationConfigContextLoader.class )
public class PrivilegeLogicRESTIntegrationTest extends AbstractLogicRESTIntegrationTest< Privilege >{
	
	@Autowired
	private PrivilegeRESTTemplateImpl restTemplate;
	
	public PrivilegeLogicRESTIntegrationTest(){
		super( Privilege.class );
	}
	
	// tests

	// template
	
	@Override
	protected final Privilege createNewEntity(){
		return restTemplate.createNewEntity();
	}
	@Override
	protected final PrivilegeRESTTemplateImpl getTemplate(){
		return restTemplate;
	}
	@Override
	protected final String getURI(){
		return getTemplate().getURI() + "/";
	}
	@Override
	protected final void change( final Privilege resource ){
		resource.setName( randomAlphabetic( 6 ) );
	}
	@Override
	protected final void makeInvalid( final Privilege resource ){
		getTemplate().makeEntityInvalid( resource );
	}
	@Override
	protected final RequestSpecification givenAuthenticated(){
		return getTemplate().givenAuthenticated();
	}
	
}

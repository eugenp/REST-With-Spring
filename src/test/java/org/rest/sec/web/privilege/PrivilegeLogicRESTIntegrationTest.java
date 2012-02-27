package org.rest.sec.web.privilege;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;

import org.junit.runner.RunWith;
import org.rest.client.template.impl.PrivilegeRESTTemplateImpl;
import org.rest.sec.model.Privilege;
import org.rest.spring.testing.TestingTestConfig;
import org.rest.web.common.AbstractLogicRESTIntegrationTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import com.jayway.restassured.specification.RequestSpecification;

@RunWith( SpringJUnit4ClassRunner.class )
@ContextConfiguration( classes = { TestingTestConfig.class },loader = AnnotationConfigContextLoader.class )
public class PrivilegeLogicRESTIntegrationTest extends AbstractLogicRESTIntegrationTest< Privilege >{
	
	@Autowired private PrivilegeRESTTemplateImpl restTemplate;
	
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
	protected final void invalidate( final Privilege resource ){
		getTemplate().invalidate( resource );
	}
	@Override
	protected final RequestSpecification givenAuthenticated(){
		return getTemplate().givenAuthenticated();
	}
	
}

package org.rest.sec.web.role;

import org.junit.runner.RunWith;
import org.rest.client.template.impl.RoleRESTTemplateImpl;
import org.rest.sec.model.Role;
import org.rest.spring.testing.TestingTestConfig;
import org.rest.web.common.AbstractMimeRESTIntegrationTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

@RunWith( SpringJUnit4ClassRunner.class )
@ContextConfiguration( classes = { TestingTestConfig.class },loader = AnnotationConfigContextLoader.class )
public class RoleMimeRESTIntegrationTest extends AbstractMimeRESTIntegrationTest< Role >{
	
	@Autowired private RoleRESTTemplateImpl restTemplate;
	
	public RoleMimeRESTIntegrationTest(){
		super( Role.class );
	}
	
	// tests
	
	// template method
	
	@Override
	protected final RoleRESTTemplateImpl getTemplate(){
		return restTemplate;
	}
	
}

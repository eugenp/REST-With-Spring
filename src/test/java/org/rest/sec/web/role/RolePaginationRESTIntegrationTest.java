package org.rest.sec.web.role;

import org.junit.runner.RunWith;
import org.rest.client.template.impl.RoleRESTTemplateImpl;
import org.rest.sec.model.Role;
import org.rest.spring.testing.TestingTestConfig;
import org.rest.web.common.AbstractPaginationRESTIntegrationTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

@RunWith( SpringJUnit4ClassRunner.class )
@ContextConfiguration( classes = { TestingTestConfig.class },loader = AnnotationConfigContextLoader.class )
public class RolePaginationRESTIntegrationTest extends AbstractPaginationRESTIntegrationTest< Role >{
	
	@Autowired private RoleRESTTemplateImpl template;
	
	// tests
	
	// template method (shortcuts)
	
	@Override
	protected final Role createNewEntity(){
		return template.createNewEntity();
	}
	
	@Override
	protected final String getURI(){
		return template.getURI();
	}
	
	@Override
	protected final RoleRESTTemplateImpl getTemplate(){
		return template;
	}
	
}

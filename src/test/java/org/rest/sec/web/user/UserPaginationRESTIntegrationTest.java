package org.rest.sec.web.user;

import org.junit.runner.RunWith;
import org.rest.client.template.impl.UserRESTTemplateImpl;
import org.rest.sec.dto.User;
import org.rest.spring.testing.TestingTestConfig;
import org.rest.web.common.AbstractPaginationRESTIntegrationTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

@RunWith( SpringJUnit4ClassRunner.class )
@ContextConfiguration( classes = { TestingTestConfig.class },loader = AnnotationConfigContextLoader.class )
public class UserPaginationRESTIntegrationTest extends AbstractPaginationRESTIntegrationTest< User >{
	
	@Autowired private UserRESTTemplateImpl template;
	
	// tests
	
	// template method (shortcuts)
	
	@Override
	protected final User createNewEntity(){
		return template.createNewEntity();
	}
	
	@Override
	protected final String getURI(){
		return template.getURI();
	}
	
	@Override
	protected final UserRESTTemplateImpl getTemplate(){
		return template;
	}
	
}

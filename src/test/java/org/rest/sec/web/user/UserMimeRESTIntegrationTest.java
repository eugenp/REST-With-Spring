package org.rest.sec.web.user;

import org.junit.runner.RunWith;
import org.rest.client.template.impl.UserRESTTemplateImpl;
import org.rest.sec.dto.User;
import org.rest.spring.testing.TestingTestConfig;
import org.rest.web.common.AbstractMimeRESTIntegrationTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

@RunWith( SpringJUnit4ClassRunner.class )
@ContextConfiguration( classes = { TestingTestConfig.class },loader = AnnotationConfigContextLoader.class )
public class UserMimeRESTIntegrationTest extends AbstractMimeRESTIntegrationTest< User >{
	
	@Autowired private UserRESTTemplateImpl restTemplate;
	
	public UserMimeRESTIntegrationTest(){
		super( User.class );
	}
	
	// tests
	
	// template method
	
	@Override
	protected final UserRESTTemplateImpl getTemplate(){
		return restTemplate;
	}
	
}

package org.rest.sec.web.user;

import org.junit.runner.RunWith;
import org.rest.sec.model.User;
import org.rest.sec.testing.template.UserRESTTemplateImpl;
import org.rest.spring.application.ApplicationTestConfig;
import org.rest.spring.persistence.jpa.PersistenceJPAConfig;
import org.rest.web.common.AbstractMimeRESTIntegrationTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

@RunWith( SpringJUnit4ClassRunner.class )
@ContextConfiguration( classes = { ApplicationTestConfig.class, PersistenceJPAConfig.class },loader = AnnotationConfigContextLoader.class )
public class UserMimeRESTIntegrationTest extends AbstractMimeRESTIntegrationTest< User >{
	
	@Autowired
	private UserRESTTemplateImpl restTemplate;
	
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

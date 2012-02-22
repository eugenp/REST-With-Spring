package org.rest.sec.web.role;

import org.junit.runner.RunWith;
import org.rest.sec.model.Role;
import org.rest.sec.testing.template.RoleRESTTemplateImpl;
import org.rest.spring.application.ContextTestConfig;
import org.rest.spring.persistence.jpa.PersistenceJPAConfig;
import org.rest.web.common.AbstractMimeRESTIntegrationTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

@RunWith( SpringJUnit4ClassRunner.class )
@ContextConfiguration( classes = { ContextTestConfig.class, PersistenceJPAConfig.class },loader = AnnotationConfigContextLoader.class )
public class RoleMimeRESTIntegrationTest extends AbstractMimeRESTIntegrationTest< Role >{
	
	@Autowired
	private RoleRESTTemplateImpl restTemplate;
	
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

package org.rest.sec.web.privilege;

import org.junit.runner.RunWith;
import org.rest.sec.model.Privilege;
import org.rest.sec.testing.template.PrivilegeRESTTemplateImpl;
import org.rest.spring.application.ContextTestConfig;
import org.rest.spring.persistence.jpa.PersistenceJPAConfig;
import org.rest.web.common.AbstractMimeRESTIntegrationTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

@RunWith( SpringJUnit4ClassRunner.class )
@ContextConfiguration( classes = { ContextTestConfig.class, PersistenceJPAConfig.class },loader = AnnotationConfigContextLoader.class )
public class PrivilegeMimeRESTIntegrationTest extends AbstractMimeRESTIntegrationTest< Privilege >{
	
	@Autowired
	private PrivilegeRESTTemplateImpl restTemplate;
	
	public PrivilegeMimeRESTIntegrationTest(){
		super( Privilege.class );
	}
	
	// tests
	
	// template method
	
	@Override
	protected final PrivilegeRESTTemplateImpl getTemplate(){
		return restTemplate;
	}
	
}

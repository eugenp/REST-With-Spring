package org.rest.sec.web.privilege;

import org.junit.runner.RunWith;
import org.rest.sec.model.Privilege;
import org.rest.sec.testing.template.PrivilegeRESTTemplateImpl;
import org.rest.spring.application.ApplicationTestConfig;
import org.rest.spring.persistence.jpa.PersistenceJPAConfig;
import org.rest.web.common.AbstractPaginationRESTIntegrationTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

@RunWith( SpringJUnit4ClassRunner.class )
@ContextConfiguration( classes = { ApplicationTestConfig.class, PersistenceJPAConfig.class },loader = AnnotationConfigContextLoader.class )
public class PrivilegePaginationRESTIntegrationTest extends AbstractPaginationRESTIntegrationTest< Privilege >{
	
	@Autowired
	private PrivilegeRESTTemplateImpl template;
	
	// tests
	
	// template method (shortcuts)
	
	@Override
	protected final Privilege createNewEntity(){
		return template.createNewEntity();
	}
	
	@Override
	protected final String getURI(){
		return template.getURI();
	}
	
	@Override
	protected final PrivilegeRESTTemplateImpl getTemplate(){
		return template;
	}
	
}

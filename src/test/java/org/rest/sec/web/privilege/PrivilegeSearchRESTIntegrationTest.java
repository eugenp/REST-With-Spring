package org.rest.sec.web.privilege;

import org.junit.runner.RunWith;
import org.rest.client.template.IEntityOperations;
import org.rest.sec.client.template.PrivilegeRESTTemplateImpl;
import org.rest.sec.model.Privilege;
import org.rest.spring.client.ClientTestConfig;
import org.rest.spring.context.ContextTestConfig;
import org.rest.spring.testing.TestingConfig;
import org.rest.web.common.AbstractSearchRESTIntegrationTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

@RunWith( SpringJUnit4ClassRunner.class )
@ContextConfiguration( classes = { ClientTestConfig.class, TestingConfig.class, ContextTestConfig.class },loader = AnnotationConfigContextLoader.class )
public class PrivilegeSearchRESTIntegrationTest extends AbstractSearchRESTIntegrationTest< Privilege >{
	
	@Autowired private PrivilegeRESTTemplateImpl restTemplate;
	
	public PrivilegeSearchRESTIntegrationTest(){
		super();
	}
	
	// tests
	
	// template
	
	@Override
	protected final PrivilegeRESTTemplateImpl getTemplate(){
		return restTemplate;
	}
	
	@Override
	protected final IEntityOperations< Privilege > getEntityOperations(){
		return restTemplate;
	}
	
}

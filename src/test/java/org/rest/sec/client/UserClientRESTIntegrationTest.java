package org.rest.sec.client;

import org.junit.runner.RunWith;
import org.rest.client.AbstractClientRESTIntegrationTest;
import org.rest.client.AbstractClientRestTemplate;
import org.rest.sec.dto.User;
import org.rest.spring.application.ApplicationTestConfig;
import org.rest.spring.client.ClientTestConfig;
import org.rest.spring.persistence.jpa.PersistenceJPAConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

@RunWith( SpringJUnit4ClassRunner.class )
@ContextConfiguration( classes = { ApplicationTestConfig.class, PersistenceJPAConfig.class, ClientTestConfig.class },loader = AnnotationConfigContextLoader.class )
public class UserClientRESTIntegrationTest extends AbstractClientRESTIntegrationTest< User >{
	
	@Autowired UserRESTTemplate userClientTemplate;
	
	public UserClientRESTIntegrationTest(){
		super();
	}
	
	// tests
	
	// template method
	
	@Override
	protected final AbstractClientRestTemplate< User > getTemplate(){
		return userClientTemplate;
	}
	@Override
	protected final String getURI(){
		return getTemplate().getURI() + "/";
	}
	
}

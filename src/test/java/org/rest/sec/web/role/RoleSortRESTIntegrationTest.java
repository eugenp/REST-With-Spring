package org.rest.sec.web.role;

import org.junit.runner.RunWith;
import org.rest.sec.model.Role;
import org.rest.sec.testing.template.RoleRESTTemplateImpl;
import org.rest.spring.application.ContextTestConfig;
import org.rest.spring.persistence.jpa.PersistenceJPAConfig;
import org.rest.web.common.AbstractSortRESTIntegrationTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import com.google.common.collect.Ordering;

@RunWith( SpringJUnit4ClassRunner.class )
@ContextConfiguration( classes = { ContextTestConfig.class, PersistenceJPAConfig.class },loader = AnnotationConfigContextLoader.class )
public class RoleSortRESTIntegrationTest extends AbstractSortRESTIntegrationTest< Role >{
	
	@Autowired
	private RoleRESTTemplateImpl template;
	
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
	
	@Override
	protected final Ordering< Role > getOrdering(){
		return new Ordering< Role >(){
			@Override
			public final int compare( final Role left, final Role right ){
				return left.getName().compareTo( right.getName() );
			}
		};
	}
	
}

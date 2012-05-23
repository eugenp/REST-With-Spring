package org.rest.sec.web.role;

import org.rest.sec.client.template.RoleRESTTemplateImpl;
import org.rest.sec.model.Role;
import org.rest.sec.test.SecSortRESTIntegrationTest;
import org.springframework.beans.factory.annotation.Autowired;

import com.google.common.collect.Ordering;

public class RoleSortRESTIntegrationTest extends SecSortRESTIntegrationTest< Role >{
	
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
	protected final RoleRESTTemplateImpl getAPI(){
		return template;
	}
	
	@Override
	protected final Ordering< Role > getOrdering(){
		return new Ordering< Role >(){
			@Override
			public final int compare( final Role left, final Role right ){
				return left.getName().compareToIgnoreCase( right.getName() );
			}
		};
	}
	
}

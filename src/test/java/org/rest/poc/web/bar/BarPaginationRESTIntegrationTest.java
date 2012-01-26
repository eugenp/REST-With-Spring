package org.rest.poc.web.bar;

import org.rest.poc.model.Bar;
import org.rest.poc.testing.template.BarRESTTemplateImpl;
import org.rest.testing.ExamplePaths;
import org.rest.web.common.AbstractPaginationRESTIntegrationTest;
import org.springframework.beans.factory.annotation.Autowired;

public class BarPaginationRESTIntegrationTest extends AbstractPaginationRESTIntegrationTest< Bar >{
	
	@Autowired
	ExamplePaths paths;
	
	@Autowired
	BarRESTTemplateImpl restTemplate;
	
	// tests
	
	// template method
	
	@Override
	protected final Bar createNewEntity(){
		return restTemplate.createNewEntity();
	}
	
	@Override
	protected final String getURI(){
		return paths.getFooUri();
	}
	
}

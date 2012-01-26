package org.rest.poc.web.foo;

import org.rest.poc.model.Foo;
import org.rest.poc.testing.template.FooRESTTemplateImpl;
import org.rest.testing.ExamplePaths;
import org.rest.web.common.AbstractPaginationRESTIntegrationTest;
import org.springframework.beans.factory.annotation.Autowired;

public class FooPaginationRESTIntegrationTest extends AbstractPaginationRESTIntegrationTest< Foo >{
	
	@Autowired
	ExamplePaths paths;
	
	@Autowired
	FooRESTTemplateImpl restTemplate;
	
	// tests
	
	// template method
	
	@Override
	protected final Foo createNewEntity(){
		return restTemplate.createNewEntity();
	}
	
	@Override
	protected final String getURI(){
		return paths.getFooUri();
	}
	
}

package org.rest.poc.web.foo;

import org.rest.poc.model.Foo;
import org.rest.poc.testing.template.FooRESTTemplateImpl;
import org.rest.web.common.AbstractMimeRESTIntegrationTest;
import org.springframework.beans.factory.annotation.Autowired;

public class FooMimeRESTIntegrationTest extends AbstractMimeRESTIntegrationTest< Foo >{
	
	@Autowired
	FooRESTTemplateImpl restTemplate;
	
	// tests
	
	// template method
	
	@Override
	protected final FooRESTTemplateImpl getTemplate(){
		return restTemplate;
	}
	
}

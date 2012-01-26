package org.rest.poc.web.bar;

import org.rest.poc.model.Bar;
import org.rest.poc.testing.template.BarRESTTemplateImpl;
import org.rest.web.common.AbstractMimeRESTIntegrationTest;
import org.springframework.beans.factory.annotation.Autowired;

public class BarMimeRESTIntegrationTest extends AbstractMimeRESTIntegrationTest< Bar >{
	
	@Autowired
	BarRESTTemplateImpl restTemplate;
	
	// tests
	
	// template method
	
	@Override
	protected final BarRESTTemplateImpl getTemplate(){
		return restTemplate;
	}
	
}

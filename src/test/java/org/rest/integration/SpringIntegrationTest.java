package org.rest.integration;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith( SpringJUnit4ClassRunner.class )
@ContextConfiguration( locations = {// @formatter:off
	"classpath:/test_rest_scan.xml"
}) 
// @formatter:on
public class SpringIntegrationTest{

	@Autowired
	ExamplePaths paths;

	@Autowired
	FooRESTTemplate restTemplate;

	// GET

	@Test
	public final void whenMinimalSpringContextIsInitializedForIntegrationTesting_thenNoExceptions(){
		
		// When

		// Then
	}

}

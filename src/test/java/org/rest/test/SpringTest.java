package org.rest.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith( SpringJUnit4ClassRunner.class )
@ContextConfiguration( locations = {//@formatter:off
	"classpath:/rest_scan.xml",
	"classpath:/rest_config.xml"
} )// @formatter:on
public final class SpringTest{
	
	@Test
	public final void whenSpringContextIsInstantiated_thenNoExceptions(){
		// When
	}
	
}

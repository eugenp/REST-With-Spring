package org.rest.sec.client.template.test.scenario;

import org.junit.Ignore;
import org.junit.runner.RunWith;
import org.rest.sec.client.template.UserClientRestTemplate;
import org.rest.sec.model.UserEntityOpsImpl;
import org.rest.sec.spring.ClientTestConfig;
import org.rest.sec.spring.ContextConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { ContextConfig.class, ClientTestConfig.class }, loader = AnnotationConfigContextLoader.class)
@Ignore
public class SecurityScenariosOverRestLiveTest {

    @Autowired
    private UserClientRestTemplate userApi;
    @Autowired
    private UserEntityOpsImpl userEntityOps;

    public SecurityScenariosOverRestLiveTest() {
        super();
    }

    // tests

}

package org.rest.sec.client.template.test.scenario;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;
import static org.rest.common.spring.util.CommonSpringProfileUtil.CLIENT;
import static org.rest.common.spring.util.CommonSpringProfileUtil.TEST;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.rest.sec.client.template.RoleClientRestTemplate;
import org.rest.sec.client.template.UserClientRestTemplate;
import org.rest.sec.model.Role;
import org.rest.sec.model.RoleEntityOpsImpl;
import org.rest.sec.model.UserEntityOpsImpl;
import org.rest.sec.model.dto.User;
import org.rest.sec.spring.ClientTestConfig;
import org.rest.sec.spring.ContextConfig;
import org.rest.sec.spring.SecCommonApiConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { ContextConfig.class, ClientTestConfig.class, SecCommonApiConfig.class }, loader = AnnotationConfigContextLoader.class)
@ActiveProfiles({ CLIENT, TEST })
public class SecurityScenariosOverRestLiveTest {

    @Autowired
    private UserClientRestTemplate userApi;
    @Autowired
    private RoleClientRestTemplate roleApi;

    @Autowired
    private UserEntityOpsImpl userEntityOps;
    @Autowired
    private RoleEntityOpsImpl roleEntityOps;

    public SecurityScenariosOverRestLiveTest() {
        super();
    }

    // tests

    @Test
    public final void givenAsAdmin_whenAssigningRoleToUser_thenNoException() {
        // Given
        final String nameOfUser = randomAlphabetic(6);
        userApi.create(userEntityOps.createNewEntity(nameOfUser));
        final String nameOfRole = randomAlphabetic(6);
        roleApi.create(roleEntityOps.createNewEntity(nameOfRole));

        // When
        final User existingUser = userApi.findByName(nameOfUser);
        final Role existingRole = roleApi.findByName(nameOfRole);
    }

}

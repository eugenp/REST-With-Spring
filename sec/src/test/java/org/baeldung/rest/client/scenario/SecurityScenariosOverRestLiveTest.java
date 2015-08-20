package org.baeldung.rest.client.scenario;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;
import static org.baeldung.rest.common.spring.util.CommonSpringProfileUtil.CLIENT;
import static org.baeldung.rest.common.spring.util.CommonSpringProfileUtil.TEST;

import org.baeldung.rest.client.RoleClientRestTemplate;
import org.baeldung.rest.client.UserClientRestTemplate;
import org.baeldung.rest.model.Role;
import org.baeldung.rest.model.RoleDtoOpsImpl;
import org.baeldung.rest.model.User;
import org.baeldung.rest.model.UserDtoOpsImpl;
import org.baeldung.rest.spring.ClientTestConfig;
import org.baeldung.rest.spring.ContextConfig;
import org.baeldung.rest.spring.SecCommonApiConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
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
    private UserDtoOpsImpl userEntityOps;
    @Autowired
    private RoleDtoOpsImpl roleEntityOps;

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

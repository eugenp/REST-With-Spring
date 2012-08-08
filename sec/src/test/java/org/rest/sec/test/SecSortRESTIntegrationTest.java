package org.rest.sec.test;

import org.junit.runner.RunWith;
import org.rest.common.persistence.model.IEntity;
import org.rest.common.security.util.AuthenticationUtil;
import org.rest.common.web.base.AbstractSortRESTIntegrationTest;
import org.rest.sec.spring.client.ClientTestConfig;
import org.rest.sec.spring.context.ContextTestConfig;
import org.rest.sec.util.SecurityConstants;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import com.jayway.restassured.specification.RequestSpecification;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { ClientTestConfig.class, ContextTestConfig.class }, loader = AnnotationConfigContextLoader.class)
public abstract class SecSortRESTIntegrationTest<T extends IEntity> extends AbstractSortRESTIntegrationTest<T> {

    //

    @Override
    protected final RequestSpecification givenAuthenticated() {
        return AuthenticationUtil.givenBasicAuthenticated(SecurityConstants.ADMIN_EMAIL, SecurityConstants.ADMIN_PASSWORD);
    }

}

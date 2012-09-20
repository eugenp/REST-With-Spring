package org.rest.sec.test;

import org.junit.runner.RunWith;
import org.rest.common.persistence.model.INameableEntity;
import org.rest.common.web.base.AbstractSortAndPaginationRestIntegrationTest;
import org.rest.sec.spring.client.ClientTestConfig;
import org.rest.sec.spring.context.ContextConfig;
import org.rest.sec.util.SecurityConstants;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import com.jayway.restassured.specification.RequestSpecification;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { ClientTestConfig.class, ContextConfig.class }, loader = AnnotationConfigContextLoader.class)
public abstract class SecSortAndPaginationRestIntegrationTest<T extends INameableEntity> extends AbstractSortAndPaginationRestIntegrationTest<T> {

    public SecSortAndPaginationRestIntegrationTest(final Class<T> clazzToSet) {
        super(clazzToSet);
    }

    //

    protected final RequestSpecification givenAuthenticated() {
        return auth.givenBasicAuthenticated(SecurityConstants.ADMIN_EMAIL, SecurityConstants.ADMIN_PASSWORD);
    }

}

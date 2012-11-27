package org.rest.sec.web.privilege;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import org.junit.Test;
import org.rest.common.client.IEntityOperations;
import org.rest.sec.client.template.PrivilegeTestRestTemplate;
import org.rest.sec.model.Privilege;
import org.rest.sec.model.PrivilegeEntityOpsImpl;
import org.rest.sec.test.SecDiscoverabilityRestLiveTest;
import org.springframework.beans.factory.annotation.Autowired;

import com.jayway.restassured.config.RedirectConfig;
import com.jayway.restassured.config.RestAssuredConfig;
import com.jayway.restassured.response.Response;
import com.jayway.restassured.specification.RequestSpecification;

public class PrivilegeDiscoverabilityRestLiveTest extends SecDiscoverabilityRestLiveTest<Privilege> {

    @Autowired
    private PrivilegeTestRestTemplate restTemplate;
    @Autowired
    private PrivilegeEntityOpsImpl entityOps;

    public PrivilegeDiscoverabilityRestLiveTest() {
        super(Privilege.class);
    }

    // tests

    @Test
    public final void whenConsumingSimillarResourceName_thenRedirectedToCorrectResourceName() {
        final String simillarUriOfResource = getUri().substring(0, getUri().length() - 1);
        final RequestSpecification customRequest = getApi().readRequest().config(new RestAssuredConfig().redirect(new RedirectConfig().followRedirects(false)));
        final Response responseOfSimillarUri = getApi().findOneByUriAsResponse(simillarUriOfResource, customRequest);
        assertThat(responseOfSimillarUri.getStatusCode(), is(301));
    }

    // template method

    @Override
    protected final Privilege createNewEntity() {
        return getEntityOps().createNewEntity();
    }

    @Override
    protected final String getUri() {
        return getApi().getUri();
    }

    @Override
    protected void change(final Privilege resource) {
        resource.setName(randomAlphabetic(6));
    }

    @Override
    protected RequestSpecification givenAuthenticated() {
        return getApi().givenAuthenticated();
    }

    @Override
    protected final PrivilegeTestRestTemplate getApi() {
        return restTemplate;
    }

    @Override
    protected final IEntityOperations<Privilege> getEntityOps() {
        return entityOps;
    }

}

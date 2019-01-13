package com.baeldung.um.web.privilege;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.baeldung.um.client.template.PrivilegeRestClient;
import com.baeldung.um.persistence.model.Privilege;
import com.baeldung.um.test.live.UmReadOnlyLogicRestLiveTest;
import io.restassured.response.Response;

public class PrivilegeReadOnlyLogicRestLiveTest extends UmReadOnlyLogicRestLiveTest<Privilege> {

    private static final String APPLICATION_KRYO = "application/x-kryo";
    
    @Autowired
    private PrivilegeRestClient api;

    public PrivilegeReadOnlyLogicRestLiveTest() {
        super(Privilege.class);
    }

    // tests

    @Test
    public final void giveConsumingAsKryo_whenAllResourcesAreRetrieved_then200IsReceived() {
        // When
        final Response response = getApi().givenReadAuthenticated().accept(APPLICATION_KRYO).get(getApi().getUri());

        // Then
        assertThat(response.getStatusCode(), is(200));
        assertThat(response.getContentType(), equalTo(APPLICATION_KRYO));
    }

    // template

    @Override
    protected final PrivilegeRestClient getApi() {
        return api;
    }

}

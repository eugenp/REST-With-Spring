package com.baeldung.um.web.privilege;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.baeldung.client.IDtoOperations;
import com.baeldung.um.client.template.PrivilegeRestClient;
import com.baeldung.um.model.PrivilegeDtoOpsImpl;
import com.baeldung.um.persistence.model.Privilege;
import com.baeldung.um.test.live.UmDiscoverabilityRestLiveTest;

public class PrivilegeDiscoverabilityRestLiveTest extends UmDiscoverabilityRestLiveTest<Privilege> {

    @Autowired
    private PrivilegeRestClient restTemplate;
    @Autowired
    private PrivilegeDtoOpsImpl entityOps;

    public PrivilegeDiscoverabilityRestLiveTest() {
        super(Privilege.class);
    }

    // tests

    @Test
    public final void whenSingleResourceIsRetrievedMultipleTimes_thenThrottled() {
        // Given
        final String uriOfExistingResource = getApi().createAsUri(createNewResource());

        ExecutorService executor = Executors.newCachedThreadPool();

        // When
        for (int i = 0; i < 10; i++) {
            executor.submit(() -> {
                getApi().read(uriOfExistingResource);
            });
        }
    }

    // template method

    @Override
    protected final Privilege createNewResource() {
        return getEntityOps().createNewResource();
    }

    @Override
    protected final String getUri() {
        return getApi().getUri();
    }

    @Override
    protected final PrivilegeRestClient getApi() {
        return restTemplate;
    }

    @Override
    protected final IDtoOperations<Privilege> getEntityOps() {
        return entityOps;
    }

}

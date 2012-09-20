package org.rest.common.web.base;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.internal.matchers.StringContains.containsString;

import org.junit.Test;
import org.rest.common.client.IEntityOperations;
import org.rest.common.client.marshall.IMarshaller;
import org.rest.common.client.template.IRestTemplate;
import org.rest.common.persistence.model.IEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;

import com.jayway.restassured.response.Response;

@ActiveProfiles({ "client", "test" })
public abstract class AbstractMimeRestIntegrationTest<T extends IEntity> {

    @Autowired
    private IMarshaller marshaller;

    public AbstractMimeRestIntegrationTest() {
        super();
    }

    // tests

    // GET

    @Test
    public final void givenRequestAcceptsMime_whenResourceIsRetrievedById__thenResponseContentTypeIsMime() {
        // Given
        final String uriForResourceCreation = getAPI().createAsURI(createNewEntity(), null);

        // When
        final Response res = getAPI().findOneByUriAsResponse(uriForResourceCreation, null);

        // Then
        assertThat(res.getContentType(), containsString(marshaller.getMime()));
    }

    // template method

    protected abstract IRestTemplate<T> getAPI();

    protected abstract IEntityOperations<T> getEntityOps();

    protected T createNewEntity() {
        return getEntityOps().createNewEntity();
    }

}

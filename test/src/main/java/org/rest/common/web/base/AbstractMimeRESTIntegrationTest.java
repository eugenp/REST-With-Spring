package org.rest.common.web.base;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.internal.matchers.StringContains.containsString;

import org.junit.Test;
import org.rest.common.client.IEntityOperations;
import org.rest.common.client.marshall.IMarshaller;
import org.rest.common.client.template.IRESTTemplate;
import org.rest.common.persistence.model.IEntity;
import org.springframework.beans.factory.annotation.Autowired;

import com.jayway.restassured.response.Response;

public abstract class AbstractMimeRESTIntegrationTest<T extends IEntity> {

    @Autowired
    private IMarshaller marshaller;

    public AbstractMimeRESTIntegrationTest() {
        super();
    }

    // tests

    // GET

    @Test
    public final void givenRequestAcceptsMime_whenResourceIsRetrievedById__thenResponseContentTypeIsMime() {
        // Given
        final String uriForResourceCreation = getAPI().createAsURI(createNewEntity());

        // When
        final Response res = getAPI().findByUriAsResponse(uriForResourceCreation);

        // Then
        assertThat(res.getContentType(), containsString(marshaller.getMime()));
    }

    // template method

    protected abstract IRESTTemplate<T> getAPI();

    protected abstract IEntityOperations<T> getEntityOps();

    protected T createNewEntity() {
        return getEntityOps().createNewEntity();
    }

}

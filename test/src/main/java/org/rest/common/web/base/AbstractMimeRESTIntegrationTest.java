package org.rest.common.web.base;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.internal.matchers.StringContains.containsString;

import org.junit.Test;
import org.rest.common.client.marshall.IMarshaller;
import org.rest.common.client.template.IRESTTemplate;
import org.rest.common.persistence.model.IEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.jayway.restassured.response.Response;

public abstract class AbstractMimeRESTIntegrationTest<T extends IEntity> {

    @Autowired
    @Qualifier("jacksonMarshaller")
    private IMarshaller marshaller;

    public AbstractMimeRESTIntegrationTest() {
        super();
    }

    // tests

    // GET

    @Test
    public final void givenRequestAcceptsMime_whenResourceIsRetrievedById__thenResponseContentTypeIsMime() {
        // Given
        final String uriForResourceCreation = getAPI().createAsURI(getAPI().createNewEntity());

        // When
        final Response res = getAPI().findOneAsResponse(uriForResourceCreation);

        // Then
        assertThat(res.getContentType(), containsString(marshaller.getMime()));
    }

    // template method

    protected abstract IRESTTemplate<T> getAPI();

}

package org.rest.client.template;

import java.util.List;

import org.rest.common.IEntity;
import org.rest.common.IRestDao;
import org.rest.testing.marshaller.IMarshaller;

import com.jayway.restassured.specification.RequestSpecification;

public interface IRESTTemplate<T extends IEntity> extends IRestDao<T>, IEntityOperations<T>, ITemplateAsResponse<T>, ITemplateAsURI<T> {

    String getURI();

    // authentication

    RequestSpecification givenAuthenticated();

    IMarshaller getMarshaller();

    // search

    List<T> search(final Long id, final String name);

}

package org.rest.client.template;

import java.util.List;

import org.rest.client.marshall.IMarshaller;
import org.rest.common.IEntity;
import org.rest.common.IOperations;

import com.jayway.restassured.specification.RequestSpecification;

public interface IRESTTemplate<T extends IEntity> extends IOperations<T>, IEntityOperations<T>, ITemplateAsResponse<T>, ITemplateAsURI<T> {

    String getURI();

    // authentication

    RequestSpecification givenAuthenticated();

    IMarshaller getMarshaller();

    // search

    List<T> search(final Long id, final String name);

}

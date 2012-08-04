package org.rest.common.client.template;

import java.util.List;

import org.apache.commons.lang3.tuple.Triple;
import org.rest.common.IOperations;
import org.rest.common.client.IEntityOperations;
import org.rest.common.client.marshall.IMarshaller;
import org.rest.common.persistence.model.IEntity;
import org.rest.common.search.ClientOperation;

import com.jayway.restassured.specification.RequestSpecification;

public interface IRESTTemplate<T extends IEntity> extends IOperations<T>, IEntityOperations<T>, ITemplateAsResponse<T>, ITemplateAsURI<T> {

    String getURI();

    // authentication

    RequestSpecification givenAuthenticated();

    IMarshaller getMarshaller();

    // search

    List<T> searchPaged(final Triple<String, ClientOperation, String> idOp, final Triple<String, ClientOperation, String> nameOp, final int page, final int size);

}

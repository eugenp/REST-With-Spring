package org.rest.common.client.template;

import java.util.List;

import org.apache.commons.lang3.tuple.Pair;
import org.apache.commons.lang3.tuple.Triple;
import org.rest.common.IOperations;
import org.rest.common.client.marshall.IMarshaller;
import org.rest.common.persistence.model.IEntity;
import org.rest.common.search.ClientOperation;

import com.jayway.restassured.specification.RequestSpecification;

public interface IRestTemplate<T extends IEntity> extends IOperations<T>, ITemplateAsResponse<T>, ITemplateWithUri<T> {

    // template

    RequestSpecification givenReadAuthenticated();

    IMarshaller getMarshaller();

    String getUri();

    // search

    List<T> searchPaginated(final Triple<String, ClientOperation, String> idOp, final Triple<String, ClientOperation, String> nameOp, final int page, final int size);

    // util

    Pair<String, String> getReadCredentials();

}

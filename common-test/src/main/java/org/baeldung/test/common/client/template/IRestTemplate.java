package org.baeldung.test.common.client.template;

import java.util.List;

import org.apache.commons.lang3.tuple.Pair;
import org.apache.commons.lang3.tuple.Triple;
import org.baeldung.client.marshall.IMarshaller;
import org.baeldung.client.template.ITemplateWithUri;
import org.baeldung.common.interfaces.IDto;
import org.baeldung.common.interfaces.IOperations;
import org.baeldung.common.search.ClientOperation;

import com.jayway.restassured.specification.RequestSpecification;

public interface IRestTemplate<T extends IDto> extends IOperations<T>, ITemplateAsResponse<T>, ITemplateWithUri<T> {

    // template

    RequestSpecification givenReadAuthenticated();

    IMarshaller getMarshaller();

    String getUri();

    // search

    List<T> searchPaginated(final Triple<String, ClientOperation, String> idOp, final Triple<String, ClientOperation, String> nameOp, final int page, final int size);

    // util

    Pair<String, String> getReadCredentials();

}

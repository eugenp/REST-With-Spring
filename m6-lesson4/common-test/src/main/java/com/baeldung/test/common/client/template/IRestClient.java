package com.baeldung.test.common.client.template;

import com.baeldung.client.marshall.IMarshaller;
import com.baeldung.client.template.IRestClientWithUri;
import com.baeldung.common.interfaces.IDto;
import com.baeldung.common.interfaces.IOperations;
import com.jayway.restassured.specification.RequestSpecification;

public interface IRestClient<T extends IDto> extends IOperations<T>, IRestClientAsResponse<T>, IRestClientWithUri<T> {

    // template

    RequestSpecification givenReadAuthenticated();

    RequestSpecification givenDeleteAuthenticated();

    IMarshaller getMarshaller();

    String getUri();

}

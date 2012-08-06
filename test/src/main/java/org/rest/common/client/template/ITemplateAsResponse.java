package org.rest.common.client.template;

import org.apache.commons.lang3.tuple.Triple;
import org.rest.common.persistence.model.IEntity;
import org.rest.common.search.ClientOperation;

import com.jayway.restassured.response.Response;

public interface ITemplateAsResponse<T extends IEntity> {

    // find - one

    Response findOneAsResponse(final String uriOfResource); // 19

    Response findOneAsResponse(final String uriOfResource, final String acceptMime); // 5

    // find - all

    Response findAllAsResponse(); // 1

    // search

    Response searchAsResponse(final Triple<String, ClientOperation, String> idOp, final Triple<String, ClientOperation, String> nameOp);

    Response searchAsResponse(final Triple<String, ClientOperation, String> idOp, final Triple<String, ClientOperation, String> nameOp, final int page, final int size);

    Response searchAsResponse(final Triple<String, ClientOperation, String>... constraints);

    // create

    Response createAsResponse(final T resource); // 14

    // update

    Response updateAsResponse(final T resource); // 6

    // delete

    Response deleteAsResponse(final String uriOfResource); // 5

}

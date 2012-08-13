package org.rest.common.client.template;

import org.apache.commons.lang3.tuple.Triple;
import org.rest.common.persistence.model.IEntity;
import org.rest.common.search.ClientOperation;

import com.jayway.restassured.response.Response;

public interface ITemplateAsResponse<T extends IEntity> {

    // find - one

    Response findByUriAsResponse(final String uriOfResource);

    // find - all

    Response findAllAsResponse();

    Response findAllPaginatedAsResponse(final int page, final int size);

    Response findAllSortedAsResponse(final String sortBy, final String sortOrder);

    Response findAllPaginatedAndSortedAsResponse(final int page, final int size, final String sortBy, final String sortOrder);

    // search

    Response searchAsResponse(final Triple<String, ClientOperation, String> idOp, final Triple<String, ClientOperation, String> nameOp);

    Response searchAsResponse(final Triple<String, ClientOperation, String> idOp, final Triple<String, ClientOperation, String> nameOp, final int page, final int size);

    Response searchAsResponse(final Triple<String, ClientOperation, String>... constraints);

    // create

    Response createAsResponse(final T resource);

    // update

    Response updateAsResponse(final T resource);

    // delete

    Response deleteAsResponse(final String uriOfResource);

}

package org.rest.common.client.template;

import java.util.List;

import org.apache.commons.lang3.tuple.Pair;
import org.rest.common.persistence.model.IEntity;

public interface ITemplateAsURI<T extends IEntity> {

    // find - one

    T findOneByURI(final String uri, final Pair<String, String> credentials);

    // find - all

    List<T> findAllByURI(final String uri, final Pair<String, String> credentials);

    // create

    String createAsURI(final T resource, final Pair<String, String> credentials);

}

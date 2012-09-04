package org.rest.common.client.template;

import java.util.List;

import org.rest.common.persistence.model.IEntity;

public interface ITemplateAsURI<T extends IEntity> {

    // find - one

    T findOneByURI(final String uri);

    // find - all

    List<T> findAllByURI(final String uri);

    // create

    String createAsURI(final T resource);

}

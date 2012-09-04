package org.rest.common.client.template;

import java.util.List;

import org.rest.common.persistence.model.IEntity;

public interface ITemplateAsURI<T extends IEntity> {

    // create

    String createAsURI(final T resource);

    // find - one

    T findOneByURI(final String uri);

    // find - all

    List<T> findAllByURI(final String uri);

    // sandboxed

    T searchOneByAttributes(final String... attributes);

    List<T> searchAllByAttributes(final String... attributes);

}

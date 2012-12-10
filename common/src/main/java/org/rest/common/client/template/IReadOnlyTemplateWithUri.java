package org.rest.common.client.template;

import java.util.List;

import org.apache.commons.lang3.tuple.Pair;
import org.rest.common.persistence.model.IEntity;

public interface IReadOnlyTemplateWithUri<T extends IEntity> {

    // find - one

    T findOneByUri(final String uri, final Pair<String, String> credentials);

    // find - all

    List<T> findAllByUri(final String uri, final Pair<String, String> credentials);

}

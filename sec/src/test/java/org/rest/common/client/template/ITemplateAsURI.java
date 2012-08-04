package org.rest.common.client.template;

import org.rest.common.persistence.model.IEntity;

public interface ITemplateAsURI<T extends IEntity> {

    // create

    String createAsURI(final T resource); // 8

}

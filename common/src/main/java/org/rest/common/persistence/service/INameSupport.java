package org.rest.common.persistence.service;

import org.rest.common.persistence.model.INameableEntity;

public interface INameSupport<T extends INameableEntity> {

    T findByName(final String name);

}

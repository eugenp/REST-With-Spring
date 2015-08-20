package org.baeldung.rest.common.web;

import org.baeldung.rest.common.persistence.model.IEntity;

public interface IUriMapper {

    <T extends IEntity> String getUriBase(final Class<T> clazz);

}

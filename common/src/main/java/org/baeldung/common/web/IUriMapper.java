package org.baeldung.common.web;

import org.baeldung.common.interfaces.IDto;

public interface IUriMapper {

    <T extends IDto> String getUriBase(final Class<T> clazz);

}

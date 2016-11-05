package com.baeldung.common.web;

import com.baeldung.common.interfaces.IDto;

public interface IUriMapper {

    <T extends IDto> String getUriBase(final Class<T> clazz);

}

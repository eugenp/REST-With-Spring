package com.baeldung.client.template;

import com.baeldung.common.interfaces.IDto;

public interface IRestClientWithUri<T extends IDto> extends IReadOnlyTemplateWithUri<T> {

    // create

    String createAsUri(final T resource);

}

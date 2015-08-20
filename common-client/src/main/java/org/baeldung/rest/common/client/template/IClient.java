package org.baeldung.rest.common.client.template;

import org.baeldung.rest.common.client.IByNameApi;
import org.baeldung.rest.common.client.INameableDto;

public interface IClient<T extends INameableDto> extends IRawClientTemplate<T>, IByNameApi<T> {

    //

}

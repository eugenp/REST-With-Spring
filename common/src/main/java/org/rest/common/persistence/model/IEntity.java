package org.rest.common.persistence.model;

import java.io.Serializable;

public interface IEntity extends Serializable {

    Long getId();

    void setId(final Long id);

}

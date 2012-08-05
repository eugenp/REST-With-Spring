package org.rest.common.util.order;

import org.rest.common.persistence.model.INameableEntity;

import com.google.common.collect.Ordering;

public final class OrderByName<T extends INameableEntity> extends Ordering<T> {

    public OrderByName() {
        super();
    }

    // API

    @Override
    public final int compare(final T left, final T right) {
        return left.getName().compareTo(right.getName());
    }

}

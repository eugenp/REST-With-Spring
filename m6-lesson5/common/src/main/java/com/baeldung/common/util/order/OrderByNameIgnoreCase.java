package com.baeldung.common.util.order;

import com.baeldung.common.persistence.model.INameableEntity;
import com.google.common.collect.Ordering;

public final class OrderByNameIgnoreCase<T extends INameableEntity> extends Ordering<T> {

    public OrderByNameIgnoreCase() {
        super();
    }

    // API

    @Override
    public final int compare(final T left, final T right) {
        return left.getName()
            .compareToIgnoreCase(right.getName());
    }

}

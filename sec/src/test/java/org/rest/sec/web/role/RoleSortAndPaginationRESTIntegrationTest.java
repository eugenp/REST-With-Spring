package org.rest.sec.web.role;

import org.rest.common.util.order.OrderByName;
import org.rest.sec.client.template.RoleRESTTemplateImpl;
import org.rest.sec.model.Role;
import org.rest.sec.test.SecSortAndPaginationRESTIntegrationTest;
import org.springframework.beans.factory.annotation.Autowired;

import com.google.common.collect.Ordering;

public class RoleSortAndPaginationRESTIntegrationTest extends SecSortAndPaginationRESTIntegrationTest<Role> {

    @Autowired
    private RoleRESTTemplateImpl template;

    public RoleSortAndPaginationRESTIntegrationTest() {
        super(Role.class);
    }

    // tests

    // template method (shortcuts)

    @Override
    protected final Role createNewEntity() {
        return template.createNewEntity();
    }

    @Override
    protected final String getURI() {
        return template.getURI();
    }

    @Override
    protected final RoleRESTTemplateImpl getAPI() {
        return template;
    }

    @Override
    protected final Ordering<Role> getOrdering() {
        return new OrderByName<Role>();
    }

}

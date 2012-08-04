package org.rest.sec.persistence.setup;

import org.rest.common.event.BeforeSetupEvent;
import org.rest.sec.web.controller.AuthenticationController;
import org.rest.sec.web.controller.PrivilegeController;
import org.rest.sec.web.controller.RoleController;
import org.rest.sec.web.controller.UserController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import com.google.common.base.Preconditions;

@Component
@Profile("production")
public final class SpringVerificationsBeforeSetupListener implements ApplicationListener<BeforeSetupEvent> {
    // private static final Logger logger = LoggerFactory.getLogger(SpringVerificationsBeforeSetupListener.class);

    @Autowired
    ApplicationContext context;

    public SpringVerificationsBeforeSetupListener() {
        super();
    }

    // API

    @Override
    public final void onApplicationEvent(final BeforeSetupEvent event) {
        Preconditions.checkNotNull(context.getBean(PrivilegeController.class));
        Preconditions.checkNotNull(context.getBean(RoleController.class));
        Preconditions.checkNotNull(context.getBean(UserController.class));
        Preconditions.checkNotNull(context.getBean(AuthenticationController.class));
    }

}

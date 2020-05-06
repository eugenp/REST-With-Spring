package com.baeldung.um.web.ops;

import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;

@Component
public class HealthCheck implements HealthIndicator {

    //

    @Override
    public Health health() {
        final int errorCode = check(); // perform some specific health check
        if (errorCode != 0) {
            return Health.down()
                .outOfService()
                .build(); // simulating "out of service"
        }
        return Health.up()
            .build();
    }

    public int check() {
        // Your logic to check health
        return -1;
    }
}
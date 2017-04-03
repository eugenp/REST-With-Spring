package com.baeldung.um.web.limit;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class RateLimiterAspect {

    @Before("@annotation(limit)")
    public void rateLimit(JoinPoint jp, RateLimit limit) {
        //...
    }

}
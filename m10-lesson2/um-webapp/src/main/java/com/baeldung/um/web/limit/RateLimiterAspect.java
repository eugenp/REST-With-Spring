package com.baeldung.um.web.limit;

import java.util.concurrent.ConcurrentHashMap;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.baeldung.um.util.limit.JoinPointToStringHelper;
import com.google.common.util.concurrent.RateLimiter;

@Aspect
@Component
public class RateLimiterAspect {
    private static final Logger LOGGER = LoggerFactory.getLogger(RateLimiterAspect.class);

    private final ConcurrentHashMap<String, RateLimiter> limiters;

    public RateLimiterAspect() {
        this.limiters = new ConcurrentHashMap<>();
    }

    // 

    @Before("@annotation(limit)")
    public void rateLimit(JoinPoint jp, RateLimit limit) {
        String key = getOrCreateKey(jp, limit);
        RateLimiter limiter = limiters.computeIfAbsent(key, (name -> RateLimiter.create(limit.value())));
        double delay = limiter.acquire();
        LOGGER.debug("Acquired rate limit permission ({} qps) for {} in {} seconds", limiter.getRate(), key, delay);
    }

    //

    private String getOrCreateKey(JoinPoint jp, RateLimit limit) {
        if( limit.key() == null ){
            return limit.key();
        }
        return JoinPointToStringHelper.toString(jp);
    }

}
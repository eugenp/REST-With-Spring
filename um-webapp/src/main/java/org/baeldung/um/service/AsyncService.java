package org.baeldung.um.service;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Future;

import org.baeldung.um.persistence.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.async.DeferredResult;

@Service
public class AsyncService {
    @Autowired
    private IUserService userService;

    public static final long DELAY = 10000L;

    private final Map<String, DeferredResult<User>> deferredResultMap = new HashMap<String, DeferredResult<User>>();
    private final Map<String, User> userMap = new HashMap<String, User>();

    @Async
    public Future<User> createUserAsync(User resource) throws InterruptedException {
        final User result = userService.create(resource);
        Thread.sleep(DELAY);
        return new AsyncResult<User>(result);
    }

    public void scheduleUser(User resource, DeferredResult<User> result) {
        final String key = resource.getName();
        deferredResultMap.put(key, result);
        userMap.put(key, resource);
        result.onCompletion(new Runnable() {
            @Override
            public void run() {
                deferredResultMap.remove(key);
                userMap.remove(key);
            }
        });
    }

    @Scheduled(fixedRate = DELAY)
    public void processUserQueue() {

        userMap.forEach((k, user) -> {
            final User created = userService.create(user);
            deferredResultMap.get(k).setResult(created);
        });
    }
}

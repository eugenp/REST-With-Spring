package com.baeldung.um.service;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.Future;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.async.DeferredResult;

import com.baeldung.um.web.dto.UserDto;

@Service
public class AsyncService {

    @Autowired
    private IUserService userService;

    public static final long DELAY = 10000L;

    private final ConcurrentMap<String, Pair<UserDto, DeferredResult<UserDto>>> deferredResultMap = new ConcurrentHashMap<String, Pair<UserDto, DeferredResult<UserDto>>>();

    @Async
    public Future<UserDto> createUserAsync(UserDto resource) throws InterruptedException {
        resource.setStatus("In Progress");

        final UserDto result = userService.create(resource);
        Thread.sleep(AsyncService.DELAY);

        result.setStatus("Ready");

        userService.update(result);
        return new AsyncResult<UserDto>(result);
    }

    public void scheduleCreateUserWithAsyncResultSetting(UserDto resource, DeferredResult<UserDto> result) {
        final String key = resource.getName();
        deferredResultMap.put(key, new ImmutablePair<UserDto, DeferredResult<UserDto>>(resource, result));
        result.onCompletion(new Runnable() {
            @Override
            public void run() {
                deferredResultMap.remove(key);
            }
        });
    }

    public void scheduleCreateUser(UserDto resource, DeferredResult<UserDto> deferredResult) {
        CompletableFuture.supplyAsync(() -> userService.createSlow(resource)).whenCompleteAsync((result, throwable) -> deferredResult.setResult(result));
    }

    @Scheduled(fixedRate = DELAY)
    public void processUserDtoQueue() {
        deferredResultMap.forEach((k, pair) -> {
            final UserDto created = userService.create(pair.getLeft());
            pair.getRight().setResult(created);
        });
    }
}

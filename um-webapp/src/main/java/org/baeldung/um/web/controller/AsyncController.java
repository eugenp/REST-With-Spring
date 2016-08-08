package org.baeldung.um.web.controller;

import java.util.concurrent.Callable;

import javax.servlet.http.HttpServletResponse;

import org.baeldung.um.persistence.model.User;
import org.baeldung.um.service.AsyncService;
import org.baeldung.um.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.async.DeferredResult;

@Controller
@RequestMapping(value = "long/users")
public class AsyncController {
    @Autowired
    private IUserService userService;

    @Autowired
    private AsyncService asyncService;

    // callable
    @RequestMapping(value = "/callable", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    public Callable<User> createLongUsingCallable(@RequestBody final User resource) {
        final User user = userService.create(resource);
        return new Callable<User>() {
            @Override
            public User call() throws Exception {
                Thread.sleep(AsyncService.DELAY);
                return user;
            }
        };
    }

    // deferred result
    @RequestMapping(value = "/def", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    public DeferredResult<User> createLongUsingDeferred(@RequestBody final User resource) {
        final DeferredResult<User> result = new DeferredResult<User>();
        asyncService.scheduleUser(resource, result);
        return result;
    }

    // async

    @RequestMapping(value = "/async", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void createLongUsingAsync(@RequestBody final User resource, HttpServletResponse response) throws InterruptedException {
        asyncService.createUserAsync(resource);
        response.setHeader("Location", "http://localhost:8081/api/long/users/" + resource.getName());
    }

    // =====================================

    // find by name
    @RequestMapping(value = "/{name}", method = RequestMethod.GET)
    @ResponseBody
    public User findByName(@PathVariable("name") final String name) {
        return userService.findByName(name);
    }

    //

}

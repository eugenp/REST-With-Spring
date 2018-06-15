package com.baeldung.um.web.controller;

import java.util.concurrent.Callable;

import javax.servlet.http.HttpServletResponse;

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
import org.springframework.web.util.UriComponentsBuilder;

import com.baeldung.um.service.AsyncService;
import com.baeldung.um.service.IUserService;
import com.baeldung.um.web.dto.UserDto;

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
    public Callable<UserDto> createUserWithCallable(@RequestBody final UserDto resource) {
        return new Callable<UserDto>() {
            @Override
            public UserDto call() throws Exception {
                return userService.createSlow(resource);
            }
        };
    }

    // deferred result

    @RequestMapping(value = "/deferred", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    public DeferredResult<UserDto> createUserWithDeferredResult(@RequestBody final UserDto resource) {
        final DeferredResult<UserDto> result = new DeferredResult<UserDto>();
        asyncService.scheduleCreateUser(resource, result);
        return result;
    }

    @RequestMapping(value = "/deferredComplex", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    public DeferredResult<UserDto> createUserWithDeferredResultWithAsyncResultSetting(@RequestBody final UserDto resource) {
        final DeferredResult<UserDto> result = new DeferredResult<UserDto>();
        asyncService.scheduleCreateUserWithAsyncResultSetting(resource, result);
        return result;
    }

    // async

    @RequestMapping(value = "/async", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void createUserWithAsync(@RequestBody final UserDto resource, HttpServletResponse response, UriComponentsBuilder uriBuilder) throws InterruptedException {
        asyncService.createUserAsync(resource);
        final String location = uriBuilder.path("/long").path("/users/").build().encode().toString();        
        response.setHeader("Location", location + resource.getName());
    }

    // find by name

    @RequestMapping(value = "/{name}", method = RequestMethod.GET)
    @ResponseBody
    public UserDto findByName(@PathVariable("name") final String name, HttpServletResponse response) {
        final UserDto user = userService.findByName(name);
        System.out.println(user);
        if (user == null) {
            response.setStatus(404);
        } else if (user.getStatus().equals("In Progress")) {
            response.setStatus(202);
            return null;
        }
        return user;
    }

    //

}

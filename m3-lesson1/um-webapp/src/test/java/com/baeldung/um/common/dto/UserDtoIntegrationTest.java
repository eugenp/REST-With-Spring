package com.baeldung.um.common.dto;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.baeldung.um.run.UmApp;
import com.baeldung.um.web.dto.UserDto;
import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = { UmApp.class })
@WebAppConfiguration
public class UserDtoIntegrationTest {

    private static final String URI = "/users";
    private MockMvc mockMvc;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    private WebApplicationContext wac;

    @Before
    public void setupTemplate() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(wac)
            .build();
    }

    @Test
    public void whenUserIsCreatedWithValidAgeUsernameAndEmail_then201Created() throws Exception {
        UserDto user = createValidUser();
        user.setName("user1");
        user.setEmail("user1@fake.com");

        String userObj = objectMapper.writeValueAsString(user);
        MvcResult mvcResult = this.mockMvc.perform(post(URI).accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON)
            .content(userObj))
            .andReturn();

        Assert.assertEquals(201, mvcResult.getResponse()
            .getStatus());
    }

    @Test
    public void whenUserIsCreatedWithInvalidEmailAddresses_then400BadRequest() throws Exception {
        UserDto user = createValidUser();
        user.setName("badname");
        user.setEmail("invalid_email.com");

        String userObj = objectMapper.writeValueAsString(user);
        MvcResult mvcResult = this.mockMvc.perform(post(URI).contentType(MediaType.APPLICATION_JSON)
            .content(userObj))
            .andReturn();

        Assert.assertEquals(400, mvcResult.getResponse()
            .getStatus());
    }

    @Test
    public void whenUserIsCreatedWithInvalidUsername_then400BadRequest() throws Exception {
        UserDto user = createValidUser();
        user.setName("n");
        user.setEmail("goodemail@fake.com");

        String userObj = objectMapper.writeValueAsString(user);
        MvcResult mvcResult = this.mockMvc.perform(post(URI).contentType(MediaType.APPLICATION_JSON)
            .content(userObj))
            .andReturn();

        Assert.assertEquals(400, mvcResult.getResponse()
            .getStatus());
    }

    private UserDto createValidUser() {
        UserDto user = new UserDto();
        user.setName("user");
        user.setEmail("user@fake.com");
        user.setPassword("userpass");
        return user;
    }
}

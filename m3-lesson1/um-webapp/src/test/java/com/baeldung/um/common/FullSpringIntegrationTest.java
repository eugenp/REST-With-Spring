package com.baeldung.um.common;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import javax.servlet.Filter;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.baeldung.um.run.UmApp;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { // @formatter:off
        UmApp.class,
})// @formatter:on
@WebAppConfiguration
public class FullSpringIntegrationTest {

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private Filter springSecurityFilterChain;

    @Before
    public void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(context)
            .addFilters(springSecurityFilterChain)
            .build();
    }

    @Test
    public void whenAPISuccessfullyAccessed_thenOk() throws Exception {
        mockMvc.perform(get("/users").with(httpBasic("user@fake.com", "userpass")))
            .andExpect(status().isOk());
    }

    @Test
    public final void whenContextIsBootstrapped_thenOk() {
        //
    }
}

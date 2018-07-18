package com.baeldung.um.live;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.client.WebClient;

import com.baeldung.um.persistence.model.Privilege;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = { LiveTestConfig.class }, loader = AnnotationConfigContextLoader.class)
public class WebClientLiveTest {

    protected final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    WebClient webClient;

    @Autowired
    WebTestClient webTestClient;

    @Test
    public void retrieveAllPrivileges() throws InterruptedException {
        // @formatter:off                
        Flux<Privilege> result = webClient.get()
                .uri("/privileges").accept(MediaType.TEXT_EVENT_STREAM)
                .retrieve()
                .bodyToFlux(Privilege.class);                   
        // @formatter:on

        result.subscribe(privilege -> System.out.println(privilege.toString()));

        Thread.sleep(35000);
    }

    @Test
    public void createNewPrivilege() throws InterruptedException {
        // @formatter:off                
        Mono<HttpStatus> result = webClient.post()
                .uri("/privileges")                
                .syncBody(getAPrivilegeForPost())
                .exchange()
                .map(response ->response.statusCode());
        // @formatter:on 

        result.subscribe(httpStatus -> System.out.println("Http Status code:" + httpStatus.value()));
    }

    private Privilege getAPrivilegeForPost() {
        Privilege privilege = new Privilege();
        privilege.setName(RandomStringUtils.random(5, 'A', 'Z'));
        return privilege;
    }

    @Test
    public void whenTheGivenPrivilegeIsRetrived_thenItIsRetrievedCorrecly() {
        // @formatter:off
         webTestClient.get()
                .uri("/privileges/1").accept(MediaType.APPLICATION_JSON)
                .exchange()                
                .expectBody(Privilege.class).isEqualTo(getPrivilege()); 
        // @formatter:on               
    }

    private Privilege getPrivilege() {
        Privilege privilege = new Privilege();
        privilege.setId(1L);
        privilege.setName("ROLE_PRIVILEGE_READ");
        return privilege;
    }

}

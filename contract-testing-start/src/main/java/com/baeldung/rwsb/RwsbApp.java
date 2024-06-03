package com.baeldung.rwsb;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class RwsbApp implements ApplicationRunner {

    public static void main(final String... args) {
        SpringApplication.run(RwsbApp.class, args);
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {

    }

}

package org.baeldung.um.run;

import java.util.HashMap;
import java.util.Map;

import org.baeldung.um.persistence.setup.MyApplicationContextInitializer;
import org.baeldung.um.spring.UmContextConfig;
import org.baeldung.um.spring.UmPersistenceJpaConfig;
import org.baeldung.um.spring.UmServiceConfig;
import org.baeldung.um.spring.UmWebConfig;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.embedded.ServletRegistrationBean;
import org.springframework.boot.context.web.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.web.servlet.DispatcherServlet;

@SpringBootApplication
@Import({ // @formatter:off
    UmContextConfig.class,
    UmPersistenceJpaConfig.class,
    UmServiceConfig.class,
    UmWebConfig.class
}) // @formatter:on
public class UmApp extends SpringBootServletInitializer {

    @Bean
    public DispatcherServlet dispatcherServlet() {
        return new DispatcherServlet();
    }

    @Bean
    public ServletRegistrationBean dispatcherServletRegistration() {
        final ServletRegistrationBean registration = new ServletRegistrationBean(dispatcherServlet(), "/api/*");

        final Map<String, String> params = new HashMap<String, String>();
        params.put("contextClass", "org.springframework.web.context.support.AnnotationConfigWebApplicationContext");
        params.put("contextConfigLocation", "org.spring.sec2.spring");
        params.put("dispatchOptionsRequest", "true");
        registration.setInitParameters(params);

        registration.setLoadOnStartup(1);
        return registration;
    }

    //

    @Override
    protected SpringApplicationBuilder configure(final SpringApplicationBuilder application) {
        return application.initializers(new MyApplicationContextInitializer()).sources(UmApp.class);
    }

    public static void main(final String... args) {
        new SpringApplicationBuilder(UmApp.class).initializers(new MyApplicationContextInitializer()).listeners().run(args);
    }

}

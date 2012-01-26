# Overview

This is a **learning project**; it's purpose be used as a starting point for a **RESTful web service** implemented with **Spring 3.1** and Java configuration and to provide solutions to the common requirements necessary to properly implement REST <br/>

# Technology Stack
The project uses the following technologies: <br/>
- **web/REST**: Spring 3.1 <br/>
- **marshalling**: Jackson (for JSON) and XStream (for XML) <br/>
- **persistence**: JPA, Spring Data JPA and Hibernate <br/>
- **testing**: Junit, Hamcrest, Mockito, rest-assured <br/>


# THE PERSISTENCE LAYER (technical notes)
### The DAO layer
- to create a new DAO, only the interface needs to be created; **Spring Data JPA** will generates the DAO implementation automatically
- the DAO interface MUST extend the Spring Data managed interface: _JpaRepository_ (with the correct parametrization)
- the DAO layer is **aware** of the persistence engine it uses; this information MUST be encoded in the name; for **example**: _IPrincipalJpaDAO_ for JPA instead of just _IPrincipalDAO_


### The Service layer
- all Service interfaces MUST extend the _IService_ interface (with the proper parametrization)
- all Service implementations MUST extend the _AbstractService_ abstract class (with the proper parametrization)
- extending _AbstractService_ and _IService_ enables a base of consistent and common functionality across services
- the Service artifacts MUST be annotated with the _@Service_ annotation

- the Service layer is **not aware** of the persistence engine it uses (indirectly); if the persistence engine will change, the DAO artifacts will change, and the service will not



# THE WEB LAYER (technical notes)
### The Controller layer
- the Controller layer MUST only use the Service layer directly (never the DAO layer)
- the Controller layer SHOULD not implement any interface
- the Controller layer MUST extend _AbstractController_ (with the proper parametrization)
- the Controller artifacts MUST be annotated with the _@Controller_ annotation


## Transaction Management and Configuration (technical notes)
- the Service layer is the transaction owner (and is annotated with _@Transactional_)
- the default transaction semantics are: propagation REQUIRED, default isolation, rollback on runtime exceptions
- **NOTE**: the transactional semantics MAY be subject to change


# Eclipse
- see the [Eclipse wiki page](https://github.com/eugenp/REST/wiki/Eclipse:-Setup-and-Configuration) of this project



# A Learning Project
Because it's a learning project, most of it's implementation is explained carefully and in great detail, in a series of posts:
### Spring and Maven basics <br/>
- [Java based Spring Configuration](http://www.baeldung.com/2011/10/20/bootstraping-a-web-application-with-spring-3-1-and-java-based-configuration-part-1/) <br/>
- [Usage of new concepts introduced with **Spring 3.1**](http://www.baeldung.com/2011/10/25/building-a-restful-web-service-with-spring-3-1-and-java-based-configuration-part-2/) <br/>
- [How to set up Integration Testing with the Maven Cargo plugin](http://www.baeldung.com/2011/10/16/how-to-set-up-integration-testing-with-the-maven-cargo-plugin/)

### Web Tier (REST) <br/>
- [**Spring MVC** for the REST web service](http://www.baeldung.com/2011/10/25/building-a-restful-web-service-with-spring-3-1-and-java-based-configuration-part-2/) <br/>
- [Driving API discoverability with integration test](http://www.baeldung.com/2011/11/06/restful-web-service-discoverability-part-4/) <br/>
- [Implementation of Discoverability with Spring](http://www.baeldung.com/2011/11/13/rest-service-discoverability-with-spring-part-5/) <br/>
- [REST advanced **content type** negotiation](http://www.baeldung.com/2011/11/06/restful-web-service-discoverability-part-4/) <br/>

### Security <br/>
- [Security of the REST web service with **Spring Security 3.1**](http://www.baeldung.com/2011/10/31/securing-a-restful-web-service-with-spring-security-3-1-part-3/) <br/>
- [**Basic and Digest authentication** for the same URI mapping of the RESTful service](http://www.baeldung.com/2011/11/20/basic-and-digest-authentication-for-a-restful-service-with-spring-security-3-1/) <br/>

### Persistence <br/>
- [The Persistence Layer with Spring 3.1 and **Hibernate** ](http://www.baeldung.com/2011/12/02/the-persistence-layer-with-spring-3-1-and-hibernate/) <br/>
- [The Persistence Layer with Spring 3.1 and **JPA** ](http://www.baeldung.com/2011/12/13/the-persistence-layer-with-spring-3-1-and-jpa/) <br/>
- [**Simplifying the Data Access Layer with Spring and Java Generics** ](http://www.baeldung.com/2011/12/08/simplifying-the-data-access-layer-with-spring-and-java-generics/) <br/>

### Testing <br/>
- [**Integration testing** of the REST service](http://www.baeldung.com/2011/10/13/integration-testing-a-rest-api/) <br/>
- [Comprehensive unit testing of the web tier, focusing on relevant behaviors to avoid brittleness](http://www.baeldung.com/2011/10/02/testing-the-service-layer/) <br/>

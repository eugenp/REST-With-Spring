## Overview

This is a **learning project**, and can be used as a starting point for a **RESTful web service** implemented with **Spring 3.1** and Java configuration. <br/> <br/>

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


## TECHNOLOGY STACK

- Spring 3.1 with Jackson, JAXB <br/>
- Hibernate 3.6 (to be moved to Hibernate 4.0 soon) <br/>
- Junit, Mockito, Hamcrest, rest-assured <br/>



## FURTHER DEVELOPMENT
======================

* improvements in validation and HTTP response codes
* improving the marshaling of the server stack trace
* expand on the design document, covering: details of the transaction strategy
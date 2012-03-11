# Overview

This is a **Spring Security RESTful service**; it's purpose be used as a internally deployable, stand alone security implementation <br/>
<br/>
An **alternative goal** of the project is to server as a starting point of a REST service implementation - a reference REST implementation with Spring and Spring Security, where 
most of the thorny problems in REST are addressed: <br/> 
- HATEOAS and Discoverability <br/> 
- Statelessness <br/> 
- Basic and Digest Authentication <br/> 
- support for Multiple Representations (JSON, XML) <br/> 
- full integration testing suites at every layer: unit tests, integration tests for the DAO and Service layers, integration tests against the REST service <br/>


# Continuous Integration
![Built on Cloudbees](http://web-static-cloudfront.s3.amazonaws.com/images/badges/BuiltOnDEV.png "Built on Cloudbees")

- **CI server**: https://rest-security.ci.cloudbees.com/


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

## Brief Description

The project is meant to be a Proof of Concept implementation of a RESTful web service. <br/>
It focuses on topics such as: <br/>
- [Java based Spring Configuration](http://www.baeldung.com/2011/10/20/bootstraping-a-web-application-with-spring-3-1-and-java-based-configuration-part-1/) <br/>
- [Usage of new concepts introduced with **Spring 3.1**](http://www.baeldung.com/2011/10/25/building-a-restful-web-service-with-spring-3-1-and-java-based-configuration-part-2/) <br/>
- [**Spring MVC** for the REST web service](http://www.baeldung.com/2011/10/25/building-a-restful-web-service-with-spring-3-1-and-java-based-configuration-part-2/) <br/>
- [Security of the REST web service with **Spring Security 3.1**](http://www.baeldung.com/2011/10/31/securing-a-restful-web-service-with-spring-security-3-1-part-3/) <br/>
- [**Basic and Digest authentication** for the same URI mapping of the RESTful service](http://www.baeldung.com/2011/11/20/basic-and-digest-authentication-for-a-restful-service-with-spring-security-3-1/) <br/>
- [Driving API discoverability with integration test](http://www.baeldung.com/2011/11/06/restful-web-service-discoverability-part-4/) <br/>
- [Implementation of Discoverability with Spring](http://www.baeldung.com/2011/11/13/rest-service-discoverability-with-spring-part-5/) <br/>
- [REST advanced **content type** negotiation](http://www.baeldung.com/2011/11/06/restful-web-service-discoverability-part-4/) <br/>
- [**Integration testing** of the REST service](http://www.baeldung.com/2011/10/13/integration-testing-a-rest-api/) <br/>
- [Comprehensive unit testing of the web tier, focusing on relevant behaviors to avoid brittleness](http://www.baeldung.com/2011/10/02/testing-the-service-layer/) <br/>



## TECHNOLOGY STACK

Implementation of a RESTfull web API, using:  <br/>
- Spring 3.1 with Jackson, JAXB <br/>
- Hibernate 3.6 (to be moved to Hibernate 4.0 soon) <br/>
- _Testing_: Junit, Mockito, Hamcrest, rest-assured <br/>



## REST WEB API

**GET /api/helloWorld/{id}** <br/>
\-  http response codes: *200, 404, 500* <br/>
\- json (request/response): none/single resource <br/>
\- retrieves the resource by id <br/>

**POST /api/helloWorld** <br/>
\- http response codes: *201, 415, 500* <br/>
\- json (request/response): single resource/none <br/>
\- creates a new resource <br/>

**PUT /api/helloWorld** <br/>
\- http response codes: *200, 400, 404, 500* <br/>
\- json (request/response): single resource/none <br/>
\- updates an existing resource <br/>

**DELETE /api/helloWorld/{id}** <br/>
\- http response codes: *200, 404, 500* <br/>
\- json (request/response): none/none <br/>
\-  delete an existing resource by id <br/>

**GET /api/helloWorld** <br/>
\- http response codes: *200, 500* <br/>
\- json (request/response): none/multiple resources <br/>
\- retrieves all resources of that type <br/>


### JSON payloads <br/>

- single resource:
{
    "name": "Dvthix",
    "id": 1
}

- multiple resources:
[    {
       "name": "Dvthix",
       "id": 1
    },
    {
       "name": "cPfCwV",
       "id": 2
    }, 
]


## ADDITIONAL BLOG DISCUSSIONS (backed by this project)
===================

* [How to set up Integration Testing with the Maven Cargo plugin](http://www.baeldung.com/2011/10/16/how-to-set-up-integration-testing-with-the-maven-cargo-plugin/)



## FURTHER DEVELOPMENT
======================

* improvements in validation and HTTP response codes
* improving the marshaling of the server stack trace
* improve the discoverability of the REST API - make good use of the Location HTTP header for returning full URLs (not just ids) so that the client doesn't have to construct his own URLs; introduce a /about URL to make discoverability better
* expand on the design document, covering: details of the transaction strategy

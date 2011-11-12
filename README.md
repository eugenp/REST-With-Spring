
## TECHNOLOGY STACK
===================

Implementation of a RESTfull web API, using: 
* Spring 3.1 and Jackson
* Hibernate 3.6
* testing: Junit, Mockito, Hamcrest, Apache HTTP client



## REST WEB API
===============

**GET /api/helloWorld/{id}**

\-  http response codes: *200, 404, 500*

\- json (request/response): none/single resource

\- retrieves the resource by id


**POST /api/helloWorld**
\- http response codes: *201, 415, 500*

\- json (request/response): single resource/none

\- creates a new resource


**PUT /api/helloWorld**

\- http response codes: *200, 400, 404, 500*

\- json (request/response): single resource/none

\- updates an existing resource

**DELETE /api/helloWorld/{id}**

\- http response codes: *200, 404, 500*

\- json (request/response): none/none

\-  delete an existing resource by id


**GET /api/helloWorld**

\- http response codes: *200, 500*

\- json (request/response): none/multiple resources

\- retrieves all resources of that type



JSON payloads
-------------

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
    }, ]




## QA AND TESTING
=================

- in progress



## BLOG DISCUSSIONS (backed by this project)
===================

* [How to avoid brittle tests when testing the Service Layer](http://www.baeldung.com/2011/10/02/testing-the-service-layer/)
* [Introduction to Java integration testing for a REST API](http://www.baeldung.com/2011/10/13/integration-testing-a-rest-api/)
* [How to set up Integration Testing with the Maven Cargo plugin](http://www.baeldung.com/2011/10/16/how-to-set-up-integration-testing-with-the-maven-cargo-plugin/)
* [Bootstrapping a web application with Spring 3.1 and Java based Configuration, part 1](http://www.baeldung.com/2011/10/20/bootstraping-a-web-application-with-spring-3-1-and-java-based-configuration-part-1/)
* [Building a RESTful Web Service with Spring 3.1 and Java based Configuration, part 2](http://www.baeldung.com/2011/10/25/building-a-restful-web-service-with-spring-3-1-and-java-based-configuration-part-2/)
* [Securing a RESTful Web Service with Spring Security 3.1, part 3](http://www.baeldung.com/2011/10/31/securing-a-restful-web-service-with-spring-security-3-1-part-3/)
* [RESTful Web Service Discoverability, part 4](http://www.baeldung.com/2011/11/06/restful-web-service-discoverability-part-4/)



## FURTHER DEVELOPMENT
======================

* improvements in validation and HTTP response codes
* improving the marshaling of the server stack trace
* improve the discoverability of the REST API - make good use of the Location HTTP header for returning full URLs (not just ids) so that the client doesn't have to construct his own URLs; introduce a /about URL to make discoverability better
* expand on the design document, covering: details of the transaction strategy


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



## BLOG DISCUSSIONS
===================

* [How to avoid brittle tests when testing the Service Layer](http://www.baeldung.com/2011/10/02/testing-the-service-layer/)
* [How to write integration tests for a RESTfull API](http://www.baeldung.com/2011/10/13/integration-testing-a-rest-api/)



## FURTHER DEVELOPMENT
======================

* improvements in validation and HTTP response codes
* introduction of Spring Security to provide authentication and authorization
* introduction of the cargo-maven-plugin to automate the deployment process and introduce remote deployment
* starting an embedded Tomcat in the maven pre-integration-test phase and stopping it in the post-integration-test, so that the integration testing can be executed locally in the maven integration phase
* using the Accept header to decide the type of the Representation - json is supported now, xml can be added later on
* improving the marshaling of the server stack trace
* improve the discoverability of the REST API - make good use of the Location HTTP header for returning full URLs (not just ids) so that the client doesn't have to construct his own URLs; introduce a /about URL to make discoverability better
* expand on the design document, covering: details of the transaction strategy

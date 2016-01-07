# Resources
- Technical forum: [rest-security google group](https://groups.google.com/forum/#!forum/restsec)
- Docs: [Main wiki](https://github.com/eugenp/REST/wiki)
- Continuous Integration: [CI on Cloudbees](https://rest-security.ci.cloudbees.com)

# Quick Start
```
git clone git://github.com/eugenp/REST.git
cd REST
mvn install
mvn cargo:run -f sec/pom.xml
```
- **next**: start consuming the service like so: [REST API: Consuming Examples with `curl`](https://github.com/eugenp/REST/wiki/REST-API%3A-Consuming-Examples-with-%60curl%60)

# Goals
**REST Security** is a Proof of Concept implementation of a RESTful Service. <br/>
The project also provides a reference implementation for: 
- REST Discoverability and HATEOAS <br/> 
- Basic and Digest Authentication <br/>
- ETag support
- support for Multiple Representations (on the same URIs) (JSON, XML) <br/> 
- a full REST based Query Language for advanced filtering of resources <br/> 
- Sorting and Pagination in REST <br/>
- Statelessness for REST with Spring <br/> 
- full integration testing suites at every layer: unit tests, integration tests for the DAO and Service layers, integration tests against the REST service <br/>


# Technology Stack
The project uses the following technologies: <br/>
- **web/REST**: [Spring](http://www.springsource.org/) 3.1.x <br/>
- **marshalling**: [Jackson](https://github.com/FasterXML/jackson-databind) 2.x (for JSON) and [XStream](http://xstream.codehaus.org/) (for XML) <br/>
- **persistence**: [Spring Data JPA](http://www.springsource.org/spring-data/jpa) and [Hibernate](http://www.hibernate.org/) 4.1.x <br/>
- **persistence providers**: h2, MySQL
- **testing**: [junit](http://www.junit.org/), [hamcrest](http://code.google.com/p/hamcrest/), [mockito](http://code.google.com/p/mockito/), [rest-assured](http://code.google.com/p/rest-assured/) <br/>


# REST API Documentation
[Link to the **API Documentation**](https://github.com/eugenp/REST/wiki/API-documentation "REST Security - API docs")


# Continuous Integration
- Built on Cloudbees: <a href="https://rest-security.ci.cloudbees.com">REST Security Jenkins CI</a> 
<br/><br/>
<a href="https://rest-security.ci.cloudbees.com"><img src="http://web-static-cloudfront.s3.amazonaws.com/images/badges/BuiltOnDEV.png"/></a>


# Community
- [Discussion Group/Mailing List](https://groups.google.com/forum/#!forum/restsec)


# Eclipse
- see the [Eclipse wiki page](https://github.com/eugenp/REST/wiki/Eclipse:-Setup-and-Configuration) of this project

# Resources
- [REST With Spring] (http://bit.ly/restwithspring)
- Continuous Integration: [CI on Cloudbees](https://rest-security.ci.cloudbees.com/view/REST-With-Spring/)


# Quick Start
```
git clone https://github.com/eugenp/REST-With-Spring.git
cd REST-With-Spring
mvn clean install
mvn cargo:run -f um-webapp/pom.xml
```


# Persistence
By default, the project uses [the H2 in-memory DB](http://www.h2database.com/html/main.html) and - `persistence-h2.properties`.

If you want to switch to - for example - MySQL - you'll need to specify a different property on startup:
```
persistenceTarget=h2
```
And of course, if you are going to use MySQL, you'llneed to run a MySQL instance locally and you'll need to either change the default credentials here, or create the following user/password in your local installation


# Technology Stack
The project uses the following technologies: <br/>
- **web/REST**: [Spring](http://www.springsource.org/) 4.2.x <br/>
- **marshalling**: [Jackson](https://github.com/FasterXML/jackson-databind) 2.x (for JSON) and the new  [Jackson XML extension](https://github.com/FasterXML/jackson-dataformat-xml) (for XML) <br/>
- **persistence**: [Spring Data JPA](http://www.springsource.org/spring-data/jpa) and [Hibernate](http://www.hibernate.org/) <br/>
- **persistence providers**: H2, MySQL
- **testing**: [junit](http://www.junit.org/), [hamcrest](http://code.google.com/p/hamcrest/), [mockito](http://code.google.com/p/mockito/), [rest-assured](http://code.google.com/p/rest-assured/) <br/>


# Continuous Integration
- Built on Cloudbees: <a href="https://rest-security.ci.cloudbees.com/view/REST-With-Spring/">REST Security Jenkins CI</a> 
<br/><br/>
<a href="https://rest-security.ci.cloudbees.com"><img src="http://web-static-cloudfront.s3.amazonaws.com/images/badges/BuiltOnDEV.png"/></a>


# Eclipse
- see the [Eclipse wiki page](https://github.com/eugenp/REST-With-Spring/wiki/Eclipse:-Setup-and-Configuration) of this project

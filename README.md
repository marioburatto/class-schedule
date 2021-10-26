# class-schedule

A Class Schedule Manager application for Philips assignment test.

## Implementation Details

### Design Considerations

The application was developed based on Java 11, Postgres 9, Spring Boot,
Spring-Web and JPA.

The _**Presentation**_ layer was represented by the *Controller classes which
were handling a respective DTO (Data Transfer Object) for the inbound and
outbound data communications. Each DTO have its respective Mapper class that
translates the DTO's into domains in order to send and receive data from the
Service layer.

The _**Service**_ layer has no much logic and basically bridges the Presentation
to the Persistence layer.

The _**Persistence**_ layer was represented by the *Repository classes. They are
basically a JPA Crud Repository persisting each one of the 4 domain entities:
Department, Professor, Course and Schedule. It's important to notice that due to
a search report requirement a dedicated database projection was added to reduce
the amount of data loaded from the database.

In order to load all the example data into the application it was created a
script called ClientIntegrationTest. It can be executed with the following
command line:

    mvn test-compile exec:java -Dexec.mainClass=com.philips.classschedule.client.ClientIntegrationTest -Dexec.classpathScope=test -f pom.xml

### Future Enhancements

As a highlight for future enhancements it could be listed:

1. Add more test scenarios to cover a wider range of possible inputs, testing
   each component individually using mocks if necessary in order to have a
   better coverage and building speed;
2. Prepare an application instance and trigger a load test against it, according
   to the expected peak usage volume;
3. As all the layers of this application, including the PostgreSql JDBC drivers,
   have a mature reactive library, the entire application could be made
   reactive, optimizing its performance and resource consumption per request. A
   good candidate would be
   the [Spring Data R2DBC](https://spring.io/projects/spring-data-r2dbc) or even
   the [Reactive Hibernate](http://hibernate.org/reactive/).
4. Configure a CI/CD environment to make the build and deployment process
   easier;

## Build and Run

### Requirements

This application is build based
on [JDK 11](https://www.oracle.com/br/java/technologies/javase-jdk11-downloads.html)
so it will be required to be in your classpath.

### Running the application locally

If you have maven installed and under linux/mac:

    mvn spring-boot:run -pl application

If maven is not installed, but still under linux/mac

    mvnw spring-boot:run -pl application

And for windows without gradle

    mvnw.cmd spring-boot:run -pl application

After the server is running, go to

```
http://localhost:8080/swagger-ui/
```

The API's swagger documentation will lead you to the available services.

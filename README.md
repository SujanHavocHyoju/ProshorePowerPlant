# ProshorePowerPlant

Welcome to ProshorePowerPlant! This application was developed as part of a technical assessment task.

## Library Dependencies

This project utilizes the following libraries to ensure its functionality and performance:

- **Spring Boot Starter Web**: Provides the foundation for building web applications using Spring MVC.
- **Spring Boot Starter Data JPA**: Simplifies database access using the Java Persistence API.
- **Spring Boot Devtools**: Offers development-time features to enhance productivity.
- **Spring Boot Starter Test**: Includes testing libraries and tools for unit and integration tests.
- **Spring Boot Starter Validation**: Adds validation support for data integrity.
- **H2 Database**: An in-memory database for development and testing purposes.
- **PostgreSQL Database**: A robust, production-ready relational database system.
- **Lombok**: Reduces boilerplate code by automatically generating getters, setters, and more.
- **Springdoc OpenAPI Starter Webmvc UI (Swagger 3)**: Generates API documentation in a clear and interactive format.
- **Log4j API and Log4j Core**: Enables efficient logging of application activities.

## Profiles

ProshorePowerPlant operates in two distinct Spring profiles:

1. **dev**: Utilizes an in-memory H2 database, ideal for development and testing.
2. **prod**: Connects to a remote PostgreSQL-15 database hosted on [Render](https://dashboard.render.com/), suitable for production.

## Running the Project Locally

To get started with the project on your local machine:
1. Clone the repository:
```
$ git clone https://github.com/SujanHavocHyoju/ProshorePowerPlant.git
$ cd ProshorePowerPlant
```
2. Run the application using Maven:
```
$ mvn spring-boot:run
```
Or specify the profile explicitly
```
$ mvn spring-boot:run -Dspring-boot.run.profiles=dev

```
Or run with the production profile
```
$ mvn spring-boot:run -Dspring-boot.run.profiles=prod

```

## Test Coverage

Implemented unit tests with Junit 5, including testing of restful endpoints, repository methods, and service logic

- **@WebMvcTest**: Used for testing restful endpoints with MockMvc.
- **@DataJpaTest**: Employed to test repository method calls.
- **Mockito**: Leveraged to test service logic.

To execute tests:
```
$ mvn test
```

## Logging

Have utilized **Log4j2** implementation to log informative messages at the **INFO** level, which are stored in the file **/proshore.log**.

## Project Demo

The projects has been deployed on Render Free Tier web services:

- Profile 1 (dev): [ProshorePowerPlantH2 Demo](https://proshorepowerplanth2.onrender.com)
- Profile 2 (prod): [ProshorePowerPlantPgSQL Demo](https://proshorepowerplantpgsql.onrender.com)

Render deployment requires a **Dockerfile** in the project root directory:

```
Dockerfile
FROM eclipse-temurin:17-jdk-alpine
VOLUME /tmp
COPY target/*.jar ProshorePowerPlant-v1.jar
ENTRYPOINT ["java","-jar","/ProshorePowerPlant-v1.jar"]
EXPOSE 8080
```

## Code Analysis
Code Analysis was done using **Codacy**.


## Documentation

Documentation for the project:

- **API Documentation (Profile 1)**: [proshorepowerplanth2 API-Docs](https://proshorepowerplanth2.onrender.com/v3/api-docs)
- **API Documentation (Profile 2)**: [proshorepowerplantpgsql API-Docs](https://proshorepowerplantpgsql.onrender.com/v3/api-docs)

Explore the APIs interactively using **Swagger UI**:

- **Swagger UI (Profile 1)**: [proshorepowerplanth2 Swagger UI](https://proshorepowerplanth2.onrender.com/swagger-ui/index.html#/)
- **Swagger UI (Profile 2)**: [proshorepowerplantpgsql Swagger UI](https://proshorepowerplantpgsql.onrender.com/swagger-ui/index.html#/)
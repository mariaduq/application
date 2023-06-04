# application

Project

## Getting Started 🚀

### Prerequisites

To run it, you need to have Docker installed locally. 

### Installing

First, you have to clone the repository in the desired directory with the following command:

```
$ git clone https://github.com/mariaduq/application.git
```
Once this is done, you have to run the following command to create the .jar file:

```
$ mvn install
```
Finally, you have to run the following command to create and run the containers with Docker:

```
$ docker-compose up --build
```

### Example


## Running the tests ✅

To execute the tests, just run the following command:

```
$ mvn test
```
These tests test different situations, including error situations.


## Documentation 📄

You can access the API documentation with the following request:

```
http://localhost:8080/swagger-ui/index.html
```

## Built With ⚒️

* [Spring Boot](https://spring.io) - The web framework used
* [Maven](https://maven.apache.org/) - Dependency Management
* [JUnit](https://junit.org/junit5/) - Testing
* [OpenJDK](https://openjdk.org) - Java

## Authors 👩🏼‍💻

* **María Duque Román** - [mariaduq](https://github.com/mariaduq)


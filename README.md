# practicaPrices

Project that provides the price of an Inditex product of a specific brand at a specific time.

## Getting Started 🚀

### Prerequisites

To run it, you need to have Docker installed locally. 

### Installing

A step by step series of examples that tell you how to get a development env running

First, you have to clone the repository in the desired directory with the following command:

```
$ git clone https://github.com/mariaduq/practicaPrices.git
```

Once this is done, you have to run the following command to create and run the containers with Docker:

```
$ docker-compose up --build
```
### Example

Once the application is already running, if you make the following request:

```
http://localhost:8080/price/search?dateString=2020-06-16 21:00:00&product_id=35455&brand_id=1
```

You get the price 38.95 EUR

## Running the tests ✅

To execute the tests, just run the following command:

```
$ mvn test
```
These tests test different situations, including error situations.

## Built With ⚒️

* [Spring Boot](https://spring.io) - The web framework used
* [Maven](https://maven.apache.org/) - Dependency Management
* [JUnit](https://junit.org/junit5/) - Testing
* [OpenJDK](https://openjdk.org) - Java

## Authors 👩🏼‍💻

* **María Duque Román** - [mariaduq](https://github.com/mariaduq)


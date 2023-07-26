# ElectroShop ⚡️

Project that provides the price of a product of an appliance store at any time of the year.

## Getting Started 🚀

### Prerequisites 1️⃣

To run it, you need to have Docker installed locally. 

### Installing 📲

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

### Example 🌈

Once the containers are up and the application is running, you can access the app.

First, enter the following url in your browser:
```
http://localhost:8080/
```
Once this is done, you can log-in with the following credentials:
```
Email: mariaduq01@gmail.com
```
```
Password: password
```
(You can also register with your own data if you wish to do so)

<br>
Once you are logged in, you can:
<br>
<br>

**1. Edit your profile** ✍🏻

You can edit the profile by pulling down the user menu in the upper right corner of the screen and clicking on "Editar perfil".
</br>
</br>

**2. Delete your account** ❌️
    
You can delete your account by pulling down the user menu in the upper right corner of the screen and clicking on "Eliminar cuenta".
</br>
</br>

**3. Log-out** 📴

You can log-out by pulling down the user menu in the upper right corner of the screen and clicking on "Cerrar sesión".
</br>
</br>

**4. See all available products and their respective prices** ✅

You can see a list of all available products by clicking on the "Ver todos los productos" button.

Once in the list, if you click on the "Consultar precios" button of a product, you will see a table with all the prices of that product and its duration.
</br>
</br>

**5. Check the price of a specific product at any time of the year** 💲

If you click on the "Consultar precio de un producto" button, a form will appear to enter the product id and the date on which you want to know its price.

For example, if you enter:
```
Product id: 9136275
```
```
Date: 23/02/2024, 10:00
```
You will get the price **850'5 €**
</br>
</br>

**6. Search products by brand** ™️

If you click on the "Consultar productos de una marca" button, a form will appear to enter the name of the brand of which you wish to see the available products.

For example, if you enter:
```
Brand: Siemens
```
A table with **4 products** will be displayed: three refrigerators and one oven.
</br>
</br>

**7. Search products by type** 👀

If you click on the "Consultar productos de un tipo" button, a form will appear to enter the type of products you wish to view.

For example, if you enter:
```
Type: Lavadora
```
A table with **10 types of washing machines** of different brands will be displayed.

<br>
If the stock of a product is less than 5, the number is shown in red.

## Running the tests 💡

To execute the tests, just run the following command:

```
$ mvn test
```
These tests are unit tests that test all use cases and mappers of the application, including error situations.


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
* [PostgreSQL](https://www.postgresql.org) - Database
* [Lombok](https://projectlombok.org) - Java Library
* [OpenAPI](https://swagger.io/specification/) - Application documentation
* [HTML](https://lenguajehtml.com) - Markup language
* [Thymeleaf](https://www.thymeleaf.org) - Template engine
* [CSS](https://lenguajecss.com) - Style language
* [JavaScript](https://lenguajejs.com/javascript/) - Interpreted front-end programming language
* [Font Awesome](https://fontawesome.com) - Icon library
* [Docker Compose](https://docs.docker.com/compose/) - Deployment
## Authors 👩🏼‍💻

* **María Duque Román** - [mariaduq](https://github.com/mariaduq)


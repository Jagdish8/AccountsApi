# Account Application using Java 18, Spring Boot and H2 DB

REST-ful API to simulate simple account operations.

## Requirements

*	CRUD operations for accounts.
*	Support deposits and withdrawals on accounts.
*	one account may transfer funds to another account.
*   get list of all payments
*   get list of payments made by an account

## Getting Started

1. Checkout the project from GitHub

```
git clone https://github.com/Jagdish8/AccountsApi.git
```

2. Open IDE and import the project

```
- Import existing maven project
- Run mvn clean install
```

3. run the AccountsApiApplication class and the application will be running

4. open the below link
```
http://localhost:8989/bank-api/swagger-ui.html
```

5. Default port for the api is 8989


### Prerequisites

* Java 18
* IntelliJ or similar IDE
* [Maven](https://maven.apache.org/) - Dependency Management

### Maven Dependencies

```
spring-boot-starter-data-jpa
spring-boot-starter-web
h2 - Inmemory database
lombok - to reduce boilerplate code
springfox-swagger2
springfox-swagger-ui
spring-boot-starter-test
```

## Swagger

Please find the Rest API documentation in the below url

```
http://localhost:8989/bank-api/swagger-ui.html
```

## H2 In-Memory Database

Below is the link for database access with credentials below it(credentials can be found in application.yml as well)
```
http://localhost:8989/bank-api/h2-console/

username: jagdish
url: jdbc:h2:mem:jagdishData
driverClassName: org.h2.Driver
password: '6969'
```


## Testing the Account APP Rest Api

1. Please use the Swagger url to perform CRUD operations.
2. Can be tested in postman or insomnia as well

## Author

* **Jagdish**


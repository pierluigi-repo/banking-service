# Banking APIs

A simple application that executes the following banking operations:

- Getting account balance
- Getting account transactions
- Executing money transfers

## Requirements

For building and running the application you need:

- [JDK 1.8](https://www.oracle.com/java/technologies/downloads/#java8)
- [Maven 3](https://maven.apache.org)

## Running the services locally

There are several ways to run the application on your local machine. One way is to execute the `main` method in the `it.pierluigi.banking.BankingServiceApplication` class from an IDE (e.g. [IntelliJ](https://www.jetbrains.com/idea/download), [Eclipse](https://www.eclipse.org/downloads/)).

As another option you can use the [Spring Boot Maven plugin](https://docs.spring.io/spring-boot/docs/current/reference/html/build-tool-plugins-maven-plugin.html) like so:

```shell
mvn spring-boot:run
```

First run may take some time to download maven dependencies.

## Testing the services

When the application is running on your machine (default 8080 port is used), for example you can obtain the balance making an HTTP GET request to:

```text
http://localhost:8080/banking/balance?accountId={accountId}
```

Replace ```{accountId}``` with the ID of the account you want.

Alternatively you can run from an IDE the class `it.pierluigi.banking.BankingServiceApplicationTests` (open the class, right-click on the class name, then select run 'BankingServiceApplicationTests'). The class contains some unit tests, to ensure that the services are running correctly.

Swagger interface contains detailed information to test all banking services, as explained in [related section](#springdocswagger-ui).

## Connecting to database

Transactions service contains a database tracking feature, that saves to database transactions id retrieved in every GET request. H2 with in-memory configuration is used as default database, after starting spring boot you can access the DB console application in the browser with [this URL](http://localhost:8080/h2-console).

You can use the following configuration in order to connect:

![Login](./h2-login.png?raw=true)

All GET requests are saved in ```TRANSACTIONS_REQUEST``` table, ```TRANSACTIONS_REQUEST_ID``` contains instead all related transactions id retrieved.

Since the default config is in-memory, all data is lost when the application stops, however you can customize the configuration in order persist data in a file, or use a different database manager.

## Springdoc/Swagger UI

When the application is running, [Swagger interface](http://localhost:8080/swagger-ui/index.html) is available to provide an additional documentation and interaction with APIs. In this section you can also find detailed information about response format of the services.


### Reference documentation
For further reference, you can consider the following sections:

* [Official Apache Maven documentation](https://maven.apache.org/guides/index.html)
* [Spring Boot Maven Plugin Reference Guide](https://docs.spring.io/spring-boot/docs/2.7.6/maven-plugin/reference/htmlsingle/)

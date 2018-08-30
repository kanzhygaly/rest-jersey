# Money Transfer RESTful API
Simple standalone application for money transfers between accounts.

## Technology stack:
 - JAX-RS 2.1 / Jersey [2.27](https://jersey.github.io/)
 - Grizzly HTTP server
 - Maven [3+](https://maven.apache.org/)
 - Java [1.8+](http://www.oracle.com/technetwork/java/javase/overview/index.html)
 - JPA / Hibernate
 - JTA for transaction management
 - c3p0 connection pool
 - Log4J for logging
 - H2 in-memory Database [latest](http://www.h2database.com)
 
## Build and Run
```sh 
mvn exec:java
```

Application starts on 
```sh 
http://localhost:8080/rest-jersey/
```

## Available Services
| HTTP METHOD | PATH | USAGE | TESTS |
| ----------- | ------ | ------ | ------ |
| GET | /account/{accountId} | get account by accountId | - |
| PUT | /account/create | create a new account | - |
| DELETE | /account/{accountId} | remove account by accountId | - |
| PUT | /account/{accountId}/withdraw/{amount} | withdraw money from account | - |
| PUT | /account/{accountId}/deposit/{amount} | deposit money to account | - |
| POST | /transfer | perform transfer from one account to another | + |

### Not Implemented
| HTTP METHOD | PATH | USAGE |
| -----------| ------ | ------ |
| GET | /account/all | get all accounts | 
| GET | /account/{accountId}/balance | get account balance by accountId | 

## TODO
- Implement left services
- Cover all tests
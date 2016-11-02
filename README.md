# Rapid prototype of a simple unsecured Lottery single-page application

## Frameworks

### Front-end

#### AngularJS 1.5.8
- **angular** - MVC-based UI framework
- **angular-resource** - a simple factory-pattern based AngularJS module
- **angular-smart-table** - an AngularJS module to display data in a table

#### Twitter Bootstrap
HTML, CSS, and JS framework.


### Back-end

#### Spring Boot
Spring Boot - a Spring project that makes it possible to write configuration-less web applications.
Dependencies: spring-boot-starter-web and spring-boot-starter-test.


## Configuration
Configure application by changing properties in the application.properties located in the **resources** folder. Allows to set lottery size (number of balls), ticket price, lottery bank (pot), and prize coefficients.

## Installation
Install front-end dependencies using Bower:
```
bower install
```

Use Maven to run unit tests and/or to package the application:
```
mvn test
```
mvn clean package
```

Run application from the command line:
```
java -jar target/lottery-1.0.0.jar
```
Open http://localhost:8080 in your prowser to start using the UI.
```

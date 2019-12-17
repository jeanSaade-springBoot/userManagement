# discount-app-services

# Retail Store Discount Application

Is an application to Calculate discount depending on Discount types.

### Prerequisites

- Java 8 and above
- Maven

### Installing

######Checkout the project from git

```
git clone path
```

######To run the application
```
mvn spring-boot:run
```

######To run tests

```
mvn clean install
```


## API documentation
no swagger documentation available
*  [api to get all users:]    http://localhost:9090/users api to get all users
*  [api to save user:]        http://localhost:9090/users/SaveUser/{first-Name}/{last-name}
*  [api to get user by Id:]   http://localhost:9090/users/id/{user-id}
*  [api to delete user by Id:] http://localhost:9090/users/deleteUserById/1

## Exception Management
*  in the class \user-management\src\main\java\com\user\support\ControllerExceptionHandler.java, 
   exception handling to return 404 if user not found

## Built With

* [Java](https://spring.io/) - The language Java
* [Spring Framework](https://spring.io/) - Spring framework


## UML Class Diagram

no uml diagram available

## Authors

* **Jean Saade**

## License

This project is licensed under the MIT License - see the [LICENSE.md](LICENSE.md) file for details

## Acknowledgments

* Thanks for the challenge





Some examples for SpecificationArgumentResolver with Spring Boot 3
===============================================

This is a simple Spring Boot project which presents [Specification Argument Resolver library](https://github.com/tkaczmarzyk/specification-arg-resolver).

It is an executable jar with embedded H2 db, so just build it with Maven and explore the API.
It is also configured to be compiled as native image.

Build
=====

How to build the app:
* jar
  * executable - `mvn clean install`
  * docker image `mvn spring-boot:build-image`
* native
  * executable - `mvn -Pnative native:compile` (should be visible as executable file in `target` directory) 
  * docker image - `mvn -Pnative spring-boot:build-image`
It is recommended to run tests against native image to be sure, that the native image contains all the code that is required at runtime:
* `mvn -PnativeTest test`

(Native image tested with maven 3.8.7 and GraalVM 22.3.0 for java 17 SDK version)


Usage examples
==============
You will find some samples below:

1. Get all customers:

   curl http://localhost:8080/customers

2. Delete a customer

   curl -X DELETE http://localhost:8080/customers/3

   it will perform a soft delete, which you can verify by getting all customers again -- the deleted customer will be still there, but with `deleted = true` flag. As you will see in subsequent points, specification-based query methods will filter it out.

3. Filter customers (include only not deleted ones) by first name:

   curl http://localhost:8080/customers?firstName=ar

4. Filter customers by last name and gender (gender param is optional):
   
   curl http://localhost:8080/customers?lastName=simp
   curl 'http://localhost:8080/customers?lastName=simp&gender=M'

5. Filter customers by name (either first or last, case insensitive) with paging:

   curl 'http://localhost:8080/customers?name=l&page=0&size=2&sort=id'

6. Filter customers by registration date:

   curl http://localhost:8080/customers?registeredBefore=2014-01-22 - returns customers registered before given date
   curl 'http://localhost:8080/customers?registeredAfter=2014-01-01&registeredBefore=2014-01-22' - returns customers registered between given `registeredAfter` and `registeredBefore` params

7. Filter customers by name (either first or last, case insensitive) and gender:

   curl 'http://localhost:8080/customers?name=l&gender=F'

8. Filter customers by registration date (customers registered before given date) and name (either first or last, case insensitive). It will return only not deleted customers:

   curl 'http://localhost:8080/customers?registeredBefore=2014-01-22&name=simp'

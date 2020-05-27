# epam-ahnl-ondoard

# Student management system

It's a simple REST-based CRUD Spring Boot application.
The com.epam.ahnl.main purpose is to get acquainted with the basics of the Kotlin language and the NoSql database MongoDB.
It's a multi module project with a three-layer architecture (Controller, Service and Model). Spring Data MongoDB is used to manipulate a student entity. Exception handler is implemented to catch Runtime exceptions. Dto object is validated before being stored in the database. Service is tested using MockK and Junit. Swagger documentation is prepared.
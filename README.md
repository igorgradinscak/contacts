# Contacts service 

This project is a contact management system developed using Java and Spring Boot. 
It provides functionality for creating, updating, and deleting contacts, along with associated phone numbers. 
The system uses a PostgreSQL database for data storage.

## Features

- Create, update, and delete contacts with associated phone numbers.
- Utilizes a PostgreSQL database for persistent data storage.
- Implements soft-delete logic to handle the deletion of records.


## Technologies Used

- Database:
    - [PostgreSQL](https://www.postgresql.org/)
- Backend:
    - Java 17
    - [Spring Boot](https://spring.io/projects/spring-boot)
    - [Spring Data JPA](https://spring.io/projects/spring-data-jpa)
    - [Flyway DB](https://flywaydb.org/)

## Installation and Setup

1. Clone this repository:
    ```shell
    git clone git@github.com:igorgradinscak/contacts.git
    ```
2. Create an empty PostgreSQL database named `contactdatabase`:
    ```postgresql
    CREATE DATABASE "contactdatabase";
    ```
3. Configure the `application-local.properties` file:
    - Modify the database connection settings (if needed):
        - `spring.datasource.url`
        - `spring.datasource.username`
        - `spring.datasource.password`
    - Modify the log levels (if needed):
        - `logging.level.root`
        - `logging.level.sql`
        - `logging.level.org.springframework.security`
        - `logging.level.org.jugendfeier.contacts`

## Running the application locally via IntelliJ IDEA

1. Create a new **Spring Boot** run configuration (for running the back-end application), with the following settings:
    - name: *Run back-end* (or something else appropriate)
    - JDK: `Java 17`
    - Main class: `org.jugenfeier.contacts.config.ContactsApplication`

## Usage

1. Access the backend application through your web browser or API client:
    - Local Backend URL: http://localhost:8080/api/

For more details about API endpoints, refer to the Open API documentation pages, that are available in
[JSON format](http://localhost:8080/api/v3/api-docs) and on a
[GUI interface](http://localhost:8080/api/swagger-ui/index.html).

## Database

The application is using a PostgreSQL database, called contactdatabase.

### Database tables ###

The database consists of the following tables:

1. `contact`:
    - Contains information about contacts, such as username, first_name, last_name, email, ID.
2. `phone_number`:
    - Contains information about phone numbers, such as the ID, call_number, telephone_number and contact_id, 
    - entities are in relation of one contact has many phone_numbers

### Soft-delete logic ###

The application uses **soft-delete** logic, which means that the data in the database is never deleted.
Instead, all tables have a field called `deleted` (of type `timestamp with time zone`) which contains the timestamp when
a particular object was deleted. <br/>
Additionally, some tables that store data that can be modified also contain `created` and `updated` fields (also of type
`timestamp with time zone`) that register the date and time when a particular object was created and last modified.

### Database migration ###

There is also an additional table called `flyway_schema_history`, which is used by [FlywayDB](https://flywaydb.org/)
migration tool for DB versioning purposes.<br/>
When a change is needed to the database design, you need to create a new file inside `src/main/resources/db/migration`
directory, name it `V<version_number>__<change_description>.sql`, add your database changes to that file, and the
changes
will be automatically applied to the database at the startup of the application.

If you also want to execute a particular script after making changes during development, 
delete the corresponding row from the flyway_schema_history table for that script, 
and drop the tables in the database that it creates. 
When starting the application, that script should run again.
**But be careful with this process; a better approach is to create new scripts with alter methods with version_number.**
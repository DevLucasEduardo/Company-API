# Company-System

## Introduction

This project is a RESTful API that is responsible for creating CRUD operations.
It uses Java and Spring boot, JPA for managing and persisting data, MySQL as the database, Swagger for the documentation of the methods and finally JUnit and Mockito for Unit tests.

## Packages

### com.lucas.company.model

This package contains entity classes representing departments and employees.

- **Department.java**: Represents a department in the company. It includes fields such as id, name, and employees.
- **Employee.java**: Represents an employee in the company. It includes fields such as id, name, cpf, birth date, civil status, and department.

### com.lucas.company.service

This package contains service classes for handling business logic related to departments and employees.

- **DepartmentService.java**: Provides methods for CRUD operations on departments, including getting all departments, getting a department by id, creating a new department, updating a department, and deleting a department.
- **EmployeeService.java**: Provides methods for CRUD operations on employees, including getting all employees, getting an employee by id, creating a new employee, updating an employee, and deleting an employee.

### com.lucas.company.controller

This package contains REST controllers for handling HTTP requests related to departments and employees.

- **DepartmentController.java**: Defines endpoints for managing departments, including getting all departments, getting a department by id, creating a new department, updating a department, and deleting a department.
- **EmployeeController.java**: Defines endpoints for managing employees, including getting all employees, getting an employee by id, creating a new employee, updating an employee, and deleting an employee.

### com.lucas.company.repository

This package contains repository interfaces for interacting with the database.

- **DepartmentRepository.java**: Provides methods for CRUD operations on departments.
- **EmployeeRepository.java**: Provides methods for CRUD operations on employees.

### com.lucas.company.model

This package contains DTO (Data Transfer Object) classes used for transferring data between the client and the server.

- **DepartmentDTO.java**: Represents a DTO for the department entity, used for transferring department data.
- **EmployeeDTO.java**: Represents a DTO for the employee entity, used for transferring employee data.

## Testing

### DepartmentServiceTest.java

This class contains unit tests for the `DepartmentService` class, covering methods such as post, put, and delete.

### EmployeeServiceTest.java

This class contains unit tests for the `EmployeeService` class, covering methods such as post, put, and delete.

## Technologies Used

- Spring Boot: Framework for building enterprise applications.
- Hibernate: ORM (Object-Relational Mapping) tool for mapping Java objects to database tables.
- JUnit: Framework for writing and running unit tests.
- Mockito: Framework for mocking dependencies in unit tests.
- MySQL: Relational database.

## Getting Started

To run the project locally, follow these steps:

1. Clone the repository to your local machine.
2. Configure your database settings in the `application.properties` file.
3. Build and run the application.
4. Use http://localhost:8080/swagger-ui/index.html#/ and check out the api documentation with swagger (the port might differ).

## Contributing

Contributions are welcome! If you find any issues or have suggestions for improvements, please feel free to open an issue or create a pull request.




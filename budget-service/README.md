# Budget Service

## Overview
The Budget Service allows users to manage budget categories and set budget amounts.

## Requirements
- Java 17
- Maven

## Getting Started

### Running the Service
1. Clone the repository.
2. Navigate to the `budget-service` directory.
3. Build the project using Maven:
   ```sh
   mvn clean install
4. Run the application:
   ```sh
   mvn spring-boot:run

### API Documentation
The Budget Service uses Swagger for API documentation.

- Swagger UI: http://localhost:8083/budget-service/swagger-ui.html
- OpenAPI Spec: http://localhost:8083/budget-service/v3/api-docs

### Postman Collection
A Postman collection is available for testing the Budget Service APIs

- [budgetMs.postman_collection.json](https://github.com/user-attachments/files/15787018/budgetMs.postman_collection.json)

### Endpoints
- GET /api/v1/budgets : 'Retrieve all budgets'
- POST /api/v1/budget : 'Create a new budget'
- PUT /api/v1/budget/{budgetId} : 'update existing budget'
- GET /api/v1/budget/{categoryName} : 'get Budget by category' 
- DELETE /api/v1/budget/{budgetId} : 'delete an existing budget'

### H2 Database Console
Access the H2 database console at (http://localhost:8083/budget-service/h2-console)

JDBC URL: jdbc:h2:mem:budgetdb
Username: sa
Password: password

### License
This project is licensed under the MIT License.

# Expense Service

## Overview
The Expense Service allows users to track expenses and communicates with the Notification Service to send alerts when budgets are exceeded.

## Requirements
- Java 17
- Maven

## Getting Started

### Running the Service
1. Clone the repository.
2. Navigate to the `expense-service` directory.
3. Build the project using Maven:
   ```sh
   mvn clean install
4. Run the application:
   ```sh
   mvn spring-boot:run

### API Documentation
The Expense Service uses Swagger for API documentation.

- Swagger UI: http://localhost:8082/expense-service/swagger-ui.html
- OpenAPI Spec: http://localhost:8082/expense-service/v3/api-docs

### Postman Collection
A Postman collection is available for testing the Budget Service APIs

- [ExpenseMS.postman_collection.json](https://github.com/user-attachments/files/15787141/ExpenseMS.postman_collection.json)


### Endpoints
- GET /api/v1/expenses : 'Retrieve all expenses'
- POST /api/v1/expense : 'Create a new expense'
- PUT /api/v1/expense/{expenseId} : 'update existing expense'
- GET /api/v1/expense/{expenseId} : 'get Expense by expenseId' 
- DELETE /api/v1/expense/{expenseId} : 'delete an existing expense'

### H2 Database Console
Access the H2 database console at (http://localhost:8082/expense-service/h2-console)

- JDBC URL: jdbc:h2:mem:expensedb
- Username: sa
- Password: password

### License
This project is licensed under the MIT License.

# Notification Service

## Overview
The Notification Service handles sending notifications when expenses exceed budgets.

## Requirements
- Java 17
- Maven

## Getting Started

### Running the Service
1. Clone the repository.
2. Navigate to the `notification-service` directory.
3. Build the project using Maven:
   ```sh
   mvn clean install
4. Run the application:
   ```sh
   mvn spring-boot:run

### API Documentation
The Notification Service uses Swagger for API documentation.

- Swagger UI: http://localhost:8081/notification-service/swagger-ui.html
- OpenAPI Spec: http://localhost:8081/notification-service/v3/api-docs

### Postman Collection
A Postman collection is available for testing the Notification Service APIs

- [NotificationMS.postman_collection.json](https://github.com/user-attachments/files/15787206/NotificationMS.postman_collection.json)



### Endpoints
- GET /api/v1/notifications : 'Retrieve all notifications'
- POST /api/v1/notification : 'Create a new notification'

### H2 Database Console
Access the H2 database console at (http://localhost:8081/notification-service/h2-console)

- JDBC URL: jdbc:h2:mem:notificationdb
- Username: sa
- Password: password

### License
This project is licensed under the MIT License.

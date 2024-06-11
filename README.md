# Microservices System Documentation

This document provides an overview of the microservices system consisting of 5 services developed using Spring Boot 3.2.5 and Java 17 to facilitate personal finance management. The system includes a Service Registry, API Gateway, Budget Service, Expense Service, and Notification Service.

## Services Overview

1. **Service Registry**: Registers all microservices for service discovery.
2. **API Gateway**: Routes requests to respective microservices.
3. **Budget Service**: create, update, delete budgets for categories.
4. **Expense Service**: create expenses and checks for any budget actegory exceeded and then publish notification.
5. **Notification Service**: notification api used for expense service to update notification.

Microservices follow DDD approach with following details:
1. Budget Context
- Entities: Budget
- Value Objects: Category
- Aggregates: Budget (root)
- Repositories: BudgetRepository
- Services: BudgetService

2. Expense Context
- Entities: Expense
- Aggregates: Expense (root)
- Repositories: ExpenseRepository
- Services: ExpenseService

3. Notification Context
- Entities: Notification
- Aggregates: Notification (root)
- Repositories: NotificationRepository
- Services: NotificationService

## Getting Started

Follow these steps to test the microservices:

2. Start the Service Registry.
3. Start the API Gateway service.
4. Start the Budget Service, Expense Service, and Notification Service.

Access Swagger UI for each service:

- Budget Service: [http://localhost:8083/budget-service/swagger-ui/index.html](http://localhost:8083/budget-service/swagger-ui/index.html)
- Expense Service: [http://localhost:8082/expense-service/swagger-ui/index.html](http://localhost:8082/expense-service/swagger-ui/index.html)
- Notification Service: [http://localhost:8081/notification-service/swagger-ui/index.html](http://localhost:8081/notification-service/swagger-ui/index.html)

You can interact with each service using Swagger UI and refer to the respective microservice README files for more details.

## Other important functionalities

1. Resiliency
 - circuitBreaker pattern is implemented between inter service communication
 - retry mechanism for connection related exception
2. Load Balancing
 - load balancing through api gateway
3. Service Registry
 - service discovery using eureka
 - eureka dashboard url : 'http://localhost:8761'

## Testing

Junit test cases have been written for each microservice to ensure proper functionality and exception handling. Error messages are appropriately displayed in case of service unavailability.


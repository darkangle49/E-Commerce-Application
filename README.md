# E-Commerce Application

## Overview

This project is a comprehensive **E-Commerce Application** designed to provide a seamless online shopping experience. It follows a **microservices architecture** to ensure scalability, maintainability, and flexibility.

## Microservices Architecture

The application consists of the following microservices:

- **Eurekaconfig** - Centralized service for managing configurations across microservices.
- **Gateway** - API Gateway that routes requests to appropriate services.
- **Order-Service** - Handles order placement, tracking, and history.
- **Payment-Service** - Manages payment transactions and integrations.
- **Product-Service** - Maintains product catalog and inventory.
- **Users-Service** - Manages user authentication, authorization, and profiles.

## Technologies Used

- **Programming Languages:** Java
- **Frameworks:** Spring Boot, Spring Cloud
- **Service Discovery:** Eureka
- **API Gateway:** Spring Cloud Gateway
- **Database:** MySQL/PostgreSQL
- **Messaging Queue:** RabbitMQ/Kafka (if applicable)
- **Containerization:** Docker
- **Orchestration:** Kubernetes

## Prerequisites

Ensure you have the following installed before running the application:

- JDK 11 or higher  
- Docker  
- Maven  
- PostgreSQL/MySQL (or configured database)  
- Any required API keys or environment variables  

## Installation and Setup

### 1. Clone the Repository

```bash
git clone https://github.com/darkangle49/E-Commerce-Application.git
cd E-Commerce-Application

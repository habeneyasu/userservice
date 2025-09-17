# 🚀 User Service - E-Commerce Microservices Platform

[![Java](https://img.shields.io/badge/Java-17+-orange.svg)](https://openjdk.java.net/)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.1.4-brightgreen.svg)](https://spring.io/projects/spring-boot)
[![Spring Security](https://img.shields.io/badge/Spring%20Security-6.0+-blue.svg)](https://spring.io/projects/spring-security)
[![PostgreSQL](https://img.shields.io/badge/PostgreSQL-8.0+-blue.svg)](https://www.postgresql.org/)
[![Docker](https://img.shields.io/badge/Docker-Enabled-2496ED.svg)](https://www.docker.com/)
[![License](https://img.shields.io/badge/License-MIT-yellow.svg)](LICENSE)

A robust, production-ready **User Management Service** built as part of a comprehensive e-commerce microservices platform. This service handles user authentication, authorization, profile management, and integrates seamlessly with other microservices in the ecosystem.

## 📋 Table of Contents

- [Overview](#overview)
- [Architecture](#architecture)
- [Features](#features)
- [Technologies](#technologies)
- [Quick Start](#quick-start)
- [API Documentation](#api-documentation)
- [Configuration](#configuration)
- [Monitoring & Observability](#monitoring--observability)
- [Docker Deployment](#docker-deployment)
- [Development](#development)
- [Contributing](#contributing)
- [License](#license)

## 🎯 Overview

The User Service is a critical component of our e-commerce microservices platform, providing:

- **Secure Authentication** using JWT tokens
- **Role-based Authorization** with Spring Security
- **User Profile Management** with full CRUD operations
- **Microservices Integration** via service discovery
- **Comprehensive Monitoring** with Prometheus metrics
- **Distributed Tracing** for request flow analysis

## 🏗️ Architecture

```
┌─────────────────┐    ┌─────────────────┐    ┌─────────────────┐
│   API Gateway   │───▶│   User Service  │───▶│   PostgreSQL    │
│                 │    │                 │    │   Database      │
└─────────────────┘    └─────────────────┘    └─────────────────┘
         │                       │                       │
         │                       ▼                       │
         │              ┌─────────────────┐              │
         │              │   Eureka        │              │
         │              │   Discovery     │              │
         └──────────────┴─────────────────┴──────────────┘
```

## ✨ Features

### 🔐 Authentication & Security
- **JWT-based Authentication** with configurable token expiration
- **Password Encryption** using BCrypt with salt rounds
- **Role-based Access Control** (RBAC) with Spring Security
- **CORS Configuration** for cross-origin requests
- **Request Validation** and input sanitization

### 👤 User Management
- **User Registration** with email validation
- **Profile Management** with comprehensive user data
- **User Search & Filtering** with pagination support
- **Bulk Operations** for user management
- **Soft Delete** functionality for data integrity

### 🔧 Technical Features
- **RESTful API** with OpenAPI 3.0 documentation
- **Database Migrations** with Flyway
- **Connection Pooling** with HikariCP
- **Caching** with Redis integration
- **Health Checks** and readiness probes
- **Metrics Collection** for monitoring

### 📊 Monitoring & Observability
- **Prometheus Metrics** for performance monitoring
- **Distributed Tracing** with Jaeger integration
- **Structured Logging** with JSON format
- **Health Endpoints** for service discovery
- **Custom Business Metrics** for user operations

## 🛠️ Technologies

| Category | Technology | Version |
|----------|------------|---------|
| **Framework** | Spring Boot | 3.1.4 |
| **Security** | Spring Security | 6.0+ |
| **Database** | PostgreSQL | 8.0+ |
| **ORM** | Spring Data JPA | 3.0+ |
| **Authentication** | JWT (JSON Web Token) | 0.12.6 |
| **Documentation** | OpenAPI 3.0 (Swagger) | 3.0+ |
| **Monitoring** | Prometheus, Grafana | Latest |
| **Tracing** | Jaeger | 1.55+ |
| **Containerization** | Docker | Latest |
| **Build Tool** | Maven | 3.6+ |
| **Java** | OpenJDK | 17+ |

## 🚀 Quick Start

### Prerequisites

- **Java 17+** (OpenJDK recommended)
- **Maven 3.6+**
- **Docker & Docker Compose**
- **PostgreSQL 8.0+** (or use Docker)

### 1. Clone the Repository

```bash
git clone https://github.com/habeneyasu/e-commerce.git
cd e-commerce/userservice
```

### 2. Environment Setup

Create a `.env` file in the project root:

```bash
# Database Configuration
SPRING_DATASOURCE_URL=jdbc:postgresql://localhost:3307/user_service_db
SPRING_DATASOURCE_USERNAME=user
SPRING_DATASOURCE_PASSWORD=your_secure_password

# JWT Configuration
JWT_SECRET=your_super_secret_jwt_key_here
JWT_EXPIRATION=86400000

# Redis Configuration (Optional)
REDIS_HOST=localhost
REDIS_PORT=6379
```

### 3. Run with Docker Compose (Recommended)

```bash
# Start all services including database
docker-compose up -d

# Check service status
docker-compose ps

# View logs
docker-compose logs -f user-service
```

### 4. Run Locally

```bash
# Build the project
mvn clean install

# Run the application
mvn spring-boot:run
```

### 5. Verify Installation

- **Service Health**: http://localhost:8181/api/v1/actuator/health
- **API Documentation**: http://localhost:8181/api/v1/swagger-ui/index.html
- **Prometheus Metrics**: http://localhost:8181/api/v1/actuator/prometheus

## 📚 API Documentation

### Base URL
```
http://localhost:8181/api/v1
```

### Authentication Endpoints

| Method | Endpoint | Description | Auth Required |
|--------|----------|-------------|---------------|
| `POST` | `/auth/login` | User login with credentials | ❌ |
| `POST` | `/auth/register` | User registration | ❌ |
| `POST` | `/auth/refresh` | Refresh JWT token | ✅ |
| `POST` | `/auth/logout` | User logout | ✅ |

### User Management Endpoints

| Method | Endpoint | Description | Auth Required |
|--------|----------|-------------|---------------|
| `GET` | `/users` | Get all users (paginated) | ✅ |
| `GET` | `/users/{id}` | Get user by ID | ✅ |
| `POST` | `/users` | Create new user | ✅ |
| `PUT` | `/users/{id}` | Update user | ✅ |
| `DELETE` | `/users/{id}` | Delete user (soft delete) | ✅ |
| `GET` | `/users/search` | Search users with filters | ✅ |

### Health & Monitoring Endpoints

| Method | Endpoint | Description |
|--------|----------|-------------|
| `GET` | `/actuator/health` | Service health status |
| `GET` | `/actuator/metrics` | Application metrics |
| `GET` | `/actuator/prometheus` | Prometheus metrics |

### Example API Usage

#### 1. User Registration
```bash
curl -X POST http://localhost:8181/api/v1/auth/register \
  -H "Content-Type: application/json" \
  -d '{
    "username": "john_doe",
    "email": "john@example.com",
    "password": "securePassword123",
    "firstName": "John",
    "lastName": "Doe"
  }'
```

#### 2. User Login
```bash
curl -X POST http://localhost:8181/api/v1/auth/login \
  -H "Content-Type: application/json" \
  -d '{
    "username": "john_doe",
    "password": "securePassword123"
  }'
```

#### 3. Get User Profile (with JWT)
```bash
curl -X GET http://localhost:8181/api/v1/users/1 \
  -H "Authorization: Bearer YOUR_JWT_TOKEN"
```

## ⚙️ Configuration

### Application Properties

The service uses Spring Boot's configuration system with profiles:

- **`application.properties`** - Base configuration
- **`application-container.properties`** - Container-specific settings
- **`application-dev.properties`** - Development environment
- **`application-prod.properties`** - Production environment

### Key Configuration Options

```properties
# Server Configuration
server.port=8181
server.servlet.context-path=/api/v1

# Database Configuration
spring.datasource.url=jdbc:postgresql://localhost:3307/user_service_db
spring.datasource.username=user
spring.datasource.password=${SPRING_DATASOURCE_PASSWORD}
spring.jpa.hibernate.ddl-auto=validate

# JWT Configuration
jwt.secret=${JWT_SECRET}
jwt.expiration=86400000

# Monitoring Configuration
management.endpoints.web.exposure.include=health,info,metrics,prometheus
management.metrics.export.prometheus.enabled=true
```

## 📊 Monitoring & Observability

### Prometheus Metrics

The service exposes comprehensive metrics:

- **HTTP Request Metrics**: Request count, duration, status codes
- **JVM Metrics**: Memory usage, GC performance, thread count
- **Database Metrics**: Connection pool status, query performance
- **Custom Business Metrics**: User registrations, login attempts, etc.

### Grafana Dashboards

Pre-configured dashboards available:
- **Service Overview**: Request rates, error rates, response times
- **JVM Performance**: Memory usage, GC metrics, thread analysis
- **Database Performance**: Connection pool, query performance
- **Business Metrics**: User operations, authentication events

### Distributed Tracing

Integrated with Jaeger for request tracing:
- **Request Flow Analysis**: Track requests across microservices
- **Performance Bottlenecks**: Identify slow operations
- **Error Tracking**: Trace errors through the system

## 🐳 Docker Deployment

### Dockerfile

```dockerfile
FROM openjdk:17-jdk-slim
VOLUME /tmp
COPY target/user_app.jar app.jar
EXPOSE 8181
ENTRYPOINT ["java","-jar","/app.jar"]
```

### Docker Compose

```yaml
version: '3.8'
services:
  user-service:
    build: ./userservice
    ports:
      - "8181:8181"
    environment:
      - SPRING_DATASOURCE_URL=jdbc:postgresql://user-service-db:3306/user_service_db
      - SPRING_DATASOURCE_USERNAME=user
      - SPRING_DATASOURCE_PASSWORD=test@123
    depends_on:
      - user-service-db
    networks:
      - microservice-net

  user-service-db:
    image: postgres:8.0
    environment:
      - POSTGRES_DB=user_service_db
      - POSTGRES_USER=user
      - POSTGRES_PASSWORD=test@123
    ports:
      - "3307:3306"
    networks:
      - microservice-net
```

### Deployment Commands

```bash
# Build and start services
docker-compose up --build

# Run in background
docker-compose up -d

# View logs
docker-compose logs -f user-service

# Stop services
docker-compose down
```

## 🛠️ Development

### Project Structure

```
userservice/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/ecommerce/userservice/
│   │   │       ├── config/          # Configuration classes
│   │   │       ├── controller/      # REST controllers
│   │   │       ├── dto/            # Data Transfer Objects
│   │   │       ├── entity/         # JPA entities
│   │   │       ├── repository/     # Data repositories
│   │   │       ├── service/        # Business logic
│   │   │       └── util/           # Utility classes
│   │   └── resources/
│   │       ├── application.properties
│   │       └── db/migration/       # Database migrations
│   └── test/                       # Test classes
├── Dockerfile
├── pom.xml
└── README.md
```

### Running Tests

```bash
# Run all tests
mvn test

# Run specific test class
mvn test -Dtest=UserServiceTest

# Run tests with coverage
mvn test jacoco:report
```

### Code Quality

The project follows:
- **Java Coding Standards** (Google Java Style)
- **Spring Boot Best Practices**
- **RESTful API Design Principles**
- **SOLID Principles**
- **Clean Architecture**

## 🤝 Contributing

We welcome contributions! Please follow these steps:

1. **Fork** the repository
2. **Create** a feature branch: `git checkout -b feature/amazing-feature`
3. **Commit** your changes: `git commit -m 'Add amazing feature'`
4. **Push** to the branch: `git push origin feature/amazing-feature`
5. **Open** a Pull Request

### Development Guidelines

- Write **unit tests** for new features
- Follow **existing code style**
- Update **documentation** as needed
- Ensure **all tests pass**
- Add **appropriate logging**

## 📄 License

This project is licensed under the **MIT License** - see the [LICENSE](LICENSE) file for details.

## 📞 Contact

**Haben Eyasu** - Senior Backend Developer

- **Email**: [haben.eyasu@gmail.com](mailto:haben.eyasu@gmail.com)
- **LinkedIn**: [linkedin.com/in/habeneyasu](https://linkedin.com/in/habeneyasu)
- **GitHub**: [github.com/habeneyasu](https://github.com/habeneyasu)
- **Portfolio**: [habeneyasu.github.io](https://habeneyasu.github.io)

---

## 🎯 Project Status

- ✅ **Core Features**: Complete
- ✅ **Authentication**: JWT-based security implemented
- ✅ **API Documentation**: OpenAPI 3.0 with Swagger UI
- ✅ **Monitoring**: Prometheus metrics and health checks
- ✅ **Docker Support**: Containerized deployment ready
- ✅ **Database**: PostgreSQL with migrations
- 🔄 **Testing**: Unit and integration tests in progress
- 🔄 **Performance**: Load testing and optimization ongoing

---

**Built with ❤️ by [Haben Eyasu](https://github.com/habeneyasu)**
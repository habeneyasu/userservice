
# User Service - E-commerce Application
This is the User Service for an e-commerce application. It manages user data, authentication, roles, and authorization using JWT (JSON Web Token) and Spring Security. The service is built with Spring Boot and exposes RESTful endpoints for user management. Additionally, it integrates with MySQL as the database and includes an API documentation powered by OpenAPI (Swagger).

Table of Contents
    Technologies
    Features
    Installation
    Usage
    Endpoints
    Swagger/OpenAPI
    Docker
    Environment Variables
    Contributing
    License
    Contact
Technologies
Spring Boot 3.2.7 - Backend framework
Spring Security - Security framework for authentication and authorization
JWT (JSON Web Token) - Used for stateless authentication
MySQL 5.7 - Database for storing user data
Docker - Containerization tool for deployment
OpenAPI (Swagger) - API documentation and testing tool

Features
User registration and management
Role-based authorization
JWT-based authentication
Custom login endpoint with secure password storage (bcrypt)
Full CRUD functionality for users
RESTful API endpoints with proper security annotations
Cross-Origin Resource Sharing (CORS) support
Integration with Jenkins for CI/CD (example included)
OpenAPI documentation for easy API exploration

Installation
Prerequisites
Ensure you have the following installed:

Java 17+ (JDK)
Maven 3.6+
Docker
Steps to Run Locally
Clone the repository:

git clone https://github.com/habeneyasu/userservice.git
cd user-service
Set up environment variables: Create a .env file in the root directory and set the following variables:

SPRING_DATASOURCE_PASSWORD=your_db_password

Build the project:

mvn clean install
Run the application:
mvn spring-boot:run

Using Docker
To run the service using Docker, follow these steps:

Build and start the services using Docker Compose:


docker-compose up --build
The application will be available at http://localhost:8181.

Usage
Endpoints
The following endpoints are exposed:

Public Endpoints
GET /users/api/v1/welcome: Returns a welcome message.
POST /users/api/v1/login: Login with username and password, returns JWT token.
Authenticated Endpoints (JWT required)
GET /users/api/v1/getUserById: Retrieve user by ID.
GET /users/api/v1/getAllUsers: Retrieve all users.
POST /users/api/v1/createUser: Create a new user.
PUT /users/api/v1/updateUser/{id}: Update user details.
DELETE /users/api/v1/deleteUser/{id}: Delete a user by ID.
For a full list of endpoints, see the Swagger/OpenAPI documentation at /swagger-ui.html.

Swagger/OpenAPI
This service is fully documented with OpenAPI 3 using Swagger.

Once the service is running, access the API documentation at:

URL: http://localhost:8181/swagger-ui/index.html
The documentation includes:

Request parameters
Response models
Ability to test API endpoints directly from the UI
Docker
The project includes a Docker Compose file for easy setup of the services. The docker-compose.yml defines the MySQL database and the user service.

Docker Compose Configuration
yaml
Copy code
version: '3.8'

services:
db:
image: mysql:5.7
environment:
MYSQL_ROOT_PASSWORD: ${SPRING_DATASOURCE_PASSWORD}
MYSQL_DATABASE: user_service_db
MYSQL_USER: user
MYSQL_PASSWORD: ${SPRING_DATASOURCE_PASSWORD}
ports:
- "3307:3306"
networks:
- ecommerce-network

user-service:
image: user-service
build: .
ports:
- "8080:8080"
networks:
- ecommerce-network
depends_on:
- db
environment:
SPRING_DATASOURCE_URL: jdbc:mysql://db:3306/user_service_db
SPRING_DATASOURCE_USERNAME: user
SPRING_DATASOURCE_PASSWORD: ${SPRING_DATASOURCE_PASSWORD}

How to Run
Ensure Docker is installed and running.
Start the application using:

docker-compose up
The service will be available at http://localhost:8181.

Environment Variables
The following environment variables need to be configured for the service:

SPRING_DATASOURCE_PASSWORD: Password for the MySQL database.
JWT_SECRET: Secret key used for JWT generation and validation.

Example .env file:
SPRING_DATASOURCE_PASSWORD=your_password
JWT_SECRET=your_jwt_secret

#Contributing
If you wish to contribute to this project:

1.Fork the repository.
2.Create a new branch for your feature or bugfix.
3.Commit your changes with clear messages.
4.Open a pull request.

License
This project is licensed under the MIT License.

Contact
For any questions, feel free to reach out:

Email: habeneyasu@gmail.com
LinkedIn: https://www.linkedin.com/in/habeneyasu
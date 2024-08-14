# Use the official OpenJDK 17 image as the base image
FROM openjdk:17-jdk-slim

# Set the working directory inside the container
WORKDIR /app

# Copy the packaged jar file into the container
COPY target/user_service_app.jar user_service_app.jar

# Expose the port on which the app will run
EXPOSE 8181

# Command to run the Spring Boot application
ENTRYPOINT ["java", "-jar", "user_service_app.jar"]

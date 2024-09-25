# Base image with OpenJDK
FROM openjdk:17-jdk-alpine
# Copy built JAR file
COPY target/user_app.jar app.jar
# Expose the port
EXPOSE 8282
# Run the JAR file
ENTRYPOINT ["java", "-jar", "app.jar"]

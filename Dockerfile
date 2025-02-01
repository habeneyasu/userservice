# Step 1: Use an official Java 17 runtime as a parent image
FROM openjdk:17-jdk-slim as build

# Step 2: Set the working directory inside the container
WORKDIR /app

# Step 3: Copy the Maven build file (pom.xml) to the container
COPY pom.xml .

# Step 4: Download and install the dependencies (without running tests)
RUN ./mvnw dependency:go-offline -B

# Step 5: Copy the entire source code into the container
COPY src /app/src

# Step 6: Build the application using Maven
RUN ./mvn clean package -DskipTests


# Step 7: Use a smaller base image for running the application
FROM openjdk:17-jre-slim

# Step 8: Set JAVA_HOME environment variable in the runtime container
ENV JAVA_HOME="/usr/lib/jvm/java-17-openjdk-amd64"
ENV PATH="${JAVA_HOME}/bin:${PATH}"

# Step 9: Set the working directory inside the container
WORKDIR /app

# Step 10: Copy the packaged .jar file from the build stage
COPY --from=build /app/target/*.jar app.jar

# Step 11: Expose the port your Spring Boot app runs on (default: 8080)
EXPOSE 8282

# Step 12: Define the command to run the application
ENTRYPOINT ["java", "-jar", "app.jar"]

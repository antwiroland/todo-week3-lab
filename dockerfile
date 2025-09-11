# Use an official OpenJDK image as a base
FROM eclipse-temurin:17-jdk-alpine AS builder

# Set working directory
WORKDIR /app

# Copy Maven/Gradle wrapper and project files (to leverage caching)
COPY mvnw pom.xml ./
COPY .mvn .mvn

# Download dependencies (this will be cached if pom.xml hasn't changed)
RUN ./mvnw dependency:go-offline -B

# Copy the source code
COPY src src

# Package the application (skip tests for faster build)
RUN ./mvnw package -DskipTests

# --------------------
# Stage 2: Run stage
# --------------------
FROM eclipse-temurin:21-jdk-alpine

# Set working directory
WORKDIR /app

# Copy the JAR file from the builder stage
COPY --from=builder /app/target/*.jar app.jar

# Expose application port (default: 8080)
EXPOSE 8080

# Set entrypoint to run the Spring Boot application
ENTRYPOINT ["java", "-jar", "app.jar"]

# ------------------------------------------------
# Stage 1: Build the Spring Boot application
# ------------------------------------------------
FROM eclipse-temurin:17-jdk-alpine AS builder

# Set working directory
WORKDIR /app

# Copy Maven wrapper and configuration
COPY mvnw pom.xml ./
COPY .mvn .mvn

# Ensure mvnw is executable
RUN chmod +x mvnw

# Download project dependencies (cached if pom.xml hasn’t changed)
RUN ./mvnw dependency:go-offline -B

# Copy the source code
COPY src src

# Build the application JAR (skip tests for faster build)
RUN ./mvnw package -DskipTests


# ------------------------------------------------
# Stage 2: Create the runtime image
# ------------------------------------------------
FROM eclipse-temurin:17-jre

# Set working directory
WORKDIR /app

# Copy the JAR file from the builder stage
COPY --from=builder /app/target/*.jar app.jar

# Expose application port
EXPOSE 8080

# Run the Spring Boot application
ENTRYPOINT ["java", "-jar", "app.jar"]

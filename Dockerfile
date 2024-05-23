# Step 1: Build the application
FROM gradle:8.7-jdk17 AS build

# Set the working directory
WORKDIR /app

# Copy the Gradle wrapper and necessary build files
COPY build.gradle.kts settings.gradle.kts /app/

# Copy the source code
COPY src /app/src

# Build the application
RUN gradle build --no-daemon

# Step 2: Create the final image to run the application
FROM openjdk:17-jre-slim

# Set the working directory
WORKDIR /app

# Copy the built application from the previous stage
COPY --from=build /app/build/libs/*.jar app.jar

# Expose the application port (default for Spring Boot)
EXPOSE 8080

# Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]

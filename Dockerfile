FROM openjdk:17.0.1-jdk-slim
WORKDIR /app
COPY build/libs/*.jar /app/budgeteer-api.jar
EXPOSE 8080
CMD ["java", "-jar", "/app/budgeteer-api.jar"]

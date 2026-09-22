# Use Java 17
FROM eclipse-temurin:17-jdk

# Working directory inside container
WORKDIR /app

# Install Maven
RUN apt-get update && apt-get install -y maven

# Copy the actual Spring Boot project
COPY myproject/ .

# Build Spring Boot application
RUN mvn clean package -DskipTests

# Spring Boot port
EXPOSE 8080

# Start application
CMD ["sh", "-c", "java -jar target/*.jar"]
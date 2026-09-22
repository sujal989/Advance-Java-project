# Use Java 17
FROM eclipse-temurin:17-jdk

# Set working directory
WORKDIR /app

# Install Maven
RUN apt-get update && apt-get install -y maven

# Copy project files into container
COPY . .

# Build Spring Boot application
RUN mvn clean package -DskipTests

# Spring Boot port
EXPOSE 8080

# Run generated JAR
CMD ["sh", "-c", "java -jar target/*.jar"]
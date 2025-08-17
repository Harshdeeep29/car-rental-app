# Step 1: Build with Maven
FROM maven:3.9.9-eclipse-temurin-21 AS builder
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests

# Step 2: Run with JDK
FROM eclipse-temurin:21-jdk
WORKDIR /app
COPY --from=builder /app/target/backend-0.0.1-SNAPSHOT.jar app.jar

# Expose backend port
EXPOSE 9090

# Run
ENTRYPOINT ["java","-jar","app.jar"]

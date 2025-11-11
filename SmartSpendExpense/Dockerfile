# Build stage
FROM maven:3.9.6-eclipse-temurin-17 AS build

WORKDIR /app

# Copy pom.xml first for better caching
COPY pom.xml .
RUN mvn dependency:go-offline -B

# Copy source code
COPY src ./src

# Build the application
RUN mvn clean package -DskipTests

# Runtime stage
FROM eclipse-temurin:17-jre-alpine

WORKDIR /app

# Copy the built JAR file
COPY --from=build /app/target/SmartSpendExpense-0.0.1-SNAPSHOT.jar app.jar

# Expose application port
EXPOSE 8081

# Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]





#FROM maven:3.8.5-openjdk-17 AS build
#COPY . .
#RUN mvn clean package -DskipTests

#FROM openjdk:17-jdk-slim
#
#WORKDIR /app
#
#COPY --from=build /target/SmartSpendExpense-0.0.1-SNAPSHOT.jar /app/SmartSpendExpense-0.0.1-SNAPSHOT.jar
#
#EXPOSE 8080
#
#CMD ["java", "-jar" ,"SmartSpendExpense-0.0.1-SNAPSHOT.jar"]

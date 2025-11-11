# Build stage
FROM maven:3.9.6-eclipse-temurin-17 AS build

WORKDIR /app

# Copy the pom.xml first and download dependencies
COPY pom.xml .
RUN mvn dependency:go-offline -B

# Copy the rest of the source code
COPY src ./src

# Build the jar file - FIXED JAR NAME
RUN mvn clean package -DskipTests

# Use a smaller JDK image to run the app
FROM eclipse-temurin:17-jre-alpine

WORKDIR /app

# Copy the jar built in the previous stage - FIXED JAR NAME
COPY --from=build /app/target/SmartSpendExpense-0.0.1-SNAPSHOT.jar app.jar

# Create a non-root user to run the application
RUN addgroup -S spring && adduser -S spring -G spring
USER spring

EXPOSE 8081

# Health check (updated port)
HEALTHCHECK --interval=30s --timeout=3s --start-period=5s --retries=3 \
    CMD wget --no-verbose --tries=1 --spider http://localhost:8081/ || exit 1

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
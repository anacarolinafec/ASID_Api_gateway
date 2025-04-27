# Use an official OpenJDK runtime as a parent image (Choose a version compatible with your project, e.g., 17)
FROM eclipse-temurin:17-jre-jammy

# Set the working directory inside the container
WORKDIR /app

# Copy the executable JAR file generated LOCALLY (e.g., via 'mvn package')

COPY target/gateway-0.0.1-SNAPSHOT.jar app.jar

# Inform Docker that the container will listen on this port at runtime
# MATCH THIS to the server.port in the Gateway's application.yml
EXPOSE 9000

# Define the command to run the application when the container starts
ENTRYPOINT ["java", "-jar", "app.jar"]
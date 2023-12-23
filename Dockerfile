# Use an official OpenJDK runtime as a parent image
FROM openjdk:11-jre-slim

# Set the working directory to /app
WORKDIR /app

# Copy the JAR file into the container at /app
COPY /sandbox/target/blog-0.0.1-SNAPSHOT.jar .

# Expose the port that your Java application listens on
EXPOSE 8080

# Define any environment variables (if needed)
ENV APP_ENV production

# Command to run your application
CMD ["java", "-jar", "blog-0.0.1-SNAPSHOT.jar"]


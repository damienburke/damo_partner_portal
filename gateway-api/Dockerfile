# Use Debian-based OpenJDK 21 image
FROM openjdk:21-jdk-slim

## Install network utilities (ping, netcat, curl, etc.)
RUN apt-get update -y && apt-get install -y iputils-ping curl

# the JAR file path
ARG JAR_FILE=build/libs/*.jar

# Copy the JAR file from the build context into the Docker image
COPY ${JAR_FILE} application.jar

CMD apt-get update -y

# Set the default command to run the Java application
ENTRYPOINT ["java", "-Xmx2048M", "-jar", "/application.jar"]
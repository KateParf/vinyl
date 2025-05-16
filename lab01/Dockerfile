# Use official OpenJDK 21 base image
FROM eclipse-temurin:21-jdk-jammy

# Set working directory
WORKDIR /app

#COPY lab01/src src
#COPY lab01/pom.xml pom.xml
#RUN mvn clean package dependency:copy-dependencies -DincludeScope=runtime -DskipTests


# Copy the JAR file into the container
# Change 'your-application.jar' to your actual JAR file name
COPY vinyl.jar vinyl.jar

# Expose the port your app runs on (change 8080 if needed)
EXPOSE 8080

# Set JVM options (adjust as needed)
ENV JAVA_OPTS="-Xmx512m -Xms256m"

# Run the application
ENTRYPOINT ["sh", "-c", "java ${JAVA_OPTS} -jar vinyl.jar"]

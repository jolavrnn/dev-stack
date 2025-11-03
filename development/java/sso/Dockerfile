FROM eclipse-temurin:21-jdk-alpine
WORKDIR /app
COPY target/java-keycloak-mtls-1.0.0.jar app.jar
COPY keycloak.p12 /certs/keycloak.p12
COPY truststore.jks /certs/truststore.jks
ENTRYPOINT ["java", "-jar", "/app/app.jar"]
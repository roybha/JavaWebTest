
FROM eclipse-temurin:17


WORKDIR /app


COPY target/JavaWebTest-1.0-SNAPSHOT.jar app.jar


EXPOSE 9080


ENTRYPOINT ["java", "-jar", "app.jar"]

FROM openjdk:17
LABEL authors="Lyubomir Georgiev"

COPY target/D387_sample_code-0.0.2-SNAPSHOT.jar app/d387-app.jar

EXPOSE 8080 4200

ENTRYPOINT ["java", "-jar", "app/d387-app.jar"]
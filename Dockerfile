FROM eclipse-temurin:21

LABEL authors="gopalgodhani"

WORKDIR /app

COPY target/springboot-blog-rest-api-0.0.1-SNAPSHOT.jar /app/springboot-blog-app-docker-demo.jar

ENTRYPOINT ["java", "-jar", "springboot-blog-app-docker-demo.jar"]
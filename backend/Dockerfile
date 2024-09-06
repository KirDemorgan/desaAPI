FROM openjdk:17-jdk-slim AS build

WORKDIR /usr/share/app

COPY .. .

RUN apt-get update && \
    apt-get install -y maven postgresql-client && \
    apt-get clean

# Выполняем сборку проекта
RUN mvn clean package -DskipTests

FROM openjdk:17-jdk-slim

WORKDIR /usr/share/app

COPY --from=build /usr/share/app/target/desaAPI-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]
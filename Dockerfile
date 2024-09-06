FROM maven:3.8.6-openjdk-17 AS build

WORKDIR /app

# Копируем файлы проекта в контейнер
COPY . .

# Выполняем сборку проекта
RUN mvn clean package -DskipTests

# Используем минимальный образ для запуска приложения
FROM openjdk:17-jdk-slim

WORKDIR /app

# Копируем собранный .jar файл из предыдущего этапа
COPY --from=build /app/target/desaAPI-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]
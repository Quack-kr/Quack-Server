FROM gradle:8.5-jdk17 AS build

WORKDIR /app

COPY build.gradle settings.gradle ./
COPY gradle ./gradle

RUN gradle dependencies --no-daemon

COPY . .
RUN gradle clean build --no-daemon

FROM gradle:8.5-jdk17

WORKDIR /app

COPY --from=build /app/build/libs/QUACKServer-0.0.1-SNAPSHOT.jar app.jar

ENTRYPOINT ["java", "-jar", "app.jar", "--spring.config.location=/config/application.yml"]

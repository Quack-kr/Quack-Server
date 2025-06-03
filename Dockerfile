FROM gradle:8.5-jdk21 AS build

WORKDIR /app

COPY . .

RUN gradle dependencies --no-daemon

RUN gradle clean build -x test --no-daemon

FROM gradle:8.5-jdk21

WORKDIR /app

COPY --from=build /app/build/libs/QUACKServer-0.0.1-SNAPSHOT.jar app.jar

ENTRYPOINT ["java", "-jar", "app.jar", "--spring.config.location=/config/application.yml"]


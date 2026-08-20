FROM eclipse-temurin:21-jdk-alpine AS build

WORKDIR /workspace

COPY gradle gradle
COPY gradlew gradlew
COPY settings.gradle.kts build.gradle.kts gradle.properties ./
COPY app/build.gradle.kts app/build.gradle.kts

RUN chmod +x gradlew && ./gradlew :app:dependencies --no-daemon

COPY app/src app/src

RUN ./gradlew :app:bootJar --no-daemon

FROM eclipse-temurin:21-jre-alpine

RUN addgroup -S smart-budget && adduser -S smart-budget -G smart-budget

WORKDIR /app

COPY --from=build --chown=smart-budget:smart-budget /workspace/app/build/libs/*.jar app.jar

USER smart-budget

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "/app/app.jar"]

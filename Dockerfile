# 1. 빌드 스테이지
FROM gradle:8.5-jdk17 AS build
COPY --chown=gradle:gradle . /home/gradle/src
WORKDIR /home/gradle/src
RUN ./gradlew build -x test --no-daemon

# 2. 실행 스테이지
# 기존 openjdk 대신 정식 지원되는 eclipse-temurin을 사용합니다.
FROM eclipse-temurin:17-jdk-jammy
EXPOSE 8080
COPY --from=build /home/gradle/src/build/libs/*.jar app.jar
ENTRYPOINT ["java", "-jar", "/app.jar"]
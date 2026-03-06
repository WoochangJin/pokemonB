# 1. 빌드 스테이지
FROM gradle:8.5-jdk17 AS build
COPY --chown=gradle:gradle . /home/gradle/src
WORKDIR /home/gradle/src

# [수정] 실행 권한 부여 (Permission denied 해결)
RUN chmod +x gradlew

RUN ./gradlew build -x test --no-daemon

# 2. 실행 스테이지
FROM eclipse-temurin:17-jdk-jammy
EXPOSE 8080
COPY --from=build /home/gradle/src/build/libs/*.jar app.jar
ENTRYPOINT ["java", "-jar", "/app.jar"]
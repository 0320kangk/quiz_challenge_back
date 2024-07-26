FROM openjdk:17-jdk
LABEL maintainer="junho"
# 환경 변수 설정
ENV CHARACTER_IMG_PATH=/app/characterImg
#변수명 설정
ARG JAR_FILE=build/libs/quiz_challenge_backend-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} quiz_challenge_backend.jar
ENTRYPOINT ["java", "-jar", "/quiz_challenge_backend.jar"]

FROM openjdk:17
ARG JAR_FILE=build/libs/study_board.jar
COPY ${JAR_FILE} ./study_board.jar
ENV TZ=Asia/Seoul
ENTRYPOINT ["java", "-jar", "./study_board.jar", "--spring.profiles.active=local"]
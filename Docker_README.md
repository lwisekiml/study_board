# docker -v

# docker-compose -v

# docker login

# build.gradle
bootJar {
archiveFileName = 'study_board.jar'
}

# ./gradlew clean
# ./gradlew build

# Dockerfile
FROM openjdk:17
ARG JAR_FILE=build/libs/study_board.jar
COPY ${JAR_FILE} ./study_board.jar
ENV TZ=Asia/Seoul
ENTRYPOINT ["java", "-jar", "./study_board.jar", "--spring.profiles.active=local"]  // java -jar ./study_board.jar --spring.profiles.active=local

# 실행할 때 아래와 같이 실행을 했었다.
# java -jar .\build\libs\study_board.jar --spring.profiles.active=local

# docker build -t wk/study-test .
# docker images

# db, redis 실행됨
# docker-compose -f docker-compose-local.yml up

=====================================================================================

# DB 실행
> docker exec -it 1a97ad2840e3 bash
> mysql -uroot -p

# show tables; 시 테이블이 안 나오는 경우, 아래와 같이 재빌드 하여 컨테이너 실행
> $ docker-compose -f docker-compose-local.yml up --build

# redis-cli 접속
# docker exec -it {Container id} redis-cli --raw // redis cli로 접속


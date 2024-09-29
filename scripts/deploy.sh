##!/bin/bash
#CURRENT_PID=$(pgrep -f .jar)
#echo "현재 PID : $CURRENT_PID"
#if [ -z $CURRENT_PID ]
#then
#  echo "현재 구동중이 것이 없으므로 중지하지 않습니다."
#else
#  echo "kill $CURRENT_PID"
#  kill -9 $CURRENT_PID
#  sleep 3
#fi
#
#JAR_PATH="/home/ec2-user/study_board/build/libs/board-0.0.1-SNAPSHOT.jar"
#nohup java -jar $JAR_PATH --spring.profiles.active=dev
#echo "jar file deploy success!!!"


## 자주 사용하는 값 변수에 저장
#REPOSITORY=/home/ec2-user/app/step1
#PROJECT_NAME=springboot-aws
#
## build의 결과물 (jar 파일) 특정 위치로 복사
#echo "> build 파일 복사"
#cp $REPOSITORY/$PROJECT_NAME/build/libs/*.jar $REPOSITORY/
#
#echo "> 현재 구동중인 애플리케이션 pid 확인"
#CURRENT_PID=$(pgrep -f ${PROJECT_NAME}.*.jar)
#
#echo "> 현재 구동중인 애플리케이션 pid: $CURRENT_PID"
#if [ -z "$CURRENT_PID" ]; then
#	echo "> 현재 구동중인 애플리케이션이 없으므로 종료하지 않습니다."
#else
#	echo "> kill -15 $CURRENT_PID"
#	kill -15 $CURRENT_PID
#	sleep 5
#fi
#
#echo "> 새 애플리케이션 배포"
#JAR_NAME=$(ls -tr $REPOSITORY/ | grep jar | tail -n 1)
#
#echo "> Jar Name: $JAR_NAME"
#nohup java -jar $REPOSITORY/$JAR_NAME 2>&1 &

# ----------------------------------------------------------------------------------------------------

!/bin/bash
BUILD_JAR=$(ls /home/ec2-user/study_board/build/libs/study_board.jar)
JAR_NAME=$(basename $BUILD_JAR)

echo "> 현재 시간: $(date)" >> /opt/codedeploy-agent/deployment-root/deployment-logs/codedeploy-agent-deployments.log

echo "> build 파일명: $JAR_NAME" >> /opt/codedeploy-agent/deployment-root/deployment-logs/codedeploy-agent-deployments.log

echo "> build 파일 복사" >> /opt/codedeploy-agent/deployment-root/deployment-logs/codedeploy-agent-deployments.log
DEPLOY_PATH=/home/ec2-user/action/
cp $BUILD_JAR $DEPLOY_PATH

echo "> 현재 실행중인 애플리케이션 pid 확인" >> /opt/codedeploy-agent/deployment-root/deployment-logs/codedeploy-agent-deployments.log
CURRENT_PID=$(pgrep -f $JAR_NAME)

if [ -z $CURRENT_PID ]
then
  echo "> 현재 구동중인 애플리케이션이 없으므로 종료하지 않습니다." >> /opt/codedeploy-agent/deployment-root/deployment-logs/codedeploy-agent-deployments.log
else
  echo "> kill -9 $CURRENT_PID" >> /opt/codedeploy-agent/deployment-root/deployment-logs/codedeploy-agent-deployments.log
  sudo kill -9 $CURRENT_PID
  sleep 5
fi


DEPLOY_JAR=$DEPLOY_PATH$JAR_NAME
echo "> DEPLOY_JAR 배포"    >> /opt/codedeploy-agent/deployment-root/deployment-logs/codedeploy-agent-deployments.log
sudo nohup java -jar $DEPLOY_JAR >> /opt/codedeploy-agent/deployment-root/deployment-logs/codedeploy-agent-deployments.log 2>/opt/codedeploy-agent/deployment-root/deployment-logs/codedeploy-agent-deployments.log &

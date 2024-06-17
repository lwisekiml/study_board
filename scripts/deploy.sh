#!/bin/bash
CURRENT_PID=$(pgrep -f .jar)
echo "현재 PID : $CURRENT_PID"
if [ -z $CURRENT_PID ]
then
  echo "현재 구동중이 것이 없으므로 중지하지 않습니다."
else
  echo "kill $CURRENT_PID"
  kill -9 $CURRENT_PID
  sleep 3
fi

JAR_PATH="/home/ec2-user/study_board/build/libs/board-0.0.1-SNAPSHOT.jar"
nohup java -jar $JAR_PATH --spring.profiles.active=dev
echo "jar file deploy success!!!"

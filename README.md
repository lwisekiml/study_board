# 게시판 만들기

- ERD

![ERD](Document/erd.drawio.svg)

<br/>

#### 준비하기
---
1. Git clone 받기
```
git clone https://github.com/lwisekiml/study_board.git
```

2. application.yml 설정(active값을 local로 수정)
> spring:  
> &nbsp;&nbsp;profiles:  
> &nbsp;&nbsp;&nbsp;&nbsp;active: local

3. .env 파일 생성하여 아래 내용 채우기
> KAKAO_CLIENT_ID=  
> KAKAO_CLIENT_SECRET=  
> MY_MYSQL_PW=  
> GMAIL=  
> GMAIL_APP_PASSWORD=  

4. mysql 
database 생성(studydb로 설정되어 있다.)

5. docker 사용 방법(Redis도 같이 사용해야한다.)
- docker 사용시 datasource.url을 본인 설정과 맞게 수정
- AppConfig에서 다음과 같이 수정
```java
// return new VerificationCodeRepository();
// docker 사용할 때
return new RedisUtil(stringRedisTemplate());
```

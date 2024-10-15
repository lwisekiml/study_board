# 게시판 만들기

- 프론트 부터 백엔드 그리고 github action을 사용하여 CI/CD 까지 구축
- 로그 저장 기능
- Spring security에서 ouath2.0으로 인증 구현
- ouath2.0을 사용하여 카카오 소셜 로그인 개발
- CodeDeploy - EC2를 통한 자동 배포 구현
- 이메일 인증 기능(Redis 선택 가능), 파일 업로드 기능 구현

<br/>

▶ 기술 스택
- Back-End
  - Java 17
  - Spring Boot
  - Spring Security
  - Spring Data JPA
  - MySQL
  - Redis

- Fornt-End
  - Thymeleaf
  - Javascript

- Devops & Tool
  - AWS EC2
  - Docker
  - GitHub

---

- ERD

![ERD](Document/erd.drawio.svg)

---

### 준비하기


1. Git clone 받기
```
git clone https://github.com/lwisekiml/study_board.git
```

<br/>

2. application.yml 설정(active값을 local로 수정)
> spring:  
> &nbsp;&nbsp;profiles:  
> &nbsp;&nbsp;&nbsp;&nbsp;active: local

<br/>

3. .env 파일 생성하여 아래 내용 채우기
> KAKAO_CLIENT_ID=  
> KAKAO_CLIENT_SECRET=  
> MY_MYSQL_PW=  
> GMAIL=  
> GMAIL_APP_PASSWORD=  

<br/>

4. mysql   
database 생성(studydb로 설정되어 있다.)

<br/>

5. docker 사용 방법(Redis도 같이 사용해야한다.)
- docker 사용시 datasource.url을 본인 설정과 맞게 수정
- AppConfig에서 다음과 같이 수정
```java
// return new VerificationCodeRepository();
// docker 사용할 때
return new RedisUtil(stringRedisTemplate());
```

<br/>

6. 파일 저장 위치 변경
- 다음과 같이 설정되어 있는 파일 저장위치 수정 필요
```
file:
  dir: /G:/
```
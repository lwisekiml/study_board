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

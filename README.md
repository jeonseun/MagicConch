# Magic Conch (코딩 질문 웹사이트)
## Overivew
* 프로그래밍을 하면서 생기는 궁금증, 질문등을 공유할 수 있는 웹 서비스를 제작함
* 스프링 프레임워크 (스프링 부트) 를 활용하여 제작하였으며 기본적인 사용자 인증과 파일 업로드 게시물 업로드 그리고 페이징이 적용된 게시판 구현에 많은 부분을 할애 함

<br>

## Usage
### application.yaml 수정 (프로젝트 내의 resources 하위 .yaml 파일)

```
<Datasource Configuration> 데이터베이스 설정

spring:
  datasource:
    username: <MySQL ID>
    password: <MySQL Password>
    driver-class-name: com.mysql.cj.jdbc.Driver
    url: jdbc:mysql://<db-host>:<db-port>/<database-name>
```

```
<File Location and Path Configuration> 파일 저장위치 설정

custom:
  file:
    profile-path: /image/profile
    profile-location: <Profile Image save location>
    tag-image-path: /image/tag
    tag-image-location: <Tag Image save location>
```

### Build and Run (커맨드 라인 실행)
```
Build (jar 패키징, gradlew 이용)
./gradlew build -x test

Run (java 명령어로 실행)
java -jar <.jar path>/magic_conch-1.0.0.jar

'통상적인 .jar path는 프로젝트 내의 build/libs/임'
```

## Preview

### 회원가입

![회원가입](https://user-images.githubusercontent.com/50051656/111439696-f869a400-8748-11eb-9159-992552a041df.gif)

### 메인페이지 로그인 전

![메인페이지](https://user-images.githubusercontent.com/50051656/111439402-ae80be00-8748-11eb-8c4e-72d65f042a6b.gif)

### 메인페이지 로그인 후

![메인페이지-로그인후](https://user-images.githubusercontent.com/50051656/111439672-f43d8680-8748-11eb-92f3-b40d1c33bf07.gif)

### 질문올리기

![질문생성페이지](https://user-images.githubusercontent.com/50051656/111439784-0f0ffb00-8749-11eb-95bc-5d30dbd38cb9.gif)

### 질문리스트

![질문보기페이지](https://user-images.githubusercontent.com/50051656/111439774-0d463780-8749-11eb-9827-487f5bc9f6b0.gif)

### 팔로우 질문리스트

![팔로우질문리스트페이지](https://user-images.githubusercontent.com/50051656/111439778-0e776480-8749-11eb-9b31-52acbde60cbd.gif)

### 마이페이지 프로필

![마이페이지 프로필부분](https://user-images.githubusercontent.com/50051656/111439788-0fa89180-8749-11eb-983f-939dc2e24eb5.gif)

### 마이페이지 번들

![마이페이지 번들](https://user-images.githubusercontent.com/50051656/111439791-10412800-8749-11eb-9d3a-1b3aef6ab8f8.gif)

### 마이페이지 번들 생성

![마이페이지 번들생성페이지](https://user-images.githubusercontent.com/50051656/111439804-12a38200-8749-11eb-854e-9c0a66527e64.gif)

### 마이페이지 팔로우

![마이페이지 팔로우부분](https://user-images.githubusercontent.com/50051656/111439800-120aeb80-8749-11eb-9d80-25bdbb15033c.gif)

### 마이페이지 팀

![마이페이지 팀](https://user-images.githubusercontent.com/50051656/111439809-133c1880-8749-11eb-8385-3f20f7c80900.gif)

## 기능 목록
### 회원 기능
|No|기능|설명|
|---|---|---|
|1|회원가입|신규 회원을 등록함|
|2|로그인|서비스 이용을 위해 로그인 (세션유지)|
|3|팔로우|다른 사용자를 팔로우 함|
<br>

### 질문 기능
|No|기능|설명|
|---|---|---|
|1|질문등록|새로운 질문을 등록함|
|2|질문수정|이미 등록된 질문을 수정함|
|3|질문삭제|이미 등록된 질문을 삭제함|
|4|질문검색|특정 키워드, 카테고리로 질문을 검색함|
|5|질문좋아요|마음에 드는 질문에 좋아요 표시를 남김|
<br>

### 답변 기능
|No|기능|설명|
|---|---|---|
|1|답변달기|질문에 답변을 다는 기능|
|2|답변수정|작성된 답변을 수정함|
|3|답변삭제|작성된 답변을 삭제함|
|4|답변채택|질문 작성자가 만족하는 답변을 채택하는 기능|
<br>

### 번들 기능 (번들 = 질문 모음 저장소)
|No|기능|설명|
|---|---|---|
|1|번들 생성|신규 번들을 생성함, 생성 시 태그(프로그래밍 언어)를 선택|
|2|번들 조회|자기자신, 타 이용자, 특정 팀이 사용중인 번들 목록 조회|
<br>

### 팀 기능
|No|기능|설명|
|---|---|---|
|1|팀 생성|새로운 팀을 생성|
|2|번들 연결|번들(질문모음)을 팀에 연결함|
|3|멤버 추가|새로운 팀원 추가|
<br>

## Develop Enviroment
* jdk 11
* Spring boot 2.4.2
* Intellij IDEA
* MySQL 8.0.22

## Technical Stack
* Spring Web MVC
* Sprinb Security
* Spring data JPA
* QueryDSL
* Thymeleaf
* JQuery
* Material Bootstrap

## Contributors

* 하주현, mylifeforcoding@gmail.com
    * 메인 화면 구성
    * 질문 CRUD 및 좋아요 기능 구현
    * 답변 CRUD 및 채택 기능 구현
    * 팔로우 기능 구현
* 전세운, jeonseun0000@gmail.com
    * 회원가입, 로그인 기능 구현
    * 번들(질문 모음) 기능 구현
    * 팀 기능 구현
    * 레이아웃 구성
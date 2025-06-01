## 🕊️ Branch Naming Convention
> "이슈 번호(Issue number)-feat-기능 내용(feature details)" ex) 1-feat-social_login

## 📍 Commit Convention
|**Type**|설명|
|:--:|:--:|
|**Docs** |  문서 작성 및 수정 작업(README 등)  |
|**Add**  |  기능이 아닌 것 생성 및 추가 작업(파일·익스텐션·프로토콜 등)  |
|**Feat**  | 새로운 기능 추가 작업  |
|**Style** |  UI 관련 작업(UI 컴포넌트, Xib 파일, 컬러·폰트 작업 등)  |
|**Fix** |  에러 및 버그 수정, 기능에 대한 수정 작업  |
|**Edit** |  Fix가 아닌 모든 수정 작업(주석, 파일 및 폴더 위치, 코드 스타일 등)  |
|**Del**   | 파일, 에셋 등 삭제 작업  |
|**Set**   | 세팅 관련 작업  |
|**Test**  |  테스트 관련 작업  |

<br />

## 🗂 Folder Architecture
<pre>
org.quack.QUACKServer
├── auth
│   ├── service
│   ├── domain
│   └── validation
├── user
│   ├── controller
│   ├── service
│   ├── domain
│   └── repository
├── inquiry
│   ├── controller
│   ├── service
│   ├── domain
│   └── repository
├── restaurant
│   ├── controller
│   ├── service
│   ├── domain
│   └── repository
├── review
│   ├── controller
│   ├── service
│   ├── domain
│   └── repository
├── core
│   ├── common
│   │   ├── constant
│   │   ├── converter
│   │   ├── dto
│   │   ├── enums
│   │   ├── interceptor
│   │   ├── log
│   │   ├── util
│   │   └── validation
│   ├── config
│   │   ├── filter
│   │   ├── http
│   │   ├── security
│   │   ├── storage
│   │   │   ├── db
│   │   │   └── redis
│   │   └── dto
│   ├── error
│   ├── enums
│   └── jwt

├── external
│   ├── db
│   ├── redis
│   └── social
└── QuackServerApplication

</pre>



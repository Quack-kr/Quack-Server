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

org.quack.QUACKServer
├── domain
│   ├── user                    # 사용자 관련 도메인
│   │   ├── controller
│   │   ├── service
│   │   ├── domain
│   │   └── repository
│   │
│   ├── inquiry                 # 문의 관련 도메인
│   │   ├── controller
│   │   ├── service
│   │   ├── domain
│   │   └── repository
│   │
│   ├── restaurant              # 식당 관련 도메인
│   │   ├── controller
│   │   ├── service
│   │   ├── domain
│   │   └── repository
│   │
│   └── review                  # 리뷰 관련 도메인
│       ├── controller
│       ├── service
│       ├── domain
│       └── repository
│
├── global
│   ├── common                  # 공통 유틸, DTO, 상수 등
│   │   ├── constant
│   │   ├── converter
│   │   ├── dto
│   │   ├── enums
│   │   ├── interceptor
│   │   ├── log
│   │   ├── util
│   │   └── validation
│   │
│   ├── config                  # 설정 관련 클래스
│   │   ├── filter
│   │   ├── http
│   │   ├── security
│   │   ├── storage
│   │   │   ├── db
│   │   │   └── redis
│   │   └── value
│   │
│   ├── infra                   # 외부 시스템 연동
│   │   ├── db
│   │   ├── redis
│   │   └── social
│   │
│   ├── error                   # 공통 에러 처리
│   ├── enums                   # 글로벌 enum 모음
│   └── jwt                     # JWT 관련 처리
│
└── QuackServerApplication      # 스프링 부트 메인 클래스

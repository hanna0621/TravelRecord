# TravelRecord

여행지, 방문 날짜, 사진, 메모를 저장하고 관리할 수 있는 Android 여행 기록 앱입니다.

## 개발 환경

* Android Studio
* Kotlin
* Minimum SDK API 26
* SQLiteOpenHelper
* RecyclerView
* Fragment
* ViewBinding
* Google Maps SDK for Android

## 주요 기능

* 여행 기록 추가
* 여행 기록 목록 조회
* 여행 기록 상세 조회
* 여행 기록 수정 및 삭제
* 갤러리에서 사진 선택
* 사진 URI SQLite 저장
* 앱 종료 후 데이터 및 사진 유지
* 최신순 및 오래된순 정렬
* 전체 기록 삭제
* RecyclerView 항목 길게 눌러 수정 및 삭제
* 삭제 전 AlertDialog 확인
* Fragment 화면 전환 및 백스택
* Google 지도 표시
* 서울시청 위치 마커 표시
* 지도 확대·축소 및 뒤로가기

## 과제 요구사항 구현 확인표

| 과제 요구사항                  | 구현 내용                                       | 확인 방법                         |
| ------------------------ | ------------------------------------------- | ----------------------------- |
| Fragment 최소 2개 이상        | `FirstFragment`, `SecondFragment` 사용        | 목록 화면에서 `앱 정보` 버튼 클릭          |
| 백스택 관리                   | `navigateUp()` 및 Activity `finish()` 사용     | 앱 정보·상세·수정·지도 화면에서 뒤로가기       |
| RecyclerView 활용          | 여행 기록 목록을 RecyclerView로 표시                  | 기록 저장 후 첫 화면 목록 확인            |
| Adapter·ViewHolder 직접 구현 | `TravelAdapter`, `TravelViewHolder` 구현      | `adapter/TravelAdapter.kt` 확인 |
| RecyclerView 클릭 이벤트      | 목록 클릭 시 상세 화면 이동                            | 목록 카드 클릭                      |
| SQLiteOpenHelper 직접 구현   | `DBHelper` 클래스에서 DB 직접 구현                   | `database/DBHelper.kt` 확인     |
| CRUD 전체 동작               | 추가·조회·수정·삭제 구현                              | 기록 추가 후 상세·수정·삭제 테스트          |
| 앱 종료 후 데이터 유지            | SQLite와 사진 URI 지속 권한 사용                     | 앱 종료 후 재실행                    |
| 옵션 메뉴 2개 이상              | 최신순·오래된순·전체 삭제                              | 우측 상단 옵션 메뉴                   |
| 컨텍스트 메뉴                  | 목록 항목 길게 눌러 수정·삭제                           | 목록 카드 길게 누르기                  |
| 삭제 AlertDialog           | 개별 삭제 및 전체 삭제 전 확인                          | 삭제 메뉴 선택                      |
| 갤러리 선택 Intent            | `ActivityResultContracts.OpenDocument()` 사용 | 기록 추가 화면에서 사진 선택              |
| 사진 상세 표시                 | 목록·상세·수정 화면에 사진 표시                          | 사진이 있는 기록 확인                  |
| 추가·수정 별도 Activity        | `AddTravelActivity`, `EditTravelActivity`   | 기록 추가·수정 화면 진입                |
| 최소 SDK API 26 이상         | `minSdk = 26` 설정                            | `app/build.gradle.kts` 확인     |
| 지도 API 활용                | Google Maps SDK 지도와 마커 구현                   | 앱 정보 → 지도 보기                  |
| APK 제출                   | 디버그 APK 생성                                  | `app-debug.apk`               |
| GitHub 제출                | Public Repository 제공                        | 저장소 URL 제출                    |

## 화면 구성

### 1. 여행 기록 목록 화면

* 저장된 여행 기록 목록 표시
* 여행지명, 방문 날짜, 대표 사진 표시
* 기록 추가 버튼
* 앱 정보 버튼
* 최신순·오래된순·전체 삭제 옵션 메뉴

### 2. 여행 기록 추가 화면

* 여행지명 입력
* 방문 날짜 입력
* 메모 입력
* 갤러리 사진 선택
* SQLite 저장

### 3. 여행 기록 상세 화면

* 여행지명, 날짜, 메모, 사진 표시
* 수정 버튼
* 삭제 버튼
* 뒤로가기 버튼

### 4. 여행 기록 수정 화면

* 기존 값 자동 입력
* 사진 변경
* 수정 저장
* 뒤로가기 버튼

### 5. 앱 정보 화면

* 앱 주요 기능 안내
* 지도 보기 버튼
* 여행 목록 복귀 버튼

### 6. 지도 화면

* Google 지도 표시
* 서울시청 위치 마커
* 확대·축소 컨트롤
* 뒤로가기 버튼

## 데이터베이스 구조

| 컬럼명        | 타입      | 설명                        |
| ---------- | ------- | ------------------------- |
| no         | INTEGER | 기록 번호, Primary Key, 자동 증가 |
| place      | TEXT    | 여행지명                      |
| visit_date | TEXT    | 방문 날짜                     |
| memo       | TEXT    | 여행 메모                     |
| photo_uri  | TEXT    | 선택한 사진의 URI               |

## 주요 클래스

| 파일                      | 역할                               |
| ----------------------- | -------------------------------- |
| `MainActivity.kt`       | Fragment와 옵션 메뉴 관리               |
| `FirstFragment.kt`      | 여행 목록, 정렬, 컨텍스트 메뉴               |
| `SecondFragment.kt`     | 앱 정보 및 지도 화면 이동                  |
| `AddTravelActivity.kt`  | 여행 기록 추가                         |
| `DetailActivity.kt`     | 여행 기록 상세 조회 및 삭제                 |
| `EditTravelActivity.kt` | 여행 기록 수정                         |
| `MapActivity.kt`        | Google 지도 및 마커 표시                |
| `TravelAdapter.kt`      | RecyclerView Adapter와 ViewHolder |
| `DBHelper.kt`           | SQLiteOpenHelper 및 CRUD          |
| `Travel.kt`             | 여행 기록 데이터 모델                     |

## 채점용 권장 테스트 순서

1. 앱 실행
2. `기록 추가` 선택
3. 여행지명·날짜·메모 입력
4. 갤러리 사진 선택
5. 저장 후 목록 표시 확인
6. 목록 항목 클릭 후 상세 화면 확인
7. 수정 기능 확인
8. 목록 항목 길게 눌러 컨텍스트 메뉴 확인
9. 삭제 AlertDialog 확인
10. 옵션 메뉴에서 최신순·오래된순 확인
11. 전체 삭제 확인
12. 앱 정보 화면 이동
13. 지도 보기 선택
14. 서울시청 마커·확대축소·뒤로가기 확인
15. 앱 재실행 후 데이터 유지 확인

## 가산점 구현

* 지도 API 단순 구현 완료
* Google Maps SDK for Android 사용
* 서울시청 위치 마커 생성
* 지도 확대·축소 가능

미구현 가산점:

* 사진 GPS 정보 추출 후 지도 마커 생성
* 스레드·코루틴 비동기 처리

## 보안

Google Maps API Key는 `secrets.properties`에서 관리하며 GitHub 저장소에는 포함하지 않았습니다.

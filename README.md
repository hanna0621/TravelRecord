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
* Fragment 화면 전환
* Google 지도 표시
* 서울시청 위치 마커 표시
* 지도 확대 및 축소

## 데이터베이스 구조

| 컬럼명        | 타입      | 설명                        |
| ---------- | ------- | ------------------------- |
| no         | INTEGER | 기록 번호, Primary Key, 자동 증가 |
| place      | TEXT    | 여행지명                      |
| visit_date | TEXT    | 방문 날짜                     |
| memo       | TEXT    | 여행 메모                     |
| photo_uri  | TEXT    | 선택한 사진의 URI               |

## 화면 구성

* 여행 기록 목록 화면
* 여행 기록 추가 화면
* 여행 기록 상세 화면
* 여행 기록 수정 화면
* 앱 정보 화면
* 지도 화면

## 사용 방법

1. 기록 추가 버튼을 누릅니다.
2. 여행지명, 방문 날짜, 메모를 입력합니다.
3. 갤러리에서 사진을 선택합니다.
4. 저장 버튼을 누르면 여행 기록 목록에 추가됩니다.
5. 목록 항목을 누르면 상세 내용을 확인할 수 있습니다.
6. 목록 항목을 길게 누르면 수정 또는 삭제할 수 있습니다.
7. 옵션 메뉴에서 최신순, 오래된순 정렬 및 전체 삭제가 가능합니다.
8. 앱 정보 화면에서 지도 보기를 누르면 서울시청 마커가 표시된 지도를 확인할 수 있습니다.

## 보안

Google Maps API Key는 `secrets.properties`에서 관리하며 GitHub 저장소에는 포함하지 않았습니다.

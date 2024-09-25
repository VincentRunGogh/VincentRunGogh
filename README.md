## 프로젝트명
Vincent Run Gogh (B307) 

### 기획 의도
- GPS 아트를 중심으로 런닝, 산책 코스 전문 건강 관리 운동 앱 서비스 기획
- 사용자에게 런닝, 산책에 내가 뛴 경로로 그림을 그려나가는 엔터테인 요소를 제공 

### 메인 기능
- 지도에 그림을 그리거나 다른 사용자가 그려나간 런닝을 새롭게 루트화하여 지도 내 해당 그림에 유사한 경로를 제공
- 도로, 위치 GPS를 실시간으로 비교하면서 경로가 이탈할 시 음성 알림 제공  

### 서비스 프로세스 정리

1. 사용자에게 런닝, 산책을 위한 코스 그리기 정보를 받음  

2. 지도 내에 한붓그리기 형태의 그림을 그리면 그림 주변의 도로와 매칭시켜 경로 생성시키기
대전광역시 보행 가능 도로 좌표와 지도 위 작성한 그림을 K-D tree를 통해 가장 인접한 도로 지점 찾기  

3. 경로를 뛰거나 걸을 때, 루트 달리기 또는 자유달리기를 통해 내가 지나온 곳을 실시간으로 그려나감

4. 내가 뛴 경로를 커뮤니티 기능을 통해 다른 사용자와 공유할 수 있고, 사용자 맞춤형 건강, 운동 관리 기능을 위해 캘린더에 데일리 기준으로 운동 시간, 거리 등을 확인할 수 있음  

### Branch 사용
- develop-BE
    - Springboot 개발 branch
- develop-FE_TS
    - svelte + TypeScript 개발 branch
- develop-DATA
    - FastAPI + PySpark 개발 branch
---
## 09.25 진행 상황 (소주제 정리)
### 1. Server(Back)
- User, Auth, Route, Drawing, Board API 명세서 기반 개발 진행 중
- Pyspark, FastAPI -> 도보 데이터 기반 아트 루트화 진행  
- [API 명세서](https://marked-branch-c99.notion.site/API-223e2f47015b4774b01f0da97fd3042d)    
### 2. Client(Front)
- TypeScript 변환 후 화면 명세서 기반 개발 진행 중
- 전체 페이지 컴포넌트 구성 후 routing + css 작업 수행
- [화면 명세서](https://www.figma.com/design/UsAnl1kynqUpX2TbukgaCo/%EC%99%80%EC%9D%B4%EC%96%B4%ED%94%84%EB%A0%88%EC%9E%84)     
    
### 3. Infra(Main, Sub)
- 메인서버(Spring)와 서브서버(Python) 분리 후 배포 진행 중
- Server, Client 연결 작업을 위한 배포 환경 내 Swagger 구축  



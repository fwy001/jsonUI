# Kendo UI JSON 통합 시스템

JSON 설정을 기반으로 Kendo UI 컴포넌트(Grid, Chart, Form)를 동적으로 생성하는 시스템입니다.
서블릿과 JSON 통신을 통해 데이터를 관리하고, 폼 양식도 JSON으로 정의할 수 있습니다.

## 주요 기능

### 1. JSON 기반 Grid 생성
- Grid 구조를 JSON으로 정의
- 컬럼, 데이터소스, 페이징, 정렬, 필터링 설정
- CRUD 작업 지원 (생성, 읽기, 수정, 삭제)
- 편집 폼도 JSON으로 정의 가능

### 2. JSON 기반 Chart 생성
- Chart 유형과 시리즈를 JSON으로 정의
- Column, Line, Pie, Bar 등 다양한 차트 지원
- 축, 범례, 레이블 설정
- 동적 데이터 로딩

### 3. JSON 기반 Form 생성
- 폼 필드와 유효성 검사 규칙을 JSON으로 정의
- 다양한 입력 위젯 지원 (텍스트, 숫자, 날짜, 드롭다운 등)
- 자동 유효성 검사
- 서버 제출 기능

## 프로젝트 구조

```
jsonUI/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/jsonui/
│   │   │       ├── model/          # JSON 모델 클래스
│   │   │       │   ├── GridConfig.java
│   │   │       │   ├── ChartConfig.java
│   │   │       │   ├── FormConfig.java
│   │   │       │   └── ApiResponse.java
│   │   │       ├── servlet/        # 서블릿
│   │   │       │   ├── BaseServlet.java
│   │   │       │   ├── GridConfigServlet.java
│   │   │       │   ├── ChartConfigServlet.java
│   │   │       │   ├── FormConfigServlet.java
│   │   │       │   └── DataServlet.java
│   │   │       └── service/        # 비즈니스 로직
│   │   │           ├── GridConfigService.java
│   │   │           ├── ChartConfigService.java
│   │   │           ├── FormConfigService.java
│   │   │           └── DataService.java
│   │   ├── webapp/
│   │   │   ├── css/
│   │   │   │   └── styles.css
│   │   │   ├── js/
│   │   │   │   └── kendo-json-ui.js
│   │   │   └── index.html
│   │   └── resources/
│   └── test/
├── pom.xml
└── README.md
```

## API 엔드포인트

### Grid 설정
- `GET /api/grid/config/{gridId}` - Grid 설정 조회
- `GET /api/grid/config` - 모든 Grid 설정 목록
- `POST /api/grid/config` - Grid 설정 저장

### Chart 설정
- `GET /api/chart/config/{chartId}` - Chart 설정 조회
- `GET /api/chart/config` - 모든 Chart 설정 목록
- `POST /api/chart/config` - Chart 설정 저장

### Form 설정
- `GET /api/form/config/{formId}` - Form 설정 조회
- `GET /api/form/config` - 모든 Form 설정 목록
- `POST /api/form/config` - Form 설정 저장

### 데이터
- `GET /api/data/{type}` - 데이터 조회 (페이징, 정렬, 필터링 지원)
- `POST /api/data/{type}` - 데이터 생성
- `PUT /api/data/{type}` - 데이터 수정
- `DELETE /api/data/{type}/{id}` - 데이터 삭제

## JSON 스키마 예시

### Grid 설정 예시
```json
{
  "id": "employeeGrid",
  "title": "직원 관리",
  "columns": [
    {
      "field": "id",
      "title": "ID",
      "width": 80,
      "editable": false
    },
    {
      "field": "name",
      "title": "이름",
      "width": 150,
      "editable": true
    },
    {
      "field": "email",
      "title": "이메일",
      "width": 200,
      "editable": true
    }
  ],
  "dataSource": {
    "transport": {
      "read": {
        "url": "api/data/employees",
        "type": "GET"
      },
      "create": {
        "url": "api/data/employees",
        "type": "POST"
      }
    },
    "pageSize": 10,
    "schema": {
      "data": "data",
      "total": "total"
    }
  },
  "options": {
    "pageable": true,
    "sortable": true,
    "filterable": true,
    "editable": true,
    "editMode": "popup"
  }
}
```

### Chart 설정 예시
```json
{
  "id": "monthlySales",
  "title": "월별 매출 현황",
  "chartType": "column",
  "series": [
    {
      "type": "column",
      "field": "sales",
      "categoryField": "month",
      "name": "매출"
    }
  ],
  "dataSource": {
    "url": "api/data/sales"
  }
}
```

### Form 설정 예시
```json
{
  "id": "userRegistration",
  "title": "사용자 등록",
  "fields": [
    {
      "name": "username",
      "label": "사용자명",
      "type": "text",
      "required": true,
      "validationRules": {
        "minLength": 3,
        "maxLength": 20,
        "message": "사용자명은 3-20자 사이여야 합니다"
      }
    },
    {
      "name": "email",
      "label": "이메일",
      "type": "text",
      "required": true,
      "validationRules": {
        "pattern": "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$",
        "message": "올바른 이메일 주소를 입력하세요"
      }
    }
  ]
}
```

## 빌드 및 실행

### 요구사항
- Java 11 이상
- Maven 3.6 이상
- Tomcat 9 이상 (또는 다른 서블릿 컨테이너)

### 빌드
```bash
mvn clean package
```

### 실행
1. WAR 파일을 Tomcat의 webapps 디렉토리에 배포
2. Tomcat 시작
3. 브라우저에서 `http://localhost:8080/kendo-json-ui/` 접속

## 사용 방법

### JavaScript에서 Grid 생성
```javascript
KendoJsonUI.createGrid('gridContainer', 'api/grid/config/employeeGrid');
```

### JavaScript에서 Chart 생성
```javascript
KendoJsonUI.createChart('chartContainer', 'api/chart/config/monthlySales');
```

### JavaScript에서 Form 생성
```javascript
KendoJsonUI.createForm('formContainer', 'api/form/config/userRegistration');
```

## 기술 스택

### 백엔드
- Java 11
- Servlet 4.0
- Gson (JSON 처리)
- Maven

### 프론트엔드
- Kendo UI (Commercial 버전 필요)
- jQuery
- HTML5/CSS3
- JavaScript

## 라이선스

이 프로젝트는 데모 목적으로 제작되었습니다.
Kendo UI 사용을 위해서는 별도의 라이선스가 필요합니다.

## 기여

버그 리포트와 기능 제안은 이슈 트래커를 통해 제출해 주세요.

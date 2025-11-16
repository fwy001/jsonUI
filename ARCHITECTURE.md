# 시스템 아키텍처

## 개요

이 시스템은 JSON 기반으로 Kendo UI 컴포넌트를 동적으로 생성하는 웹 애플리케이션입니다.
서블릿과 JSON 통신을 통해 Grid, Chart, Form을 관리합니다.

## 아키텍처 다이어그램

```
┌─────────────────────────────────────────────────────────────┐
│                        프론트엔드                              │
│  ┌──────────────┐  ┌──────────────┐  ┌──────────────┐      │
│  │   index.html │  │  styles.css  │  │kendo-json-ui │      │
│  │              │  │              │  │     .js      │      │
│  └──────┬───────┘  └──────────────┘  └──────┬───────┘      │
│         │                                    │              │
│         └────────────────┬───────────────────┘              │
│                          │                                  │
└──────────────────────────┼──────────────────────────────────┘
                           │ JSON/HTTP
┌──────────────────────────┼──────────────────────────────────┐
│                          │    백엔드 (서블릿)                  │
│         ┌────────────────▼────────────────┐                 │
│         │      BaseServlet (공통)          │                 │
│         └─────────────┬───────────────────┘                 │
│                       │                                     │
│      ┌────────────────┼────────────────┐                    │
│      │                │                │                    │
│  ┌───▼───────┐  ┌────▼─────┐  ┌──────▼──────┐             │
│  │ GridConfig│  │ChartConfig│  │ FormConfig  │             │
│  │  Servlet  │  │  Servlet  │  │   Servlet   │             │
│  └───┬───────┘  └────┬─────┘  └──────┬──────┘             │
│      │               │                │                    │
│  ┌───▼────────┐  ┌──▼──────┐  ┌──────▼──────┐             │
│  │GridConfig  │  │ChartConfig│  │ FormConfig  │             │
│  │  Service   │  │  Service │  │   Service   │             │
│  └───┬────────┘  └──┬──────┘  └──────┬──────┘             │
│      │              │                 │                    │
│      └──────────────┴─────────────────┘                    │
│                     │                                      │
│          ┌──────────▼──────────┐                           │
│          │   DataServlet       │                           │
│          │   (CRUD 작업)        │                           │
│          └──────────┬──────────┘                           │
│                     │                                      │
│          ┌──────────▼──────────┐                           │
│          │   DataService       │                           │
│          │   (비즈니스 로직)     │                           │
│          └─────────────────────┘                           │
│                                                            │
└────────────────────────────────────────────────────────────┘
```

## 데이터 흐름

### 1. Grid 생성 흐름

```
1. 브라우저 → GET /api/grid/config/employeeGrid
2. GridConfigServlet → GridConfigService.getGridConfig()
3. GridConfigService → JSON 설정 반환
4. GridConfigServlet → JSON 응답 생성
5. 브라우저 ← GridConfig JSON
6. kendo-json-ui.js → JSON 파싱 및 Grid 생성
7. Grid 렌더링 완료
```

### 2. 데이터 로딩 흐름

```
1. Grid 초기화 → DataSource 요청
2. 브라우저 → GET /api/data/employees?page=1&pageSize=10
3. DataServlet → DataService.getData()
4. DataService → 페이징 처리 및 데이터 반환
5. DataServlet → JSON 응답 (data, total 포함)
6. 브라우저 ← 데이터 JSON
7. Grid에 데이터 표시
```

### 3. CRUD 작업 흐름

#### Create (생성)
```
1. Grid → "추가" 버튼 클릭
2. 편집 폼 표시 (JSON 설정 기반)
3. 데이터 입력 → "저장" 클릭
4. 브라우저 → POST /api/data/employees
5. DataServlet → DataService.createData()
6. 새 데이터 생성 및 ID 할당
7. 브라우저 ← 생성된 데이터 반환
8. Grid 자동 새로고침
```

#### Update (수정)
```
1. Grid → "편집" 버튼 클릭
2. 편집 폼 표시 (기존 데이터 로드)
3. 데이터 수정 → "저장" 클릭
4. 브라우저 → PUT /api/data/employees
5. DataServlet → DataService.updateData()
6. 데이터 업데이트
7. 브라우저 ← 업데이트된 데이터 반환
8. Grid 자동 새로고침
```

#### Delete (삭제)
```
1. Grid → "삭제" 버튼 클릭
2. 확인 대화상자 표시
3. 확인 → DELETE 요청
4. 브라우저 → DELETE /api/data/employees/5
5. DataServlet → DataService.deleteData()
6. 데이터 삭제
7. 브라우저 ← 삭제 확인 응답
8. Grid 자동 새로고침
```

## 컴포넌트 설명

### 백엔드

#### Model Layer (모델 계층)
- **GridConfig**: Grid의 모든 설정을 담는 모델
  - columns: 컬럼 정의
  - dataSource: 데이터소스 설정
  - options: Grid 옵션 (페이징, 정렬 등)
  - editorForm: 편집 폼 정의

- **ChartConfig**: Chart 설정 모델
  - series: 시리즈 정의
  - dataSource: 데이터소스
  - axes: 축 설정
  - legend: 범례 설정

- **FormConfig**: Form 설정 모델
  - fields: 필드 정의
  - validation: 유효성 검사 규칙
  - options: 폼 옵션

- **ApiResponse**: 표준 API 응답 모델
  - success: 성공 여부
  - message: 메시지
  - data: 실제 데이터
  - total: 전체 개수 (페이징용)

#### Servlet Layer (서블릿 계층)
- **BaseServlet**: 모든 서블릿의 기본 클래스
  - JSON 응답 처리
  - CORS 헤더 설정
  - 공통 에러 처리

- **GridConfigServlet**: Grid 설정 관리
- **ChartConfigServlet**: Chart 설정 관리
- **FormConfigServlet**: Form 설정 관리
- **DataServlet**: 실제 데이터 CRUD 처리

#### Service Layer (서비스 계층)
- **GridConfigService**: Grid 설정 비즈니스 로직
- **ChartConfigService**: Chart 설정 비즈니스 로직
- **FormConfigService**: Form 설정 비즈니스 로직
- **DataService**: 데이터 관리 비즈니스 로직

### 프론트엔드

#### kendo-json-ui.js
JSON 설정을 Kendo UI 컴포넌트로 변환하는 핵심 라이브러리

**주요 함수:**
- `createGrid(containerId, configUrl)`: Grid 생성
- `createChart(containerId, configUrl)`: Chart 생성
- `createForm(containerId, configUrl)`: Form 생성
- `createDataSource(config)`: DataSource 생성
- `createFormTemplate(formConfig)`: 폼 템플릿 생성
- `createFieldEditor(fieldConfig)`: 필드 편집기 생성

## JSON 스키마 상세

### GridConfig 스키마

```json
{
  "id": "string",              // Grid 고유 ID
  "title": "string",           // Grid 제목
  "columns": [                 // 컬럼 정의
    {
      "field": "string",       // 필드명
      "title": "string",       // 컬럼 제목
      "type": "string",        // 데이터 타입
      "width": number,         // 너비
      "format": "string",      // 포맷
      "editable": boolean      // 편집 가능 여부
    }
  ],
  "dataSource": {             // 데이터소스 설정
    "transport": {            // 통신 설정
      "read": {
        "url": "string",
        "type": "GET|POST",
        "dataType": "json"
      },
      "create": {...},
      "update": {...},
      "destroy": {...}
    },
    "schema": {               // 스키마 정의
      "data": "string",       // 데이터 경로
      "total": "string",      // 전체 개수 경로
      "model": {              // 모델 정의
        "id": "string",       // ID 필드
        "fields": {...}       // 필드 정의
      }
    },
    "pageSize": number        // 페이지 크기
  },
  "options": {                // Grid 옵션
    "pageable": boolean,
    "sortable": boolean,
    "filterable": boolean,
    "editable": boolean,
    "editMode": "inline|popup|incell",
    "height": "string"
  },
  "editorForm": {...}         // 편집 폼 정의
}
```

### ChartConfig 스키마

```json
{
  "id": "string",
  "title": "string",
  "chartType": "column|line|pie|bar|area",
  "series": [
    {
      "type": "string",
      "field": "string",
      "categoryField": "string",
      "name": "string",
      "color": "string",
      "labels": {
        "visible": boolean,
        "format": "string",
        "template": "string"
      }
    }
  ],
  "dataSource": {
    "url": "string",
    "data": []
  },
  "categoryAxis": {
    "title": "string",
    "categories": [],
    "labels": {...}
  },
  "valueAxis": {
    "title": "string",
    "labels": {...}
  },
  "legend": {
    "visible": boolean,
    "position": "top|bottom|left|right"
  }
}
```

### FormConfig 스키마

```json
{
  "id": "string",
  "title": "string",
  "fields": [
    {
      "name": "string",
      "label": "string",
      "type": "text|number|date|dropdown|checkbox|textarea",
      "required": boolean,
      "placeholder": "string",
      "validationRules": {
        "minLength": number,
        "maxLength": number,
        "min": number,
        "max": number,
        "pattern": "string",
        "message": "string"
      },
      "editor": {
        "widget": "dropdownlist|datepicker|numerictextbox",
        "options": [],
        "format": "string",
        "step": number,
        "decimals": number
      }
    }
  ],
  "options": {
    "orientation": "vertical|horizontal",
    "submitUrl": "string",
    "submitMethod": "POST|PUT",
    "buttons": ["submit", "clear"]
  },
  "validation": {
    "validateOnBlur": boolean
  }
}
```

## 확장 방법

### 1. 새로운 Grid 추가

GridConfigService.java에 새로운 설정 추가:

```java
private static void initializeSampleConfigs() {
    GridConfig newGrid = new GridConfig();
    newGrid.setId("newGrid");
    // ... 설정
    configs.put("newGrid", newGrid);
}
```

HTML에서 사용:
```javascript
KendoJsonUI.createGrid('container', 'api/grid/config/newGrid');
```

### 2. 새로운 Chart 추가

ChartConfigService.java에 새로운 차트 설정 추가

### 3. 새로운 데이터 타입 추가

DataService.java의 initializeSampleData()에 데이터 추가

### 4. 커스텀 위젯 추가

kendo-json-ui.js의 createFieldEditor() 함수에 새로운 위젯 케이스 추가

## 보안 고려사항

1. **입력 검증**: 모든 사용자 입력은 서버에서 검증
2. **SQL 인젝션 방지**: 현재는 메모리 기반이지만, DB 연동시 PreparedStatement 사용
3. **XSS 방지**: Kendo UI가 자동으로 이스케이프 처리
4. **CSRF 방지**: 프로덕션에서는 CSRF 토큰 추가 필요
5. **인증/인가**: 프로덕션에서는 적절한 인증 메커니즘 추가

## 성능 최적화

1. **페이징**: 서버 사이드 페이징으로 대용량 데이터 처리
2. **캐싱**: 설정 데이터는 메모리에 캐시
3. **지연 로딩**: 차트는 탭이 활성화될 때만 로드
4. **압축**: 프로덕션에서는 JS/CSS 압축 사용
5. **CDN**: Kendo UI는 CDN에서 로드

## 데이터베이스 연동

현재는 메모리 기반이지만, 실제 환경에서는 다음과 같이 DB 연동:

1. **JPA/Hibernate** 사용
2. **Connection Pool** 설정 (HikariCP 등)
3. **DAO 패턴** 구현
4. **트랜잭션 관리**

## 테스트

### 단위 테스트
- Service 레이어 테스트 (JUnit)
- 모델 직렬화/역직렬화 테스트

### 통합 테스트
- 서블릿 테스트 (MockMvc)
- API 엔드포인트 테스트

### E2E 테스트
- Selenium을 이용한 UI 테스트

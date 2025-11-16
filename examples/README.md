# Kendo UI JSON 통합 시스템 - Standalone 예제

이 폴더에는 서버 없이 **로컬에서 바로 실행 가능한** HTML 예제 파일들이 포함되어 있습니다.

## 🚀 빠른 시작

### 실행 방법

1. 이 폴더의 HTML 파일을 **더블클릭**하거나
2. 웹 브라우저에서 **파일 열기**로 실행
3. 인터넷 연결 필요 (Kendo UI CDN 사용)

**그게 전부입니다!** 서버 설치나 빌드 과정이 필요 없습니다.

## 📁 파일 목록

### 1. standalone-grid-example.html
**Grid 전용 예제**
- JSON 설정으로 Grid 생성
- CRUD 작업 (생성, 수정, 삭제) 가능
- 페이징, 정렬, 필터링 지원
- 15명의 직원 샘플 데이터 포함

```bash
# 실행 방법
더블클릭 또는 브라우저에서 파일 열기
```

**주요 기능:**
- ✅ 데이터 추가/수정/삭제
- ✅ 페이징 (10개씩 표시)
- ✅ 정렬 (컬럼 클릭)
- ✅ 필터링
- ✅ JSON 설정 보기
- ✅ 현재 데이터 보기

---

### 2. standalone-chart-example.html
**Chart 전용 예제**
- 4가지 차트 타입 (Column, Pie, Line, Bar)
- JSON 설정으로 차트 생성
- 각 차트별 샘플 데이터 포함

```bash
# 실행 방법
더블클릭 또는 브라우저에서 파일 열기
```

**포함된 차트:**
- 📊 월별 매출 현황 (Column Chart)
- 🥧 부서별 인원 (Pie Chart)
- 📈 분기별 성장률 (Line Chart)
- 📊 제품별 판매량 (Bar Chart)

---

### 3. standalone-form-example.html
**Form 전용 예제**
- JSON 설정으로 폼 생성
- 자동 유효성 검사
- 다양한 입력 위젯 (텍스트, 숫자, 날짜, 드롭다운, 체크박스, 텍스트영역)

```bash
# 실행 방법
더블클릭 또는 브라우저에서 파일 열기
```

**주요 기능:**
- ✅ 다양한 입력 필드
- ✅ 자동 유효성 검사
- ✅ 샘플 데이터 로드 버튼
- ✅ 제출된 데이터 표시
- ✅ JSON 설정 보기

---

### 4. standalone-complete-demo.html ⭐ 추천
**완전한 통합 예제**
- Grid, Chart, Form을 모두 포함
- 탭 인터페이스로 구성
- 통합 대시보드 포함

```bash
# 실행 방법
더블클릭 또는 브라우저에서 파일 열기
```

**포함 탭:**
1. 📖 개요 - 시스템 소개 및 기능 설명
2. 📊 Grid 데모 - 직원 관리 Grid
3. 📈 Chart 데모 - 다양한 차트
4. 📝 Form 데모 - 사용자 등록 폼
5. 🎨 통합 대시보드 - 모든 컴포넌트 통합

---

### 5. kendo-json-ui-standalone.js
**Standalone 라이브러리**
- Grid, Chart, Form 생성 함수
- 샘플 데이터 포함
- `standalone-complete-demo.html`에서 사용

## 💡 사용 팁

### Grid 예제 활용
```javascript
// Grid에서 할 수 있는 작업:
1. "추가" 버튼 클릭 → 새 직원 추가
2. "편집" 버튼 클릭 → 기존 데이터 수정
3. "삭제" 버튼 클릭 → 데이터 삭제
4. 컬럼 헤더 클릭 → 정렬
5. 필터 아이콘 클릭 → 데이터 필터링
```

### Chart 예제 활용
```javascript
// Chart에서 확인할 수 있는 것:
1. 마우스 오버 → 툴팁 표시
2. 범례 클릭 → 시리즈 표시/숨김
3. 다양한 차트 타입 비교
```

### Form 예제 활용
```javascript
// Form에서 시도해볼 것:
1. "샘플 데이터 로드" 버튼 클릭 → 자동 입력
2. 잘못된 이메일 입력 → 유효성 검사 확인
3. 필수 필드 비우기 → 에러 메시지 확인
4. "제출" 버튼 클릭 → 결과 확인
```

## 🔧 JSON 설정 예시

### Grid 설정
```json
{
  "id": "employeeGrid",
  "title": "직원 관리",
  "columns": [
    {
      "field": "name",
      "title": "이름",
      "width": 150,
      "editable": true
    }
  ],
  "options": {
    "pageable": true,
    "sortable": true,
    "filterable": true,
    "editable": true
  }
}
```

### Chart 설정
```json
{
  "id": "salesChart",
  "title": "월별 매출",
  "chartType": "column",
  "series": [{
    "type": "column",
    "field": "sales",
    "name": "매출"
  }]
}
```

### Form 설정
```json
{
  "id": "userForm",
  "title": "사용자 등록",
  "fields": [
    {
      "name": "username",
      "label": "사용자명",
      "type": "text",
      "required": true
    }
  ]
}
```

## 📝 주요 특징

### ✨ 서버 불필요
- 로컬 파일로 바로 실행
- 데이터는 메모리에 저장
- 새로고침하면 초기 상태로 복원

### 🎯 완전한 기능
- 실제 프로젝트와 동일한 기능
- CRUD 작업 완벽 지원
- 유효성 검사 포함

### 📱 반응형 디자인
- 모바일/태블릿에서도 작동
- 자동 레이아웃 조정

### 🌐 CDN 사용
- Kendo UI는 CDN에서 로드
- jQuery도 CDN에서 로드
- 인터넷 연결 필요

## ⚠️ 주의사항

1. **인터넷 연결 필요**: Kendo UI와 jQuery를 CDN에서 로드합니다.
2. **데이터 영구 저장 안됨**: 페이지 새로고침 시 초기 상태로 돌아갑니다.
3. **브라우저 호환성**: 최신 브라우저 사용 권장 (Chrome, Firefox, Edge, Safari)
4. **Kendo UI 라이선스**: 프로덕션 사용 시 별도 라이선스 필요

## 🎓 학습 순서 추천

1. **`standalone-complete-demo.html`** 먼저 실행
   - 전체 시스템 개요 파악
   - 각 컴포넌트 체험

2. **`standalone-grid-example.html`** 상세 학습
   - Grid CRUD 작업 연습
   - JSON 설정 구조 이해

3. **`standalone-chart-example.html`** 차트 학습
   - 다양한 차트 타입 비교
   - 데이터 시각화 방법 학습

4. **`standalone-form-example.html`** 폼 학습
   - 유효성 검사 테스트
   - 다양한 입력 위젯 확인

## 🚀 다음 단계

이 예제를 이해했다면:

1. **서버 버전 사용**: `src/main/webapp/index.html` 참조
2. **커스텀 설정 작성**: JSON 설정을 수정하여 자신만의 UI 생성
3. **실제 프로젝트 적용**: 서블릿과 통합하여 실제 애플리케이션 구축

## 💬 문의 및 피드백

- 버그 리포트: GitHub Issues
- 기능 제안: Pull Request
- 질문: README.md 참조

## 📄 라이선스

이 예제는 데모 목적으로 제공됩니다.
Kendo UI 사용 시 별도 라이선스가 필요할 수 있습니다.

---

**즐거운 코딩 되세요! 🎉**

/**
 * Kendo UI JSON 통합 라이브러리 - Standalone 버전
 * 서버 없이 로컬에서 작동하는 버전
 */
const KendoJsonUI = (function() {
    'use strict';

    let nextId = 16;

    // 설정 데이터
    const configs = {
        gridConfig: {
            "id": "employeeGrid",
            "title": "직원 관리",
            "columns": [
                {"field": "id", "title": "ID", "width": 80, "editable": false},
                {"field": "name", "title": "이름", "width": 150, "editable": true},
                {"field": "email", "title": "이메일", "width": 200, "editable": true},
                {"field": "department", "title": "부서", "width": 150, "editable": true},
                {"field": "salary", "title": "급여", "type": "number", "width": 120, "format": "{0:c}", "editable": true},
                {"field": "hireDate", "title": "입사일", "type": "date", "width": 120, "format": "{0:yyyy-MM-dd}", "editable": true}
            ],
            "options": {
                "pageable": true,
                "sortable": true,
                "filterable": true,
                "editable": true,
                "editMode": "popup",
                "height": "550px",
                "resizable": true,
                "reorderable": true
            }
        },

        salesChartConfig: {
            "id": "salesChart",
            "title": "월별 매출 현황",
            "chartType": "column",
            "series": [{"type": "column", "field": "sales", "name": "매출", "color": "#4CAF50"}],
            "categoryAxis": {"field": "month"},
            "valueAxis": {"title": "매출액 (원)", "format": "{0:c}"}
        },

        deptChartConfig: {
            "id": "deptChart",
            "title": "부서별 인원",
            "chartType": "pie",
            "series": [{"type": "pie", "field": "count", "categoryField": "department"}],
            "legend": {"position": "right"}
        },

        formConfig: {
            "id": "userRegistration",
            "title": "사용자 등록",
            "fields": [
                {
                    "name": "username",
                    "label": "사용자명",
                    "type": "text",
                    "required": true,
                    "placeholder": "사용자명을 입력하세요"
                },
                {
                    "name": "email",
                    "label": "이메일",
                    "type": "text",
                    "required": true,
                    "placeholder": "email@example.com",
                    "validationRules": {
                        "pattern": "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$"
                    }
                },
                {
                    "name": "department",
                    "label": "부서",
                    "type": "dropdown",
                    "editor": {
                        "widget": "dropdownlist",
                        "options": [
                            {"text": "개발팀", "value": "개발팀"},
                            {"text": "디자인팀", "value": "디자인팀"},
                            {"text": "마케팅팀", "value": "마케팅팀"},
                            {"text": "인사팀", "value": "인사팀"}
                        ]
                    }
                },
                {
                    "name": "bio",
                    "label": "자기소개",
                    "type": "textarea",
                    "placeholder": "자기소개를 입력하세요"
                }
            ],
            "options": {"orientation": "vertical", "buttons": ["submit", "clear"]}
        }
    };

    // Mock 데이터
    const data = {
        employeeData: [
            {"id": 1, "name": "김철수", "email": "kim@example.com", "department": "개발팀", "salary": 5500000, "hireDate": new Date("2020-01-15")},
            {"id": 2, "name": "이영희", "email": "lee@example.com", "department": "디자인팀", "salary": 4800000, "hireDate": new Date("2020-03-20")},
            {"id": 3, "name": "박민수", "email": "park@example.com", "department": "개발팀", "salary": 6000000, "hireDate": new Date("2019-05-10")},
            {"id": 4, "name": "최지은", "email": "choi@example.com", "department": "마케팅팀", "salary": 5000000, "hireDate": new Date("2021-02-01")},
            {"id": 5, "name": "정현우", "email": "jung@example.com", "department": "인사팀", "salary": 5200000, "hireDate": new Date("2020-07-15")},
            {"id": 6, "name": "강민지", "email": "kang@example.com", "department": "개발팀", "salary": 5800000, "hireDate": new Date("2019-11-20")},
            {"id": 7, "name": "윤서연", "email": "yoon@example.com", "department": "디자인팀", "salary": 4900000, "hireDate": new Date("2021-04-10")},
            {"id": 8, "name": "임동현", "email": "lim@example.com", "department": "마케팅팀", "salary": 5100000, "hireDate": new Date("2020-09-05")},
            {"id": 9, "name": "한지훈", "email": "han@example.com", "department": "개발팀", "salary": 6200000, "hireDate": new Date("2018-12-01")},
            {"id": 10, "name": "조수진", "email": "jo@example.com", "department": "인사팀", "salary": 5300000, "hireDate": new Date("2021-06-15")}
        ],

        salesData: [
            {"month": "1월", "sales": 45000000}, {"month": "2월", "sales": 52000000},
            {"month": "3월", "sales": 48000000}, {"month": "4월", "sales": 61000000},
            {"month": "5월", "sales": 58000000}, {"month": "6월", "sales": 67000000},
            {"month": "7월", "sales": 72000000}, {"month": "8월", "sales": 68000000},
            {"month": "9월", "sales": 75000000}, {"month": "10월", "sales": 81000000},
            {"month": "11월", "sales": 78000000}, {"month": "12월", "sales": 95000000}
        ],

        deptData: [
            {"department": "개발팀", "count": 4},
            {"department": "디자인팀", "count": 2},
            {"department": "마케팅팀", "count": 2},
            {"department": "인사팀", "count": 2}
        ]
    };

    // Grid 생성
    function createGrid(containerId, config, gridData) {
        const container = $('#' + containerId);
        const columns = config.columns.map(col => ({
            field: col.field,
            title: col.title,
            width: col.width,
            format: col.format,
            editable: col.editable,
            editor: col.type === 'date' ? function(container, options) {
                $('<input name="' + options.field + '"/>').appendTo(container)
                    .kendoDatePicker({format: "yyyy-MM-dd"});
            } : col.field === 'department' ? function(container, options) {
                $('<input name="' + options.field + '"/>').appendTo(container)
                    .kendoDropDownList({dataSource: ["개발팀", "디자인팀", "마케팅팀", "인사팀"]});
            } : undefined
        }));

        if (config.options.editable) {
            columns.push({command: ["edit", "destroy"], title: "작업", width: "200px"});
        }

        const dataSource = new kendo.data.DataSource({
            data: gridData,
            pageSize: 10,
            schema: {
                model: {
                    id: "id",
                    fields: {
                        id: {type: "number", editable: false},
                        name: {type: "string", validation: {required: true}},
                        email: {type: "string", validation: {required: true, email: true}},
                        department: {type: "string"},
                        salary: {type: "number"},
                        hireDate: {type: "date"}
                    }
                }
            }
        });

        container.kendoGrid({
            dataSource: dataSource,
            columns: columns,
            pageable: {refresh: true, pageSizes: [5, 10, 20, 50]},
            sortable: config.options.sortable,
            filterable: config.options.filterable,
            resizable: config.options.resizable,
            reorderable: config.options.reorderable,
            height: config.options.height,
            toolbar: ["create"],
            editable: config.options.editMode,
            edit: function(e) {
                if (e.model.isNew()) {
                    e.model.set("id", nextId++);
                }
            }
        });
    }

    // Chart 생성
    function createChart(containerId, config, chartData) {
        const container = $('#' + containerId);
        container.kendoChart({
            title: {text: config.title},
            dataSource: chartData,
            series: config.series.map(s => ({
                type: s.type,
                field: s.field,
                categoryField: s.categoryField,
                name: s.name,
                color: s.color,
                labels: {
                    visible: config.chartType === 'pie',
                    template: config.chartType === 'pie' ? "#= category # - #= value #명" : undefined
                }
            })),
            legend: {visible: true, position: config.legend ? config.legend.position : 'bottom'},
            categoryAxis: config.categoryAxis && config.chartType !== 'pie' ? {field: config.categoryAxis.field} : undefined,
            valueAxis: config.valueAxis ? {
                title: {text: config.valueAxis.title},
                labels: {format: config.valueAxis.format}
            } : undefined,
            tooltip: {visible: true}
        });
    }

    // Form 생성
    function createForm(containerId, config) {
        const container = $('#' + containerId);
        let html = '<form class="k-form"><h3>' + config.title + '</h3>';

        config.fields.forEach(field => {
            html += '<div class="k-form-field">';
            html += '<label class="k-form-label">' + field.label;
            if (field.required) html += '<span class="k-required">*</span>';
            html += '</label><div class="k-form-field-wrap">';

            if (field.type === 'textarea') {
                html += '<textarea id="' + field.name + '" class="k-textarea" rows="4"></textarea>';
            } else if (field.type === 'checkbox') {
                html += '<input type="checkbox" id="' + field.name + '" class="k-checkbox"/>';
            } else {
                html += '<input id="' + field.name + '" placeholder="' + (field.placeholder || '') + '"/>';
            }
            html += '</div></div>';
        });

        html += '<div class="k-form-buttons">';
        html += '<button type="submit" class="k-button k-button-solid-primary">제출</button>';
        html += '<button type="reset" class="k-button k-button-solid-base">초기화</button>';
        html += '</div></form>';

        container.html(html);

        config.fields.forEach(field => {
            const input = $('#' + field.name);
            if (field.editor && field.editor.widget === 'dropdownlist') {
                input.kendoDropDownList({
                    dataSource: field.editor.options,
                    dataTextField: 'text',
                    dataValueField: 'value',
                    optionLabel: '선택하세요'
                });
            }
        });

        container.find('form').on('submit', function(e) {
            e.preventDefault();
            const formData = {};
            config.fields.forEach(field => {
                const input = $('#' + field.name);
                const widget = input.data('kendoDropDownList');
                formData[field.name] = widget ? widget.value() :
                    (field.type === 'checkbox' ? input.is(':checked') : input.val());
            });
            kendo.alert('폼이 제출되었습니다!<br><pre>' + JSON.stringify(formData, null, 2) + '</pre>');
        });
    }

    return {
        configs: configs,
        data: data,
        createGrid: createGrid,
        createChart: createChart,
        createForm: createForm
    };
})();

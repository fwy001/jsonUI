/**
 * Kendo UI JSON 통합 라이브러리
 * JSON 설정을 기반으로 Kendo UI 컴포넌트를 동적으로 생성
 */
const KendoJsonUI = (function() {
    'use strict';

    /**
     * JSON 설정을 기반으로 Kendo Grid 생성
     */
    function createGrid(containerId, configUrl) {
        return $.ajax({
            url: configUrl,
            type: 'GET',
            dataType: 'json'
        }).done(function(response) {
            if (!response.success) {
                console.error('Failed to load grid configuration:', response.message);
                return;
            }

            const config = response.data;
            const container = $('#' + containerId);

            // 데이터소스 생성
            const dataSource = createDataSource(config.dataSource);

            // 컬럼 설정
            const columns = config.columns.map(function(col) {
                const column = {
                    field: col.field,
                    title: col.title,
                    width: col.width,
                    format: col.format,
                    template: col.template,
                    attributes: col.attributes
                };

                // 편집기 설정
                if (config.editorForm && col.editable) {
                    const formField = config.editorForm.fields.find(f => f.name === col.field);
                    if (formField && formField.editor) {
                        column.editor = createFieldEditor(formField);
                    }
                }

                return column;
            });

            // 편집 도구 컬럼 추가
            if (config.options.editable) {
                columns.push({ command: ["edit", "destroy"], title: "작업", width: "200px" });
            }

            // Grid 옵션 구성
            const gridOptions = {
                dataSource: dataSource,
                columns: columns,
                pageable: config.options.pageable ? {
                    refresh: true,
                    pageSizes: [5, 10, 20, 50],
                    buttonCount: 5
                } : false,
                sortable: config.options.sortable,
                filterable: config.options.filterable,
                groupable: config.options.groupable,
                resizable: config.options.resizable,
                reorderable: config.options.reorderable,
                scrollable: config.options.scrollable,
                height: config.options.height,
                toolbar: config.options.editable ? ["create"] : null,
                editable: config.options.editable ? {
                    mode: config.options.editMode || "popup",
                    template: config.editorForm ? createFormTemplate(config.editorForm) : null
                } : false
            };

            // Grid 생성
            container.kendoGrid(gridOptions);

            // 타이틀 추가
            if (config.title) {
                container.before('<h2>' + config.title + '</h2>');
            }

            return container.data('kendoGrid');
        }).fail(function(error) {
            console.error('Error loading grid configuration:', error);
        });
    }

    /**
     * DataSource 생성
     */
    function createDataSource(config) {
        const dsConfig = {
            type: config.type || 'json',
            pageSize: config.pageSize || 20,
            serverPaging: true,
            serverSorting: true,
            serverFiltering: true,
            schema: {
                data: config.schema.data || 'data',
                total: config.schema.total || 'total',
                model: {
                    id: config.schema.model.id,
                    fields: config.schema.model.fields
                }
            }
        };

        // Transport 설정
        if (config.transport) {
            dsConfig.transport = {
                read: config.transport.read ? {
                    url: config.transport.read.url,
                    type: config.transport.read.type || 'GET',
                    dataType: config.transport.read.dataType || 'json'
                } : null,
                create: config.transport.create ? {
                    url: config.transport.create.url,
                    type: config.transport.create.type || 'POST',
                    dataType: config.transport.create.dataType || 'json',
                    contentType: "application/json"
                } : null,
                update: config.transport.update ? {
                    url: config.transport.update.url,
                    type: config.transport.update.type || 'PUT',
                    dataType: config.transport.update.dataType || 'json',
                    contentType: "application/json"
                } : null,
                destroy: config.transport.destroy ? {
                    url: config.transport.destroy.url,
                    type: config.transport.destroy.type || 'DELETE',
                    dataType: config.transport.destroy.dataType || 'json'
                } : null,
                parameterMap: function(data, type) {
                    if (type === "create" || type === "update") {
                        return JSON.stringify(data);
                    }
                    return data;
                }
            };
        } else if (config.url) {
            dsConfig.transport = {
                read: {
                    url: config.url,
                    dataType: 'json'
                }
            };
        }

        return new kendo.data.DataSource(dsConfig);
    }

    /**
     * 폼 템플릿 생성
     */
    function createFormTemplate(formConfig) {
        let html = '<div class="k-edit-form-container">';

        formConfig.fields.forEach(function(field) {
            html += '<div class="k-edit-label">';
            html += '<label for="' + field.name + '">' + field.label;
            if (field.required) {
                html += '<span class="k-required">*</span>';
            }
            html += '</label></div>';

            html += '<div class="k-edit-field">';
            html += '<input name="' + field.name + '" ';
            html += 'data-bind="value:' + field.name + '" ';

            if (field.required) {
                html += 'required ';
            }

            if (field.validationRules) {
                if (field.validationRules.pattern) {
                    html += 'pattern="' + field.validationRules.pattern + '" ';
                }
                if (field.validationRules.message) {
                    html += 'validationMessage="' + field.validationRules.message + '" ';
                }
            }

            html += '/>';
            html += '</div>';
        });

        html += '</div>';
        return html;
    }

    /**
     * 필드 편집기 생성
     */
    function createFieldEditor(fieldConfig) {
        return function(container, options) {
            const input = $('<input name="' + options.field + '"/>');
            input.appendTo(container);

            if (fieldConfig.editor) {
                const editor = fieldConfig.editor;

                switch (editor.widget) {
                    case 'dropdownlist':
                        input.kendoDropDownList({
                            dataSource: editor.options || [],
                            dataTextField: 'text',
                            dataValueField: 'value'
                        });
                        break;

                    case 'numerictextbox':
                        input.kendoNumericTextBox({
                            format: editor.format,
                            decimals: editor.decimals,
                            step: editor.step
                        });
                        break;

                    case 'datepicker':
                        input.kendoDatePicker({
                            format: editor.format || 'yyyy-MM-dd'
                        });
                        break;

                    default:
                        // 기본 텍스트 입력
                        break;
                }
            }
        };
    }

    /**
     * JSON 설정을 기반으로 Kendo Chart 생성
     */
    function createChart(containerId, configUrl) {
        return $.ajax({
            url: configUrl,
            type: 'GET',
            dataType: 'json'
        }).done(function(response) {
            if (!response.success) {
                console.error('Failed to load chart configuration:', response.message);
                return;
            }

            const config = response.data;
            const container = $('#' + containerId);

            // 차트 옵션 구성
            const chartOptions = {
                title: {
                    text: config.title
                },
                legend: {
                    visible: config.legend ? config.legend.visible : true,
                    position: config.legend ? config.legend.position : 'bottom'
                },
                chartArea: {
                    height: config.options ? config.options.height : 400
                },
                series: config.series.map(function(s) {
                    return {
                        type: s.type,
                        field: s.field,
                        categoryField: s.categoryField,
                        name: s.name,
                        color: s.color,
                        labels: s.labels ? {
                            visible: s.labels.visible,
                            template: s.labels.template,
                            format: s.labels.format
                        } : null,
                        tooltip: s.tooltip
                    };
                }),
                categoryAxis: config.categoryAxis ? {
                    title: { text: config.categoryAxis.title },
                    categories: config.categoryAxis.categories,
                    labels: config.categoryAxis.labels
                } : null,
                valueAxis: config.valueAxis ? {
                    title: { text: config.valueAxis.title },
                    labels: config.valueAxis.labels
                } : null,
                tooltip: {
                    visible: true,
                    format: "{0}"
                }
            };

            // 데이터소스 설정
            if (config.dataSource.url) {
                $.ajax({
                    url: config.dataSource.url,
                    type: 'GET',
                    dataType: 'json'
                }).done(function(dataResponse) {
                    chartOptions.dataSource = dataResponse.data || dataResponse;
                    container.kendoChart(chartOptions);
                });
            } else if (config.dataSource.data) {
                chartOptions.dataSource = config.dataSource.data;
                container.kendoChart(chartOptions);
            }

            return container.data('kendoChart');
        }).fail(function(error) {
            console.error('Error loading chart configuration:', error);
        });
    }

    /**
     * JSON 설정을 기반으로 Kendo Form 생성
     */
    function createForm(containerId, configUrl) {
        return $.ajax({
            url: configUrl,
            type: 'GET',
            dataType: 'json'
        }).done(function(response) {
            if (!response.success) {
                console.error('Failed to load form configuration:', response.message);
                return;
            }

            const config = response.data;
            const container = $('#' + containerId);

            // 폼 HTML 생성
            let formHtml = '<form id="' + config.id + '" class="k-form">';

            if (config.title) {
                formHtml += '<h3>' + config.title + '</h3>';
            }

            config.fields.forEach(function(field) {
                formHtml += '<div class="k-form-field">';
                formHtml += '<label class="k-form-label" for="' + field.name + '">';
                formHtml += field.label;
                if (field.required) {
                    formHtml += '<span class="k-required">*</span>';
                }
                formHtml += '</label>';

                formHtml += '<div class="k-form-field-wrap">';

                switch (field.type) {
                    case 'textarea':
                        formHtml += '<textarea id="' + field.name + '" name="' + field.name + '" ';
                        formHtml += 'class="k-textarea" ';
                        if (field.placeholder) formHtml += 'placeholder="' + field.placeholder + '" ';
                        if (field.required) formHtml += 'required ';
                        formHtml += '></textarea>';
                        break;

                    case 'checkbox':
                        formHtml += '<input type="checkbox" id="' + field.name + '" name="' + field.name + '" ';
                        formHtml += 'class="k-checkbox" ';
                        if (field.required) formHtml += 'required ';
                        formHtml += '/>';
                        break;

                    default:
                        formHtml += '<input id="' + field.name + '" name="' + field.name + '" ';
                        if (field.placeholder) formHtml += 'placeholder="' + field.placeholder + '" ';
                        if (field.required) formHtml += 'required ';
                        formHtml += '/>';
                        break;
                }

                formHtml += '</div>';
                formHtml += '</div>';
            });

            // 버튼
            if (config.options && config.options.buttons) {
                formHtml += '<div class="k-form-buttons">';
                config.options.buttons.forEach(function(btn) {
                    if (btn === 'submit') {
                        formHtml += '<button type="submit" class="k-button k-button-solid-primary">제출</button>';
                    } else if (btn === 'clear') {
                        formHtml += '<button type="reset" class="k-button k-button-solid-base">초기화</button>';
                    }
                });
                formHtml += '</div>';
            }

            formHtml += '</form>';

            container.html(formHtml);

            // 위젯 초기화
            config.fields.forEach(function(field) {
                const input = $('#' + field.name);

                if (field.editor) {
                    const editor = field.editor;

                    switch (editor.widget) {
                        case 'dropdownlist':
                            input.kendoDropDownList({
                                dataSource: editor.options || [],
                                dataTextField: 'text',
                                dataValueField: 'value',
                                optionLabel: '선택하세요'
                            });
                            break;

                        case 'numerictextbox':
                            input.kendoNumericTextBox({
                                format: editor.format,
                                decimals: editor.decimals,
                                step: editor.step
                            });
                            break;

                        case 'datepicker':
                            input.kendoDatePicker({
                                format: editor.format || 'yyyy-MM-dd'
                            });
                            break;
                    }
                } else {
                    // 기본 타입 처리
                    switch (field.type) {
                        case 'number':
                            input.kendoNumericTextBox();
                            break;
                        case 'date':
                            input.kendoDatePicker({
                                format: 'yyyy-MM-dd'
                            });
                            break;
                    }
                }

                // 유효성 검사 규칙 추가
                if (field.validationRules) {
                    if (field.validationRules.pattern) {
                        input.attr('pattern', field.validationRules.pattern);
                    }
                }
            });

            // Kendo Validator 초기화
            const validator = container.find('form').kendoValidator({
                validateOnBlur: config.validation ? config.validation.validateOnBlur : true
            }).data('kendoValidator');

            // 폼 제출 처리
            if (config.options && config.options.submitUrl) {
                container.find('form').on('submit', function(e) {
                    e.preventDefault();

                    if (validator.validate()) {
                        const formData = {};
                        config.fields.forEach(function(field) {
                            const input = $('#' + field.name);
                            const widget = input.data('kendoNumericTextBox') ||
                                         input.data('kendoDatePicker') ||
                                         input.data('kendoDropDownList');

                            if (widget) {
                                formData[field.name] = widget.value();
                            } else if (field.type === 'checkbox') {
                                formData[field.name] = input.is(':checked');
                            } else {
                                formData[field.name] = input.val();
                            }
                        });

                        $.ajax({
                            url: config.options.submitUrl,
                            type: config.options.submitMethod || 'POST',
                            contentType: 'application/json',
                            data: JSON.stringify(formData),
                            success: function(response) {
                                kendo.alert('폼이 성공적으로 제출되었습니다.');
                                if (config.options.buttons.includes('clear')) {
                                    container.find('form')[0].reset();
                                }
                            },
                            error: function(error) {
                                kendo.alert('폼 제출 중 오류가 발생했습니다.');
                            }
                        });
                    }
                });
            }

            return validator;
        }).fail(function(error) {
            console.error('Error loading form configuration:', error);
        });
    }

    // Public API
    return {
        createGrid: createGrid,
        createChart: createChart,
        createForm: createForm
    };
})();

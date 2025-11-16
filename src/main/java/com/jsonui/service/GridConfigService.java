package com.jsonui.service;

import com.jsonui.model.FormConfig;
import com.jsonui.model.GridConfig;

import java.util.*;

/**
 * Grid 설정 관리 서비스
 */
public class GridConfigService {
    private static final Map<String, GridConfig> configs = new HashMap<>();

    static {
        // 샘플 Grid 설정 초기화
        initializeSampleConfigs();
    }

    private static void initializeSampleConfigs() {
        // 직원 Grid 설정
        GridConfig employeeGrid = new GridConfig();
        employeeGrid.setId("employeeGrid");
        employeeGrid.setTitle("직원 관리");

        // 컬럼 설정
        List<GridConfig.ColumnConfig> columns = new ArrayList<>();

        GridConfig.ColumnConfig idCol = new GridConfig.ColumnConfig();
        idCol.setField("id");
        idCol.setTitle("ID");
        idCol.setWidth(80);
        idCol.setEditable(false);
        columns.add(idCol);

        GridConfig.ColumnConfig nameCol = new GridConfig.ColumnConfig();
        nameCol.setField("name");
        nameCol.setTitle("이름");
        nameCol.setWidth(150);
        nameCol.setEditable(true);
        columns.add(nameCol);

        GridConfig.ColumnConfig emailCol = new GridConfig.ColumnConfig();
        emailCol.setField("email");
        emailCol.setTitle("이메일");
        emailCol.setWidth(200);
        emailCol.setEditable(true);
        columns.add(emailCol);

        GridConfig.ColumnConfig deptCol = new GridConfig.ColumnConfig();
        deptCol.setField("department");
        deptCol.setTitle("부서");
        deptCol.setWidth(150);
        deptCol.setEditable(true);
        columns.add(deptCol);

        GridConfig.ColumnConfig salaryCol = new GridConfig.ColumnConfig();
        salaryCol.setField("salary");
        salaryCol.setTitle("급여");
        salaryCol.setType("number");
        salaryCol.setWidth(120);
        salaryCol.setFormat("{0:c}");
        salaryCol.setEditable(true);
        columns.add(salaryCol);

        GridConfig.ColumnConfig hireDateCol = new GridConfig.ColumnConfig();
        hireDateCol.setField("hireDate");
        hireDateCol.setTitle("입사일");
        hireDateCol.setType("date");
        hireDateCol.setWidth(120);
        hireDateCol.setFormat("{0:yyyy-MM-dd}");
        hireDateCol.setEditable(true);
        columns.add(hireDateCol);

        employeeGrid.setColumns(columns);

        // DataSource 설정
        GridConfig.DataSourceConfig dataSource = new GridConfig.DataSourceConfig();
        dataSource.setType("json");
        dataSource.setPageSize(10);

        GridConfig.DataSourceConfig.TransportConfig transport = new GridConfig.DataSourceConfig.TransportConfig();

        GridConfig.DataSourceConfig.TransportConfig.EndpointConfig read = new GridConfig.DataSourceConfig.TransportConfig.EndpointConfig();
        read.setUrl("api/data/employees");
        read.setType("GET");
        read.setDataType("json");
        transport.setRead(read);

        GridConfig.DataSourceConfig.TransportConfig.EndpointConfig create = new GridConfig.DataSourceConfig.TransportConfig.EndpointConfig();
        create.setUrl("api/data/employees");
        create.setType("POST");
        create.setDataType("json");
        transport.setCreate(create);

        GridConfig.DataSourceConfig.TransportConfig.EndpointConfig update = new GridConfig.DataSourceConfig.TransportConfig.EndpointConfig();
        update.setUrl("api/data/employees");
        update.setType("PUT");
        update.setDataType("json");
        transport.setUpdate(update);

        GridConfig.DataSourceConfig.TransportConfig.EndpointConfig destroy = new GridConfig.DataSourceConfig.TransportConfig.EndpointConfig();
        destroy.setUrl("api/data/employees");
        destroy.setType("DELETE");
        destroy.setDataType("json");
        transport.setDestroy(destroy);

        dataSource.setTransport(transport);

        GridConfig.DataSourceConfig.SchemaConfig schema = new GridConfig.DataSourceConfig.SchemaConfig();
        schema.setData("data");
        schema.setTotal("total");

        GridConfig.DataSourceConfig.SchemaConfig.ModelConfig model = new GridConfig.DataSourceConfig.SchemaConfig.ModelConfig();
        model.setId("id");

        Map<String, GridConfig.DataSourceConfig.SchemaConfig.ModelConfig.FieldConfig> fields = new HashMap<>();

        GridConfig.DataSourceConfig.SchemaConfig.ModelConfig.FieldConfig idField = new GridConfig.DataSourceConfig.SchemaConfig.ModelConfig.FieldConfig();
        idField.setType("number");
        idField.setEditable(false);
        fields.put("id", idField);

        GridConfig.DataSourceConfig.SchemaConfig.ModelConfig.FieldConfig nameField = new GridConfig.DataSourceConfig.SchemaConfig.ModelConfig.FieldConfig();
        nameField.setType("string");
        fields.put("name", nameField);

        GridConfig.DataSourceConfig.SchemaConfig.ModelConfig.FieldConfig emailField = new GridConfig.DataSourceConfig.SchemaConfig.ModelConfig.FieldConfig();
        emailField.setType("string");
        fields.put("email", emailField);

        GridConfig.DataSourceConfig.SchemaConfig.ModelConfig.FieldConfig salaryField = new GridConfig.DataSourceConfig.SchemaConfig.ModelConfig.FieldConfig();
        salaryField.setType("number");
        fields.put("salary", salaryField);

        GridConfig.DataSourceConfig.SchemaConfig.ModelConfig.FieldConfig hireDateField = new GridConfig.DataSourceConfig.SchemaConfig.ModelConfig.FieldConfig();
        hireDateField.setType("date");
        fields.put("hireDate", hireDateField);

        model.setFields(fields);
        schema.setModel(model);
        dataSource.setSchema(schema);

        employeeGrid.setDataSource(dataSource);

        // Grid 옵션
        GridConfig.GridOptions options = new GridConfig.GridOptions();
        options.setPageable(true);
        options.setSortable(true);
        options.setFilterable(true);
        options.setEditable(true);
        options.setEditMode("popup");
        options.setHeight("550px");
        options.setResizable(true);
        options.setReorderable(true);

        employeeGrid.setOptions(options);

        // 편집 폼 설정
        FormConfig editorForm = createEmployeeEditorForm();
        employeeGrid.setEditorForm(editorForm);

        configs.put("employeeGrid", employeeGrid);
    }

    private static FormConfig createEmployeeEditorForm() {
        FormConfig form = new FormConfig();
        form.setId("employeeForm");
        form.setTitle("직원 정보");

        List<FormConfig.FormFieldConfig> fields = new ArrayList<>();

        // 이름 필드
        FormConfig.FormFieldConfig nameField = new FormConfig.FormFieldConfig();
        nameField.setName("name");
        nameField.setLabel("이름");
        nameField.setType("text");
        nameField.setRequired(true);
        nameField.setPlaceholder("이름을 입력하세요");
        fields.add(nameField);

        // 이메일 필드
        FormConfig.FormFieldConfig emailField = new FormConfig.FormFieldConfig();
        emailField.setName("email");
        emailField.setLabel("이메일");
        emailField.setType("text");
        emailField.setRequired(true);
        FormConfig.FormFieldConfig.ValidationRules emailValidation = new FormConfig.FormFieldConfig.ValidationRules();
        emailValidation.setPattern("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$");
        emailValidation.setMessage("올바른 이메일 주소를 입력하세요");
        emailField.setValidationRules(emailValidation);
        fields.add(emailField);

        // 부서 필드
        FormConfig.FormFieldConfig deptField = new FormConfig.FormFieldConfig();
        deptField.setName("department");
        deptField.setLabel("부서");
        deptField.setType("dropdown");
        FormConfig.FormFieldConfig.EditorConfig deptEditor = new FormConfig.FormFieldConfig.EditorConfig();
        deptEditor.setWidget("dropdownlist");
        List<FormConfig.FormFieldConfig.EditorConfig.OptionConfig> deptOptions = new ArrayList<>();
        deptOptions.add(createOption("개발팀", "개발팀"));
        deptOptions.add(createOption("디자인팀", "디자인팀"));
        deptOptions.add(createOption("마케팅팀", "마케팅팀"));
        deptOptions.add(createOption("인사팀", "인사팀"));
        deptEditor.setOptions(deptOptions);
        deptField.setEditor(deptEditor);
        fields.add(deptField);

        // 급여 필드
        FormConfig.FormFieldConfig salaryField = new FormConfig.FormFieldConfig();
        salaryField.setName("salary");
        salaryField.setLabel("급여");
        salaryField.setType("number");
        FormConfig.FormFieldConfig.EditorConfig salaryEditor = new FormConfig.FormFieldConfig.EditorConfig();
        salaryEditor.setWidget("numerictextbox");
        salaryEditor.setFormat("c");
        salaryEditor.setDecimals(0);
        salaryField.setEditor(salaryEditor);
        fields.add(salaryField);

        // 입사일 필드
        FormConfig.FormFieldConfig hireDateField = new FormConfig.FormFieldConfig();
        hireDateField.setName("hireDate");
        hireDateField.setLabel("입사일");
        hireDateField.setType("date");
        FormConfig.FormFieldConfig.EditorConfig dateEditor = new FormConfig.FormFieldConfig.EditorConfig();
        dateEditor.setWidget("datepicker");
        dateEditor.setFormat("yyyy-MM-dd");
        hireDateField.setEditor(dateEditor);
        fields.add(hireDateField);

        form.setFields(fields);

        FormConfig.FormOptions formOptions = new FormConfig.FormOptions();
        formOptions.setOrientation("vertical");
        form.setOptions(formOptions);

        return form;
    }

    private static FormConfig.FormFieldConfig.EditorConfig.OptionConfig createOption(String text, String value) {
        FormConfig.FormFieldConfig.EditorConfig.OptionConfig option = new FormConfig.FormFieldConfig.EditorConfig.OptionConfig();
        option.setText(text);
        option.setValue(value);
        return option;
    }

    public List<GridConfig> getAllGridConfigs() {
        return new ArrayList<>(configs.values());
    }

    public GridConfig getGridConfig(String id) {
        return configs.get(id);
    }

    public GridConfig saveGridConfig(GridConfig config) {
        if (config.getId() == null || config.getId().isEmpty()) {
            config.setId("grid_" + System.currentTimeMillis());
        }
        configs.put(config.getId(), config);
        return config;
    }

    public void deleteGridConfig(String id) {
        configs.remove(id);
    }
}

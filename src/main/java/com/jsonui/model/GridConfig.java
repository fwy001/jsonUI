package com.jsonui.model;

import java.util.List;
import java.util.Map;

/**
 * Kendo Grid 설정을 JSON으로 정의하기 위한 모델
 */
public class GridConfig {
    private String id;
    private String title;
    private List<ColumnConfig> columns;
    private DataSourceConfig dataSource;
    private GridOptions options;
    private FormConfig editorForm;  // 그리드 편집 폼 정의

    public static class ColumnConfig {
        private String field;
        private String title;
        private String type;  // string, number, date, boolean
        private Integer width;
        private String format;
        private Boolean editable;
        private String template;
        private Map<String, Object> attributes;

        // Getters and Setters
        public String getField() { return field; }
        public void setField(String field) { this.field = field; }

        public String getTitle() { return title; }
        public void setTitle(String title) { this.title = title; }

        public String getType() { return type; }
        public void setType(String type) { this.type = type; }

        public Integer getWidth() { return width; }
        public void setWidth(Integer width) { this.width = width; }

        public String getFormat() { return format; }
        public void setFormat(String format) { this.format = format; }

        public Boolean getEditable() { return editable; }
        public void setEditable(Boolean editable) { this.editable = editable; }

        public String getTemplate() { return template; }
        public void setTemplate(String template) { this.template = template; }

        public Map<String, Object> getAttributes() { return attributes; }
        public void setAttributes(Map<String, Object> attributes) { this.attributes = attributes; }
    }

    public static class DataSourceConfig {
        private String url;
        private String type;  // json, odata, etc.
        private TransportConfig transport;
        private SchemaConfig schema;
        private Integer pageSize;
        private SortConfig sort;

        public static class TransportConfig {
            private EndpointConfig read;
            private EndpointConfig create;
            private EndpointConfig update;
            private EndpointConfig destroy;

            public static class EndpointConfig {
                private String url;
                private String type;  // GET, POST, PUT, DELETE
                private String dataType;

                public String getUrl() { return url; }
                public void setUrl(String url) { this.url = url; }

                public String getType() { return type; }
                public void setType(String type) { this.type = type; }

                public String getDataType() { return dataType; }
                public void setDataType(String dataType) { this.dataType = dataType; }
            }

            public EndpointConfig getRead() { return read; }
            public void setRead(EndpointConfig read) { this.read = read; }

            public EndpointConfig getCreate() { return create; }
            public void setCreate(EndpointConfig create) { this.create = create; }

            public EndpointConfig getUpdate() { return update; }
            public void setUpdate(EndpointConfig update) { this.update = update; }

            public EndpointConfig getDestroy() { return destroy; }
            public void setDestroy(EndpointConfig destroy) { this.destroy = destroy; }
        }

        public static class SchemaConfig {
            private String data;
            private String total;
            private ModelConfig model;

            public static class ModelConfig {
                private String id;
                private Map<String, FieldConfig> fields;

                public static class FieldConfig {
                    private String type;
                    private Boolean editable;
                    private Boolean nullable;
                    private Object defaultValue;

                    public String getType() { return type; }
                    public void setType(String type) { this.type = type; }

                    public Boolean getEditable() { return editable; }
                    public void setEditable(Boolean editable) { this.editable = editable; }

                    public Boolean getNullable() { return nullable; }
                    public void setNullable(Boolean nullable) { this.nullable = nullable; }

                    public Object getDefaultValue() { return defaultValue; }
                    public void setDefaultValue(Object defaultValue) { this.defaultValue = defaultValue; }
                }

                public String getId() { return id; }
                public void setId(String id) { this.id = id; }

                public Map<String, FieldConfig> getFields() { return fields; }
                public void setFields(Map<String, FieldConfig> fields) { this.fields = fields; }
            }

            public String getData() { return data; }
            public void setData(String data) { this.data = data; }

            public String getTotal() { return total; }
            public void setTotal(String total) { this.total = total; }

            public ModelConfig getModel() { return model; }
            public void setModel(ModelConfig model) { this.model = model; }
        }

        public static class SortConfig {
            private String field;
            private String dir;  // asc or desc

            public String getField() { return field; }
            public void setField(String field) { this.field = field; }

            public String getDir() { return dir; }
            public void setDir(String dir) { this.dir = dir; }
        }

        public String getUrl() { return url; }
        public void setUrl(String url) { this.url = url; }

        public String getType() { return type; }
        public void setType(String type) { this.type = type; }

        public TransportConfig getTransport() { return transport; }
        public void setTransport(TransportConfig transport) { this.transport = transport; }

        public SchemaConfig getSchema() { return schema; }
        public void setSchema(SchemaConfig schema) { this.schema = schema; }

        public Integer getPageSize() { return pageSize; }
        public void setPageSize(Integer pageSize) { this.pageSize = pageSize; }

        public SortConfig getSort() { return sort; }
        public void setSort(SortConfig sort) { this.sort = sort; }
    }

    public static class GridOptions {
        private Boolean pageable;
        private Boolean sortable;
        private Boolean filterable;
        private Boolean groupable;
        private Boolean editable;
        private String editMode;  // inline, popup, incell
        private Boolean resizable;
        private Boolean reorderable;
        private String height;
        private Boolean scrollable;

        public Boolean getPageable() { return pageable; }
        public void setPageable(Boolean pageable) { this.pageable = pageable; }

        public Boolean getSortable() { return sortable; }
        public void setSortable(Boolean sortable) { this.sortable = sortable; }

        public Boolean getFilterable() { return filterable; }
        public void setFilterable(Boolean filterable) { this.filterable = filterable; }

        public Boolean getGroupable() { return groupable; }
        public void setGroupable(Boolean groupable) { this.groupable = groupable; }

        public Boolean getEditable() { return editable; }
        public void setEditable(Boolean editable) { this.editable = editable; }

        public String getEditMode() { return editMode; }
        public void setEditMode(String editMode) { this.editMode = editMode; }

        public Boolean getResizable() { return resizable; }
        public void setResizable(Boolean resizable) { this.resizable = resizable; }

        public Boolean getReorderable() { return reorderable; }
        public void setReorderable(Boolean reorderable) { this.reorderable = reorderable; }

        public String getHeight() { return height; }
        public void setHeight(String height) { this.height = height; }

        public Boolean getScrollable() { return scrollable; }
        public void setScrollable(Boolean scrollable) { this.scrollable = scrollable; }
    }

    // Main GridConfig Getters and Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public List<ColumnConfig> getColumns() { return columns; }
    public void setColumns(List<ColumnConfig> columns) { this.columns = columns; }

    public DataSourceConfig getDataSource() { return dataSource; }
    public void setDataSource(DataSourceConfig dataSource) { this.dataSource = dataSource; }

    public GridOptions getOptions() { return options; }
    public void setOptions(GridOptions options) { this.options = options; }

    public FormConfig getEditorForm() { return editorForm; }
    public void setEditorForm(FormConfig editorForm) { this.editorForm = editorForm; }
}

package com.jsonui.model;

import java.util.List;
import java.util.Map;

/**
 * Kendo Chart 설정을 JSON으로 정의하기 위한 모델
 */
public class ChartConfig {
    private String id;
    private String title;
    private String chartType;  // line, bar, column, pie, area, scatter, etc.
    private List<SeriesConfig> series;
    private DataSourceConfig dataSource;
    private ChartOptions options;
    private AxisConfig categoryAxis;
    private AxisConfig valueAxis;
    private LegendConfig legend;

    public static class SeriesConfig {
        private String type;
        private String field;
        private String categoryField;
        private String name;
        private String color;
        private LabelsConfig labels;
        private Map<String, Object> tooltip;

        public static class LabelsConfig {
            private Boolean visible;
            private String template;
            private String format;

            public Boolean getVisible() { return visible; }
            public void setVisible(Boolean visible) { this.visible = visible; }

            public String getTemplate() { return template; }
            public void setTemplate(String template) { this.template = template; }

            public String getFormat() { return format; }
            public void setFormat(String format) { this.format = format; }
        }

        public String getType() { return type; }
        public void setType(String type) { this.type = type; }

        public String getField() { return field; }
        public void setField(String field) { this.field = field; }

        public String getCategoryField() { return categoryField; }
        public void setCategoryField(String categoryField) { this.categoryField = categoryField; }

        public String getName() { return name; }
        public void setName(String name) { this.name = name; }

        public String getColor() { return color; }
        public void setColor(String color) { this.color = color; }

        public LabelsConfig getLabels() { return labels; }
        public void setLabels(LabelsConfig labels) { this.labels = labels; }

        public Map<String, Object> getTooltip() { return tooltip; }
        public void setTooltip(Map<String, Object> tooltip) { this.tooltip = tooltip; }
    }

    public static class DataSourceConfig {
        private String url;
        private String type;
        private List<Map<String, Object>> data;  // 인라인 데이터용

        public String getUrl() { return url; }
        public void setUrl(String url) { this.url = url; }

        public String getType() { return type; }
        public void setType(String type) { this.type = type; }

        public List<Map<String, Object>> getData() { return data; }
        public void setData(List<Map<String, Object>> data) { this.data = data; }
    }

    public static class AxisConfig {
        private String title;
        private List<String> categories;
        private LabelsConfig labels;
        private LineConfig line;
        private Boolean visible;

        public static class LabelsConfig {
            private String format;
            private Integer rotation;
            private String template;

            public String getFormat() { return format; }
            public void setFormat(String format) { this.format = format; }

            public Integer getRotation() { return rotation; }
            public void setRotation(Integer rotation) { this.rotation = rotation; }

            public String getTemplate() { return template; }
            public void setTemplate(String template) { this.template = template; }
        }

        public static class LineConfig {
            private Boolean visible;
            private String color;
            private Integer width;

            public Boolean getVisible() { return visible; }
            public void setVisible(Boolean visible) { this.visible = visible; }

            public String getColor() { return color; }
            public void setColor(String color) { this.color = color; }

            public Integer getWidth() { return width; }
            public void setWidth(Integer width) { this.width = width; }
        }

        public String getTitle() { return title; }
        public void setTitle(String title) { this.title = title; }

        public List<String> getCategories() { return categories; }
        public void setCategories(List<String> categories) { this.categories = categories; }

        public LabelsConfig getLabels() { return labels; }
        public void setLabels(LabelsConfig labels) { this.labels = labels; }

        public LineConfig getLine() { return line; }
        public void setLine(LineConfig line) { this.line = line; }

        public Boolean getVisible() { return visible; }
        public void setVisible(Boolean visible) { this.visible = visible; }
    }

    public static class LegendConfig {
        private Boolean visible;
        private String position;  // top, bottom, left, right

        public Boolean getVisible() { return visible; }
        public void setVisible(Boolean visible) { this.visible = visible; }

        public String getPosition() { return position; }
        public void setPosition(String position) { this.position = position; }
    }

    public static class ChartOptions {
        private Boolean transitions;
        private String theme;
        private Integer width;
        private Integer height;

        public Boolean getTransitions() { return transitions; }
        public void setTransitions(Boolean transitions) { this.transitions = transitions; }

        public String getTheme() { return theme; }
        public void setTheme(String theme) { this.theme = theme; }

        public Integer getWidth() { return width; }
        public void setWidth(Integer width) { this.width = width; }

        public Integer getHeight() { return height; }
        public void setHeight(Integer height) { this.height = height; }
    }

    // Main ChartConfig Getters and Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getChartType() { return chartType; }
    public void setChartType(String chartType) { this.chartType = chartType; }

    public List<SeriesConfig> getSeries() { return series; }
    public void setSeries(List<SeriesConfig> series) { this.series = series; }

    public DataSourceConfig getDataSource() { return dataSource; }
    public void setDataSource(DataSourceConfig dataSource) { this.dataSource = dataSource; }

    public ChartOptions getOptions() { return options; }
    public void setOptions(ChartOptions options) { this.options = options; }

    public AxisConfig getCategoryAxis() { return categoryAxis; }
    public void setCategoryAxis(AxisConfig categoryAxis) { this.categoryAxis = categoryAxis; }

    public AxisConfig getValueAxis() { return valueAxis; }
    public void setValueAxis(AxisConfig valueAxis) { this.valueAxis = valueAxis; }

    public LegendConfig getLegend() { return legend; }
    public void setLegend(LegendConfig legend) { this.legend = legend; }
}

package com.jsonui.model;

import java.util.List;
import java.util.Map;

/**
 * Kendo Form 설정을 JSON으로 정의하기 위한 모델
 * Grid의 편집 폼으로도 사용 가능
 */
public class FormConfig {
    private String id;
    private String title;
    private List<FormFieldConfig> fields;
    private FormOptions options;
    private ValidationConfig validation;

    public static class FormFieldConfig {
        private String name;
        private String label;
        private String type;  // text, number, date, dropdown, checkbox, textarea, etc.
        private Boolean required;
        private String placeholder;
        private Object defaultValue;
        private ValidationRules validationRules;
        private EditorConfig editor;
        private Map<String, Object> attributes;

        public static class ValidationRules {
            private Boolean required;
            private Integer minLength;
            private Integer maxLength;
            private Number min;
            private Number max;
            private String pattern;
            private String customValidation;
            private String message;

            public Boolean getRequired() { return required; }
            public void setRequired(Boolean required) { this.required = required; }

            public Integer getMinLength() { return minLength; }
            public void setMinLength(Integer minLength) { this.minLength = minLength; }

            public Integer getMaxLength() { return maxLength; }
            public void setMaxLength(Integer maxLength) { this.maxLength = maxLength; }

            public Number getMin() { return min; }
            public void setMin(Number min) { this.min = min; }

            public Number getMax() { return max; }
            public void setMax(Number max) { this.max = max; }

            public String getPattern() { return pattern; }
            public void setPattern(String pattern) { this.pattern = pattern; }

            public String getCustomValidation() { return customValidation; }
            public void setCustomValidation(String customValidation) { this.customValidation = customValidation; }

            public String getMessage() { return message; }
            public void setMessage(String message) { this.message = message; }
        }

        public static class EditorConfig {
            private String widget;  // dropdownlist, datepicker, numerictextbox, etc.
            private List<OptionConfig> options;
            private String dataTextField;
            private String dataValueField;
            private String format;
            private Number step;
            private Number decimals;

            public static class OptionConfig {
                private String text;
                private Object value;

                public String getText() { return text; }
                public void setText(String text) { this.text = text; }

                public Object getValue() { return value; }
                public void setValue(Object value) { this.value = value; }
            }

            public String getWidget() { return widget; }
            public void setWidget(String widget) { this.widget = widget; }

            public List<OptionConfig> getOptions() { return options; }
            public void setOptions(List<OptionConfig> options) { this.options = options; }

            public String getDataTextField() { return dataTextField; }
            public void setDataTextField(String dataTextField) { this.dataTextField = dataTextField; }

            public String getDataValueField() { return dataValueField; }
            public void setDataValueField(String dataValueField) { this.dataValueField = dataValueField; }

            public String getFormat() { return format; }
            public void setFormat(String format) { this.format = format; }

            public Number getStep() { return step; }
            public void setStep(Number step) { this.step = step; }

            public Number getDecimals() { return decimals; }
            public void setDecimals(Number decimals) { this.decimals = decimals; }
        }

        public String getName() { return name; }
        public void setName(String name) { this.name = name; }

        public String getLabel() { return label; }
        public void setLabel(String label) { this.label = label; }

        public String getType() { return type; }
        public void setType(String type) { this.type = type; }

        public Boolean getRequired() { return required; }
        public void setRequired(Boolean required) { this.required = required; }

        public String getPlaceholder() { return placeholder; }
        public void setPlaceholder(String placeholder) { this.placeholder = placeholder; }

        public Object getDefaultValue() { return defaultValue; }
        public void setDefaultValue(Object defaultValue) { this.defaultValue = defaultValue; }

        public ValidationRules getValidationRules() { return validationRules; }
        public void setValidationRules(ValidationRules validationRules) { this.validationRules = validationRules; }

        public EditorConfig getEditor() { return editor; }
        public void setEditor(EditorConfig editor) { this.editor = editor; }

        public Map<String, Object> getAttributes() { return attributes; }
        public void setAttributes(Map<String, Object> attributes) { this.attributes = attributes; }
    }

    public static class FormOptions {
        private String orientation;  // horizontal, vertical
        private String formData;  // URL to load form data
        private String submitUrl;
        private String submitMethod;  // POST, PUT
        private Boolean buttonsTemplate;
        private List<String> buttons;  // submit, clear, cancel

        public String getOrientation() { return orientation; }
        public void setOrientation(String orientation) { this.orientation = orientation; }

        public String getFormData() { return formData; }
        public void setFormData(String formData) { this.formData = formData; }

        public String getSubmitUrl() { return submitUrl; }
        public void setSubmitUrl(String submitUrl) { this.submitUrl = submitUrl; }

        public String getSubmitMethod() { return submitMethod; }
        public void setSubmitMethod(String submitMethod) { this.submitMethod = submitMethod; }

        public Boolean getButtonsTemplate() { return buttonsTemplate; }
        public void setButtonsTemplate(Boolean buttonsTemplate) { this.buttonsTemplate = buttonsTemplate; }

        public List<String> getButtons() { return buttons; }
        public void setButtons(List<String> buttons) { this.buttons = buttons; }
    }

    public static class ValidationConfig {
        private Boolean validateOnBlur;
        private String errorTemplate;

        public Boolean getValidateOnBlur() { return validateOnBlur; }
        public void setValidateOnBlur(Boolean validateOnBlur) { this.validateOnBlur = validateOnBlur; }

        public String getErrorTemplate() { return errorTemplate; }
        public void setErrorTemplate(String errorTemplate) { this.errorTemplate = errorTemplate; }
    }

    // Main FormConfig Getters and Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public List<FormFieldConfig> getFields() { return fields; }
    public void setFields(List<FormFieldConfig> fields) { this.fields = fields; }

    public FormOptions getOptions() { return options; }
    public void setOptions(FormOptions options) { this.options = options; }

    public ValidationConfig getValidation() { return validation; }
    public void setValidation(ValidationConfig validation) { this.validation = validation; }
}

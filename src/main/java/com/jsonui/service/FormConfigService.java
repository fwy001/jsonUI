package com.jsonui.service;

import com.jsonui.model.FormConfig;

import java.util.*;

/**
 * Form 설정 관리 서비스
 */
public class FormConfigService {
    private static final Map<String, FormConfig> configs = new HashMap<>();

    static {
        initializeSampleConfigs();
    }

    private static void initializeSampleConfigs() {
        // 사용자 등록 폼
        FormConfig userForm = new FormConfig();
        userForm.setId("userRegistration");
        userForm.setTitle("사용자 등록");

        List<FormConfig.FormFieldConfig> fields = new ArrayList<>();

        // 사용자명
        FormConfig.FormFieldConfig usernameField = new FormConfig.FormFieldConfig();
        usernameField.setName("username");
        usernameField.setLabel("사용자명");
        usernameField.setType("text");
        usernameField.setRequired(true);
        usernameField.setPlaceholder("사용자명을 입력하세요");
        FormConfig.FormFieldConfig.ValidationRules usernameValidation = new FormConfig.FormFieldConfig.ValidationRules();
        usernameValidation.setMinLength(3);
        usernameValidation.setMaxLength(20);
        usernameValidation.setMessage("사용자명은 3-20자 사이여야 합니다");
        usernameField.setValidationRules(usernameValidation);
        fields.add(usernameField);

        // 이메일
        FormConfig.FormFieldConfig emailField = new FormConfig.FormFieldConfig();
        emailField.setName("email");
        emailField.setLabel("이메일");
        emailField.setType("text");
        emailField.setRequired(true);
        emailField.setPlaceholder("email@example.com");
        FormConfig.FormFieldConfig.ValidationRules emailValidation = new FormConfig.FormFieldConfig.ValidationRules();
        emailValidation.setPattern("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$");
        emailValidation.setMessage("올바른 이메일 주소를 입력하세요");
        emailField.setValidationRules(emailValidation);
        fields.add(emailField);

        // 나이
        FormConfig.FormFieldConfig ageField = new FormConfig.FormFieldConfig();
        ageField.setName("age");
        ageField.setLabel("나이");
        ageField.setType("number");
        FormConfig.FormFieldConfig.EditorConfig ageEditor = new FormConfig.FormFieldConfig.EditorConfig();
        ageEditor.setWidget("numerictextbox");
        ageEditor.setStep(1);
        ageEditor.setDecimals(0);
        ageField.setEditor(ageEditor);
        FormConfig.FormFieldConfig.ValidationRules ageValidation = new FormConfig.FormFieldConfig.ValidationRules();
        ageValidation.setMin(18);
        ageValidation.setMax(100);
        ageValidation.setMessage("나이는 18-100 사이여야 합니다");
        ageField.setValidationRules(ageValidation);
        fields.add(ageField);

        // 성별
        FormConfig.FormFieldConfig genderField = new FormConfig.FormFieldConfig();
        genderField.setName("gender");
        genderField.setLabel("성별");
        genderField.setType("dropdown");
        FormConfig.FormFieldConfig.EditorConfig genderEditor = new FormConfig.FormFieldConfig.EditorConfig();
        genderEditor.setWidget("dropdownlist");
        List<FormConfig.FormFieldConfig.EditorConfig.OptionConfig> genderOptions = new ArrayList<>();
        genderOptions.add(createOption("남성", "male"));
        genderOptions.add(createOption("여성", "female"));
        genderOptions.add(createOption("기타", "other"));
        genderEditor.setOptions(genderOptions);
        genderField.setEditor(genderEditor);
        fields.add(genderField);

        // 생년월일
        FormConfig.FormFieldConfig birthDateField = new FormConfig.FormFieldConfig();
        birthDateField.setName("birthDate");
        birthDateField.setLabel("생년월일");
        birthDateField.setType("date");
        FormConfig.FormFieldConfig.EditorConfig dateEditor = new FormConfig.FormFieldConfig.EditorConfig();
        dateEditor.setWidget("datepicker");
        dateEditor.setFormat("yyyy-MM-dd");
        birthDateField.setEditor(dateEditor);
        fields.add(birthDateField);

        // 자기소개
        FormConfig.FormFieldConfig bioField = new FormConfig.FormFieldConfig();
        bioField.setName("bio");
        bioField.setLabel("자기소개");
        bioField.setType("textarea");
        bioField.setPlaceholder("자기소개를 입력하세요");
        fields.add(bioField);

        // 수신 동의
        FormConfig.FormFieldConfig agreeField = new FormConfig.FormFieldConfig();
        agreeField.setName("agreeToTerms");
        agreeField.setLabel("이용약관에 동의합니다");
        agreeField.setType("checkbox");
        agreeField.setRequired(true);
        fields.add(agreeField);

        userForm.setFields(fields);

        // 폼 옵션
        FormConfig.FormOptions options = new FormConfig.FormOptions();
        options.setOrientation("vertical");
        options.setSubmitUrl("api/data/users");
        options.setSubmitMethod("POST");
        options.setButtons(Arrays.asList("submit", "clear"));
        userForm.setOptions(options);

        // 검증 설정
        FormConfig.ValidationConfig validation = new FormConfig.ValidationConfig();
        validation.setValidateOnBlur(true);
        userForm.setValidation(validation);

        configs.put("userRegistration", userForm);
    }

    private static FormConfig.FormFieldConfig.EditorConfig.OptionConfig createOption(String text, String value) {
        FormConfig.FormFieldConfig.EditorConfig.OptionConfig option = new FormConfig.FormFieldConfig.EditorConfig.OptionConfig();
        option.setText(text);
        option.setValue(value);
        return option;
    }

    public List<FormConfig> getAllFormConfigs() {
        return new ArrayList<>(configs.values());
    }

    public FormConfig getFormConfig(String id) {
        return configs.get(id);
    }

    public FormConfig saveFormConfig(FormConfig config) {
        if (config.getId() == null || config.getId().isEmpty()) {
            config.setId("form_" + System.currentTimeMillis());
        }
        configs.put(config.getId(), config);
        return config;
    }

    public void deleteFormConfig(String id) {
        configs.remove(id);
    }
}

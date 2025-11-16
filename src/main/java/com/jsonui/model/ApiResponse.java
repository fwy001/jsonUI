package com.jsonui.model;

/**
 * 표준 API 응답 모델
 */
public class ApiResponse<T> {
    private boolean success;
    private String message;
    private T data;
    private Integer total;  // 페이징용
    private Object errors;

    public ApiResponse() {
    }

    public ApiResponse(boolean success, String message, T data) {
        this.success = success;
        this.message = message;
        this.data = data;
    }

    public static <T> ApiResponse<T> success(T data) {
        return new ApiResponse<>(true, "Success", data);
    }

    public static <T> ApiResponse<T> success(T data, int total) {
        ApiResponse<T> response = new ApiResponse<>(true, "Success", data);
        response.setTotal(total);
        return response;
    }

    public static <T> ApiResponse<T> error(String message) {
        return new ApiResponse<>(false, message, null);
    }

    public static <T> ApiResponse<T> error(String message, Object errors) {
        ApiResponse<T> response = new ApiResponse<>(false, message, null);
        response.setErrors(errors);
        return response;
    }

    // Getters and Setters
    public boolean isSuccess() { return success; }
    public void setSuccess(boolean success) { this.success = success; }

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }

    public T getData() { return data; }
    public void setData(T data) { this.data = data; }

    public Integer getTotal() { return total; }
    public void setTotal(Integer total) { this.total = total; }

    public Object getErrors() { return errors; }
    public void setErrors(Object errors) { this.errors = errors; }
}

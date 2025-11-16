package com.jsonui.servlet;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.jsonui.model.ApiResponse;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * 모든 서블릿의 기본 클래스
 * JSON 응답 처리 공통 기능 제공
 */
public abstract class BaseServlet extends HttpServlet {
    protected static final Gson gson = new GsonBuilder()
            .setPrettyPrinting()
            .setDateFormat("yyyy-MM-dd'T'HH:mm:ss")
            .create();

    /**
     * JSON 응답 전송
     */
    protected void sendJsonResponse(HttpServletResponse response, Object data) throws IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
        response.setHeader("Access-Control-Allow-Headers", "Content-Type");

        PrintWriter out = response.getWriter();
        out.print(gson.toJson(data));
        out.flush();
    }

    /**
     * 성공 응답 전송
     */
    protected void sendSuccessResponse(HttpServletResponse response, Object data) throws IOException {
        sendJsonResponse(response, ApiResponse.success(data));
    }

    /**
     * 성공 응답 전송 (페이징 포함)
     */
    protected void sendSuccessResponse(HttpServletResponse response, Object data, int total) throws IOException {
        sendJsonResponse(response, ApiResponse.success(data, total));
    }

    /**
     * 에러 응답 전송
     */
    protected void sendErrorResponse(HttpServletResponse response, String message) throws IOException {
        response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        sendJsonResponse(response, ApiResponse.error(message));
    }

    /**
     * 에러 응답 전송 (상세 정보 포함)
     */
    protected void sendErrorResponse(HttpServletResponse response, String message, Object errors) throws IOException {
        response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        sendJsonResponse(response, ApiResponse.error(message, errors));
    }

    /**
     * OPTIONS 요청 처리 (CORS)
     */
    @Override
    protected void doOptions(HttpServletResponse response) throws IOException {
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
        response.setHeader("Access-Control-Allow-Headers", "Content-Type");
        response.setStatus(HttpServletResponse.SC_OK);
    }
}

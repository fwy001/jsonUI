package com.jsonui.servlet;

import com.jsonui.model.FormConfig;
import com.jsonui.service.FormConfigService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Form 설정을 JSON으로 제공하는 서블릿
 */
@WebServlet("/api/form/config/*")
public class FormConfigServlet extends BaseServlet {
    private final FormConfigService configService = new FormConfigService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            String pathInfo = request.getPathInfo();

            if (pathInfo == null || pathInfo.equals("/")) {
                sendSuccessResponse(response, configService.getAllFormConfigs());
            } else {
                String formId = pathInfo.substring(1);
                FormConfig config = configService.getFormConfig(formId);

                if (config != null) {
                    sendSuccessResponse(response, config);
                } else {
                    sendErrorResponse(response, "Form configuration not found: " + formId);
                }
            }
        } catch (Exception e) {
            sendErrorResponse(response, "Error retrieving form configuration: " + e.getMessage());
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            FormConfig config = gson.fromJson(request.getReader(), FormConfig.class);
            FormConfig saved = configService.saveFormConfig(config);
            sendSuccessResponse(response, saved);
        } catch (Exception e) {
            sendErrorResponse(response, "Error saving form configuration: " + e.getMessage());
        }
    }
}

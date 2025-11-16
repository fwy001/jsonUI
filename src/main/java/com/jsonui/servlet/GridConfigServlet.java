package com.jsonui.servlet;

import com.jsonui.model.GridConfig;
import com.jsonui.service.GridConfigService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Grid 설정을 JSON으로 제공하는 서블릿
 */
@WebServlet("/api/grid/config/*")
public class GridConfigServlet extends BaseServlet {
    private final GridConfigService configService = new GridConfigService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            String pathInfo = request.getPathInfo();

            if (pathInfo == null || pathInfo.equals("/")) {
                // 모든 Grid 설정 목록 반환
                sendSuccessResponse(response, configService.getAllGridConfigs());
            } else {
                // 특정 Grid 설정 반환
                String gridId = pathInfo.substring(1);
                GridConfig config = configService.getGridConfig(gridId);

                if (config != null) {
                    sendSuccessResponse(response, config);
                } else {
                    sendErrorResponse(response, "Grid configuration not found: " + gridId);
                }
            }
        } catch (Exception e) {
            sendErrorResponse(response, "Error retrieving grid configuration: " + e.getMessage());
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            GridConfig config = gson.fromJson(request.getReader(), GridConfig.class);
            GridConfig saved = configService.saveGridConfig(config);
            sendSuccessResponse(response, saved);
        } catch (Exception e) {
            sendErrorResponse(response, "Error saving grid configuration: " + e.getMessage());
        }
    }
}

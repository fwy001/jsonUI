package com.jsonui.servlet;

import com.jsonui.model.ChartConfig;
import com.jsonui.service.ChartConfigService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Chart 설정을 JSON으로 제공하는 서블릿
 */
@WebServlet("/api/chart/config/*")
public class ChartConfigServlet extends BaseServlet {
    private final ChartConfigService configService = new ChartConfigService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            String pathInfo = request.getPathInfo();

            if (pathInfo == null || pathInfo.equals("/")) {
                sendSuccessResponse(response, configService.getAllChartConfigs());
            } else {
                String chartId = pathInfo.substring(1);
                ChartConfig config = configService.getChartConfig(chartId);

                if (config != null) {
                    sendSuccessResponse(response, config);
                } else {
                    sendErrorResponse(response, "Chart configuration not found: " + chartId);
                }
            }
        } catch (Exception e) {
            sendErrorResponse(response, "Error retrieving chart configuration: " + e.getMessage());
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            ChartConfig config = gson.fromJson(request.getReader(), ChartConfig.class);
            ChartConfig saved = configService.saveChartConfig(config);
            sendSuccessResponse(response, saved);
        } catch (Exception e) {
            sendErrorResponse(response, "Error saving chart configuration: " + e.getMessage());
        }
    }
}

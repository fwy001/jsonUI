package com.jsonui.servlet;

import com.jsonui.service.DataService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * 실제 데이터를 제공하는 서블릿
 * Grid와 Chart에서 사용할 데이터를 JSON으로 반환
 */
@WebServlet("/api/data/*")
public class DataServlet extends BaseServlet {
    private final DataService dataService = new DataService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            String pathInfo = request.getPathInfo();

            if (pathInfo == null || pathInfo.equals("/")) {
                sendErrorResponse(response, "Data type not specified");
                return;
            }

            String dataType = pathInfo.substring(1);

            // 페이징 파라미터
            int page = getIntParameter(request, "page", 1);
            int pageSize = getIntParameter(request, "pageSize", 20);
            String sort = request.getParameter("sort");
            String filter = request.getParameter("filter");

            List<Map<String, Object>> data = dataService.getData(dataType, page, pageSize, sort, filter);
            int total = dataService.getTotalCount(dataType, filter);

            sendSuccessResponse(response, data, total);

        } catch (Exception e) {
            sendErrorResponse(response, "Error retrieving data: " + e.getMessage());
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            String pathInfo = request.getPathInfo();
            String dataType = pathInfo != null ? pathInfo.substring(1) : null;

            Map<String, Object> data = gson.fromJson(request.getReader(), Map.class);
            Map<String, Object> saved = dataService.createData(dataType, data);

            sendSuccessResponse(response, saved);
        } catch (Exception e) {
            sendErrorResponse(response, "Error creating data: " + e.getMessage());
        }
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            String pathInfo = request.getPathInfo();
            String dataType = pathInfo != null ? pathInfo.substring(1) : null;

            Map<String, Object> data = gson.fromJson(request.getReader(), Map.class);
            Map<String, Object> updated = dataService.updateData(dataType, data);

            sendSuccessResponse(response, updated);
        } catch (Exception e) {
            sendErrorResponse(response, "Error updating data: " + e.getMessage());
        }
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            String pathInfo = request.getPathInfo();
            String[] parts = pathInfo.substring(1).split("/");
            String dataType = parts[0];
            String id = parts.length > 1 ? parts[1] : null;

            if (id == null) {
                sendErrorResponse(response, "ID not specified");
                return;
            }

            dataService.deleteData(dataType, id);
            sendSuccessResponse(response, Map.of("deleted", true, "id", id));

        } catch (Exception e) {
            sendErrorResponse(response, "Error deleting data: " + e.getMessage());
        }
    }

    private int getIntParameter(HttpServletRequest request, String name, int defaultValue) {
        String value = request.getParameter(name);
        if (value != null && !value.isEmpty()) {
            try {
                return Integer.parseInt(value);
            } catch (NumberFormatException e) {
                return defaultValue;
            }
        }
        return defaultValue;
    }
}

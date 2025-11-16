package com.jsonui.service;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 실제 데이터 관리 서비스
 * 실제 환경에서는 데이터베이스와 연동
 */
public class DataService {
    private static final Map<String, List<Map<String, Object>>> dataStore = new HashMap<>();
    private static int nextEmployeeId = 1;

    static {
        initializeSampleData();
    }

    private static void initializeSampleData() {
        // 직원 데이터
        List<Map<String, Object>> employees = new ArrayList<>();
        employees.add(createEmployee(nextEmployeeId++, "김철수", "kim@example.com", "개발팀", 5500000, "2020-01-15"));
        employees.add(createEmployee(nextEmployeeId++, "이영희", "lee@example.com", "디자인팀", 4800000, "2020-03-20"));
        employees.add(createEmployee(nextEmployeeId++, "박민수", "park@example.com", "개발팀", 6000000, "2019-05-10"));
        employees.add(createEmployee(nextEmployeeId++, "최지은", "choi@example.com", "마케팅팀", 5000000, "2021-02-01"));
        employees.add(createEmployee(nextEmployeeId++, "정현우", "jung@example.com", "인사팀", 5200000, "2020-07-15"));
        employees.add(createEmployee(nextEmployeeId++, "강민지", "kang@example.com", "개발팀", 5800000, "2019-11-20"));
        employees.add(createEmployee(nextEmployeeId++, "윤서연", "yoon@example.com", "디자인팀", 4900000, "2021-04-10"));
        employees.add(createEmployee(nextEmployeeId++, "임동현", "lim@example.com", "마케팅팀", 5100000, "2020-09-05"));
        employees.add(createEmployee(nextEmployeeId++, "한지훈", "han@example.com", "개발팀", 6200000, "2018-12-01"));
        employees.add(createEmployee(nextEmployeeId++, "조수진", "jo@example.com", "인사팀", 5300000, "2021-06-15"));
        employees.add(createEmployee(nextEmployeeId++, "신예은", "shin@example.com", "디자인팀", 5000000, "2020-10-20"));
        employees.add(createEmployee(nextEmployeeId++, "배준호", "bae@example.com", "개발팀", 5700000, "2019-08-15"));
        employees.add(createEmployee(nextEmployeeId++, "홍서윤", "hong@example.com", "마케팅팀", 5200000, "2021-01-10"));
        employees.add(createEmployee(nextEmployeeId++, "송민호", "song@example.com", "개발팀", 6100000, "2019-03-25"));
        employees.add(createEmployee(nextEmployeeId++, "문채원", "moon@example.com", "인사팀", 5400000, "2020-12-05"));

        dataStore.put("employees", employees);

        // 월별 매출 데이터
        List<Map<String, Object>> sales = new ArrayList<>();
        sales.add(createSalesData("1월", 45000000));
        sales.add(createSalesData("2월", 52000000));
        sales.add(createSalesData("3월", 48000000));
        sales.add(createSalesData("4월", 61000000));
        sales.add(createSalesData("5월", 58000000));
        sales.add(createSalesData("6월", 67000000));
        sales.add(createSalesData("7월", 72000000));
        sales.add(createSalesData("8월", 68000000));
        sales.add(createSalesData("9월", 75000000));
        sales.add(createSalesData("10월", 81000000));
        sales.add(createSalesData("11월", 78000000));
        sales.add(createSalesData("12월", 95000000));

        dataStore.put("sales", sales);

        // 부서별 통계 데이터
        List<Map<String, Object>> deptStats = new ArrayList<>();
        deptStats.add(createDeptStats("개발팀", 6));
        deptStats.add(createDeptStats("디자인팀", 3));
        deptStats.add(createDeptStats("마케팅팀", 3));
        deptStats.add(createDeptStats("인사팀", 3));

        dataStore.put("department-stats", deptStats);
    }

    private static Map<String, Object> createEmployee(int id, String name, String email,
                                                       String department, int salary, String hireDate) {
        Map<String, Object> employee = new HashMap<>();
        employee.put("id", id);
        employee.put("name", name);
        employee.put("email", email);
        employee.put("department", department);
        employee.put("salary", salary);
        employee.put("hireDate", hireDate);
        return employee;
    }

    private static Map<String, Object> createSalesData(String month, int sales) {
        Map<String, Object> data = new HashMap<>();
        data.put("month", month);
        data.put("sales", sales);
        return data;
    }

    private static Map<String, Object> createDeptStats(String department, int count) {
        Map<String, Object> stats = new HashMap<>();
        stats.put("department", department);
        stats.put("count", count);
        return stats;
    }

    public List<Map<String, Object>> getData(String type, int page, int pageSize, String sort, String filter) {
        List<Map<String, Object>> data = dataStore.getOrDefault(type, new ArrayList<>());

        // 필터링 (간단한 구현)
        // 실제로는 더 복잡한 필터링 로직 필요

        // 정렬 (간단한 구현)
        // 실제로는 동적 정렬 로직 필요

        // 페이징
        int start = (page - 1) * pageSize;
        int end = Math.min(start + pageSize, data.size());

        if (start >= data.size()) {
            return new ArrayList<>();
        }

        return new ArrayList<>(data.subList(start, end));
    }

    public int getTotalCount(String type, String filter) {
        List<Map<String, Object>> data = dataStore.getOrDefault(type, new ArrayList<>());
        return data.size();
    }

    public Map<String, Object> createData(String type, Map<String, Object> data) {
        List<Map<String, Object>> list = dataStore.computeIfAbsent(type, k -> new ArrayList<>());

        // ID 자동 생성
        if (type.equals("employees")) {
            data.put("id", nextEmployeeId++);
        } else {
            data.put("id", list.size() + 1);
        }

        list.add(data);
        return data;
    }

    public Map<String, Object> updateData(String type, Map<String, Object> data) {
        List<Map<String, Object>> list = dataStore.get(type);
        if (list == null) {
            return null;
        }

        Object id = data.get("id");
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).get("id").equals(id)) {
                list.set(i, data);
                return data;
            }
        }

        return null;
    }

    public void deleteData(String type, String id) {
        List<Map<String, Object>> list = dataStore.get(type);
        if (list == null) {
            return;
        }

        list.removeIf(item -> item.get("id").toString().equals(id));
    }
}

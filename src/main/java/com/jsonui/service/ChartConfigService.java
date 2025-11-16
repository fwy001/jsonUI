package com.jsonui.service;

import com.jsonui.model.ChartConfig;

import java.util.*;

/**
 * Chart 설정 관리 서비스
 */
public class ChartConfigService {
    private static final Map<String, ChartConfig> configs = new HashMap<>();

    static {
        initializeSampleConfigs();
    }

    private static void initializeSampleConfigs() {
        // 월별 매출 차트
        ChartConfig salesChart = new ChartConfig();
        salesChart.setId("monthlySales");
        salesChart.setTitle("월별 매출 현황");
        salesChart.setChartType("column");

        // 시리즈 설정
        List<ChartConfig.SeriesConfig> series = new ArrayList<>();

        ChartConfig.SeriesConfig series1 = new ChartConfig.SeriesConfig();
        series1.setType("column");
        series1.setField("sales");
        series1.setCategoryField("month");
        series1.setName("매출");
        series1.setColor("#4CAF50");

        ChartConfig.SeriesConfig.LabelsConfig labels = new ChartConfig.SeriesConfig.LabelsConfig();
        labels.setVisible(true);
        labels.setFormat("{0:c}");
        series1.setLabels(labels);

        series.add(series1);
        salesChart.setSeries(series);

        // DataSource 설정
        ChartConfig.DataSourceConfig dataSource = new ChartConfig.DataSourceConfig();
        dataSource.setUrl("api/data/sales");
        dataSource.setType("json");
        salesChart.setDataSource(dataSource);

        // 축 설정
        ChartConfig.AxisConfig categoryAxis = new ChartConfig.AxisConfig();
        categoryAxis.setTitle("월");
        salesChart.setCategoryAxis(categoryAxis);

        ChartConfig.AxisConfig valueAxis = new ChartConfig.AxisConfig();
        valueAxis.setTitle("매출액");
        ChartConfig.AxisConfig.LabelsConfig valueLabels = new ChartConfig.AxisConfig.LabelsConfig();
        valueLabels.setFormat("{0:c}");
        valueAxis.setLabels(valueLabels);
        salesChart.setValueAxis(valueAxis);

        // 범례 설정
        ChartConfig.LegendConfig legend = new ChartConfig.LegendConfig();
        legend.setVisible(true);
        legend.setPosition("bottom");
        salesChart.setLegend(legend);

        // 차트 옵션
        ChartConfig.ChartOptions options = new ChartConfig.ChartOptions();
        options.setTransitions(true);
        options.setHeight(400);
        salesChart.setOptions(options);

        configs.put("monthlySales", salesChart);

        // 부서별 인원 파이 차트
        ChartConfig deptChart = new ChartConfig();
        deptChart.setId("departmentPie");
        deptChart.setTitle("부서별 인원");
        deptChart.setChartType("pie");

        List<ChartConfig.SeriesConfig> pieSeries = new ArrayList<>();
        ChartConfig.SeriesConfig pieSeries1 = new ChartConfig.SeriesConfig();
        pieSeries1.setType("pie");
        pieSeries1.setField("count");
        pieSeries1.setCategoryField("department");

        ChartConfig.SeriesConfig.LabelsConfig pieLabels = new ChartConfig.SeriesConfig.LabelsConfig();
        pieLabels.setVisible(true);
        pieLabels.setTemplate("#= category # - #= value #명");
        pieSeries1.setLabels(pieLabels);

        pieSeries.add(pieSeries1);
        deptChart.setSeries(pieSeries);

        ChartConfig.DataSourceConfig pieDataSource = new ChartConfig.DataSourceConfig();
        pieDataSource.setUrl("api/data/department-stats");
        deptChart.setDataSource(pieDataSource);

        ChartConfig.LegendConfig pieLegend = new ChartConfig.LegendConfig();
        pieLegend.setVisible(true);
        pieLegend.setPosition("right");
        deptChart.setLegend(pieLegend);

        ChartConfig.ChartOptions pieOptions = new ChartConfig.ChartOptions();
        pieOptions.setHeight(400);
        deptChart.setOptions(pieOptions);

        configs.put("departmentPie", deptChart);
    }

    public List<ChartConfig> getAllChartConfigs() {
        return new ArrayList<>(configs.values());
    }

    public ChartConfig getChartConfig(String id) {
        return configs.get(id);
    }

    public ChartConfig saveChartConfig(ChartConfig config) {
        if (config.getId() == null || config.getId().isEmpty()) {
            config.setId("chart_" + System.currentTimeMillis());
        }
        configs.put(config.getId(), config);
        return config;
    }

    public void deleteChartConfig(String id) {
        configs.remove(id);
    }
}

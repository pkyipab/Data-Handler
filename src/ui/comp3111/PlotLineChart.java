package ui.comp3111;

import core.comp3111.ChartTable;
import core.comp3111.DataColumn;
import core.comp3111.DataTable;

import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Series;

public class PlotLineChart {
	
	private NumberAxis xAxis = new NumberAxis();
    private NumberAxis yAxis = new NumberAxis();
    int chartCount;
    String chartTitle = "Chart" + chartCount;
    LineChart<Number, Number> lineChart = new LineChart<Number, Number>(xAxis, yAxis);
    XYChart.Series<Number, Number> series = new Series<Number, Number>();
    
    public PlotLineChart(ChartTable o) {
    	chartCount = o.ct.size() + 1; //First Chart will called Chart 1, then Chart 2, then Chart 3...etc
    }
    
    public void createLineChart(ChartTable exist, DataColumn x, DataColumn y) {
    	//chartTitle = ...Think later
    }
    
    public void addChart(ChartTable target) {
    	target.ct.put(chartTitle, lineChart);
    }
    
    /*
     * Not a requirement, optional
     * public void removeLineChart(ChartTable exist, String chartName) {
     *    
     * }
     * 
     */
    
}

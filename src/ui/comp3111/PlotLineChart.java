package ui.comp3111;

import core.comp3111.ChartTable;
import core.comp3111.DataColumn;

import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Data;
import javafx.scene.chart.XYChart.Series;

public class PlotLineChart {
	
	private NumberAxis xAxis = new NumberAxis();
    private NumberAxis yAxis = new NumberAxis();
    int chartCount;
    
    public void createLineChart(ChartTable exist, DataColumn x, DataColumn y) {
    	chartCount = exist.ct.size();
    	String cTitle = "Chart " + chartCount;	//First Chart will called Chart 1, then Chart 2, then Chart 3...etc
    	LineChart<Number, Number> lineChart = new LineChart<Number, Number>(xAxis, yAxis);
    	XYChart.Series<Number, Number> series = new Series<Number, Number>();
    	for(int i = 0; i < x.getSize(); i++) {
    		series.getData().add(new Data<Number, Number>((Number)x.getData()[i], (Number)y.getData()[i]));
    		System.out.println("Added the " + i + " row data: X-axis index = " + (Number)x.getData()[i] + ", Y-axis index = " + (Number)y.getData()[i]);
    	}
    	lineChart.getData().add(series);
    	exist.ct.put(cTitle, lineChart);
    }
    
    /*
     * Not a requirement, optional
     * public void removeLineChart(ChartTable exist, String chartName) {
     *    
     * }
     * 
     */
    
}

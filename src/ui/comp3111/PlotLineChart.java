package ui.comp3111;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

import core.comp3111.DataColumn;
import core.comp3111.DataTable;
import core.comp3111.DataType;
import javafx.scene.chart.Chart;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Data;
import javafx.scene.chart.XYChart.Series;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class PlotLineChart {
	
	private NumberAxis xAxis = new NumberAxis();
    private NumberAxis yAxis = new NumberAxis();
    private DataTable recieved;
    private LinkedHashMap<String ,DataColumn> filtedSet;
    int chartCount;
    
    public PlotLineChart(DataTable dataTable) {
    	this.recieved = dataTable;
    	//filtrateColumn(recieved);    <----this function need to fix bug
    }
    
    public void filtrateColumn(DataTable source) {
    	DataColumn temp;
    	String name;
    	int size = source.getNumCol();
    	for(int i = 0; i < size; i++) {
    		temp = source.getColByNum(i);
    		name = source.getColName(i);
    		if(temp.getTypeName() == DataType.TYPE_NUMBER) {
    			filtedSet.put(name, temp);
    		}
    	}
    }
    
    public LinkedHashMap<String ,DataColumn> getFiltedSet(){
    	return this.filtedSet;
    }
    
    /*
     * Filtered all the dataColumn from the receive one
     * String type dataColumn are removed
     * Become a new DataTable to hold remaining numeric type dataColumn
     */
    
    public void createLineChart(ArrayList<Chart> exist, Map<VBox, Chart> cMap, DataColumn x, DataColumn y) {
    	
    	chartCount = exist.size();
    	String cTitle = "Chart " + chartCount;
    	
    	LineChart<Number, Number> lineChart = new LineChart<Number, Number>(xAxis, yAxis);
    	XYChart.Series<Number, Number> series = new Series<Number, Number>();
    	for(int i = 0; i < x.getSize(); i++) {
    		series.getData().add(new Data<Number, Number>((Number)x.getData()[i], (Number)y.getData()[i]));
    		System.out.println("Added the " + i + " row data: X-axis index = " + (Number)x.getData()[i] + ", Y-axis index = " + (Number)y.getData()[i]);
    	}
    	
    	lineChart.getData().add(series);
    	exist.add(lineChart);
    	VBox chartBox = new VBox(20);
    	chartBox.getChildren().add(new Label(cTitle));
    	cMap.put(chartBox, lineChart);
    	
    	System.out.println("NEW Line Chart : " + cTitle + " created.");
    }
    
    /*
     * Not a requirement, optional
     * public void removeLineChart(ChartTable exist, String chartName) {
     *    
     * }
     * 
     */
    
}

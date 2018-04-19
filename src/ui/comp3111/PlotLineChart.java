package ui.comp3111;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

import core.comp3111.DataColumn;
import core.comp3111.DataTable;
import core.comp3111.DataType;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
    private LinkedHashMap<String ,DataColumn> filtedSet = new LinkedHashMap<String ,DataColumn>();
    private ArrayList<String> list = new ArrayList<String>();
    private ArrayList<String> name = new ArrayList<String>();
    
    public PlotLineChart(DataTable dataTable) {
    	this.recieved = dataTable;
    	filtrateColumn(recieved);
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
    			list.add(name);
    		}
    	}
    }
    
    public ArrayList<String> getList(){
    	return this.list;
    }
    
    public LinkedHashMap<String ,DataColumn> getFiltedSet(){
    	return this.filtedSet;
    }
    
    public void createLineChart(ArrayList<Chart> exist, Map<VBox, Chart> cMap, LinkedHashMap<String ,DataColumn> givenSet, String xAxisData, String yAxisData) {
    	
    	int chartCount = exist.size();
    	String cTitle = "Chart " + (chartCount + 1);
    	
    	LineChart<Number, Number> lineChart = new LineChart<Number, Number>(xAxis, yAxis);
    	XYChart.Series<Number, Number> series = new Series<Number, Number>();
    	lineChart.setTitle(xAxisData + " versus " + yAxisData);
    	xAxis.setLabel(xAxisData);   
    	yAxis.setLabel(yAxisData);   
    	series.setName(xAxisData + "/" + yAxisData);
    	DataColumn dataColumnX = givenSet.get(xAxisData);
    	DataColumn dataColumnY = givenSet.get(yAxisData);
    	for(int i = 0; i < dataColumnX.getSize(); i++) {
    		series.getData().add(new Data<Number, Number>((Number)dataColumnX.getData()[i], (Number)dataColumnY.getData()[i]));
    		System.out.println("Added the " + i + " row data: X-axis index = " + (Number)dataColumnX.getData()[i] + ", Y-axis index = " + (Number)dataColumnY.getData()[i]);
    	}
    	
    	lineChart.getData().add(series);
    	exist.add(lineChart);
    	name.add(cTitle);
    	VBox chartBox = new VBox(20);
    	chartBox.getChildren().add(new Label(cTitle));
    	cMap.put(chartBox, lineChart);
    	
    	System.out.println("NEW Line Chart : " + cTitle + " created.");
    }
    
    /*
     * Not a requirement, optional
     * public void removeLineChart(ArrayList<Chart> exist, Map<VBox, Chart> cMap) {
     *    
     * }
     * 
     */
    
}

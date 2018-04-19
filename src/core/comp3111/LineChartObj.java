package core.comp3111;

import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Data;
import javafx.scene.chart.XYChart.Series;

public class LineChartObj extends GeneralChart {
	private DataColumn xAxisColumn;
	private DataColumn yAxisColumn;
	private NumberAxis xAxis = new NumberAxis();
    private NumberAxis yAxis = new NumberAxis();
    XYChart.Series<Number, Number> series;
    
	public LineChartObj(DataColumn xData, DataColumn yData, DataTable dt, String xAxisName, String yAxisName) {
		this.chartName = "Chart " + (dt.getStoredChart().size() + 1) + " [Type = Line] [X-Axis: " + xAxisName + ", Y-Axis: " + yAxisName + "]";
		this.xAxisColumn = xData;
		this.yAxisColumn = yData;
		chart = new LineChart<Number, Number>(xAxis, yAxis);
		series = new Series<Number, Number>();
		chart.setTitle(xAxisName + " versus " + yAxisName);
		xAxis.setLabel(xAxisName);
		yAxis.setLabel(yAxisName);
		series.setName(xAxisName + " versus " + yAxisName);
		for(int i = 0; i < xData.getSize(); i++) {
			series.getData().add(new Data<Number, Number>((Number)xAxisColumn.getData()[i], (Number)yAxisColumn.getData()[i]));
			System.out.println("Added " + ( i + 1 ) + " row data. [ X-Axis index = " + (Number)xAxisColumn.getData()[i] + " ] [ Y-Axis index = " + (Number)yAxisColumn.getData()[i] + " ]");
		}
		System.out.println("[ NEW Line Chart : " + this.chartName + " created ]");
	}

	@Override
	public void show() {
		
	}

}

package core.comp3111;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.PieChart;

public class PieChartObj extends GeneralChart {
	private DataColumn numColumn;
	private DataColumn textColumn;
	ObservableList<PieChart.Data> pieChartData;

	public PieChartObj(DataColumn number, DataColumn string, DataTable dt, String numItemsName, String stringItemsName) {
		this.numColumn = number;
		this.textColumn = string;
		pieChartData = FXCollections.observableArrayList();
		this.chartName = "Chart " + (dt.getStoredChart().size() + 1) + "[Type = Pie] [Items: " + stringItemsName + ", Quantity: " + numItemsName + "]";
		this.chart.setTitle(stringItemsName + " Distriubution");
		for(int i = 0; i < number.getSize(); i++) {
			pieChartData.add(new PieChart.Data((String)textColumn.getData()[i], (double)numColumn.getData()[i]));
			System.out.println("Added " + i + " data. [ Items = " + (String)textColumn.getData()[i] + " ] [ Quantity = " + (double)numColumn.getData()[i] + " ]");
		}
		this.chart = new PieChart(pieChartData);
		System.out.println("[ NEW Line Chart : " + this.chartName + " created ]");
	}
	
	@Override
	public void show() {
		
	}

}

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
		PieChart pieChart = new PieChart(pieChartData);;
		this.chartName = "Chart " + (dt.getStoredChart().size() + 1) + " [Type = Pie] [Items: " + stringItemsName + ", Quantity: " + numItemsName + "]";
		pieChart.setTitle(numItemsName + " Distriubution");
		for(int i = 0; i < number.getSize(); i++) {
			pieChartData.add(new PieChart.Data((String)textColumn.getData()[i], ((Number)numColumn.getData()[i]).doubleValue()));
			System.out.println("Added " + ( i + 1 ) + " data. [ Items = " + (String)textColumn.getData()[i] + " ] [ Quantity = " + ((Number)numColumn.getData()[i]).doubleValue() + " ]");
		}
		this.chart = pieChart;
		System.out.println("[ NEW Line Chart : " + this.chartName + " created ]");
	}

	@Override
	public void animationStart() {
		//WILL NOT DO
		
	}
}

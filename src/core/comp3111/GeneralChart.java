package core.comp3111;

import javafx.scene.chart.Chart;

public abstract class GeneralChart {
	protected String chartName;
	protected Chart chart;
	
	public String getTitle() {
		return this.chartName;
	}
	
	public Chart getChart() {
		return this.chart;
	}
	
	public abstract void show();
}

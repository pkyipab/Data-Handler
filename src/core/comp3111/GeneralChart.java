package core.comp3111;

import java.io.Serializable;

import javafx.scene.Scene;
import javafx.scene.chart.Chart;
import javafx.stage.Stage;

public abstract class GeneralChart implements Serializable {
	protected String chartName;
	protected Chart chart;
	
	public String getTitle() {
		return this.chartName;
	}
	
	public Chart getChart() {
		return this.chart;
	}
	
	public void show() {
		Stage dialog = new Stage();
		Scene dialogScene = new Scene(this.chart, 800, 600);
		dialog.setScene(dialogScene);
		dialog.show();
	}
	
	public abstract void animationStart();
}

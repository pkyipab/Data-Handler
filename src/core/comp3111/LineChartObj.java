package core.comp3111;

import java.io.Serializable;

public class LineChartObj extends GeneralChart implements Serializable{
	private DataColumn xAxisColumn;
	private DataColumn yAxisColumn;
	private String xAxisName;
	private String yAxisName;
	private boolean animated;

    
	public LineChartObj(DataColumn xData, DataColumn yData, DataTable dt, String xAxisName, String yAxisName, boolean animated) {
		if(animated)
			this.chartName = "Chart " + (dt.getStoredChart().size() + 1) + " [Animated Line] [X-Axis: " + xAxisName + 
								", Y-Axis: " + yAxisName + "]";
		else
			this.chartName = "Chart " + (dt.getStoredChart().size() + 1) + " [Line] [X-Axis: " + xAxisName + 
								", Y-Axis: " + yAxisName + "]";
		this.xAxisColumn = xData;
		this.yAxisColumn = yData;
		this.xAxisName = xAxisName;
		this.yAxisName = yAxisName;
		this.animated = animated;
	}
	
	public String getXAxisName() {
		return xAxisName;
	}

	public String getYAxisName() {
		return yAxisName;
	}

	public DataColumn getXAxisColumn() {
		return xAxisColumn;
	}
	
	public DataColumn getYAxisColumn() {
		return yAxisColumn;
	}

	public boolean isAnimated() {
		return animated;
	}
	
}

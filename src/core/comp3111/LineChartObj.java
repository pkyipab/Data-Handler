package core.comp3111;

import java.io.Serializable;


/**
 * LineChartObj - A class that stored the x, y DataColumn for line chart. This class will be used by DataTable. It
 * stores the x, y axis column and the column name and would it animated
 * 
 * @author cpkoaajack
 *
 */
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
	/**
	 * Get Method 
	 * 
	 * @return the XAxisName
	 */
	public String getXAxisName() {
		return xAxisName;
	}

	/**
	 * Get Method 
	 * 
	 * @return the yAxisName
	 */
	public String getYAxisName() {
		return yAxisName;
	}

	/**
	 * Get Method 
	 * 
	 * @return the xAxisColumn
	 */
	public DataColumn getXAxisColumn() {
		return xAxisColumn;
	}

	/**
	 * Get Method 
	 * 
	 * @return the YAxisColumn
	 */
	public DataColumn getYAxisColumn() {
		return yAxisColumn;
	}

	/**
	 * Get Method 
	 * 
	 * @return boolean is this animated
	 */
	public boolean isAnimated() {
		return animated;
	}
	
}

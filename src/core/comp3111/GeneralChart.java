package core.comp3111;

import java.io.Serializable;

/**
 * GeneralChart - A abstract for implement the line chart and pie chart. This class will be used by DataTable. It
 * stores the chart name
 * 
 * @author cpkoaajack
 *
 */
public abstract class GeneralChart implements Serializable {
	protected String chartName;

	/**
	 * Get Method 
	 * 
	 * @return the chartName
	 */
	public String getTitle() {
		return this.chartName;
	}
}

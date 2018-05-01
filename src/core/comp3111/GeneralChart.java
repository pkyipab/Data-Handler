package core.comp3111;

import java.io.Serializable;

public abstract class GeneralChart implements Serializable {
	protected String chartName;
	
	public String getTitle() {
		return this.chartName;
	}
}

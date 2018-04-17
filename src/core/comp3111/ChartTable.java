package core.comp3111;

import java.util.LinkedHashMap;
import java.util.Map;

import javafx.scene.chart.*;

public class ChartTable {
	
	public Map<String, Chart> ct;
	
	public ChartTable() {
		ct = new LinkedHashMap<String, Chart>();
	}
}

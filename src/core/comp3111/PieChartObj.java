package core.comp3111;


public class PieChartObj extends GeneralChart {
	private DataColumn numColumn;
	private DataColumn textColumn;
	private String numItemsName;

	public PieChartObj(DataColumn number, DataColumn string, DataTable dt, String numItemsName, String stringItemsName) {
		this.numColumn = number;
		this.textColumn = string;
		this.chartName = "Chart " + (dt.getStoredChart().size() + 1) + " [Pie] [Items: " + stringItemsName + 
				", Quantity: " + numItemsName + "]";
		this.numItemsName = numItemsName;
	}
	
	public DataColumn getnumColumn() {
		return numColumn;
	}
	
	public DataColumn getTextColumn() {
		return textColumn;
	} 
	
	public String getNumItemsName() {
		return numItemsName;
	}
}

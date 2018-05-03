package core.comp3111;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * LineChartObj - A class that stored the text and number DataColumn for pie chart. This class will be used by DataTable. It
 * stores the text and number column and column name
 * 
 * @author cpkoaajack
 *
 */
public class PieChartObj extends GeneralChart {
	private DataColumn numColumn;
	private DataColumn textColumn;
	private String numItemsName;

	public PieChartObj(DataColumn number, DataColumn string, DataTable dt, String numItemsName, String stringItemsName) {
		this.chartName = "Chart " + (dt.getStoredChart().size() + 1) + " [Pie] [Items: " + stringItemsName + 
				", Quantity: " + numItemsName + "]";
		this.numItemsName = numItemsName;
		ArrayList<Double> num = new ArrayList<>();
		ArrayList<Object> stringName = new ArrayList<>();
		num.add((Double)number.getData()[0]);
		stringName.add(string.getData()[0]);

		for(int i = 1; i < string.getData().length; i++) {
			if(stringName.contains(string.getData()[i])) {
				for(int j = 0; j < num.size(); j++) {
					if(stringName.get(j).equals(string.getData()[i])) {
						num.set(j, num.get(j) + (Double)number.getData()[i]);
						break;
					}
				}
			}
			else {
				num.add((Double)number.getData()[i]);
				stringName.add(string.getData()[i]);}
		}
		Object[] numDone = new Object[num.size()];
		Object[] stringDone = new Object[stringName.size()];
		for(int i = 0; i < num.size(); i++) {
			numDone[i] = num.get(i);
			stringDone[i] = stringName.get(i);
		}
		this.numColumn = new DataColumn(number.getTypeName(), numDone);
		this.textColumn = new DataColumn(string.getTypeName(), stringDone);
		
		
	}

	/**
	 * Get Method 
	 * 
	 * @return the numColumn
	 */
	public DataColumn getnumColumn() {
		return numColumn;
	}

	/**
	 * Get Method 
	 * 
	 * @return the textColumn
	 */
	public DataColumn getTextColumn() {
		return textColumn;
	} 

	/**
	 * Get Method 
	 * 
	 * @return the numItemsName
	 */
	public String getNumItemsName() {
		return numItemsName;
	}
}

package core.comp3111;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;

public class PieChartObj extends GeneralChart {
	private DataColumn numColumn;
	private DataColumn textColumn;
	private String numItemsName;

	public PieChartObj(DataColumn number, DataColumn string, DataTable dt, String numItemsName, String stringItemsName) {
		this.chartName = "Chart " + (dt.getStoredChart().size() + 1) + " [Pie] [Items: " + stringItemsName + 
				", Quantity: " + numItemsName + "]";
		this.numItemsName = numItemsName;
		Object[] numTemp = number.getData().clone();
		Object[] stringTemp = string.getData().clone();
		Arrays.sort(stringTemp);
		Arrays.sort(numTemp);
		ArrayList<Double> num = new ArrayList<>();
		ArrayList<Object> stringName = new ArrayList<>();
		num.add((Double)number.getData()[0]);
		stringName.add(stringTemp[0]);
		int counter = 0;
		for(int i = 1; i < stringTemp.length; i++) {
			if(stringTemp[i].equals(stringName.get(counter))) {
				num.set(counter, num.get(counter)+(Double)numTemp[i]);
			}
			else {
				stringName.add(stringTemp[i]);
				num.add((Double)number.getData()[i]);
				counter++;
			}
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

package core.comp3111;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

/**
 * 2D array of data values with the following requirements: (1) There are 0 to
 * many columns (2) The number of row for each column is the same (3) 2 columns
 * may have different type (e.g. String and Number). (4) A column can be
 * uniquely identified by its column name (5) add/remove a column is supported
 * (6) Suitable exception handling is implemented
 * 
 * @author cspeter, tmtam, cpkoaajack, pkyipab
 *
 */
public class DataTable implements Serializable {

	/**
	 * Construct - Create an empty DataTable
	 */
	public DataTable(String fileName) {

		// In this application, we use HashMap data structure defined in
		// java.util.HashMap
		dc = new LinkedHashMap<String, DataColumn>();
		storedChart = new ArrayList<GeneralChart>();
		this.fileName = fileName;
	}

	/**
	 * Add a data column to the table.
	 * 
	 * @param colName
	 *            - name of the column. It should be a unique identifier
	 * @param newCol
	 *            - the data column
	 * @throws DataTableException
	 *             - It throws DataTableException if a column is already exist, or
	 *             the row size does not match.
	 */
	public void addCol(String colName, DataColumn newCol) throws DataTableException {
		if (containsColumn(colName)) {
			throw new DataTableException("addCol: The column already exists");
		}

		int curNumCol = getNumCol();
		if (curNumCol == 0) {
			dc.put(colName, newCol); // add the column
			return; // exit the method
		}

		// If there is more than one column,
		// we need to ensure that all columns having the same size

		int curNumRow = getNumRow();
		if (newCol.getSize() != curNumRow) {
			throw new DataTableException(String.format(
					"addCol: The row size does not match: newCol(%d) and curNumRow(%d)", newCol.getSize(), curNumRow));
		}

		dc.put(colName, newCol); // add the mapping
	}

	/**
	 * Remove a column from the data table
	 * 
	 * @param colName
	 *            - The column name. It should be a unique identifier
	 * @throws DataTableException.
	 *             It throws DataTableException if the column does not exist
	 */
	public void removeCol(String colName) throws DataTableException {
		if (containsColumn(colName)) {
			dc.remove(colName);
			return;
		}
		throw new DataTableException("removeCol: The column does not exist");
	}

	/**
	 * Get the DataColumn object based on the give colName. Return null if the
	 * column does not exist
	 * 
	 * @param colName
	 *            The column name
	 * @return DataColumn reference or null
	 */
	public DataColumn getCol(String colName) {
		if (containsColumn(colName)) {
			return dc.get(colName);
		}
		return null;
	}
	
	
	/**
	 * Get the DataColumn object based on the give colName. Return null if the
	 * column does not exist
	 * 
	 * @param n
	 *            The order of the specific column
	 * @return DataColumn reference or null
	 * 
	 * @author dev-pkyipab
	 */
	public DataColumn getColByNum(int n) {
		int counter = 0;
		
		if(n < dc.size()) {
			for (Object key : dc.keySet()) {
				if(counter == n) {
					return getCol((String)key);
				}
				++counter;
	        }
		}
		return null;
	}
	
	/**
	 * Get the DataColumn object based on the give colName. Return null if the
	 * column does not exist
	 * 
	 * @param order
	 *            The order of the specific column
	 * @return The key of the Map 
	 * 
	 * @author dev-pkyipab
	 */
	public String getColName(int order) {
		int counter = 0;
		for (Map.Entry<String, DataColumn> entry : dc.entrySet()) {
			if(order == counter) {
				return entry.getKey();
			}
			++counter;
		}
		return null;
	}

	/**
	 * Check whether the column exists by the given column name
	 * 
	 * @param colName
	 * @return true if the column exists, false otherwise
	 */
	public boolean containsColumn(String colName) {
		return dc.containsKey(colName);
	}

	/**
	 * Return the number of column in the data table
	 * 
	 * @return the number of column in the data table
	 */
	public int getNumCol() {
		return dc.size();
	}

	/**
	 * Return the number of row of the data table. This data structure ensures that
	 * all columns having the same number of row
	 * 
	 * @return the number of row of the data table
	 */
	public int getNumRow() {
		if (dc.size() <= 0)
			return dc.size();

		// Pick the first entry and get its size
		// assumption: For DataTable, all columns should have the same size
		Map.Entry<String, DataColumn> entry = dc.entrySet().iterator().next();
		return dc.get(entry.getKey()).getSize();
	}
	
	/**
	 * get method
	 * 
	 * @return the fileName
	 */
	public String getFileName() {
		return fileName;
	}
	/**
	 * get method
	 * 
	 * @return the storedChart
	 */
	public ArrayList<GeneralChart> getStoredChart(){
		return this.storedChart;
	}

	/**
	 * Helper method for line chart creation
	 * 
	 * @return the list with just Number Type column
	 */
	public ArrayList<String> getNumberTypeColnumName(){
		ArrayList<String> list = new ArrayList<String>();
		for(String key: dc.keySet()) {
			if(dc.get(key).getTypeName().equals(DataType.TYPE_NUMBER)) {
				list.add(key);
			}
		}
		return list;
	}

	/**
	 * Helper method for pie chart creation
	 * 
	 * @return the list with non negative Number Type column
	 */
	public ArrayList<String> getNonNegativeNumberTypeColnumName(){
		ArrayList<String> list = new ArrayList<String>();
		for(String key: dc.keySet()) {
			if(dc.get(key).getTypeName().equals(DataType.TYPE_NUMBER)) {
				list.add(key);
				for(int i = 0; i < dc.get(key).getSize(); i++) {
					if(((Number)dc.get(key).getData()[i]).doubleValue() < 0) {
						list.remove(list.size() - 1);
						break;
					}
				}
			}
		}
		return list;
	}

	/**
	 * Helper method for pie chart creation
	 * 
	 * @return the list that just string Type column
	 */
	public ArrayList<String> getStringTypeColnumName(){
		ArrayList<String> list = new ArrayList<String>();
		for(String key: dc.keySet()) {
			if(dc.get(key).getTypeName().equals(DataType.TYPE_STRING)) {
				list.add(key);
			}
		}
		return list;
	}

	/**
	 * Helper method for Data Import. To fill in the empty row at the column
	 * 
	 */
	public void handleEmptyNumericSpace(String action) {	
		dc.forEach((name, col) -> {
			if(col.hasNumericEmpty()) {
			switch(action) {
				case "Zero":
					col.fillin(0);
					break;
				case "Mean":
					col.fillin(col.getMean());
					break;
				case "Median":
					col.fillin(col.getMedian());
					break;
				}
			}
		});
	}
	/**
	 * get method
	 * 
	 * @return the Map dc
	 */
	public Map<String, DataColumn> getMap() {	
		return dc;
	}
	
	// attribute: A java.util.Map interface
	// KeyType: String
	// ValueType: DataColumn
	private Map<String, DataColumn> dc;
	private String fileName;
	private ArrayList<GeneralChart> storedChart;

}

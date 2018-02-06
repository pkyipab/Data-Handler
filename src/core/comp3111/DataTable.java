package core.comp3111;

import java.util.HashMap;
import java.util.Map;

public class DataTable {

	public DataTable() {
		dc = new HashMap<String, DataColumn>();
	}

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

	public void removeCol(String colName) throws DataTableException {
		if (containsColumn(colName)) {
			dc.remove(colName);
			return;
		}
		throw new DataTableException("removeCol: The column does not exist");
	}

	public DataColumn getCol(String colName) {
		if (containsColumn(colName)) {
			return dc.get(colName);
		}
		return null;
	}

	public boolean containsColumn(String colName) {
		return dc.containsKey(colName);
	}

	public int getNumCol() {
		return dc.size();
	}

	public int getNumRow() {
		if (dc.size() <= 0)
			return dc.size();

		// Pick the first entry and get its size
		// assumption: For DataTable, all columns should have the same size
		Map.Entry<String, DataColumn> entry = dc.entrySet().iterator().next();
		return dc.get(entry.getKey()).getSize();
	}

	private Map<String, DataColumn> dc;

}

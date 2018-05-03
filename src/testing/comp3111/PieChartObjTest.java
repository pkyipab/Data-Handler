package testing.comp3111;

import static org.junit.Assert.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import core.comp3111.DataColumn;
import core.comp3111.DataTable;
import core.comp3111.DataTableException;
import core.comp3111.DataType;
import core.comp3111.PieChartObj;

public class PieChartObjTest {
	@Test
	void TestAllMethod() throws DataTableException {

		DataTable dataTable = new DataTable("Test");
		DataColumn testDataColumn = new DataColumn(DataType.TYPE_STRING, new String[] {"a", "b", "c"});
		DataColumn testDataColumn2 = new DataColumn(DataType.TYPE_NUMBER, new Number[] {1.1, 2.1, 3.1});
		dataTable.addCol("TestGetColNum", testDataColumn);
		dataTable.addCol("TestGetColNum2", testDataColumn2);
		
		PieChartObj obj = new PieChartObj(testDataColumn2, testDataColumn, dataTable, "numItemsName", "stringItemsName");
		assert(obj.getnumColumn().getSize() == 3);
		assert(obj.getTextColumn().getSize() == 3);
		assertEquals(obj.getNumItemsName(),  "numItemsName");

		dataTable = new DataTable("Test");
		testDataColumn = new DataColumn(DataType.TYPE_STRING, new String[] {"a", "a", "c", "c", "c", "a", "b"});
		testDataColumn2 = new DataColumn(DataType.TYPE_NUMBER, new Number[] {1, 2, 3, 1, 2, 3, 1});
		dataTable.addCol("TestGetColNum", testDataColumn);
		dataTable.addCol("TestGetColNum2", testDataColumn2);
		obj = new PieChartObj(testDataColumn2, testDataColumn, dataTable, "numItemsName", "stringItemsName");

	}
	
}
